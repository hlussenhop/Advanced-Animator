package cs3500.animator.view.shapes;

import java.awt.Graphics2D;

import cs3500.animator.view.ShapeCommand;
import cs3500.model.ShapeState;

/**
 * Rectangle implements Shape Command and paints a rectangle with the given Shape State.
 */
public class Rectangle implements ShapeCommand {

  private ShapeState s;

  /**
   * Constructor for Rectangle.
   *
   * @param s Current shape state to be painted
   */
  public Rectangle(ShapeState s) {
    this.s = s;
  }

  @Override
  public void go(Graphics2D g) {
    g.setColor(s.getC());
    g.fillRect(s.getP().getX(), s.getP().getY(), s.getD().getW(), s.getD().getH());
  }
}
