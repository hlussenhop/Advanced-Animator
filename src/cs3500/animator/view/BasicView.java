package cs3500.animator.view;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import cs3500.animator.util.AnimationBuilder;
import cs3500.model.AnimationModelInterface;
import cs3500.model.BasicAMI;
import cs3500.model.Dimension;
import cs3500.model.Position;
import cs3500.model.Shape;
import cs3500.model.ShapeState;

/**
 * BasicView extends JFrame and implements AMIView. This shows the animation moving in a JFrame.
 */
public class BasicView extends JFrame implements AMIView {

  private AMIPanel panel;
  private BasicAMI model;

  /**
   * Builder class for BasicView.
   */
  public static final class Builder implements AnimationBuilder<AMIView> {

    private BasicAMI model = null;
    private int speed;

    /**
     * Returns new instance of Builder.
     *
     * @return Builder
     */
    public static Builder newInstance() {
      return new Builder();
    }

    private Builder() {
    }

    @Override
    public void setSpeed(int speed) {
      this.speed = speed;
    }

    @Override
    public AMIView build() {
      return new BasicView(this);
    }

    @Override
    public AnimationBuilder<AMIView> setBounds(int x, int y, int width, int height) {
      model = new BasicAMI(new Dimension(width, height), new Position(x, y), 10, speed);
      return this;
    }

    @Override
    public AnimationBuilder<AMIView> declareShape(String name, String type) {
      model.addShape(new Shape(name, type));
      return this;
    }

    @Override
    public AnimationBuilder<AMIView> addMotion(String name, int t1, int x1, int y1, int w1,
                                               int h1, int r1, int g1, int b1, int t2, int x2,
                                               int y2, int w2, int h2, int r2, int g2, int b2) {
      model.getShape(name).setNewState(t1, x1, y1, h1, w1, r1, g1, b1,
          t2, x2, y2, h2, w2, r2, g2, b2);
      return this;
    }

    @Override
    public AnimationBuilder<AMIView> addKeyframe(String name, int t, int x, int y, int w,
                                                 int h, int r, int g, int b) {
      throw new IllegalStateException("Don't use this");
    }
  }

  /**
   * Constructor for BasicView.
   *
   * @param windowTitle Title of animation window
   * @param m           Model used to control animation
   */
  public BasicView(String windowTitle, AnimationModelInterface m) {
    super(windowTitle);
    model = (BasicAMI) m;
    setSize(model.getDimension().getW(), model.getDimension().getH());
    setLocation(model.getPosition().getX(), model.getPosition().getY());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel = new AMIPanel();
    this.add(panel);
  }

  /**
   * Constructor using Builder class.
   *
   * @param b Builder
   */
  public BasicView(Builder b) {
    this.model = b.model;
    setSize(model.getDimension().getW(), model.getDimension().getH());
    setLocation(model.getPosition().getX(), model.getPosition().getY());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new AMIPanel();
    this.add(panel);
  }

  public BasicAMI getModel(){
    return model;
  }

  public AMIPanel getPanel(){
    return panel;
  }

  public void updatePanel(int tick){
    ArrayList<ShapeState> arr = new ArrayList<>();
    for (Map.Entry<String, Shape> e : model.getElements().entrySet()) {
      try {
        if (e.getValue().getLog().get(tick) != null) {
          arr.add(e.getValue().getLog().get(tick));
        }
      } catch (Exception ignored) {
      }
    }
    panel.update(arr);
  }

  @Override
  public void view() {
    int tick = 0;
    while (true) {
      this.setVisible(true);
      ArrayList<ShapeState> arr = new ArrayList<>();
      int size = 0;
      for (Map.Entry<String, Shape> e : model.getElements().entrySet()) {
        try {
          if (e.getValue().getLog().get(tick) != null) {
            arr.add(e.getValue().getLog().get(tick));
          }
          if (e.getValue().getRecentTick() > size) {
            size = e.getValue().getRecentTick();
          }
        } catch (Exception ignored) {
        }
      }
      panel.update(arr);
      try {
        TimeUnit.MICROSECONDS.sleep((1000000 / model.getSpeed()) / model.getResolution());
      } catch (InterruptedException ignored) {
      }
      this.repaint();
      tick++;
      tick %= size;
    }
  }
}
