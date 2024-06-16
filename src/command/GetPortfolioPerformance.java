package command;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import model.ISmartPortfolio;
import view.IView;

/**
 * GetPortfolioPerformance calculates and displays the performance of a portfolio
 * over a specified period. It prompts the user to input start and end dates for
 * the performance evaluation, handles date validation including adjustments for
 * weekends, and computes the portfolio's performance using market data. The results
 * are displayed in as a bar graph indicating the portfolio's value at regular
 * intervals during the specified period.
 */
public class GetPortfolioPerformance extends APerformance
        implements IPortfolioStrategies {

  /**
   * Constructs a GetPortfolioPerformance object with a specified
   * view for displaying messages.
   *
   * @param view the view interface for user interaction
   */
  public GetPortfolioPerformance(IView view) {
    super(view);
  }

  /**
   * Initiates the performance calculation for a given portfolio over
   * a user-specified period.Prompts the user to enter start and end dates,
   * validates the input, and generates the performance report.
   *
   * @param s         the scanner object for user input
   * @param portfolio the portfolio for which performance is calculated
   */
  @Override
  public void stratGo(Scanner s, ISmartPortfolio portfolio) {
    try {
      view.writeMessage("Please enter your start date of the performance " +
              "period (YYYY-MM-DD): ");
      LocalDate start = LocalDate.parse(s.next());
      view.writeMessage("Please enter your end date of the " +
              "performance period (YYYY-MM-DD): ");
      LocalDate end = LocalDate.parse(s.next());
      view.writeMessage(createPerformance(start, end, portfolio));
    } catch (Exception e) {
      view.writeMessage("Invalid input, enter a valid dates (YYYY-MM-DD) " +
              System.lineSeparator());
      view.writeMessage("That are within the time of this portfolios " +
              "existence and " +
              "in chronological order" +
              System.lineSeparator());
      view.writeMessage("Keep in mind using a national holiday " +
              "as a start or end day may" +
              "prevent calculations" +
              System.lineSeparator());
    }

  }

  /**
   * Calculates the performance of a portfolio between the specified start and end dates.
   * Adjusts dates to nearest available weekday, validates date order, and computes
   * portfolio values at regular intervals during the period.
   *
   * @param start     the start date of the performance period
   * @param end       the end date of the performance period
   * @param portfolio the portfolio for which performance is calculated
   * @return a formatted string presenting the portfolio's performance over the period
   */
  public String createPerformance(LocalDate start, LocalDate end,
                                  ISmartPortfolio portfolio) {
    StringBuilder result = new StringBuilder();
    result.append("\n");
    DateUtil dateUtil = new DateUtil();

    if (start.isAfter(end)) {
      throw new IllegalArgumentException("Start date cannot be " +
              "after end date");
    }
    if (dateUtil.isWeekend(start)) {
      start = dateUtil.getNearestAvailableDate(start);
    }
    if (dateUtil.isWeekend(end)) {
      end = dateUtil.getNearestAvailableDate(end);
    }
    if (start.isBefore(portfolio.getDateCreated())) {
      throw new IllegalArgumentException("Stock must have " +
              "valid start date");
    }
    result.append("Performance of " + portfolio.getName()
            + " from " + start + " to " + end);
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
      try {
        mapOfPrices.put(newTime, new ValuePortfolio(view)
                .getPortfolioValue(newTime, portfolio));
      } catch (Exception e) {
        // no need to print anything since all messages to the user
      }
    }
    return printPerformance(result, mapOfPrices);
  }
}
