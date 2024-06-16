package model;

import java.io.FileNotFoundException;
import java.util.Objects;

public class SmartStockMarket extends StockMarket implements ISmartStockMarket{
  private String data;

  public SmartStockMarket() throws FileNotFoundException {
    super();
    this.data = "";
  }

  @Override
  public String getData() {
    return super.getPortfolios().toString();
  }

  @Override
  public void setData(String data) {
    this.data = Objects.requireNonNull(data);
  }
}
