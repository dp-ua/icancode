package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.DomainElement;
import com.codenjoy.dojo.icancode.analize.elements.domain.DomainJump;
import com.codenjoy.dojo.icancode.analize.elements.domain.DomainWalk;
import org.apache.log4j.Logger;

public class ElementFree extends DomainElement implements DomainWalk, DomainJump {
    private final Logger log = Logger.getLogger(ElementFree.class);

    @Override
    public String toString() {
        return "ElementFree{}" + super.toString();
    }

    public ElementFree(int x, int y) {
        super(x, y);
    }

    @Override
    public BackgroundMatrix getBackgroundMatrix() {
        BackgroundMatrix backgroundMatrix = new BackgroundMatrix();
        int[][] matrix = new int[1][1];
        matrix[0][0] = 1;
        backgroundMatrix.setMatrix(matrix);

        return backgroundMatrix;
    }
}
