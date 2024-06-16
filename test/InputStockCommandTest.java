import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import command.InputStockCommand;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that when a user inputs
 * a stocks.
 */
public class InputStockCommandTest extends ACommandTest {


  public InputStockCommandTest() throws FileNotFoundException {
    // constructor created to support exception thrown
  }

  @Before
  public void setUp() {
    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new InputStockCommand(view, s);
  }

  @Test
  public void testOriginalOutputBack() throws IOException {
    command.run(stockMarket);
    assertEquals("How would you like to input your stock data? \n" +
                    "Enter 'f' to input the stock data through a csv file.\n" +
                    "Enter 'a' to input the stock data through our online resource (API).\n" +
                    "Enter 'b' to go back to previous options or view stock data.\n",
            builder.toString());

  }

  @Test
  public void testOriginalOutputFile() throws IOException {
    in = new StringReader("f\nstocks/AMZN.csv\nAMZN\nb\n");
    s = new Scanner(in);
    command = new InputStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("How would you like to input your stock data? \n" +
                    "Enter 'f' to input the stock data through a csv file.\n" +
                    "Enter 'a' to input the stock data through our online resource (API).\n" +
                    "Enter 'b' to go back to previous options or view stock data.\n" +
                    "Please enter your csv file, with the format: \n" +
                    "date (YYYY-MM-DD) (most recent date starting), open price, high price," +
                    " low price, close price, volume\n" +
                    "Please enter the ticker symbol of the stock\n" +
                    "Successfully entered stock data\n" +
                    "How would you like to input your stock data? \n" +
                    "Enter 'f' to input the stock data through a csv file.\n" +
                    "Enter 'a' to input the stock data through our online resource (API).\n" +
                    "Enter 'b' to go back to previous options or view stock data.\n",
            builder.toString());

  }

  @Test
  public void testRandomErrorCase() throws IOException {
    in = new StringReader("k\nb\n");
    s = new Scanner(in);
    command = new InputStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("How would you like to input your stock data? \n" +
                    "Enter 'f' to input the stock data through a csv file.\n" +
                    "Enter 'a' to input the stock data through our online resource (API).\n" +
                    "Enter 'b' to go back to previous options or view stock data.\n" +
                    "Undefined instruction: k\n" +
                    "How would you like to input your stock data? \n" +
                    "Enter 'f' to input the stock data through a csv file.\n" +
                    "Enter 'a' to input the stock data through our online resource (API).\n" +
                    "Enter 'b' to go back to previous options or view stock data.\n",
            builder.toString());

  }
}