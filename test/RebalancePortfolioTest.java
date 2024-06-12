import org.junit.Before;
import org.junit.Test;


import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;

import command.IPortfolioStrategies;
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

public class RebalancePortfolioTest {

  Appendable a = new StringBuilder();
  IView view = new ViewImpl(a);
  ISmartPortfolio portfolio = new SmartPortfolio("Technology");

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
  IStock appleStock = stockBuilderNike.buildStock("AAPL", fileReaderApple);

  RebalancePortfolio rebalance = new RebalancePortfolio(view);
  LocalDate today = LocalDate.of(2024, 6, 7);

  @Before
  public void setUp() throws Exception {
    portfolio.addStockShare("NKE", nikeStock, 15); // 1448.25
    portfolio.addStockShare("GOOG", googleStock, 5); // 879.75
    portfolio.addStockShare("AMZN", amazonStock, 15); // 2,764.5
    portfolio.addStockShare("AAPL", appleStock, 5); // 984.45
  }

  @Test
  public void testRebalance() throws FileNotFoundException {
    ISmartPortfolio expected = new SmartPortfolio("Technology");

    double priceOfNikeStock = nikeStock.getStockValue(today);
    System.out.println(priceOfNikeStock);
    double priceOfAmazonStock = amazonStock.getStockValue(today);
    System.out.println(priceOfAmazonStock);
    double priceOfGoogleStock = googleStock.getStockValue(today);
    System.out.println(priceOfGoogleStock);
    double priceAppleStock = appleStock.getStockValue(today);
    System.out.println(priceAppleStock);

    HashMap<String, Double> distrbute = new HashMap<>();
    distrbute.put("GOOG", .25);
    distrbute.put("AMZN", .25);
    distrbute.put("APPL", .25);
    distrbute.put("NKE", .25);



//    assertEquals(
//            rebalance.rebalancePortolfio(distrbute, portfolio, today));
  }

}
