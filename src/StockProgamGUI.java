import java.io.IOException;
import java.io.InputStreamReader;

import controller.IController;
import controller.StockController;
import controller.StockControllerGUI;
import model.ISmartStockMarket;
import model.IStockMarket;
import model.SmartStockMarket;
import model.StockMarket;
import view.IViewGUI;
import view.ViewGUIImpl;

public class StockProgamGUI {
  public static void main(String [] args) throws IOException {
    ISmartStockMarket model = new SmartStockMarket();
    IViewGUI view = new ViewGUIImpl();
    IController controller = new StockControllerGUI(model, view);

    controller.goController();
  }
}
