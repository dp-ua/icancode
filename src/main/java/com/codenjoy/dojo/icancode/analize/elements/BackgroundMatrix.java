package com.codenjoy.dojo.icancode.analize.elements;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;


public class BackgroundMatrix {
    private final Logger log = Logger.getLogger(BackgroundMatrix.class);

    @Getter
    private int[][] matrix;

    @Setter
    @Getter
    private Pair<Integer, Integer> center;

    public BackgroundMatrix() {
    }

    public void setMatrix(int[][] matrix) {
        if (matrix.length % 2 == 0) throw new IllegalArgumentException("Matrix length must be not odd");
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != matrix.length)
                throw new IllegalArgumentException("Wrong matrix size. Line + " + i + " has wrong length");
        }
        this.matrix = matrix;
    }

}
