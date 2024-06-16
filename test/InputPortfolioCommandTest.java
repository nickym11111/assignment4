import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import command.InputPortfolioCommand;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that when a user inputs
 * a portfolio.
 */
public class InputPortfolioCommandTest extends ACommandTest {

  public InputPortfolioCommandTest() throws FileNotFoundException {
    // constructor created to support exception thrown
  }

  @Before
  public void setUp() {
    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new InputPortfolioCommand(view, s);
  }

  @Test
  public void testInputPortfolioGoBack() throws IOException {
    command.run(stockMarket);
    assertEquals("Enter 'p' to create another portfolio\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testInputPortfolioDefaultError() throws IOException {
    in = new StringReader("lol\nb\n");
    s = new Scanner(in);
    command = new InputPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter 'p' to create another portfolio\n" +
            "Enter 'b' to go back to previous options\n" +
            "Undefined instruction: lol\n" +
            "Enter 'p' to create another portfolio\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testInputPortfolioEnterP() throws IOException {
    in = new StringReader("p\nrandom\n1\n1\n2024-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new InputPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter 'p' to create another portfolio\n" +
            "Enter 'b' to go back to previous options\n" +
            "What would you like to name your portfolio? \n" +
            "How would many different stocks (different companies) " +
            "would you like to input into your portfolio? \n" +
            "First input how many shares you'd like for stock 1\n" +
            "(after this you will be ask to enter the specific stock itself). \n" +
            "So please put the shares for this stock: \n" +
            "Please enter the date you'd like to buy this stock on: (YYYY-MM-DD)\n" +
            "How would you like to input your stock data? \n" +
            "Enter 'f' to input the stock data through a csv file.\n" +
            "Enter 'a' to input the stock data through our online resource (API).\n" +
            "Enter 'b' to go back to previous options or view stock data.\n" +
            "Successfully entered portfolio data\n" +
            "Enter 'p' to create another portfolio\n" +
            "Enter 'b' to go back to previous options" +
            System.lineSeparator(), builder.toString());
  }


}