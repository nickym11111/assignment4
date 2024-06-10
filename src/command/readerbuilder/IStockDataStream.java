package command.readerbuilder;

/**
 * IStockDataStream represents a type of data stream that can be used to build a stock.
 * These could be different files, API, etc.
 */
public interface IStockDataStream {

  /**
   * GetReadable for an IStockDataStream returns a readable version of the data stream that
   * can later be parsed.
   *
   * @return a new Readable object
   */
  public Readable getReadable();
}
