package io.wende.aoc.sixteen;

import io.wende.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Three extends Puzzle {

    public static void main(final String... args) {
        new Three().run();
    }

    private List<List<Integer>> reorder(List<List<Integer>> in) {
        List<List<Integer>> out = new ArrayList<>();
        for(int i = 0; i < in.size(); i += 3) {
            out.add(List.of(new Integer[]{in.get(i).get(0), in.get(i + 1).get(0), in.get(i + 2).get(0)}));
            out.add(List.of(new Integer[]{in.get(i).get(1), in.get(i + 1).get(1), in.get(i + 2).get(1)}));
            out.add(List.of(new Integer[]{in.get(i).get(2), in.get(i + 1).get(2), in.get(i + 2).get(2)}));
        }
        return out;
    }



    private void run() {
        List<List<Integer>> in = createInput();
        out("Input {}", in);
        out("Valid triangles {}", in.stream().filter(triangle -> {
            var sorted = triangle.stream().sorted().toList();return sorted.get(0) + sorted.get(1) > sorted.get(2);}).count());
        out("Input {}", this.reorder(in));
        out("Valid vertical triangles {}", this.reorder(in).stream().filter(triangle -> {
            var sorted = triangle.stream().sorted().toList();return sorted.get(0) + sorted.get(1) > sorted.get(2);}).count());
    }

    private List<List<Integer>> createInput() {
        return this.getInputLines().stream()
            .map(line -> Arrays.stream(line.trim().split("\\s+")).toList().stream()
                .map(Integer::parseInt).toList()).toList();
    }
}
