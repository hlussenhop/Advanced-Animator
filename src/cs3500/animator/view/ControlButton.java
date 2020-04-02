package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ControlButton extends JButton {

  public class Listener implements ActionListener{
    private JButton button;
    public Listener(JButton b){
      this.button = b;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      if(!click) {
        if (isPressed) {
          isPressed = false;
          button.setBackground(Color.BLUE);
        } else {
          isPressed = true;
          button.setBackground(Color.RED);
        }
      }
    }
  }

  private boolean isPressed;
  private boolean click;

  private Image getScaledImage(Image srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    return resizedImg;
  }

  public ControlButton(String png, int width, int height, boolean click){
    this.isPressed = false;
    this.click = click;
    try {
      Image img = ImageIO.read(new File(png));
      this.setIcon(new ImageIcon(getScaledImage(img,width,height)));
    } catch (Exception ex) {
      System.out.println(ex);
    }
    this.setBackground(Color.BLUE);
    this.addActionListener(new Listener(this));
  }
}
