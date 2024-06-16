import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;

import command.ValuePortfolio;
import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.ISmartPortfolio;
import model.IStock;
import model.SmartPortfolio;
import model.StockShares;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * A test class that guarantees that a user is allowed to properly view
 * the value of stocks in their propfilio that created.
 */
public class ValuePortfolioTest {
  IStockDataStream data = new FileStockDataStreamImpl("stocks/GOOG.csv");
  IStockBuilder stockBuilder = new StockBuilderImpl();
  IStock stock = stockBuilder.buildStock("GOOG", data);
  Appendable a = new StringBuilder();
  IView view = new ViewImpl(a);
  ValuePortfolio strategies = new ValuePortfolio(view);
  ISmartPortfolio portfolio = new SmartPortfolio("Technology");
  IStockDataStream fileReaderAmazon = new FileStockDataStreamImpl("stocks/AMZN.csv");
  IStockBuilder stockBuilderAmazon = new StockBuilderImpl();

  IStock amazonStock = stockBuilderAmazon.buildStock("AMZN", fileReaderAmazon);
  LocalDate holiday = LocalDate.of(2024, 2, 14);
  LocalDate may = LocalDate.of(2022, 5, 1);


  @Test
  public void testCreatePortfolioWithOneStock() throws FileNotFoundException {

    portfolio.addStockShare("GOOG", this.stock, 5, may);

    assertEquals("Technology", portfolio.getName());

    assertEquals(5, portfolio.getCurrentStockSharesMap()
            .get("GOOG").getShares(), 0.01);
  }


  @Test
  public void testReturnPortfolioPrice() throws FileNotFoundException {

    StockShares stockShares = new StockShares(5, this.stock);

    LocalDate date = LocalDate.of(2014, 5, 1);
    portfolio.addStockShare("GOOG", stock, 5, date);


    assertEquals(2656.75, strategies.getPortfolioValue(date, portfolio), 0.01);
  }


  @Test
  public void testReturnPortfolioPrice3Stock() throws FileNotFoundException {
    LocalDate date = LocalDate.of(2014, 5, 1);

    portfolio.addStockShare("AMZN", this.amazonStock, 7, date);
    portfolio.addStockShare("GOOG", this.stock, 5, date);
    //nicky.addStockShare("NKE", this.nikeStock, 10);

    assertEquals(4811.98, strategies.getPortfolioValue(date, portfolio), 0.01);
  }


  @Test
  public void testReturnPortfolioPriceOnDayThatDoesNotExist() throws FileNotFoundException {


    portfolio.addStockShare("GOOG", stock, 5, may);

    assertEquals(735.69, strategies.getPortfolioValue(holiday, portfolio), 0.01);
  }

  @Test(expected = DateTimeException.class)
  public void testReturnPortfolioPriceNegativeDay() throws FileNotFoundException {
    LocalDate newDate = LocalDate.of(2014, -5, 1);
    portfolio.addStockShare("GOOG", stock, 5, may);

    assertEquals(735.69, strategies.getPortfolioValue(newDate, portfolio), 0.01);
  }


  @Test
  public void testReturnPortfolioPriceOnDayThatDoesNotExistThrowAndCatch()
          throws FileNotFoundException {


    this.portfolio.addStockShare("GOOG", stock, 5);

    try {
      strategies.getPortfolioValue(holiday, portfolio);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Stock does not exist");
    }


  }

  @Test
  public void testPortfolioWithZeroShares() throws FileNotFoundException {
    portfolio.addStockShare("GOOG", stock, 0, may);
    LocalDate date = LocalDate.of(2014, 5, 1);
    assertEquals(0.0, strategies.getPortfolioValue(date, portfolio), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioOnFutureDate() throws FileNotFoundException {
    portfolio.addStockShare("GOOG", stock, 5, may);
    LocalDate futureDate = LocalDate.of(2030, 1, 1);
    assertEquals(0.0, strategies.getPortfolioValue(futureDate, portfolio), 0.01);
  }

  @Test
  public void testEmptyPortfolio() throws FileNotFoundException {
    ISmartPortfolio portfolio1 = new SmartPortfolio("Technology");
    LocalDate date = LocalDate.of(2014, 5, 1);
    assertEquals(0.0, strategies.getPortfolioValue(date, portfolio1), 0.01);
  }

  @Test(expected = DateTimeException.class)
  public void testNegativeDatePortfolio() throws FileNotFoundException {
    ISmartPortfolio portfolio1 = new SmartPortfolio("Technology");
    LocalDate date = LocalDate.of(2014, -5, 1);
    assertEquals(0.0, strategies.getPortfolioValue(date, portfolio1), 0.01);
  }


}
