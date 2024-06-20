package view;

import java.util.ArrayList;
import java.util.Date;

/**
 * This interface represents a view that uses a GUI to display the program to the user.
 */
public interface IViewGUI extends IView {
  /**
   * addViewListener is a method that adds the given IViewListener to the list of IViewListeners
   * that the implementation has.
   *
   * @param viewListener represents an IViewListener to the list of viewListeners for this IViewGUI
   */
  void addViewListener(IViewListener viewListener);

  void setVisible(boolean visible);

  void requestFocus();

  /**
   * setPortfolioButtons creates a list of buttons and adds them to a panel to be displayed to the
   * user. It gets the list of portfolio from the controller which gets them from the model. These
   * buttons will then be clicked to show the options of actions for each portfolio.
   *
   * @param portfolios represents the list of portfolio names that will each become a button
   */
  void setPortfolioButtons(ArrayList<String> portfolios);

  /**
   * displayHeader creates the header that welcomes the user with a message and demonstrates
   * how to use the user interface to the user.
   */
  void displayHeader();

  /**
   * displayButtonOptions creates a dialog with the buttons for buying, selling, getting value, and
   * getting the composition for a stock. Each of these buttons are clickable for the user and
   * then call a new method to create a new pop up to intake the information for that command.
   */
  void displayButtonOptions();

  /**
   * displayPort displays the portfolio buttons in a panel in the middle of the screen.
   */
  void displayPort();

  /**
   * setValue displays the value when a user tries to get a value for a given user.
   *
   * @param value represents the given value of the given portfolio
   * @param date represents the date of the value
   */
  void setValue(double value, Date date);

  /**
   * setComposition displays the composition of a given portfolio on a specific date.
   *
   * @param comp represents the given value of the given portfolio
   * @param date represents the date of the value
   */
  void setComposition(ArrayList<String> comp, Date date);

}
