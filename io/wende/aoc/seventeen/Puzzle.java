package io.wende.aoc.seventeen;

import io.wende.aoc.Util;

public abstract class Puzzle {

  protected String input;

  protected Puzzle() {
    System.out.println(this.getClass().getSimpleName());
    this.input = Util.loadFileContent(this.getClass().getSimpleName().toLowerCase());
  }

  protected void out(String message, final Object... args) {
    for(Object arg : args) {
      message = message.replaceFirst("\\{\\}", String.valueOf(arg));
    }
    System.out.println(message);
  }
}
