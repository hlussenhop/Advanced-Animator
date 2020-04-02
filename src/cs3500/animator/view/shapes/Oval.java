package cs3500.animator.view.shapes;

import java.awt.Graphics2D;

import cs3500.animator.view.ShapeCommand;
import cs3500.model.ShapeState;

/**
 * Oval implements Shape Command and paints an oval with the given Shape State.
 */
public class Oval implements ShapeCommand {

  private ShapeState s;

  /**
   * Constructor for Oval shape command.
   *
   * @param s Current shape state to be painted
   */
  public Oval(ShapeState s) {
    this.s = s;
  }

  @Override
  public void go(Graphics2D g) {
    g.setColor(s.getC());
    g.fillOval(s.getP().getX(), s.getP().getY(), s.getD().getW(), s.getD().getH());
  }
}
