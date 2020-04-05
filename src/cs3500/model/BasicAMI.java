package cs3500.model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * BasicAMI implements the Animation Interface. This implementation is the bare minimum of what this
 * interface needs to function.
 */
public class BasicAMI implements AnimationModelInterface {

  /**
   * Only data field, represents the current list of shapes.
   */
  private final Map<String, Shape> elements;
  private Dimension dim;
  private Position pos;
  private final int resolution;
  private int speed;

  /**
   * Constructor for BasicAMI.
   *
   * @param dim        Dimensions of animation
   * @param pos        Position of animation
   * @param resolution Resolution of animation
   * @param speed      Speed of resolution
   */
  public BasicAMI(Dimension dim, Position pos, int resolution, int speed) {
    this.dim = dim;
    this.pos = pos;
    this.resolution = resolution;
    this.speed = speed;
    this.elements = new HashMap<>();
  }

  @Override
  public void addShape(Shape s) {
    s.setResolution(resolution);
    elements.put(s.getName(), s);
  }

  @Override
  public Shape getShape(String name) {
    if (!this.elements.containsKey(name)) {
      throw new IllegalArgumentException("Shape is not in the ami");
    }

    return elements.get(name);
  }

  /**
   * Returns the highest tick number of an animation, therefore the length.
   *
   * @return length, int
   */
  public int getLength() {
    int length = 0;
    for (Map.Entry<String, Shape> e : elements.entrySet()) {
      if (e.getValue().getRecentTick() > length) {
        length = e.getValue().getRecentTick();
      }
    }
    return length;
  }

  /**
   * Returns dimensions of animation.
   *
   * @return dim
   */
  public Dimension getDimension() {
    return dim;
  }

  /**
   * Returns position of animation.
   *
   * @return pos
   */
  public Position getPosition() {
    return pos;
  }

  /**
   * Returns resolution of animation.
   *
   * @return resolution
   */
  public int getResolution() {
    return resolution;
  }

  /**
   * Returns speed of animation.
   *
   * @return speed
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * Sets the speed of the animation, enabling edit view.
   *
   * @param speed New speed
   */
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  @Override
  public Map<String, Shape> getElements() {
    return elements;
  }

  @Override
  public void modifyShape(String name, int tick, Position p, Dimension d, Color c) {
    if (!elements.containsKey(name)) {
      throw new IllegalArgumentException(("Shape doesn't exist in the AMI"));
    }
    elements.get(name).setNewState(tick, p, d, c);
  }

  @Override
  public void removeShape(String name) {
    if (!elements.containsKey(name)) {
      throw new IllegalArgumentException(("Shape already doesn't exist in the AMI"));
    }
    elements.remove(name);
  }

  @Override
  public void removeAll() {
    elements.clear();
  }

  @Override
  public String textOutput() {
    StringBuilder ret_val = new StringBuilder();
    ret_val.append("canvas ").append(pos.getX()).append(" ").append(pos.getY()).append(" ").
        append(dim.getH()).append(" ").append(dim.getW()).append("\n");
    for (Map.Entry<String, Shape> entry : elements.entrySet()) {
      ret_val.append(entry.getValue().toString());
    }
    return ret_val.toString();
  }
}