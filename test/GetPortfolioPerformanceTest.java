import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.Scanner;

import command.GetPortfolioPerformance;
import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.ISmartPortfolio;
import model.IStock;
import model.SmartPortfolio;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * A test class that reassures the portfolio performance is correct.
 */
public class GetPortfolioPerformanceTest extends ACommandTest {

  Appendable a = new StringBuilder();
  IView view = new ViewImpl(a);
  GetPortfolioPerformance getPortfolioPerformance = new GetPortfolioPerformance(view);
  ISmartPortfolio portfolio = new SmartPortfolio("Technology");
  StringReader in = new StringReader("");
  Scanner s = new Scanner(in);
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

  LocalDate start = LocalDate.of(2023, 5, 1);
  LocalDate end = LocalDate.of(2023, 5, 31);
  LocalDate weekend = LocalDate.of(2024, 5, 4);
  LocalDate holiday = LocalDate.of(2023, 5, 29);


  ISmartPortfolio nicky = new SmartPortfolio("Life Savings");

  public GetPortfolioPerformanceTest() throws FileNotFoundException {
    // this is a test class that use file store
  }

  @Before
  public void setUp() throws FileNotFoundException {
    portfolio.addStockShare("NKE", nikeStock, 15, start); // 1448.25
    portfolio.addStockShare("GOOG", googleStock, 5, start); // 879.75
    portfolio.addStockShare("AMZN", amazonStock, 15, start); // 2,764.5
    portfolio.addStockShare("AAPL", appleStock, 5, start); // 984.45

    nicky.setDateCreated(LocalDate.of(2022, 5, 6));
    nicky.addStockShare("NKE", nikeStock, 15, start.minusYears(1));
    nicky.addStockShare("GOOG", googleStock, 5, start.minusYears(1));
  }


  @Test
  public void getPortfolioPerformance30Days() throws FileNotFoundException {

    assertEquals(
            "\n" +
                    "Performance of Technology from 2023-05-01 to 2023-05-31\n" +
                    "2023-05-01 : ********************\n" +
                    "2023-05-02 : ********************\n" +
                    "2023-05-03 : ********************\n" +
                    "2023-05-04 : *******************\n" +
                    "2023-05-05 : ********************\n" +
                    "2023-05-08 : ********************\n" +
                    "2023-05-09 : ********************\n" +
                    "2023-05-10 : ********************\n" +
                    "2023-05-11 : ********************\n" +
                    "2023-05-12 : ********************\n" +
                    "2023-05-15 : ********************\n" +
                    "2023-05-16 : ********************\n" +
                    "2023-05-17 : ********************\n" +
                    "2023-05-18 : ********************\n" +
                    "2023-05-19 : ********************\n" +
                    "2023-05-22 : ********************\n" +
                    "2023-05-23 : ********************\n" +
                    "2023-05-24 : ********************\n" +
                    "2023-05-25 : ********************\n" +
                    "2023-05-26 : ********************\n" +
                    "2023-05-30 : ********************\n" +
                    "2023-05-31 : ********************\n" +
                    "SCALE: * = 253 US dollars\n" +
                    "\n",
            getPortfolioPerformance.createPerformance(start, end, portfolio));
  }


  @Test
  public void getPortfolioPerformanceStartDateAfterEnd()
          throws FileNotFoundException {
    try {
      getPortfolioPerformance.createPerformance(weekend, holiday, portfolio);
    } catch (IllegalArgumentException e) {
      assertEquals("Start date cannot be after end date", e.getMessage());
    }
  }


  @Test
  public void getPortfolioPerformanceStartDateBeforePorfolioCreated()
          throws FileNotFoundException {
    LocalDate start = LocalDate.of(1999, 5, 1);

    try {
      getPortfolioPerformance.createPerformance(start, holiday, portfolio);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock must have valid start date", e.getMessage());
    }

  }

  @Test
  public void getPortfolioPerformanceWeekEndToHoliday() throws FileNotFoundException {
    weekend = weekend.minusYears(1);

    assertEquals("\n" +
                    "Performance of Technology from 2023-05-04 to 2023-05-29\n" +
                    "2023-05-04 : *******************\n" +
                    "2023-05-05 : ********************\n" +
                    "2023-05-08 : ********************\n" +
                    "2023-05-09 : ********************\n" +
                    "2023-05-10 : ********************\n" +
                    "2023-05-11 : ********************\n" +
                    "2023-05-12 : ********************\n" +
                    "2023-05-15 : ********************\n" +
                    "2023-05-16 : ********************\n" +
                    "2023-05-17 : ********************\n" +
                    "2023-05-18 : ********************\n" +
                    "2023-05-19 : ********************\n" +
                    "2023-05-22 : ********************\n" +
                    "2023-05-23 : ********************\n" +
                    "2023-05-24 : ********************\n" +
                    "2023-05-25 : ********************\n" +
                    "2023-05-26 : ********************\n" +
                    "SCALE: * = 253 US dollars\n" +
                    "\n",
            getPortfolioPerformance.createPerformance(weekend, holiday, portfolio));
  }

  @Test
  public void getPortfolioPerformanceAYear() throws FileNotFoundException {
    LocalDate aYearStart = start.plusYears(1);
    System.out.println(nicky.getDateCreated());
    assertEquals("\n" +
                    "Performance of Life Savings from 2023-05-01 to 2024-05-01\n" +
                    "2023-05-01 : ********************\n" +
                    "2023-06-01 : ******************\n" +
                    "2023-06-30 : *******************\n" +
                    "2023-08-02 : *******************\n" +
                    "2023-09-01 : *******************\n" +
                    "2023-10-03 : ******************\n" +
                    "2023-11-03 : *******************\n" +
                    "2023-12-04 : ********************\n" +
                    "2024-01-04 : *******************\n" +
                    "2024-02-02 : *******************\n" +
                    "2024-03-06 : ******************\n" +
                    "2024-04-05 : ******************\n" +
                    "2024-05-07 : *******************\n" +
                    "SCALE: * = 123 US dollars\n" +
                    "\n",
            getPortfolioPerformance.createPerformance(start, aYearStart, nicky));
  }

  @Test
  public void testGetPortfolioPerformanceWeek() throws FileNotFoundException {
    LocalDate aWeekStart = start.plusWeeks(1);

    assertEquals("\n" +
            "Performance of Technology from 2023-05-01 to 2023-05-08\n" +
            "2023-05-01 : ********************\n" +
            "2023-05-02 : ********************\n" +
            "2023-05-03 : ********************\n" +
            "2023-05-04 : ********************\n" +
            "2023-05-05 : ********************\n" +
            "2023-05-08 : ********************\n" +
            "SCALE: * = 245 US dollars\n" +
            "\n", getPortfolioPerformance.createPerformance(start,
            aWeekStart, portfolio));
  }


  @Test
  public void testFutrueDate() {
    LocalDate date = start.plusYears(1);

    try {
      getPortfolioPerformance.createPerformance(start, date, portfolio);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock must have date", e.getMessage());
    }
  }


  @Test
  public void testHolidayToWeekend() {

    try {
      getPortfolioPerformance.createPerformance(holiday, weekend, portfolio);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock must have date", e.getMessage());
    }
  }


  @Test
  public void testStartDateAfterEndDate() {
    try {
      getPortfolioPerformance.createPerformance(weekend, holiday, portfolio);
    } catch (IllegalArgumentException e) {
      assertEquals("Start date cannot be after end date",
              e.getMessage());
    }
  }

  @Test
  public void testEndDateBeforeStartDate() {
    LocalDate aYearBeforeStart = start.minusYears(1);
    try {
      getPortfolioPerformance.createPerformance(aYearBeforeStart, start, portfolio);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock must have valid start date",
              e.getMessage());
    }
  }


}
