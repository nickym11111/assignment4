package command;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import model.IPortfolio;
import model.IStock;
import model.StockShares;
import view.IView;

/**
 * ValuePortfolio is a type of Portfolio strategy. The value of a portfolio
 * at a given date is the closing price for each stock multiplied by the number of shares
 * all summed together. So the ValuePortfolio class is a strategy
 * that calculates the value of a portfolio at given date.
 */
public class ValuePortfolio implements IPortfolioStrategies {
  private final IView out;

  /**
   * ValuePortfolio creates a new ValuePortfolio object with the
   * given IView field.
   *
   * @param out represents an IView field which holds program output
   */
  public ValuePortfolio(IView out) {
    this.out = Objects.requireNonNull(out);
  }

  /**
   * stratGo for ValuePortfolio asks the user for a date for which they want the portfolio value
   * and then uses that date to get that value and append the result to view(output).
   * If the user incorrectly inputs the date, it notifies the user
   * and prompts them to try the process again.
   *
   * @param s         represents a scanner that holds the user input.
   * @param portfolio represents an portfolio object
   */
  @Override
  public void stratGo(Scanner s, IPortfolio portfolio) {
    try {
      LocalDate date = LocalDate.parse(s.next());
      out.writeMessage(getPortfolioValue(date, portfolio) + " ");

    } catch (Exception e) {
      out.writeMessage("Invalid input, enter a valid date (YYYY-MM-DD)");
    }
  }

  /**
   * getPortfolioValue is a method that calculates the value of a given portfolio on a
   * given date by multiplying the closing price for each stock by the number of shares
   * and summing all together.
   *
   * @param date      the given date to be evaluated by
   * @param portfolio the given portfolio
   * @return a double representing the value for that portfolio at the given date
   */

  public double getPortfolioValue(LocalDate date, IPortfolio portfolio)
          throws FileNotFoundException {
    DateUtil d = new DateUtil();
    if (d.isWeekend(date)) {
      date = d.getNearestAvailableDate(date);
    }
    double totalValue = 0.0;
    for (Map.Entry<String, StockShares> entry : portfolio.portfolioStateAtDate(date).entrySet()) {
      IStock stock = entry.getValue().getStock();
      int shares = entry.getValue().getShares();
      double priceOnDate = stock.getStockValue(date);
      if (priceOnDate > 0) {
        totalValue += priceOnDate * shares;
      } else {
        throw new IllegalArgumentException("No price available for stock: "
                + stock.getTickerSymbol()
                + " on date: " + date);
      }
    }
    return totalValue;
  }


}

