package command;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import view.IView;

/**
 * APerformance is an abstract class that holds helper methods to be used in Stock and Portfolio
 * performance commands. The code of calculating the timestamp/number of rows
 * and printing out the actual chart itself can be abstracted and defined here to avoid
 * the duplication of code.
 */
public abstract class APerformance {
  protected IView view;

  /**
   * APerformance constructor creates an APerformance object with the given IView field.
   *
   * @param view represents a view (output) to the user.
   */
  public APerformance(IView view) {
    this.view = view;
  }

  /**
   * calculateNumRows takes in a number of days and returns the number of rows that will be
   * created in the chart when given this number of days as the timespan.
   *
   * @param days represents the days of the timespan
   * @return an int representing the number of rows formulated for this timespan of days
   */
  public int calculateNumRows(int days) {
    int numRows;
    if (days < 31) {
      numRows = days;
    } else if (days > 30 && days < 151) {
      numRows = (int) Math.ceilDiv(days, 7); // if it has a double return type return type
      // set a residue boolean to true to know to plus one (to do in the future)
    } else if (days > 151 && days < 900) {
      numRows = (int) Math.ceilDiv(days, 31);
    } else if (days > 900 && days < 1800) {
      numRows = (int) Math.ceilDiv(days, 62);
    } else if (days > 1800 && days < (365 * 5)) {
      numRows = (int) Math.ceilDiv(days, 93);
    } else if (days > (365 * 5) && days < (365 * 30)) {
      numRows = (int) Math.ceilDiv(days, 365);
    } else {
      numRows = (int) Math.ceilDiv(days, (365 * 5));
    }

    return numRows;
  }

  /**
   * printPerformance creates the chart itself with the stars when given
   * the map of dates to prices and the stringbuilder of the curated result,
   * it creates the scale for stars and adds the chart to the stringbuilder.
   *
   * @param result the string builder of the chart so far
   * @param mapOfPrices the map of local dates to prices representing value at each timestamp
   * @return the chart as a string
   */
  public String printPerformance(StringBuilder result, Map<LocalDate, Double> mapOfPrices) {
    double max = 0.0;
    for (Map.Entry<LocalDate, Double> entry : mapOfPrices.entrySet()) {
      if (max < entry.getValue()) {
        max = entry.getValue();
      }
    }

    max = Math.ceil(max);
    int scale = Math.ceilDiv((int) max, 20);

    Map<LocalDate, String> starScale = new TreeMap<>();
    for (Map.Entry<LocalDate, Double> entry : mapOfPrices.entrySet()) {
      double price = entry.getValue();
      double stars = price / scale;
      int starsRound = (int) Math.ceil(stars);
      StringBuilder starLine = new StringBuilder();
      for (int j = 0; j < starsRound; j++) {
        starLine.append("*");

      }
      starScale.put(entry.getKey(), starLine.toString());
    }

    for (Map.Entry<LocalDate, String> entry : starScale.entrySet()) {
      result.append(entry.getKey() + " : " + entry.getValue());
      result.append("\n");
    }

    result.append("SCALE: * = " + scale + " US dollars");
    result.append("\n");

    result.append(System.lineSeparator());
    return result.toString();

  }

}
