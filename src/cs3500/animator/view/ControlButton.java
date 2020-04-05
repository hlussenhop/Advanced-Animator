package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Class to have unique button functionality.
 */
public class ControlButton extends JButton {

  /**
   * Implements ActionListener to take construction parameters.
   */
  public class Listener implements ActionListener {
    private JButton button;
    private String name;
    private String alt;

    /**
     * Constructor for listener.
     *
     * @param b    JButton
     * @param name Name of button
     * @param alt  Alternative name
     */
    public Listener(JButton b, String name, String alt) {
      this.button = b;
      this.name = name;
      this.alt = alt;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (!click) {
        if (isPressed) {
          isPressed = false;
          button.setText(name);
        } else {
          isPressed = true;
          button.setText(alt);
        }
      }
    }
  }

  private boolean isPressed;
  private boolean click;

  /**
   * Constructor for controlButton.
   *
   * @param name  Name of controlButton
   * @param alt   Alternative name
   * @param click Whether it stays pressed, or just a click
   */
  public ControlButton(String name, String alt, boolean click) {
    this.isPressed = false;
    this.click = click;
    this.setText(name);
    this.addActionListener(new Listener(this, name, alt));
  }
}
