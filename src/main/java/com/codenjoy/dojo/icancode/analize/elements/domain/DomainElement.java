package com.codenjoy.dojo.icancode.analize.elements.domain;

import com.codenjoy.dojo.icancode.analize.elements.BackgroundMatrix;
import com.codenjoy.dojo.icancode.client.Command;
import com.codenjoy.dojo.services.Direction;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


public abstract class DomainElement {

    @Getter
    private Map<DomainElement, Command> links;
    @Getter
    private int x;
    @Getter
    private int y;

    @Setter
    @Getter
    private Direction attackDirection;

    @Setter
    @Getter
    EPriority priority = EPriority.LOW;

public boolean isMyLaser(){
    return false;
}

    @Override
    public String toString() {
        return "{" +
                "links=" + links.size() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public DomainElement(int x, int y, Direction attackDirection) {
        this(x, y);
        this.attackDirection = attackDirection;
    }

    public DomainElement(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    private DomainElement() {
        links = new HashMap<>();
        attackDirection = Direction.STOP;

    }

    public BackgroundMatrix getBackgroundMatrix() {
        return null;
    }

    public void addLinkToCell(DomainElement element, Command command) {
        links.put(element, command);
    }

    public boolean isLink(DomainElement cell) {
        return links.containsKey(cell);
    }

}
