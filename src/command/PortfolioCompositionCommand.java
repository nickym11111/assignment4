package command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

import model.ISmartPortfolio;
import model.ISmartStockShares;
import model.IStockMarket;
import view.IView;

public class PortfolioCompositionCommand extends ACommand{
  private ISmartPortfolio portfolio;

  public PortfolioCompositionCommand(IView view, Scanner scanner, ISmartPortfolio portfolio) {
    super(view, scanner);
    this.portfolio = portfolio;
  }

  @Override
  public void run(IStockMarket stockMarket) throws IOException {

    view.writeMessage("Please enter the date you'd like to get the composition for: (YYYY-MM-DD)"
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
      view.writeMessage("Portfolio does not have composition for a date before its earliest " +
              "purchase" + System.lineSeparator());
      return;
    }

    Map<String, ISmartStockShares> stateOfPort = portfolio.portfolioStateAtDate(start);
    view.writeMessage("The composition of this portfolio at " + start + ":" +
            System.lineSeparator());
    for (Map.Entry<String, ISmartStockShares> entry : stateOfPort.entrySet()) {
      ISmartStockShares stockShare = entry.getValue();
      view.writeMessage(entry.getKey() + " - " + stockShare.getShares() +
              System.lineSeparator());
    }
  }


}
