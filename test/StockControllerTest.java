import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import controller.IController;
import controller.StockController;
import model.IStockMarket;
import model.StockMarket;

import static org.junit.Assert.assertEquals;

/**
 * A test class that make guarantees that the stock controller works
 * properly when given specific inputs.
 */
public class StockControllerTest {

  @Test
  public void testStockController() throws IOException {
    Readable rd = new StringReader("quit");
    Appendable ap = new StringBuilder();
    IStockMarket stockMarket = new StockMarket();
    IController controller = new StockController(ap, rd, stockMarket);
    controller.go();

    assertEquals("Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "Thank you for using this program!", ap.toString());

  }


  @Test
  public void testStockControllerLittleQ() throws IOException {
    Readable rd = new StringReader("q");
    Appendable ap = new StringBuilder();
    IStockMarket stockMarket = new StockMarket();
    IController controller = new StockController(ap, rd, stockMarket);
    controller.go();

    assertEquals("Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "Thank you for using this program!", ap.toString());

  }


  @Test
  public void testStockControllerVP() throws IOException {
    Readable rd = new StringReader("vp\nb\nquit\n");
    Appendable ap = new StringBuilder();
    IStockMarket stockMarket = new StockMarket();
    IController controller = new StockController(ap, rd, stockMarket);
    controller.go();

    assertEquals("Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "There are 1 portfolios, with the following names: \n" +
            "nicky\n" +
            "Enter a 'p' to check value of a portfolio\n" +
            "Enter 'b' to go back to previous options\n" +
            "Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "Thank you for using this program!", ap.toString());

  }


  @Test
  public void testStockControllerVS() throws IOException {
    Readable rd = new StringReader("vs\nb\nq");
    Appendable ap = new StringBuilder();
    IStockMarket stockMarket = new StockMarket();
    IController controller = new StockController(ap, rd, stockMarket);
    controller.go();

    assertEquals("Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "There are 7 stocks, with the following names: \n" +
            "NFLX\n" +
            "META\n" +
            "GOOG\n" +
            "AAPL\n" +
            "NKE\n" +
            "AMZN\n" +
            "MFST\n" +
            "Note: some stocks may not be up to date if they were previously inputted\n" +
            "Enter a 's' to view statistics about a specific stock\n" +
            "Enter 'b' to go back to previous options\n" +
            "Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "Thank you for using this program!", ap.toString());

  }


  @Test
  public void testStockControllerPort() throws IOException {
    Readable rd = new StringReader("portfolio\nb\nquit");
    Appendable ap = new StringBuilder();
    IStockMarket stockMarket = new StockMarket();
    IController controller = new StockController(ap, rd, stockMarket);
    controller.go();

    assertEquals("Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "Enter 'p' to create another portfolio\n" +
            "Enter 'b' to go back to previous options\n" +
            "Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "Thank you for using this program!", ap.toString());
  }

  @Test
  public void testStockControllerStocks() throws IOException {
    Readable rd = new StringReader("stock\nb\nquit\n");
    Appendable ap = new StringBuilder();
    IStockMarket stockMarket = new StockMarket();
    IController controller = new StockController(ap, rd, stockMarket);
    controller.go();

    assertEquals("Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "How would you like to input your stock data? \n" +
            "Enter 'f' to input the stock data through a csv file.\n" +
            "Enter 'a' to input the stock data through our online resource (API).\n" +
            "Enter 'b' to go back to previous options or view stock data.\n" +
            "Your current options are: \n" +
            "view current stocks you've previously inputted (INPUT: 'vs')\n" +
            "view current portfolios you've previously inputted (INPUT: 'vp')\n" +
            "input a new stock (INPUT: 'stock')\n" +
            "input a new portfolio (INPUT: 'portfolio')\n" +
            "q or quit (quit the program) \n" +
            "Thank you for using this program!", ap.toString());

  }


}