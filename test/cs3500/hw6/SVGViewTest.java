package cs3500.hw6;

import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import cs3500.animator.view.SVGView;
import cs3500.model.BasicAMI;
import cs3500.model.Dimension;
import cs3500.model.Position;
import cs3500.model.Shape;

import static org.junit.Assert.assertEquals;

/**
 * SVGView test suite.
 */
public class SVGViewTest {
  private Shape testShapeCircle = new Shape("testShape1Circle", "ellipse");
  private Shape testShapeRectangle = new Shape("testShape1Rect", "rectangle");
  private BasicAMI ami1 = new BasicAMI(new Dimension(500, 500), new Position(200, 200),
      1080, 10);
  private SVGView testSvgView = new SVGView(ami1, 1.1, "http://www.w3.org/2000/svg");

  public String svgString(String path) {
    StringBuilder contentBuilder = new StringBuilder();
    try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
      stream.forEach(s -> contentBuilder.append(s).append("\n"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return contentBuilder.toString();
  }

  @Test
  public void svgSetFrameTest() {
    testSvgView.view();
    assertEquals(svgString("src/cs3500/resources/SVG.svg"),
        "<svg width=\"500\" height=\"500\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "</svg>\n");

  }

  @Test
  public void svgViewCircleTest() {
    Position position1 = new Position();
    Dimension dimension1 = new Dimension();
    ami1.addShape(testShapeCircle);
    testShapeCircle.setNewState(1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    testSvgView.view();
    assertEquals(svgString("src/cs3500/resources/SVG.svg"),
        "<svg width=\"500\" height=\"500\" version=\"1.1\" xmlns=\"" +
            "http://www.w3.org/2000/svg\">\n" +
            "\t<ellipse id=\"testShape1Circle\" cx=\"1\" cy=\"1\" rx=\"1\" ry=\"1\" " +
            "fill=\"transparent\">\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"cx\" from=\"1\" to=\"1\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"cy\" from=\"1\" to=\"1\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"rx\" from=\"1\" to=\"1\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"ry\" from=\"1\" to=\"1\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(1,1,1)\" to=\"rgb(1,1,1)\"/>\n" +
            "</ellipse>\n" +
            "</svg>\n");
  }

  @Test
  public void svgViewRectTest() {
    ami1.addShape(testShapeRectangle);
    testShapeRectangle.setNewState(1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    String[] lines = testShapeRectangle.getLogStr().split("\\r?\\n");
    testSvgView.view();
    assertEquals(svgString("src/cs3500/resources/SVG.svg"),
        "<svg width=\"500\" height=\"500\" version=\"1.1\" xmlns=\"" +
            "http://www.w3.org/2000/svg\">\n" +
            "\t<rect id=\"testShape1Rect\" x=\"1\" y=\"1\" width=\"1\" " +
            "height=\"1\" fill=\"transparent\">\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"x\" from=\"1\" to=\"1\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"y\" from=\"1\" to=\"1\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"width\" from=\"1\" to=\"1\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"height\" from=\"1\" to=\"1\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"0.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(1,1,1)\" to=\"rgb(1,1,1)\"/>\n" +
            "</rect>\n" +
            "</svg>\n");

  }

  @Test
  public void svgViewAnimationTest() {
    ami1.addShape(testShapeRectangle);
    testShapeRectangle.setNewState(1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    testShapeRectangle.setNewState(1, 1, 1, 1, 1, 1,
        1, 1, 2, 2, 2, 2, 2, 2, 2, 2);
    testShapeRectangle.setNewState(2, 2, 2, 2, 2, 2,
        2, 2, 3, 3, 3, 3, 3, 3, 3, 3);
    String[] lines = testShapeRectangle.getLogStr().split("\\r?\\n");
    testSvgView.view();
    assertEquals(svgString("src/cs3500/resources/SVG.svg"),
        "<svg width=\"500\" height=\"500\" version=\"1.1\" xmlns=\"" +
            "http://www.w3.org/2000/svg\">\n" +
            "\t<rect id=\"testShape1Rect\" x=\"1\" y=\"1\" width=\"1\" height=\"1\" " +
            "fill=\"transparent\">\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"x\" from=\"1\" to=\"2\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"y\" from=\"1\" to=\"2\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"width\" from=\"1\" to=\"2\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"height\" from=\"1\" to=\"2\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(1,1,1)\" to=\"rgb(2,2,2)\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"200.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"x\" from=\"2\" to=\"3\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"200.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"y\" from=\"2\" to=\"3\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"200.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"width\" from=\"2\" to=\"3\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"200.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"height\" from=\"2\" to=\"3\"/>\n" +
            "\t\t<animate attributeType=\"xml\" begin=\"200.0ms\" dur=\"100.0ms\" " +
            "attributeName=\"fill\" from=\"rgb(2,2,2)\" to=\"rgb(3,3,3)\"/>\n" +
            "</rect>\n" +
            "</svg>\n");
  }
}
