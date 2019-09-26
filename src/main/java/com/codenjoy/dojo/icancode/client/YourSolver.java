package com.codenjoy.dojo.icancode.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the ElementFree Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.icancode.analize.Analize;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Your AI
 */
public class YourSolver extends AbstractSolver {
    private Analize analize = new Analize();


    /**
     * @param dice DIP (SOLID) for Random dependency in your Solver realization
     */
    public YourSolver(Dice dice) {
        super(dice);
    }

    /**
     * @param board use it for find elements on board
     * @return what hero should do in this tick (for this board)
     */
    @Override
    public Command whatToDo(Board board) {
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Старт: " + getTimeString(start));
        long startms = System.currentTimeMillis();
        if (!board.isMeAlive()) {
            System.out.println("Умер. Так бывает :)");
            return Command.doNothing();
        }
        Command command = analize.getNextMove(board);
        long endms = System.currentTimeMillis();
        System.out.println("Время принятия решения: " + (endms - startms) + "ms");
        return command;
    }

    private String getTimeString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        return localDateTime.format(formatter);
    }

    /**
     * Run this method for connect to Server
     */
    public static void main(String[] args) {

        connectClient(
                // paste here board page url from browser after registration
                "http://dojorena.io:80/codenjoy-contest/board/player/21j3x97ybk34032hzoii?code=8736559310819449456",
                // and solver here
                new YourSolver(new RandomDice()));
    }
}