package cs3500.animator;

import java.io.FileReader;

import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.AMIView;
import cs3500.animator.view.BasicView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;

/**
 * Class to run our animator from. It uses a main function that takes command line args.
 */
public final class Excellence {

  /**
   * Main method to run our animator from.
   *
   * @param args Command line args
   */
  public static void main(String[] args) {
    String fileName = "";
    String fileOut = "";
    int speed = 1;
    AnimationBuilder<AMIView> builder = null;
    for (int i = 0; i < args.length; ++i) {
      switch (args[i]) {
        case ("-in"):
          fileName = args[++i];
          break;
        case ("-view"):
          switch (args[++i]) {
            case ("text"):
              builder = TextView.Builder.newInstance();
              break;
            case ("svg"):
              builder = SVGView.Builder.newInstance();
              break;
            case ("visual"):
              builder = BasicView.Builder.newInstance();
              break;
            default:
              throw new IllegalArgumentException("Invalid entry");
          }
          break;
        case ("-out"):
          fileOut = args[++i];
          break;
        case ("-speed"):
          speed = Integer.parseInt(args[++i]);
          break;
        default:
          throw new IllegalArgumentException("Invalid input");
      }
    }
    try {
      assert builder != null;
      builder.setSpeed(speed);
      if (builder instanceof SVGView.Builder) {
        ((SVGView.Builder) builder).setOut(fileOut);
      }
      FileReader f = new FileReader("src/cs3500/resources/" + fileName);
      AMIView v = AnimationReader.parseFile(f, builder);
      v.view();
    } catch (Exception e) {
      System.out.print("An error occurred!\n");
    }
  }
}
