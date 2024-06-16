package command;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import command.readerbuilder.SavePortfolioOperation;
import model.ISmartPortfolio;
import model.ISmartStockShares;
import model.IStock;
import view.IView;

/**
 * The RebalancePortfolio class allows the user to rebalance their investment portfolio by
 * redistributing their investments according to specified target percentages. This class
 * provides a method to gather user input for the target distribution and the date for
 * rebalancing. It then calculates the necessary buy or sell transactions to adjust the
 * portfolio to the desired state. The class also ensures that the total target distribution
 * does not exceed 100% and that the number of stocks in the portfolio matches the number of
 * stocks specified in the target distribution. Detailed information about the rebalanced
 * portfolio is then displayed to the user.
 */
public class RebalancePortfolio implements IPortfolioStrategies {
  private final IView out;

  /**
   * Constructs a RebalancePortfolio instance with the specified view for output.
   *
   * @param out the view for displaying messages
   */
  public RebalancePortfolio(IView out) {
    this.out = Objects.requireNonNull(out);
  }


  /**
   * Executes the rebalancing strategy by taking user input for date and target
   * percentages, and performing the rebalancing process.
   *
   * @param s         the scanner to read user input.
   * @param portfolio the portfolio to be rebalanced.
   */
  @Override
  public void stratGo(Scanner s, ISmartPortfolio portfolio) {
    try {
      LocalDate date = LocalDate.MIN;
      boolean gotDate = false;
      while (!gotDate) {
        try {
          date = LocalDate.parse(s.next());
          gotDate = true;
        } catch (Exception e) {
          out.writeMessage("Invalid input, please enter a valid date " +
                  "(YYYY-MM-DD): " + System.lineSeparator());
        }
      }
      if (date.isBefore(portfolio.getDateCreated())) {
        out.writeMessage("Hello, the portfolio, " + portfolio.getName()
                + " was created on "
                + portfolio.getDateCreated() + System.lineSeparator()
                + "You have inputted the wrong date. Here is our menu please " +
                "try again. " + System.lineSeparator());
        return;
      }

      HashMap<String, Double> redistributionMap = new HashMap<>();
      Map<String, ISmartStockShares> stateOfPort = portfolio.portfolioStateAtDate(date);
      out.writeMessage("Your portfolio at this time has " + stateOfPort.size() + " stocks"
              + System.lineSeparator());
      for (Map.Entry<String, ISmartStockShares> entry : stateOfPort.entrySet()) {
        out.writeMessage("Please enter the percentage of as a decimal: " + entry.getKey()
                + System.lineSeparator());
        double percentage;
        if (s.hasNextDouble()) {
          percentage = s.nextDouble();
        } else {
          while (!s.hasNextDouble()) {
            out.writeMessage("Invalid percentage value for " + entry.getKey() +
                    System.lineSeparator());
            s.next();
          }
          percentage = s.nextDouble();
        }
        redistributionMap.put(entry.getKey(), percentage);
      }
      double sum = 0;
      for (Map.Entry<String, Double> entry : redistributionMap.entrySet()) {
        sum += entry.getValue();
      }
      if (sum != 1.0) {
        out.writeMessage("Sum of redistributions is: " + sum + System.lineSeparator());
        out.writeMessage("Sum of redistributions must be 1 to rebalance, unable to rebalance " +
                "try again" + System.lineSeparator());
        return;
      }
      rebalancePortfolio(redistributionMap, portfolio, date);
      String newInfo = printInfo(portfolio, date);
      new SavePortfolioOperation(portfolio).run();
      out.writeMessage("Portfolio rebalance completed!" +
              System.lineSeparator() + newInfo + System.lineSeparator());

    } catch (Exception e) {
      out.writeMessage("Error: invalid date, ensure the date is in chronological order of buying," +
              "selling, rebalances, and within the existence of this stock."
              + System.lineSeparator());
    }
  }

  /**
   * Balances the portfolio based on the target distribution provided.
   *
   * @param targetDist the target distribution map with ticker symbols and percentages
   * @param portfolio  the portfolio to be rebalanced
   * @param date       the date at which to rebalance the portfolio
   * @throws FileNotFoundException if the stock data file is not found
   */
  public void rebalancePortfolio(HashMap<String, Double> targetDist,
                                 ISmartPortfolio portfolio, LocalDate date)
          throws FileNotFoundException {
    double totalPercentage = 0;
    for (double percentage : targetDist.values()) {
      totalPercentage += percentage;
    }
    if (totalPercentage != 1.0) {
      throw new IllegalArgumentException("Please look at target distribution for " +
              "each stock. Target distribution " +
              "percentages cannot go over 1.0 (100%) or under 1.0 (100%). "
              + System.lineSeparator() + "The distribution must equal " +
              "to 1.0 (100%) added all together." + System.lineSeparator());
    }

    int totalStocks = portfolio.getCurrentStockSharesMap().size();
    if (totalStocks != targetDist.size()) {
      throw new IllegalArgumentException("The number of stocks in the portfolio " +
              "does not match the size of the target distribution");
    }
    Map<String, ISmartStockShares> currentInformation = portfolio.portfolioStateAtDate(date);
    double totalValueOfPortfolio = calculateTotalValueOfPortfolio(currentInformation, date);
    Map<String, Double> currentPrices = getCurrentPrices(currentInformation, date);
    Map<String, Double> currentValues = getCurrentValues(currentInformation, date);
    Map<String, Double> targetValues = getTargetValues(targetDist, totalValueOfPortfolio);
    Map<String, Double> differences = calculateDifferences(currentValues, targetValues);
    performRebalancing(differences, currentPrices, currentInformation, portfolio, date);
  }

  // This private methods gets total value of the given portfolio to determine the total
  // amount of money that can be used to rebalance portfolio.
  private double calculateTotalValueOfPortfolio(Map<String, ISmartStockShares>
                                                        currentInformation, LocalDate date) {
    double totalValue = 0;
    for (ISmartStockShares shares : currentInformation.values()) {
      IStock stock = shares.getStock();
      double currentValue = stock.getStockValue(date);
      totalValue += shares.getShares() * currentValue;
    }
    return totalValue;
  }

  // This is private method gets the current price for a stock to determine the amount
  // in dollars used to buy or sell the shares.
  private Map<String, Double> getCurrentPrices(Map<String, ISmartStockShares>
                                                       currentInformation, LocalDate date) {
    Map<String, Double> currentPrices = new HashMap<>();
    for (Map.Entry<String, ISmartStockShares> entry : currentInformation.entrySet()) {
      IStock stock = entry.getValue().getStock();
      String ticker = entry.getKey();
      double currentPrice = stock.getStockValue(date);
      currentPrices.put(ticker, currentPrice);
    }
    return currentPrices;
  }

  // This private gets the total price of the stocks based on how many shares
  // owned of that stock to then be used to determine what the target
  // values will now be based off the current values.
  private Map<String, Double> getCurrentValues(Map<String, ISmartStockShares> currentInformation,
                                               LocalDate date) {
    Map<String, Double> currentValues = new HashMap<>();
    for (Map.Entry<String, ISmartStockShares> entry : currentInformation.entrySet()) {
      IStock stock = entry.getValue().getStock();
      String ticker = entry.getKey();
      double currentPrice = stock.getStockValue(date);
      currentValues.put(ticker, entry.getValue().getShares() * currentPrice);
    }
    return currentValues;
  }

  // This private methods determine the target values based of on how much the
  // user would like to rebalance their portfolio.
  private Map<String, Double> getTargetValues(Map<String, Double> targetDist,
                                              double totalValueOfPortfolio) {
    Map<String, Double> targetValues = new HashMap<>();
    for (Map.Entry<String, Double> entry : targetDist.entrySet()) {
      targetValues.put(entry.getKey(), entry.getValue() * totalValueOfPortfolio);
    }
    return targetValues;
  }

  // This private method calculates the difference between the target values and the current
  // values to determine the new values of the portfolio.
  private Map<String, Double> calculateDifferences(Map<String, Double> currentValues,
                                                   Map<String, Double> targetValues) {
    Map<String, Double> differences = new HashMap<>();
    for (Map.Entry<String, Double> entry : currentValues.entrySet()) {

      String ticker = entry.getKey();
      differences.put(ticker, entry.getValue() - targetValues.get(ticker));

    }

    return differences;
  }

  // This private performs the balancing based of the current price of stocks,
  // the prices of the stock, of the portoflio, and the date.
  private void performRebalancing(Map<String, Double> differences,
                                  Map<String, Double> currentPrices,
                                  Map<String, ISmartStockShares> currentInformation,
                                  ISmartPortfolio portfolio,
                                  LocalDate date)
          throws FileNotFoundException {
    for (Map.Entry<String, Double> entry : differences.entrySet()) {
      String ticker = entry.getKey();
      double differenceTotal = entry.getValue();
      double currentPrice = currentPrices.get(ticker);
      if (differenceTotal > 0) {
        double sellShares = Math.floor(differenceTotal / currentPrice * 100) / 100;
        portfolio.removeStockShare(ticker, sellShares, date);
      }

      if (differenceTotal < 0) {
        double sharesToBuy = Math.floor(-differenceTotal / currentPrice * 100) / 100;
        IStock stock = currentInformation.get(ticker).getStock();
        portfolio.addStockShare(ticker, stock, sharesToBuy, date);
      }

    }
  }

  // This private method just prints information of the stock to then be outputted to
  // the user.
  private String printInfo(ISmartPortfolio portfolio, LocalDate start)
          throws FileNotFoundException {
    Map<String, ISmartStockShares> stateOfPort = portfolio.portfolioStateAtDate(start);
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, ISmartStockShares> entry : stateOfPort.entrySet()) {
      ISmartStockShares stockShare = entry.getValue();
      sb.append(entry.getKey()).append(" - ").append(stockShare.getShares())
              .append(System.lineSeparator());
    }

    return sb.toString();
  }

}
