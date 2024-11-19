package io.wende.aoc.common;

import java.util.Arrays;
import java.util.List;

public abstract class Puzzle {

  protected String input;

  protected Puzzle() {
    this.input = Util.loadFileContent(
        this.getClass().getPackageName().substring(this.getClass().getPackageName().lastIndexOf(".") + 1)
            + "/"
            + this.getClass().getSimpleName().toLowerCase());
  }

  protected void out(String message, final Object... args) {
    for(int i = 0; i < args.length; i++) {
      message = message.replaceFirst("\\{\\}", String.valueOf(args[i]));
    }
    System.out.println(message);
  }

  protected List<String> getInputLines() {
    return Arrays.stream(this.input.split(System.lineSeparator())).toList();
  }
}
