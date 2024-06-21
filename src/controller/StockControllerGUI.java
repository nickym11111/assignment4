package controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import command.ValuePortfolio;
import command.readerbuilder.APIStockDataStreamImpl;
import command.readerbuilder.SavePortfolioOperation;
import command.readerbuilder.StockBuilderImpl;
import model.ISmartPortfolio;
import model.ISmartStockShares;
import model.IStock;
import model.IStockMarket;
import view.IViewGUI;
import view.IViewListener;

/**
 * StockControllerGUI creates a new StockControllerGUI that accommodates a GUI which has users
 * input data on the interface and saves the data. Additionally, it will the go to the model
 * to update the new date inputted.
 */
public class StockControllerGUI implements IController, IViewListener {
  private final IStockMarket stockMarket;
  private final IViewGUI view;

  /**
   * Constructs a StockControllerGUI object given a IStockMarket and IViewGUI objects.
   * @param stockMarket the object that is being used to be adjusted in the GUI.
   * @param view the object class that provides the physical appearance of the GUI.
   */
  public StockControllerGUI(IStockMarket stockMarket, IViewGUI view) {
    this.stockMarket = Objects.requireNonNull(stockMarket);
    this.view = Objects.requireNonNull(view);
    this.view.addViewListener(this);
  }

  /**
   * Will set the view visible and display the main options for our program such as
   * the header, button options, and the portfolios.
   */
  @Override
  public void goController() {
    view.setVisible(true);
    view.displayHeader();
    view.displayButtonOptions();
    view.displayPort();
  }


  /**
   *  The method will get all the portfolio currently in the stock market to then be displayed to
   *  the view as a button.
   */
  public void getPortfolioButtons() {
    Map<String, ISmartPortfolio> portfolios = this.stockMarket.getPortfolios();
    ArrayList<String> names = new ArrayList<>(portfolios.keySet());
    this.view.setPortfolioButtons(names);
    this.view.requestFocus();
  }


  /**
   * Handles the case when a new portfolio has been created will then add to the stock
   * market and save it.
   * @param name the name of the portfolio.
   */
  public void handleCreatePortfolios(String name) {
    stockMarket.addPortfolio(name);
    new SavePortfolioOperation(stockMarket.getPortfolio(name)).run();
    this.view.requestFocus();
  }

  /**
   * Handles the case when a user would like to buy shares for a stock. Will add the number of
   * shares to that stock and save it to the model. Additionally, it converts dates to Local Date
   * to assure that dates are properly adjusted and correct.
   * @param date the date that shares are being bought on.
   * @param ticker the ticker symbol that represents the stock.
   * @param shares the number of shares being bought.
   * @param portfolio the user's portfolio that has the stock shares.
   * @throws FileNotFoundException when we do not have the portfolio
   *                               or stock inputted in our system.
   */
  @Override
  public void handleBuyStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException {
    ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
    LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    if (p.hasStockAtDate(d, ticker)) {
      p.addExistingStock(ticker, d, shares);

    }
    else {
      APIStockDataStreamImpl apiStockDataStream = new APIStockDataStreamImpl(ticker);
      StockBuilderImpl stockBuilder = new StockBuilderImpl();
      IStock stock = stockBuilder.buildStock(ticker, apiStockDataStream);
      p.addStockShare(ticker, stock, shares, d);

    }
    new SavePortfolioOperation(p).run();
    this.view.requestFocus();
  }

  /**
   * Handles the case when a user would like to sell shares for a stock. Will remove the number of
   * shares to that stock and save it to the model. Additionally, it converts dates to Local Date
   * to assure that dates are properly asjusted and correct.
   * @param date the date that shares are being sold on.
   * @param ticker the ticker symbol that represents the stock.
   * @param shares the number of shares being sold.
   * @param portfolio the user's portfolio that has the stock shares.
   * @throws FileNotFoundException when we do not have the portfolio or
   *                                stock inputted in our system.
   */
  @Override
  public void handleSellStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException {
    ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
    LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    p.removeStockShare(ticker, shares, d);
    new SavePortfolioOperation(p).run();
    this.view.requestFocus();
  }

  /**
   * This method will the get the total value of a portfolio which is calculated using the
   * ValuePortfolio command and set the value in the view.
   * @param date the date requested to get the value.
   * @param portfolio the name of the portfolio requested to get the value.
   */
  @Override
  public void handleGetValue(Date date, String portfolio) {
    ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
    LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    double value = new ValuePortfolio(this.view).getPortfolioValue(d, p);
    this.view.setValue(value, date);
    this.view.requestFocus();
  }

  /**
   * This method will the get the total value of a portfolio which is being built using the
   * by going through the for loop to then create a line of with the Stock and the number of shares
   * currently in the stock.
   * @param date the date requested to get the composition.
   * @param portfolio the name of the portfolio requested to get the composition.
   */
  @Override
  public void handleGetComposition(Date date, String portfolio)
          throws FileNotFoundException {
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
    this.view.setComposition(names, date);
    this.view.requestFocus();
  }


}
