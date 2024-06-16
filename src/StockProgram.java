package view;

import java.io.IOException;
import java.io.InputStreamReader;

import controller.StockController;
import model.IStockMarket;
import model.StockMarket;

/**
 * The main class for that executes the stock program.
 */
public class StockProgram {

  /**
   * The entry point of the application.
   *
   * @param args command-line arguments
   * @throws IOException if an I/O error occurs
   */
  public static void main(String[] args) throws IOException {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    IStockMarket stockMarket = new StockMarket();
    StockController controller = new StockController(ap, rd, stockMarket);
    controller.go();
  }
}