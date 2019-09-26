package com.codenjoy.dojo.icancode.analize;

import com.codenjoy.dojo.icancode.analize.elements.ElementFree;
import com.codenjoy.dojo.icancode.analize.elements.Scanner;
import com.codenjoy.dojo.icancode.analize.elements.domain.*;
import com.codenjoy.dojo.icancode.client.Board;
import com.codenjoy.dojo.icancode.client.Command;
import com.codenjoy.dojo.icancode.model.Elements;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Stream;

public class Analize {
    public static final int RANGE_ATTACK_SCAN = 4;

    private int tick;
    private Pair<Integer, Direction> fireTick = new Pair<>(Integer.MAX_VALUE, Direction.STOP);
    private int noMoveTick;


    private final Logger log = Logger.getLogger(Analize.class);
    Scanner scanner = new Scanner();

    private DomainElement[][] elementBoard;

    private DomainElement[][] getBoardOfElements(Board board) {
        char[][] field = board.getField();
        DomainElement[][] result = new DomainElement[field.length][field.length];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                char c = field[i][j];
                List<Elements> allAt = board.getAllAt(i, j);
                result[i][j] = scanner.getMainCellElement(allAt, i, j);
            }
        }
        return result;
    }

    private boolean isPointInField(int x, int y, Object[][] field) {
        if (x < 0 || y < 0) return false;
        if (x >= field.length || y >= field[0].length) return false;
        return true;
    }

    private boolean isPointInField(Point point, Object[][] field) {
        return isPointInField(point.getX(), point.getY(), field);
    }

    private void setLinkBetweenCells() {
        for (int i = 0; i < elementBoard.length; i++) {
            for (int j = 0; j < elementBoard[i].length; j++) {
                DomainElement currentElement = elementBoard[i][j];
                if (currentElement instanceof EWalk) {
                    Direction direction = Direction.UP;
                    do {
                        int x = direction.changeX(i);
                        int y = direction.changeY(j);
                        if (isPointInField(x, y, elementBoard)) {
                            DomainElement tempElement = elementBoard[x][y];
                            if (tempElement instanceof EWalk)
                                currentElement.addLinkToCell(tempElement, Command.go(direction));
                            if (tempElement instanceof EJump) {
                                x = direction.changeX(x);
                                y = direction.changeY(y);
                                if (isPointInField(x, y, elementBoard)) {
                                    tempElement = elementBoard[x][y];
                                    if (tempElement instanceof EWalk)
                                        currentElement.addLinkToCell(tempElement, Command.jump(direction));
                                }
                            }
                        }
                        direction = direction.clockwise();
                    }
                    while (direction != Direction.UP);
                }
            }
        }
    }

    private void analizeBoard(Board board) {
        elementBoard = getBoardOfElements(board);
        setLinkBetweenCells();
    }

    private List<DomainElement> getUnlimitCell() {
        List<DomainElement> result = new ArrayList<>();
        int x = 0;
        int y = 19;
        Direction scanDirection = Direction.RIGHT;
        do {
            x = scanDirection.changeX(x);
            y = scanDirection.changeY(y);
            if (isPointInField(x, y, elementBoard)) {
                DomainElement element = elementBoard[x][y];
                if (element instanceof ElementFree) result.add(element);
            } else {
                x -= scanDirection.changeX(x) - x;
                y -= scanDirection.changeY(y) - y;
                scanDirection = scanDirection.clockwise();
            }

        }
        while (!(x == 0 && y == 19));
        return result;
    }


    private List<DomainElement> getTargets(Board board) {

        List<DomainElement> result = new ArrayList<>();
        List<Point> goldList = board.getGold();
        List<Point> robots = board.getOtherHeroes();
        List<Point> exitList = board.getExits();
        List<Point> zombies = board.getZombies();
        for (Point exitPoint : exitList)
            result.add(elementBoard[exitPoint.getX()][exitPoint.getY()]);
        for (Point goldPoint : goldList) {
            result.add(elementBoard[goldPoint.getX()][goldPoint.getY()]);
        }
        if (result.size() == 0) {
            List<DomainElement> unlimitCell = getUnlimitCell();
            for (DomainElement element : unlimitCell) {
                result.add(element);
            }
        }
        if (result.size() == 0)
            for (Point point : robots) {
                result.add(elementBoard[point.getX()][point.getY()]);
            }
        if (result.size() == 0)
            for (Point point : zombies)
                result.add(elementBoard[point.getX()][point.getY()]);

// TODO: 22.09.2019 научиться искать выходы, если на карте нет специальной точки
        return result;
    }

    private DomainElement getPriorityTarget(List<DomainElement> targets, Map<DomainElement, Pair<Integer, Command>> allLinksFromMyPosition) {
        int min = Integer.MAX_VALUE;
        DomainElement result = null;

        for (DomainElement target : targets) {
            if (allLinksFromMyPosition.containsKey(target)) {
                Pair<Integer, Command> moveAndCommand = allLinksFromMyPosition.get(target);
                if (min > moveAndCommand.getKey()) {
                    min = moveAndCommand.getKey();
                    result = target;
                }
            }
        }
        return result;
    }

    private Command getBestRandomMove(Map<DomainElement, Pair<Integer, Command>> allLinksFromMyPosition) {
        ArrayList<Pair<Integer, Command>> pairs = new ArrayList<>(allLinksFromMyPosition.values());
        int rnd = (int) (Math.random() * pairs.size());
        return pairs.get(rnd).getValue();
    }

    public Command getNextMove(Board board) {
        tick++;
        Command result = Command.jump();

        analizeBoard(board);

        DomainElement myPosition = elementBoard[board.getMe().getX()][board.getMe().getY()];
        int enemies = board.getOtherHeroes().size();
        Map<DomainElement, Pair<Integer, Command>> allLinksFromMyPosition = getAllLinksFromThisPoint(myPosition);
        Map<DomainElement, Direction> closetEnemiesThatISee = getClosetEnemiesThatISee(myPosition);
        Direction prohibitedDirection = (tick - fireTick.getKey() <= 1) ? fireTick.getValue() : Direction.STOP;
        if (closetEnemiesThatISee.size() > 0) {
            System.out.println("Кругом враги. Рота в ружье");
            Direction fireDirection = getDirectionForFire(myPosition, closetEnemiesThatISee);
            result = Command.fire(fireDirection);
            fireTick = new Pair<>(tick, fireDirection);
        } else {
            result = getCommandForMove(board, allLinksFromMyPosition);
            if (result.toString().contains(prohibitedDirection.toString())) result = Command.doNothing();
        }
        if ("".equals(result.toString())) {
            System.out.println("Действие пустое");
            if (tick - noMoveTick == 1) {
                result = randomMove(prohibitedDirection);
                System.out.println("Делаю случайный ход");
            } else {
                noMoveTick = tick;
                System.out.println("Пропущу один ход");
            }
        }
        System.out.println("Буду делать:  " + result);
        return result;
    }

    private Command randomMove(Direction prohibited) {
        while (true) {
            Direction random = Direction.random();
            if (!random.equals(prohibited)) return Command.go(random);
        }
    }

    private Direction getDirectionForFire(DomainElement myPositionElement, Map<DomainElement, Direction> closetEnemiesThatISee) {
        double range = RANGE_ATTACK_SCAN + 1;
        Direction closest = Direction.STOP;
        for (Map.Entry<DomainElement, Direction> entry : closetEnemiesThatISee.entrySet()) {
            double tempRange = Math.sqrt(
                    Math.pow(myPositionElement.getX() - entry.getKey().getX(), 2) +
                            Math.pow(myPositionElement.getY() - entry.getKey().getY(), 2));
            if (tempRange < range) {
                range = tempRange;
                closest = entry.getValue();
            }
        }
        return closest;
    }

    private Command getCommandForMove(Board board, Map<DomainElement, Pair<Integer, Command>> allLinksFromMyPosition) {
        Command result;
        List<DomainElement> targets = getTargets(board);
        if (targets.size() > 0) {
            DomainElement priorityTarget = getPriorityTarget(targets, allLinksFromMyPosition);
            if (priorityTarget != null) {
                System.out.println("Целюсь в " + priorityTarget);
                result = allLinksFromMyPosition.get(priorityTarget).getValue();
            } else {
                System.out.println("Нет достижимых целей. Хожу рандомно");
                result = getBestRandomMove(allLinksFromMyPosition);
            }
        } else {
            System.out.println("Нет целей. Хожу рандомно");
            result = getBestRandomMove(allLinksFromMyPosition);
        }
        return result;
    }


    private boolean isICanShoot() {
        return true;
    }

    private Map<DomainElement, Direction> getClosetEnemiesThatISee(DomainElement me) {
        Map<DomainElement, Direction> result = new HashMap<>();
        Direction scanDirection = Direction.UP;

        do {
            int x = me.getX();
            int y = me.getY();
            for (int i = 0; i < RANGE_ATTACK_SCAN; i++) {
                x = scanDirection.changeX(x);
                y = scanDirection.changeY(y);
                if (isPointInField(x, y, elementBoard)) {
                    DomainElement tempElement = elementBoard[x][y];
                    if (tempElement instanceof EKill) {
                        result.put(tempElement, scanDirection);
                        break;
                    }
                    if (tempElement instanceof EBlockLaser) {
                        break;
                    }
                } else break;
            }
            scanDirection = scanDirection.clockwise();
        } while (scanDirection != Direction.UP);
        return result;
    }

    private boolean isGoodElementForMove(DomainElement element) {
        if (element instanceof EAttack) return false;
        if (element instanceof EKill) return true;
        Map<DomainElement, Command> links = element.getLinks();
        for (Map.Entry<DomainElement, Command> entry : links.entrySet()) {
            Command command = entry.getValue();
            switch (command.toString()) {
                case "UP":
                case "RIGHT":
                case "LEFT":
                case "DOWN":
                    DomainElement link = entry.getKey();
                    if (link instanceof EAttack) {
                        Direction attackDirection = link.getAttackDirection();
                        if (attackDirection == Direction.STOP) return false;
                        if (attackDirection.inverted() == Direction.valueOf(command.toString())) return false;
                    }
            }
        }

        return true;
    }

    private Map<DomainElement, Pair<Integer, Command>> getAllLinksFromThisPoint(DomainElement element) {
        Map<DomainElement, Pair<Integer, Command>> result = new HashMap<>();
        result.put(element, new Pair<>(0, Command.doNothing()));
        int move = 0;
        while (true) {
            move++;
            int count = 0;
            Map<DomainElement, Pair<Integer, Command>> temp = new HashMap<>();
            for (Map.Entry<DomainElement, Pair<Integer, Command>> entryResult : result.entrySet()) {
                Map<DomainElement, Command> links = entryResult.getKey().getLinks();
                for (Map.Entry<DomainElement, Command> entryLinks : links.entrySet()) {
                    if (isGoodElementForMove(entryLinks.getKey())) {
                        Command command = move == 1 ? entryLinks.getValue() : entryResult.getValue().getValue();
                        temp.put(entryLinks.getKey(), new Pair<>(move, command));
                    }
                }
            }
            for (Map.Entry<DomainElement, Pair<Integer, Command>> entryTemp : temp.entrySet()) {
                if (result.containsKey(entryTemp.getKey())) {
                    Pair<Integer, Command> valueFromResult = result.get(entryTemp.getKey());
                    if (valueFromResult.getKey() > entryTemp.getValue().getKey())
                        result.put(entryTemp.getKey(), entryTemp.getValue());
                } else {
                    result.put(entryTemp.getKey(), entryTemp.getValue());
                    count++;
                }
            }
            if (count == 0) break;
        }
        return result;
    }
}
