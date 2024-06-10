package command;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import model.IStock;
import model.IStockMarket;
import view.IView;

/**
 * ViewStockCommand is a type of Command. It happens when the user has asked to
 * view the stocks and is prompted whether they want to look more closely at
 * a specific stock or go back. The purpose of
 * this command is to show the current stocks and ask the user if they want to
 * look at specific stocks statistics.
 */
public class ViewStockCommand extends ACommand {

  /**
   * ViewStockCommand creates a ViewStockCommand
   * object with the given fields. It calls on the superclass to initialize the object
   * to hold the respective inputs and outputs (or rather holders of
   * input and output) of the program.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */
  public ViewStockCommand(IView view, Scanner s) {
    super(view, s);
  }

  /**
   * The run method in ViewStockCommand show the user all the current
   * stocks and then will ask the user if they want to look more closely at a
   * specific stock statistics or go back.
   *
   * @param myStockMarket represents the current stock market for the command to access and
   *                      obtain data from.
   */
  public void run(IStockMarket myStockMarket) throws IOException {
    Map<String, IStock> stocks = myStockMarket.getStocks();
    if (stocks.isEmpty()) {
      view.writeMessage("There are no stocks" + System.lineSeparator());
    } else {
      view.writeMessage("There are " + stocks.size() + " stocks, " +
              "with the following names: " + System.lineSeparator());
    }
    for (String key : stocks.keySet()) {
      view.writeMessage(key + System.lineSeparator());
    }
    view.writeMessage("Note: some stocks may not be up to date if they were previously inputted"
            + System.lineSeparator());
    boolean back = false;
    while (!back) {
      view.writeMessage("Enter a 's' to view statistics about a specific stock"
              + System.lineSeparator());
      view.writeMessage("Enter 'b' to go back to previous options" + System.lineSeparator());
      String userInstruction = s.next();
      switch (userInstruction) {
        case "b":
          back = true;
          break;
        case "s":
          new CheckSpecificStockCommand(view, s).run(myStockMarket);
          break;
        default: //error due to unrecognized instruction
          view.writeMessage("Undefined instruction: " + userInstruction + System.lineSeparator());
          break;
      }
    }
  }

}
