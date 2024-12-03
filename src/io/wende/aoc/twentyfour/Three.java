package io.wende.aoc.twentyfour;

import io.wende.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Three extends Puzzle {

  public static void main(final String... args) {
    new Three().run();
  }

  private void run() {
    out("Result {}", this.findInstuctions().stream().mapToLong(this::multiply).sum());
    out("Result {}", this.findExtendedInstructions().stream().mapToLong(this::multiply).sum());
  }

  private List<String> findInstuctions() {
    List<String> allMatches = new ArrayList<>();
    Matcher m = Pattern.compile("mul\\(\\d+,\\d+\\)")
        .matcher(this.input);
    while (m.find()) {
      allMatches.add(m.group());
    }
    return allMatches;
  }

  private List<String> findExtendedInstructions() {
    List<String> allMatches = new ArrayList<>();
    Matcher m = Pattern.compile("(do\\(\\))|(don\\'t\\(\\))|(mul\\(\\d+,\\d+\\))")
        .matcher(this.input);

    while (m.find()) {
      allMatches.add(m.group());
    }
    boolean enabled = true;
    List<String> validMatches = new ArrayList<>();
    for (int i = 0; i < allMatches.size(); i++) {
      if(allMatches.get(i).startsWith("mul(")) {
        if (enabled) {
          validMatches.add(allMatches.get(i));
        }
      } else if (allMatches.get(i).equals("do()")) {
        enabled = true;
      }
      else if(allMatches.get(i).equals("don't()")){
        enabled = false;
      }
    }
    return validMatches;
  }

  private long multiply(final String in) {
    return Long.parseLong(in.substring(in.indexOf("(") + 1, in.indexOf(","))) *
        Long.parseLong(in.substring(in.indexOf(",") + 1, in.indexOf(")")));
  }
}
