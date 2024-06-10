package command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import model.IStock;
import view.IView;

/**
 * MovingAverage is a type of Stock strategy. The x-day moving average is the average of
 * the last x days (starting from the given date). So the MovingAverage class is a strategy
 * that calculates the moving average when given a start date and number of days by the user.
 */
public class MovingAverage implements IStockStrategies {
  private final IView out;

  /**
   * MovingAverage creates a new MovingAverage object with the
   * given IView field.
   *
   * @param out represents an IView field which holds program output
   */
  public MovingAverage(IView out) {
    this.out = Objects.requireNonNull(out);
  }

  /**
   * stratGo for a MovingAverage takes in a dates and a number that it prompts
   * the user for and takes in from the user and calculates
   * the x-day moving average for that range of days (start + x days).
   * If they incorrectly input dates, it notifies the user
   * and prompts them to try the process again.
   *
   * @param s     represents a scanner that holds the user input.
   * @param stock represents an IStock object
   */
  @Override
  public void stratGo(Scanner s, IStock stock) {
    try {
      LocalDate date = LocalDate.parse(s.next());
      int x = s.nextInt();
      while (x <= 0) {
        out.writeMessage("Please enter a positive number" + System.lineSeparator());
        x = s.nextInt();
      }
      out.writeMessage(movingAverage(date, x, stock) + " ");
    } catch (Exception e) {
      out.writeMessage("Invalid input, enter a valid date (YYYY-MM-DD) " +
              "followed by a space and a positive integer");
    }

  }

  /**
   * movingAverage takes in the start date, x days, and the given stock
   * and sums all the closing prices in that range that divides them
   * by the number of days to get the average.
   * It accommodates to weekends/holidays/future days by setting that date to the closest
   * available day.
   * It will not calculate if the start day is before the stock was ever created.
   *
   * @param start represents the start date for the range
   * @param x     represents the x days for x day moving average
   * @param stock represents the stock data being checked
   * @return a double representing the moving average for x days for a given stock and start date
   */
  public double movingAverage(LocalDate start, int x, IStock stock) {
    DateUtil d = new DateUtil();
    if (d.isWeekend(start)) {
      start = d.getNearestAvailableDate(start);
    }
    if (stock.getMostRecentDate().isBefore(start)) {
      start = stock.getMostRecentDate();
    }
    LocalDate end;
    ArrayList<LocalDate> dates = new ArrayList<LocalDate>();

    LocalDate currDate = start;
    while (x > 0) {
      if ((currDate).isBefore(stock.getEarliestDate())) {
        throw new IllegalArgumentException("Stock does not exist for this date");
      }
      if (stock.doesDateExist(currDate)) {
        dates.add(currDate);
        currDate = currDate.minusDays(1);
        x -= 1;
      } else {
        currDate = currDate.minusDays(1);
      }
    }
    double sum = 0;
    for (int i = 0; i < dates.size(); i++) {
      sum += stock.getStockAtDate(dates.get(i)).getCloseValue();
    }
    double preciseMovingAverage = sum / dates.size();
    long bigRound = Math.round(preciseMovingAverage * 10000);
    double littleRound = bigRound / 10000.0;
    return littleRound;
  }


}
