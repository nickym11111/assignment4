package command;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * DateUtil is a helper class containing helpful methods that
 * check information about a date or create a new date based on information.
 */
public class DateUtil {

  /**
   * getNearestAvailableDate takes in a date and if it's a weekend
   * day sets it to the friday of that week.
   *
   * @param date represents the given LocalDate
   * @return a LocalDate representing the nearest available non weekend date
   */
  public LocalDate getNearestAvailableDate(LocalDate date) {
    LocalDate nearestDate = date;
    DayOfWeek dayOfWeek = date.getDayOfWeek();

    if (dayOfWeek == DayOfWeek.SATURDAY) {
      nearestDate = nearestDate.minusDays(1);
    } else if (dayOfWeek == DayOfWeek.SUNDAY) {
      nearestDate = nearestDate.minusDays(2);
    }
    return nearestDate;
  }

  /**
   * isWeekend checks if a given date is a weekend (sat or sun).
   *
   * @param date
   *     represents the given date.
   * @return
   *     a boolean returning yes if the date is a weekend, false
   *     if otherwise.
   */
  public boolean isWeekend(LocalDate date) {
    DayOfWeek dayOfWeek = date.getDayOfWeek();
    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
  }
}
