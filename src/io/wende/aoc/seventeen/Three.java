package io.wende.aoc.seventeen;

import io.wende.aoc.common.Puzzle;

public class Three extends Puzzle {

  public Three() {
    super();
    //this.input = "361527";
    this.input = "14";
  }

  public static void main(final String... args) {
    new Three().run();
  }

  private void run() {
    int destination = Integer.parseInt(input);
    int steps = 0;
    for(int i = 1; i * i < destination; i++) {
      out("Step {}", i*i);
      steps = i;
    }



    out("Solution is {}", steps);
  }
}

// 37  36  35  34  33  32  31
// 38  17  16  15  14  13  30
// 39  18   5   4   3  12  29
// 40  19   6   1   2  11  28
// 41  20   7   8   9  10  27
// 42  21  22  23  24  25  26
// 43  44  45  46  47  48  49