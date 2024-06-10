import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import command.Crossovers;
import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.IStock;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that when a user specifically checks
 * for cross over days they receive the proper data.
 */
public class CrossOverDaysTest {

  IStockDataStream data = new FileStockDataStreamImpl("stocks/GOOG.csv");
  IStockBuilder stockBuilder = new StockBuilderImpl();
  IStock stock = stockBuilder.buildStock("GOOG", data);
  Appendable a = new StringBuilder();
  IView view = new ViewImpl(a);
  Crossovers strategies = new Crossovers(view);


  @Test
  public void testGetCrossOverDaysWithin30days() {
    LocalDate start = LocalDate.of(2024, 5, 1);
    LocalDate end = LocalDate.of(2024, 5, 31);


    ArrayList<LocalDate> expected = new ArrayList<>();
    expected.add(LocalDate.of(2024, 5, 1));
    expected.add(LocalDate.of(2024, 5, 2));
    expected.add(LocalDate.of(2024, 5, 3));
    expected.add(LocalDate.of(2024, 5, 6));
    expected.add(LocalDate.of(2024, 5, 7));
    expected.add(LocalDate.of(2024, 5, 8));
    expected.add(LocalDate.of(2024, 5, 9));
    expected.add(LocalDate.of(2024, 5, 10));
    expected.add(LocalDate.of(2024, 5, 13));
    expected.add(LocalDate.of(2024, 5, 14));
    expected.add(LocalDate.of(2024, 5, 15));
    expected.add(LocalDate.of(2024, 5, 16));
    expected.add(LocalDate.of(2024, 5, 17));
    expected.add(LocalDate.of(2024, 5, 20));
    expected.add(LocalDate.of(2024, 5, 21));
    expected.add(LocalDate.of(2024, 5, 22));
    expected.add(LocalDate.of(2024, 5, 23));
    expected.add(LocalDate.of(2024, 5, 24));
    expected.add(LocalDate.of(2024, 5, 28));
    expected.add(LocalDate.of(2024, 5, 29));
    expected.add(LocalDate.of(2024, 5, 30));
    expected.add(LocalDate.of(2024, 5, 31));


    assertEquals(expected, strategies.getCrossoverDays(start, end, 30, stock));

  }


  @Test
  public void testGetCrossOverDaysWithin5days() {
    LocalDate start = LocalDate.of(2024, 5, 1);
    LocalDate end = LocalDate.of(2024, 5, 8);

    ArrayList<LocalDate> expected = new ArrayList<>();
    expected.add(LocalDate.of(2024, 5, 2));
    expected.add(LocalDate.of(2024, 5, 3));
    expected.add(LocalDate.of(2024, 5, 6));
    expected.add(LocalDate.of(2024, 5, 7));
    expected.add(LocalDate.of(2024, 5, 8));


    assertEquals(expected, strategies.getCrossoverDays(start, end, 5, stock));
  }

  @Test
  public void testGetCrossOverDaysWithin30daysNewYear() {
    LocalDate start = LocalDate.of(2023, 12, 31);
    LocalDate end = LocalDate.of(2024, 1, 30);

    ArrayList<LocalDate> expected = new ArrayList<>();
    expected.add(LocalDate.of(2023, 12, 29));
    expected.add(LocalDate.of(2024, 1, 2));
    expected.add(LocalDate.of(2024, 1, 3));
    expected.add(LocalDate.of(2024, 1, 4));
    expected.add(LocalDate.of(2024, 1, 5));
    expected.add(LocalDate.of(2024, 1, 8));
    expected.add(LocalDate.of(2024, 1, 9));
    expected.add(LocalDate.of(2024, 1, 10));
    expected.add(LocalDate.of(2024, 1, 11));
    expected.add(LocalDate.of(2024, 1, 12));
    expected.add(LocalDate.of(2024, 1, 16));
    expected.add(LocalDate.of(2024, 1, 17));
    expected.add(LocalDate.of(2024, 1, 18));
    expected.add(LocalDate.of(2024, 1, 19));
    expected.add(LocalDate.of(2024, 1, 22));
    expected.add(LocalDate.of(2024, 1, 23));
    expected.add(LocalDate.of(2024, 1, 24));
    expected.add(LocalDate.of(2024, 1, 25));
    expected.add(LocalDate.of(2024, 1, 26));
    expected.add(LocalDate.of(2024, 1, 29));
    expected.add(LocalDate.of(2024, 1, 30));


    assertEquals(expected, strategies.getCrossoverDays(start, end, 30, stock));
  }

  @Test
  public void testCrossOverDaysOverWithNoCrossOvers() {
    LocalDate start = LocalDate.of(2015, 3, 31);
    LocalDate end = LocalDate.of(2015, 4, 5);

    ArrayList<LocalDate> expected = new ArrayList<>();

    assertEquals(expected, strategies.getCrossoverDays(start, end, 5, stock));
  }


  @Test
  public void testCrossOverDaysOverStartingDayDoesNotExist() {
    LocalDate start = LocalDate.of(2015, 3, 21);
    LocalDate end = LocalDate.of(2015, 4, 3);

    ArrayList<LocalDate> expected = new ArrayList<>();
    expected.add(LocalDate.of(2015, 3, 20));
    expected.add(LocalDate.of(2015, 3, 23));
    expected.add(LocalDate.of(2015, 3, 24));

    assertEquals(expected, strategies.getCrossoverDays(start, end, 5, stock));
  }

  @Test
  public void testCrossOverDaysOverEndingDayDoesNotExist() {
    LocalDate start = LocalDate.of(2015, 3, 16);
    LocalDate end = LocalDate.of(2015, 3, 21);

    ArrayList<LocalDate> expected = new ArrayList<>();
    expected.add(LocalDate.of(2015, 3, 16));
    expected.add(LocalDate.of(2015, 3, 18));
    expected.add(LocalDate.of(2015, 3, 19));
    expected.add(LocalDate.of(2015, 3, 20));

    assertEquals(expected, strategies.getCrossoverDays(start, end, 5, stock));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testCrossOverDaysOverStoredValues() {
    LocalDate start = LocalDate.of(1997, 5, 16);
    LocalDate end = LocalDate.of(2015, 3, 21);

    ArrayList<LocalDate> expected = new ArrayList<>();
    expected.add(LocalDate.of(2015, 3, 16));
    expected.add(LocalDate.of(2015, 3, 18));
    expected.add(LocalDate.of(2015, 3, 19));
    expected.add(LocalDate.of(2015, 3, 20));

    assertEquals(expected, strategies.getCrossoverDays(start, end, 5, stock));
  }


  @Test
  public void testCrossOverDaysInTheFuture() {
    LocalDate start = LocalDate.of(2028, 5, 16);
    LocalDate end = LocalDate.of(2015, 3, 21);

    ArrayList<LocalDate> expected = new ArrayList<>();

    assertEquals(expected, strategies.getCrossoverDays(start, end, 5, stock));
  }

  @Test
  public void testDifferentStocks() {

    IStockDataStream data2 = new FileStockDataStreamImpl("stocks/AAPL.csv");
    IStock stock2 = stockBuilder.buildStock("AAPL", data2);


    LocalDate start = LocalDate.of(2024, 5, 1);
    LocalDate end = LocalDate.of(2024, 5, 31);

    ArrayList<LocalDate> expected = new ArrayList<>();
    expected.add(LocalDate.of(2024, 5, 2));
    expected.add(LocalDate.of(2024, 5, 3));
    expected.add(LocalDate.of(2024, 5, 6));
    expected.add(LocalDate.of(2024, 5, 7));
    expected.add(LocalDate.of(2024, 5, 8));
    expected.add(LocalDate.of(2024, 5, 9));
    expected.add(LocalDate.of(2024, 5, 10));
    expected.add(LocalDate.of(2024, 5, 13));
    expected.add(LocalDate.of(2024, 5, 14));
    expected.add(LocalDate.of(2024, 5, 15));
    expected.add(LocalDate.of(2024, 5, 16));
    expected.add(LocalDate.of(2024, 5, 17));
    expected.add(LocalDate.of(2024, 5, 20));
    expected.add(LocalDate.of(2024, 5, 21));
    expected.add(LocalDate.of(2024, 5, 22));
    expected.add(LocalDate.of(2024, 5, 23));
    expected.add(LocalDate.of(2024, 5, 24));
    expected.add(LocalDate.of(2024, 5, 28));
    expected.add(LocalDate.of(2024, 5, 29));
    expected.add(LocalDate.of(2024, 5, 30));
    expected.add(LocalDate.of(2024, 5, 31));

    assertEquals(expected, strategies.getCrossoverDays(start, end, 30, stock2));
  }


  @Test
  public void testCrossOverSundaySaturday() {

    LocalDate start = LocalDate.of(2024, 5, 4); // Friday
    LocalDate end = LocalDate.of(2024, 5, 5); // Monday

    ArrayList<LocalDate> expected = new ArrayList<>();
    expected.add(LocalDate.of(2024, 5, 3));


    assertEquals(expected, strategies.getCrossoverDays(start, end, 1, stock));
  }


  @Test
  public void testCrossOverOverTheWeekend() {

    LocalDate end = LocalDate.of(2024, 5, 3); // Friday
    LocalDate start = LocalDate.of(2024, 5, 6); // Monday

    ArrayList<LocalDate> expected = new ArrayList<>();


    assertEquals(expected, strategies.getCrossoverDays(start, end, 3, stock));
  }


  @Test
  public void testOverAHoliday() {
    LocalDate end = LocalDate.of(2024, 3, 29); // Friday
    LocalDate start = LocalDate.of(2024, 3, 31);

    ArrayList<LocalDate> expected = new ArrayList<>();


    assertEquals(expected, strategies.getCrossoverDays(start, end, 2, stock));
  }


}
