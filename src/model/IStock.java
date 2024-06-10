package model;

import java.time.LocalDate;
import java.util.Map;

/**
 * Interface representing a stock with methods to access stock data.
 */
public interface IStock {

  /**
   * Retrieves the daily stock data for a given date.
   *
   * @param date the date for which the stock data is requested
   * @return the DailyStock data for the specified date
   */
  DailyStock getStockAtDate(LocalDate date);

  /**
   * Checks if stock data exists for a given date.
   *
   * @param date the date to check
   * @return true if stock data exists for the date, false otherwise
   */
  boolean doesDateExist(LocalDate date);

  /**
   * Retrieves the most recent date for which stock data is available.
   *
   * @return the most recent date with stock data
   */
  LocalDate getMostRecentDate();

  /**
   * Sets the most recent date for which stock data is available.
   *
   * @param date the most recent date to be set
   */
  void setMostRecentDate(LocalDate date);

  /**
   * Retrieves the stock value for a given date.
   *
   * @param date the date for which the stock value is requested
   * @return the stock value on the specified date
   */
  double getStockValue(LocalDate date);

  /**
   * Retrieves the ticker symbol of the stock.
   *
   * @return the ticker symbol of the stock
   */
  String getTickerSymbol();

  /**
   * Retrieves all daily stock data.
   *
   * @return a map with dates as keys and DailyStock objects as values
   */
  Map<LocalDate, DailyStock> getAllStocks();

  /**
   * Adds a daily stock data entry for a given date.
   *
   * @param date  the date for which the stock data is added
   * @param stock the DailyStock data to be added
   */
  void addDailyStock(LocalDate date, DailyStock stock);

  /**
   * Retrieves the earliest date for which stock data is available.
   *
   * @return the earliest date with stock data
   */
  LocalDate getEarliestDate();

  /**
   * Sets the earliest date for which stock data is available.
   *
   * @param date the earliest date to be set
   */
  void setEarliestDate(LocalDate date);


}
