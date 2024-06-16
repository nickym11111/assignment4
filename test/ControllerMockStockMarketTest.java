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
    controller.goController();
    assertEquals("addStock (AMZN) ", log.toString());
  }

  @Test
  public void testInputStockAddStock() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("vs\nb\nq\n");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.goController();
    assertEquals("getStocks (Got stocks) ", log.toString());
  }


  @Test
  public void testViewPortfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("vp\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.goController();
    assertEquals("getPortfolios (Got portfolios) ", log.toString());
  }

  @Test
  public void testportPortfolioNotComplete() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("portfolio\np\narsema\n1\na\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.goController();
    assertEquals("addPortfolio (arsema) ", log.toString());
  }

  @Test
  public void testportPortfolio() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("portfolio\np\narsema\n1\n1\n" +
            "2024-05-01\nf\nstocks/NKE.csv" +
            "\nNKE\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.goController();
    assertEquals("addPortfolio (arsema) getPortfolio (arsema) getPortfolio (arsema) "
            , log.toString());
  }


  @Test
  public void testportPortfolioValue() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("portfolio\np\narsema\n1\n1\n" +
            "2024-05-01\nf\nstocks/NKE.csv" +
            "\nNKE\nb\nvp\np\n" +
            "arsema\nv\n05\nb\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.goController();
    assertEquals("addPortfolio (arsema) getPortfolio (arsema) getPortfolio " +
                    "(arsema) getPortfolios " +
                    "(Got portfolios) checkPortfolio (arsema)" +
                    " checkPortfolio (v) checkPortfolio (05) ",
            log.toString());
  }


  @Test
  public void testStockStats() throws IOException {
    StringBuilder log = new StringBuilder();
    IStockMarket model = new StockMarketMock(log);
    Readable reader = new StringReader("vs\ns\nNKE\ng\n05\nb\nb\nq");
    Appendable appendable = new StringBuilder();
    IController controller = new StockController(appendable, reader, model);
    controller.goController();
    assertEquals("getStocks (Got stocks) checkStock (NKE) checkStock (g)" +
                    " checkStock (05) ",
            log.toString());
  }


}
