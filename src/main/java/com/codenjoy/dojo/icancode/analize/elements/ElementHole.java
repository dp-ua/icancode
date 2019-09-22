package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.DomainElement;
import com.codenjoy.dojo.icancode.analize.elements.domain.DomainJump;
import org.apache.log4j.Logger;


public class ElementHole extends DomainElement implements DomainJump {
    private final Logger log = Logger.getLogger(ElementHole.class);

    public ElementHole(int x, int y) {
        super(x, y);
    }

    @Override
    public BackgroundMatrix getBackgroundMatrix() {
        BackgroundMatrix backgroundMatrix = new BackgroundMatrix();
        int [][] matrix = new int[1][1];
        matrix[0][0]=-10;
        backgroundMatrix.setMatrix(matrix);

        return backgroundMatrix;
    }
}
