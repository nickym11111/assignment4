package model;

import java.util.HashMap;

/**
 * A mock implementation of the StockMarket interface for testing purposes.
 */
public class StockMarketMock implements IStockMarket {
  private StringBuilder log;

  /**
   * Constructs a StockMarketMock object with the specified
   * StringBuilder for logging.
   *
   * @param log the StringBuilder to log method calls
   */
  public StockMarketMock(StringBuilder log) {
    this.log = log;
  }

  /**
   * Logs the addition of a stock to the StringBuilder.
   *
   * @param ticker the ticker symbol of the stock to add
   * @param stock  the stock to add
   */
  @Override
  public void addStock(String ticker, IStock stock) {
    this.log.append("addStock (").append(ticker).append(") ");
    return;
  }

  /**
   * Logs the checking of a stock in the StringBuilder and returns false.
   *
   * @param ticker the ticker symbol of the stock to check
   * @return always returns false
   */
  @Override
  public boolean checkStock(String ticker) {
    this.log.append("checkStock (").append(ticker).append(") ");
    return false;
  }

  /**
   * Logs the checking of a portfolio in the StringBuilder and returns false.
   *
   * @param name the name of the portfolio to check
   * @return always returns false
   */
  @Override
  public boolean checkPortfolio(String name) {
    this.log.append("checkPortfolio (").append(name).append(") ");
    return false;
  }

  /**
   * Logs the addition of a portfolio to the StringBuilder.
   *
   * @param name the name of the portfolio to add
   */
  @Override
  public void addPortfolio(String name) {
    this.log.append("addPortfolio (").append(name).append(") ");
    return;
  }

  /**
   * Logs the retrieval of a portfolio from the StringBuilder and
   * returns a new Portfolio object.
   *
   * @param name the name of the portfolio to retrieve
   * @return a new Portfolio object with the specified name
   */
  @Override
  public ISmartPortfolio getPortfolio(String name) {
    this.log.append("getPortfolio (").append(name).append(") ");
    return new SmartPortfolio(name);
  }


  /**
   * Logs the retrieval of portfolios from the StringBuilder and
   * returns an empty HashMap.
   *
   * @return an empty HashMap
   */
  @Override
  public HashMap<String, ISmartPortfolio> getPortfolios() {
    this.log.append("getPortfolios (").append("Got portfolios) ");
    return new HashMap<>();
  }

  /**
   * Logs the retrieval of stocks from the StringBuilder and returns an
   * empty HashMap.
   *
   * @return an empty HashMap
   */
  @Override
  public HashMap<String, IStock> getStocks() {
    this.log.append("getStocks (").append("Got stocks) ");
    return new HashMap<>();
  }

}
