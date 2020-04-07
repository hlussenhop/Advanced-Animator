package cs3500.animator.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import cs3500.model.BasicAMI;

/**
 * Creates a new JPanel with animation controls.
 */
public class ControlPanel extends JPanel {

  private final JButton pausePlay =
      new ControlButton("Play", "Pause", false);
  private final JButton restart =
      new ControlButton("Restart", "Restart", true);
  private final JButton loop =
      new ControlButton("Loop", "Stop loop", false);
  private final JButton save = new JButton("Save");
  private final JSlider speed = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
  private final JComboBox<String> shapes = new JComboBox<String>();
  private final JTextArea shapeContent = new JTextArea();
  private final BasicAMI model;

  /**
   * Adds actionListener to play/pause button.
   *
   * @param a ActionListener
   */
  public void addPausePlay(ActionListener a) {
    pausePlay.addActionListener(a);
  }

  /**
   * Adds actionListener to loop button.
   *
   * @param a ActionListener
   */
  public void addLooping(ActionListener a) {
    loop.addActionListener(a);
  }

  /**
   * Adds actionListener to restart button.
   *
   * @param a ActionListener
   */
  public void addRestart(ActionListener a) {
    restart.addActionListener(a);
  }

  /**
   * Adds actionListener to save button.
   *
   * @param a ActionListener
   */
  public void addSave(ActionListener a) {
    save.addActionListener(a);
  }

  /**
   * Access to textArea.
   *
   * @return shapeContent
   */
  public JTextArea getTextArea() {
    return shapeContent;
  }

  /**
   * Access to slider.
   *
   * @return speed
   */
  public JSlider getSpeed() {
    return speed;
  }

  /**
   * Gets access to ComboBox.
   *
   * @return shapes
   */
  public JComboBox<String> getShapes() {
    return shapes;
  }

  /**
   * FOR TESTING: gets the save button
   *
   * @return save
   */
  public JButton getSave(){
    return save;
  }

  /**
   * Constructor for ControlPanel.
   *
   * @param model Takes a BasicAMI model
   */
  public ControlPanel(BasicAMI model) {
    this.model = model;
    Hashtable<Integer, JLabel> labels = new Hashtable<>();
    labels.put(1, new JLabel("1x"));
    labels.put(2, new JLabel("2x"));
    labels.put(3, new JLabel("3x"));
    labels.put(4, new JLabel("4x"));
    labels.put(5, new JLabel("5x"));
    speed.setLabelTable(labels);
    speed.setPaintLabels(true);
    this.setLayout(new GridLayout(7, 1));
    this.add(pausePlay);
    this.add(restart);
    this.add(loop);
    this.add(speed);
    this.add(shapes);
    this.add(new JScrollPane(shapeContent));
    this.add(save);
  }
}
