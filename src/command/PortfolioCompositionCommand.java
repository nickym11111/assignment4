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
 * The PortfolioCompositionCommand class represents a command to display the composition
 * of a portfolio at a specific date. This command prompts the user to input a date
 * and then retrieves the composition of the portfolio at that date.
 * It ensures that the entered date is valid and not before the earliest purchase
 * date of the portfolio. The composition includes the number of shares for each
 * stock in the portfolio at the specified date.
 */
public class PortfolioCompositionCommand extends ACommand {
  private ISmartPortfolio portfolio;

  /**
   * Constructs a PortfolioCompositionCommand object.
   *
   * @param view      The view to display messages.
   * @param scanner   The scanner to read user input.
   * @param portfolio The portfolio for which to display the composition.
   */
  public PortfolioCompositionCommand(IView view, Scanner scanner, ISmartPortfolio portfolio) {
    super(view, scanner);
    this.portfolio = portfolio;
  }

  /**
   * Runs the PortfolioCompositionCommand, prompting the user to input a date and then
   * displaying the composition of the portfolio at that date.
   *
   * @param stockMarket The stock market from which to retrieve stock data.
   * @throws IOException If an input or out error occurs during the system.
   */
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
    if (start.isBefore(portfolio.getDateCreated())) {
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
