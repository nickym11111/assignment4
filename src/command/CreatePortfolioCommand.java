package command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import command.readerbuilder.SavePortfolioOperation;
import model.IStockMarket;
import view.IView;

/**
 * CreatePortfolioCommand is a type of Command. It happens when the user has asked to
 * input a new portfolio. The purpose of this command is to allow a user to create
 * a new portfolio with stocks and shares for each stock. The portfolio will
 * be hard saved once the user has created it so it can still be accessed even after the program
 * is quit.
 */
public class CreatePortfolioCommand extends ACommand {

  /**
   * CreatePortfolioCommand creates a CreatePortfolioCommand
   * object with the given fields. It calls on the superclass to initialize the object
   * to hold the respective inputs and outputs (or rather holders of
   * input and output) of the program.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */
  public CreatePortfolioCommand(IView view, Scanner s) {
    super(view, s);
  }

  /**
   * The run method in CreatePortfolioCommand will ask the user to name their
   * portfolio, then for how many stocks they want, then the shares for each stock
   * and the stock itself. They can use API or files to input stock data and this
   * method calls on the InputStockCommand which will allow the user to input new
   * stock data.
   * This command allows the user to create a new portfolio.
   *
   * @param myStockMarket represents the current stock market for the command to access and
   *                      obtain data from.
   */
  public void run(IStockMarket myStockMarket) {
    try {
      view.writeMessage("What would you like to name your portfolio? " +
              System.lineSeparator());
      String portName = s.next();
      myStockMarket.addPortfolio(portName);
      printDiffStocks();
      int numberOfStocks = s.nextInt();
      // add catch exception here
      for (int i = 0; i < numberOfStocks; i++) {
        view.writeMessage("First input how many " +
                "shares you'd like for stock " + (i + 1) +
                System.lineSeparator());
        view.writeMessage("(after this you will " +
                "be ask to enter the specific stock itself). "
                + System.lineSeparator());
        view.writeMessage("So please put the " +
                "shares for this stock: "
                + System.lineSeparator());

        try {
          int share = s.nextInt();
          while (share <= 0) {
            view.writeMessage("Please enter a positive number"
                    + System.lineSeparator());
            share = s.nextInt();
          }
          // add catch exception here
          view.writeMessage("Please enter the date you'd like to buy this stock on: (YYYY-MM-DD)"
          + System.lineSeparator());
          LocalDate start = null;
          boolean gotDate = false;
          while (!gotDate) {
            try {
              start = LocalDate.parse(s.next());
              gotDate = true;
            } catch (Exception e) {
              view.writeMessage("Invalid input, enter a valid date" +
                      " (YYYY-MM-DD) ");
            }
          }

          InputStockCommand createStock = new InputStockCommand(view, s);
          createStock.setToPortFolio(portName, share, start);
          createStock.run(myStockMarket);

        } catch (InputMismatchException e) {
          i -= 1;
          view.writeMessage("Not a number " + System.lineSeparator());
          int share = s.nextInt();
        } catch (IOException e) {
          view.writeMessage("File not found for inputted stock.");
        }
      }
      new SavePortfolioOperation(myStockMarket.getPortfolio(portName)).run();
      view.writeMessage("Successfully entered portfolio data"
              + System.lineSeparator());
    } catch (InputMismatchException e) {
      view.writeMessage("Error: not a number" + System.lineSeparator());
      String userInstruction = s.next();
    }
  }

  // prints the message for how many different companies to put in portfolio.
  private void printDiffStocks() {
    view.writeMessage("How would many different stocks (different companies) " +
            "would you like to input into your portfolio? "
            + System.lineSeparator());

  }
}
