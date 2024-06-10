import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.IPortfolio;
import model.IStock;
import model.Portfolio;
import model.StockShares;

import static org.junit.Assert.assertEquals;

/**
 * A test class that guarantees that different functionality of portfolio
 * property works and can be obtained.
 */
public class PortfolioTest {
  private IStockBuilder amazonBuilder;
  private IStockDataStream fileReaderAmazon;
  private IPortfolio portfolio;
  private IStock amazonStock;





  @Before
  public void setUp() {
    this.amazonBuilder = new StockBuilderImpl();
    this.fileReaderAmazon = new FileStockDataStreamImpl("stocks/AMZN.csv");
    this.amazonStock = amazonBuilder.buildStock("AMZN", fileReaderAmazon);
    this.portfolio = new Portfolio("Technology");
  }


  @Test
  public void testAddStockShare() throws FileNotFoundException {
    StockShares expected = new StockShares(5, this.amazonStock);
    this.portfolio.addStockShare("AMZN", this.amazonStock, 5);


    assertEquals(expected, this.portfolio.getStocksShareMap().get("AMZN"));
  }

  @Test
  public void testGetStockShare() throws FileNotFoundException {
    StockShares expected = new StockShares(5, this.amazonStock);
    this.portfolio.addStockShare("AMZN", this.amazonStock, 5);
    HashMap<String, StockShares> expectedMap = new HashMap<>();
    expectedMap.put("AMZN", expected);


    assertEquals(expectedMap, this.portfolio.getStocksShareMap());
  }


  @Test
  public void testGetName() {

    assertEquals("Technology", portfolio.getName());
  }






}