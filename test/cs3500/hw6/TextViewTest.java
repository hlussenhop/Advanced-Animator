package cs3500.hw6;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.awt.*;

import cs3500.animator.view.TextView;
import cs3500.model.BasicAMI;
import cs3500.model.Dimension;
import cs3500.model.Position;
import cs3500.model.Shape;

/**
 * TextView test suite.
 */
public class TextViewTest {
  private Shape testShapeCircle = new Shape("testShape1Circle", "rectangle");
  private BasicAMI ami1 = new BasicAMI(new Dimension(500, 500), new Position(200, 200),
      1080, 10);
  private TextView testTextView = new TextView(ami1);

  @Test
  public void textViewTest() {
    ami1.addShape(testShapeCircle);
    testTextView.view();
    assertEquals(testTextView.getTextView(), "canvas 200 200 500 500\n" +
        "shape testShape1Circle rectangle\n");
  }

  @Test
  public void textViewAnimationTest() {
    testShapeCircle.setNewState(1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    testShapeCircle.setNewState(1, 1, 1, 1, 1, 1,
        2, 2, 2, 2, 2, 2, 2, 2, 2, 2);
    ami1.addShape(testShapeCircle);
    testTextView.view();
    assertEquals(testTextView.getTextView(), "canvas 200 200 500 500\n" +
        "shape testShape1Circle rectangle\n" +
        "motion testShape1Circle 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n" +
        "motion testShape1Circle 1 1 1 1 1 1 2 2 2 2 2 2 2 2 2 2\n");
  }
}
