import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

/**
 * This test class looks at the SmartPortfolio to ensure the functionality
 * properly works such as adding and removing stock shares. Additionally
 * obtain and getting information such as the current, bought, sold shares
 * of stocks.
 */
public class SmartPortfolioTest {
  String filePath = "src/stocks/AMZN.csv";
  IStockBuilder amazonBuilder = new StockBuilderImpl();
  IStockDataStream fileReaderAmazon = new FileStockDataStreamImpl(filePath);
  IStock amazonStock = amazonBuilder.buildStock("AMZN", fileReaderAmazon);
  ISmartPortfolio portfolio = new SmartPortfolio("Technology");
  LocalDate present = LocalDate.of(2024, 5, 1);
  ArrayList<ISmartStockShares> expectedFiveSharesOfAmazon = new ArrayList<>();
  ISmartStockShares fiveSharesOfAmazon;

  @Before
  public void setUp() throws Exception {
    this.fiveSharesOfAmazon = new SmartStockShares(5.0, amazonStock, present);

  }


  @Test
  public void testAddStockShare() throws FileNotFoundException {
    ISmartStockShares expectedShare = new SmartStockShares(5, amazonStock,
            LocalDate.of(2024, 12, 5));

    portfolio.addStockShare("AMZN", amazonStock, 5);

    assertEquals(1, portfolio.getStockShareMap().size());

    assertEquals(5.0, portfolio.getStockShareMap().get("AMZN").getShares(), 0.01);
  }


  @Test
  public void testRemoveStockShare() throws FileNotFoundException {
    assertEquals(0, portfolio.getStockShareMap().size());
    portfolio.addStockShare("AMZN", amazonStock, 5, present);

    assertEquals(5, portfolio.getCurrentStockSharesMap().get("AMZN").getShares(), 0.01);

    portfolio.removeStockShare("AMZN", 2, present);

    assertEquals(3, portfolio.getCurrentStockSharesMap().get("AMZN").getShares(), 0.01);
  }


  @Test
  public void testPortfolioStateAtDate() {

  }

  @Test
  public void testGetBoughtStockSharesMap() throws FileNotFoundException {
    expectedFiveSharesOfAmazon.add(fiveSharesOfAmazon);

    Map<String, ArrayList<ISmartStockShares>> expected = new HashMap<>();
    expected.put("AMZN", expectedFiveSharesOfAmazon);

    this.portfolio.addStockShare("AMZN", amazonStock, 5, present);

    assertEquals(5,
            this.portfolio.getBoughtStockSharesMap().get("AMZN").getFirst().getShares(), 0.01);
  }

  @Test
  public void testGetSoldStockSharesMap() throws FileNotFoundException {


    expectedFiveSharesOfAmazon.add(fiveSharesOfAmazon);
    Map<String, ArrayList<ISmartStockShares>> list = new HashMap<>();
    list.put("AMZN", expectedFiveSharesOfAmazon);

    this.portfolio.addStockShare("AMZN", amazonStock, 6, present);
    this.portfolio.removeStockShare("AMZN", 3, present);

    assertEquals(3, this.portfolio.getSoldStockSharesMap().get("AMZN")
            .getFirst().getShares(), 0.01);

  }

  @Test
  public void testSetCurrentStockSharesMap() throws FileNotFoundException {
    ISmartStockShares expected3 = new SmartStockShares(2.0, amazonStock, present);
    Map<String, ISmartStockShares> list = new HashMap<>();
    list.put("AMZN", expected3);


    portfolio.setCurrentStockSharesMap(list);
    assertEquals(expected3, portfolio.getCurrentStockSharesMap().get("AMZN"));
  }

  @Test
  public void testSetBoughtStockSharesMap() throws FileNotFoundException {

    expectedFiveSharesOfAmazon.add(fiveSharesOfAmazon);

    Map<String, ArrayList<ISmartStockShares>> expected = new HashMap<>();
    expected.put("AMZN", expectedFiveSharesOfAmazon);

    this.portfolio.setBoughtStockSharesMap(expected);

    assertEquals(5.0, portfolio.getBoughtStockSharesMap()
            .get("AMZN").getFirst().getShares(), 0.01);

    assertEquals(fiveSharesOfAmazon, portfolio.getBoughtStockSharesMap().get("AMZN").getFirst());

  }


  @Test
  public void testSetSoldStockSharesMap() throws FileNotFoundException {

    ArrayList<ISmartStockShares> expectedShares = new ArrayList<>();
    ISmartStockShares expected3 = new SmartStockShares(5.0, amazonStock, present);
    expectedShares.add(expected3);

    Map<String, ArrayList<ISmartStockShares>> expected = new HashMap<>();
    expected.put("AMZN", expectedShares);

    this.portfolio.setSoldStockSharesMap(expected);

    assertEquals(5.0, portfolio.getSoldStockSharesMap()
            .get("AMZN").getFirst().getShares(), 0.01);

    assertEquals(expected3, portfolio.getSoldStockSharesMap().get("AMZN").getFirst());

  }


  @Test
  public void testGetCurrentStockSharesMap() throws FileNotFoundException {
    portfolio.addStockShare("AMZN", amazonStock, 5, present);
    assertEquals(5, portfolio.getCurrentStockSharesMap().get("AMZN")
            .getShares(), 0.01);
  }


}
