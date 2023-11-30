package io.wende.aoc.twentytwo;

import io.wende.aoc.common.Puzzle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Five extends Puzzle {

  // width height
  private LinkedList<LinkedList<String>> stacks= new LinkedList<>();

  public static void main(final String... args) {
    new Five().run();
  }

  protected void run() {
    List<String> inputBlocks = Arrays.stream(this.input.split(System.lineSeparator() + System.lineSeparator())).toList();
    List<Move> moves = this.getMoves(inputBlocks.get(1));
    this.stacks = this.getStacks(inputBlocks.get(0));

    for(Move move : moves) {
      this.move(move);
    }
    out("Top crates with CrateMover 9000 are {}", this.stacks.stream().map(LinkedList::getLast).collect(Collectors.joining("")));

    this.stacks.clear();
    this.stacks = this.getStacks(inputBlocks.get(0));

    for(Move move : moves) {
      this.multiMove(move);
    }
    out("Top crates with CrateMover 9001 are {}", this.stacks.stream().map(LinkedList::getLast).collect(Collectors.joining("")));
  }

  private void move(final Move move) {
    //out("Moving {} crate{} from stack {} to stack {}", move.count, move.count > 1 ? "s" : "", move.from + 1, move.to + 1);
    for(int i = move.count; i-- > 0;) {
      stacks.get(move.to).add(stacks.get(move.from).removeLast());
    }
    //this.printStacks();
  }

  private void multiMove(final Move move) {
    //out("Moving {} crate{} from stack {} to stack {}", move.count, move.count > 1 ? "s" : "", move.from + 1, move.to + 1);
    List<String> subStack = this.stacks.get(move.from).subList(this.stacks.get(move.from).size() - move.count, this.stacks.get(move.from).size());
    this.stacks.get(move.to).addAll(subStack);
    subStack.clear();
    //this.printStacks();
  }



  private LinkedList<LinkedList<String>> getStacks(final String stackInput) {
    List<String> stackLines = Arrays.stream(stackInput.split(System.lineSeparator())).toList();
    //out("Stack input\n");
    //stackLines.forEach(it-> out("{}", it));

    //out("\nBuilding {} stacks", stackLines.get(stackLines.size() - 1).replace(" ", "").length());
    for(int i = stackLines.get(stackLines.size() - 1).replace(" ", "").length(); i-- > 0; ) {
      stacks.add(new LinkedList<>());
    }

    for(int i = stackLines.size() - 1; i-- > 0; ) {
      String stackLine = stackLines.get(i);
      //out("\nBuilding up stacks for line '{}'", stackLine);

      for(int crate = 1, width = 0; crate < stackLine.length(); crate += 4, width++) {

        if(32 != (int)stackLine.charAt(crate)) {
          //out("Adding crate [{}](char {}) to stack {}", String.valueOf(stackLine.charAt(crate)), (int)stackLine.charAt(crate), width);
          stacks.get(width).add(String.valueOf(stackLine.charAt(crate)));
        }
      }
    }
    out("\nInital stacks:");
    this.printStacks();

    return stacks;
  }

  private List<Move> getMoves(final String movementInput) {
    Pattern p = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)");
    return Arrays.stream(movementInput.split(System.lineSeparator()))
        .map(it -> {
          Matcher m = p.matcher(it);
          if(m.find()) {
            return new Move(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)) - 1, Integer.parseInt(m.group(3)) - 1);
          }
          return null;
    }).toList();
  }

  private void printStacks() {
    System.out.print("\n");
    for(int i = 0; i < stacks.size(); i++) {
      System.out.print((i + 1) + " | ");
      for(String crate : stacks.get(i)) {
        System.out.print("[" + crate + "]  ");
      }
      System.out.print("\n");
    }
    System.out.print("\n");

  }

  private record Move(int count, int from, int to) {}
}
