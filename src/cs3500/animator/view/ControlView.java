package cs3500.animator.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import cs3500.animator.util.AnimationBuilder;
import cs3500.model.BasicAMI;
import cs3500.model.Position;
import cs3500.model.Shape;

/**
 * Creates an editable/controllable view for users.
 */
public class ControlView extends JFrame implements AMIView {

  private final BasicView animation;
  private final BasicAMI model;
  private final AMIPanel panel;
  private final ControlPanel controls;
  private boolean playing = false;
  private boolean looping = false;
  private boolean restart = false;

  /**
   * Builder class for BasicView.
   */
  public static final class Builder implements AnimationBuilder<AMIView> {

    private BasicAMI model = null;
    private BasicView view = null;
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
      return new ControlView(this);
    }

    @Override
    public AnimationBuilder<AMIView> setBounds(int x, int y, int width, int height) {
      model = new BasicAMI(new cs3500.model.Dimension(width, height), new Position(x, y), 10, speed);
      view = new BasicView("animation", model);
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
   * Constructor for ControlView.
   *
   * @param view Takes a BasicView to create view from.
   */
  public ControlView(BasicView view) {
    this.animation = view;
    this.model = this.animation.getModel();
    this.panel = this.animation.getPanel();
    this.controls = new ControlPanel(model);
    setSize((model.getDimension().getW() * 4) / 3, model.getDimension().getH());
    setLocation(model.getPosition().getX(), model.getPosition().getY());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
    panel.setPreferredSize(new Dimension(model.getDimension().getW(),
        model.getDimension().getH()));
    container.add(this.panel);
    controls.setPreferredSize(new Dimension(model.getDimension().getW() / 3,
        model.getDimension().getH()));
    container.add(this.controls);
    this.add(container);
    addPausePlay();
    addLooping();
    addRestart();
    addSpeed();
    addComboBox();
    addSave();
  }

  /**
   * Constructor that takes a builder.
   *
   * @param b Builder
   */
  public ControlView(Builder b) {
    this(b.view);
  }

  private void addPausePlay() {
    ActionListener a = e -> {
      playing = !playing;
    };
    controls.addPausePlay(a);
  }

  private void addLooping() {
    ActionListener a = e -> {
      looping = !looping;
    };
    controls.addLooping(a);
  }

  private void addRestart() {
    ActionListener a = e -> {
      restart = true;
    };
    controls.addRestart(a);
  }

  private void addSave() {
    ActionListener a = e -> {
      if (controls.getShapes().getSelectedItem() == "New Shape") {
        String[] text = controls.getTextArea().getText().split("\n");
        String[] tokens = text[0].split(" ");
        Shape s = new Shape(tokens[1], tokens[2]);
        StringBuilder log = new StringBuilder();
        for (int i = 1; i < text.length; ++i) {
          log.append(text[i]).append("\n");
        }
        s.setLogStr(log.toString());
        s.updateLog();
        model.addShape(s);
        controls.getShapes().addItem(s.getName());
      } else {
        model.getElements().get(controls.getShapes().getSelectedItem()).
            setLogStr(controls.getTextArea().getText());
        model.getElements().get(controls.getShapes().getSelectedItem()).updateLog();
      }
    };
    controls.addSave(a);
  }

  private void addSpeed() {
    controls.getSpeed().addChangeListener(e -> {
      int speed = controls.getSpeed().getValue();
      model.setSpeed((int) Math.pow(2, speed - 1));
    });
  }

  private void addComboBox() {
    controls.getShapes().addItem("New Shape");
    for (Map.Entry<String, Shape> s : model.getElements().entrySet()) {
      controls.getShapes().addItem(s.getKey());
    }
    ActionListener a = e -> {
      try {
        controls.getTextArea().setText(model.getElements().
            get(controls.getShapes().getSelectedItem()).getLogStr());
      } catch (Exception ex) {
        controls.getTextArea().setText("shape \"Name\" \"ShapeType\"\n" +
            "motion \"Name\" 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
            "motion \"Name\" 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
      }
    };
    controls.getShapes().addActionListener(a);
  }

  @Override
  public void view() {
    int i = 1;
    while (true) {
      for (; i < model.getLength(); ++i) {
        this.setVisible(true);
        this.animation.updatePanel(i);
        try {
          TimeUnit.MICROSECONDS.sleep(10000 / model.getSpeed());
        } catch (InterruptedException ignored) {
        }
        this.repaint();
        if (restart) {
          restart = false;
          i = 1;
        }
        if (looping && i == model.getLength() - 1) {
          i = 1;
        }
        if (!playing) {
          --i;
        }
      }
      if (looping || restart) {
        restart = false;
        i = 1;
      }
    }
  }
}
