import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import command.CheckSpecificPortfolioCommand;

import static org.junit.Assert.assertEquals;

/**
 * A testing class that allows use to guarantee that when a user specifically checks
 * a portfolio.
 */
public class CheckSpecificPortfolioCommandTest extends ACommandTest {

  public CheckSpecificPortfolioCommandTest() throws FileNotFoundException {
    // constructor created to support exception thrown
  }

  @Before
  public void setUp() {
    in = new StringReader("b\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
  }

  @Test
  public void testRandInp() throws IOException {
    in = new StringReader("lola\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter name of portfolio\n" +
            "That portfolio was not found/ has not been inputted\n" +
            "Enter name of portfolio\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

  @Test
  public void testExistingPort() throws IOException {
    in = new StringReader("nicky\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testExistingPortGetValue() throws IOException {
    in = new StringReader("nicky\nv\n0202\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date that has not happened yet will result\n" +
            "in the most recently recorded date being used as the date instead.\n" +
            "Enter a date (YYYY-MM-DD) for which you want the portfolio value\n" +
            "Invalid input, enter a valid date (YYYY-MM-DD) is the value of this portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }


  @Test
  public void testExistingPortGetValueRealDate() throws IOException {
    in = new StringReader("nicky\nv\n2024-05-01\nb\nb\n");
    s = new Scanner(in);
    command = new CheckSpecificPortfolioCommand(view, s);
    command.run(stockMarket);
    assertEquals("Enter name of portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'b' to go back to previous options\n" +
            "Keep in mind that entering a date that has not happened yet will result\n" +
            "in the most recently recorded date being used as the date instead.\n" +
            "Enter a date (YYYY-MM-DD) for which you want the portfolio value\n" +
            "0.0  is the value of this portfolio\n" +
            "Enter 'v' to get the value of this portfolio on a specific date\n" +
            "Enter 'b' to go back to previous options\n", builder.toString());
  }

}