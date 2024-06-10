package command;

import java.io.IOException;
import java.util.Scanner;

import command.readerbuilder.APIStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.IStock;
import model.IStockMarket;
import view.IView;

/**
 * InputAPIStockCommand is a type of Command. It happens when the user has asked to
 * input a new stock and wants to use the API data. The purpose of this command is to
 * allow a user to create a new stock for the API alpha vantage data. The stock will
 * be hard saved once the user has created it so it can still be accessed even after the program
 * is quit.
 */
public class InputAPIStockCommand extends ACommand {

  boolean inPortfolio = false;
  String portfolioName = "";
  int shares = 0;

  /**
   * InputAPIStockCommand creates a InputAPIStockCommand
   * object with the given fields. It calls on the superclass to initialize the object
   * to hold the respective inputs and outputs (or rather holders of
   * input and output) of the program.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */

  public InputAPIStockCommand(IView view, Scanner s) {
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
  public void setToPortFolio(String name, int shares) {
    this.inPortfolio = true;
    this.portfolioName = name;
    this.shares = shares;
  }

  /**
   * The run method in InputAPIStockCommand will ask the user to give the ticker symbol
   * for the stock company they want. The method will then build a stock
   * from the API source, if its ran out of queries it will notify the user and point them
   * to the backup saved stock data.
   * This command allows the user to create a new stock using API data.
   *
   * @param myStockMarket represents the current stock market for the command to access and
   *                      obtain data from.
   */
  @Override
  public void run(IStockMarket myStockMarket) {
    String userInstruction = "";
    try {
      view.writeMessage("Enter 4 letter capital ticker symbol of desired stock: "
              + System.lineSeparator());
      userInstruction = s.next();
      while (!isTickerSymbolWellFormed(userInstruction)) {
        view.writeMessage("Please enter a valid ticker symbol" + System.lineSeparator());
        userInstruction = s.next();
      }
      IStockDataStream data = new APIStockDataStreamImpl(userInstruction);
      IStockBuilder stockBuilder = new StockBuilderImpl();
      IStock stock = stockBuilder.buildStock(userInstruction, data);
      if (inPortfolio) {
        myStockMarket.getPortfolio(portfolioName).addStockShare(userInstruction, stock, shares);
      } else {
        myStockMarket.addStock(userInstruction, stock);
      }

      view.writeMessage("Successfully entered stock data" + System.lineSeparator());
    } catch (RuntimeException e) {
      view.writeMessage("Error: " + e.getMessage() + System.lineSeparator());
    } catch (IOException e) {
      view.writeMessage("Ran out of API queries, please try again tomorrow." +
              System.lineSeparator());
      view.writeMessage("Instead you can view all saved stocks and input stocks from files."
              + System.lineSeparator());
    }
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

