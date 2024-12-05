package io.wende.aoc.twentyfour;

import io.wende.aoc.common.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Five extends Puzzle {

  private List<Tuple> rules = new ArrayList<>();
  private List<String> updates = new ArrayList<>();

  private List<String> incorrectUpdates = new ArrayList<>();
  private List<String> correctedUpdates = new ArrayList<>();

  public static void main(final String... args) {
    new Five().run();
  }

  private void run() {
    this.prepareInput();
    out("Result of valid updates {}", this.validateUpdates(updates));
    validateIncorrectUpdates();
    out("Result of corrected updates {}", this.validateUpdates(correctedUpdates));
  }

  private void validateIncorrectUpdates() {
    this.correctedUpdates.clear();
    for(String update : incorrectUpdates) {
      boolean violationFound;
      do {
        violationFound = false;
        for (Tuple rule : rules) {
          if (!validate(update, rule)) {
            violationFound = true;
            String[] aSplit = update.split("" + rule.getA());
            String[] bSplit = aSplit[0].split("" + rule.getB());
            update = bSplit[0] + rule.getA() + bSplit[1] + rule.getB() + aSplit[1];
          }
        }
      } while(violationFound);
      this.correctedUpdates.add(update);
    }
    this.incorrectUpdates.clear();
  }

  private long validateUpdates(List<String> updateList) {
    this.incorrectUpdates.clear();
    long result = 0;
    for(String update : updateList) {
      boolean validUpdate = true;
      for (Tuple rule : rules) {
        if(!validate(update, rule)) {
          incorrectUpdates.add(update);
          validUpdate = false;
          break;
        }
      }
      if(validUpdate) {
        String[] pages = update.split(",");
        result += Long.parseLong(pages[pages.length / 2]);
      }
    }
    return result;
  }

  private boolean validate(final String update, final Tuple rule) {
    if(update.contains("," + rule.getA() + ",") && update.contains("," + rule.getB() + ",") &&
        update.indexOf("," + rule.getA() + ",") > update.indexOf("," + rule.getB() + ",")) {
      return false;
    }
    return true;
  }

  private void prepareInput() {
    String[] instructions = this.input.split(System.lineSeparator() + System.lineSeparator());
    rules = Arrays.stream(instructions[0].split(System.lineSeparator())).map(Tuple::new).toList();
    updates = Arrays.stream(instructions[1].split(System.lineSeparator())).map(it -> "," + it + ",").toList();
  }
}

class Tuple {
  private int a;
  private int b;

  public Tuple(String notation) {
    this.a = Integer.parseInt(notation.split("\\|")[0]);
    this.b = Integer.parseInt(notation.split("\\|")[1]);
  }
  public Tuple(int a, int b) {
    this.a = a;
    this.b = b;
  }

  public int getA() {
    return a;
  }

  public int getB() {
    return b;
  }

  @Override
  public String toString() {
    return "[" + a + "|" + b + "]";
  }
}