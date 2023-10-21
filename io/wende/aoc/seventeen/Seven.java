package io.wende.aoc.seventeen;

import java.util.Arrays;
import java.util.List;

public class Seven extends Puzzle {

  public static void main(final String... args) {
    new Seven().run();
  }

  private void run() {
    List<String> remaining = Arrays.stream(input.split("\n"))
        .filter(line -> line.contains("->")).toList();

    for(String line : remaining) {
      boolean found = false;
      String name = line.substring(0, line.indexOf(" "));
      for(String check : remaining) {
        if(check.substring(check.indexOf(" ")).contains(name)) {
          found = true;
        }
      }
      if(!found) {
        this.out("Bottom program is called {}", name);
        break;
      }
    }
  }
}
