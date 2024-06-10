package command;

import java.io.IOException;
import java.util.Scanner;

import model.IStockMarket;
import view.IView;

/**
 * The abstract class ACommand represents an ICommand with the given fields and the given
 * constructor. The purpose of this class is to clearly show what the intake fields of a command
 * are and to avoid duplication code in subclasses.
 */
public abstract class ACommand implements ICommand {
  IView view;
  Scanner s;

  /**
   * ACommand constructor creates a Command with the given view and scanner to
   * be able to append text to the view and read in text from the user.
   *
   * @param view represents the view of this program (output).
   * @param s    represents the scanner, which holds the user input of the program.
   */
  public ACommand(IView view, Scanner s) {
    this.view = view;
    this.s = s;
  }

  /**
   * The abstract run method will perform the specific command in each subclass differently
   * so therefor it is abstract in this class and will be concretely implemented in the
   * subclasses since each run command serves a different purpose.
   *
   * @param stockMarket represents the current stock market for the command to access and
   *                    obtain data from.
   * @throws IOException when subclasses through an IOException.
   */
  public abstract void run(IStockMarket stockMarket) throws IOException;


}
