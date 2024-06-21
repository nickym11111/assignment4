import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.DateTimeException;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This test class looks at the SmartPortfolio to ensure the functionality
 * properly works such as adding and removing stock shares. Additionally
 * obtain and getting information such as the current, bought, sold shares
 * of stocks.
 */
public class SmartPortfolioTest {
  String filePath = "stocks/AMZN.csv";
  IStockBuilder amazonBuilder = new StockBuilderImpl();
  IStockDataStream fileReaderAmazon = new FileStockDataStreamImpl(filePath);
  IStock amazonStock = amazonBuilder.buildStock("AMZN", fileReaderAmazon);
  ISmartPortfolio portfolio = new SmartPortfolio("Technology");
  LocalDate present = LocalDate.of(2024, 5, 1);
  ArrayList<ISmartStockShares> expectedFiveSharesOfAmazon = new ArrayList<>();
  ISmartStockShares fiveSharesOfAmazon;

  LocalDate weekend = LocalDate.of(2024, 5, 4);
  LocalDate holiday = LocalDate.of(2023, 5, 29);

  @Before
  public void setUp() throws Exception {
    this.fiveSharesOfAmazon = new SmartStockShares(5.0, amazonStock, present);

  }


  @Test
  public void testAddStockShare() throws FileNotFoundException {
    ISmartStockShares expectedShare = new SmartStockShares(5, amazonStock,
            LocalDate.of(2024, 12, 5));

    portfolio.addStockShare("AMZN", amazonStock, 5, present);

    assertEquals(1, portfolio.getCurrentStockSharesMap().size());

    assertEquals(5.0, portfolio.getCurrentStockSharesMap()
            .get("AMZN").getShares(), 0.01);
  }

  @Test
  public void testAddStockShareWeekend() throws FileNotFoundException {
    ISmartStockShares expectedShare = new SmartStockShares(5, amazonStock,
            LocalDate.of(2024, 12, 5));

    portfolio.addStockShare("AMZN", amazonStock, 5, weekend);

    assertEquals(1, portfolio.getCurrentStockSharesMap().size());

    assertEquals(5.0, portfolio.getCurrentStockSharesMap()
            .get("AMZN").getShares(), 0.01);
  }

  @Test
  public void testAddShareNegative() throws FileNotFoundException {
    assertEquals(0, portfolio.getStockShareMap().size());

    try {
      portfolio.addStockShare("AMZN", amazonStock, -5, present);
    } catch (IllegalArgumentException e) {
      assertEquals("You cannot buy a negative amount of stock.", e.getMessage());
    }


  }


  @Test
  public void testAddStockShareHoliday() throws FileNotFoundException {
    ISmartStockShares expectedShare = new SmartStockShares(5, amazonStock,
            LocalDate.of(2024, 12, 5));

    portfolio.addStockShare("AMZN", amazonStock, 5, holiday);

    assertEquals(1,
            portfolio.getCurrentStockSharesMap().size());

    assertEquals(5.0, portfolio
            .getCurrentStockSharesMap().get("AMZN").getShares(), 0.01);
  }

  @Test(expected = DateTimeException.class)
  public void testAddStockShareNegativeDate() throws FileNotFoundException {
    LocalDate negativeDate = LocalDate.of(2024, 12, -5);

    portfolio.addStockShare("AMZN", amazonStock, 5, negativeDate);

    assertEquals(1,
            portfolio.getCurrentStockSharesMap().size());

    assertEquals(5.0, portfolio
            .getCurrentStockSharesMap().get("AMZN").getShares(), 0.01);
  }


  @Test
  public void testRemoveStockShare() throws FileNotFoundException {
    assertEquals(0, portfolio.getStockShareMap().size());
    portfolio.addStockShare("AMZN", amazonStock, 5, present);

    assertEquals(5, portfolio.getCurrentStockSharesMap()
            .get("AMZN").getShares(), 0.01);

    portfolio.removeStockShare("AMZN", 2, present);

    assertEquals(3, portfolio.getCurrentStockSharesMap()
            .get("AMZN").getShares(), 0.01);
  }

  @Test
  public void testRemoveStockShareNegative() throws FileNotFoundException {
    assertEquals(0, portfolio.getStockShareMap().size());
    portfolio.addStockShare("AMZN", amazonStock, 5, present);

    assertEquals(5, portfolio.getCurrentStockSharesMap()
            .get("AMZN").getShares(), 0.01);

    try {
      portfolio.removeStockShare("AMZN", -2, present);
    } catch (IllegalArgumentException e) {
      assertEquals("You cannot sell a negative amount of stock.", e.getMessage());
    }

  }


  @Test
  public void testNoInOrder() throws FileNotFoundException {
    LocalDate date = LocalDate.of(2005, 5, 1);

    try {
      portfolio.addStockShare("AMZN", amazonStock, 5, date);
      portfolio.removeStockShare("AMZN", 5, date.minusDays(1));
    } catch (IllegalArgumentException e) {
      assertEquals("You cannot sell a stock from before " +
              "the most recent transaction 2005-05-01", e.getMessage());
    }


  }


  @Test
  public void testPortfolioStateAtDate() throws FileNotFoundException {
    LocalDate date = LocalDate.of(2005, 5, 1);


    portfolio.addStockShare("AMZN", amazonStock, 5, date);

    ISmartStockShares expected3 = new SmartStockShares(5, amazonStock, date);
    Map<String, ISmartStockShares> list = new HashMap<>();
    list.put("AMZN", expected3);


    assertEquals(list, portfolio.portfolioStateAtDate(date));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioStateSellStockMoreThanOwned() throws FileNotFoundException {
    LocalDate date = LocalDate.of(2005, 5, 1);

    portfolio.addStockShare("AMZN", amazonStock, 5, date);
    portfolio.removeStockShare("AMZN", 8, date);

    ISmartStockShares expected3 = new SmartStockShares(5, amazonStock, date);
    Map<String, ISmartStockShares> list = new HashMap<>();
    list.put("AMZN", expected3);


    assertEquals(list, portfolio.portfolioStateAtDate(date));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioStateSellingAStockNeverOwned() throws FileNotFoundException {
    LocalDate date = LocalDate.of(2005, 5, 1);

    portfolio.addStockShare("AMZN", amazonStock, 5, date);
    portfolio.removeStockShare("GOOG", 8, date);

    ISmartStockShares expected3 = new SmartStockShares(5, amazonStock, date);
    Map<String, ISmartStockShares> list = new HashMap<>();
    list.put("AMZN", expected3);


    assertEquals(list, portfolio.portfolioStateAtDate(date));

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

  @Test(expected = DateTimeException.class)
  public void testNegativeInput() throws FileNotFoundException {
    LocalDate invalidDate = LocalDate.of(2005, -5, 1);

    portfolio.addStockShare("AMZN", amazonStock, 5, invalidDate);

  }

  @Test
  public void testHasStocksFalsePresentDay() {

    assertFalse(portfolio.hasStockAtDate(present, "NKE"));

  }


  @Test
  public void testHasStocksFalseHoliday() {

    assertFalse(portfolio.hasStockAtDate(holiday, "NKE"));

  }

  @Test
  public void testHasStocksTrueHoliday() throws FileNotFoundException {
    portfolio.addStockShare("AMZN", amazonStock, 5, present);

    assertTrue(portfolio.hasStockAtDate(holiday, "AMZN"));

  }

  @Test
  public void testHasStocksTruePresent() throws FileNotFoundException {
    portfolio.addStockShare("AMZN", amazonStock, 5, present);

    assertTrue(portfolio.hasStockAtDate(present, "AMZN"));

  }


  @Test
  public void testAddExistingStock() throws FileNotFoundException {
    portfolio.addStockShare("AMZN", amazonStock, 5, present);
    portfolio.addExistingStock("AMZN", present, 5);


    assertEquals(portfolio.getBoughtStockSharesMap().get("AMZN").getFirst()
            .getShares(), 5.0, 0.01);

    assertEquals(portfolio.getBoughtStockSharesMap().size(), 1);

    assertTrue(portfolio.hasStockAtDate(present, "AMZN"));
  }

  @Test
  public void testAddExistingStockNegative() throws FileNotFoundException {
    portfolio.addStockShare("AMZN", amazonStock, 5, present);

    try {
      portfolio.addExistingStock("AMZN", present, 5);
    } catch (IllegalArgumentException e) {
      assertEquals("You cannot buy negative shares of a stock.", e.getMessage());
    }

  }

  @Test
  public void testAddExistingStockNotOrder() throws FileNotFoundException {
    portfolio.addStockShare("AMZN", amazonStock, 5, present);


    try {
      portfolio.addExistingStock("AMZN", present.minusYears(1), 5);
    } catch (IllegalArgumentException e) {
      assertEquals("You cannot buy a stock from before the most " +
              "recent transaction :2024-05-01", e.getMessage());
    }

  }

  @Test
  public void testAddExistingStockOnHoliday() throws FileNotFoundException {
    portfolio.addStockShare("AMZN", amazonStock, 5, holiday);
    portfolio.addExistingStock("AMZN", holiday, 5);


    assertEquals(portfolio.getBoughtStockSharesMap().get("AMZN").getFirst()
            .getShares(), 5.0, 0.01);

    assertEquals(portfolio.getBoughtStockSharesMap().size(), 1);

    assertTrue(portfolio.hasStockAtDate(holiday, "AMZN"));
  }


}