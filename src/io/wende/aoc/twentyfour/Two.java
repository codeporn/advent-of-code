package io.wende.aoc.twentyfour;

import io.wende.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Two extends Puzzle {

  private List<List<Integer>> reports;
  public static void main(final String... args) {
    new Two().run();
  }

  private void run() {
    this.prepareInput();
    out("Safe reports {}", reports.stream().mapToInt(this::validate).sum());
    out("Safe reports with tolerance {}", reports.stream().mapToInt(this::validateWithTolerance).sum());
  }

  private void prepareInput() {
    reports = this.getInputLines().stream()
        .map(line -> Arrays.stream(line.split(" "))
            .map(Integer::valueOf).toList())
        .toList();
  }

  private int validateWithTolerance(List<Integer> input) {
    if(this.validate(input) == 1){
      return 1;
    }
    else {
      for(int i = 0; i < input.size(); i++){
        List<Integer> clone = new ArrayList<>(input);
        clone.remove(i);
        if(this.validate(clone) == 1) {
          return 1;
        }
      }
    }
    return 0;
  }

  private int validate(List<Integer> input) {
    boolean asc = input.get(0) < input.get(1);
    for(int i = 0; i < input.size() - 1; i++) {
      int diff = input.get(i) - input.get(i + 1);
      if(
          (asc && (diff > -1 || diff < -3)) ||
              (!asc && (diff < 1 || diff > 3))
      ) {
        return 0;
      }
    }
    return 1;
  }
}
