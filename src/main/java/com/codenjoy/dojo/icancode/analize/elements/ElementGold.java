package com.codenjoy.dojo.icancode.analize.elements;

import com.codenjoy.dojo.icancode.analize.elements.domain.DomainElement;
import com.codenjoy.dojo.icancode.analize.elements.domain.DomainWalk;
import org.apache.log4j.Logger;

public class ElementGold extends DomainElement implements DomainWalk {
    private final Logger log = Logger.getLogger(ElementGold.class);

    public ElementGold(int x, int y) {
        super(x, y);
    }

    @Override
    public BackgroundMatrix getBackgroundMatrix() {
        BackgroundMatrix backgroundMatrix = new BackgroundMatrix();
        int [][] matrix = new int[1][1];
        matrix[0][0]=5;
        backgroundMatrix.setMatrix(matrix);

        return backgroundMatrix;
    }
}
