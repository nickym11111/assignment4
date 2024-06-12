package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
    // this is empty because it uitls clas.
  }
  String stockName = "stocks/";
  String portName = "portfolios/";


  /**
   * Initializes data in the stock market by reading files from the
   * "stocks" and "portfolios" directories.
   *
   * @param myStockMarket the stock market instance to initialize data for
   * @throws FileNotFoundException if a file is not found
   */
  public void initializeData(IStockMarket myStockMarket) throws FileNotFoundException {
    File directory = new File(stockName);
    File[] files = directory.listFiles();
    for (File file : files) {
      IStockDataStream data = new FileStockDataStreamImpl(stockName + file.getName());
      IStockBuilder stockBuilder = new StockBuilderImpl();
      String[] split = file.getName().split("\\.");
      IStock stock = stockBuilder.buildStock(split[0], data);
      myStockMarket.addStock(split[0], stock);
    }

    File portDirectory = new File(portName);
    File[] portDirs = portDirectory.listFiles();
    for (File portDir : portDirs) {
      myStockMarket.addPortfolio(portDir.getName());
      ISmartPortfolio thisPortfolio = myStockMarket.getPortfolio(portDir.getName());

      Readable s = new FileReader(portName + portDir.getName() + "/current.csv");
      Map<String, ISmartStockShares> currentState = parseCurrentFile(s, myStockMarket);
      thisPortfolio.setCurrentStockSharesMap(currentState);

      Readable bought = new FileReader(portName + portDir.getName() + "/bought.csv");
      Map<String, ArrayList<ISmartStockShares>> boughtState = parseBoughtFile(bought, myStockMarket, true);
      thisPortfolio.setBoughtStockSharesMap(boughtState);


      Readable sold = new FileReader(portName + portDir.getName() + "/sold.csv");
      Map<String, ArrayList<ISmartStockShares>> soldState = parseBoughtFile(sold, myStockMarket, false);
      thisPortfolio.setSoldStockSharesMap(soldState);

      Readable date = new FileReader(portName + portDir.getName() + "/date-created.csv");
      thisPortfolio.setDateCreated(parseDate(date));
    }
  }

  private Map<String, ISmartStockShares> parseCurrentFile(Readable s, IStockMarket myStockMarket)
          throws FileNotFoundException {
    Map<String, ISmartStockShares> stocks = new HashMap<>();

    Scanner scanner = new Scanner(s);
    if (scanner.hasNextLine()) {
      scanner.next();
    }
    while (scanner.hasNext()) {
      String line = scanner.next();
      String[] parts = line.split(",");
      String ticker = parts[0];
      double shares = Double.parseDouble(parts[1]);
      String date = parts[2];
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",
              Locale.ENGLISH);
      LocalDate localdate = LocalDate.parse(date, formatter);

      ISmartStockShares thisStockShares = new SmartStockShares(shares,
              myStockMarket.getStocks().get(ticker), localdate);
      thisStockShares.setBought(true);
      stocks.put(ticker, thisStockShares);
    }
    return stocks;
  }

  private Map<String, ArrayList<ISmartStockShares>> parseBoughtFile(Readable s,
                                                              IStockMarket myStockMarket,
                                                              boolean bought)
          throws FileNotFoundException {
    Map<String, ArrayList<ISmartStockShares>> stocks = new HashMap<>();

    Scanner scanner = new Scanner(s);
    if (scanner.hasNextLine()) {
      scanner.next();
    }
    while (scanner.hasNext()) {
      String line = scanner.next();
      String[] parts = line.split(",");
      String ticker = parts[0];
      double shares = Double.parseDouble(parts[1]);
      String date = parts[2];
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",
              Locale.ENGLISH);
      LocalDate localdate = LocalDate.parse(date, formatter);

      ISmartStockShares thisStockShares = new SmartStockShares(shares,
              myStockMarket.getStocks().get(ticker), localdate);
      thisStockShares.setBought(bought);
      if (stocks.containsKey(ticker)) {
        stocks.get(ticker).add(thisStockShares);
      } else {
        ArrayList<ISmartStockShares> stockShares = new ArrayList<>();
        stockShares.add(thisStockShares);
        stocks.put(ticker, stockShares);
      }
    }
    return stocks;
  }

  private LocalDate parseDate(Readable s) {
    Scanner scanner = new Scanner(s);
      String dateAsString = scanner.next();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",
              Locale.ENGLISH);
      LocalDate localdate = LocalDate.parse(dateAsString, formatter);
    return localdate;
  }


}
