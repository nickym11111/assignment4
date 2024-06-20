package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

/**
 * This interface represent a new version of portfolio that allows a user to add and remove shares
 * of a stock. Additionally, it that provides methods that obtain and set the information
 * such as bought, current, and sold shares.
 */
public interface ISmartPortfolio extends IPortfolio {


  /**
   * Removes a specified number of shares of a stock to the portfolio.
   *
   * @param ticker the ticker symbol of the stock
   * @param shares the number of shares to be removed
   * @throws FileNotFoundException if the stock data file is not found
   */
  void removeStockShare(String ticker, double shares, LocalDate date)
          throws FileNotFoundException;


  /**
   * Adds a specified number of shares of a stock to the portfolio.
   *
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock to be added
   * @param shares the number of shares to be added
   * @throws FileNotFoundException if the stock data file is not found
   */
  void addStockShare(String ticker, IStock stock, double shares, LocalDate date)
          throws FileNotFoundException;

  /**
   * Retrieves the current state of the portfolio with information such as the stocks in that
   * portfolio and the amount of shares owned of that specific stock.
   *
   * @param date the date that the user would like to obtain the information of the portfolio.
   * @return The stock and the shares owned for that stock.
   */
  Map<String, ISmartStockShares> portfolioStateAtDate(LocalDate date)
          throws FileNotFoundException;


  /**
   * Gets the total amount of shares of a stock  that were a bought by a user.
   *
   * @return the total of bought shares.
   */
  Map<String, ArrayList<ISmartStockShares>> getBoughtStockSharesMap();

  /**
   * Sets the total amount of bought shares  by a user.
   */
  void setBoughtStockSharesMap(Map<String, ArrayList<ISmartStockShares>> bought);

  /**
   * Gets the total amount of shares of a stock that were a sold by a user.
   *
   * @return the total of sold shares.
   */
  Map<String, ArrayList<ISmartStockShares>> getSoldStockSharesMap();

  /**
   * Sets the total amount of sold shares  by a user.
   */
  void setSoldStockSharesMap(Map<String, ArrayList<ISmartStockShares>> sold);

  /**
   * Gets the total amount of shares that were owned by a user.
   *
   * @return the total of sold shares.
   */
  Map<String, ISmartStockShares> getCurrentStockSharesMap();

  /**
   * Sets the total amount of shares owned by a user.
   */
  void setCurrentStockSharesMap(Map<String, ISmartStockShares> currentStockSharesMap);

  /**
   * Gets the current day of when a portfolio was created.
   *
   * @return the date of the portfolio was created.
   */
  LocalDate getDateCreated();


  /**
   * sets the dateCreated to the given LocalDate.
   *
   * @param dateCreated represents the new dateCreated
   */
  void setDateCreated(LocalDate dateCreated);

  boolean hasStockAtDate(LocalDate date, String ticker);

  void addExistingStock(String ticker, LocalDate date, double shares) throws FileNotFoundException;

}
