package cs3500.model;

import java.util.Objects;

/**
 * Dimension is a class to represent the height and width of a shape.
 */
public final class Dimension {

  private final int h;
  private final int w;

  /**
   * Constructor for Dimension.
   */
  public Dimension() {
    this.h = 0;
    this.w = 0;
  }

  /**
   * Additional constructor.
   *
   * @param h height
   * @param w width
   */
  public Dimension(int h, int w) {
    if (h < 0 || w < 0) {
      throw new IllegalArgumentException("Invalid Dimensions");
    }
    this.h = h;
    this.w = w;
  }

  /**
   * Copy constructor for dimension.
   *
   * @param dim Dimension to copy
   */
  public Dimension(Dimension dim) {
    this.h = dim.h;
    this.w = dim.w;
  }

  /**
   * getH returns the h component.
   *
   * @return int, h
   */
  public int getH() {
    return this.h;
  }

  /**
   * getH returns the w component.
   *
   * @return int, w
   */
  public int getW() {
    return this.w;
  }

  @Override
  public String toString() {
    return w + " " + h;
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
    if (!(obj instanceof Dimension)) {
      return false;
    }
    Dimension temp = (Dimension) obj;
    return (temp.getH() == this.h && temp.getW() == this.w);
  }
}
