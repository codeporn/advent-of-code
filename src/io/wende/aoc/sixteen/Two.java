package io.wende.aoc.sixteen;

import io.wende.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Position {
    int x = 0;
    int y = 0;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(final String dir) {
        switch (dir) {
            case "U": y = (y < 1 ? y + 1: y); break;
            case "D": y = (y > -1 ? y - 1 : y); break;
            case "R": x = (x < 1 ? x + 1: x); break;
            case "L": x = (x > -1 ? x - 1 : x); break;
        }
    }

    public int code() {
        switch (x) {
            case -1: return y == 1 ? 1 : y == 0 ? 4 : 7;
            case 0: return y == 1 ? 2 : y == 0 ? 5 : 8;
            case 1: return y == 1 ? 3 : y == 0 ? 6 : 9;
        }
        return 0;
    }
}

class PositionBathroom {
    int x = 0;
    int y = 0;

    public PositionBathroom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(final String dir) {
        boolean moveAllowed =
            (x == 2 && dir.equals("L")) || (x == -2 && dir.equals("R")) ||
            (y == 2 && dir.equals("D")) || (y == -2 && dir.equals("U")) ||
            (x == 1 && dir.equals("L")) || (x == -1 && dir.equals("R")) ||
            (y == 1 && dir.equals("D")) || (y == -1 && dir.equals("U")) ||
            Math.abs(x) + Math.abs(y) <= 1;

        switch (dir) {
            case "U": y = (moveAllowed ? y + 1: y); break;
            case "D": y = (moveAllowed ? y - 1 : y); break;
            case "R": x = (moveAllowed ? x + 1: x); break;
            case "L": x = (moveAllowed ? x - 1 : x); break;
        }
    }

    public String code() {
        switch (x) {
            case -2: return "5";
            case -1: return y == 1 ? "2" : y == 0 ? "6" : "A";
            case 0: return y == 2 ? "1" : y == 1 ? "3" : y == 0 ? "7" : y == -1 ? "B" : "D";
            case 1: return y == 1 ? "4" : y == 0 ? "8" : "C";
            case 2: return "9";
        }
        return "0";
    }
    public String position() {
        return x + ":" + y;
    }
}

public class Two extends Puzzle {

    public static void main(final String... args) {
        new Two().run();
    }

    private String calculate() {
        String code = "";
        Position p = new Position(0, 0);
        for(String line : getInputLines()) {
            for(String dir : line.split("")) {
                p.move(dir);
            }
            code += p.code();
        }
        return code;
    }

    private String calculateBathroom() {
        String code = "";
        PositionBathroom p = new PositionBathroom(-2, 0);
        for(String line : getInputLines()) {
            for(String dir : line.split("")) {
                p.move(dir);
            }
            code += p.code();
        }
        return code;
    }

    private void run() {
        out("Code {}", calculate());
        out("Bathroom code {}", calculateBathroom());
    }
}
