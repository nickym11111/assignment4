import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.Scanner;

import command.PortfolioCompositionCommand;
import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.IStock;

import static org.junit.Assert.assertEquals;

/**
 * A test class that guarantees that a user is allowed to view
 * the composition of their portfolio that created.
 */
public class PortfolioCompositionCommandTest extends ACommandTest {


  IStockDataStream fileReaderGoogle = new FileStockDataStreamImpl("stocks/GOOG.csv");
  IStockBuilder stockBuilder = new StockBuilderImpl();
  IStock googleStock = stockBuilder.buildStock("GOOG", fileReaderGoogle);

  IStockBuilder stockBuilderAmazon = new StockBuilderImpl();
  IStockDataStream fileReaderAmazon = new FileStockDataStreamImpl("stocks/AMZN.csv");
  IStock amazonStock = stockBuilderAmazon.buildStock("AMZN", fileReaderAmazon);


  IStockBuilder stockBuilderNike = new StockBuilderImpl();
  IStockDataStream fileReaderNike = new FileStockDataStreamImpl("stocks/NKE.csv");
  IStock nikeStock = stockBuilderNike.buildStock("NKE", fileReaderNike);


  IStockBuilder stockBuilderApple = new StockBuilderImpl();
  IStockDataStream fileReaderApple = new FileStockDataStreamImpl("stocks/AAPL.csv");
  IStock appleStock = stockBuilderApple.buildStock("AAPL", fileReaderApple);

  LocalDate today = LocalDate.of(2024, 5, 1);


  public PortfolioCompositionCommandTest() throws FileNotFoundException {
    // constructor created to demonstrate possibility of exception
  }

  @Before
  public void setUp() throws FileNotFoundException {

    portfolio.addStockShare("NKE", nikeStock, 15, today);
    portfolio.addStockShare("GOOG", googleStock, 5, today);
    portfolio.addStockShare("AMZN", amazonStock, 15, today);
    portfolio.addStockShare("AAPL", appleStock, 5, today);


    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new PortfolioCompositionCommand(view, s, portfolio);
  }


  @Test
  public void testWrongInput() throws IOException {
    in = new StringReader("20258-05-01\n2025-05-01\nb");
    s = new Scanner(in);
    command = new PortfolioCompositionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date you'd like to " +
            "get the composition for: (YYYY-MM-DD)\n" +
            "Invalid input, enter a valid date (YYYY-MM-DD) \n" +
            "The composition of this portfolio at 2025-05-01:\n" +
            "GOOG - 5.0\n" +
            "AAPL - 5.0\n" +
            "NKE - 15.0\n" +
            "AMZN - 15.0"
            + System.lineSeparator(), builder.toString());
  }

  @Test
  public void testMostCurrentDate() throws IOException {
    in = new StringReader("2024-06-13\n2025-05-01\nb");
    s = new Scanner(in);
    command = new PortfolioCompositionCommand(view, s, portfolio);
    this.command.run(stockMarket);


    assertEquals("Please enter the date you'd like to " +
            "get the composition for: (YYYY-MM-DD)\n" +
            "The composition of this portfolio at 2024-06-13:\n" +
            "GOOG - 5.0\n" +
            "AAPL - 5.0\n" +
            "NKE - 15.0\n" +
            "AMZN - 15.0"
            + System.lineSeparator(), builder.toString());
  }



  @Test
  public void testWrongInputLetter() throws IOException {
    in = new StringReader("l-05-01\n2025-05-01\nb");
    s = new Scanner(in);
    command = new PortfolioCompositionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date you'd like to " +
            "get the composition for: (YYYY-MM-DD)\n" +
            "Invalid input, enter a valid date (YYYY-MM-DD) \n" +
            "The composition of this portfolio at 2025-05-01:\n" +
            "GOOG - 5.0\n" +
            "AAPL - 5.0\n" +
            "NKE - 15.0\n" +
            "AMZN - 15.0"
            + System.lineSeparator(), builder.toString());
  }

  @Test
  public void testFutrueDate() throws IOException {
    in = new StringReader("2025-05-01\nb");
    s = new Scanner(in);
    command = new PortfolioCompositionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date you'd like to get the" +
            " composition for: (YYYY-MM-DD)\n" +
            "The composition of this portfolio at 2025-05-01:\n" +
            "GOOG - 5.0\n" +
            "AAPL - 5.0\n" +
            "NKE - 15.0\n" +
            "AMZN - 15.0"
            + System.lineSeparator(), builder.toString());
  }


  @Test
  public void testPastDayOfPortfolio() throws IOException {
    in = new StringReader("1999-05-01\nb");
    s = new Scanner(in);
    command = new PortfolioCompositionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date you'd like to " +
            "get the composition for: (YYYY-MM-DD)\n" +
            "Portfolio does not have composition for a " +
            "date before its earliest purchase\n", builder.toString());
  }


  @Test
  public void testRightInput() throws IOException {
    in = new StringReader("2024-05-01\nb");
    s = new Scanner(in);
    command = new PortfolioCompositionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date you'd like to " +
            "get the composition for: (YYYY-MM-DD)\n" +
            "The composition of this portfolio at 2024-05-01:\n" +
            "GOOG - 5.0\n" +
            "AAPL - 5.0\n" +
            "NKE - 15.0\n" +
            "AMZN - 15.0" + System.lineSeparator(), builder.toString());
  }

  @Test
  public void testHoliday() throws IOException {
    in = new StringReader("2025-03-29\nb");
    s = new Scanner(in);
    command = new PortfolioCompositionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date you'd " +
            "like to get the composition for: (YYYY-MM-DD)\n" +
            "The composition of this portfolio at 2025-03-29:\n" +
            "GOOG - 5.0\n" +
            "AAPL - 5.0\n" +
            "NKE - 15.0\n" +
            "AMZN - 15.0\n", builder.toString());
  }






}