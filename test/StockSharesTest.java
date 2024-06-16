import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import model.Stock;
import model.StockShares;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test class that guarantees that shares are properly  being stored.
 */
public class StockSharesTest {
  private StockShares stockShares1;
  private StockShares stockShares2;
  private StockShares stockShares3;


  @Before
  public void setUp() throws FileNotFoundException {
    this.stockShares1 = new StockShares(100, new Stock("GOOG"));
    this.stockShares2 = new StockShares(100, new Stock("GOOG"));
    this.stockShares3 = new StockShares(300, new Stock("GOOG"));
  }

  @Test
  public void testGetShares() {

    assertEquals(100, this.stockShares1.getShares(), 0.01);
  }


  @Test
  public void testGetStock() {
    assertEquals(new Stock("GOOG"),
            this.stockShares2.getStock());
  }

  @Test
  public void testEquals() {

    assertTrue(stockShares1.equals(stockShares2));
  }

  @Test
  public void testNotEquals() {

    assertFalse(stockShares3.equals(stockShares2));
  }


  @Test
  public void testToString() {


    String expected = "StockSharesshares= 100.0, stock= StocktickerSymbol='GOOG'," +
            " mostRecentDate=null, earliestDate=null, allStocks={}";

    assertEquals(expected, stockShares2.toString());
  }



}

