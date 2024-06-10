package command;

import java.io.IOException;
import java.util.Scanner;

import model.IStockMarket;
import view.IView;

/**
 * InputPortfolioCommand is a type of Command. It happens when the user wants to
 * input a new stock and is being asked from want source, they can use a file, API,
 * or go back. The purpose of this command is to ask the user
 * if they want to continue in making a new stock and how they want to (with
 * what data source).
 */
public class InputStockCommand extends ACommand {

  boolean inPortfolio = false;
  String portfolioName = "";
  int shares = 0;

  /**
   * InputStockCommand creates a InputStockCommand
   * object with the given fields. It calls on the superclass to initialize the object
   * to hold the respective inputs and outputs (or rather holders of
   * input and output) of the program.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */

  public InputStockCommand(IView view, Scanner s) {
    super(view, s);
  }

  /**
   * setToPortFolio sets the portfolio related fields to their respective values.
   * This method is used when a stock is being inputted into a portfolio as well
   * as being created for the first time.
   *
   * @param name   represents the name of the portfolio
   * @param shares represents the shares of the portfolio
   */
  public void setToPortFolio(String name, int shares) {
    this.inPortfolio = true;
    this.portfolioName = name;
    this.shares = shares;
  }


  /**
   * The run method in InputStockCommand will ask the user if they want
   * to continue to make a new/another stock by asking where they want to
   * input their from, they can choose a file, API, or go back.
   * This command allows the user to decide how they want to get their stock data.
   *
   * @param myStockMarket represents the current stock market for the command to access and
   *                      obtain data from.
   */
  public void run(IStockMarket myStockMarket) throws IOException {
    boolean back = false;
    while (!back) {
      view.writeMessage("How would you like to input your stock data? "
              + System.lineSeparator());
      printInputStockOptions();
      String userInstruction = s.next();
      switch (userInstruction) {
        case "f":
          InputUserFileStockCommand fileStock = new InputUserFileStockCommand(view, s);
          if (inPortfolio) {
            fileStock.setToPortFolio(portfolioName, shares);
            fileStock.run(myStockMarket);
            back = true;
          } else {
            fileStock.run(myStockMarket);
          }
          break;
        case "a":
          InputAPIStockCommand apiStock = new InputAPIStockCommand(view, s);
          if (inPortfolio) {
            apiStock.setToPortFolio(portfolioName, shares);
            apiStock.run(myStockMarket);
            back = true;
          } else {
            apiStock.run(myStockMarket);
          }
          break;
        case "b":
          back = true;
          break;
        default: //error due to unrecognized instruction
          view.writeMessage("Undefined instruction: " + userInstruction
                  + System.lineSeparator());
          break;
      }
    }
  }

  // prints the options for how to input stock data
  private void printInputStockOptions() throws IllegalStateException {
    view.writeMessage("Enter 'f' to input the stock data through a csv file."
            + System.lineSeparator());
    view.writeMessage("Enter 'a' to input the stock data through our online resource (API)."
            + System.lineSeparator());
    view.writeMessage("Enter 'b' to go back to previous options or view stock data."
            + System.lineSeparator());
  }
}
