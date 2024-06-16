package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import command.InputPortfolioCommand;
import command.InputStockCommand;
import command.ViewPortfolioCommand;
import command.ViewStockCommand;
import model.IStockMarket;
import view.IView;
import view.ViewImpl;

/**
 * This class represents the controller of an interactive stock market application.
 * This controller offers a simple text interface in which the user can
 * type instructions to manipulate a stock market by creating stocks, portfolios,
 * and viewing data on stocks and portfolios.
 *
 *
 *
 * <p>This controller works with any Readable to read its inputs and
 * any Appendable to transmit output. This controller does not
 * directly use the Appendable object, instead using the IView interface
 * to display the output and the view holds the appendable.
 *
 *
 *
 * <p>Stocks can be created using files or APIs and portfolios can be created using stocks.
 * Stocks can be viewed and checked for statistics such and gain or loss over a period,
 * an x-day moving average, and an x-day crossover range. Portfolios can be evaluated for
 * a given date. All portfolios are saved and all stocks from the API are saved so the data
 * is not erased even if the program is quit.
 */
public class StockController implements IController {
  protected final Readable in;
  protected final IStockMarket myStockMarket;
  protected IView view;

  /**
   * StockController creates a new StockController and assigns the given appendable to the view,
   * and assigns the other given parameters to the controller's fields.
   *
   * @param out         represents the appendable the displays the output of the program
   * @param in          represents the readable that takes in the input from the user
   * @param stockMarket represents the stock market that holds the data that the controller
   *                    can access
   * @throws FileNotFoundException if any files in the stock market were not found
   */
  public StockController(Appendable out, Readable in, IStockMarket stockMarket)
          throws FileNotFoundException {
    this.in = Objects.requireNonNull(in);
    view = new ViewImpl(out);
    myStockMarket = stockMarket;
  }


  /**
   * Starts the stock market application and provides sever command that the
   * user can input. They can view a current stock or portfolio that has been previously inputted.
   * Additionally, have different options to  input a new stock or portfolio.
   * Lastly, the user can also quit the program.
   *
   * @throws IOException if an I/O error occurs while reading input.
   */
  @Override
  public void goController() throws IOException {
    Scanner s = new Scanner(in);
    boolean quit = false;
    while (!quit) {
      this.welcomeMessage();
      String userInstruction = s.next();
      switch (userInstruction) {
        case "q":
        case "quit":
          quit = true;
          break;
        case "vp":
          new ViewPortfolioCommand(view, s).run(myStockMarket);
          break;
        case "vs":
          new ViewStockCommand(view, s).run(myStockMarket);
          break;
        case "stock":
          new InputStockCommand(view, s).run(myStockMarket);
          break;
        case "portfolio":
          new InputPortfolioCommand(view, s).run(myStockMarket);
          break;
        default: //error due to unrecognized instruction
          view.writeMessage("Undefined instruction: "
                  + userInstruction + System.lineSeparator());
          break;
      }
    }
    this.farewellMessage();
  }

  // Prints the available options for the user.
  private void printOptions() throws IllegalStateException {
    view.writeMessage("Your current options are: " + System.lineSeparator());
    view.writeMessage("view current stocks you've previously inputted (INPUT: 'vs')"
            + System.lineSeparator());
    view.writeMessage("view current portfolios you've previously inputted (INPUT: 'vp')"
            + System.lineSeparator());
    view.writeMessage("input a new stock (INPUT: 'stock')"
            + System.lineSeparator());
    view.writeMessage("input a new portfolio (INPUT: 'portfolio')"
            + System.lineSeparator());
    view.writeMessage("q or quit (quit the program) "
            + System.lineSeparator());
  }

  // Displays a welcome message and prints the available options.
  private void welcomeMessage() throws IllegalStateException {
    printOptions();
  }

  // Displays a farewell message when the program ends.
  private void farewellMessage() throws IllegalStateException {
    view.writeMessage("Thank you for using this program!");
  }


}
