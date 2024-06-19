package view;

import java.util.ArrayList;
import java.util.Date;

/**
 * This interface allows for
 */
public interface IViewGUI extends IView {
  void addViewListener(IViewListener viewListener);

  void requestFocus();

  void setVisible(boolean value);

  void displayPortfolios(String portfolios);

  void setPortfolioButtons(ArrayList<String> portfolios);

  void setStocks(ArrayList<String> stocks);


  void displayHeader();

  void displayButtonOptions();

  void displayPort();

  void displayStocks();

  void setValue(double value, Date date);

  void setComposition(ArrayList<String> comp, Date date);

}
