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
   String filePath = "src/stocks/AMZN.csv";
   IStockBuilder amazonBuilder = new StockBuilderImpl();
   IStockDataStream fileReaderAmazon = new FileStockDataStreamImpl(filePath);
   IStock amazonStock = amazonBuilder.buildStock("AMZN", fileReaderAmazon);
   IPortfolio portfolio =  new Portfolio("Technology");








  @Test
  public void testAddStockShare() throws FileNotFoundException {
    StockShares expected = new StockShares(5, this.amazonStock);
    this.portfolio.addStockShare("AMZN", this.amazonStock, 5);


    assertEquals(expected, this.portfolio.getStockShareMap().get("AMZN"));
  }

  @Test
  public void testGetStockShare() throws FileNotFoundException {
    StockShares expected = new StockShares(5, this.amazonStock);
    this.portfolio.addStockShare("AMZN", this.amazonStock, 5);
    HashMap<String, StockShares> expectedMap = new HashMap<>();
    expectedMap.put("AMZN", expected);


    assertEquals(expectedMap, this.portfolio.getStockShareMap());
  }


  @Test
  public void testGetName() {

    assertEquals("Technology", portfolio.getName());
  }






}