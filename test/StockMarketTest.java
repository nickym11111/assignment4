import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;

import model.IPortfolio;
import model.ISmartPortfolio;
import model.IStock;
import model.Stock;
import model.StockMarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * A test class that make sures that the portfolios and stocks
 * are properly being stored.
 */
public class StockMarketTest {

  IStock copyOfGoogle = new Stock("GOOG");
  private StockMarket stockMarket;
  private IStock stock;

  @Before
  public void setUp() throws FileNotFoundException {
    stockMarket = new StockMarket();
    stock = new Stock("GOOG");

  }

  @Test
  public void testAddStock() {
    stockMarket.addStock("GOOG", stock);
    assertTrue(stockMarket.checkStock("GOOG"));
  }


  @Test
  public void testAddPortfolio() {
    stockMarket.addPortfolio("Tech");
    assertTrue(stockMarket.checkPortfolio("Tech"));
  }

  @Test
  public void testCheckPortfolioNotPresent() {

    assertFalse(stockMarket.checkPortfolio("Health"));
  }

  @Test
  public void testGetPortfolio() {
    stockMarket.addPortfolio("Finance");
    IPortfolio portfolio = stockMarket.getPortfolio("Finance");

    assertEquals("Finance", portfolio.getName());
  }

  @Test
  public void testGetPortfolioNotPresent() {

    assertNull(stockMarket.getPortfolio("Unknown"));
  }

  @Test
  public void testGetPortfolios() {
    stockMarket.addPortfolio("Tech");
    stockMarket.addPortfolio("Health");
    Map<String, ISmartPortfolio> portfolios = stockMarket.getPortfolios();

    assertTrue(portfolios.containsKey("Tech"));
    assertTrue(portfolios.containsKey("Health"));
  }

  @Test
  public void testGetStocks() {
    stockMarket.addStock("GOOG", stock);
    Map<String, IStock> stocks = stockMarket.getStocks();

    assertTrue(stocks.containsKey("GOOG"));
  }

  @Test
  public void testAddDuplicateStock() {
    stockMarket.addStock("GOOG", stock);
    stockMarket.addStock("GOOG", copyOfGoogle);

    Map<String, IStock> stocks = stockMarket.getStocks();

    assertEquals(10, stocks.size());
    assertEquals(copyOfGoogle, stocks.get("GOOG"));
  }

  @Test
  public void testAddStockWithEmptyTickerSymbol() {

    stockMarket.addStock("", stock);
    assertEquals("GOOG", stock.getTickerSymbol());
  }


}