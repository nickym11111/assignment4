import java.io.IOException;
import java.io.InputStreamReader;

import controller.IController;
import controller.StockController;
import controller.StockControllerGUI;
import model.IStockMarket;
import model.StockMarket;
import view.IViewGUI;
import view.ViewGUIImpl;

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
    if(args.length == 1 && args[0].equals("-text")) {
      Readable rd = new InputStreamReader(System.in);
      Appendable ap = System.out;
      IStockMarket stockMarket = new StockMarket();
      StockController controller = new StockController(ap, rd, stockMarket);
      controller.goController();
    }
    if(args.length == 0) {
      IStockMarket model = new StockMarket();
      IViewGUI view = new ViewGUIImpl();
      IController controller = new StockControllerGUI(model, view);
      controller.goController();
    }
  }
}