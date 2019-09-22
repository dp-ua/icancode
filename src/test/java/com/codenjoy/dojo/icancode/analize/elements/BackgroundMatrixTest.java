package com.codenjoy.dojo.icancode.analize.elements;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BackgroundMatrixTest {
    BackgroundMatrix backgroundMatrix;

    @Before
    public void setUp() {
        backgroundMatrix = new BackgroundMatrix();
    }

    @Test
    public void normalSetMatrix() {
        int[][] matrix = new int[][]
                {
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
        backgroundMatrix.setMatrix(matrix);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorLegthInRow() {
        int[][] matrix = new int[][]
                {
                        {0, 0, 0},
                        {0, 0},
                        {0, 0, 0}
                };
        backgroundMatrix.setMatrix(matrix);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorLegthMustBeOdd() {
        int[][] matrix = new int[][]
                {
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                };
        backgroundMatrix.setMatrix(matrix);
    }

}