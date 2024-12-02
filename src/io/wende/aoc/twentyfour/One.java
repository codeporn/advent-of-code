package io.wende.aoc.twentyfour;

import io.wende.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class One extends Puzzle {

  private List<Long> left = new ArrayList<>();
  private List<Long> right = new ArrayList<>();

  public static void main(final String... args) {
    new One().run();
  }

  private void run() {
    Long sum = 0L;
    Long similar = 0L;
    this.prepareInput();
    Collections.sort(left);
    Collections.sort(right);
    for (int i = 0; i < left.size(); i++) {
      int leftValCount = Collections.frequency(right, left.get(i));
      sum += Math.abs(left.get(i) - right.get(i));
      similar += left.get(i) * leftValCount;
    }
    out("Sum: {} ", sum);
    out("Similarity Score: {} ", similar);
  }

  private void prepareInput() {
    this.getInputLines().forEach(line -> {
      left.add(Long.parseLong(line.split("   ")[0]));
      right.add(Long.parseLong(line.split("   ")[1]));
    });
  }
}
