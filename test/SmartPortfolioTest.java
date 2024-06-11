import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;

import model.ISmartPortfolio;
import model.ISmartStockShares;
import model.IStock;
import model.SmartPortfolio;
import model.SmartStockShares;

import static org.junit.Assert.assertEquals;
public class SmartPortfolioTest {

  String filePath = "src/stocks/AMZN.csv";
  IStockBuilder amazonBuilder = new StockBuilderImpl();
  IStockDataStream fileReaderAmazon = new FileStockDataStreamImpl(filePath);
  IStock amazonStock = amazonBuilder.buildStock("AMZN", fileReaderAmazon);
  ISmartPortfolio portfolio =  new SmartPortfolio("Technology");


  @Test
  public void addStockShare() throws FileNotFoundException {
    ISmartStockShares expectedShare = new SmartStockShares(5, amazonStock,
            LocalDate.of(2024, 12, 5));

    portfolio.addStockShare("AMZN", amazonStock, 5);

    assertEquals(1, portfolio.getStockShareMap().size());
    assertEquals(expectedShare, portfolio.getStockShareMap().get("AMZN")); //GOING TO FAIL
  }



  @Test
  public void testRemoveStockShare() throws FileNotFoundException {
    assertEquals(0, portfolio.getStockShareMap().size());
    portfolio.addStockShare("AMZN", amazonStock, 5);

  }

  @Test
  public void testsAddStockShare() {

  }
  @Test
  public void testPortfolioStateAtDate() {

  }

  @Test
  public void testGetBoughtStockSharesMap() {

  }

  @Test
  public void testGetSoldStockSharesMap() {}

  @Test
  public void testSetCurrentStockSharesMap() {}
 @Test
  public void testSetBoughtStockSharesMap() {}



  @Test
  public void  testSetSoldStockSharesMap() {

  }



  @Test
  public void testgetCurrentStockSharesMap() {

  }
}
