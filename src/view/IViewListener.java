package view;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * IViewListener  accommodates a GUI which has user input data on this interface
 * will listen to user inputs and saves the data. Additionally, it will the go to the model
 * to update the new date inputted.
 */
public interface IViewListener {


  /**
   *  The method will get all the portfolio currently in the stock market to then be displayed to
   *  the view as a button.
   */
  void getPortfolioButtons();

  /**
   * Handles the case when a new portfolio has been created will then add to the stock
   * market and save it.
   * @param name the name of the portfolio.
   */
  void handleCreatePortfolios(String name);

  /**
   * Handles the case when a user would like to buy shares for a stock. Will add the number of
   * shares to that stock and save it to the model. Additionally, it converts dates to Local Date
   * to assure that dates are properly asjusted and correct.
   * @param date the date that shares are being bought on.
   * @param ticker the ticker symbol that represents the stock.
   * @param shares the number of shares being bought.
   * @param portfolio the user's portfolio that has the stock shares.
   * @throws FileNotFoundException when the system does not have the portfolio
   *                               or stock inputted in our system.
   */
  void handleBuyStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException;

  /**
   * Handles the case when a user would like to sell shares for a stock. Will remove the number of
   * shares to that stock and save it to the model. Additionally, it converts dates to Local Date
   * to assure that dates are properly asjusted and correct.
   * @param date the date that shares are being sold on.
   * @param ticker the ticker symbol that represents the stock.
   * @param shares the number of shares being sold.
   * @param portfolio the user's portfolio that has the stock shares.
   * @throws FileNotFoundException when the system does not have the
   *                               portfolio or stock inputted in our system.
   */
  void handleSellStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException;


  /**
   * This method will the get the total value of a portfolio which is calculated using the
   * ValuePortfolio command and set the value in the view.
   * @param date the date requested to get the value.
   * @param portfolio the name of the portfolio requested to get the value.
   */
  void handleGetValue(Date date, String portfolio);

  /**
   * This method will the get the total value of a portfolio which is being built using the
   * by going through the for loop to then create a line of with the Stock and the number of shares
   * currently in the stock.
   * @param date the date requested to get the composition.
   * @param portfolio the name of the portfolio requested to get the composition.
   */
  void handleGetComposition(Date date, String portfolio) throws FileNotFoundException;

}