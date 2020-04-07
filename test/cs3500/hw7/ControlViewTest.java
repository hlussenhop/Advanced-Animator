package cs3500.hw7;

import org.junit.Test;

import cs3500.animator.view.BasicView;
import cs3500.animator.view.ControlView;
import cs3500.model.BasicAMI;
import cs3500.model.Dimension;
import cs3500.model.Position;
import cs3500.model.Shape;

import static org.junit.Assert.assertEquals;

/**
 * ControlView test suite.
 */
public class ControlViewTest {
  private Shape rect = new Shape("rect", "rectangle");
  private BasicAMI ami1 = new BasicAMI(new Dimension(500, 500), new Position(200, 200),
      5, 1);

  @Test
  public void controlViewTestSave() {
    ami1.addShape(rect);
    BasicView basicView = new BasicView("animation",ami1);
    ControlView view = new ControlView(basicView);
    rect.setLogStr("motion rect 127 462 162 6 6 255 255 255 127 462 162 6 6 255 255 255\n"
        +"motion rect 127 462 162 6 6 255 255 255 200 462 162 6 6 255 255 255");
    view.getControls().getShapes().setSelectedItem(rect.getName());
    view.getControls().getTextArea().setText("motion rect 1 462 162 6 6 255 255 255 1 462 162 6 6 255 255 255\n"
        +"motion rect 1 462 162 6 6 255 255 255 200 462 162 6 6 255 255 255");
    view.getControls().getSave().doClick();
    assertEquals("motion rect 1 462 162 6 6 255 255 255 1 462 162 6 6 255 255 255\n"
        +"motion rect 1 462 162 6 6 255 255 255 200 462 162 6 6 255 255 255",rect.getLogStr());
  }

  @Test
  public void controlViewTestSpeed() {
    ami1.addShape(rect);
    BasicView basicView = new BasicView("animation",ami1);
    ControlView view = new ControlView(basicView);
    view.getControls().getSpeed().setValue(2);
    assertEquals(2,ami1.getSpeed());
  }
}
