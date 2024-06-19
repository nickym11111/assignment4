package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.swing.text.View;

import command.ValuePortfolio;
import command.readerbuilder.APIStockDataStreamImpl;
import command.readerbuilder.SavePortfolioOperation;
import command.readerbuilder.StockBuilderImpl;
import model.ISmartPortfolio;
import model.ISmartStockMarket;
import model.ISmartStockShares;
import model.IStock;
import model.IStockMarket;
import view.IView;
import view.IViewGUI;
import view.IViewListener;

/**
 * Created by vidojemihajlovikj on 6/10/24.
 */
public class StockControllerGUI implements IController, IViewListener {
  private final IStockMarket stockMarket;
  private final IViewGUI view;

  public StockControllerGUI(IStockMarket stockMarket, IViewGUI view) {
    this.stockMarket = Objects.requireNonNull(stockMarket);
    this.view = Objects.requireNonNull(view);
    this.view.addViewListener(this);
  }

  @Override
  public void goController() {
    view.setVisible(true);

    view.displayHeader();
    view.displayButtonOptions();
    view.displayPort();
    view.displayStocks();
  }
  //public void handleGetDataForStockAndDate(String ticker, String date){
  /*
      IStock stock = this.model.getStock(ticker);
      double price = stock.getPrice(date);
      view.displayStock(ticker, price);
   */

  @Override
  public void handleDisplayPortfolios() {
    Map<String, ISmartPortfolio> portfolios = this.stockMarket.getPortfolios();
    StringBuilder sb = new StringBuilder();
    for( Map.Entry<String, ISmartPortfolio> entry : portfolios.entrySet() ){
      sb.append(entry.getKey());
      sb.append("\n");
    }
    this.view.displayPortfolios(sb.toString());
    this.view.requestFocus();
  }

  public void getPortfolioButtons() {
    Map<String, ISmartPortfolio> portfolios = this.stockMarket.getPortfolios();
    ArrayList<String> names = new ArrayList<>(portfolios.keySet());
    this.view.setPortfolioButtons(names);
    this.view.requestFocus();
  }


  public  void getStocks() {
    Map<String, IStock>  stocks = this.stockMarket.getStocks();
    ArrayList<String> names = new ArrayList<>(stocks.keySet());
    this.view.setStocks(names);
    this.view.requestFocus();
  }

  public void handleCreatePortfolios(String name) {
    stockMarket.addPortfolio(name);
    new SavePortfolioOperation(stockMarket.getPortfolio(name)).run();
    this.view.requestFocus();
  }

  @Override
  public void handleBuyStock(Date date, String ticker, int shares, String portfolio) throws FileNotFoundException {
    ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
    LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    APIStockDataStreamImpl apiStockDataStream = new APIStockDataStreamImpl(ticker);
    StockBuilderImpl stockBuilder = new StockBuilderImpl();
    IStock stock = stockBuilder.buildStock(ticker, apiStockDataStream);
    p.addStockShare(ticker, stock, shares, d);
    new SavePortfolioOperation(p).run();


    System.out.println("reached handle buy stock");
    this.view.requestFocus();
  }

  @Override
  public void handleSellStock(Date date, String ticker, int shares, String portfolio) throws FileNotFoundException {
    ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
    LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    p.removeStockShare(ticker, shares, d);
    new SavePortfolioOperation(p).run();
    System.out.println("reached handle buy stock");
    this.view.requestFocus();
  }

  @Override
  public void handleGetValue(Date date, String portfolio) {
    ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
    LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    double value = new ValuePortfolio(this.view).getPortfolioValue(d, p);
    this.view.setValue(value, date);
    this.view.requestFocus();
  }

  @Override
  public void handleGetComposition(Date date, String portfolio) throws FileNotFoundException {
    ISmartPortfolio p = stockMarket.getPortfolio(portfolio);
    LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    Map<String, ISmartStockShares> port = p.portfolioStateAtDate(d);
    ArrayList<String> names = new ArrayList<>();
    for(Map.Entry<String, ISmartStockShares> entry : port.entrySet()) {
      ISmartStockShares s = entry.getValue();
      String name = entry.getKey();
      double shares = s.getShares();
      String line = name + ": " + shares;
      names.add(line);
    }
    this.view.setComposition(names, date);
    this.view.requestFocus();
  }


}
