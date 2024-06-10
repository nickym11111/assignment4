package controller;

import java.io.IOException;

/**
 * This interface defines the contract for starting the stock program.
 */
public interface IController {

  /**
   * Starts the controller, initiating the application's main logic.
   * This is how we can start our program through the go method.
   *
   * @throws IOException if an I/O error occurs during controller execution
   */
  void go() throws IOException;
}
