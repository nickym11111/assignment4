package command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import model.ISmartPortfolio;
import model.ISmartStockShares;
import model.IStockMarket;
import view.IView;

/**
 * The PortfolioDistributionCommand class is part of a financial application and
 * lets users see how their investment portfolio is distributed on a specific date.
 * It asks users for a date input and then shows the distribution of their portfolio,
 * including the value of each stock they own. If the chosen date is before the
 * portfolio's earliest purchase, it tells users that distribution data isn't
 * available for those dates. This command gives users a snapshot of their
 * portfolio's state and value at any chosen date.
 */
public class PortfolioDistributionCommand extends ACommand {
  ISmartPortfolio portfolio;

  /**
   * Constructs a new PortfolioDistributionCommand with
   * the given view, scanner, and portfolio.
   *
   * @param view      The view associated with this command.
   * @param scanner   The scanner object for user input.
   * @param portfolio The portfolio for which the distribution will be calculated.
   */
  public PortfolioDistributionCommand(IView view, Scanner scanner, ISmartPortfolio portfolio) {
    super(view, scanner);
    this.portfolio = portfolio;
  }

  /**
   * Prompts the user to input a date and then displays the distribution of the portfolio,
   * including the value of each stock, on that specific date. If the chosen date is before
   * the portfolio's earliest purchase date, it informs the user that distribution data
   * isn't available for those dates.
   *
   * @param stockMarket The stock market from which to retrieve stock data.
   * @throws IOException If an I/O error occurs.
   */
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

    if (start.isBefore(portfolio.getDateCreated())) {
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
        view.writeMessage(entry.getKey() + " - " + "$" + stockShare.getShares() *
                stockShare.getStock().getStockValue(start) +
                System.lineSeparator());
      } catch (Exception e) {
        view.writeMessage(entry.getKey() + " - " + e.getLocalizedMessage() +
                System.lineSeparator());
      }
    }
  }
}
