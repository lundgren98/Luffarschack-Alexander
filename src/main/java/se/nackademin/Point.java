package se.nackademin;

import java.util.ArrayList;
import java.util.List;

public class Point {

  public int x;
  public int y;

  public Point() {
    this.invalidate();
  }

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // For mutability
  public void set(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void set(Point p) {
    this.x = p.x;
    this.y = p.y;
  }

  public void invalidate() {
    this.x = -1;
    this.y = -1;
  }

  public boolean isInBounds(int lower, int upper) {
    return (
      this.x >= lower &&
      this.y >= lower &&
      this.x < upper &&
      this.y < upper
      );
  }

  public boolean isInBounds(int upper) {
    return this.isInBounds(0, upper);
  }

  public int distance(Point other) {
    return Math.max(Math.abs(this.x - other.x),
        Math.abs(this.y - other.y));
  }

  public Point plus(Point other) {
    return new Point(this.x + other.x, this.y + other.y);
  }

  public Point minus(Point other) {
    return new Point(this.x - other.x, this.y - other.y);
  }

  public Point direction(Point other) {
    int dx = (this.x == other.x) ? 0
      : (this.x < other.x) ? 1 : -1;
    int dy = (this.y == other.y) ? 0
      : (this.y < other.y) ? 1 : -1;
    return new Point(dx, dy);
  }

  public boolean equals(Point other) {
    return (this.x == other.x && this.y == other.y);
  }

  public List<Point> getNeighbours() {
    List<Point> retVal = new ArrayList<Point>();
    for (int y = -1; y <= 1; y++) {
      for (int x = -1; x <= 1; x++) {
        if (x == 0 && y == 0)
          continue;
        retVal.add(this.plus(new Point(x,y)));
      }
    }
    return retVal;
  }
}