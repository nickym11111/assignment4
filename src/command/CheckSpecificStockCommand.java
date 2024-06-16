package command;

import java.util.Scanner;

import model.IStock;
import model.IStockMarket;
import view.IView;

/**
 * CheckSpecificStockCommand is a type of Command. It happens when the user has asked to
 * view a stock and now want to look more closely at a specific stock. The purpose of
 * this command is to get the specific stock from the user and ask them what they want
 * to do with that stock next (view gain or loss, moving average, etc).
 */
public class CheckSpecificStockCommand extends ACommand {

  /**
   * CheckSpecificStockCommand creates a CheckSpecificStockCommand
   * object with the given fields. It calls on the superclass to initialize the object
   * to hold the respective inputs and outputs (or rather holders of
   * input and output) of the program.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */
  public CheckSpecificStockCommand(IView view, Scanner s) {
    super(view, s);
  }

  /**
   * The run method in CheckSpecificStockCommand will ask the user for the name of the
   * stock and ask them if they want to see either the gain/loss, moving average, crossovers,
   * or go back.
   * This command is responsible for when a user wants to examine data about a specific
   * stock.
   *
   * @param myStockMarket represents the current stock market for the command to access and
   *                      obtain data from.
   */
  public void run(IStockMarket myStockMarket) {
    boolean back = false;
    view.writeMessage("Enter tickerSymbol to view " +
            "statistics about a stock"
            + System.lineSeparator());
    String userInstruction = s.next();
    while (!myStockMarket.checkStock(userInstruction)) {
      printTryAgainStockInstructions();
      userInstruction = s.next();
      if (userInstruction.equals("b")) {
        return;
      }
    }
    IStock stock = myStockMarket.getStocks().get(userInstruction);
    while (!back) {
      printStockCalculations();
      userInstruction = s.next();
      switch (userInstruction) {
        case "m":
          printMovingDayInstructions();
          new MovingAverage(view).stratGo(s, stock);
          view.writeMessage(" is the moving average"
                  + System.lineSeparator());
          break;
        case "c":
          printCrossoverInstructions();
          new Crossovers(view).stratGo(s, stock);
          break;
        case "g":
          printGainOrLossInstructions();
          new GainOrLoss(view).stratGo(s, stock);
          view.writeMessage(" is gain or loss of this stock " +
                  "between the start and end date"
                  + System.lineSeparator());
          break;
        case "p":
          new GetStockPerformance(view).stratGo(s, stock);
          break;
        case "b":
          back = true;
          break;
        default:
          view.writeMessage("Undefined instruction: " + userInstruction +
                  System.lineSeparator());
          break;
      }
    }
  }

  //prints the message for stock statistic options.
  private void printStockCalculations() throws IllegalStateException {
    view.writeMessage("Enter 'g' to get the gain or loss of this stock " +
            "from a start date to end date"
            + System.lineSeparator());

    view.writeMessage("Enter 'm' to get the x-day moving average of this " +
            "stock at a starting date"
            + System.lineSeparator());

    view.writeMessage("Enter 'c' to show which days are x-day crossovers" +
            " for this stock at a start date " +
            "and end date" + System.lineSeparator());

    view.writeMessage("Enter 'p' to get the performance of a stock" +
            " from a start date to end date" + System.lineSeparator());

    view.writeMessage("Enter 'b' to go back to previous options"
            + System.lineSeparator());
  }

  //prints the message for how to enter the date and number to view the
  // moving day avg of a stock.
  private void printMovingDayInstructions() throws IllegalStateException {
    view.writeMessage("Keep in mind that entering an end " +
            "date/moving day range" +
            " that has not happened yet will " +
            "result" + System.lineSeparator());

    view.writeMessage("in the most recently recorded " +
            "date being used as the " +
            "end date instead."
            + System.lineSeparator());

    view.writeMessage("Keep in mind entering a start date that" +
            " has not happened" +
            " yet will prevent the calculation."
            + System.lineSeparator());

    view.writeMessage("Enter a start date (YYYY-MM-DD) " +
            "followed by a space and" +
            " the positive integer number " +
            "of days of moving average (x): " + System.lineSeparator());
  }

  //prints the message for how to enter the dates and number to view
  // the crossover days of a stock.
  private void printCrossoverInstructions() throws IllegalStateException {
    view.writeMessage("Keep in mind that entering a date/crossover" +
            " day range that " +
            "has not happened yet will result"
            + System.lineSeparator());

    view.writeMessage("in the most recently recorded date being " +
            "used as the" +
            " end date instead."
            + System.lineSeparator());

    view.writeMessage("Keep in mind entering a start date that " +
            "has not happened" +
            " yet will prevent the calculation."
            + System.lineSeparator());

    view.writeMessage("Enter a start date (YYYY-MM-DD) followed " +
            "by a space and" +
            " an end date (YYYY-MM-DD) " +
            "followed by a space and " + System.lineSeparator());

    view.writeMessage("the positive integer number of days of" +
            " moving average (x): "
            + System.lineSeparator());
  }

  //prints the message for how to enter the dates to view
  // the gain or loss of a stock.
  private void printGainOrLossInstructions() throws IllegalStateException {
    view.writeMessage("Keep in mind that entering a date/crossover " +
            "day range that" +
            " has not happened yet will " +
            "result" + System.lineSeparator());

    view.writeMessage("in the most recently recorded date being " +
            "used as the end" +
            " date instead."
            + System.lineSeparator());

    view.writeMessage("Keep in mind entering a start date that" +
            " has not happened" +
            " yet will prevent the calculation."
            + System.lineSeparator());

    view.writeMessage("Enter a start date (YYYY-MM-DD) followed" +
            " by a space and" +
            " an end date (YYYY-MM-DD)"
            + System.lineSeparator());

  }

  //prints the message when a user inputted a non-existent stock.
  private void printTryAgainStockInstructions() {
    view.writeMessage("That stock has not been inputted"
            + System.lineSeparator());
    view.writeMessage("Enter tickerSymbol to view statistics about a stock" +
            System.lineSeparator());
    view.writeMessage("Enter 'b' to go back to previous options"
            + System.lineSeparator());
  }

}
