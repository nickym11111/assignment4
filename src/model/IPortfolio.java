package model;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Interface representing a portfolio of stock shares. Includes  methods that allow a
 * user to add shares of stock and get information such as the name or portfolio and
 * total shares owned of a stock.
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
  Map<String, StockShares> getStockShareMap();

  /**
   * Retrieves the name of the portfolio.
   *
   * @return the name of the portfolio
   */
  String getName();


}
