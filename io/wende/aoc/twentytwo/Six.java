package io.wende.aoc.twentytwo;

import io.wende.aoc.Puzzle;

import java.util.List;

public class Six extends Puzzle {

  public static void main(final String... args) {
    new Six().run();
  }

  private void run() {
    out("Found first start-of-packet marker with marker size {} at index {}", 4, this.getMarkerIndex(4));
    out("Found first start-of-packet marker with marker size {} at index {}", 14, this.getMarkerIndex(14));
  }

  private int getMarkerIndex(int makerSize) {
    for(int i = 0; i < input.length() - makerSize - 1; i++) {
      String sub = input.substring(i, i + makerSize);
      int size = List.of(sub.split("")).stream().distinct().toList().size();
      if(size > makerSize - 1) {
        return i + makerSize;
      }
    }
    return 0;
  }
}
