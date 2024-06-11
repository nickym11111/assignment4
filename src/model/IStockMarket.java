package model;

import java.util.HashMap;

/**
 * Interface representing a stock market with methods to manage
 * stocks and portfolios.
 */
public interface IStockMarket {

  /**
   * Adds a stock to the stock market.
   *
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock to be added
   */
  void addStock(String ticker, IStock stock);

  /**
   * Checks if a stock with the specified ticker symbol exists
   * in the stock market.
   *
   * @param ticker the ticker symbol of the stock to check
   * @return true if the stock exists, false otherwise
   */
  boolean checkStock(String ticker);

  /**
   * Checks if a portfolio with the specified name exists in the
   * stock market.
   *
   * @param name the name of the portfolio to check
   * @return true if the portfolio exists, false otherwise
   */
  boolean checkPortfolio(String name);

  /**
   * Adds a portfolio to the stock market.
   *
   * @param name the name of the portfolio to add
   */
  void addPortfolio(String name);

  /**
   * Retrieves a portfolio from the stock market by its name.
   *
   * @param name the name of the portfolio to retrieve
   * @return the portfolio with the specified name, or null if not found
   */
  ISmartPortfolio getPortfolio(String name);

  /**
   * Retrieves all portfolios in the stock market.
   *
   * @return a map of portfolio names to portfolio objects
   */
  HashMap<String, ISmartPortfolio> getPortfolios();

  /**
   * Retrieves all stocks in the stock market.
   *
   * @return a map of ticker symbols to stock objects
   */
  HashMap<String, IStock> getStocks();
}
