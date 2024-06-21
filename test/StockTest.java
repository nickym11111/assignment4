import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.DailyStock;
import model.IStock;
import model.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test class that guarantees that our Stock class is properly being implemented.
 */
public class StockTest {

  IStockBuilder amazonBuilder = new StockBuilderImpl();
  IStockDataStream fileReaderAmazon = new FileStockDataStreamImpl("stocks/AMZN.csv");
  IStock amazonStock = amazonBuilder.buildStock("AMZN", fileReaderAmazon);
  LocalDate aGoodDay = LocalDate.of(2024, 5, 1);
  LocalDate holiday = LocalDate.of(2024, 3, 29);
  LocalDate weekend = LocalDate.of(2024, 3, 24);
  LocalDate futrueDay = LocalDate.of(2025, 5, 1);
  DailyStock dailyStock = new DailyStock("AMZN", aGoodDay, 181.6350,
          185.1500, 176.5600, 179.0000, 94645148);

  IStockBuilder nikeBuilder = new StockBuilderImpl();
  IStockDataStream fileReaderNike = new FileStockDataStreamImpl("stocks/NKE.csv");
  IStock nikeStock = nikeBuilder.buildStock("MKE", fileReaderNike);


  @Test
  public void testGetStockOnGoodDate() {
    DailyStock expected = new DailyStock("AMZN", aGoodDay, 181.6350,
            185.1500, 176.5600, 179.0000, 94645148);

    assertEquals(expected, this.amazonStock.getStockAtDate(aGoodDay));

  }


  @Test
  public void testGetStockOnHolidayDate() {

    try {
      this.amazonStock.getStockAtDate(holiday);
    } catch (Exception e) {
      assertEquals(e.getMessage(),
              "Check date. We do not have a stock for this date");
    }

  }

  @Test
  public void testGetStockOnWeekendDate() {

    try {
      this.amazonStock.getStockAtDate(weekend);
    } catch (Exception e) {
      assertEquals(e.getMessage(),
              "Check date. We do not have a stock for this date");
    }

  }

  @Test
  public void testGetStockOnFutrueDate() {

    try {
      this.amazonStock.getStockAtDate(futrueDay);
    } catch (Exception e) {
      assertEquals(e.getMessage(),
              "Check date. We do not have a stock for this date");
    }

  }


  @Test
  public void testDateExistFalse() {

    assertFalse(this.amazonStock.doesDateExist(futrueDay));
  }

  @Test
  public void testDateExistTrue() {

    assertTrue(this.amazonStock.doesDateExist(aGoodDay));
  }

  @Test
  public void testHolidayExist() {

    assertFalse(this.amazonStock.doesDateExist(holiday));
  }

  @Test
  public void testGetMostRecentDate() {
    LocalDate expectedDate = LocalDate.of(2024, 6, 18);

    assertEquals(expectedDate, this.amazonStock.getMostRecentDate());

  }

  @Test
  public void testGetStockValueADayThatExist() {

    assertEquals(179.0, this.amazonStock.getStockValue(aGoodDay), 0.01);
  }

  @Test
  public void testGetStockValueADayThatExistNike() {

    assertEquals(90.34, this.nikeStock.getStockValue(aGoodDay), 0.01);
  }


  @Test
  public void testGetStockValueADayThatDoesNotExist() {

    try {
      this.amazonStock.getStockValue(futrueDay);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Stock does not exist");
    }
  }


  @Test
  public void testGetStockValueAHoliday() {

    try {
      this.amazonStock.getStockValue(holiday);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Stock does not exist");
    }
  }

  @Test
  public void getAllStockValues() {
    Map<LocalDate, DailyStock> expected = new HashMap<>();
    IStock newStock = new Stock("AMZN");

    newStock.addDailyStock(holiday, dailyStock);
    Map<LocalDate, DailyStock> actual = newStock.getAllStocks();

    expected.put(holiday, dailyStock);

    assertEquals(expected, actual);
  }


  @Test
  public void testGetTickerSymbol() {
    assertEquals("AMZN",
            this.amazonStock.getTickerSymbol());
  }

  @Test
  public void testGetTickerSymbolAgain() {
    assertEquals("MKE",
            this.nikeStock.getTickerSymbol());
  }


  @Test
  public void testSetEarliestDate() {
    LocalDate date = LocalDate.of(2024, 1, 1);
    amazonStock.setEarliestDate(date);

    assertEquals(date, amazonStock.getEarliestDate());
  }

  @Test
  public void testSetEarliestDateInTheFile() {
    LocalDate date = LocalDate.of(1999, 11, 1);

    assertEquals(date, amazonStock.getEarliestDate());
  }


  @Test
  public void testSetMostRecentDate() {
    LocalDate date = LocalDate.of(2024, 6, 6);
    amazonStock.setMostRecentDate(date);
    assertEquals(date, amazonStock.getMostRecentDate());
  }

  @Test
  public void testAddDailyStock() {
    LocalDate date = LocalDate.of(2024, 6, 6);
    DailyStock dailyStock = new DailyStock("AMZN", date, 100.0,
            110.0, 90.0, 105.0, 100000);
    amazonStock.addDailyStock(date, dailyStock);
    assertEquals(dailyStock, amazonStock.getAllStocks().get(date));
  }


  @Test
  public void testAddDailyStockAHoliday() {
    LocalDate date = LocalDate.of(2024, 3, 29);
    DailyStock dailyStock = new DailyStock("AMZN", date,
            100.0, 110.0, 90.0, 105.0, 100000);

    amazonStock.addDailyStock(date, dailyStock);
    assertEquals(dailyStock, amazonStock.getAllStocks().get(date));
  }


  @Test
  public void testAddDailyStockThatDoesNotExist() {
    DailyStock dailyStock = new DailyStock("AMZN", futrueDay,
            100.0, 110.0, 90.0, 105.0, 100000);

    amazonStock.addDailyStock(futrueDay, dailyStock);
    assertEquals(dailyStock, amazonStock.getAllStocks().get(futrueDay));
  }


}
