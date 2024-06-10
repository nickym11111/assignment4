package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;

/**
 * Utility class for initializing data in the stock market system.
 */
public class ModelUtil {

  /**
   * Constructs a new ModelUtil object.
   */
  public ModelUtil() {
    // this is empty because it uitlis clas.
  }

  /**
   * Initializes data in the stock market by reading files from the
   * "stocks" and "portfolios" directories.
   *
   * @param myStockMarket the stock market instance to initialize data for
   * @throws FileNotFoundException if a file is not found
   */
  public void initializeData(IStockMarket myStockMarket) throws FileNotFoundException {
    File directory = new File("src/stocks/");
    File[] files = directory.listFiles();
    for (File file : files) {
      IStockDataStream data = new FileStockDataStreamImpl("src/stocks/" + file.getName());
      IStockBuilder stockBuilder = new StockBuilderImpl();
      String[] split = file.getName().split("\\.");
      IStock stock = stockBuilder.buildStock(split[0], data);
      myStockMarket.addStock(split[0], stock);
    }

    File portDirectory = new File("src/portfolios/");
    File[] portFiles = portDirectory.listFiles();
    for (File fileP : portFiles) {
      String[] splitPort = fileP.getName().split("\\.");
      myStockMarket.addPortfolio(splitPort[0]);
      Readable s = new FileReader("src/portfolios/" + fileP.getName());
      Scanner scanner = new Scanner(s);
      if (scanner.hasNextLine()) {
        scanner.next();
      }
      while (scanner.hasNext()) {
        String line = scanner.next();
        String[] parts = line.split(",");
        String ticker = parts[0];
        int shares = Integer.parseInt(parts[1]);
        myStockMarket.getPortfolio(splitPort[0]).addStockShare(ticker,
                myStockMarket.getStocks().get(ticker), shares);
      }
    }
  }


}
