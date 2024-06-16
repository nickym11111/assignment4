package command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import command.readerbuilder.SavePortfolioOperation;
import model.ISmartPortfolio;
import model.IStockMarket;
import view.IView;

/**
 * The BuyStockCommand class represents a command in a stock market application
 * that allows users to purchase stocks for a specified portfolio. When executed,
 * it prompts the user to input the number of shares they wish to buy and the
 * date of the transaction. It interacts with the user interface (IView) and the
 * stock market data (IStockMarket) to facilitate the purchase process. After
 * successfully adding the stocks to the portfolio, it saves the updated portfolio
 * data using SavePortfolioOperation. This class extends ACommand and implements
 * the run method to execute its functionality within the application.
 */
public class BuyStockCommand extends ACommand {
  private ISmartPortfolio portfolio;

  /**
   * Constructs a BuyStockCommand object.
   *
   * @param view      the view component for user interaction
   * @param s         the Scanner object for input handling
   * @param portfolio the portfolio where stocks will be bought
   */
  public BuyStockCommand(IView view, Scanner s, ISmartPortfolio portfolio) {
    super(view, s);
    this.portfolio = portfolio;
  }

  /**
   * Executes the command to purchase stocks for the specified portfolio. This command
   * prompts the user to input the number of shares they want to buy and the date of
   * the transaction. It validates user input, interacts with the user interface (IView)
   * to gather necessary information, and then utilizes an InputAPIStockCommand to
   * add the purchased stocks to the portfolio. After successfully updating the portfolio,
   * it ensures the changes are saved using SavePortfolioOperation to persist the data.
   *
   * @param stockMarket the stock market instance providing access to stock data
   * @throws IOException if there is an error during the execution of the command
   */
  @Override
  public void run(IStockMarket stockMarket) throws IOException {


    view.writeMessage("How many shares would you like to buy? " +
            System.lineSeparator());
    view.writeMessage("(after entering the shares you will be prompted to enter the " +
            "specific stock itself)" + System.lineSeparator());
    boolean valid = false;
    int shares = 0;
    while (!valid) {
      try {
        shares = Integer.parseInt(s.next());
        valid = true;
      } catch (Exception e) {
        view.writeMessage("Please enter a positive whole number." + System.lineSeparator());
      }
    }


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
    InputAPIStockCommand createStock = new InputAPIStockCommand(view, s);
    createStock.setToPortFolio(portfolio.getName(), shares, start);
    createStock.run(stockMarket);
    new SavePortfolioOperation(portfolio).run();
  }
}
