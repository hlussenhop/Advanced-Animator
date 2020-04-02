package cs3500.animator.view;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import cs3500.animator.util.AnimationBuilder;
import cs3500.model.AnimationModelInterface;
import cs3500.model.BasicAMI;
import cs3500.model.Dimension;
import cs3500.model.Position;
import cs3500.model.Shape;

/**
 * SVGView implements AMIView, to output view as SVG file.
 */
public class SVGView implements AMIView {

  private BasicAMI model;
  private StringBuilder file = new StringBuilder();
  private final double version;
  private final String web;
  private final int speed;
  private final String out;

  /**
   * Builder class for SVGView.
   */
  public static final class Builder implements AnimationBuilder<AMIView> {

    private BasicAMI model = null;
    private double version = 1.1;
    private String web = "http://www.w3.org/2000/svg";
    private int speed;
    private String out;

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

    /**
     * Sets the name of the output file.
     *
     * @param out Outfile name
     */
    public void setOut(String out) {
      this.out = out;
    }

    @Override
    public AMIView build() {
      return new SVGView(this);
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
      model.getShape(name).setNewState(t1, x1, y1, h1, w1, r1, g1, b1, t2, x2, y2, h2, w2, r2, g2, b2);
      return this;
    }

    @Override
    public AnimationBuilder<AMIView> addKeyframe(String name, int t, int x, int y, int w, int h, int r, int g, int b) {
      throw new IllegalStateException("Don't use this");
    }
  }

  /**
   * Constructor for SVGView.
   *
   * @param m       Model
   * @param version Version number
   * @param web     Web address used
   */
  public SVGView(AnimationModelInterface m, double version, String web) {
    this.model = (BasicAMI) m;
    this.version = version;
    this.web = web;
    this.speed = 1000 / model.getSpeed();
    this.out = "SVG.svg";
  }

  /**
   * Constructor using Builder class.
   *
   * @param b Builder
   */
  public SVGView(Builder b) {
    this.model = b.model;
    this.version = b.version;
    this.web = b.web;
    this.speed = 1000 / model.getSpeed();
    this.out = b.out;
  }

  private void setFrame(int width, int height, double version, String web) {
    file.append("<svg width=\"").append(width).append("\" height=\"").append(height).
        append("\" version=\"").append(version).append("\" xmlns=\"").append(web).append("\">\n");
  }

  private void setShape(Shape s) {
    ArrayList<ArrayList<Integer>> data = s.getTokenData();
    switch (s.getShapeType()) {
      case OVAL:
        file.append("\t<ellipse id=\"").append(s.getName()).append("\" cx=\"").
            append(data.get(0).get(1))
            .append("\" cy=\"")
            .append(data.get(0).get(2)).
            append("\" rx=\"").append(data.get(0).get(4)).append("\" ry=\"").
            append(data.get(0).get(3)).append("\" fill=\"transparent\">\n");
        for (ArrayList<Integer> a : data) {
          for (int i = 1; i < 6; ++i) {
            file.append("\t\t<animate attributeType=\"xml\" begin=\"").
                append((double) a.get(0) * speed).append("ms\" dur=\"").
                append((double) (a.get(8) - a.get(0)) * speed).append("ms\" attributeName=\"");
            switch (i) {
              case 1:
                file.append("cx\" from=\"").append(a.get(1)).append("\" to=\"").append(a.get(9)).
                    append("\"/>\n");
                break;
              case 2:
                file.append("cy\" from=\"").append(a.get(2)).append("\" to=\"").append(a.get(10)).
                    append("\"/>\n");
                break;
              case 3:
                file.append("rx\" from=\"").append(a.get(4)).append("\" to=\"").append(a.get(12)).
                    append("\"/>\n");
                break;
              case 4:
                file.append("ry\" from=\"").append(a.get(3)).append("\" to=\"").append(a.get(11)).
                    append("\"/>\n");
                break;
              case 5:
                file.append("fill\" from=\"rgb(").append(a.get(5)).
                    append(",").append(a.get(6)).append(",").append(a.get(7)).
                    append(")\"").append(" to=\"rgb(").append(a.get(13)).
                    append(",").append(a.get(14)).append(",").append(a.get(15)).
                    append(")\"").append("/>\n");
                break;
              default:
                throw new IllegalArgumentException("Something went wrong");
            }
          }
        }
        file.append("</ellipse>\n");
        break;
      case RECTANGLE:
        file.append("\t<rect id=\"").append(s.getName()).append("\" x=\"").
            append(data.get(0).get(1)).append("\" y=\"").append(data.get(0).get(2)).
            append("\" width=\"").append(data.get(0).get(4)).append("\" height=\"").
            append(data.get(0).get(3)).append("\" fill=\"transparent\">\n");
        for (ArrayList<Integer> a : data) {
          for (int i = 1; i < 6; ++i) {
            file.append("\t\t<animate attributeType=\"xml\" begin=\"").
                append((double) a.get(0) * speed).append("ms\" dur=\"").
                append((double) (a.get(8) - a.get(0)) * speed).append("ms\" attributeName=\"");
            switch (i) {
              case 1:
                file.append("x\" from=\"").append(a.get(1)).append("\" to=\"").append(a.get(9)).
                    append("\"/>\n");
                break;
              case 2:
                file.append("y\" from=\"").append(a.get(2)).append("\" to=\"").append(a.get(10)).
                    append("\"/>\n");
                break;
              case 3:
                file.append("width\" from=\"").append(a.get(4)).append("\" to=\"").
                    append(a.get(12)).append("\"/>\n");
                break;
              case 4:
                file.append("height\" from=\"").append(a.get(3)).append("\" to=\"").
                    append(a.get(11)).append("\"/>\n");
                break;
              case 5:
                file.append("fill\" from=\"rgb(").append(a.get(5)).
                    append(",").append(a.get(6)).append(",").append(a.get(7)).
                    append(")\"").append(" to=\"rgb(").append(a.get(13)).
                    append(",").append(a.get(14)).append(",").append(a.get(15)).
                    append(")\"").append("/>\n");
                break;
              default:
                throw new IllegalArgumentException("Something went wrong");
            }
          }
        }
        file.append("</rect>\n");
        break;
      default:
        throw new IllegalArgumentException("Bad shape type");
    }
  }

  @Override
  public void view() {
    setFrame(model.getDimension().getW(), model.getDimension().getH(), version, web);
    ArrayList<Shape> s = new ArrayList<Shape>(model.getElements().values());
    Collections.sort(s);
    for (Shape x : s) {
      setShape(x);
    }
    file.append("</svg>\n");
    try {
      FileWriter myWriter = new FileWriter("src/cs3500/resources/" + out);
      myWriter.write(file.toString());
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
