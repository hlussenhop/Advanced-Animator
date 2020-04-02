package cs3500.model;

/**
 * ShapeType is an enum that represents the different kinds of shapes that this model can display.
 */
public enum ShapeType {

  TRIANGLE("triangle"), RECTANGLE("rectangle"), OVAL("oval"), STAR("star");

  private final String name;

  ShapeType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
