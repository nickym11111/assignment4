package view;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by vidojemihajlovikj on 6/11/24.
 */
public interface IViewListener {
  // all controller commands
  void handleDisplayPortfolios();

  void getPortfolioButtons();

  void getStocks();

  void handleCreatePortfolios(String name);

  void handleBuyStock(Date date, String symbol, int shares, String portfolio)
          throws FileNotFoundException;

  void handleSellStock(Date date, String symbol, int shares, String portfolio)
          throws FileNotFoundException;

  void handleGetValue(Date date, String portfolio);

  void handleGetComposition(Date date, String portfolio) throws FileNotFoundException;

}