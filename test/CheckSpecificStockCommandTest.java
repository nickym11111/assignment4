import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import command.CheckSpecificStockCommand;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that when a user specifically checks
 * a stock.
 */
public class CheckSpecificStockCommandTest extends ACommandTest {
  public CheckSpecificStockCommandTest() throws FileNotFoundException {
    // constructor created to support exception thrown
  }

  @Before
  public void setUp() {
    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new CheckSpecificStockCommand(view, s);
  }

  @Test
  public void testNotATickerSymbol() throws IOException {
    in = new StringReader("lola\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter tickerSymbol to view statistics about a stock\n" +
            "That stock has not been inputted\n" +
            "Enter tickerSymbol to view statistics about a stock\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testEnterG() throws IOException {
    in = new StringReader("AMZN\ng\n05\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter tickerSymbol to view statistics about a stock\n" +
            "Enter 'g' to get the gain or loss of this stock from" +
            " a start date to end date\n" +
            "Enter 'm' to get the x-day moving average of this " +
            "stock at a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers " +
            "for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a " +
            "stock from a start date to end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date/crossover day range " +
            "that has not happened yet will result\n" +
            "in the most recently recorded date being used as the " +
            "end date instead.\n" +
            "Keep in mind entering a start date that has not happened " +
            "yet will prevent the calculation.\n" +
            "Enter a start date (YYYY-MM-DD) followed by a space and " +
            "an end date (YYYY-MM-DD)\n" +
            "Invalid input, enter a valid date (YYYY-MM-DD) followed" +
            " by a space and another valid date (YYYY-MM_DD)  is gain or " +
            "loss of this stock between the start and end date\n" +
            "Enter 'g' to get the gain or loss of this stock from a " +
            "start date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at " +
            "a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers for this " +
            "stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from a start " +
            "date to end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testEnterGRealDates() throws IOException {
    in = new StringReader("AMZN\ng\n2024-05-01 2024-05-08\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter tickerSymbol to view statistics about a stock\n" +
            "Enter 'g' to get the gain or loss of this stock from a start date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers" +
            " for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from " +
            "a start date to end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date/crossover day range " +
            "that has not happened yet will result\n" +
            "in the most recently recorded date being used as the end date instead.\n" +
            "Keep in mind entering a start date that has not " +
            "happened yet will prevent the calculation.\n" +
            "Enter a start date (YYYY-MM-DD) followed by a " +
            "space and an end date (YYYY-MM-DD)\n" +
            "$9.0 is gain or loss of this stock between the start and end date\n" +
            "Enter 'g' to get the gain or loss of this stock from a start date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers " +
            "for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from" +
            " a start date to end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testEnterM() throws IOException {
    in = new StringReader("AMZN\nm\n202404\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter tickerSymbol to view statistics about a stock\n" +
            "Enter 'g' to get the gain or loss of this stock from a start date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at a starting date\n" +
            "Enter 'c' to show which days are x-day " +
            "crossovers for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from a start date to end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering an end date/moving " +
            "day range that has not happened yet will result\n" +
            "in the most recently recorded date being used as the end date instead.\n" +
            "Keep in mind entering a start date that has not " +
            "happened yet will prevent the calculation.\n" +
            "Enter a start date (YYYY-MM-DD) followed by a " +
            "space and the positive integer number of days of moving average (x): \n" +
            "Invalid input, enter a valid date (YYYY-MM-DD) " +
            "followed by a space and a positive integer is the moving average\n" +
            "Enter 'g' to get the gain or loss of this stock " +
            "from a start date to end date\n" +
            "Enter 'm' to get the x-day moving average of " +
            "this stock at a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers " +
            "for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from " +
            "a start date to end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testEnterMRealDates() throws IOException {
    in = new StringReader("AMZN\nm\n2024-05-01 5\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter tickerSymbol to view statistics about a stock\n" +
            "Enter 'g' to get the gain or loss of this stock from a start " +
            "date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at a " +
            "starting date\n" +
            "Enter 'c' to show which days are x-day crossovers " +
            "for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from a " +
            "start date to end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering an end date/moving day range " +
            "that has not happened yet will result\n" +
            "in the most recently recorded date being used as the end date instead.\n" +
            "Keep in mind entering a start date that has not happened " +
            "yet will prevent the calculation.\n" +
            "Enter a start date (YYYY-MM-DD) followed by a space and " +
            "the positive integer number of days of moving average (x): \n" +
            "$177.65 is the moving average\n" +
            "Enter 'g' to get the gain or loss of this stock from " +
            "a start date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers for " +
            "this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from a" +
            " start date to end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testEnterC() throws IOException {
    in = new StringReader("AMZN\nc\n202404 202405\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter tickerSymbol to view statistics about a stock\n" +
            "Enter 'g' to get the gain or loss of this stock from a start" +
            " date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at a starting date\n" +
            "Enter 'c' to show which days are x-day " +
            "crossovers for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from" +
            " a start date to end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date/crossover day " +
            "range that has not happened yet will result\n" +
            "in the most recently recorded date being used as the " +
            "end date instead.\n" +
            "Keep in mind entering a start date that has not " +
            "happened yet will prevent the calculation.\n" +
            "Enter a start date (YYYY-MM-DD) followed by a space " +
            "and an end date (YYYY-MM-DD) followed by a space and \n" +
            "the positive integer number of days of moving average (x): \n" +
            "Invalid input, enter a valid date (YYYY-MM-DD) followed " +
            "by a space and a positive integerEnter 'g' to get the gain " +
            "or loss of this stock from a start date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock " +
            "at a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers for " +
            "this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from a start " +
            "date to end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Undefined instruction: 202405\n" +
            "Enter 'g' to get the gain or loss of this " +
            "stock from a start date to end date\n" +
            "Enter 'm' to get the x-day moving average " +
            "of this stock at a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers " +
            "for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from " +
            "a start date to end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testEnterCRealDates() throws IOException {
    in = new StringReader("AMZN\nc\n2024-05-01 2024-05-08 5\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter tickerSymbol to view statistics about a stock\n" +
            "Enter 'g' to get the gain or loss of this stock from a start" +
            " date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at a " +
            "starting date\n" +
            "Enter 'c' to show which days are x-day crossovers for this stock " +
            "at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from a start date to " +
            "end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date/crossover day range that has not " +
            "happened yet will result\n" +
            "in the most recently recorded date being used as the end date instead.\n" +
            "Keep in mind entering a start date that has not happened yet will " +
            "prevent the calculation.\n" +
            "Enter a start date (YYYY-MM-DD) followed by a space and an " +
            "end date (YYYY-MM-DD) followed by a space and \n" +
            "the positive integer number of days of moving average (x): \n" +
            "2024-05-01\n" +
            "2024-05-02\n" +
            "2024-05-03\n" +
            "2024-05-06\n" +
            "2024-05-07\n" +
            "2024-05-08\n" +
            " were crossoversEnter 'g' to get the gain or loss of this " +
            "stock from a start date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at" +
            " a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers for this " +
            "stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock" +
            " from a start date to end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testEnterRandInp() throws IOException {
    in = new StringReader("AMZN\nlala\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificStockCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter tickerSymbol to view statistics about a stock\n" +
            "Enter 'g' to get the gain or loss of this stock from a start " +
            "date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at a " +
            "starting date\n" +
            "Enter 'c' to show which days are x-day crossovers " +
            "for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from " +
            "a start date to end date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Undefined instruction: lala\n" +
            "Enter 'g' to get the gain or loss of this stock " +
            "from a start date to end date\n" +
            "Enter 'm' to get the x-day moving average of this stock at " +
            "a starting date\n" +
            "Enter 'c' to show which days are x-day crossovers " +
            "for this stock at a start date and end date\n" +
            "Enter 'p' to get the performance of a stock from a " +
            "start date to end date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


}