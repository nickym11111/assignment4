import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

import command.RebalancePortfolio;
import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.ISmartPortfolio;
import model.IStock;
import model.SmartPortfolio;
import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * A test class that reassures that the user's portfolio has been
 * rebalanced.
 */
public class RebalancePortfolioTest {

  Appendable a = new StringBuilder();
  IView view = new ViewImpl(a);
  RebalancePortfolio rebalance = new RebalancePortfolio(view);
  ISmartPortfolio portfolio = new SmartPortfolio("Technology");
  StringReader in = new StringReader("");
  Scanner s = new Scanner(in);
  IStockDataStream fileReaderGoogle = new FileStockDataStreamImpl("stocks/GOOG.csv");
  IStockBuilder stockBuilder = new StockBuilderImpl();
  IStock googleStock = stockBuilder.buildStock("GOOG", fileReaderGoogle);

  IStockBuilder stockBuilderAmazon = new StockBuilderImpl();
  IStockDataStream fileReaderAmazon = new FileStockDataStreamImpl("stocks/AMZN.csv");
  IStock amazonStock = stockBuilderAmazon.buildStock("AMZN", fileReaderAmazon);


  IStockBuilder stockBuilderNike = new StockBuilderImpl();
  IStockDataStream fileReaderNike = new FileStockDataStreamImpl("stocks/NKE.csv");
  IStock nikeStock = stockBuilderNike.buildStock("NKE", fileReaderNike);


  IStockBuilder stockBuilderApple = new StockBuilderImpl();
  IStockDataStream fileReaderApple = new FileStockDataStreamImpl("stocks/AAPL.csv");
  IStock appleStock = stockBuilderApple.buildStock("AAPL", fileReaderApple);

  LocalDate today = LocalDate.of(2024, 6, 7);
  LocalDate holiday = LocalDate.of(2024, 3, 29);

  @Before
  public void setUp() throws Exception {
    portfolio.addStockShare("NKE", nikeStock, 15, today); // 1448.25
    portfolio.addStockShare("GOOG", googleStock, 5, today); // 879.75
    portfolio.addStockShare("AMZN", amazonStock, 15, today); // 2,764.5
    portfolio.addStockShare("AAPL", appleStock, 5, today); // 984.45
  }


  @Test
  public void testRebalance() throws FileNotFoundException {
    ISmartPortfolio expected = new SmartPortfolio("Technology");

    expected.addStockShare("NKE", nikeStock, 15.73, today);    //
    expected.addStockShare("GOOG", googleStock, 8.629, today); // 1321.875
    expected.addStockShare("AMZN", amazonStock, 8.25, today); // 2764.5
    expected.addStockShare("AAPL", appleStock, 7.71, today);


    HashMap<String, Double> distrbute = new HashMap<>();
    distrbute.put("GOOG", .25);
    distrbute.put("AMZN", .25);
    distrbute.put("AAPL", .25);
    distrbute.put("NKE", .25);


    rebalance.rebalancePortfolio(distrbute, portfolio, today);


    assertEquals(expected.getCurrentStockSharesMap().get("GOOG").getShares(),
            portfolio.getCurrentStockSharesMap().get("GOOG").getShares(), 0.01);

    assertEquals(expected.getCurrentStockSharesMap().get("AMZN").getShares(),
            portfolio.getCurrentStockSharesMap().get("AMZN").getShares(), 0.01);

    assertEquals(expected.getCurrentStockSharesMap().get("AAPL").getShares(),
            portfolio.getCurrentStockSharesMap().get("AAPL").getShares(), 0.01);

    assertEquals(expected.getCurrentStockSharesMap().get("NKE").getShares(),
            portfolio.getCurrentStockSharesMap().get("NKE").getShares(), 0.01);

  }

  // in the future it throws expcetion
  @Test(expected = IllegalArgumentException.class)
  public void testFutrueDayRebalance() throws FileNotFoundException {
    LocalDate futurDate = LocalDate.of(2025, 6, 7);
    ISmartPortfolio expected = new SmartPortfolio("Technology");

    HashMap<String, Double> distrbute = new HashMap<>();
    distrbute.put("GOOG", .25);
    distrbute.put("AMZN", .25);
    distrbute.put("AAPL", .25);
    distrbute.put("NKE", .25);

    rebalance.rebalancePortfolio(distrbute, portfolio, futurDate);

  }

  @Test(expected = DateTimeException.class)
  public void testNegativeDay() throws FileNotFoundException {
    LocalDate futurDate = LocalDate.of(2025, 6, -7);
    ISmartPortfolio expected = new SmartPortfolio("Technology");

    HashMap<String, Double> distrbute = new HashMap<>();
    distrbute.put("GOOG", .25);
    distrbute.put("AMZN", .25);
    distrbute.put("AAPL", .25);
    distrbute.put("NKE", .25);

    rebalance.rebalancePortfolio(distrbute, portfolio, futurDate);

  }


  // On a holiday it gets the most current shares
  @Test
  public void testHolidayRebalance() throws FileNotFoundException {
    ISmartPortfolio expected = new SmartPortfolio("Technology");

    HashMap<String, Double> distrbute = new HashMap<>();
    distrbute.put("GOOG", .25);
    distrbute.put("AMZN", .25);
    distrbute.put("AAPL", .25);
    distrbute.put("NKE", .25);

    expected.addStockShare("NKE", nikeStock, 15.0, today);    //
    expected.addStockShare("GOOG", googleStock, 5.0, today); // 1321.875
    expected.addStockShare("AMZN", amazonStock, 15.0, today); // 2764.5
    expected.addStockShare("AAPL", appleStock, 5.0, today);

    rebalance.rebalancePortfolio(distrbute, portfolio, holiday);

    assertEquals(expected.getCurrentStockSharesMap().get("GOOG").getShares(),
            portfolio.getCurrentStockSharesMap().get("GOOG").getShares(), 0.01);

    assertEquals(expected.getCurrentStockSharesMap().get("AMZN").getShares(),
            portfolio.getCurrentStockSharesMap().get("AMZN").getShares(), 0.01);

    assertEquals(expected.getCurrentStockSharesMap().get("AAPL").getShares(),
            portfolio.getCurrentStockSharesMap().get("AAPL").getShares(), 0.01);

    assertEquals(expected.getCurrentStockSharesMap().get("NKE").getShares(),
            portfolio.getCurrentStockSharesMap().get("NKE").getShares(), 0.01);

  }

  @Test(expected = Exception.class)
  public void testMoreStockNotEnoughPercentage() throws FileNotFoundException {
    ISmartPortfolio expected = new SmartPortfolio("Technology");

    HashMap<String, Double> distrbute = new HashMap<>();
    distrbute.put("GOOG", .25);
    rebalance.rebalancePortfolio(distrbute, portfolio, today);
  }




  @Test
  public void testMorePercentDoesNotAddUpTo100() throws FileNotFoundException {
    ISmartPortfolio expected = new SmartPortfolio("Technology");

    try {
      HashMap<String, Double> distrbute = new HashMap<>();
      distrbute.put("GOOG", .25);
      distrbute.put("AMZN", .90);
      distrbute.put("AAPL", .25);
      distrbute.put("NKE", .25);
      rebalance.rebalancePortfolio(distrbute, portfolio, today);
    } catch (Exception e) {
      assertEquals("Please look at target distribution for each stock. " +
              "Target distribution percentages cannot go over 1.0 (100%) " +
              "or under 1.0 (100%). \n" +
              "The distribution must equal to 1.0 (100%) " +
              "added all together.\n", e.getMessage());
    }

  }


}






