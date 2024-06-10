import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

import command.ICommand;
import model.IStockMarket;
import model.StockMarket;
import view.IView;
import view.ViewImpl;

/**
 * An abstract testing class used for the commands in the program.
 */
public class ACommandTest {
  StringBuilder builder = new StringBuilder();
  StringReader in;
  IView view = new ViewImpl(builder);
  Scanner s;
  ICommand command;
  IStockMarket stockMarket = new StockMarket();

  public ACommandTest() throws FileNotFoundException {
    // constructor created to support exception thrown

  }

}