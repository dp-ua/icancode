package com.codenjoy.dojo.icancode.analize.elements.domain;

import com.codenjoy.dojo.icancode.analize.elements.BackgroundMatrix;
import com.codenjoy.dojo.icancode.client.Command;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public abstract class DomainElement {
    @Getter
    private Map<DomainElement,Command> links;
    @Getter
    private int x;
    @Getter
    private int y;

    @Override
    public String toString() {
        return "{" +
                "links=" + links.size() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public DomainElement(int x, int y) {
        this();
        this.x=x;
        this.y=y;
    }

    public BackgroundMatrix getBackgroundMatrix() {
        return null;
    }

    private DomainElement() {
        links = new HashMap<>();
    }

    public void addLinkToCell(DomainElement element,Command command) {
        links.put(element,command);
    }

    public boolean isLink(DomainElement cell){
        return  links.containsKey(cell);
    }

}
