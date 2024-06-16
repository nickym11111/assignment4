import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import command.CreatePortfolioCommand;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that when a user creates
 * a portfolio.
 */
public class CreatePortfolioCommandTest extends ACommandTest {

  public CreatePortfolioCommandTest() throws FileNotFoundException {
    // constructor created to support exception thrown
  }

  @Before
  public void setUp() {
    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new CreatePortfolioCommand(view, s);
  }

  @Test
  public void testCreatetPortfolioGoBack() throws IOException {
    in = new StringReader("nicky\n1\n1" +
            "\n2024-05-01\nf\nstocks/NKE.csv\nNKE\nb\n");
    s = new Scanner(in);
    command = new CreatePortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("What would you like to name your portfolio? \n" +
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
            "Please enter your csv file, with the format: \n" +
            "date (YYYY-MM-DD) (most recent date starting), open price, " +
            "high price, low price, close price, volume\n" +
            "Please enter the ticker symbol of the stock\n" +
            "Successfully entered stock data\n" +
            "Successfully entered portfolio data" +
            System.lineSeparator(), builder.toString());
  }


  @Test
  public void testCreatetPortfolioMoreThanOnePorfolio() throws IOException {
    in = new StringReader("nicky\n2\n2\n2024-05-01\nf" +
            "\nstocks/AMZN.csv\nAMZN\n4\n2024-05-01\nstocks" +
            "/GOOG.csv\nGOOG\nb\n");
    s = new Scanner(in);
    command = new CreatePortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("What would you like to name your portfolio? \n" +
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
            "Please enter your csv file, with the format: \n" +
            "date (YYYY-MM-DD) (most recent date starting), open price, " +
            "high price, low price, close price, volume\n" +
            "Please enter the ticker symbol of the stock\n" +
            "Successfully entered stock data\n" +
            "First input how many shares you'd like for stock 2\n" +
            "(after this you will be ask to enter the specific stock itself). \n" +
            "So please put the shares for this stock: \n" +
            "Please enter the date you'd like to buy this stock on: (YYYY-MM-DD)\n" +
            "How would you like to input your stock data? \n" +
            "Enter 'f' to input the stock data through a csv file.\n" +
            "Enter 'a' to input the stock data through our online resource (API).\n" +
            "Enter 'b' to go back to previous options or view stock data.\n" +
            "Undefined instruction: stocks/GOOG.csv\n" +
            "How would you like to input your stock data? \n" +
            "Enter 'f' to input the stock data through a csv file.\n" +
            "Enter 'a' to input the stock data through our online resource (API).\n" +
            "Enter 'b' to go back to previous options or view stock data.\n" +
            "Undefined instruction: GOOG\n" +
            "How would you like to input your stock data? \n" +
            "Enter 'f' to input the stock data through a csv file.\n" +
            "Enter 'a' to input the stock data through our online resource (API).\n" +
            "Enter 'b' to go back to previous options or view stock data.\n" +
            "Successfully entered portfolio data" +
            System.lineSeparator(), builder.toString());
  }

  @Test
  public void testCreatetPortfolioFileNotFound() throws IOException {
    in = new StringReader("nicky\n1\n1\n2024-05-01\nf\nstocks/AMJN.csv\nAMZN\nb\n");
    s = new Scanner(in);
    command = new CreatePortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("What would you like to name your portfolio? \n" +
                    "How would many different stocks (different companies) would you like" +
                    " to input into your portfolio? \n" +
                    "First input how many shares you'd like for stock 1\n" +
                    "(after this you will be ask to enter the specific stock itself). \n" +
                    "So please put the shares for this stock: \n" +
                    "Please enter the date you'd like to buy this stock on: (YYYY-MM-DD)\n" +
                    "How would you like to input your stock data? \n" +
                    "Enter 'f' to input the stock data through a csv file.\n" +
                    "Enter 'a' to input the stock data through our online resource (API).\n" +
                    "Enter 'b' to go back to previous options or view stock data.\n" +
                    "Please enter your csv file, with the format: \n" +
                    "date (YYYY-MM-DD) (most recent date starting), open price, high price," +
                    " low price, close price, volume\n" +
                    "Please enter the ticker symbol of the stock\n" +
                    "File not found, check file name and location\n" +
                    "Successfully entered portfolio data\n"
            , builder.toString());
  }


  @Test
  public void testCreatetPortfolioBackInTheMiddle() throws IOException {
    in = new StringReader("nicky\n1\n1\n2024-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CreatePortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("What would you like to name your portfolio? \n" +
            "How would many different stocks (different companies) would you like to " +
            "input into your portfolio? \n" +
            "First input how many shares you'd like for stock 1\n" +
            "(after this you will be ask to enter the specific stock itself). \n" +
            "So please put the shares for this stock: \n" +
            "Please enter the date you'd like to buy this stock on: (YYYY-MM-DD)\n" +
            "How would you like to input your stock data? \n" +
            "Enter 'f' to input the stock data through a csv file.\n" +
            "Enter 'a' to input the stock data through our online resource (API).\n" +
            "Enter 'b' to go back to previous options or view stock data.\n" +
            "Successfully entered portfolio data\n", builder.toString());
  }


  @Test
  public void testCreatetPortfolioNotANumber() throws IOException {
    in = new StringReader("nicky\na\n1\nb\nb\n");
    s = new Scanner(in);
    command = new CreatePortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("What would you like to name your portfolio? \n" +
            "How would many different stocks (different companies) would you " +
            "like to input into your portfolio? \n" +
            "Error: not a number\n", builder.toString());
  }


  @Test
  public void testCreatetPortfolioSharesNotANumber() throws IOException {
    in = new StringReader("nicky\n2\na\nb\nb\n");
    s = new Scanner(in);
    command = new CreatePortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("What would you like to name your portfolio? \n" +
            "How would many different stocks (different companies) would you" +
            " like to input into your portfolio? \n" +
            "First input how many shares you'd like for stock 1\n" +
            "(after this you will be ask to enter the specific stock itself). \n" +
            "So please put the shares for this stock: \n" +
            "Not a number \n" +
            "Error: not a number\n", builder.toString());
  }
}