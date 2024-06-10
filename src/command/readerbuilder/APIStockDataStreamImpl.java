package command.readerbuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.Objects;

/**
 * APIStockDataStreamImpl is a class that is a type of data stream for creating an IStock object.
 * This data stream comes from the API Alpha Vantage and uses the ticker symbol to
 * fetch stock data.
 */
public class APIStockDataStreamImpl implements IStockDataStream {
  private final String ticker;

  /**
   * APIStockDataStreamImpl constructor creates a {@code APIStockDataStreamImpl}
   * object and sets the given fields.
   *
   * @param ticker represents the ticker symbol of this stock
   */
  public APIStockDataStreamImpl(String ticker) {
    this.ticker = Objects.requireNonNull(ticker);
  }

  /**
   * getReadable for APIStockDataStreamImpl returns a Stringbuilder that is all the data for this
   * stock formatted in a csv format. It will throw an exception if it has run out of queries.
   *
   * @return a StringBuilder representing stock data
   */
  @Override
  public Readable getReadable() throws RuntimeException {
    String stockSymbol = ticker; //ticker symbol for Google
    String apiKey = "W0M1JOKC82EZEQA8";//"QK596AIV63NNGJPP"; //

    URL url = null;

    boolean correctURL = false;
    try {
      url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
      correctURL = true;
    } catch (Exception e) {
      throw new RuntimeException("The Alpha Vantage API has either changed or no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    boolean correctString = false;
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      if (output.toString().contains("Information")) {
        throw new RuntimeException("API is out of queries. " +
                "Please use the saved stocks to view stock data.");
      }
      if (output.toString().contains("Error")) {
        throw new RuntimeException("This is not a valid ticker symbol.");
      }
      correctString = true;
    } catch (IOException e) {

      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }

    if (correctString && correctURL) {
      try (FileWriter writer = new FileWriter("src/stocks/" + ticker + ".csv")) {
        writer.write(output.toString());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return new StringReader(output.toString());
  }

}
