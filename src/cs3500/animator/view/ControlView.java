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
      model = new BasicAMI(new cs3500.model.Dimension(width, height), new Position(x, y),
              10, speed);
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
    AMIPanel panel = this.animation.getPanel();
    this.controls = new ControlPanel(model);
    setSize((model.getDimension().getW() * 4) / 3, model.getDimension().getH());
    setLocation(model.getPosition().getX(), model.getPosition().getY());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
    panel.setPreferredSize(new Dimension(model.getDimension().getW(),
        model.getDimension().getH()));
    container.add(panel);
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

  private boolean validateLine(String name, String line) {
    String[] tokens = line.split(" ");
    if (tokens.length != 18) {
      return false;
    }
    if (!tokens[0].equals("motion")) {
      return false;
    }
    if (!tokens[1].equals(name)) {
      return false;
    }
    if (Integer.parseInt(tokens[2]) < 1 || Integer.parseInt(tokens[10]) < 1) {
      return false;
    }
    if (Integer.parseInt(tokens[5]) < 0 || Integer.parseInt(tokens[6]) < 0 ||
        Integer.parseInt(tokens[13]) < 0 || Integer.parseInt(tokens[14]) < 0) {
      return false;
    }
    return Integer.parseInt(tokens[7]) >= 0 && Integer.parseInt(tokens[7]) <= 255 &&
            Integer.parseInt(tokens[8]) >= 0 && Integer.parseInt(tokens[8]) <= 255 &&
            Integer.parseInt(tokens[9]) >= 0 && Integer.parseInt(tokens[9]) <= 255 &&
            Integer.parseInt(tokens[15]) >= 0 && Integer.parseInt(tokens[15]) <= 255 &&
            Integer.parseInt(tokens[16]) >= 0 && Integer.parseInt(tokens[16]) <= 255 &&
            Integer.parseInt(tokens[17]) >= 0 && Integer.parseInt(tokens[17]) <= 255;
  }

  private void addSave() {
    ActionListener a = e -> {
      if (controls.getShapes().getSelectedItem().equals("New Shape")) {
        String[] text = controls.getTextArea().getText().split("\n");
        if (text.length < 1) {
          return;
        }
        String[] tokens = text[0].split(" ");
        if (tokens.length != 3) {
          return;
        }
        if (!tokens[2].equals("ellipse") && !tokens[2].equals("rectangle")) {
          return;
        }
        Shape s = new Shape(tokens[1], tokens[2]);
        StringBuilder log = new StringBuilder();
        for (int i = 1; i < text.length; ++i) {
          if (!validateLine(tokens[1],text[i])) {
            return;
          }
          log.append(text[i]).append("\n");
        }
        s.setLogStr(log.toString());
        s.updateLog();
        model.addShape(s);
        controls.getShapes().addItem(s.getName());
      } else {
        String[] check = controls.getTextArea().getText().split("\n");
        for (String s : check) {
          if (!validateLine((String) controls.getShapes().getSelectedItem(),s)) {
            return;
          }
        }
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
            "motion \"Name\" 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0\n" +
            "motion \"Name\" 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0");
      }
    };
    controls.getShapes().addActionListener(a);
  }

  /**
   * FOR TESTING: gets control panel.
   *
   * @return controls
   */
  public ControlPanel getControls() {
    return controls;
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
