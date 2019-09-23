package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.DomainElement;
import com.codenjoy.dojo.icancode.analize.elements.domain.EJump;
import com.codenjoy.dojo.icancode.analize.elements.domain.EMove;
import com.codenjoy.dojo.icancode.analize.elements.domain.EPriority;
import org.apache.log4j.Logger;

public class ElementNotDetected extends DomainElement implements EJump {
    private final Logger log = Logger.getLogger(ElementNotDetected.class);

    public ElementNotDetected(int x, int y) {
        super(x, y);this.setPriority(EPriority.LOW);
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
