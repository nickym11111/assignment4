import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import command.ViewStockCommand;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows user to view
 * a stock.
 */
public class ViewStockCommandTest extends ACommandTest {

  public ViewStockCommandTest() throws FileNotFoundException {
    // The reason it is empty because it extends an abstract class
    // where it gets its information from
  }

  @Before
  public void setUp() {
    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new ViewStockCommand(view, s);
  }

  @Test
  public void testRunToViewPortFolios() throws IOException {
    command.run(stockMarket);
    assertEquals("There are 7 stocks, with the following names: \n" +
            "NFLX\n" +
            "META\n" +
            "GOOG\n" +
            "AAPL\n" +
            "NKE\n" +
            "AMZN\n" +
            "MFST\n" +
            "Note: some stocks may not be up to date if they were previously inputted\n" +
            "Enter a 's' to view statistics about a specific stock\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());

  }

  @Test
  public void testRunToViewPortFoliosRandInp() throws IOException {
    in = new StringReader("k\nb\n");
    s = new Scanner(in);
    command = new ViewStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("There are 7 stocks, with the following names: \n" +
            "NFLX\n" +
            "META\n" +
            "GOOG\n" +
            "AAPL\n" +
            "NKE\n" +
            "AMZN\n" +
            "MFST\n" +
            "Note: some stocks may not be up to date if they were previously inputted\n" +
            "Enter a 's' to view statistics about a specific stock\n" +
            "Enter 'b' to go back to previous options\n" +
            "Undefined instruction: k\n" +
            "Enter a 's' to view statistics about a specific stock\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());

  }

  @Test
  public void testRunToViewPortFoliosViewStock() throws IOException {
    in = new StringReader("s\nLOLA\nb\nb\n");
    s = new Scanner(in);
    command = new ViewStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("There are 7 stocks, with the following names: \n" +
            "NFLX\n" +
            "META\n" +
            "GOOG\n" +
            "AAPL\n" +
            "NKE\n" +
            "AMZN\n" +
            "MFST\n" +
            "Note: some stocks may not be up to date if they were previously inputted\n" +
            "Enter a 's' to view statistics about a specific stock\n" +
            "Enter 'b' to go back to previous options\n" +
            "Enter tickerSymbol to view statistics about a stock\n" +
            "That stock has not been inputted\n" +
            "Enter tickerSymbol to view statistics about a stock\n" +
            "Enter 'b' to go back to previous options\n" +
            "Enter a 's' to view statistics about a specific stock\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }
}