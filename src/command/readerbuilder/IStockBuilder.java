package command.readerbuilder;

import model.IStock;

/**
 * IStockBuilder represents a builder class for a stock that can be used to build a stock
 * using different ticker symbols and different sources of data.
 */
public interface IStockBuilder {

  /**
   * BuildStock builds an IStock object based on the ticker symbol and the source of data
   * given by parsing through the data and creating a stock object with that data.
   *
   * @param ticker          represents the ticker symbol of the company
   * @param stockDataStream represents the source of stock data
   * @return an IStock object
   */
  IStock buildStock(String ticker, IStockDataStream stockDataStream);
}