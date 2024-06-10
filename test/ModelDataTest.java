import org.junit.Before;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import command.readerbuilder.FileStockDataStreamImpl;
import command.readerbuilder.IStockBuilder;
import command.readerbuilder.IStockDataStream;
import command.readerbuilder.StockBuilderImpl;
import model.IStock;
import model.StockShares;

/**
 * A testing class that store general data to be used in different
 * test classes.
 */
public abstract class ModelDataTest {
  protected IStockDataStream fileReaderAmazon;
  protected IStockDataStream fileReaderNike;
  protected IStockDataStream fileReaderGOOG;
  protected IStockBuilder builder;
  protected IStockBuilder amazonBuilder;
  protected IStock amazonStock;
  protected IStock nikeStock;
  protected IStock googleStock;
  protected LocalDate aGoodDay;
  protected LocalDate futrueDay;
  protected LocalDate holiday;
  protected LocalDate oldDate;
  protected LocalDate weekend;
  protected StockShares stockShares;


  @Before
  public void setUp() throws FileNotFoundException {
    this.builder = new StockBuilderImpl();


    this.fileReaderAmazon = new FileStockDataStreamImpl("stocks/AMZN.csv");
    this.amazonStock = amazonBuilder.buildStock("AMZN", fileReaderAmazon);

    this.fileReaderNike = new FileStockDataStreamImpl("stocks/NKE.csv");
    this.nikeStock = builder.buildStock("MKE", fileReaderNike);

    this.fileReaderGOOG = new FileStockDataStreamImpl("stocks/GOOG.csv");
    this.googleStock = builder.buildStock("GOOG", fileReaderGOOG);


    this.aGoodDay = LocalDate.of(2024, 5, 1);
    this.futrueDay = LocalDate.of(2025, 5, 1);
    this.holiday = LocalDate.of(2024, 3, 29);
    this.oldDate = LocalDate.of(1987, 3, 29);
    this.weekend = LocalDate.of(2024, 3, 24);

    this.stockShares = new StockShares(5, this.amazonStock);
  }

}
