package io.wende.aoc.twentytwo;

import io.wende.aoc.common.Puzzle;

import java.util.List;

public class Ten extends Puzzle {

  public static void main(final String... args) {
    new Ten().run();
  }

  private void run() {

    int finalSignalStrength = 0;

    int cycles = 20;
    int signalStrength = this.getSignalStrength(cycles);
    out("After {} cycles, register is at {}, signal strength at {}", cycles,  signalStrength/ cycles, signalStrength);
    finalSignalStrength += signalStrength;

    cycles = 60;
    signalStrength = this.getSignalStrength(cycles);
    out("After {} cycles, register is at {}, signal strength at {}", cycles,  signalStrength/ cycles, signalStrength);
    finalSignalStrength += signalStrength;

    cycles = 100;
    signalStrength = this.getSignalStrength(cycles);
    out("After {} cycles, register is at {}, signal strength at {}", cycles,  signalStrength/ cycles, signalStrength);
    finalSignalStrength += signalStrength;

    cycles = 140;
    signalStrength = this.getSignalStrength(cycles);
    out("After {} cycles, register is at {}, signal strength at {}", cycles,  signalStrength/ cycles, signalStrength);
    finalSignalStrength += signalStrength;

    cycles = 180;
    signalStrength = this.getSignalStrength(cycles);
    out("After {} cycles, register is at {}, signal strength at {}", cycles,  signalStrength/ cycles, signalStrength);
    finalSignalStrength += signalStrength;

    cycles = 220;
    signalStrength = this.getSignalStrength(cycles);
    out("After {} cycles, register is at {}, signal strength at {}", cycles,  signalStrength/ cycles, signalStrength);
    finalSignalStrength += signalStrength;

    out("Sum of signal strengths is {}", finalSignalStrength);
  }

  private int getSignalStrength(final int cycleCount) {
    int register = 1;
    List<String> instructions = this.getInputLines();

    for(int i = 0, j = 0; j < cycleCount - 1 && i < instructions.size(); i++, j++) {
      String instruction = instructions.get(i);
      if("noop".equals(instruction)) {

      }
      else if(instruction.startsWith("addx")) {
        j++;
        if(j >= cycleCount - 1) {
          break;
        }
        //out("Adding {} count {}", Integer.parseInt(instruction.substring(instruction.indexOf(" ") + 1)), j);
        register += Integer.parseInt(instruction.substring(instruction.indexOf(" ") + 1));
      }
    }
    return register * cycleCount;
  }
}
