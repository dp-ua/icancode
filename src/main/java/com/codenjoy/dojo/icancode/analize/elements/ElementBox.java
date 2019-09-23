package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.*;
import org.apache.log4j.Logger;

public class ElementBox extends DomainElement implements EJump, EMove, EBlockLaser {
    private final Logger log = Logger.getLogger(ElementBox.class);

    public ElementBox(int x, int y) {
        super(x, y);this.setPriority(EPriority.MID);
    }

    @Override
    public BackgroundMatrix getBackgroundMatrix() {
        BackgroundMatrix backgroundMatrix = new BackgroundMatrix();
        int [][] matrix = new int[1][1];
        matrix[0][0]=-5;
        backgroundMatrix.setMatrix(matrix);

        return backgroundMatrix;
    }
}
