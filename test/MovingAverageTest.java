import org.junit.Test;

import java.time.LocalDate;

import command.MovingAverage;
import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.IStock;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * A test class that calculates the moving average for stock when givn
 * the start day and the number of days and ensure that proper value is
 * being outputted.
 */
public class MovingAverageTest {

  IStockDataStream data = new FileStockDataStreamImpl("stocks/GOOG.csv");
  IStockBuilder stockBuilder = new StockBuilderImpl();
  IStock stock = stockBuilder.buildStock("GOOG", data);
  Appendable a = new StringBuilder();
  IView view = new ViewImpl(a);
  MovingAverage strategies = new MovingAverage(view);


  @Test
  public void testMovingAverage5Days() {
    LocalDate start = LocalDate.of(2024, 5, 1);

    assertEquals(165.95, strategies.movingAverage(start, 5, stock), 0.01);
  }

  @Test
  public void testMovingAverage30Days() {
    LocalDate start = LocalDate.of(2024, 5, 1);

    assertEquals(157.163, strategies.movingAverage(start, 30, stock), 0.01);
  }


  @Test
  public void testMovingAverageForADayThatDoesNotExist() {
    LocalDate start = LocalDate.of(2024, 1, 1);

    assertEquals(137.1853, strategies.movingAverage(start, 30, stock), 0.01);
  }

  @Test
  public void testMovingAverageOnASunday() {
    LocalDate start = LocalDate.of(2024, 5, 7);

    assertEquals(159.7937, strategies.movingAverage(start, 30, stock), 0.01);
  }

  @Test
  public void testMovingAverageOnASaturday() {
    LocalDate start = LocalDate.of(2024, 5, 6);

    assertEquals(159.066, strategies.movingAverage(start, 30, stock), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovingAverage100DaysOverDaysStored() {
    LocalDate start = LocalDate.of(2014, 5, 7);

    assertEquals(568.66, strategies.movingAverage(start, 100, stock), 0.01);
  }


  @Test
  public void testMovingAverage366Days() {
    LocalDate start = LocalDate.of(2024, 5, 7);

    assertEquals(124.7976, strategies.movingAverage(start, 366, stock), 0.01);
  }


  @Test
  public void testMovingAverageNoEndDate() {
    LocalDate start = LocalDate.of(2024, 5, 28);
    assertEquals(177.39, strategies.movingAverage(start, 5, stock), 0.01);
  }


}
