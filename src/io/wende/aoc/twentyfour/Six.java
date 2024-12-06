package io.wende.aoc.twentyfour;

import io.wende.aoc.common.Puzzle;

import java.util.*;

enum Direction {
  NORTH, EAST, SOUTH, WEST;
  public Direction opposite() {
    switch (this) {
      case NORTH: return SOUTH;
      case EAST: return WEST;
      case SOUTH: return NORTH;
      case WEST: return EAST;
    }
    return null;
  }
  public Direction right() {
    switch (this) {
      case NORTH: return EAST;
      case EAST: return SOUTH;
      case SOUTH: return WEST;
      case WEST: return NORTH;
    }
    return null;
  }
}

public class Six extends Puzzle {

  private List<List<String>> map = new ArrayList<>();

  public static void main(final String... args) {
    new Six().run();
  }

  private void run() {
    this.prepareInput();
    Point start = this.findStart();
    Direction direction = Direction.NORTH;
    Set<Point> visitedLocations = this.escape(start, direction);
    out("Locations visited {}", visitedLocations.size());
    visitedLocations.remove(start);
    out("Possible loops {}", this.countLoops(start, direction, visitedLocations));
  }

  public int countLoops(final Point start, final Direction direction, final Set<Point> possibleObstructions) {
    int loops = 0;
    for(Point obstruction : possibleObstructions) {
      if(this.isLoop(start, direction, obstruction)) {
        loops++;
      }
    }
    return loops;
  }

  public boolean isLoop(final Point start, final Direction direction, final Point obstruction) {
    Map<Point, Direction> locations = new HashMap();
    locations.put(start.snapshot(), direction);
    Point position = start.snapshot();
    Direction dir = direction;
    while(position.ahead(dir).getX() >= 0 && position.ahead(dir).getY() >= 0 && position.ahead(dir).getX() < map.get(0).size() && position.ahead(dir).getY() < map.size()) {
      if(locations.containsKey(position.ahead(dir)) && locations.get(position.ahead(dir)) == dir) { // loop detected
        return true;
      }
      if(!this.obstacleAhead(position, dir, obstruction)) {
        position.move(dir);
        locations.put(position.snapshot(), dir);
      }
      else {
        dir = dir.right();
      }
    }
    return false;
  }

  public Set<Point> escape(final Point start, final Direction direction) {
    Set<Point> locations = new HashSet();
    locations.add(start.snapshot());
    Point position = start.snapshot();
    Direction dir = direction;
    while(position.ahead(dir).getX() >= 0 && position.ahead(dir).getY() >= 0 && position.ahead(dir).getX() < map.get(0).size() && position.ahead(dir).getY() < map.size()) {
      if(!this.obstacleAhead(position, dir)) {
        position.move(dir);
        locations.add(position.snapshot());
      }
      else {
        dir = dir.right();
      }
    }
    return locations;
  }

  public boolean obstacleAhead(final Point position, final Direction dir) {
    return obstacleAhead(position, dir, null);
  }

  public boolean obstacleAhead(final Point position, final Direction dir, final Point obstruction) {
      Point ahead = position.ahead(dir);
      if(ahead.getX() >= 0 && ahead.getY() >= 0 && ahead.getX() < map.get(0).size() && ahead.getY() < map.size()) {
        if(obstruction != null && ahead.equals(obstruction)) {
          return true;
        }
        return map.get(ahead.getY()).get(ahead.getX()).equals("#");
      }
      else {
        return false;
      }
  }

  public Point findStart() {
    for(int y = map.size(); y-->0; ) {
      int x = String.join("", map.get(y)).indexOf("^");
      if(x > 0) {
        return new Point(x, y);
      }
    }
    return null;
  }

  private void prepareInput() {
    this.map = this.getInputLines().stream().map(it-> Arrays.stream(it.split("")).toList()).toList();
  }
}

class Point {
  private int x;
  private int y;

  public Point(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  public Point move(final Direction direction) {
    switch (direction) {
      case NORTH:
        this.y -= 1;
        break;
      case EAST:
        this.x += 1;
        break;
      case SOUTH:
        this.y += 1;
        break;
      case WEST:
        this.x -= 1;
        break;
    }
    return this;
  }

  public Point ahead(final Direction direction) {
    return this.snapshot().move(direction);
  }

  public Point snapshot() {
    return new Point(this.x, this.y);
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Point point)) return false;
    return x == point.x && y == point.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return this.x + ":" + this.y;
  }
}

