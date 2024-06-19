package command.readerbuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import model.ISmartPortfolio;
import model.ISmartStockShares;

/**
 * SavePortfolioOperation is used to save portfolios into the project (hard save them)
 * so that when a user quits and reruns their program their portfolios are not deleted.
 */
public class SavePortfolioOperation {
  private final ISmartPortfolio portfolio;
  String port = "portfolios/";

  /**
   * SavePortfolioOperation creates a SavePortfolioOperation object with the
   * given portfolio field.
   *
   * @param portfolio represents the portfolio this object will be saving
   */
  public SavePortfolioOperation(ISmartPortfolio portfolio) {
    this.portfolio = portfolio;
  }

  /**
   * The run method for SavePortfolioOperation saves this portfolio as a csv file
   * with the portfolio name and inside the file holds the names of the stocks with their
   * number of shares for each stock company.
   */
  public void run() {
    File theDir = new File(port + portfolio.getName());
    boolean x = theDir.mkdir();
    System.out.println("directory status: " + x);
    System.out.println("Portfolio name:" + portfolio.getName());

    writeCurrentToFile();
    writeBoughtFile();
    writeSoldFile();
    writeDateCreatedFile();
  }

  private void writeCurrentToFile() {
    StringBuilder s = new StringBuilder();
    s.append("ticker");
    s.append(",");
    s.append("shares");
    s.append(",");
    s.append("most-recent-transaction-date");
    s.append("\n");
    for (Map.Entry<String, ISmartStockShares> entry :
            portfolio.getCurrentStockSharesMap().entrySet()) {
      s.append(entry.getKey());
      s.append(",");
      s.append(entry.getValue().getShares());
      s.append(",");
      s.append(entry.getValue().getDate());
      s.append("\n");
    }
    try (FileWriter writer = new FileWriter(port + portfolio.getName() + "/current.csv")) {
      writer.write(s.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeBoughtFile() {
    StringBuilder bought = new StringBuilder();
    bought.append("ticker");
    bought.append(",");
    bought.append("shares");
    bought.append(",");
    bought.append("date-bought-on");
    bought.append("\n");

    for (Map.Entry<String, ArrayList<ISmartStockShares>> entry :
            portfolio.getBoughtStockSharesMap().entrySet()) {
      String ticker = entry.getKey();
      ArrayList<ISmartStockShares> shares = entry.getValue();
      for (int i = 0; i < shares.size(); i++) {
        bought.append(ticker);
        bought.append(",");
        bought.append(shares.get(i).getShares());
        bought.append(",");
        bought.append(shares.get(i).getDate());
        bought.append("\n");
      }
    }
    try (FileWriter writer = new FileWriter(port + portfolio.getName() + "/bought.csv")) {
      writer.write(bought.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeSoldFile() {
    StringBuilder sold = new StringBuilder();
    sold.append("ticker");
    sold.append(",");
    sold.append("shares");
    sold.append(",");
    sold.append("date-sold-on");
    sold.append("\n");

    for (Map.Entry<String, ArrayList<ISmartStockShares>> entry :
            portfolio.getSoldStockSharesMap().entrySet()) {
      String ticker = entry.getKey();
      ArrayList<ISmartStockShares> shares = entry.getValue();
      for (int i = 0; i < shares.size(); i++) {
        sold.append(ticker);
        sold.append(",");
        sold.append(shares.get(i).getShares());
        sold.append(",");
        sold.append(shares.get(i).getDate());
        sold.append("\n");
      }
    }
    try (FileWriter writer = new FileWriter(port + portfolio.getName() + "/sold.csv")) {
      writer.write(sold.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeDateCreatedFile() {
    StringBuilder dateCreated = new StringBuilder();
    dateCreated.append(portfolio.getDateCreated());
    try (FileWriter writer = new FileWriter(port + portfolio.getName()
            + "/date-created.csv")) {
      writer.write(dateCreated.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


