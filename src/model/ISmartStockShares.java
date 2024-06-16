package model;

import java.time.LocalDate;

/**
 * This interface includes methods that allow to get the date
 * of when a stock share is bough, set the date of the share, and
 * set the current state of share if has been bought or not.
 */
public interface ISmartStockShares extends IStockShares {


  /**
   * Gets the current date of when the stock share was bought.
   *
   * @return the current date.
   */
  LocalDate getDate();


  /**
   * Sets the date of a share.
   *
   * @param date The new date the date will be set too.
   */
  void setDate(LocalDate date);

  /**
   * Determines if a share has been bought or not.
   *
   * @return true if a stock has been bought else returns false.
   */
  boolean isBought();

  /**
   * Sets the bought status based on if the user has
   * bought the shares.
   *
   * @param bought the current status of a share bought or not.
   */
  void setBought(boolean bought);
}
