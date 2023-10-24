package io.wende.aoc.twentytwo;

import io.wende.aoc.Puzzle;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class One extends Puzzle {

  public static void main(final String... args) {
    new One().run();
  }

  private void run() {
    List<Elf> elfs = this.prepare();
    out("The elf with maximum calories is carrying {}", elfs.stream().max(Comparator.comparing(it->it.sumCalories())).get().sumCalories());

    elfs.stream().sorted(Comparator.comparing(it->it.sumCalories()*-1)).forEach(it -> out("{}", it.sumCalories()));
    out("Sum of calories carried by the elfs with top 3 backpacks {}", elfs.stream().sorted(Comparator.comparing(it->it.sumCalories()*-1)).toList().subList(0, 3).stream().map(Elf::sumCalories).reduce(Integer::sum).get());
  }

  private List<Elf> prepare() {
    return Arrays.stream(this.input.split("\r\n\r\n"))
        .map(calList ->
            new Elf(Arrays.stream(calList.split("\r\n"))
                .map(Integer::parseInt)
                .toList())
        ).toList();
  }
  private record Elf(List<Integer> calories){
    int sumCalories() {
      return calories.stream().reduce(Integer::sum).get();
    }
  }
}
