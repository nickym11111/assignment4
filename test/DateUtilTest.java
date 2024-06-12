import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

  @Test
  public void testDateRange() {
    LocalDate localdate1 = LocalDate.parse("2024-05-01", formatter);
    LocalDate localdate2 = LocalDate.parse("2024-06-02", formatter);
    //LocalDate localdate7 = LocalDate.parse("2022-05-04", formatter);

    System.out.println(ChronoUnit.DAYS.between( localdate1, localdate2));
    int days = (int) ChronoUnit.DAYS.between( localdate1, localdate2);
    int numRows;
    if(days > 5 && days < 31) {
      numRows = days;
    }
    else if(days > 30 && days < 151) {
      numRows = (int) Math.ceilDiv(days, 7); // if it has a double return type return type
      // set a residue boolean to true to know to plus one 
      System.out.println(numRows);

    }
    else if(days > 151 && days < (365 * 5)) {
      numRows = (int) Math.ceilDiv(days, 31);
    }
    else {
      numRows = (int) Math.ceilDiv(days, 365);
    }
    int increment = (int) Math.ceilDiv(days, numRows);
    for(int i = 0; i < numRows + 1; i++) {
      System.out.println(localdate1.plusDays(increment * i));
    }


  }


}