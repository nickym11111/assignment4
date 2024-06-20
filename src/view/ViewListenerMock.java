package view;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import command.ValuePortfolio;
import command.readerbuilder.APIStockDataStreamImpl;
import command.readerbuilder.StockBuilderImpl;
import model.ISmartPortfolio;
import model.ISmartStockShares;
import model.IStock;
import model.StockMarket;


/**
 * A mock class for testing purposes.
 */
public class ViewListenerMock implements IViewListener {
  private StringBuilder log;
  private IViewGUI view = new ViewGUIImpl();
  private StockMarket stockMarket = new StockMarket();

  public ViewListenerMock(StringBuilder log) throws FileNotFoundException {
    this.log = log;
  }


  /**
   *  The method will get all the portfolio currently in the stock market to then be displayed to
   *  the view as a button.
   */
  @Override
  public void getPortfolioButtons() {
    this.log.append("Get portfolio buttons received");
  }


  /**
   * Handles the case when a new portfolio has been created will then add to the stock
   * market and save it.
   * @param name the name of the portfolio.
   */
  @Override
  public void handleCreatePortfolios(String name) {
    this.log.append("New portfolio created: ").append(name);
  }

  /**
   * Handles the case when a user would like to buy shares for a stock. Will add the number of
   * shares to that stock and save it to the model. Additionally, it converts dates to Local Date
   * to assure that dates are properly asjusted and correct.
   * @param date the date that shares are being bought on.
   * @param ticker the ticker symbol that represents the stock.
   * @param shares the number of shares being bought.
   * @param portfolio the user's portfolio that has the stock shares.
   * @throws FileNotFoundException when the system does not have the portfolio or stock inputted in our system.
   */
  @Override
  public void handleBuyStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException {

    try {
      ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
      LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      APIStockDataStreamImpl apiStockDataStream = new APIStockDataStreamImpl(ticker);
      StockBuilderImpl stockBuilder = new StockBuilderImpl();
      IStock stock = stockBuilder.buildStock(ticker, apiStockDataStream);

      p.addStockShare(ticker, stock, shares, d);
      this.log.append("The current date is: ").append(date)
              .append(", the current symbol is:  ")
              .append(ticker).append(", the current number of being bought shares is: ")
              .append(shares).append(", the portfolio is: ")
              .append(portfolio).append("\n");
    } catch (Exception e) {
      log.append(e.getMessage());
    }

  }

  /**
   * Handles the case when a user would like to sell shares for a stock. Will remove the number of
   * shares to that stock and save it to the model. Additionally, it converts dates to Local Date
   * to assure that dates are properly asjusted and correct.
   * @param date the date that shares are being sold on.
   * @param ticker the ticker symbol that represents the stock.
   * @param shares the number of shares being sold.
   * @param portfolio the user's portfolio that has the stock shares.
   * @throws FileNotFoundException when the system does not have the portfolio or stock inputted in our system.
   */
  @Override
  public void handleSellStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException {
    try {
      ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
      LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      p.removeStockShare(ticker, shares, d);
      this.log.append("The current date is: ").append(date)
              .append(", the current symbol is:  ")
              .append(ticker).append(", the current number of shares being sold is: ")
              .append(shares).append(", the portfolio is: ")
              .append(portfolio).append("\n");
    } catch (Exception e) {
      log.append(e.getMessage());
    }

  }

  /**
   * This method will the get the total value of a portfolio which is calculated using the
   * ValuePortfolio command and set the value in the view.
   * @param date the date requested to get the value.
   * @param portfolio the name of the portfolio requested to get the value.
   */
  @Override
  public void handleGetValue(Date date, String portfolio) {

    try {
      ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
      LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      double value = new ValuePortfolio(this.view).getPortfolioValue(d, p);
      this.log.append("The current date is for the value of portfolio being found on: ").append(date)
              .append(",this is the value for the portfolio: ")
              .append(portfolio).append("\n").append(value);
    } catch (Exception e) {
      log.append(e.getMessage());
    }


  }

  /**
   * This method will the get the total value of a portfolio which is being built using the
   * by going through the for loop to then create a line of with the Stock and the number of shares
   * currently in the stock.
   * @param date the date requested to get the composition.
   * @param portfolio the name of the portfolio requested to get the composition.
   */
  @Override
  public void handleGetComposition(Date date, String portfolio) throws FileNotFoundException {
    ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
    LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    Map<String, ISmartStockShares> port = p.portfolioStateAtDate(d);
    ArrayList<String> names = new ArrayList<>();
    for (Map.Entry<String, ISmartStockShares> entry : port.entrySet()) {
      ISmartStockShares s = entry.getValue();
      String name = entry.getKey();
      double shares = s.getShares();
      String line = name + ": " + shares;
      names.add(line);

    }
    this.log.append("The current date is for the composition of " +
                    "the portfolio being found on: ").append(date)
            .append(", this is the composition for the portfolio: ")
            .append(portfolio).append("\n").append(names);

  }

}
