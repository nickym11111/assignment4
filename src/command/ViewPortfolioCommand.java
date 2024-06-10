package command;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import model.IPortfolio;
import model.IStockMarket;
import view.IView;

/**
 * ViewPortfolioCommand is a type of Command. It happens when the user has asked to
 * view the portfolios and is prompted whether they want to look more closely at
 * a specific portfolio or go back. The purpose of
 * this command is to show the current portfolios and ask the user if they want to
 * look at specific portfolio statistics.
 */
public class ViewPortfolioCommand extends ACommand {

  /**
   * ViewPortfolioCommand creates a ViewPortfolioCommand
   * object with the given fields. It calls on the superclass to initialize the object
   * to hold the respective inputs and outputs (or rather holders of
   * input and output) of the program.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */
  public ViewPortfolioCommand(IView view, Scanner s) {
    super(view, s);
  }

  /**
   * The run method in ViewPortfolioCommand show the user all the current
   * portfolios and then will ask the user if they want to look more closely at a
   * specific portfolios statistics or go back.
   *
   * @param myStockMarket represents the current stock market for the command to access and
   *                      obtain data from.
   */
  public void run(IStockMarket myStockMarket) throws IOException {
    Map<String, IPortfolio> portfolios = myStockMarket.getPortfolios();
    if (portfolios.isEmpty()) {
      view.writeMessage("There are no portfolios" + System.lineSeparator());
    } else {
      view.writeMessage("There are " + portfolios.size() + " portfolios, " +
              "with the following names: " + System.lineSeparator());
    }
    for (String key : portfolios.keySet()) {
      view.writeMessage(key + System.lineSeparator());
    }
    boolean back = false;
    while (!back) {
      view.writeMessage("Enter a 'p' to check value of a portfolio"
              + System.lineSeparator());
      view.writeMessage("Enter 'b' to go back to previous options"
              + System.lineSeparator());

      String userInstruction = s.next();

      switch (userInstruction) {
        case "b":
          back = true;
          break;
        case "p":
          new CheckSpecificPortfolioCommand(view, s).run(myStockMarket);
          break;
        default: //error due to unrecognized instruction
          view.writeMessage("Undefined instruction: " + userInstruction +
                  System.lineSeparator());
          break;
      }
    }
  }

}
