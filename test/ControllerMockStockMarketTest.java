import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import controller.IController;
import controller.StockController;
import model.IStockMarket;
import model.StockMarketMock;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that a user can properly
 * use our program.
 */
public class ControllerMockStockMarketTest {

  @Test
  public void testInputStock() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("stock\nf\nstocks/AMZN.csv\nAMZN\nb\nq\n");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.go();
    assertEquals("addStock (AMZN) ", log.toString());
  }

  @Test
  public void testInputStockAddStock() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("vs\nb\nq\n");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.go();
    assertEquals("getStocks (Got stocks) ", log.toString());
  }


  @Test
  public void testViewPortfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("vp\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.go();
    assertEquals("getPortfolios (Got portfolios) ", log.toString());
  }

  @Test
  public void testportPortfolioNotComplete() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("portfolio\np\nnicky\n1\na\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.go();
    assertEquals("addPortfolio (nicky) ", log.toString());
  }

  @Test
  public void testportPortfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("portfolio\np\nnicky\n1\n1\nf\nstocks/AMZN.csv" +
            "\nAMZN\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.go();
    assertEquals("addPortfolio (nicky) getPortfolio (nicky) getPortfolio (nicky) "
            , log.toString());
  }


  @Test
  public void testportPortfolioValue() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("portfolio\np\nnicky\n1\n1\nf\nstocks/AMZN.csv" +
            "\nAMZN\nb\nvp\np\n" +
            "nicky\nv\n05\nb\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.go();
    assertEquals("addPortfolio (nicky) getPortfolio (nicky) getPortfolio " +
                    "(nicky) getPortfolios " +
                    "(Got portfolios) checkPortfolio (nicky)" +
                    " checkPortfolio (v) checkPortfolio (05) ",
            log.toString());
  }


  @Test
  public void testStockStats() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("vs\ns\nAMZN\ng\n05\nb\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.go();
    assertEquals("getStocks (Got stocks) checkStock (AMZN) checkStock (g)" +
                    " checkStock (05) ",
            log.toString());
  }


}
