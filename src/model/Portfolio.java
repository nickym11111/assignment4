package model;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a portfolio containing shares of various stocks with a name.
 * Includes  methods that allow a user to add shares of stock and get information
 * such as the name or portfolio and total shares owned of a stock.
 */
public class Portfolio implements IPortfolio {
  protected Map<String, StockShares> stocksShareMap;
  String name;

  /**
   * Constructs a new Portfolio object with the given name.
   *
   * @param name the name of the portfolio
   */
  public Portfolio(String name) {
    this.name = name;
    this.stocksShareMap = new HashMap<>();
  }

  /**
   * Gets the map of ticker symbols to stock shares in the portfolio.
   *
   * @return the map of stock shares
   */
  public Map<String, StockShares> getStockShareMap() {
    return stocksShareMap;
  }

  /**
   * Gets the name of the portfolio.
   *
   * @return the name of the portfolio
   */
  public String getName() {
    return name;
  }

  /**
   * Adds shares of a stock to the portfolio.
   *
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock to add shares of
   * @param shares the number of shares to add
   * @throws FileNotFoundException if the file containing the stock data is not found
   */
  public void addStockShare(String ticker, IStock stock, int shares)
          throws FileNotFoundException {
    stocksShareMap.put(ticker, new StockShares(shares, stock));
  }


}



