package command;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

import model.IStock;
import view.IView;

/**
 * GainOrLoss is a type of Stock strategy. Gain or loss is the change of stock
 * over a given period of time, based on the closing price only.
 * So this strategy calculates
 * the gain or loss of a stock from a start and end date.
 */
public class GainOrLoss implements IStockStrategies {
  private final IView out;

  /**
   * GainOrLoss creates a new GainOrLoss object with the
   * given IView field.
   *
   * @param out represents an IView field which holds program output
   */
  public GainOrLoss(IView out) {
    this.out = Objects.requireNonNull(out);
  }

  /**
   * stratGo for a GainOrLoss takes in 2 dates and calculates
   * the difference between the stocks closing prices on the start date
   * and end date and appends it to the view (output).
   * If they incorrectly input dates, it notifies the user
   * and prompts them to try the process again.
   *
   * @param s     represents a scanner that holds the user input.
   * @param stock represents an IStock object
   */
  @Override
  public void stratGo(Scanner s, IStock stock) {
    try {
      LocalDate start = LocalDate.parse(s.next());
      LocalDate end = LocalDate.parse(s.next());
      out.writeMessage("$" + changeOverTime(start, end, stock));
    } catch (Exception e) {
      out.writeMessage("Invalid input, enter a valid date" +
              " (YYYY-MM-DD) followed by a space and another " +
              "valid date (YYYY-MM_DD) ");
    }
  }

  /**
   * changeOverTime is a method that calculates the difference between a start and
   * end dates closing price for a given stock. It throws an exception if the
   * date is before the start of the stock and accomdates for weekends/holidays
   * /future days by getting the nearest available date.
   *
   * @param start represents the start date
   * @param end   represents the end date
   * @param stock represents the given stock
   * @return a double that represents the gain or loss
   */
  public double changeOverTime(LocalDate start, LocalDate end, IStock stock) {
    DateUtil d = new DateUtil();
    if (!stock.getAllStocks().containsKey(start)) {
      throw new IllegalArgumentException("Stock does not exist for this start date");
    }
    if (stock.getMostRecentDate().isBefore(end)) {
      end = stock.getMostRecentDate();
    }
    if (d.isWeekend(start)) {
      start = d.getNearestAvailableDate(start);
    }
    if (d.isWeekend(end)) {
      end = d.getNearestAvailableDate(end);
    }
    double startClose = stock.getStockAtDate(start).getCloseValue();
    double endClose = stock.getStockAtDate(end).getCloseValue();

    double total = endClose - startClose;
    long bigRound = Math.round(total * 100);
    return bigRound / 100.0;
  }

}
