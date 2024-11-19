package io.wende.aoc.sixteen;

import io.wende.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

enum Direction {
    N, E, S, W;

    public Direction get(String dir) {
        boolean left = dir.equalsIgnoreCase("l");
        switch (this) {
            case N:
                return left ? W : E;
            case E:
                return left ? N : S;
            case S:
                return left ? E : W;
            case W:
                return left ? S : N;
        }
        return null;
    }
}

public class One extends Puzzle {

    public static void main(final String... args) {
        new One().run();
    }

    private Integer calculate() {
        Integer[] result = {0, 0};
        Direction currentDirection = Direction.N;
        for(String instruction : this.createInput()) {
            currentDirection = currentDirection.get(instruction.substring(0, 1));
            Integer steps = Integer.valueOf(instruction.substring(1));
            switch (currentDirection) {
                case E: result[0] = result[0] + steps;break;
                case W: result[0] = result[0] - steps;break;
                case N: result[1] = result[1] + steps;break;
                case S: result[1] = result[1] - steps;break;
            }
        }
        return Math.abs(result[0]) + Math.abs(result[1]);
    }

    private Integer calculateDistinct() {
        Direction currentDirection = Direction.N;
        Integer[] result = {0, 0};
        List<String> log = new ArrayList<>();
        log.add(result[0] + "|" + result[1]);

        for(String instruction : this.createInput()) {
            currentDirection = currentDirection.get(instruction.substring(0, 1));
            Integer steps = Integer.valueOf(instruction.substring(1));

            for(int i = steps; i-->0;) {
                switch (currentDirection) {
                    case E: result[0] = result[0] + 1; break;
                    case W: result[0] = result[0] - 1; break;
                    case N: result[1] = result[1] + 1; break;
                    case S: result[1] = result[1] - 1; break;
                }
                if(log.contains(result[0] + "|" + result[1])) {
                    return Math.abs(result[0]) + Math.abs(result[1]);
                }
                log.add(result[0] + "|" + result[1]);
            }
        }
        return Math.abs(result[0]) + Math.abs(result[1]);
    }

    private void run() {
        out("Blocks {}", calculate());
        out("Distinct Blocks {}", calculateDistinct());
    }

    private List<String> createInput() {
        out("", input);
        return Arrays.stream(input.split(", ")).collect(Collectors.toUnmodifiableList());
    }
}
