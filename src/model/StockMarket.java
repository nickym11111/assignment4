package model;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Represents a stock market that manages stocks and portfolios.
 */
public class StockMarket implements IStockMarket {
  private HashMap<String, IStock> stocks;
  private HashMap<String, IPortfolio> portfolios;

  /**
   * Constructs a new StockMarket object.
   *
   * @throws FileNotFoundException if data initialization fails
   */
  public StockMarket() throws FileNotFoundException {
    stocks = new HashMap<>();
    portfolios = new HashMap<>();

    ModelUtil m = new ModelUtil();
    m.initializeData(this);
  }

  /**
   * Adds a stock to the stock market.
   *
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock to add
   */
  public void addStock(String ticker, IStock stock) {
    stocks.put(ticker, stock);
  }

  /**
   * Checks if a stock with the given ticker symbol exists in the stock market.
   *
   * @param ticker the ticker symbol to check
   * @return true if the stock exists, otherwise false
   */
  public boolean checkStock(String ticker) {
    return stocks.containsKey(ticker);
  }

  /**
   * Checks if a portfolio with the given name exists in the stock market.
   *
   * @param name the name of the portfolio to check
   * @return true if the portfolio exists, otherwise false
   */
  public boolean checkPortfolio(String name) {
    return portfolios.containsKey(name);
  }

  /**
   * Adds a portfolio to the stock market.
   *
   * @param name the name of the portfolio to add
   */
  public void addPortfolio(String name) {
    portfolios.put(name, new Portfolio(name));
  }

  /**
   * Gets the portfolio with the given name from the stock market.
   *
   * @param name the name of the portfolio to get
   * @return the portfolio with the given name
   */
  public IPortfolio getPortfolio(String name) {
    return portfolios.get(name);
  }

  /**
   * Gets all portfolios in the stock market.
   *
   * @return a map of portfolio names to portfolios
   */
  public HashMap<String, IPortfolio> getPortfolios() {
    return portfolios;
  }

  /**
   * Gets all stocks in the stock market.
   *
   * @return a map of ticker symbols to stocks
   */
  public HashMap<String, IStock> getStocks() {
    return stocks;
  }
}
