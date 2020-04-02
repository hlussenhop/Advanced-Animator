package cs3500.model;

import java.awt.Color;
import java.util.Map;

/**
 * AnimationModelInterface is used to represent a series of shapes moving in a frame at different
 * times.
 */
public interface AnimationModelInterface {

  /**
   * addShape adds a shape object to the AMI object.
   *
   * @param s New shape object to be added
   */
  void addShape(Shape s);

  /**
   * getShape gets a shape object from the AMI object.
   *
   * @param name shape to be retrieved
   */
  Shape getShape(String name);

  /**
   * getElements returns the map.
   */
  Map<String, Shape> getElements();

  /**
   * modifyShape changes the characteristics of a specific shape in the frame.
   *
   * @param name Name of shape to be modified
   * @param tick The tick time of the motion
   * @param p    The new position of the shape
   * @param d    The new dimensions of the shape
   * @param c    The new color of the shape
   */
  void modifyShape(String name, int tick, Position p, Dimension d, Color c);

  /**
   * removeShape removes a shape based on the inputted name.
   *
   * @param name The shape to be removed
   */
  void removeShape(String name);

  /**
   * removeAll removes all shapes from the frame.
   */
  void removeAll();

  /**
   * textOutput returns a string of the shapes in the frame.
   *
   * @return String of the text representation of the shapes in the frame
   */
  String textOutput();
}
