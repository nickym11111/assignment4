package command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

import model.ISmartPortfolio;
import model.ISmartStockShares;
import model.IStockMarket;
import view.IView;

public class PortfolioDistributionCommand extends ACommand {
  ISmartPortfolio portfolio;

  public PortfolioDistributionCommand(IView view, Scanner scanner, ISmartPortfolio portfolio) {
    super(view, scanner);
    this.portfolio = portfolio;
  }

  @Override
  public void run(IStockMarket stockMarket) throws IOException {

    view.writeMessage("Please enter the date you'd like to get the distribution for: (YYYY-MM-DD)"
            + System.lineSeparator());
    LocalDate start = null;
    boolean gotDate = false;
    while (!gotDate) {
      try {
        start = LocalDate.parse(s.next());
        gotDate = true;
      } catch (Exception e) {
        view.writeMessage("Invalid input, enter a valid date" +
                " (YYYY-MM-DD) " + System.lineSeparator());
      }
    }

    if(start.isBefore(portfolio.getDateCreated())) {
      view.writeMessage("Portfolio does not have distribution for a date before its earliest " +
              "purchase" + System.lineSeparator());
      return;
    }

    Map<String, ISmartStockShares> stateOfPort = portfolio.portfolioStateAtDate(start);
    view.writeMessage("The distribution of this portfolio at " + start + ":" +
            System.lineSeparator());
    for (Map.Entry<String, ISmartStockShares> entry : stateOfPort.entrySet()) {
      ISmartStockShares stockShare = entry.getValue();
      try {
        view.writeMessage(entry.getKey() + " - " + stockShare.getShares() *
                stockShare.getStock().getStockValue(start) +
                System.lineSeparator());
      }
      catch (Exception e) {
        view.writeMessage(entry.getKey() + " - " + e.getLocalizedMessage() +
                System.lineSeparator());
      }
    }
  }
}
