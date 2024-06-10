package command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import model.IStock;
import view.IView;

/**
 * Crossovers is a type of Stock strategy. A crossover is when happens when the closing price
 * for a day is greater than the x-day moving average for that day. So this strategy calculates
 * the number of days in a date range that are crossovers.
 */
public class Crossovers implements IStockStrategies {
  private final IView out;

  /**
   * Crossovers creates a new Crossovers object with the
   * given IView field.
   *
   * @param out represents an IView field which holds program output
   */
  public Crossovers(IView out) {
    this.out = Objects.requireNonNull(out);
  }

  /**
   * stratGo for a Crossovers takes in 2 dates and a positive whole number
   * and appends the list of crossover days to the view to show the user.
   * If they incorrectly input dates or number, it notifies the user
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
      int x = s.nextInt();
      while (x <= 0) {
        out.writeMessage("Please enter a positive number" + System.lineSeparator());
        x = s.nextInt();
      }
      out.writeMessage(printCrossOverDays(getCrossoverDays(start, end, x, stock)) + " "
              + "were crossovers");
    } catch (Exception e) {
      out.writeMessage("Invalid input, enter a valid date (YYYY-MM-DD) followed by a space " +
              "and a positive integer");
    }
  }


  /**
   * isDayACrossover checks if a stock's given date's closing value
   * is greater than the x-day moving average for that date.
   *
   * @param date  represents the day that is being checked for crossover.
   * @param x     represents the x days for x day moving average
   * @param stock represents the stock data being checked
   * @return a boolean representing whether the given date is a crossover
   */
  public boolean isDayACrossover(LocalDate date, int x, IStock stock) {
    return stock.getStockAtDate(date).getCloseValue() >=
            new MovingAverage(out).movingAverage(date, x, stock);
  }

  /**
   * getCrossoverDays goes through a range of time for a given stock and returns a
   * list of all days in the range which were crossovers. It skips weekends and holidays.
   * It accommodates to weekends/holidays/future days by setting that date to the closest
   * available day.
   * It will not calculate if the start day is before the stock was ever created.
   *
   * @param start represents the start date for the range
   * @param end   represents the end date for the range
   * @param x     represents the x days for x day moving average
   * @param stock represents the stock data being checked
   * @return a list of dates that represent the crossover days
   */
  public ArrayList<LocalDate> getCrossoverDays(LocalDate start,
                                               LocalDate end, int x, IStock stock) {
    DateUtil d = new DateUtil();
    if (d.isWeekend(start)) {
      start = d.getNearestAvailableDate(start);
    }
    if (d.isWeekend(end)) {
      end = d.getNearestAvailableDate(end);
    }
    if (start.isBefore(stock.getEarliestDate())) {
      throw new IllegalArgumentException("Stock does not exist for this start date");
    }
    if (stock.getMostRecentDate().isBefore(end)) {
      end = stock.getMostRecentDate();
    }

    ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
    LocalDate currDate = start;
    while (currDate.isBefore(end) || currDate.equals(end)) {
      if (stock.doesDateExist(currDate)) {
        dates.add(currDate);
        currDate = currDate.plusDays(1);
      } else {
        currDate = currDate.plusDays(1);
      }
    }
    ArrayList<LocalDate> crossoverDays = new ArrayList<>();
    for (int i = 0; i < dates.size(); i++) {
      if (this.isDayACrossover(dates.get(i), x, stock)) {
        crossoverDays.add(dates.get(i));
      }
    }
    return crossoverDays;
  }

  /**
   * printCrossOverDays prints out all the given dates in an array list
   * or print 0 days if the list is empty.
   *
   * @param dates represents the list of LocalDates
   * @return a String represents all of the dates
   */
  public String printCrossOverDays(ArrayList<LocalDate> dates) {
    if (dates.size() == 0) {
      return "0 days";
    }
    String result = "";
    for (int i = 0; i < dates.size(); i++) {
      result += dates.get(i).toString();
      result += "\n";
    }
    return result;
  }

}
