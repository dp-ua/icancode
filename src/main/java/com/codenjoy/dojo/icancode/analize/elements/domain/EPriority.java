package com.codenjoy.dojo.icancode.analize.elements.domain;

import lombok.val;

import java.util.Comparator;

public enum EPriority {
    HIGH(2), MID(1), LOW(0);

    private int value;

    EPriority(int value) {
        this.value = value;
    }

    public boolean isHigh(EPriority object) {
        return value > object.value;
    }
}
