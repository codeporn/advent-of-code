package io.wende.aoc.twentyfour;

import io.wende.aoc.common.Puzzle;
import io.wende.aoc.common.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Four extends Puzzle {

  private List<List<String>> inputMatrix;

  public static void main(final String... args) {
    new Four().run();
  }

  private void run() {
    this.inputMatrix = prepareInput();
    int solution = 0;
    int solutionX = 0;

    for(int i = 4; i-->0;) {
      solution += this.findHorizontals("XMAS");
      solution += this.findDiagonals("XMAS");
      solutionX += this.findX("MAS");
      this.inputMatrix = Util.rotateMatrix(inputMatrix);
    }

    out("XMAS count {}", solution);
    out("X-MAS count {}", solutionX);
  }

  private int findX(final String search) {
    int count = 0;
    for(int i = 0; i <= inputMatrix.size() - search.length(); i++) {
      for(int j = 0; j <= inputMatrix.get(i).size() - search.length(); j++) {
        if(inputMatrix.get(i).get(j).equals(String.valueOf(search.charAt(0))) &&
            inputMatrix.get(i).get(j + 1 + search.length() / 2).equals(String.valueOf(search.charAt(0))) &&
            inputMatrix.get(i + search.length() - 1).get(j).equals(String.valueOf(search.charAt(search.length() - 1)))) {
          boolean found = true;
          for(int k = 1; k < search.length() && found; k++) {
            if(!inputMatrix.get(i + k).get(j + k).equals(String.valueOf(search.charAt(k)))) {
              found = false;
            }
          }
          if(found) {
            count++;
          }
        }
      }
    }
    return count;
  }

  private int findDiagonals(final String search) {
    int count = 0;
    for(int i = 0; i <= inputMatrix.size() - search.length(); i++) {
      for(int j = 0; j <= inputMatrix.get(i).size() - search.length(); j++) {
        if(inputMatrix.get(i).get(j).equals(String.valueOf(search.charAt(0)))) {
          boolean found = true;
          for(int k = 1; k < search.length() && found; k++) {
            if(!inputMatrix.get(i + k).get(j + k).equals(String.valueOf(search.charAt(k)))) {
              found = false;
            }
          }
          if(found) {
            count++;
          }
        }
      }
    }
    return count;
  }

  private int findHorizontals(final String search) {
    int count = 0;
    for(List<String> matrixLine : inputMatrix) {
      count += Pattern.compile(search).matcher(String.join("", matrixLine)).results().count();
    }
    return count;
  }

  private List<List<String>> prepareInput() {
    return this.getInputLines().stream().map(it-> List.of(it.split(""))).toList();
  }
}
