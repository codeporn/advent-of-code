package io.wende.aoc.seventeen;

import java.util.Arrays;
import java.util.List;

public class Two extends Puzzle {

  public static void main(final String... args) {
    new Two().run();
  }

  private void run() {
    int checksum = 0;
    int[][] table = this.prepareInput();

    for(int[] line : table) {
      checksum += line[line.length - 1] - line[0];
    }
    out("Checksum is {}", checksum); // 34581

    checksum = 0;
    for(int[] line : table) {
      for(int i = 0; i < line.length; i++) {
        for(int j = line.length; j-- >0; ) {
          if(line[j] != line[i] && line[j] % line[i] == 0) {
            out("Division {}/{} Result {}",line[j], line[i], line[j] / line[i] );
            checksum += line[j] / line[i];
            break;
          }
        }
      }
    }
    out("Checksum is {}", checksum);
  }

  private int[][] prepareInput() {
    List<String> flatList = Arrays.stream(input.split("\r\n")).toList();
    int[][] inputArray = new int[flatList.size()][flatList.get(0).split("\t").length];

    for(int i = flatList.size(); i-- >0; ) {
      String[] numbers = flatList.get(i).split("\t");
      for(int j = numbers.length; j-- > 0; ) {
        inputArray[i][j] = Integer.parseInt(numbers[j]);
      }
    }

    for(int i = inputArray.length; i-- > 0; ) {
      Arrays.sort(inputArray[i]);
    }

    return inputArray;
  }
}
