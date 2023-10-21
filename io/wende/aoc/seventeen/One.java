package io.wende.aoc.seventeen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class One extends Puzzle{

  public static void main(final String... args) {
    new One().run();
  }

  private void run() {
    Pattern p = Pattern.compile("(\\d)(?=\\1)");
    Matcher m = p.matcher(input);
    int i = m.results().map(match -> Integer.valueOf(match.group(1))).reduce(0, Integer::sum);

    if(input.charAt(0) == input.charAt(input.length() - 1)) {
      i += Integer.parseInt(input.substring(0, 1));
    }

    this.out("Solution is {}", i);
  }
}
