package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a stock with historical daily stock data. Such as the
 * earliest date of the stock, if the date does exist for the stock,
 * keeps track of the ticker symbol of the stock, and allows for more daily
 * stocks to be added to the stock
 */
public class Stock implements IStock {
  protected Map<LocalDate, DailyStock> allStocks;
  private String tickerSymbol;
  private LocalDate mostRecentDate;
  private LocalDate earliestDate;

  /**
   * Constructs a new Stock object with the given ticker symbol.
   *
   * @param tickerSymbol the ticker symbol of the stock
   */
  public Stock(String tickerSymbol) {
    this.tickerSymbol = tickerSymbol;
    allStocks = new HashMap<>();

  }

  /**
   * Gets the daily stock data for the given date.
   *
   * @param date the date for which to get the stock data
   * @return the daily stock data for the given date
   */
  public DailyStock getStockAtDate(LocalDate date) {
    return allStocks.get(date);
  }

  /**
   * Gets all the daily stock data for this stock.
   *
   * @return a map of dates to daily stock data
   */
  public Map<LocalDate, DailyStock> getAllStocks() {
    return allStocks;
  }

  /**
   * Checks if stock data exists for the given date.
   *
   * @param date the date to check
   * @return true if stock data exists for the date, otherwise false
   */
  public boolean doesDateExist(LocalDate date) {
    return allStocks.containsKey(date);
  }


  /**
   * Gets the most recent date for which stock data is available.
   *
   * @return the most recent date
   */
  public LocalDate getMostRecentDate() {
    return mostRecentDate;
  }

  /**
   * Sets the most recent date for which stock data is available.
   *
   * @param date the most recent date
   */
  public void setMostRecentDate(LocalDate date) {
    this.mostRecentDate = date;
  }

  /**
   * Gets the ticker symbol of this stock.
   *
   * @return the ticker symbol
   */
  public String getTickerSymbol() {
    return tickerSymbol;
  }

  /**
   * Adds daily stock data for the given date.
   *
   * @param date  the date for which to add stock data
   * @param stock the daily stock data to add
   */
  public void addDailyStock(LocalDate date, DailyStock stock) {
    this.allStocks.put(date, stock);
  }

  /**
   * Gets the earliest date for which stock data is available.
   *
   * @return the earliest date
   */
  public LocalDate getEarliestDate() {
    return earliestDate;
  }

  /**
   * Sets the earliest date for which stock data is available.
   *
   * @param date the earliest date
   */
  public void setEarliestDate(LocalDate date) {
    this.earliestDate = date;
  }

  /**
   * Gets the closing stock value for the given date.
   *
   * @param date the date for which to get the stock value
   * @return the closing stock value for the given date
   * @throws IllegalArgumentException if stock data does not exist for the given date
   */
  public double getStockValue(LocalDate date) {
    if (!allStocks.containsKey(date)) {
      throw new IllegalArgumentException("Stock does not exist");
    }
    return allStocks.get(date).getCloseValue();
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the reference object with which to compare
   * @return true if this object is the same as the o argument, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Stock stock = (Stock) o;
    return Objects.equals(tickerSymbol, stock.tickerSymbol) &&
            Objects.equals(mostRecentDate, stock.mostRecentDate) &&
            Objects.equals(earliestDate, stock.earliestDate) &&
            Objects.equals(allStocks, stock.allStocks);
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(tickerSymbol, mostRecentDate, earliestDate, allStocks);
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString() {
    return "Stock" +
            "tickerSymbol='" + tickerSymbol + '\'' +
            ", mostRecentDate=" + mostRecentDate +
            ", earliestDate=" + earliestDate +
            ", allStocks=" + allStocks;
  }


}
