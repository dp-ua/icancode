package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.DomainElement;
import com.codenjoy.dojo.icancode.analize.elements.domain.EAttack;
import com.codenjoy.dojo.icancode.analize.elements.domain.EJump;
import com.codenjoy.dojo.icancode.analize.elements.domain.EPriority;
import com.codenjoy.dojo.services.Direction;
import org.apache.log4j.Logger;

public class ElementMyLaser extends DomainElement implements EJump, EAttack {
    private final Logger log = Logger.getLogger(ElementMyLaser.class);

    public ElementMyLaser(int x, int y) {
        super(x, y);
        this.setPriority(EPriority.HIGH);
        System.out.println("ВОУ ВОУ ВОУ. я стрелял");
    }

    @Override
    public boolean isMyLaser() {
        return true;
    }

    public ElementMyLaser(int x, int y, Direction attackDirection) {
        super(x, y,attackDirection);
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
