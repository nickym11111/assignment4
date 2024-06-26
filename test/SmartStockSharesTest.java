import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;

import model.ISmartStockShares;
import model.SmartStockShares;
import model.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class to demonstrates that the methods and implementation of SmartStockShares works
 * as intended. It tests various methods with various inputs to demonstrate functionality.
 */
public class SmartStockSharesTest {
  ISmartStockShares stockShares;
  ISmartStockShares stockSharesHoliday;
  ISmartStockShares stockSharesFutrue;
  LocalDate holiday;
  LocalDate may01;
  LocalDate weekend;
  LocalDate oldestDay;
  LocalDate futureDay;


  @Before
  public void setUp() throws FileNotFoundException {
    this.holiday = LocalDate.parse("2020-12-31");
    this.may01 = LocalDate.parse("2024-05-01");
    this.weekend = LocalDate.parse("2024-05-26");
    this.oldestDay = LocalDate.parse("1999-11-01");
    this.futureDay = LocalDate.parse("2024-12-31");
    this.stockShares = new SmartStockShares(100,
            new Stock("GOOG"), may01);
    this.stockSharesHoliday = new SmartStockShares(100,
            new Stock("GOOG"), holiday);
    this.stockSharesFutrue = new SmartStockShares(300,
            new Stock("GOOG"), futureDay);

  }

  @Test
  public void testGetDate() {
    LocalDate date = LocalDate.of(2020, 12, 31);

    assertEquals(date, this.stockSharesHoliday.getDate());
  }

  @Test(expected = DateTimeException.class)
  public void testGetDateNegativeNumber() {
    LocalDate date = LocalDate.of(2020, 12, -31);

    assertEquals(date, this.stockSharesHoliday.getDate());
  }

  @Test
  public void testSetDate() {
    this.stockSharesHoliday.setDate(LocalDate.of(2004, 4, 1));

    assertEquals(LocalDate.of(2004, 4, 1),
            this.stockSharesHoliday.getDate());
  }

  @Test
  public void testSetDateNegativeNumber() {

    try {
      this.stockSharesHoliday.setDate(LocalDate.of(2004, -4, 1));
    } catch (DateTimeException e) {
      assertEquals("Invalid value for MonthOfYear (valid values 1 - 12): " +
              "-4", e.getMessage());
    }
  }


  @Test
  public void testIsBought() {

    assertFalse(this.stockSharesHoliday.isBought());
  }


  @Test
  public void testSetBought() {

    this.stockSharesHoliday.setBought(true);

    assertTrue(this.stockSharesHoliday.isBought());
  }


}