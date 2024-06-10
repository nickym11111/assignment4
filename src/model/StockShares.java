package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents the shares of a stock.
 */
public class StockShares {
  private int shares;
  private IStock stock;
  private LocalDate date;
  private boolean bought;


  /**
   * Constructs a StockShares object with the specified number of shares and stock.
   *
   * @param shares the number of shares
   * @param stock  the stock associated with the shares
   * @throws FileNotFoundException if the stock file is not found
   */
  public StockShares(int shares, IStock stock) throws FileNotFoundException {
    this.shares = shares;
    this.stock = stock;
  }

  /**
   * Gets the number of shares.
   *
   * @return the number of shares
   */
  public int getShares() {
    return shares;
  }

  /**
   * Gets the stock associated with the shares.
   *
   * @return the stock associated with the shares
   */
  public IStock getStock() {
    return stock;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the reference object with which to compare
   * @return true if this object is the same as the obj argument; false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockShares that = (StockShares) o;
    return shares == that.shares && Objects.equals(stock, that.stock);
  }

  /**
   * Returns a string representation of the StockShares object.
   *
   * @return a string representation of the object
   */

  @Override
  public String toString() {
    return "StockShares" +
            "shares= " + shares +
            ", stock= " + stock;
  }

  /**
   * Returns a hash code value for the StockShares object.
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(shares, stock);
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public boolean isBought() {
    return bought;
  }

  public void setBought(boolean bought) {
    this.bought = bought;
  }

}
