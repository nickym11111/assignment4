package command;

import java.time.LocalDate;
import java.util.Scanner;

import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.IStock;
import model.IStockMarket;
import view.IView;

/**
 * InputUserFileStockCommand is a type of Command. It happens when the user has asked to
 * input a new stock and wants to use their file data. The purpose of this command is to
 * allow a user to create a new stock from their files.
 */
public class InputUserFileStockCommand extends ACommand {

  boolean inPortfolio = false;
  String portfolioName = "";
  int shares = 0;
  LocalDate date = LocalDate.now();

  /**
   * InputUserFileStockCommand creates a InputUserFileStockCommand
   * object with the given fields. It calls on the superclass to initialize the object
   * to hold the respective inputs and outputs (or rather holders of
   * input and output) of the program.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */
  public InputUserFileStockCommand(IView view, Scanner s) {
    super(view, s);
  }

  /**
   * setToPortFolio sets the portfolio related fields to their respective values.
   * This method is used when a stock is being inputted into a portfolio as well
   * as being created for the first time.
   *
   * @param name   represents the name of the portfolio
   * @param shares represents the shares of the portfolio
   */
  public void setToPortFolio(String name, int shares, LocalDate date) {
    this.inPortfolio = true;
    this.portfolioName = name;
    this.shares = shares;
    this.date = date;
  }

  /**
   * The run method in InputUserFileStockCommand will ask the user to give the ticker symbol
   * and the csv from which they want to make a stock for. The method will then build a stock
   * from the file source, if the file cannot be found it will notify the user.
   * This command allows the user to create a new stock using file data.
   *
   * @param myStockMarket represents the current stock market for the command to access and
   *                      obtain data from.
   */
  public void run(IStockMarket myStockMarket) {
    printFileStockInputOptions();
    String userInstruction = s.next();
    while (!userInstruction.contains(".csv")) {
      view.writeMessage("Please enter a CSV file with proper format containing stock information"
              + System.lineSeparator());
      userInstruction = s.next();
    }
    view.writeMessage("Please enter the ticker symbol of the stock" + System.lineSeparator());

    String tickerSymbol = s.next();
    while (!isTickerSymbolWellFormed(tickerSymbol)) {
      view.writeMessage("Please enter a valid ticker symbol" + System.lineSeparator());
      tickerSymbol = s.next();
    }
    try {
      IStockDataStream data = new FileStockDataStreamImpl(userInstruction);
      IStockBuilder stockBuilder = new StockBuilderImpl();
      IStock stock = stockBuilder.buildStock(tickerSymbol, data);
      if (inPortfolio) {
        myStockMarket.getPortfolio(portfolioName).addStockShare(tickerSymbol, stock, shares, date);
      } else {
        myStockMarket.addStock(tickerSymbol, stock);
      }
      view.writeMessage("Successfully entered stock data" + System.lineSeparator());
    } catch (Exception e) {
      view.writeMessage("File not found, check file name and location" + System.lineSeparator());
    }
  }

  // prints the message for how the csv file should be formatted
  private void printFileStockInputOptions() throws IllegalStateException {
    view.writeMessage("Please enter your csv file, with the format: " + System.lineSeparator());
    view.writeMessage("date (YYYY-MM-DD) (most recent date starting), open price, high price," +
            " low price, " +
            "close price, volume" + System.lineSeparator());

  }

  // checks that the ticker symbol is well formatted
  private boolean isTickerSymbolWellFormed(String tickerSymbol) {
    return isUpperCase(tickerSymbol);
  }

  // checks that  string is all upper case
  private boolean isUpperCase(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (!Character.isUpperCase(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }

}
