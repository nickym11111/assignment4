import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import command.ViewPortfolioCommand;
import model.StockMarket;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows user to view
 * a portfolio.
 */
public class ViewPortfolioCommandTest extends ACommandTest {

  public ViewPortfolioCommandTest() throws FileNotFoundException {
    // The reason it is empty because it ectends an abstreact class
    // where it gets its information from
  }

  @Before
  public void setUp() {
    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new ViewPortfolioCommand(view, s);
  }


  @Test
  public void testRunToViewPortFoliosOnePrt() throws IOException {
    stockMarket = new StockMarket();
    stockMarket.addPortfolio("nicky");
    command.run(stockMarket);
    assertEquals("There are 1 portfolios, with the following names: \n" +
            "nicky\n" +
            "Enter a 'p' to check value of a portfolio\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());

  }
  // we have 1 portfolio so this test will pass "There are no portfolios" if portfolio is deleted

  @Test
  public void testRunToViewPortFoliosRandInp() throws IOException {
    in = new StringReader("k\nb\n");
    s = new Scanner(in);
    command = new ViewPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("There are 1 portfolios, with the following names: \n" +
            "nicky\n" +
            "Enter a 'p' to check value of a portfolio\n" +
            "Enter 'b' to go back to previous options\n" +
            "Undefined instruction: k\n" +
            "Enter a 'p' to check value of a portfolio\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());

  }

  @Test
  public void testRunToViewPortFoliosPort() throws IOException {
    in = new StringReader("p\nlola\nb\nb\n");
    s = new Scanner(in);
    command = new ViewPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("There are 1 portfolios, with the following names: \n" +
            "nicky\n" +
            "Enter a 'p' to check value of a portfolio\n" +
            "Enter 'b' to go back to previous options\n" +
            "Enter name of portfolio\n" +
            "That portfolio was not found/ has not been inputted\n" +
            "Enter name of portfolio\n" +
            "Enter 'b' to go back to previous options\n" +
            "Enter a 'p' to check value of a portfolio\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());

  }

}