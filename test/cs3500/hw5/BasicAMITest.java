package cs3500.hw5;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.awt.Color;

import cs3500.model.*;

/**
 * Test suite for BasicAMI. Tests valid construction, functionality, and exceptions.
 */
public class BasicAMITest {
  /*private Position position1 = new Position();
  private Dimension dimension1 = new Dimension();
  private Shape testShapeCircle = new Shape("testShape1Circle", 1,
      position1, dimension1, Color.GREEN, ShapeType.OVAL);
  private BasicAMI ami1 = new BasicAMI(new Dimension(500,500),new Position(200,200));


  @Test
  public void testAddShape() {
    ami1.addShape(testShapeCircle);
    assertEquals(testShapeCircle, ami1.getShape("testShape1Circle"));
  }

  @Test
  public void testGetShape() {
    ami1.addShape(testShapeCircle);
    assertEquals(testShapeCircle, ami1.getShape("testShape1Circle"));

    Shape testShapeCircle2 = new Shape("testShape1Circle", 1,
        position1, dimension1, Color.GREEN, ShapeType.OVAL);
  }

  @Test
  public void testModifyShape() {
    ami1.addShape(testShapeCircle);
    ami1.modifyShape("testShape1Circle", 1, position1, dimension1, Color.BLACK);
    Shape tempShape1 = new Shape("tempshape", 1, position1, dimension1, Color.BLACK,
        ShapeType.OVAL);
    assertEquals(ami1.getShape("testShape1Circle"), tempShape1);

    Position position2 = new Position(1, 1);
    ami1.modifyShape("testShape1Circle", 1, position2, dimension1, Color.GREEN);
    Shape tempShape2 = new Shape("tempshape", 1, position2, dimension1, Color.GREEN,
        ShapeType.OVAL);
    assertEquals(ami1.getShape("testShape1Circle"), tempShape2);

    Dimension dimension2 = new Dimension(1, 1);
    ami1.modifyShape("testShape1Circle", 1, position1, dimension2, Color.GREEN);
    Shape tempShape3 = new Shape("tempshape", 1, position1, dimension2, Color.GREEN,
        ShapeType.OVAL);
    assertEquals(ami1.getShape("testShape1Circle"), tempShape3);

    ami1.modifyShape("testShape1Circle", 2, position1, dimension1, Color.GREEN);
    Shape tempShape4 = new Shape("tempshape", 2, position1, dimension1, Color.GREEN,
        ShapeType.OVAL);
    assertEquals(ami1.getShape("testShape1Circle"), tempShape4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveShape() {
    ami1.addShape(testShapeCircle);
    Shape tempShape1 = new Shape("tempShape", 1, position1, dimension1, Color.BLACK,
        ShapeType.OVAL);
    ami1.addShape(tempShape1);
    ami1.removeShape("tempShape");
    ami1.getShape("tempShape");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveAll() {
    ami1.addShape(testShapeCircle);
    Shape tempShape1 = new Shape("tempShape", 1, position1, dimension1, Color.BLACK,
        ShapeType.OVAL);
    ami1.addShape(tempShape1);
    ami1.removeAll();
    ami1.getShape("testShape1Circle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDimension() {
    ami1.addShape(testShapeCircle);
    Shape tempShape1 = new Shape("tempShape", 1, position1, new Dimension(-1, -1), Color.BLACK,
        ShapeType.OVAL);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTick() {
    ami1.addShape(testShapeCircle);
    Shape tempShape1 = new Shape("tempShape", -1, position1, dimension1, Color.BLACK,
        ShapeType.OVAL);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMod() {
    ami1.addShape(testShapeCircle);
    Shape tempShape1 = new Shape("tempShape", 1, position1, new Dimension(1, 1), Color.BLACK,
        ShapeType.OVAL);
    ami1.modifyShape("test", 1, position1, dimension1, Color.BLUE);
  }

  @Test
  public void testTextOutput() {
    ami1.addShape(testShapeCircle);
    assertEquals(ami1.textOutput(), "\nshape testShape1Circle oval\n\n\n");

    Position position2 = new Position(1, 1);
    Dimension dimension2 = new Dimension(1, 1);
    ami1.modifyShape("testShape1Circle", 1, position2, dimension2, Color.GREEN);
    Shape tempShape2 = new Shape("tempShape", 1, position2, dimension2, Color.GREEN,
        ShapeType.OVAL);
    ami1.addShape(testShapeCircle);

    assertEquals(ami1.textOutput(), "\nshape testShape1Circle oval\n\nmotion testShape1Circle 1 " +
        "0 0 0 00 255 0   motion testShape1Circle 1 1 1 1 10 255 0\n\n");
  }*/
}
