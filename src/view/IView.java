package view;

/**
 * Defines the contract for a view in the application.
 */
public interface IView {

  /**
   * Writes a message to the view.
   *
   * @param message the message to be written
   */
  void writeMessage(String message);
}
