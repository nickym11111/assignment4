package command;

import java.util.Scanner;

import model.IStockMarket;
import view.IView;

/**
 * InputPortfolioCommand is a type of Command. It happens when the user has asked to
 * input a new portfolio. The purpose of this command is to ask the user
 * if they want to continue in making a new/another portfolio or go back
 * to previous options.
 */
public class InputPortfolioCommand extends ACommand {

  /**
   * InputPortfolioCommand creates a InputPortfolioCommand
   * object with the given fields. It calls on the superclass to initialize the object
   * to hold the respective inputs and outputs (or rather holders of
   * input and output) of the program.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */
  public InputPortfolioCommand(IView view, Scanner s) {
    super(view, s);
  }


  /**
   * The run method in InputPortfolioCommand will ask the user if they want
   * to continue to make a new/another portfolio or go back to previous options.
   * This command allows the user to decide to create a new portfolio
   * or go back.
   *
   * @param myStockMarket represents the current stock market for the command to access and
   *                      obtain data from.
   */
  @Override
  public void run(IStockMarket myStockMarket) {
    boolean back = false;
    while (!back) {
      view.writeMessage("Enter 'p' to create another portfolio" + System.lineSeparator());
      view.writeMessage("Enter 'b' to go back to previous options" + System.lineSeparator());
      String userInstruction = s.next();
      switch (userInstruction) {
        case "p":
          new CreatePortfolioCommand(view, s).run(myStockMarket);
          break;
        case "b":
          back = true;
          break;
        default:
          view.writeMessage("Undefined instruction: " + userInstruction + System.lineSeparator());
          break;
      }
    }
  }


}
