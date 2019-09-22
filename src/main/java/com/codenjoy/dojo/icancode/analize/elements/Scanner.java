package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.DomainElement;
import com.codenjoy.dojo.icancode.model.Elements;
import org.apache.log4j.Logger;

public class Scanner {
    private final Logger log = Logger.getLogger(Scanner.class);

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

            case START:
            case EMPTY:
            case FLOOR:
            case ZOMBIE_START:
            case ZOMBIE_DIE:
                return new ElementFree(x, y);

            case FEMALE_ZOMBIE:
            case MALE_ZOMBIE: return new ElementZombie(x,y);

            case LASER_MACHINE_CHARGING_LEFT:
            case LASER_MACHINE_CHARGING_RIGHT:
            case LASER_MACHINE_CHARGING_UP:
            case LASER_MACHINE_CHARGING_DOWN:
            case LASER_MACHINE_READY_LEFT:
            case LASER_MACHINE_READY_RIGHT:
            case LASER_MACHINE_READY_UP:
            case LASER_MACHINE_READY_DOWN:


            case ROBO:
            case ROBO_FALLING:
            case ROBO_FLYING:
            case ROBO_LASER:
            case ROBO_OTHER:
            case ROBO_OTHER_FALLING:
            case ROBO_OTHER_FLYING:
            case ROBO_OTHER_LASER:
            case LASER_LEFT:
            case LASER_RIGHT:
            case LASER_UP:
            case LASER_DOWN:

            case BACKGROUND:

            default:
                return new ElementWall(x, y);
        }
    }

}
