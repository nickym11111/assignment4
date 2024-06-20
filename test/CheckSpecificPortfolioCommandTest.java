import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import command.CheckSpecificPortfolioCommand;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that when a user specifically checks
 * a portfolio.
 */
public class CheckSpecificPortfolioCommandTest extends ACommandTest {

  public CheckSpecificPortfolioCommandTest() throws FileNotFoundException {
    // constructor created to support exception thrown
  }


  @Before
  public void setUp() {
    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
  }

  @Test
  public void testRandInp() throws IOException {
    in = new StringReader("lola\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter name of portfolio\n" +
            "That portfolio was not found/ has not been inputted\n" +
            "Enter name of portfolio\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testNumberInput() throws IOException {
    in = new StringReader("1\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter name of portfolio\n" +
            "That portfolio was not found/ has not been inputted\n" +
            "Enter name of portfolio\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testExistingPort() throws IOException {
    in = new StringReader("arsema\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options"
            + System.lineSeparator(), builder.toString());
  }


  @Test
  public void testExistingPortGetValueRealDate() throws IOException {
    in = new StringReader("arsema\nv\n2024-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date that has not happened yet will result\n" +
            "in the most recently recorded date being used as the date instead.\n" +
            "Enter a date (YYYY-MM-DD) for which you want the portfolio value\n" +
            "$950.02 is the value of this portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this " +
            "portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testRebalancePortfolio() throws IOException {
    in = new StringReader("arsema\nrb\n2024-05-01\nAAPL\n0.50\nNKE\n0.50\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date that has not happened yet will result\n" +
            "in the most recently recorded date being used instead.\n" +
            "Enter a date (YYYY-MM-DD) for which you want to balance the portfolio value.\n" +
            "Your portfolio at this time has 2 stocks\n" +
            "Please enter the percentage of as a decimal: AAPL\n" +
            "Invalid percentage value for AAPL\n" +
            "Please enter the percentage of as a decimal: NKE\n" +
            "Invalid percentage value for NKE\n" +
            "Portfolio rebalance completed!\n" +
            "AAPL - 2.81\n" +
            "NKE - 5.25\n" +
            "\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testRebalancePortfolioFutureDay() throws IOException {
    in = new StringReader("arsema\nrb\n2028-05-01\nAAPL\n0.50\nNKE\n0.50\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);


    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this " +
            "portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date that has not happened yet will result\n" +
            "in the most recently recorded date being used instead.\n" +
            "Enter a date (YYYY-MM-DD) for which you want to balance the portfolio value.\n" +
            "Your portfolio at this time has 2 stocks\n" +
            "Please enter the percentage of as a decimal: AAPL\n" +
            "Invalid percentage value for AAPL\n" +
            "Please enter the percentage of as a decimal: NKE\n" +
            "Invalid percentage value for NKE\n" +
            "Error: invalid date, ensure the date is in " +
            "chronological order of buying,selling, rebalances, " +
            "and within the existence of this stock.\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this " +
            "portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testRebalancePortfolioNegativeDay() throws IOException {
    in = new StringReader("arsema\nrb\n-2028-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);


    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date that has not happened yet will result\n" +
            "in the most recently recorded date being used instead.\n" +
            "Enter a date (YYYY-MM-DD) for which you want to balance the portfolio value.\n" +
            "Hello, the portfolio, arsema was created on 2005-03-01\n" +
            "You have inputted the wrong date. Here is our menu please try again. \n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testRebalancePortfolioBeforePortfolioCreat() throws IOException {
    in = new StringReader("arsema\nrb\n2004-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);


    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date that has not happened yet will result\n" +
            "in the most recently recorded date being used instead.\n" +
            "Enter a date (YYYY-MM-DD) for which you want to balance the portfolio value.\n" +
            "Hello, the portfolio, arsema was created on 2005-03-01\n" +
            "You have inputted the wrong date. Here is our menu please try again. \n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testViewPortfolioOption() throws IOException {
    in = new StringReader("arsema\nb\nb");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio" +
            " on a given start and end date\n" +
            "Enter 'b' to go back to previous options" +
            System.lineSeparator(), builder.toString());
  }


  @Test
  public void testDistrubution() throws IOException {
    in = new StringReader("arsema\nd\n2024-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter the date you'd like to get the distribution for: (YYYY-MM-DD)\n" +
            "The distribution of this portfolio at 2024-05-01:\n" +
            "AAPL - $475.73300000000006\n" +
            "NKE - $474.285\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testDistrubutionFutureDay() throws IOException {
    in = new StringReader("arsema\nd\n2028-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter the date you'd like to get the distribution for: (YYYY-MM-DD)\n" +
            "The distribution of this portfolio at 2028-05-01:\n" +
            "AAPL - Stock does not exist\n" +
            "NKE - Stock does not exist\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testDistrubutionNegativeDay() throws IOException {
    in = new StringReader("arsema\nd\n-2028-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter the date you'd like to get the distribution for: (YYYY-MM-DD)\n" +
            "Portfolio does not have distribution for a date before its earliest purchase\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testDistrubutionBeforePortWasCreated() throws IOException {
    in = new StringReader("arsema\nd\n-2004-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter the date you'd like to get the distribution for: (YYYY-MM-DD)\n" +
            "Portfolio does not have distribution for a date before its earliest purchase\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testComposition() throws IOException {
    in = new StringReader("arsema\nc\n2024-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter the date you'd like to get the composition for: (YYYY-MM-DD)\n" +
            "The composition of this portfolio at 2024-05-01:\n" +
            "AAPL - 2.81\n" +
            "NKE - 5.25\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());

  }

  @Test
  public void testCompositionFutureDays() throws IOException {
    in = new StringReader("arsema\nc\n2028-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter the date you'd like to get the composition for: (YYYY-MM-DD)\n" +
            "The composition of this portfolio at 2028-05-01:\n" +
            "AAPL - 2.81\n" +
            "NKE - 5.25\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());

  }

  @Test
  public void testCompositionNegativeDays() throws IOException {
    in = new StringReader("arsema\nc\n-2028-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter the date you'd like to get the composition for: (YYYY-MM-DD)\n" +
            "Portfolio does not have composition for a date before its earliest purchase\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());

  }

  @Test
  public void testPerformance() throws IOException {
    in = new StringReader("arsema\np\n2024-05-01\n2024-05-31\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter your start date of the performance period (YYYY-MM-DD):" +
            " Please enter your end date of the performance period (YYYY-MM-DD): \n" +
            "Performance of arsema from 2024-05-01 to 2024-05-31\n" +
            "2024-05-01 : *******************\n" +
            "2024-05-02 : *******************\n" +
            "2024-05-03 : ********************\n" +
            "2024-05-06 : ********************\n" +
            "2024-05-07 : ********************\n" +
            "2024-05-08 : ********************\n" +
            "2024-05-09 : ********************\n" +
            "2024-05-10 : ********************\n" +
            "2024-05-13 : ********************\n" +
            "2024-05-14 : ********************\n" +
            "2024-05-15 : ********************\n" +
            "2024-05-16 : ********************\n" +
            "2024-05-17 : ********************\n" +
            "2024-05-20 : ********************\n" +
            "2024-05-21 : ********************\n" +
            "2024-05-22 : ********************\n" +
            "2024-05-23 : ********************\n" +
            "2024-05-24 : ********************\n" +
            "2024-05-28 : ********************\n" +
            "2024-05-29 : ********************\n" +
            "2024-05-30 : ********************\n" +
            "2024-05-31 : ********************\n" +
            "SCALE: * = 52 US dollars\n" +
            "\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testPerformanceBadDateInput() throws IOException {
    in = new StringReader("arsema\np\n20246-05-01\n2024-05-31\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given " +
            "start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter your start date of the performance period (YYYY-MM-DD): " +
            "Invalid input, enter a valid dates (YYYY-MM-DD) \n" +
            "That are within the time of this portfolios existence and " +
            "in chronological order\n" +
            "Keep in mind using a national holiday as a start or end day mayprevent " +
            "calculations\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Undefined instruction: 2024-05-31\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options" +
            System.lineSeparator(), builder.toString());
  }


  @Test
  public void testPerformanceEndDateBeforeStart() throws IOException {
    in = new StringReader("arsema\np\n2024-05-31\n2024-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);

    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Please enter your start date of the performance period (YYYY-MM-DD): " +
            "Please enter your end date of the performance period (YYYY-MM-DD):" +
            " Invalid input, enter a valid dates (YYYY-MM-DD) \n" +
            "That are within the time of this portfolios existence and " +
            "in chronological order\n" +
            "Keep in mind using a national holiday as a start or end day" +
            " mayprevent calculations\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'rb' to balance this portfolio on a specific date\n" +
            "Enter 'c' to get the composition of this portfolio on a specific date\n" +
            "Enter 'd' to get the distribution of this portfolio on a specific date\n" +
            "Enter 'buy' to buy a stock for the portfolio\n" +
            "Enter 'sell' to sell stocks from the portfolio\n" +
            "Enter 'p' to get the performance of this portfolio " +
            "on a given start and end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


}