package model;

/**
 * This interface represent shares of stocks. It provides
 * methods that get the shares of the stocks, and gets the stock.
 * Additionally, makes sure that to override the equals, toString, and
 * hash code methods.
 */
public interface IStockShares {

  /**
   * Gets the number of shares.
   *
   * @return the number of shares
   */
  double getShares();

  /**
   * Gets the stock associated with the shares.
   *
   * @return the stock associated with the shares
   */
  IStock getStock();

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param obj the reference object with which to compare
   * @return true if this object is the same as the obj argument; false otherwise
   */
  boolean equals(Object obj);

  /**
   * Returns a string representation of the StockShares object.
   *
   * @return a string representation of the object
   */
  String toString();

  /**
   * Returns a hash code value for the StockShares object.
   *
   * @return a hash code value for this object
   */
  int hashCode();
}
