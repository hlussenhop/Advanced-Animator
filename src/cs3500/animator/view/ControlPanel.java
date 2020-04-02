package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.*;

public class ControlPanel extends JPanel {

  private final JButton pausePlay =
      new ControlButton("src/cs3500/resources/pause_play.png",50,50,false);
  private final JButton restart =
      new ControlButton("src/cs3500/resources/reset.png",50,50,true);
  private final JButton loop =
      new ControlButton("src/cs3500/resources/repeat.png",50,50,false);
  private final JSlider speed = new JSlider(JSlider.HORIZONTAL,1,5,1);

  public void addPausePlay(ActionListener a){
    pausePlay.addActionListener(a);
  }

  public void addLooping(ActionListener a){
    loop.addActionListener(a);
  }

  public void addRestart(ActionListener a){
    restart.addActionListener(a);
  }

  public JSlider getSpeed(){
    return speed;
  }

  public ControlPanel(){
    Hashtable<Integer, JLabel> labels = new Hashtable<>();
    labels.put(1, new JLabel("1x"));
    labels.put(2, new JLabel("2x"));
    labels.put(3, new JLabel("3x"));
    labels.put(4, new JLabel("4x"));
    labels.put(5, new JLabel("5x"));
    speed.setLabelTable(labels);
    speed.setPaintLabels(true);
    this.setLayout(new GridLayout(6,1));
    this.add(pausePlay);
    this.add(restart);
    this.add(loop);
    this.add(speed);
  }
}
