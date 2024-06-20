package view;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Calendar;
import java.util.Date;



import static org.junit.Assert.assertEquals;

/**
 *  A test class that properly checks that the view is properly listening to the
 *  actions and displaying the proper information.
 */
public class ControllerMockTesting {


  private Date goodDate;
  private Date pastDate;
  private Date holiday;
  private Date futrueDate;


  private String stock;
  private String arsema;
  private String fake;

  public ControllerMockTesting() throws FileNotFoundException {
    this.goodDate = new Date(2024 - 1900, Calendar.JUNE, 18);
    this.pastDate = new Date(2024 - 1900, Calendar.MAY, 1);
    this.holiday = new Date(2024 - 1900, Calendar.MARCH, 29);
    this.futrueDate = new Date(2026 - 1900, Calendar.MAY, 1);
    this.fake = "Technology";


    this.stock = "GOOG";
    this.arsema = "arsema";
  }






  @Test
  public void testGetPortfolioButtons() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    view.getPortfolioButtons();
    viewListener.getPortfolioButtons();
    assertEquals("Get portfolio buttons received", log.toString());
  }

  @Test
  public void testHandleCreatePortfolios() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();


    view.getNewPortfolio("Arsema");
    viewListener.handleCreatePortfolios("Arsema");
    assertEquals("New portfolio created: Arsema", log.toString());
  }



  @Test
  public void testHandleBuyStocksGoodDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    view.fireBuyStock(goodDate, stock, 5, arsema);

    viewListener.handleBuyStock(goodDate, stock, 5, arsema);
    assertEquals("The current date is: Tue Jun 18 00:00:00 EDT 2024, " +
            "the current symbol is:  GOOG, the current number of being" +
            " bought shares is: 5, the portfolio is: arsema\n", log.toString());

  }
  @Test
  public void testHandleBuyStocksPastDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleBuyStock(pastDate, stock, 5, arsema);
    view.fireBuyStock(pastDate, stock, 5, arsema);

    assertEquals("You cannot buy a stock from before " +
            "the most recent transaction :2024-05-01", log.toString());

  }

  @Test
  public void testHandleBuyStocksHolidayDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleBuyStock(holiday, stock, 5, arsema);
    view.fireBuyStock(pastDate, stock, 5, arsema);

    assertEquals("You cannot buy a stock from before the most " +
            "recent transaction :2024-03-29", log.toString());

  }

  @Test
  public void testHandleBuyStocksFutureDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleBuyStock(futrueDate, stock, 5, arsema);
    view.fireBuyStock(pastDate, stock, 5, arsema);

    assertEquals("You cannot buy a stock from the future", log.toString());
  }



  @Test
  public void testHandleSellStocksGoodDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleSellStock(goodDate, stock, 5, arsema);
    view.fireSellStock(pastDate, stock, 5, arsema);

    assertEquals("The current date is: Tue Jun 18 00:00:00 EDT " +
            "2024, the current symbol " +
            "is:  GOOG, the current number of shares being sold is: 5" +
            ", the portfolio is: arsema\n", log.toString());
  }

  @Test
  public void testHandleSellStocksPastDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleSellStock(pastDate, stock, 5, arsema);
    view.fireSellStock(pastDate, stock, 5, arsema);

    assertEquals("You cannot sell a stock from before " +
            "the most recent transaction 2024-05-01", log.toString());
  }

  @Test
  public void testHandleSellStocksHolidayDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleSellStock(holiday, stock, 5, arsema);
    view.fireSellStock(pastDate, stock, 5, arsema);

    assertEquals("You cannot sell a stock from " +
            "before the most recent transaction 2024-03-29", log.toString());

  }

  @Test
  public void testHandleSellStocksFutureDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleSellStock(futrueDate, "AMZN", 5, arsema);
    view.fireSellStock(pastDate, "AMZN", 5, arsema);

    assertEquals("You cannot remove a stock you never bought", log.toString());

  }


  @Test
  public void testGetComposition() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();


    viewListener.handleGetComposition(goodDate, arsema);
    view.fireComp(goodDate, arsema);

    assertEquals("The current date is for the composition of " +
            "the portfolio being found on: Tue Jun 18 00:00:00 EDT 2024, " +
            "this is the composition for the portfolio: arsema\n" +
            "[GOOG: 7.0]", log.toString());

  }

  @Test
  public void testGetCompositionPastDate() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();



    viewListener.handleGetComposition(pastDate, arsema);
    view.fireComp(pastDate, arsema);

    assertEquals("The current date is for the composition " +
            "of the portfolio being found on: Wed May 01 00:00:00 EDT " +
            "2024, this is the composition for the portfolio: arsema\n" +
            "[]", log.toString());

  }

  @Test
  public void testGetCompositionFutureDate() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();



    viewListener.handleGetComposition(futrueDate, arsema);
    view.fireComp(futrueDate, arsema);

    assertEquals("The current date is for the composition " +
            "of the portfolio being found on: Fri May 01 00:00:00 EDT 2026, " +
            "this is the composition for the portfolio: arsema\n" +
            "[GOOG: 7.0]", log.toString());

  }

  @Test
  public void testGetCompositionHoliday() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();


    viewListener.handleGetComposition(holiday, arsema);
    view.fireComp(holiday, arsema);

    assertEquals("The current date is for the" +
            " composition of the portfolio being found on: " +
            "Fri Mar 29 00:00:00 EDT 2024, this is the " +
            "composition for the portfolio: arsema\n" +
            "[]", log.toString());

  }



  @Test
  public void testGetPortValue() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleGetValue(goodDate, arsema);
    view.fireValue(goodDate, arsema);

    assertEquals("The current date is for the value of portfolio being found on: " +
            "Tue Jun 18 00:00:00 EDT 2024,this is the value for the portfolio: arsema\n" +
            "1235.15", log.toString());
  }


  @Test
  public void testGetValueOnPastDate() throws FileNotFoundException{
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleGetValue(pastDate, arsema);
    view.fireValue(pastDate, arsema);

    assertEquals("The current date is for the value of portfolio being " +
            "found on: Wed May 01 00:00:00 EDT 2024,this is the " +
            "value for the portfolio: arsema\n" +
            "0.0", log.toString());

  }

  @Test
  public void testGetValueOnFutureDate() throws FileNotFoundException{
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    viewListener.handleGetValue(futrueDate, arsema);
    view.fireValue(futrueDate, arsema);

    assertEquals("Stock does not exist", log.toString());

  }

  @Test
  public void testGetValueOnHolidayDate() throws FileNotFoundException{
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();


    view.fireValue(holiday, arsema);
    viewListener.handleGetValue(holiday, arsema);

    assertEquals("The current date is for the value of portfolio being found on: " +
            "Fri Mar 29 00:00:00 EDT 2024,this is the value for the portfolio: arsema\n" +
            "0.0", log.toString());

  }



  // FIRE EVENTS TESTING

  @Test
  public void testFireEventCompGoodDay() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    view.fireComp(goodDate, "arsema");
    viewListener.handleGetComposition(goodDate, "arsema");
    assertEquals("The current date is for the composition of the portfolio " +
            "being found on: Tue Jun 18 00:00:00 EDT 2024, this is the " +
            "composition for the portfolio: arsema\n" +
            "[GOOG: 7.0]", log.toString());

  }

  @Test
  public void testFireEventValueGoodDay() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    view.fireValue(goodDate, "arsema");
    viewListener.handleGetValue(goodDate, "arsema");

    assertEquals("The current date is for the value of portfolio being found on: " +
            "Tue Jun 18 00:00:00 EDT 2024,this is the value for the portfolio: arsema\n" +
            "1235.15", log.toString());

  }


  @Test
  public void testFireBuyStockGoodDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    view.fireBuyStock(goodDate, stock, 5, arsema);
    viewListener.handleBuyStock(goodDate, stock, 5, arsema);


    assertEquals("The current date is: Tue Jun 18 00:00:00 EDT 2024, " +
            "the current symbol is:  GOOG, the current number of " +
            "being bought shares is: 5, the portfolio is: arsema\n", log.toString());

  }



  @Test
  public void testFireSellStockGoodDate() throws IOException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    view.fireSellStock(goodDate, stock, 5, arsema);
    viewListener.handleSellStock(goodDate, stock, 5, arsema);

    assertEquals("The current date is: Tue Jun 18 00:00:00 EDT " +
            "2024, the current symbol " +
            "is:  GOOG, the current number of shares being sold is: 5" +
            ", the portfolio is: arsema\n", log.toString());
  }







  @Test
  public void testGetNewPortfolio() throws FileNotFoundException {
    StringBuilder log = new StringBuilder();
    IViewListener viewListener = new ViewListenerMock(log);
    ViewGUIImpl view = new ViewGUIImpl();

    view.getNewPortfolio("nicky");
    viewListener.handleCreatePortfolios("nicky");
    assertEquals("New portfolio created: nicky", log.toString());

  }






}
