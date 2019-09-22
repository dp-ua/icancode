package com.codenjoy.dojo.icancode.analize;

import com.codenjoy.dojo.icancode.analize.elements.domain.DomainElement;
import com.codenjoy.dojo.icancode.analize.elements.Scanner;
import com.codenjoy.dojo.icancode.analize.elements.domain.DomainJump;
import com.codenjoy.dojo.icancode.analize.elements.domain.DomainWalk;
import com.codenjoy.dojo.icancode.client.Board;
import com.codenjoy.dojo.icancode.client.Command;
import com.codenjoy.dojo.icancode.model.Elements;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.util.*;


public class Analize {
    private final Logger log = Logger.getLogger(Analize.class);
    Scanner scanner = new Scanner();

    private DomainElement[][] elementBoard;

    private DomainElement[][] getBoardOfElements(Board board) {
        char[][] field = board.getField();
        DomainElement[][] result = new DomainElement[field.length][field.length];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                char c =  field[i][j];

                if (board.isAt(i,j, Elements.BOX)) c=Elements.BOX.ch();
                if (board.isAt(i,j, Elements.FEMALE_ZOMBIE)) c=Elements.FEMALE_ZOMBIE.ch();
                if (board.isAt(i,j, Elements.MALE_ZOMBIE)) c=Elements.MALE_ZOMBIE.ch();
                result[i][j] = scanner.getElement(c, i, j);
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
                if (currentElement instanceof DomainWalk) {
                    Direction direction = Direction.UP;
                    do {
                        int x = direction.changeX(i);
                        int y = direction.changeY(j);
                        if (isPointInField(x, y, elementBoard)) {
                            DomainElement tempElement = elementBoard[x][y];
                            if (tempElement instanceof DomainWalk)
                                currentElement.addLinkToCell(tempElement, Command.go(direction));
                            if (tempElement instanceof DomainJump) {
                                x = direction.changeX(x);
                                y = direction.changeY(y);
                                if (isPointInField(x, y, elementBoard)) {
                                    tempElement = elementBoard[x][y];
                                    if (tempElement instanceof DomainWalk)
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


    private List<DomainElement> getTargets(Board board) {
        List<DomainElement> result = new ArrayList<>();
        List<Point> goldList = board.getGold();
        List<Point> exitList = board.getExits();
        for (Point goldPoint : goldList) {
            result.add(elementBoard[goldPoint.getX()][goldPoint.getY()]);
        }
        if (result.size() == 0)
            for (Point exitPoint : exitList)
                result.add(elementBoard[exitPoint.getX()][exitPoint.getY()]);
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
        Command result = Command.doNothing();

        analizeBoard(board);

        DomainElement myPositionElement = elementBoard[board.getMe().getX()][board.getMe().getY()];
        Map<DomainElement, Pair<Integer, Command>> allLinksFromMyPosition = getAllLinksFromThisPoint(myPositionElement);
        List<DomainElement> targets = getTargets(board);
        if (targets.size() > 0) {
            DomainElement priorityTarget = getPriorityTarget(targets, allLinksFromMyPosition);
            if (priorityTarget != null) {
                result = allLinksFromMyPosition.get(priorityTarget).getValue();
            } else result = getBestRandomMove(allLinksFromMyPosition);
        } else result = getBestRandomMove(allLinksFromMyPosition);
        return result;
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
                    Command command = move == 1 ? entryLinks.getValue() : entryResult.getValue().getValue();
                    temp.put(entryLinks.getKey(), new Pair<>(move, command));
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
