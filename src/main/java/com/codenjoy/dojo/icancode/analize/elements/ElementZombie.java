package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.*;
import com.codenjoy.dojo.services.Direction;
import org.apache.log4j.Logger;

public class ElementZombie extends DomainElement implements EJump,  EKill, EBlockLaser {
    private final Logger log = Logger.getLogger(ElementZombie.class);

    public ElementZombie(int x, int y) {
        super(x, y);
        this.setPriority(EPriority.HIGH);
    }

    public ElementZombie(int x, int y, Direction attackDirection) {
        this(x, y);
        //ignore attack direction. Use default
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
