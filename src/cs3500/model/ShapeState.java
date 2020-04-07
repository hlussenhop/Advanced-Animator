package cs3500.model;

import java.awt.Color;
import java.util.Objects;

/**
 * ShapeState represents a shape at a single moment. A list of these make up the lifespan of a
 * shape.
 */
public class ShapeState implements Comparable {

  private final int tick;
  private final ShapeType t;
  private final Position p;
  private final Dimension d;
  private final Color c;

  /**
   * Constructor for ShapeState.
   *
   * @param tick Tick
   * @param p    Position
   * @param d    Dimension
   * @param c    Color
   */
  public ShapeState(int tick, ShapeType t, Position p, Dimension d, Color c) {
    if (tick < 0) {
      throw new IllegalArgumentException("Invalid Tick");
    }
    this.tick = tick;
    this.t = t;
    this.p = new Position(p);
    this.d = new Dimension(d);
    this.c = new Color(c.getRGB());
  }

  /**
   * Copy Constructor for ShapeState.
   *
   * @param s ShapeState to copy from
   */
  public ShapeState(ShapeState s) {
    this.tick = s.tick;
    this.t = s.t;
    this.p = new Position(s.p);
    this.d = new Dimension(s.d);
    this.c = new Color(s.c.getRGB());
  }

  @Override
  public String toString() {
    return tick + " " + p.toString() +
        " " + d.toString() + " " + c.getRed() +
        " " + c.getGreen() + " " + c.getBlue();
  }

  /**
   * Returns position.
   *
   * @return p
   */
  public Position getP() {
    return this.p;
  }

  /**
   * Returns dimensions of ShapeState.
   *
   * @return d
   */
  public Dimension getD() {
    return this.d;
  }

  private int getSize() {
    return d.getH() * d.getW();
  }

  /**
   * Returns color of ShapeState.
   *
   * @return c
   */
  public Color getC() {
    return this.c;
  }

  /**
   * Returns ShapeType of Shape State.
   *
   * @return t
   */
  public ShapeType getT() {
    return this.t;
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
    if (!(obj instanceof ShapeState)) {
      return false;
    }
    ShapeState temp = (ShapeState) obj;
    return (temp.tick == this.tick && temp.t.equals(this.t) && temp.p.equals(this.p)
        && temp.d.equals(this.d) && temp.c.equals(this.c));
  }

  @Override
  public int compareTo(Object o) {
    ShapeState s = (ShapeState) o;
    if (this.getSize() > s.getSize()) {
      return -1;
    } else if (this.getSize() == s.getSize()) {
      return 0;
    }
    return 1;
  }
}
