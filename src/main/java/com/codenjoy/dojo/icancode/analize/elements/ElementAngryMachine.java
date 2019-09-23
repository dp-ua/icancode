package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.*;
import com.codenjoy.dojo.services.Direction;
import org.apache.log4j.Logger;

public class ElementAngryMachine extends DomainElement implements EJump, EAttack, EBlockLaser {
    private final Logger log = Logger.getLogger(ElementAngryMachine.class);

    public ElementAngryMachine(int x, int y) {
        super(x, y);
    }

    public ElementAngryMachine(int x, int y, Direction attackDirection) {
        super(x, y, attackDirection);
        this.setPriority(EPriority.HIGH);
    }

    @Override
    public BackgroundMatrix getBackgroundMatrix() {
        BackgroundMatrix backgroundMatrix = new BackgroundMatrix();
        int[][] matrix = new int[1][1];
        matrix[0][0] = -5;
        backgroundMatrix.setMatrix(matrix);

        return backgroundMatrix;
    }
}
