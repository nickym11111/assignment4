package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a portfolio containing shares of various stocks with a name.
 */
public class Portfolio implements IPortfolio {
  protected LocalDate dateCreated;
  protected Map<String, StockShares> currentStocksShareMap;
  protected Map<String, ArrayList<StockShares>> bought;
  protected Map<String, ArrayList<StockShares>> sold;
  String name;

  /**
   * Constructs a new Portfolio object with the given name.
   *
   * @param name the name of the portfolio
   */
  public Portfolio(String name) {
    this.name = name;
    this.currentStocksShareMap = new HashMap<>();
  }

  /**
   * Gets the map of ticker symbols to stock shares in the portfolio.
   *
   * @return the map of stock shares
   */
  public Map<String, StockShares> getStocksShareMap() {
    return currentStocksShareMap;
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
    if(bought.isEmpty()) {
      dateCreated = LocalDate.now();
    }
    if(bought.containsKey(ticker)) {
      bought.get(ticker).add(new StockShares(shares, stock));
    }
    else {
      ArrayList<StockShares> stockShare = new ArrayList<>();
      stockShare.add(new StockShares(shares, stock));
      bought.put(ticker, stockShare);
    }
  }


  /**
   * Removes shares of a stock to the portfolio.
   *
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock to remove shares of
   * @param shares the number of shares to remove
   * @throws FileNotFoundException if the file containing the stock data is not found
   */
  public void removeStockShare(String ticker, IStock stock, int shares)
          throws FileNotFoundException {
    if(sold.containsKey(ticker)) {
      sold.get(ticker).add(new StockShares(shares, stock));
    }
    else {
      ArrayList<StockShares> stockShare = new ArrayList<>();
      stockShare.add(new StockShares(shares, stock));
      sold.put(ticker, stockShare);
    }
  }


  public HashMap<String, StockShares> portfolioStateAtDate(LocalDate date)
          throws FileNotFoundException {
    HashMap<String, StockShares> portfolioState = new HashMap<>();
    for(Map.Entry<String, ArrayList<StockShares>> entry : bought.entrySet()) {
      StockShares s = stockSharesAtDate(date, entry.getValue());
      if(s.getShares() != 0) {
        portfolioState.put(entry.getKey(), s);
      }
    }
    return portfolioState;
  }

  private StockShares stockSharesAtDate(LocalDate date,
                                        ArrayList<StockShares> stockSharesList)
          throws FileNotFoundException {
    int sumShares = 0;
    IStock stock = stockSharesList.get(0).getStock();

    for(int i = 0; i < stockSharesList.size(); i++) {
      if(stockSharesList.get(i).getDate().equals(date)
              || stockSharesList.get(i).getDate().isAfter(date)) {

        sumShares += stockSharesList.get(i).getShares();
      }
    }
    StockShares updatedStockForDate = new StockShares(sumShares, stock);
    updatedStockForDate.setDate(date);
    return updatedStockForDate;
  }

  // hello
  private void updateCurrentStock() {
    HashMap<String, StockShares> newCurrentStockShare = new HashMap<>();

    for(Map.Entry<String, StockShares> entry : currentStocksShareMap.entrySet()) {
      String ticker = entry.getKey();
      int currentShares = entry.getValue().getShares();
    }

  }





}



