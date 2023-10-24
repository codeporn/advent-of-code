package io.wende.aoc.twentytwo;

import io.wende.aoc.Puzzle;

import java.util.Arrays;

public class Two extends Puzzle {

    public static void main(final String... args) {
        new Two().run();
    }

    private void run() {
        out("Final score is {}",
                Arrays.stream(input.split("\n"))
                        .map(it -> new Game(it.toCharArray()[0], it.toCharArray()[2]).score())
                        .reduce(Integer::sum).get());

        out("Final score of a staged game is {}",
                Arrays.stream(input.split("\n"))
                        .map(it -> new StagedGame(it.toCharArray()[0], it.toCharArray()[2]).score())
                        .reduce(Integer::sum).get());
    }


    private record Game(char opponent, char me) {
        int score() {
            int o = opponent - 64;
            int m = me - 87;
            int result = (o - m + 3) % 3;
            return m + (result == 2 ? 6 : result == 0 ? 3 : 0);
        }
    }

    private record StagedGame(char opponent, char state) {
        int score() {
            int o = opponent - 64;
            int s = state - 87;
            int me = (o + s - 2) % 3;
            return (me == 0 ? 3 : me) + (s - 1) * 3;
        }
    }
}
