import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import model.DailyStock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * A test class that allows use to  guarantee  that our daily stocks is properly being
 * stored and obtained.
 */
public class DailyStockTest {
  private DailyStock dailyStock;


  @Before
  public void setUp() throws Exception {
    LocalDate aGoodDay = LocalDate.of(2024, 5, 1);
    this.dailyStock = new DailyStock("AMZN", aGoodDay, 181.6350,
            185.1500, 176.5600, 179.0000, 94645148);
  }

  @Test
  public void testOpenValue() {

    assertEquals(181.635, this.dailyStock.getOpenValue(), 0.01);
  }


  @Test
  public void testCloseValue() {
    assertEquals(179.0,
            this.dailyStock.getCloseValue(), 0.01);
  }

  @Test
  public void testHighValue() {
    assertEquals(185.15,
            this.dailyStock.getHighValue(), 0.01);
  }

  @Test
  public void testLowValue() {
    assertEquals(176.56,
            this.dailyStock.getLowValue(), 0.01);
  }

  @Test
  public void testDate() {
    LocalDate localDate = LocalDate.of(2024, 5, 1);
    assertEquals(localDate, this.dailyStock.getDate());
  }

  @Test
  public void testToString() {

    LocalDate aGoodDay = LocalDate.of(2024, 5, 1);
    DailyStock dailyStock = new DailyStock("AMZN", aGoodDay,
            181.6350, 185.1500, 176.5600, 179.0000, 94645148);
    String expected = "DailyStocktickerSymbol='AMZN, date=2024-05-01," +
            " open=181.635, high=185.15, low=176.56, close=179.0, volume= 94645148";
    assertEquals(expected, dailyStock.toString());

  }

  @Test
  public void testEquals() {
    LocalDate aGoodDay = LocalDate.of(2024, 5, 1);
    DailyStock dailyStock1 = new DailyStock("AMZN", aGoodDay,
            181.6350, 185.1500, 176.5600, 179.0000, 94645148);
    DailyStock dailyStock2 = new DailyStock("AMZN", aGoodDay,
            181.6350, 185.1500, 176.5600, 179.0000, 94645148);


    assertEquals(dailyStock1, dailyStock2);

  }

  @Test
  public void testNotEqualsDifferentTicker() {
    LocalDate aGoodDay = LocalDate.of(2024, 5, 1);
    DailyStock dailyStock1 = new DailyStock("AMZN", aGoodDay,
            181.6350, 185.1500, 176.5600, 179.0000, 94645148);
    DailyStock dailyStock2 = new DailyStock("GOOG", aGoodDay,
            181.6350, 185.1500, 176.5600, 179.0000, 94645148);

    assertNotEquals(dailyStock1, dailyStock2);
  }

  @Test
  public void testNotEqualsDifferentDate() {
    LocalDate date1 = LocalDate.of(2024, 5, 1);
    LocalDate date2 = LocalDate.of(2024, 5, 2);
    DailyStock dailyStock1 = new DailyStock("AMZN", date1,
            181.6350, 185.1500, 176.5600, 179.0000, 94645148);
    DailyStock dailyStock2 = new DailyStock("AMZN", date2,
            181.6350, 185.1500, 176.5600, 179.0000, 94645148);

    assertNotEquals(dailyStock1, dailyStock2);
  }

  @Test
  public void testNegativeValues() {
    LocalDate date = LocalDate.of(2024, 5, 1);
    DailyStock dailyStock = new DailyStock("AMZN", date,
            -181.6350, -185.1500, -176.5600, -179.0000, 94645148);

    assertEquals(-181.635, dailyStock.getOpenValue(), 0.01);
    assertEquals(-185.15, dailyStock.getHighValue(), 0.01);
    assertEquals(-176.56, dailyStock.getLowValue(), 0.01);
    assertEquals(-179.0, dailyStock.getCloseValue(), 0.01);
  }

  @Test
  public void testZeroValues() {
    LocalDate date = LocalDate.of(2024, 5, 1);
    DailyStock dailyStock = new DailyStock("AMZN", date,
            0.0, 0.0, 0.0, 0.0, 94645148);

    assertEquals(0.0, dailyStock.getOpenValue(), 0.01);
    assertEquals(0.0, dailyStock.getHighValue(), 0.01);
    assertEquals(0.0, dailyStock.getLowValue(), 0.01);
    assertEquals(0.0, dailyStock.getCloseValue(), 0.01);
  }


}
