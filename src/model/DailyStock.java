package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class that represents the daily stock data for a specific stock with the tickerSymbol,
 * date, open value, high value, low val.
 * INVARIANT: The stock price throughout the day must be greater than or equal to 0.
 */
public class DailyStock {
  private String tickerSymbol;

  private LocalDate date;

  private double open;

  private double high;

  private double low;

  private double close;

  private int volume;

  /**
   * Constructs a new DailyStock instance.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @param date         the date of the stock data
   * @param open         the opening price of the stock
   * @param high         the highest price of the stock during the day
   * @param low          the lowest price of the stock during the day
   * @param close        the closing price of the stock
   * @param volume       the volume of the stock traded during the day
   */
  public DailyStock(String tickerSymbol, LocalDate date, double open, double high, double low,
                    double close, int volume) {
    this.tickerSymbol = tickerSymbol;
    this.date = date;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  /**
   * Returns the opening price of the stock.
   *
   * @return the opening price
   */
  public double getOpenValue() {
    return open;
  }

  /**
   * Returns the closing price of the stock.
   *
   * @return the closing price
   */
  public double getCloseValue() {
    return close;
  }

  /**
   * Returns the highest price of the stock during the day.
   *
   * @return the highest price
   */
  public double getHighValue() {
    return high;
  }

  /**
   * Returns the lowest price of the stock during the day.
   *
   * @return the lowest price
   */
  public double getLowValue() {
    return low;
  }

  /**
   * Returns the date of the stock data.
   *
   * @return the date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Returns a string representation of the DailyStock instance.
   *
   * @return a string representation of the DailyStock instance
   */
  @Override
  public String toString() {
    return "DailyStock" +
            "tickerSymbol='" + tickerSymbol +
            ", date=" + date +
            ", open=" + open +
            ", high=" + high +
            ", low=" + low +
            ", close=" + close +
            ", volume= " + volume;
  }

  /**
   * Compares this DailyStock to the specified object. The result is
   * true if and only if the argument is not null and is a DailyStock
   * object that has the same values as this object.
   *
   * @param o the object to compare this DailyStock against
   * @return true if the given object represents a DailyStock equivalent
   *         to this DailyStock, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DailyStock that = (DailyStock) o;
    return Double.compare(that.open, open) == 0 &&
            Double.compare(that.high, high) == 0 &&
            Double.compare(that.low, low) == 0 &&
            Double.compare(that.close, close) == 0 &&
            Objects.equals(tickerSymbol, that.tickerSymbol) &&
            Objects.equals(date, that.date) &&
            Objects.equals(volume, that.volume);
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(tickerSymbol, date, open, high, low, close, volume);
  }


}
