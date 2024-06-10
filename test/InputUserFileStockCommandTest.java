import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import command.InputUserFileStockCommand;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that when a user inputs
 * a stock file.
 */
public class InputUserFileStockCommandTest extends ACommandTest {
  public InputUserFileStockCommandTest() throws FileNotFoundException {
    // constructor created to support exception thrown
  }


  @Before
  public void setUp() {
    in = new StringReader("b\nstocks/AMZN.csv\nAMZN");
    s = new Scanner(in);
    command = new InputUserFileStockCommand(view, s);
  }

  @Test
  public void testRunToViewPortFolios() throws IOException {
    command.run(stockMarket);
    assertEquals("Please enter your csv file, with the format: \n" +
            "date (YYYY-MM-DD) (most recent date starting), open price, high price, " +
            "low price, close price, volume\n" +
            "Please enter a CSV file with proper format containing stock information\n" +
            "Please enter the ticker symbol of the stock\n" +
            "Successfully entered stock data\n", builder.toString());
  }

  @Test
  public void testRunToViewPortFoliosNotFound() throws IOException {
    in = new StringReader("b\nstocks/LOLA.csv\nLOLA");
    s = new Scanner(in);
    command = new InputUserFileStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Please enter your csv file, with the format: \n" +
            "date (YYYY-MM-DD) (most recent date starting), open price, high price," +
            " low price, close price, volume\n" +
            "Please enter a CSV file with proper format containing stock information\n" +
            "Please enter the ticker symbol of the stock\n" +
            "File not found, check file name and location\n", builder.toString());
  }

  @Test
  public void testRunToViewPortFoliosJSONentered() throws IOException {
    in = new StringReader("b\nstocks/AMZN.json\nAMZN.csv\nAMZN\n");
    s = new Scanner(in);
    command = new InputUserFileStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Please enter your csv file, with the format: \n" +
            "date (YYYY-MM-DD) (most recent date starting), open price, high price," +
            " low price, close price, volume\n" +
            "Please enter a CSV file with proper format containing stock information\n" +
            "Please enter a CSV file with proper format containing stock information\n" +
            "Please enter the ticker symbol of the stock\n" +
            "File not found, check file name and location\n", builder.toString());
  }

  @Test
  public void testRunToViewPortFoliosWrongTicker() throws IOException {
    in = new StringReader("b\nstocks/AMZN.csv\nAoZN\nAMZN\n");
    s = new Scanner(in);
    command = new InputUserFileStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Please enter your csv file, with the format: \n" +
            "date (YYYY-MM-DD) (most recent date starting), open price, high price," +
            " low price, close price, volume\n" +
            "Please enter a CSV file with proper format containing stock information\n" +
            "Please enter the ticker symbol of the stock\n" +
            "Please enter a valid ticker symbol\n" +
            "Successfully entered stock data\n", builder.toString());
  }


}