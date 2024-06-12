package command;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import model.ISmartPortfolio;
import model.ISmartStockShares;
import model.IStock;
import view.IView;

public class RebalancePortfolio implements IPortfolioStrategies{
private final IView out;

  public RebalancePortfolio(IView out) {this.out = Objects.requireNonNull(out);}


  @Override
  public void stratGo(Scanner s, ISmartPortfolio portfolio) {

  }



  public void rebalancePortolfio(HashMap<String, Double> targetDist,
                                  ISmartPortfolio portfolio, LocalDate date) throws FileNotFoundException {
    double totalValueOfPortfolio = 0;

    HashMap<String, ISmartStockShares> currentInformation = portfolio.portfolioStateAtDate(date);

    // get the total of the portolfio
    for (Map.Entry<String, ISmartStockShares> entry : currentInformation.entrySet()) {
      IStock stock = entry.getValue().getStock();
      ISmartStockShares shares = entry.getValue();
      double currentValue = stock.getStockValue(date);
      totalValueOfPortfolio += shares.getShares() * currentValue;
    }


    // get the values of stocks
    Map<String, Double> currentPrices  = new HashMap<>();
    Map<String, Double> currentValues = new HashMap<>();
    for (Map.Entry<String, ISmartStockShares> entry : currentInformation.entrySet()) {
      IStock stock = entry.getValue().getStock();
      String ticker = entry.getKey();
      ISmartStockShares shares = entry.getValue();
      double currentPrice = stock.getStockValue(date);
      currentValues.put(ticker, shares.getShares() * currentPrice);
      currentPrices.put(ticker, currentPrice);
    }


    // get the targetValues
    Map<String, Double> targetValues = new HashMap<>();
    for (Map.Entry<String, Double> entry : targetDist.entrySet()) {
      targetValues.put(entry.getKey(), entry.getValue() * totalValueOfPortfolio);
    }


    // detrmine the differents
    Map<String, Double> differences = new HashMap<>();
    for (Map.Entry<String, Double> entry : currentValues.entrySet()) {
      String ticker = entry.getKey();
      differences.put(ticker, entry.getValue() - targetValues.get(ticker));
    }


// perform the rebalancig
    for (Map.Entry<String, Double> entry : differences.entrySet()) {
      String ticker = entry.getKey();
      double differenceTotal = entry.getValue();
      double currentPrice = currentPrices.get(ticker);

      if (differenceTotal > 0) {
        double sellShares = (differenceTotal / currentPrice);
        portfolio.removeStockShare(ticker, sellShares, date);
      }

      if (differenceTotal < 0) {
        double sharesToBuy = (-differenceTotal / currentPrice);
        IStock stock = currentInformation.get(ticker).getStock();
        portfolio.addStockShare(ticker, stock, sharesToBuy, date);
      }
    }
  }

}
