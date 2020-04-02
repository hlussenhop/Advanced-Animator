package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;

import cs3500.model.BasicAMI;

public class ControlView extends JFrame implements AMIView {

  private final BasicView animation;
  private final BasicAMI model;
  private final AMIPanel panel;
  private final ControlPanel controls;
  private boolean playing = false;
  private boolean looping = false;
  private boolean restart = false;

  public ControlView(BasicView view){
    this.animation = view;
    this.model = this.animation.getModel();
    this.panel = this.animation.getPanel();
    this.controls = new ControlPanel();
    setSize((model.getDimension().getW() * 4)/3, model.getDimension().getH());
    setLocation(model.getPosition().getX(), model.getPosition().getY());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
    panel.setPreferredSize(new Dimension(model.getDimension().getW(),
        model.getDimension().getH()));
    container.add(this.panel);
    controls.setPreferredSize(new Dimension(model.getDimension().getW()/3,
        model.getDimension().getH()));
    container.add(this.controls);
    this.add(container);
    addPausePlay();
    addLooping();
    addRestart();
    addSpeed();
  }

  private void addPausePlay() {
    ActionListener a = e -> {
      if(playing){
        playing = false;
      }else{
        playing = true;
      }
    };
    controls.addPausePlay(a);
  }

  private void addLooping() {
    ActionListener a = e -> {
      if(looping){
        looping = false;
      }else{
        looping = true;
      }
    };
    controls.addLooping(a);
  }

  private void addRestart() {
    ActionListener a = e -> {
      restart = true;
    };
    controls.addRestart(a);
  }

  private void addSpeed() {
    controls.getSpeed().addChangeListener(e -> {
      int speed = controls.getSpeed().getValue();
      model.setSpeed((int) Math.pow(2,speed-1));
    });
  }

  @Override
  public void view() {
    int i = 0;
    while(true) {
      for (; i < model.getLength(); ++i) {
        this.setVisible(true);
        this.animation.updatePanel(i);
        try {
          TimeUnit.MICROSECONDS.sleep(10000 / model.getSpeed());
        } catch (InterruptedException ignored) {
        }
        this.repaint();
        if(restart){
          restart = false;
          i=0;
        }
        while (!playing) {
          this.setVisible(true);
          if(restart){
            restart = false;
            i=0;
          }
        }
      }
      this.setVisible(true);
      if(looping){
        i=0;
      }
      if(restart){
        restart = false;
        i=0;
      }
    }
  }
}
