package command.readerbuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * FileStockDataStreamImpl is a class that is a type of data stream for creating an IStock object.
 * This data stream comes from the file that can either be given from the user or hardcoded
 * in tests.
 */
public class FileStockDataStreamImpl implements IStockDataStream {
  private final String fileName;

  /**
   * FileStockDataStreamImpl constructor creates a {@code FileStockDataStreamImpl}
   * object and sets the given fields.
   *
   * @param fileName represents the name of the file
   */
  public FileStockDataStreamImpl(String fileName) {
    this.fileName = fileName;
  }


  /**
   * GetReadable for a FileStockDataStreamImpl returns a new FileReader for this file
   * or throws an exception if this file does not exist.
   *
   * @return a new FileReader
   */
  @Override
  public Readable getReadable() {
    try {
      return new FileReader(fileName);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("This file does not exist");
    }
  }
}
