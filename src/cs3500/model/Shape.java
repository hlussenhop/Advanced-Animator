package cs3500.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Shape represents an element within our animation model.
 */
public class Shape implements Comparable {

  private final Map<Integer, ShapeState> log = new HashMap<>();
  private final String name;
  private final ShapeType s;
  private int resolution;
  private int recentTick;
  private StringBuilder log_str = new StringBuilder();
  private double average_size;

  /**
   * Constructor for shape class.
   *
   * @param name Name of string
   */
  public Shape(String name, ShapeType s) {
    this.name = name;
    this.s = s;
    this.recentTick = 0;
    this.average_size = 0;
  }

  /**
   * Overloaded constructor for Shape.
   *
   * @param name Name of shape
   * @param type String type of shape
   */
  public Shape(String name, String type) {
    this.name = name;
    switch (type) {
      case "rectangle":
        this.s = ShapeType.RECTANGLE;
        break;
      case "ellipse":
        this.s = ShapeType.OVAL;
        break;
      default:
        this.s = null;
        break;
    }
    this.recentTick = 0;
    this.average_size = 0;
  }

  private double getSize() {
    int sum = 0;
    for (Map.Entry<Integer, ShapeState> e : log.entrySet()) {
      sum += e.getValue().getD().getH() * e.getValue().getD().getW();
    }
    return (double) sum / (double) log.size();
  }

  /**
   * Sets the resolution of the shape.
   *
   * @param resolution Resolution used for shape
   */
  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

  /**
   * Sets a new state, using integer.
   *
   * @param tick1 First tick
   * @param px1   First x
   * @param py1   First y
   * @param dh1   First height
   * @param dw1   First width
   * @param cr1   First red
   * @param cg1   First green
   * @param cb1   First blue
   * @param tick2 Second tick
   * @param px2   Second x
   * @param py2   Second y
   * @param dh2   Second height
   * @param dw2   Second width
   * @param cr2   Second red
   * @param cg2   Second green
   * @param cb2   Second blue
   */
  public void setNewState(int tick1, int px1, int py1, int dh1, int dw1,
                          int cr1, int cg1, int cb1, int tick2, int px2, int py2,
                          int dh2, int dw2, int cr2, int cg2, int cb2) {
    setNewState(Integer.toString(tick1), Integer.toString(px1), Integer.toString(py1),
        Integer.toString(dh1), Integer.toString(dw1), Integer.toString(cr1), Integer.toString(cg1),
        Integer.toString(cb1), Integer.toString(tick2), Integer.toString(px2),
        Integer.toString(py2), Integer.toString(dh2), Integer.toString(dw2), Integer.toString(cr2),
        Integer.toString(cg2), Integer.toString(cb2));
  }

  /**
   * Sets a new state for the shape, taking only string inputs
   *
   * @param tick1 First tick
   * @param px1   First x position
   * @param py1   First y position
   * @param dh1   First height
   * @param dw1   First width
   * @param cr1   First red component
   * @param cg1   First green component
   * @param cb1   First blue component
   * @param tick2 Second tick
   * @param px2   Second x position
   * @param py2   Second y position
   * @param dh2   Second height
   * @param dw2   Second width
   * @param cr2   Second red component
   * @param cg2   Second green component
   * @param cb2   Second blue component
   */
  public void setNewState(String tick1, String px1, String py1, String dh1, String dw1,
                          String cr1, String cg1, String cb1, String tick2, String px2, String py2,
                          String dh2, String dw2, String cr2, String cg2, String cb2) {
    log_str.append("motion ").append(name).append(" ").append(tick1).append(" ").append(px1).
        append(" ").append(py1).append(" ").append(dh1).append(" ").append(dw1).append(" ").
        append(cr1).append(" ").append(cg1).append(" ").append(cb1).append(" ").append(tick2).
        append(" ").append(px2).append(" ").append(py2).append(" ").append(dh2).append(" ").
        append(dw2).append(" ").append(cr2).append(" ").append(cg2).append(" ").append(cb2).
        append("\n");
    setNewState(Integer.parseInt(tick2), new Position(Integer.parseInt(px2),
        Integer.parseInt(py2)), new Dimension(Integer.parseInt(dh2),
        Integer.parseInt(dw2)), new Color(Integer.parseInt(cr2),
        Integer.parseInt(cg2), Integer.parseInt(cb2)));
  }

  private int tween(int oldVal, int newVal, int initTick, int currentTick, int finalTick) {
    return ((int) Math.round(oldVal * (double) (finalTick - currentTick) /
        (double) (finalTick - initTick) + newVal * (double) (currentTick - initTick) /
        (double) (finalTick - initTick)));
  }

  /**
   * setNewState adds another ShapeState to a shape's history.
   *
   * @param tick The tick time of the new SS
   * @param p    The position of the new SS
   * @param d    The dimensions of the new SS
   * @param c    The color of the new SS
   */
  public void setNewState(int tick, Position p, Dimension d, Color c) {
    tick *= resolution;
    if (tick < recentTick) {
      throw new IllegalArgumentException("Invalid tick");
    }
    if (log.isEmpty()) {
      log.put(tick, new ShapeState(tick, s, p, d, c));
      recentTick = tick;
      return;
    }
    Position old_p = log.get(recentTick).getP();
    Dimension old_d = log.get(recentTick).getD();
    Color old_c = log.get(recentTick).getC();
    for (int i = recentTick + 1; i <= tick; ++i) {
      Position new_p = new Position(tween(old_p.getX(), p.getX(), recentTick, i, tick),
          tween(old_p.getY(), p.getY(), recentTick, i, tick));
      Dimension new_d = new Dimension(tween(old_d.getH(), d.getH(), recentTick, i, tick),
          tween(old_d.getW(), d.getW(), recentTick, i, tick));
      Color new_c = new Color(tween(old_c.getRed(), c.getRed(), recentTick, i, tick),
          tween(old_c.getGreen(), c.getGreen(), recentTick, i, tick),
          tween(old_c.getBlue(), c.getBlue(), recentTick, i, tick));
      log.put(i, new ShapeState(i, s, new_p, new_d, new_c));
    }
    recentTick = tick;
  }

  /**
   * Updates log based on log_str, this enables editing of shapes and updating their keyframes.
   */
  public void updateLog() {
    log.clear();
    String[] lines = log_str.toString().split("\n");
    recentTick = 0;
    for (String line : lines) {
      String[] tokens = line.split(" ");
      setNewState(Integer.parseInt(tokens[10]), new Position(Integer.parseInt(tokens[11]),
          Integer.parseInt(tokens[12])), new Dimension(Integer.parseInt(tokens[13]),
          Integer.parseInt(tokens[14])), new Color(Integer.parseInt(tokens[15]),
          Integer.parseInt(tokens[16]), Integer.parseInt(tokens[17])));
    }
    this.average_size = getSize();
  }

  /**
   * getShapeState returns a ShapeState based on tick.
   *
   * @return ShapeState, s
   */
  public Map<Integer, ShapeState> getLog() {
    return log;
  }

  /**
   * getName returns the name of a Shape.
   *
   * @return String, name
   */
  public String getName() {
    return name;
  }

  /**
   * getLogStr returns the string log.
   *
   * @return String, log_str
   */
  public String getLogStr() {
    return log_str.toString();
  }

  /**
   * Allows for editing of log_str and updating log in edit view
   *
   * @param logStr String of new log
   */
  public void setLogStr(String logStr) {
    log_str = new StringBuilder(logStr);
  }

  /**
   * getTokenData returns the string log as tokens.
   *
   * @return String, ret_val
   */
  public ArrayList<ArrayList<Integer>> getTokenData() {
    ArrayList<ArrayList<Integer>> ret_val = new ArrayList<>();
    String[] lines = log_str.toString().split("\\r?\\n");
    if (lines.length == 1) {
      ret_val.add(new ArrayList<>());
      String[] line = log_str.toString().split(" ");
      for (int j = 2; j < line.length; ++j) {
        ret_val.get(0).add(Integer.parseInt(line[j].trim()));
      }
    } else {
      for (int i = 1; i < lines.length; ++i) {
        ret_val.add(new ArrayList<>());
        String[] line = lines[i].split(" ");
        for (int j = 2; j < line.length; ++j) {
          ret_val.get(i - 1).add(Integer.parseInt(line[j]));
        }
      }
    }
    return ret_val;
  }

  /**
   * Returns the ShapeType.
   *
   * @return s
   */
  public ShapeType getShapeType() {
    return s;
  }

  /**
   * Returns the resolution.
   *
   * @return resolution
   */
  public int getResolution() {
    return resolution;
  }

  /**
   * Returns the most recent tick added
   *
   * @return recentTick
   */
  public int getRecentTick() {
    return recentTick;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Shape)) {
      return false;
    }
    Shape temp = (Shape) obj;
    return (temp.name.equals(this.name) && log.equals(this.log));
  }

  @Override
  public String toString() {
    return "shape " + name + " " + s.toString() + "\n" +
        log_str.toString();
  }

  @Override
  public int compareTo(Object o) {
    Shape s = (Shape) o;
    if (this.average_size > s.average_size) {
      return -1;
    } else if (this.average_size == s.average_size) {
      return 0;
    }
    return 1;
  }
}