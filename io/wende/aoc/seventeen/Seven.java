package io.wende.aoc.seventeen;

import java.util.*;
import java.util.stream.Collectors;

public class Seven extends Puzzle {

  public static void main(final String... args) {
    new Seven().run();
  }

  private void run() {
    List<String> remaining = Arrays.stream(input.split("\n"))
        .filter(line -> line.contains("->")).toList();

    for(String line : remaining) {
      boolean found = false;
      String name = line.substring(0, line.indexOf(" "));
      for(String check : remaining) {
        if(check.substring(check.indexOf(" ")).contains(name)) {
          found = true;
        }
      }
      if(!found) {
        out("Bottom program is called {}\n", name);
        calculate(name);
        break;
      }
    }
  }

  private int calculate(final String bottomProgramName) {
    List<String> flatList = Arrays.stream(input.split("\r\n")).collect(Collectors.toList());
    Program bottomProgram = this.buildHierarchy(flatList, bottomProgramName);
    Map<Program, Integer> weightMap = new HashMap(bottomProgram.dependencies.size());

    System.out.print("Calculating weights: ");
    for(Program dependency : bottomProgram.dependencies) {
      int weight = this.sumWeights(dependency);
      weightMap.put(dependency, weight);
      System.out.print(dependency.name  + "(" + weight + ") ");
    }
    System.out.print("\n");

    String nextAnomaly = this.findDifference(weightMap);
    if(nextAnomaly != null) {

      out("Recalculation with anomaly {}", nextAnomaly);
      if(this.calculate(nextAnomaly) == -1) {
        out("Suspect found: {}", nextAnomaly);
        int correctWeight = weightMap.keySet().stream().filter(program -> program.name.equals(nextAnomaly)).findFirst().get().weight + weightMap.values().stream().distinct().reduce((left, right)->left-right).get();
        out("Needs actual weight of {} to balance the tower.", correctWeight);
        return correctWeight;
      }
    }
    else {
      return -1;
    }
    return 1;
  }

  private String findDifference(final Map<Program,Integer> weightMap) {
    Map.Entry<Program,Integer> multi = null, single = null;
    for(Map.Entry<Program,Integer> entry : weightMap.entrySet()) {
      if(multi == null) {
        multi = entry;
      } else if(entry.getValue().compareTo(multi.getValue()) != 0 && single == null) {
        single = entry;
      } else if (entry.getValue().compareTo(multi.getValue()) != 0 && single != null) {
        single = multi;
        multi = entry;
      }
    }

    if(single == null) {
      out("No anomalies found anymore");
      return null;
    }
    else {
      out("Calculated anomaly is {}({})", single.getKey().name, single.getValue());
      return single.getKey().name;
    }
  }

  private int sumWeights(final Program program) {
    return program == null ? 0 : program.weight + program.dependencies().stream().map(i-> sumWeights(i)).reduce(0, Integer::sum);
  }

  private Program buildHierarchy(List<String> programList, String programName) {
    String programLine = programList.stream().filter(it -> it.startsWith(programName)).findFirst().get();

    List dependencyList = new ArrayList();
    if(programLine.indexOf(">") > 0) {
      dependencyList = Arrays.stream(programLine.substring(programLine.indexOf(">") + 2).split(", ")).map(dependencyName -> buildHierarchy(programList, dependencyName)).toList();
    }
    int programWeight = Integer.parseInt(programLine.substring(programLine.indexOf("(") + 1, programLine.indexOf(")")));
    Program p = new Program(programName, programWeight, dependencyList);

    return p;
  }

  public record Program(String name, int weight, List<Program> dependencies){}
}
