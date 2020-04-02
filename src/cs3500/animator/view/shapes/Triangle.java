package cs3500.animator.view.shapes;

import java.awt.Graphics2D;

import cs3500.animator.view.ShapeCommand;
import cs3500.model.ShapeState;

/**
 * Triangle implements Shape Command and paints a triangle with the given Shape State.
 */
public class Triangle implements ShapeCommand {

  private ShapeState s;

  /**
   * Constructor for Triangle.
   *
   * @param s Current shape state to be painted
   */
  public Triangle(ShapeState s) {
    this.s = s;
  }

  @Override
  public void go(Graphics2D g) {
    g.setColor(s.getC());
    g.fillPolygon(new int[]{(s.getP().getX() - (s.getD().getW() / 2)), s.getP().getX(),
        (s.getP().getX() + (s.getD().getW() / 2))}, new int[]{(s.getP().getY() +
        (s.getD().getH() / 2)), (s.getP().getY() - (s.getD().getH() / 2)), (s.getP().getY() +
        (s.getD().getH() / 2))}, 3);
  }
}
