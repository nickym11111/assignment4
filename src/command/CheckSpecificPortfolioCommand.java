package command;

import java.io.IOException;
import java.util.Scanner;

import model.ISmartPortfolio;
import model.IStockMarket;
import view.IView;

/**
 * CheckSpecificPortfolioCommand is a type of Command. It happens when the user has asked to
 * view a portfolio and now want to look more closely at a specific portfolio. The purpose of
 * this command is to get the specific portfolio from the user and ask them what they want
 * to do with that portfolio next.
 */
public class CheckSpecificPortfolioCommand extends ACommand {

  /**
   * CheckSpecificPortfolioCommand creates a CheckSpecificPortfolioCommand
   * object with the given fields. It calls on the superclass to initialize the object
   * to hold the respective inputs and outputs (or rather holders of
   * input and output) of the program.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */
  public CheckSpecificPortfolioCommand(IView view, Scanner s) {
    super(view, s);
  }

  /**
   * The run method in CheckSpecificPortfolioCommand will ask the user for the name of the
   * portfolio and ask them if they want to see the value or go back to a previous stage.
   * This command is responsible for when a user wants to examine data about a specific
   * portfolio.
   *
   * @param myStockMarket represents the current stock market for the command to access and
   *                      obtain data from.
   */
  public void run(IStockMarket myStockMarket) throws IOException {
    boolean back = false;
    view.writeMessage("Enter name of portfolio" + System.lineSeparator());
    String userInstruction = s.next();
    while (!myStockMarket.checkPortfolio(userInstruction)) {
      printTryAgainPortfolioInstructions();
      userInstruction = s.next();
      if (userInstruction.equals("b")) {
        return;
      }
    }
    ISmartPortfolio portfolio = myStockMarket.getPortfolios().get(userInstruction);
    while (!back) {
      printPortfolioCalculations();
      userInstruction = s.next();
      switch (userInstruction) {
        case "b":
          back = true;
          break;
        case "v":
          printEvalutePortfolioInstructions();
          new ValuePortfolio(view).stratGo(s, portfolio);
          break;
        case "c":
          new PortfolioCompositionCommand(view, s, portfolio).run(myStockMarket);
          break;
        case "d":
          new PortfolioDistributionCommand(view, s, portfolio).run(myStockMarket);
          break;
        case "buy":
          new BuyStockCommand(view, s, portfolio).run(myStockMarket);
          break;
        case "sell":
          new SellStockCommand(view, s, portfolio).run(myStockMarket);
          break;
        default:
          view.writeMessage("Undefined instruction: " + userInstruction +
                  System.lineSeparator());
          break;
      }
    }
  }

  // prints the message for how to input the date for evaluating a portfolio and notes.
  // to the user about inputted dates the stock doesn't have
  private void printEvalutePortfolioInstructions() throws IllegalStateException {
    view.writeMessage("Keep in mind that entering a date that has not happened " +
            "yet will result"
            + System.lineSeparator());

    view.writeMessage("in the most recently recorded date being used as the " +
            "date instead."
            + System.lineSeparator());

    view.writeMessage("Enter a date (YYYY-MM-DD) for which you want " +
            "the portfolio value"
            + System.lineSeparator());
  }

  // prints the message for how to get the value of a portfolio or go back.
  private void printPortfolioCalculations() {
    view.writeMessage("Enter 'v' to get the value of this portfolio " +
            "on a specific date"
            + System.lineSeparator());
    view.writeMessage("Enter 'c' to get the composition of this portfolio " +
            "on a specific date"
            + System.lineSeparator());
    view.writeMessage("Enter 'd' to get the distribution of this portfolio " +
            "on a specific date"
            + System.lineSeparator());
    view.writeMessage("Enter 'b' to go back to previous options"
            + System.lineSeparator());
    view.writeMessage("Enter 'buy' to buy a stock for the portfolio"
            + System.lineSeparator());
    view.writeMessage("Enter 'sell' to sell stocks from the portfolio"
            + System.lineSeparator());

  }

  // prints the message for when a user inputted a non-existent portfolio name,
  // asks them to try again.
  private void printTryAgainPortfolioInstructions() {
    view.writeMessage("That portfolio was not found/ has not been inputted"
            + System.lineSeparator());
    view.writeMessage("Enter name of portfolio" + System.lineSeparator());
    view.writeMessage("Enter 'b' to go back to previous options"
            + System.lineSeparator());
  }
}
