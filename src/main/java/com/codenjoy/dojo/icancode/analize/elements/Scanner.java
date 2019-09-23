package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.DomainElement;
import com.codenjoy.dojo.icancode.model.Elements;
import com.codenjoy.dojo.services.Direction;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scanner {
    private final Logger log = Logger.getLogger(Scanner.class);

    public DomainElement getMainCellElement(List<Elements> elements, int x, int y) {
        List<DomainElement> detectedElements = new ArrayList<>();
        elements.forEach(element -> {
            DomainElement detectElement = getElement(element.ch(), x, y);
            detectedElements.add(detectElement);
        });
        DomainElement result = detectedElements.stream()
                .reduce(new ElementNotDetected(x, y), (left, right) ->
                        left.getPriority().isHigh(right.getPriority()) ? left : right);
        return result;
    }

    public DomainElement getElement(char element, int x, int y) {

        Elements elements = Elements.valueOf(element);
        switch (elements) {

            case HOLE:
                return new ElementHole(x, y);
            case BOX:
                return new ElementBox(x, y);

            case GOLD:
                return new ElementGold(x, y);
            case EXIT:
                return new ElementExit(x, y);


            case ROBO_LASER:
                return new ElementMyLaser(x, y);


            case ROBO:
            case ROBO_FALLING:
            case ROBO_FLYING:
                //себя считаем пока как свободный элемент
                return new ElementFree(x, y);
            case START:
            case EMPTY:
            case FLOOR:
                return new ElementFree(x, y);

            case FEMALE_ZOMBIE:
            case MALE_ZOMBIE:
                return new ElementZombie(x, y);

            case ANGLE_IN_LEFT:
            case WALL_FRONT:
            case ANGLE_IN_RIGHT:
            case WALL_RIGHT:
            case ANGLE_BACK_RIGHT:
            case WALL_BACK:
            case ANGLE_BACK_LEFT:
            case WALL_LEFT:
            case WALL_BACK_ANGLE_LEFT:
            case WALL_BACK_ANGLE_RIGHT:
            case ANGLE_OUT_RIGHT:
            case ANGLE_OUT_LEFT:
            case SPACE:
                return new ElementWall(x, y);

            case LASER_MACHINE_CHARGING_LEFT:
                return new ElementSleepMachine(x, y, Direction.LEFT);
            case LASER_MACHINE_CHARGING_RIGHT:
                return new ElementSleepMachine(x, y, Direction.RIGHT);
            case LASER_MACHINE_CHARGING_UP:
                return new ElementSleepMachine(x, y, Direction.UP);
            case LASER_MACHINE_CHARGING_DOWN:
                return new ElementSleepMachine(x, y, Direction.DOWN);
            case LASER_MACHINE_READY_LEFT:
                return new ElementAngryMachine(x, y, Direction.LEFT);
            case LASER_MACHINE_READY_RIGHT:
                return new ElementAngryMachine(x, y, Direction.RIGHT);
            case LASER_MACHINE_READY_UP:
                return new ElementAngryMachine(x, y, Direction.UP);
            case LASER_MACHINE_READY_DOWN:
                return new ElementAngryMachine(x, y, Direction.DOWN);


            case ROBO_OTHER:
            case ROBO_OTHER_FLYING:
                return new ElementEnemyRobot(x, y);

            case ROBO_OTHER_FALLING:

            case ROBO_OTHER_LASER:


            case LASER_LEFT:
                return new ElementLaser(x, y, Direction.LEFT);
            case LASER_RIGHT:
                return new ElementLaser(x, y, Direction.RIGHT);
            case LASER_UP:
                return new ElementLaser(x, y, Direction.UP);
            case LASER_DOWN:
                return new ElementLaser(x, y, Direction.DOWN);

            case FOG:

            case BACKGROUND:
            case ZOMBIE_START:
            case ZOMBIE_DIE:
            default:
                return new ElementNotDetected(x, y);

        }
    }

}
