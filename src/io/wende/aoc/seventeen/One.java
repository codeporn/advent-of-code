package io.wende.aoc.seventeen;

import io.wende.aoc.common.Puzzle;

public class One extends Puzzle {

    public static void main(final String... args) {
        new One().run();
    }

    private void run() {
        calculate(1);
        calculate(input.length() / 2);
    }

    private void calculate(final int steps) {
        int[] inputArr = this.createInput();

        int result = 0;
        for (int i = 0; i < inputArr.length; i++) {
            if (inputArr[i] == inputArr[getIndexAhead(inputArr.length, i, steps)]) {
                result += inputArr[i];
            }
        }
        this.out("Solution for {} steps ahead is {}", steps, result);
    }

    private int[] createInput() {
        int[] arr = new int[input.length()];
        Character[] charObjectArray =
                input.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        for (int i = charObjectArray.length; i-- > 0; ) {
            arr[i] = Integer.parseInt(String.valueOf(charObjectArray[i]));
        }
        return arr;
    }

    private int getIndexAhead(final int size, final int position, final int steps) {
        int ret = position + steps;

        return ret >= size ? ret - size : ret;
    }
}
