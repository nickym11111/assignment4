package command.readerbuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.DailyStock;
import model.IStock;
import model.Stock;

/**
 * StockBuilderImpl is an implementation of IStockBuilder that parses through the given data
 * and creates an IStock with that data.
 */

public class StockBuilderImpl implements IStockBuilder {


  /**
   * BuildStock gets the readable version of the given data stream, parses through the data,
   * assigns each day of data to a daily stock, and stores all daily stocks in the new IStock
   * object. The purpose of this method is to be able to build a stock form any data stream
   * smoothly without convolution in the stock class.
   *
   * @param ticker
   *     represents the ticker symbol of the company
   * @param stockDataStream
   *     represents the source of stock data
   * @return
   *     a new IStock that represents a stock from the ticker symbol company
   *     and holds all the data from the given stream
   */
  @Override
  public IStock buildStock(String ticker, IStockDataStream stockDataStream) {
    IStock stock = new Stock(ticker);
    Readable readable = stockDataStream.getReadable();

    Scanner scanner = new Scanner(readable);

    if (scanner.hasNextLine()) {
      String line = scanner.next();
    }
    int i = 0;
    while (scanner.hasNext()) {
      String line = scanner.next();
      String[] parts = line.split(",");
      String date = parts[0];
      String open = parts[1];
      String high = parts[2];
      String low = parts[3];
      String close = parts[4];
      String volume = parts[5];
      //parse to doubles and date.

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
      LocalDate localdate = LocalDate.parse(date, formatter);
      if (i == 0) {
        stock.setMostRecentDate(localdate);
        i += 1;
      }
      stock.setEarliestDate(localdate);
      double openDouble = Double.parseDouble(open);
      double highDouble = Double.parseDouble(high);
      double lowDouble = Double.parseDouble(low);
      double closeDouble = Double.parseDouble(close);
      int volumeInt = Integer.parseInt(volume);


      DailyStock day = new DailyStock(ticker, localdate, openDouble, highDouble,
              lowDouble, closeDouble, volumeInt);

      stock.addDailyStock(localdate, day);
    }

    return stock;
  }


}





