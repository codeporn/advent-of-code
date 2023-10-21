package io.wende.aoc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
    System.out.println("Content: \n" + Util.loadFileContent("tewst"));
  }
}
