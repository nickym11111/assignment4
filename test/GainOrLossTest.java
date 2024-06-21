import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import command.GainOrLoss;
import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.IStock;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that when a user specifically check
 * if the stock has gained or lost its value.
 */
public class GainOrLossTest {
  LocalDate aDayInJan2014;
  LocalDate aDayInFeb2015;
  LocalDate aDayInMarch2015;
  LocalDate aDayInApril2016;
  LocalDate aDayInJune2016;
  LocalDate aDayInJuly2017;
  LocalDate aDayInAugust2017;
  LocalDate aDayInSeptember2018;
  LocalDate aDayInOctober2019;
  LocalDate aDayInNovember2019;
  LocalDate aDayInDecember2020;
  LocalDate dayDoesNotExitInDecember;
  LocalDate dateDoesExitInMay;

  IStockDataStream data = new FileStockDataStreamImpl("stocks/GOOG.csv");
  IStockBuilder stockBuilder = new StockBuilderImpl();
  IStock stock = stockBuilder.buildStock("GOOG", data);
  Appendable a = new StringBuilder();
  IView view = new ViewImpl(a);
  GainOrLoss strategies = new GainOrLoss(view);


  @Before
  public void setUp() {

    this.dayDoesNotExitInDecember = LocalDate.of(2024, 12, 1);
    this.dateDoesExitInMay = LocalDate.of(2014, 5, 1);

    this.aDayInJan2014 = LocalDate.of(2014, 5, 1);
    this.aDayInFeb2015 = LocalDate.of(2015, 2, 13);
    this.aDayInMarch2015 = LocalDate.of(2015, 3, 21);
    this.aDayInApril2016 = LocalDate.of(2016, 4, 9);
    this.aDayInJune2016 = LocalDate.of(2016, 6, 19);
    this.aDayInJuly2017 = LocalDate.of(2017, 7, 4);
    this.aDayInAugust2017 = LocalDate.of(2017, 8, 1);
    this.aDayInSeptember2018 = LocalDate.of(2018, 9, 25);
    this.aDayInOctober2019 = LocalDate.of(2019, 10, 31);
    this.aDayInNovember2019 = LocalDate.of(2019, 11, 26);
    this.aDayInDecember2020 = LocalDate.of(2020, 12, 25);
  }

  @Test
  public void testGainOrLoss() {
    LocalDate start = LocalDate.of(2024, 5, 1);
    LocalDate end = LocalDate.of(2024, 5, 8);

    assertEquals(5.59, strategies.changeOverTime(start, end, stock), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGainOrLossStartDayDoesNotExist() {
    LocalDate start = LocalDate.of(2024, 5, 4);
    LocalDate end = LocalDate.of(2024, 5, 9);

    assertEquals(-2.59, strategies.changeOverTime(start, end, stock), 0.01);
  }

  @Test
  public void testGainOrLossEndDayDoesNotExist() {
    LocalDate start = LocalDate.of(2024, 4, 29);
    LocalDate end = LocalDate.of(2024, 5, 4);

    assertEquals(1.09, strategies.changeOverTime(start, end, stock), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGainOrLossEndDayBeforeStartDay() {
    LocalDate start = LocalDate.of(2024, 5, 4);
    LocalDate end = LocalDate.of(2024, 4, 29);

    assertEquals(-1.09, strategies.changeOverTime(start, end, stock), 0.01);
  }


  @Test
  public void testGainOrLossInJanuary2014ToFebruary2015() {

    assertEquals(17.66, strategies.changeOverTime(this.aDayInJan2014,
            this.aDayInFeb2015, stock), 0.01);
  }


  @Test
  public void testGainOrLossInJanuary2014ToFebruary2015DoesNotExist() {
    LocalDate end = LocalDate.of(2015, 2, 14);

    assertEquals(17.66, strategies.changeOverTime(this.aDayInJan2014,
            end, stock), 0.01);
  }


  @Test
  public void testGainOrLossInFebruary2015ToMarch2015() {

    assertEquals(11.35, strategies.changeOverTime(this.aDayInFeb2015,
            this.aDayInMarch2015, stock), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGainOrLossInMarchToApril() {
    assertEquals(178.78, strategies.changeOverTime(this.aDayInMarch2015,
            this.aDayInApril2016, stock), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGainOrLostStartDateAfterEndDate() {
    assertEquals(386.4, strategies.changeOverTime(this.aDayInJuly2017,
            this.aDayInMarch2015, stock), 0.01);
  }


  @Test
  public void testGainOrLostEndDateBeforeStartDate() {

    assertEquals(-370.47, strategies.changeOverTime(this.aDayInAugust2017,
            this.aDayInMarch2015, stock), 0.01);
  }

  @Test
  public void testGainOrLossNoEndDate() {
    LocalDate start = LocalDate.of(2024, 5, 1);
    LocalDate end = LocalDate.of(2024, 7, 31);

    assertEquals(12.14, strategies.changeOverTime(start, end, stock), 0.01);
  }

  @Test
  public void testGainOrLossOverAMonth() {
    LocalDate start = LocalDate.of(2024, 3, 1);
    LocalDate end = LocalDate.of(2024, 4, 1);

    assertEquals(18.42, strategies.changeOverTime(start, end, stock), 0.01);
  }

  @Test
  public void testGainOrLossOverAYear() {
    LocalDate start = LocalDate.of(2024, 3, 1);
    LocalDate end = LocalDate.of(2023, 3, 1);

    assertEquals(-47.57, strategies.changeOverTime(start, end, stock), 0.01);
  }


  @Test
  public void testGainOrLossDifferentYear() {
    LocalDate start = LocalDate.of(2023, 12, 15);
    LocalDate end = LocalDate.of(2024, 1, 2);

    assertEquals(5.72, strategies.changeOverTime(start, end, stock), 0.01);
  }

  @Test
  public void testGainOrLossDifferentMonth() {
    LocalDate start = LocalDate.of(2021, 11, 15);
    LocalDate end = LocalDate.of(2023, 10, 2);

    assertEquals(-2852.59, strategies.changeOverTime(start, end, stock), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGainOrOastDate() {
    LocalDate start = LocalDate.of(1999, 6, 1);
    LocalDate end = LocalDate.of(2024, 6, 30);
    assertEquals(12.34, strategies.changeOverTime(start, end, stock), 0.01);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGainOrLossFarFutureDate() {
    LocalDate start = LocalDate.of(2050, 1, 1);
    LocalDate end = LocalDate.of(2050, 12, 31);
    strategies.changeOverTime(start, end, stock);
  }


}