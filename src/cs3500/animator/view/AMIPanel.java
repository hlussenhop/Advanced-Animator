package cs3500.animator.view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import javax.swing.JPanel;

import cs3500.animator.view.shapes.Oval;
import cs3500.animator.view.shapes.Rectangle;
import cs3500.animator.view.shapes.Triangle;
import cs3500.model.ShapeState;
import cs3500.model.ShapeType;

/**
 * AMIPanel extends a JPanel. It is used in our view and updates with a new array of shape states.
 */
public class AMIPanel extends JPanel {

  private ArrayList<ShapeState> shapes;
  private static final Map<ShapeType, Function<ShapeState, ShapeCommand>> commands =
      Map.of(ShapeType.OVAL, Oval::new, ShapeType.RECTANGLE, Rectangle::new,
          ShapeType.TRIANGLE, Triangle::new);

  /**
   * Constructor for AMIPanel.
   */
  public AMIPanel() {
    this.shapes = new ArrayList<>();
  }

  /**
   * update changes the stored list of shapes to be painted on panel.
   *
   * @param s New list of shapes
   */
  public void update(ArrayList<ShapeState> s) {
    this.shapes = (ArrayList<ShapeState>) s.clone();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    Collections.sort(shapes);
    for (ShapeState s : shapes) {
      if (s == null) {
        continue;
      }
      Function<ShapeState, ShapeCommand> cmd = commands.get(s.getT());
      ShapeCommand sc = cmd.apply(s);
      sc.go(g2d);
    }
  }
}
