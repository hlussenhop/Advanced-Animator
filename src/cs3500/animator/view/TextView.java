package cs3500.animator.view;

import cs3500.animator.util.AnimationBuilder;
import cs3500.model.AnimationModelInterface;
import cs3500.model.BasicAMI;
import cs3500.model.Dimension;
import cs3500.model.Position;
import cs3500.model.Shape;

/**
 * TextView implements AMIView and outputs the textual animation to the console.
 */
public class TextView implements AMIView {

  private AnimationModelInterface model;

  /**
   * Builder class for TextView.
   */
  public static final class Builder implements AnimationBuilder<AMIView> {

    private AnimationModelInterface model = null;
    private int speed;

    /**
     * Returns an instance of Builder.
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
      return new TextView(this);
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
    public AnimationBuilder<AMIView> addMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
      model.getShape(name).setNewState(t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2);
      return this;
    }

    @Override
    public AnimationBuilder<AMIView> addKeyframe(String name, int t, int x, int y, int w, int h, int r, int g, int b) {
      throw new IllegalStateException("Don't use this");
    }
  }

  private String view;

  /**
   * Constructor for TextView.
   *
   * @param m Model used for animation rendering
   */
  public TextView(AnimationModelInterface m) {
    this.model = m;
    this.view = "";
  }

  /**
   * Constructor using builder.
   *
   * @param b Builder
   */
  public TextView(Builder b) {
    this.model = b.model;
    this.view = "";
  }

  @Override
  public void view() {
    this.view = model.textOutput();
    System.out.print(model.textOutput());
  }

  /**
   * Returns text view as string.
   *
   * @return view
   */
  public String getTextView() {
    return this.view;
  }
}
