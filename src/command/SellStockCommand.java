package command;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import command.readerbuilder.SavePortfolioOperation;
import model.ISmartPortfolio;
import model.IStockMarket;
import view.IView;

public class SellStockCommand extends ACommand{
  private ISmartPortfolio portfolio;

  public SellStockCommand(IView view, Scanner s, ISmartPortfolio portfolio) {
    super(view, s);
    this.portfolio = portfolio;
  }

  @Override
  public void run(IStockMarket stockMarket) throws FileNotFoundException {
    view.writeMessage("What company would you like to sell?" +
            System.lineSeparator());
    String ticker = s.next();

    view.writeMessage("How many shares would you like to sell? " +
            System.lineSeparator());
    boolean valid = false;
    int shares = 0;
    while(!valid) {
      try {
        shares = Integer.parseInt(s.next());
        valid = true;
      } catch (Exception e) {
        view.writeMessage("Please enter a positive whole number." + System.lineSeparator());
      }
    }
    view.writeMessage("Please enter the date you'd like to sell this stock on: (YYYY-MM-DD)"
            + System.lineSeparator());
    LocalDate start = null;
    boolean gotDate = false;
    while (!gotDate) {
      try {
        start = LocalDate.parse(s.next());
        gotDate = true;
      } catch (Exception e) {
        view.writeMessage("Invalid input, enter a valid date" +
                " (YYYY-MM-DD) " + System.lineSeparator());
      }
    }

    try {
      portfolio.removeStockShare(ticker, shares, start);
      new SavePortfolioOperation(portfolio).run();
    }
    catch (Exception e) {
      view.writeMessage("Invalid Input: " + e  + System.lineSeparator());
    }

  }


}
