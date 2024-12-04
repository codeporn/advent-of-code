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
    //System.out.println("Content: \n" + Util.loadFileContent("test"));

    List<String> list = new ArrayList<>();
    list.add("A");
    list.add("B");
    list.add("C");

    List<String> list2 = new ArrayList<>();
    list2.add("D");
    list2.add("E");
    list2.add("F");

    List<String> list3 = new ArrayList<>();
    list3.add("G");
    list3.add("H");
    list3.add("I");

    List<List<String>> list4 = new ArrayList<>();
    list4.add(list);
    list4.add(list2);
    list4.add(list3);

    System.out.println("Content: \n" + rotateMatrix(list4));

  }

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
