package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Interface representing a portfolio of stock shares.
 */
public interface IPortfolio {

  /**
   * Adds a specified number of shares of a stock to the portfolio.
   *
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock to be added
   * @param shares the number of shares to be added
   * @throws FileNotFoundException if the stock data file is not found
   */
  void addStockShare(String ticker, IStock stock, int shares) throws FileNotFoundException;

  /**
   * Retrieves the map of stock shares in the portfolio.
   *
   * @return a map where the key is the ticker symbol and the value is the StockShares object
   */
  Map<String, StockShares> getStocksShareMap();

  /**
   * Retrieves the name of the portfolio.
   *
   * @return the name of the portfolio
   */
  String getName();


  /**
   * Removes a specified number of shares of a stock to the portfolio.
   *
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock to be added
   * @param shares the number of shares to be removed
   * @throws FileNotFoundException if the stock data file is not found
   */
  void removeStockShare(String ticker, IStock stock, int shares) throws FileNotFoundException;;

  /**
   * Retrieves the current state of the portfolio with information such as the stocks in that
   * portfolio and the amount of shares owned of that specific stock.
   * @param date the date that the user would like to obtain the information of the portfolio.
   * @return The stock and the shares owned for that stock.
   */
  HashMap<String, StockShares> portfolioStateAtDate(LocalDate date) throws FileNotFoundException;


}
