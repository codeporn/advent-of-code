package io.wende.aoc.twentytwo;

import io.wende.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Four extends Puzzle {

  public static void main(final String... args) {
    new Four().run();
  }

  protected void run() {
    int fullyContainedPairs = 0;
    int overlappingPairs = 0;
    List<Pair> pairs = this.generateInput(this.getInputLines());
    for(Pair pair : pairs) {
      if((pair.startA - pair.startB) * (pair.endA - pair.endB) < 1) {
        fullyContainedPairs++;
      }
      if((pair.startA - pair.endB) * (pair.endA - pair.startB) < 1) {
        overlappingPairs++;
      }
    }
    out("{} pairs fully contain sample or the other", fullyContainedPairs);
    out("{} pairs overlap", overlappingPairs);
  }

  private List<Pair> generateInput(final List<String> inputLines) {
    Pattern p = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");
    List<Pair> pairs = new ArrayList<>();
    for(String line : inputLines) {
      Matcher m = p.matcher(line);
      if(m.find()) {
        Pair i = new Pair(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
        pairs.add(i);
      }
    }
    return pairs;
  }

  private record Pair(int startA, int endA, int startB, int endB) {}
}
