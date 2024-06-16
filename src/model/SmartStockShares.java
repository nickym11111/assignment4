package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;

/**
 * This class keeps track of the date and current state of the share if
 * has been bought or not. It includes methods that allow to get the date
 * of when a stock share is bough, set the date of the share, and
 * set the current state of share if has been bought or not.
 */
public class SmartStockShares extends StockShares implements ISmartStockShares {
  private LocalDate date;
  private boolean bought;

  /**
   * Constructs a smart StockShares object with the specified number of shares and stock.
   *
   * @param shares the number of shares
   * @param stock  the stock associated with the shares
   * @throws FileNotFoundException if the stock file is not found
   */
  public SmartStockShares(double shares, IStock stock, LocalDate date)
          throws FileNotFoundException {
    super(shares, stock);
    this.date = date;
  }

  /**
   * Gets the current date of when the stock share was bought.
   *
   * @return the current date.
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Sets the date of a share.
   *
   * @param date The new date the date will be set too.
   */
  public void setDate(LocalDate date) {
    this.date = date;
  }

  /**
   * Determines if a share has been bought or not.
   *
   * @return true if a stock has been bought else returns false.
   */
  public boolean isBought() {
    return bought;
  }

  /**
   * Sets the current state the share if has been bough or not.
   *
   * @param bought the current status of the share.
   */
  public void setBought(boolean bought) {
    this.bought = bought;
  }

}
