package io.wende.aoc.twentytwo;

import io.wende.aoc.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Three extends Puzzle {

  public static void main(final String... args) {
    new Three().run();
  }

  private void run() {
    int duplicatePriorities = 0;
    int badgePriorities = 0;
    List<String> rucksacks = this.getInputLines();

    for(int i = 0; i < rucksacks.size(); i++) {
      List<Integer> frontCompartment = this.calculatePriority(rucksacks.get(i).substring(0, rucksacks.get(i).length() / 2).toCharArray());
      List<Integer> rearCompartment = this.calculatePriority(rucksacks.get(i).substring(rucksacks.get(i).length() / 2).toCharArray());

      frontCompartment.retainAll(rearCompartment);
      duplicatePriorities += frontCompartment.get(0);

      if(i < rucksacks.size() - 2 && i % 3 == 0) {
        List<Integer> one = this.calculatePriority(rucksacks.get(i).toCharArray());
        List<Integer> two = this.calculatePriority(rucksacks.get(i + 1).toCharArray());
        List<Integer> three = this.calculatePriority(rucksacks.get(i + 2).toCharArray());

        one.retainAll(two);
        one.retainAll(three);
        badgePriorities += one.get(0);
      }
    }
    out("Sum of duplicate item priorities is {}", duplicatePriorities);
    out("Sum of badge priorities is {}", badgePriorities);
  }

  private List<Integer> calculatePriority(final char[] characters) {
    List<Integer> ret = new ArrayList<>();
    for (char character : characters) {
      ret.add(this.calculatePriority(character));
    }
    return ret;
  }

  private int calculatePriority(final char character) {
    return character - (character > 96 ? 96 : 38);
  }
}
