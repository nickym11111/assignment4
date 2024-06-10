package view;

import java.io.IOException;
import java.util.Objects;

/**
 * A class that outputs a message to user.
 */
public class ViewImpl implements IView {
  private final Appendable out;

  /**
   * Constructs a new ViewImpl object with the specified message.
   *
   * @param out the appendable to write messages to
   * @throws NullPointerException if the specified appendable is null
   */
  public ViewImpl(Appendable out) {
    this.out = Objects.requireNonNull(out);
  }


  /**
   * Writes a message to the view by appending the given string to the
   * view's appendable, out.
   *
   * @param message the message to be written
   */
  @Override
  public void writeMessage(String message) throws IllegalStateException {
    try {
      out.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
