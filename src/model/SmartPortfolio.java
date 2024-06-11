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
   * shares/
   * @param name The name of the smart portfolio created.
   */
  public SmartPortfolio(String name) {
    super(name);
    bought = new HashMap<>();
    sold = new HashMap<>();
    current = new HashMap<>();
  }

  /**
   * Adds the amount of shares for a stock.
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock that shares will be bought for.
   * @param shares the number of shares to be added.
   *  @param date the date that the share was added.
   * @throws FileNotFoundException if the file containing the stock data is not found.
   */
  public void addStockShare(String ticker, IStock stock, int shares, LocalDate date)
          throws FileNotFoundException {
    ISmartStockShares stockShares = new SmartStockShares(shares, stock, date);
    stockShares.setBought(true);

    if(dateCreated == null) { // this shouldnt be else if bc then it wont add it to bought if
      // its the first item
      dateCreated = date;
    }
    if(bought.containsKey(ticker) && date.isBefore(this.getMostRecentTransaction(ticker))) {
      throw new IllegalArgumentException("You cannot add a stock share with a future date"
              + date);
      // is Before?
    }

    if(bought.containsKey(ticker)) {
      bought.get(ticker).add(stockShares);

    }
    else {
      ArrayList<ISmartStockShares> stockSharesArrayList = new ArrayList<>();
      stockSharesArrayList.add(stockShares);
      bought.put(ticker, stockSharesArrayList);
    }
    current = portfolioStateAtDate(LocalDate.now());
    System.out.println("reached");

  }


  /**
   * Removes shares of a stock to the portfolio.
   *
   * @param ticker the ticker symbol of the stock
   * @param stock  the stock to remove shares of
   * @param shares the number of shares to remove
   * @param date the date that the share was removed
   * @throws FileNotFoundException if the file containing the stock data is not found.
   */
  public void removeStockShare(String ticker, IStock stock, int shares, LocalDate date)
          throws FileNotFoundException {


    if(sold.containsKey(ticker) && date.isBefore(this.getMostRecentTransaction(ticker))) {
      throw new IllegalArgumentException("You cannot remove a stock share with a future date"
              // from the past? changed to isBefore
              + date);
    }
    else if(sold.containsKey(ticker)) {
      sold.get(ticker).add(new SmartStockShares(shares, stock, date));

    }
    else {
      ArrayList<ISmartStockShares> stockShare = new ArrayList<>();
      stockShare.add(new SmartStockShares(shares, stock, date));
      sold.put(ticker, stockShare);
    }
    current = portfolioStateAtDate(LocalDate.now());

  }

  /**
   * Determines the current information such as amount of shares for the stocks
   * and the stocks of a portfolio on a specific date.
   * @param date the date that the user would like to obtain the information of the portfolio.
   * @return The portfolio with most update information about the stocks and shares of the portfolio.
   * @throws FileNotFoundException if the file containing the stock data is not found.
   */
  public HashMap<String, ISmartStockShares> portfolioStateAtDate(LocalDate date)
          throws FileNotFoundException {
    HashMap<String, ISmartStockShares> portfolioState = new HashMap<>();
    for(Map.Entry<String, ArrayList<ISmartStockShares>> entry : bought.entrySet()) {
      ISmartStockShares s = stockSharesAtDate(date, entry.getValue());
      if(s.getShares() != 0) {
        portfolioState.put(entry.getKey(), s);
      }
    }
    return portfolioState;
  }


  // A helper method that gets the current amount of shares on specific for the stocks.
  private ISmartStockShares stockSharesAtDate(LocalDate date,
                                        ArrayList<ISmartStockShares> stockSharesList)
          throws FileNotFoundException {
    double sumShares = 0;
    IStock stock = stockSharesList.get(0).getStock();

    for(int i = 0; i < stockSharesList.size(); i++) {
      if(stockSharesList.get(i).getDate().equals(date)
              || stockSharesList.get(i).getDate().isBefore(date)) {

        sumShares += stockSharesList.get(i).getShares();
      }
    }
    ISmartStockShares updatedStockForDate = new SmartStockShares(sumShares, stock, date);
    return updatedStockForDate;
  }

  /**
   *  Gets the total amount of shares of a stock  that were a bought by a user.
   * @return the total of bought shares.
   */
  public Map<String, ArrayList<ISmartStockShares>> getBoughtStockSharesMap() {
    return bought;
  }

  /**
   *  Gets the total amount of shares of a stock that were a sold by a user.
   * @return the total of sold shares.
   */
  public Map<String, ArrayList<ISmartStockShares>> getSoldStockSharesMap() {
    return sold;
  }

  /**
   * Gets the total amount of shares that were owned by a user.
   * @return the total of sold shares.
   */
  public Map<String, ISmartStockShares> getCurrentStockSharesMap() {
    return current;
  }


  /**
   * Sets the total amount of shares owned by a user.
   */
  public void setCurrentStockSharesMap(Map<String,  ISmartStockShares> currentStockSharesMap) {
    this.current = currentStockSharesMap;
  }

  /**
   * Sets the total amount of bought shares  by a user.
   */
  public void setBoughtStockSharesMap(Map<String, ArrayList<ISmartStockShares>> bought) {
    this.bought = bought;
  }

  /**
   * Sets the total amount of sold shares  by a user.
   */
  public void setSoldStockSharesMap(Map<String, ArrayList<ISmartStockShares>> sold) {
    this.sold = sold;
  }

  // This is helper method that allows to get the most recent transaction.
  private LocalDate getMostRecentTransaction(String ticker) throws FileNotFoundException {

    LocalDate mostRecentDate = bought.get(ticker).getFirst().getDate();

    if(bought.containsKey(ticker)) {
      for(ISmartStockShares s : bought.get(ticker)) {
        if(s.getDate().isAfter(mostRecentDate)) {
          mostRecentDate = s.getDate();
        }
      }
      if(sold.containsKey(ticker)) {
        for(ISmartStockShares s : sold.get(ticker)) {
          if (s.getDate().isAfter(mostRecentDate)) {
            mostRecentDate = s.getDate();
          }
        }
      }
    }
    return mostRecentDate;
  }
}
