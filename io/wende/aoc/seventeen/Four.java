package io.wende.aoc.seventeen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Four extends Puzzle {

  public static void main(final String... args) {
    new Four().run();
  }

  private void run() {
    List<List<String>> phrases = Arrays.stream(input.split("\r\n")).map(line -> Arrays.stream(line.split(" ")).collect(Collectors.toList())).toList();
    int phraseCount = phrases.size();

    for(List<String> phrase : phrases) {
      if(phrase.size() != phrase.stream().distinct().count()) {
        phraseCount--;
      }
    }
    this.out("Found {} valid passphrases", phraseCount);

    phraseCount = 0;
    for(List<String> phrase : phrases) {
      List<String> sorted = new ArrayList<>();
      for(String word : phrase) {
        char[] c = word.toCharArray();
        Arrays.sort(c);
        sorted.add(new String(c));
      }
      if(sorted.size() == sorted.stream().distinct().count()) {
        phraseCount++;
      }
    }
    this.out("Found {} valid passphrases", phraseCount);
  }
}
