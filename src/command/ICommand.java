package command;

import java.io.IOException;

import model.IStockMarket;

/**
 * The ICommand interface represents an interface of commands, or actions that
 * are caused/called from specific user input. A command happens when a user wants to
 * see data, add objects, or another actions. The goal of commands is to split
 * up user abilities in a clear way.
 */
public interface ICommand {
  /**
   * The run method will perform the specific command in each subclass differently,
   * allow the user to complete the desired action or command and possibly call on other commands
   * based on user input.
   *
   * @param stockMarket represents the current stock market for the command to access and
   *                    obtain data from.
   * @throws IOException when subclasses through an IOException.
   */
  void run(IStockMarket stockMarket) throws IOException;
}
