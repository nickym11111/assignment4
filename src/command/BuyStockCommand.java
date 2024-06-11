package command;

import java.io.IOException;
import java.util.Scanner;

import model.ISmartPortfolio;
import model.IStockMarket;
import view.IView;

public class BuyStockCommand extends ACommand{
  private ISmartPortfolio portfolio;

  public BuyStockCommand(IView view, Scanner s, ISmartPortfolio portfolio) {
   super(view, s);
   this.portfolio = portfolio;
  }

  @Override
  public void run(IStockMarket stockMarket) throws IOException {

//    view.writeMessage("Is the stock you want to buy currently in your portfolio?" +
//            System.lineSeparator());
//    view.writeMessage("Enter 'y' for yes, 'n' for no: ");
//    String input = s.next();
//    while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
//      view.writeMessage("Please enter 'y' for yes, 'n' for no: ");
//      input = s.next();
//    }
    view.writeMessage("How many shares would you like to buy? " +
              System.lineSeparator());
    view.writeMessage( "(after entering the shares you will be prompted to enter the " +
            "specific stock itself)" + System.lineSeparator());
    boolean valid = false;
    int shares = 0;
    while(!valid) {
      try {
        shares = s.nextInt();
        valid = true;
      } catch (Exception e) {
        view.writeMessage("Please enter a positive whole number.");
      }
    }
    view.writeMessage("What company would you like to buy " + shares + "of?" +
            System.lineSeparator());
    InputAPIStockCommand stock = new InputAPIStockCommand(view, s);
    //stock.setToPortFolio(portfolio.getName(), shares);
    //InputAPIStockCommand.run(stockMarket);

  }
}
