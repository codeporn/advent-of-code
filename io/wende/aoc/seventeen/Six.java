package io.wende.aoc.seventeen;

import io.wende.aoc.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Six extends Puzzle {

  public static void main(final String... args) {
    new Six().run();
  }

  private void run() {

    List<String> history = new ArrayList<>();
    List<Integer> inputList = Arrays.stream(input.split("\t")).map(Integer::parseInt).toList();

    do {
      history.add(inputList.stream().map(it->it.toString()).collect(Collectors.joining(",")));
      inputList = this.redistribute(inputList);
    }while(!history.contains(inputList.stream().map(it->it.toString()).collect(Collectors.joining(","))));

    out("It took {} iterations to find recurring state.", history.size());

    history.clear();

    do {
      history.add(inputList.stream().map(it->it.toString()).collect(Collectors.joining(",")));
      inputList = this.redistribute(inputList);
    }while(!history.contains(inputList.stream().map(it->it.toString()).collect(Collectors.joining(","))));

    out("It took {} iterations to find recurring state again.", history.size());
  }

  private List<Integer> redistribute(final List<Integer> input) {

    int index = 0, max = 0;
    for(int i = 0; i < input.size(); i++ ) {
      if(input.get(i).intValue() > max) {
        index = i;
        max = input.get(i).intValue();
      }
    }

    int[] ret = new int[input.size()];
    for(int i = input.size(); i-- > 0; ) {
      if(i == index) {
        ret[i] = 0;
      }
      else {
        ret[i] = input.get(i);
      }
    }

    for(int i = (index +1 == input.size() ? 0 : index + 1); max > 0; max--, i = i + 1 == input.size() ? 0 : i + 1) {
      ret[i]++;
    }

    return Arrays.stream(ret).boxed().toList();
  }
}
