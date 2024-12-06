package io.wende.aoc.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Util {

  public static String loadFileContent(final String filename) {
    try (InputStream in = new FileInputStream("data/" + filename)) {
      return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    } catch(Exception e) {
      System.err.println("Unable to pull file from [" + filename + "]");
      e.printStackTrace();
    }
    return null;
  }

  public static void main(final String args[]) {
    System.out.println("Hello, Util!");
  }

  /**
   * Rotates a matix structure 90° counter-clockwise, whereas rows are represented by outer list, co.
   * Example:
   * <pre>
   *   1 2 3     3 6 9
   *   4 5 6  >  2 5 8
   *   7 8 9     1 4 7
   * </pre>
   * @param matrix
   * @return
   */
  public static List<List<String>> rotateMatrix(final List<List<String>> matrix) {
    final List<List<String>> result = new ArrayList<>();
    for(int i = matrix.get(0).size(); i-->0;) {
      List<String> newRow = new ArrayList<>();
      for(List<String> row : matrix) {
        newRow.add(row.get(i));
      }
      result.add(newRow);
    }
    return result;
  }

  public static void printMatrix(final List<List<String>> matrix) {
    for(final List<String> row : matrix) {
      System.out.println(row);
    }
  }
}
