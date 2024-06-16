import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.Scanner;

import command.PortfolioCompositionCommand;
import command.PortfolioDistributionCommand;
import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.IStock;

import static org.junit.Assert.assertEquals;

/**
 * A test class that guarantees that a user is allowed to view
 * the distribution of their portfolio that created.
 */
public class PortfolioDistributionCommandTest extends ACommandTest {


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
  LocalDate holiday = LocalDate.of(204, 3, 29);

  public PortfolioDistributionCommandTest() throws FileNotFoundException {
    // this empty because were are using files to test our data and
    // there is no need to initialize date here in the constructor.
  }

  @Before
  public void setUp() throws FileNotFoundException {

    portfolio.addStockShare("NKE", nikeStock, 15, today);
    portfolio.addStockShare("GOOG", googleStock, 5, today);
    portfolio.addStockShare("AMZN", amazonStock, 15, today);
    portfolio.addStockShare("AAPL", appleStock, 5, today);


    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new PortfolioDistributionCommand(view, s, portfolio);
  }


  @Test
  public void testWrongInput() throws IOException {
    in = new StringReader("\"20258-05-01\n2025-05-01\nb\"");
    s = new Scanner(in);
    command = new PortfolioDistributionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date you'd like " +
            "to get the distribution for: (YYYY-MM-DD)\n" +
            "Invalid input, enter a valid date (YYYY-MM-DD) \n" +
            "The distribution of this portfolio at 2025-05-01:\n" +
            "GOOG - Stock does not exist\n" +
            "AAPL - Stock does not exist\n" +
            "NKE - Stock does not exist\n" +
            "AMZN - Stock does not exist" +
            System.lineSeparator(), builder.toString());
  }

  @Test
  public void testPastDayOfPortfolio() throws IOException {
    in = new StringReader("1999-05-01\nb");
    s = new Scanner(in);
    command = new PortfolioDistributionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date you'd like to get " +
            "the distribution for: (YYYY-MM-DD)\n" +
            "Portfolio does not have distribution for a date " +
            "before its earliest purchase\n", builder.toString());
  }

  @Test
  public void testFutureDay() throws IOException {
    in = new StringReader("2025-05-01\nb");
    s = new Scanner(in);
    command = new PortfolioDistributionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date " +
            "you'd like to get the distribution for: (YYYY-MM-DD)\n" +
            "The distribution of this portfolio at 2025-05-01:\n" +
            "GOOG - Stock does not exist\n" +
            "AAPL - Stock does not exist\n" +
            "NKE - Stock does not exist\n" +
            "AMZN - Stock does not exist" + System.lineSeparator(), builder.toString());
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
  public void testRightInput() throws IOException {
    in = new StringReader("2024-05-01\nb");
    s = new Scanner(in);
    command = new PortfolioDistributionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    assertEquals("Please enter the date you'd like " +
            "to get the distribution for: (YYYY-MM-DD)\n" +
            "The distribution of this portfolio at 2024-05-01:\n" +
            "GOOG - $827.8499999999999\n" +
            "AAPL - $846.5\n" +
            "NKE - $1355.1000000000001\n" +
            "AMZN - $2685.0" + System.lineSeparator(), builder.toString());
  }

  @Test
  public void testHoliday() throws IOException {
    in = new StringReader("2024-03-29\nb");
    s = new Scanner(in);
    command = new PortfolioDistributionCommand(view, s, portfolio);
    this.command.run(stockMarket);

    //portfolio.setDateCreated("");

    assertEquals("Please enter the date you'd like to get " +
            "the distribution for: (YYYY-MM-DD)\n" +
            "Portfolio does not have distribution for a date " +
            "before its earliest purchase\n"
           , builder.toString());
  }


}