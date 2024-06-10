import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import command.DateUtil;

import static org.junit.Assert.assertEquals;

/**
 * A test class that allows use to guarantee that dates are properly being created.
 */
public class DateUtilTest {
  DateUtil dateUtil = new DateUtil();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",
          Locale.ENGLISH);
  LocalDate localdate1 = LocalDate.parse("2024-05-01", formatter);
  LocalDate localdate2 = LocalDate.parse("2024-06-02", formatter);
  LocalDate localdate3 = LocalDate.parse("1950-05-04", formatter);
  LocalDate localdate4 = LocalDate.parse("2024-06-01", formatter);

  @Test
  public void testIsWeekendDate() {
    assertEquals(true, dateUtil.isWeekend(localdate2));
    assertEquals(false, dateUtil.isWeekend(localdate3));
    assertEquals(false, dateUtil.isWeekend(localdate1));
  }

  @Test
  public void testGetMostRecentDate() {
    assertEquals(LocalDate.parse("2024-05-31", formatter),
            dateUtil.getNearestAvailableDate(localdate2));
    assertEquals(LocalDate.parse("2024-05-31", formatter),
            dateUtil.getNearestAvailableDate(localdate4));
    assertEquals(LocalDate.parse("2024-05-01", formatter),
            dateUtil.getNearestAvailableDate(localdate1));

  }


}