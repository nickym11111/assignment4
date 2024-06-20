package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent a new version of the Portfolio with extended features such
 * as being able to add and sell shares. Additionally, it keeps track of the amount
 * of shares being bought and sold also, keeping track of the current amount of shares
 * for the stock and the date . Also provides methods that allow to obtain and set the
 * information such as  bought, current, and sold shares.
 */
public class SmartPortfolio extends Portfolio implements ISmartPortfolio {
  protected Map<String, ArrayList<ISmartStockShares>> bought;
  protected Map<String, ArrayList<ISmartStockShares>> sold;
  protected Map<String, ISmartStockShares> current;
  protected LocalDate dateCreated;

  /**
   * Construct a smart portfolio and initializes the bought, solid, current
   * shares as well as the date created.
   *
   * @param name The name of the smart portfolio created.
   */
  public SmartPortfolio(String name) {
    super(name);
    bought = new HashMap<>();
    sold = new HashMap<>();
    current = new HashMap<>();
    dateCreated = LocalDate.MIN;
  }

  /**
   * Adds the amount of shares for a stock checking that it's a chronological buy,
   * and proper buy date.
   *
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock that shares will be bought for.
   * @param shares the number of shares to be added.
   * @param date   the date that the share was added.
   * @throws FileNotFoundException if the file containing the stock data is not found.
   */
  public void addStockShare(String ticker, IStock stock, double shares, LocalDate date)
          throws FileNotFoundException {
    ISmartStockShares stockShares = new SmartStockShares(shares, stock, date);
    stockShares.setBought(true);

    if (dateCreated == LocalDate.MIN) {
      dateCreated = date;
    } else if (date.isBefore(dateCreated)) {
      dateCreated = date;
    }
    if (bought.containsKey(ticker) && date.isBefore(this.getMostRecentTransaction(ticker))) {
      throw new IllegalArgumentException("You cannot buy a stock from before the most " +
              "recent transaction :" + this.getMostRecentTransaction(ticker));
    }
    if (stock.getEarliestDate().isAfter(date)) {
      throw new IllegalArgumentException("You cannot " +
              "buy a stock from the stock was ever created");
    }

    if (stock.getMostRecentDate().isBefore(date)) {
      throw new IllegalArgumentException("You cannot buy a stock from the future");
    }

    if (bought.containsKey(ticker)) {
      bought.get(ticker).add(stockShares);

    } else {
      ArrayList<ISmartStockShares> stockSharesArrayList = new ArrayList<>();
      stockSharesArrayList.add(stockShares);
      bought.put(ticker, stockSharesArrayList);
    }
    current = portfolioStateAtDate(LocalDate.now());
  }

  /**
   * Removes shares of a stock to the portfolio given that the stock exists in this portfolio,
   * is a chronological transaction, and has enough to sell.
   *
   * @param ticker the ticker symbol of the stock
   * @param shares the number of shares to remove
   * @param date   the date that the share was removed
   * @throws FileNotFoundException if the file containing the stock data is not found.
   */
  public void removeStockShare(String ticker, double shares, LocalDate date)
          throws FileNotFoundException {
    if (!bought.containsKey(ticker)) {
      throw new IllegalArgumentException("You cannot remove a stock you never bought");
    }
    IStock stock = bought.get(ticker).getFirst().getStock();

    if (bought.containsKey(ticker) && date.isBefore(this.getMostRecentTransaction(ticker))) {
      throw new IllegalArgumentException("You cannot sell a stock from before the most " +
              "recent transaction " + this.getMostRecentTransaction(ticker));
    }

    if (bought.containsKey(ticker) && getTotalSharesOfBought(ticker) < shares) {
      throw new IllegalArgumentException("You cannot sell shares " +
              "you do not own enough shares of this stock");
    }

    if (sold.containsKey(ticker)) {
      sold.get(ticker).add(new SmartStockShares(shares, stock, date));

    } else {
      ArrayList<ISmartStockShares> stockShare = new ArrayList<>();
      stockShare.add(new SmartStockShares(shares, stock, date));
      sold.put(ticker, stockShare);
    }
    current = portfolioStateAtDate(LocalDate.now());


  }

  /**
   * Determines the current information such as amount of shares for the stocks
   * and the stocks of a portfolio on a specific date.
   *
   * @param date
   *     the date that the user would like to obtain
   *     the information of the portfolio.
   * @return
   *     The portfolio with most update information about
   *     the stocks and shares of the portfolio.
   * @throws FileNotFoundException
   *     if the file containing the stock data is not found.
   */
  public Map<String, ISmartStockShares> portfolioStateAtDate(LocalDate date)
          throws FileNotFoundException {
    Map<String, ISmartStockShares> portfolioState = new HashMap<>();
    for (Map.Entry<String, ArrayList<ISmartStockShares>> entry : bought.entrySet()) {
      double boughtStock = stockSharesAtDate(date, entry.getValue());
      if (sold.containsKey(entry.getKey())) {
        double soldStock = stockSharesAtDate(date, sold.get(entry.getKey()));
        boughtStock -= soldStock;
      }
      if (boughtStock != 0) {
        portfolioState.put(entry.getKey(), new SmartStockShares(boughtStock,
                entry.getValue().getFirst().getStock(),
                getMostRecentTransaction(entry.getKey())));
      }
    }
    return portfolioState;
  }


  // A helper method that gets the current amount of shares on specific for the stocks.
  private double stockSharesAtDate(LocalDate date,
                                   ArrayList<ISmartStockShares> stockSharesList)
          throws FileNotFoundException {
    double sumShares = 0;

    for (int i = 0; i < stockSharesList.size(); i++) {
      if (stockSharesList.get(i).getDate().equals(date)
              || stockSharesList.get(i).getDate().isBefore(date)) {

        sumShares += stockSharesList.get(i).getShares();
      }
    }
    return sumShares;
  }

  /**
   * Gets the total amount of shares of a stock  that were a bought by a user.
   *
   * @return the total of bought shares.
   */
  public Map<String, ArrayList<ISmartStockShares>> getBoughtStockSharesMap() {
    return bought;
  }

  /**
   * Sets the total amount of bought shares  by a user.
   */
  public void setBoughtStockSharesMap(Map<String, ArrayList<ISmartStockShares>> bought) {
    this.bought = bought;
  }

  /**
   * Gets the total amount of shares of a stock that were a sold by a user.
   *
   * @return the total of sold shares.
   */
  public Map<String, ArrayList<ISmartStockShares>> getSoldStockSharesMap() {
    return sold;
  }

  /**
   * Sets the total amount of sold shares  by a user.
   */
  public void setSoldStockSharesMap(Map<String, ArrayList<ISmartStockShares>> sold) {
    this.sold = sold;
  }

  /**
   * Gets the total amount of shares that were owned by a user.
   *
   * @return the total of sold shares.
   */
  public Map<String, ISmartStockShares> getCurrentStockSharesMap() {
    return current;
  }

  /**
   * Sets the total amount of shares owned by a user.
   */
  public void setCurrentStockSharesMap(Map<String, ISmartStockShares> currentStockSharesMap) {
    this.current = currentStockSharesMap;
  }

  // This is helper method that allows to get the most recent transaction.
  private LocalDate getMostRecentTransaction(String ticker) throws FileNotFoundException {

    LocalDate mostRecentDate = bought.get(ticker).getFirst().getDate();

    if (bought.containsKey(ticker)) {
      for (ISmartStockShares s : bought.get(ticker)) {
        if (s.getDate().isAfter(mostRecentDate)) {
          mostRecentDate = s.getDate();
        }
      }
      if (sold.containsKey(ticker)) {
        for (ISmartStockShares s : sold.get(ticker)) {
          if (s.getDate().isAfter(mostRecentDate)) {
            mostRecentDate = s.getDate();
          }
        }
      }
    }
    return mostRecentDate;
  }

  /**
   * gets the date created of this portfolio.
   *
   * @return LocalDate dateCreated
   */
  public LocalDate getDateCreated() {
    return dateCreated;
  }

  /**
   * sets the date created of this portfolio.
   *
   * @param dateCreated represents new dateCreated date
   */
  public void setDateCreated(LocalDate dateCreated) {
    this.dateCreated = dateCreated;
  }


  // gets the total bought shares for a stock
  private double getTotalSharesOfBought(String ticker) {
    double totalShares = 0;
    for (int i = 0; i < bought.get(ticker).size(); i++) {
      bought.get(ticker).get(i).getShares();
      totalShares += bought.get(ticker).get(i).getShares();
    }
    return totalShares;
  }

  /**
   * hasStockAtDate checks whether this portfolio has the stock of the given ticker symbol
   * and that the date of the transaction is a date that is within the existing
   * time range of the stock.
   *
   * @param date represents the date of the transaction
   * @param tickerSymbol represents the ticker symbol of the stock being bought
   * @return a boolean representing whether the stock already exists in the portfolio
   * considering the date
   */
  public boolean hasStockAtDate(LocalDate date, String tickerSymbol) {
    if(bought.containsKey(tickerSymbol)) {
      IStock stock = bought.get(tickerSymbol).getFirst().getStock();
      if(date.isAfter(stock.getEarliestDate()) && date.isBefore(stock.getMostRecentDate())) {
        return true;
      }
    }
    return false;
  }

  /**
   * addExistingStock adds a stock to this portfolio that already exists in the portfolio,
   * this method is used when the portfolio already has the stock to prevent recalling the API
   * when unnecessary.
   *
   * @param ticker represents the ticker symbol of the stock
   * @param date represents the date of the buying transaction
   * @param shares represents the shares being bought
   * @throws FileNotFoundException if the file not found for the stock being bought
   */
  public void addExistingStock(String ticker, LocalDate date, double shares) throws FileNotFoundException {
    if(bought.containsKey(ticker) && date.isBefore(this.getMostRecentTransaction(ticker))) {
      throw new IllegalArgumentException("You cannot buy a stock from before the most " +
              "recent transaction :" + this.getMostRecentTransaction(ticker));
    }

    if(bought.containsKey(ticker)) {
      ArrayList<ISmartStockShares> stockSharesList = bought.get(ticker);
      IStock stock = stockSharesList.getFirst().getStock();
      ISmartStockShares newShares = new SmartStockShares(shares, stock, date );
      stockSharesList.add(newShares);
    }
  }


}
