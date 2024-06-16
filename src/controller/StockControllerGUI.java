package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

import javax.swing.text.View;

import model.ISmartStockMarket;
import model.IStockMarket;
import view.IView;
import view.IViewGUI;
import view.IViewListener;

/**
 * Created by vidojemihajlovikj on 6/10/24.
 */
public class StockControllerGUI implements IController, IViewListener {
  private final ISmartStockMarket stockMarket;
  private final IViewGUI view;

  public StockControllerGUI(ISmartStockMarket stockMarket, IViewGUI view) {
    this.stockMarket = Objects.requireNonNull(stockMarket);
    this.view = Objects.requireNonNull(view);
    this.view.addViewListener(this);
  }

  @Override
  public void goController() {
    view.setVisible(true);
  }
  //public void handleGetDataForStockAndDate(String ticker, String date){
  /*
      IStock stock = this.model.getStock(ticker);
      double price = stock.getPrice(date);
      view.displayStock(ticker, price);
   */
  public void handleSetData(){
    this.stockMarket.setData( this.view.getData() );
    this.view.requestFocus();
  }

  public void handleGetData(){
    this.view.setData( stockMarket.getData() );
    this.view.requestFocus();
  }
}
