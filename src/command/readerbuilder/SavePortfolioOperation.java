package command.readerbuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import model.IPortfolio;
import model.StockShares;

/**
 * SavePortfolioOperation is used to save portfolios into the project (hard save them)
 * so that when a user quits and reruns their program their portfolios are not deleted.
 */
public class SavePortfolioOperation {
  private IPortfolio portfolio;

  /**
   * SavePortfolioOperation creates a SavePortfolioOperation object with the
   * given portfolio field.
   *
   * @param portfolio represents the portfolio this object will be saving
   */
  public SavePortfolioOperation(IPortfolio portfolio) {
    this.portfolio = portfolio;
  }

  /**
   * The run method for SavePortfolioOperation saves this portfolio as a csv file
   * with the portfolio name and inside the file holds the names of the stocks with their
   * number of shares for each stock company.
   */
  public void run() {
    StringBuilder s = new StringBuilder();
    s.append("ticker");
    s.append(",");
    s.append("shares");
    s.append("\n");
    for (Map.Entry<String, ArrayList<StockShares>> entry : portfolio.getStocksShareMap().entrySet()) {
      s.append(entry.getKey());
      s.append(",");
      ArrayList<StockShares> allShares = entry.getValue();
      int sumShares = 0;
      for(int i = 0; i < allShares.size(); i++) {
        sumShares += allShares.get(i).getShares();
      }
      s.append(sumShares);
      s.append("\n");
    }
    try (FileWriter writer = new FileWriter("src/portfolios/" + portfolio.getName() + ".csv")) {
      writer.write(s.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
