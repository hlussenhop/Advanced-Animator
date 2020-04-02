package cs3500.animator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cs3500.model.AnimationModelInterface;
import cs3500.model.BasicAMI;
import cs3500.model.Dimension;
import cs3500.model.Position;
import cs3500.model.Shape;

/**
 * Run a Tic Tac Toe game interactively.
 */
public class Main {
  /**
   * Run a Tic Tac Toe game interactively.
   */
  public static void main(String[] args) throws InterruptedException {
    // Old News: console-based game:
    //new TicTacToeConsoleController(new InputStreamReader(System.in),
    //    System.out).playGame(new TicTacToeModel());

    // New Hotness: Graphical User Interface:
    // 1. Create an instance of the model.
    // 2. Create an instance of the view.
    // 3. Create an instance of the controller, passing the view to its constructor.
    // 4. Call playGame() on the controller.
    AnimationModelInterface model = null;
    try {
      File f = new File("src/cs3500/resources/buildings.txt");
      Scanner read = new Scanner(f);
      while (read.hasNextLine()) {
        String data = read.nextLine();
        String[] tokens = data.split("\\s+");
        switch (tokens[0]) {
          case "canvas":
            model = new BasicAMI(new Dimension(Integer.parseInt(tokens[3]),
                Integer.parseInt(tokens[4])), new Position(Integer.parseInt(tokens[1]),
                Integer.parseInt(tokens[2])), 5, 1);
            break;
          case "shape":
            if (model != null) {
              model.addShape(new Shape(tokens[1], tokens[2]));
            }
            break;
          case "motion":
            if (model != null) {
              model.getShape(tokens[1]).setNewState(tokens[2], tokens[3], tokens[4],
                  tokens[6], tokens[5], tokens[7], tokens[8], tokens[9], tokens[10], tokens[11],
                  tokens[12], tokens[14], tokens[13], tokens[15], tokens[16], tokens[17]);
            }
            break;
          default:
            throw new IllegalArgumentException("This is bad: " + data);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    AMIView tView = new TextView(model);
    AMIView bView = new BasicView("Animation", model);
    AMIView sView = new SVGView(model, 1.1, "http://www.w3.org/2000/svg"); //Speed should be powers of 10, higher means slower
    AMIView cView = new ControlView((BasicView) bView);

    sView.view();
    tView.view();
    //bView.view();
    cView.view();
  }
}
