package command;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import model.IStock;
import view.IView;

/**
 * GetStockPerformance calculates and displays the performance of a stock
 * over a specified period. It prompts the user to input start and end dates for
 * the performance evaluation, handles date validation including adjustments for
 * weekends, and computes the stock's performance using market data. The results
 * are displayed in as a bar graph indicating the stock's value at regular
 * intervals during the specified period. However, for stocks we do
 * not have the date on like holidays we will inform the
 * user that we do not have access to that stock when inputting it as
 * a start date or end date.
 */
public class GetStockPerformance extends APerformance implements IStockStrategies {


  /**
   * Constructs a GetStockPerformance object with a view.
   *
   * @param view represents the view of this program (output).
   */
  public GetStockPerformance(IView view) {
    super(view);
  }

  /**
   * Initiates the performance calculation for a given stock over a user-specified period.
   * Prompts the user to enter start and end dates, validates the input, and generates
   * the performance report.
   *
   * @param s     the scanner object for user input
   * @param stock the stock for which performance is calculated
   */
  @Override
  public void stratGo(Scanner s, IStock stock) {
    try {
      view.writeMessage("Please enter your start date of the performance period (YYYY-MM-DD): ");
      LocalDate start = LocalDate.parse(s.next());
      view.writeMessage("Please enter your end date of the performance period (YYYY-MM-DD): ");
      LocalDate end = LocalDate.parse(s.next());
      view.writeMessage(createPerformance(start, end, stock));
    } catch (Exception e) {
      view.writeMessage("Invalid input, enter a valid dates (YYYY-MM-DD) " +
              System.lineSeparator());
      view.writeMessage("That are within the time of this stocks existence and " +
              "in chronological order" +
              System.lineSeparator());
      view.writeMessage("Keep in mind using a national holiday as a start or end day may" +
              "prevent calculations" +
              System.lineSeparator());
    }

  }

  /**
   * Calculates the performance of a stock between the specified start and end dates.
   * Adjusts dates to nearest available weekday, validates date order, and computes
   * stock values at regular intervals during the period.
   *
   * @param start the start date of the performance period
   * @param end   the end date of the performance period
   * @param stock the stock for which performance is calculated
   * @return a formatted string presenting the stock's performance over the period
   */
  public String createPerformance(LocalDate start, LocalDate end, IStock stock) {
    StringBuilder result = new StringBuilder();
    result.append("\n");
    DateUtil dateUtil = new DateUtil();

    if (start.isAfter(end)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }
    if (dateUtil.isWeekend(start) || !stock.doesDateExist(start)) {
      start = dateUtil.getNearestAvailableDate(start);
    }
    if (dateUtil.isWeekend(end) || !stock.doesDateExist(end)) {
      end = dateUtil.getNearestAvailableDate(end);
    }
    if (!stock.doesDateExist(start) || !stock.doesDateExist(end)) {
      throw new IllegalArgumentException("Stock must have date");
    }
    result.append("Performance of " + stock.getTickerSymbol() + " from " + start + " to " + end);
    result.append("\n");

    int days = (int) ChronoUnit.DAYS.between(start, end);
    int numRows = calculateNumRows(days);
    int increment = (int) Math.ceilDiv(days, numRows);

    Map<LocalDate, Double> mapOfPrices = new TreeMap<>();
    for (int i = 0; i < numRows + 1; i++) {
      LocalDate newTime = start.plusDays(increment * i);
      if (dateUtil.isWeekend(newTime)) {
        newTime = dateUtil.getNearestAvailableDate(newTime);
      }
      if (!stock.doesDateExist(newTime)) {
        newTime = end;
      }

      try {


        mapOfPrices.put(newTime, stock.getStockValue(newTime));
      } catch (Exception e) {
        view.writeMessage(e.getMessage());
      }
    }
    return printPerformance(result, mapOfPrices);
  }

}
