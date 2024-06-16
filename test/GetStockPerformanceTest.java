import org.junit.Test;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.Scanner;

import command.GetStockPerformance;
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
 * A test class that assures that the current performance of stock
 * is being properly displayed.
 */
public class GetStockPerformanceTest {
  Appendable a = new StringBuilder();
  IView view = new ViewImpl(a);
  GetStockPerformance stockPerformance = new GetStockPerformance(view);
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

  LocalDate start = LocalDate.of(2024, 5, 1);
  LocalDate end = LocalDate.of(2024, 5, 31);
  LocalDate weekend = LocalDate.of(2024, 5, 4);
  LocalDate holiday = LocalDate.of(2023, 5, 29);


  @Test
  public void testApple() {

    assertEquals(System.lineSeparator()
            + "Performance of AAPL from 2024-05-01 to 2024-05-31\n" +
            "2024-05-01 : *****************\n" +
            "2024-05-02 : ******************\n" +
            "2024-05-03 : *******************\n" +
            "2024-05-06 : *******************\n" +
            "2024-05-07 : *******************\n" +
            "2024-05-08 : *******************\n" +
            "2024-05-09 : *******************\n" +
            "2024-05-10 : *******************\n" +
            "2024-05-13 : *******************\n" +
            "2024-05-14 : *******************\n" +
            "2024-05-15 : *******************\n" +
            "2024-05-16 : *******************\n" +
            "2024-05-17 : *******************\n" +
            "2024-05-20 : ********************\n" +
            "2024-05-21 : ********************\n" +
            "2024-05-22 : ********************\n" +
            "2024-05-23 : *******************\n" +
            "2024-05-24 : *******************\n" +
            "2024-05-28 : *******************\n" +
            "2024-05-29 : ********************\n" +
            "2024-05-30 : ********************\n" +
            "2024-05-31 : ********************\n" +
            "SCALE: * = 10 US dollars"
            + System.lineSeparator()
            + System.lineSeparator(), stockPerformance.createPerformance(start, end, appleStock));
  }

  @Test
  public void testNike() {

    assertEquals(
            "\n" +
                    "Performance of NKE from 2024-05-01 to 2024-05-31\n" +
                    "2024-05-01 : *******************\n" +
                    "2024-05-02 : *******************\n" +
                    "2024-05-03 : *******************\n" +
                    "2024-05-06 : *******************\n" +
                    "2024-05-07 : *******************\n" +
                    "2024-05-08 : *******************\n" +
                    "2024-05-09 : *******************\n" +
                    "2024-05-10 : *******************\n" +
                    "2024-05-13 : *******************\n" +
                    "2024-05-14 : *******************\n" +
                    "2024-05-15 : *******************\n" +
                    "2024-05-16 : *******************\n" +
                    "2024-05-17 : *******************\n" +
                    "2024-05-20 : *******************\n" +
                    "2024-05-21 : *******************\n" +
                    "2024-05-22 : *******************\n" +
                    "2024-05-23 : *******************\n" +
                    "2024-05-24 : *******************\n" +
                    "2024-05-28 : *******************\n" +
                    "2024-05-29 : *******************\n" +
                    "2024-05-30 : *******************\n" +
                    "2024-05-31 : ********************\n" +
                    "SCALE: * = 5 US dollars\n" +
                    "\n"

            , stockPerformance.createPerformance(start, end, nikeStock));
  }

  @Test
  public void testGoogle() {

    assertEquals(
            "\n" +
                    "Performance of GOOG from 2024-05-01 to 2024-05-31\n" +
                    "2024-05-01 : *******************\n" +
                    "2024-05-02 : *******************\n" +
                    "2024-05-03 : *******************\n" +
                    "2024-05-06 : *******************\n" +
                    "2024-05-07 : ********************\n" +
                    "2024-05-08 : ********************\n" +
                    "2024-05-09 : ********************\n" +
                    "2024-05-10 : *******************\n" +
                    "2024-05-13 : *******************\n" +
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
                    "SCALE: * = 9 US dollars\n" +
                    "\n"

            , stockPerformance.createPerformance(start, end, googleStock));
  }

  @Test
  public void testAmazon() {

    assertEquals(
            "\n" +
                    "Performance of AMZN from 2024-05-01 to 2024-05-31\n" +
                    "2024-05-01 : ******************\n" +
                    "2024-05-02 : *******************\n" +
                    "2024-05-03 : *******************\n" +
                    "2024-05-06 : *******************\n" +
                    "2024-05-07 : *******************\n" +
                    "2024-05-08 : *******************\n" +
                    "2024-05-09 : *******************\n" +
                    "2024-05-10 : *******************\n" +
                    "2024-05-13 : *******************\n" +
                    "2024-05-14 : *******************\n" +
                    "2024-05-15 : *******************\n" +
                    "2024-05-16 : *******************\n" +
                    "2024-05-17 : *******************\n" +
                    "2024-05-20 : *******************\n" +
                    "2024-05-21 : *******************\n" +
                    "2024-05-22 : *******************\n" +
                    "2024-05-23 : *******************\n" +
                    "2024-05-24 : *******************\n" +
                    "2024-05-28 : *******************\n" +
                    "2024-05-29 : *******************\n" +
                    "2024-05-30 : ******************\n" +
                    "2024-05-31 : ******************\n" +
                    "SCALE: * = 10 US dollars\n" +
                    "\n"
            , stockPerformance.createPerformance(start, end, amazonStock));
  }


  @Test
  public void testGetPerformanceAYear() {
    LocalDate date = start.minusYears(1);


    assertEquals("\n" +
            "Performance of AMZN from 2023-05-01 to 2024-05-01\n" +
            "2023-05-01 : ***********\n" +
            "2023-06-01 : *************\n" +
            "2023-06-30 : **************\n" +
            "2023-08-02 : *************\n" +
            "2023-09-01 : **************\n" +
            "2023-10-03 : *************\n" +
            "2023-11-03 : **************\n" +
            "2023-12-04 : ***************\n" +
            "2024-01-04 : ***************\n" +
            "2024-02-02 : ******************\n" +
            "2024-03-06 : ******************\n" +
            "2024-04-05 : *******************\n" +
            "2024-05-07 : *******************\n" +
            "SCALE: * = 10 US dollars\n" +
            "\n", stockPerformance.createPerformance(date, start, amazonStock));
  }


  @Test
  public void testFutrueDate() {
    LocalDate date = start.plusYears(1);

    try {
      stockPerformance.createPerformance(start, date, amazonStock);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock must have date", e.getMessage());
    }
  }


  @Test
  public void testHolidayToWeekend() {

    try {
      stockPerformance.createPerformance(holiday, weekend, amazonStock);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock must have date", e.getMessage());
    }
  }


  @Test
  public void testStartDateAfterEndDate() {
    try {
      stockPerformance.createPerformance(weekend, holiday, amazonStock);
    } catch (IllegalArgumentException e) {
      assertEquals("Start date cannot be after end date",
              e.getMessage());
    }
  }

  @Test
  public void testEndDateBeforeStartDate() {
    LocalDate aYearBeforeStart = start.minusYears(1);
    try {
      stockPerformance.createPerformance(aYearBeforeStart, start, amazonStock);
    } catch (IllegalArgumentException e) {
      assertEquals("Start date cannot be after end date",
              e.getMessage());
    }
  }





}
