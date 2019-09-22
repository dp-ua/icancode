package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.DomainElement;
import org.apache.log4j.Logger;

public class ElementWall extends DomainElement {
    private final Logger log = Logger.getLogger(ElementWall.class);

    public ElementWall(int x, int y) {
        super(x, y);
    }

    @Override
    public BackgroundMatrix getBackgroundMatrix() {
        BackgroundMatrix backgroundMatrix = new BackgroundMatrix();
        int[][] matrix = new int[1][1];
        matrix[0][0] = -10;
        backgroundMatrix.setMatrix(matrix);

        return backgroundMatrix;
    }
}
