package command;

import java.util.Scanner;

import model.IStock;

/**
 * An IStockStrategies is a specific type of strategy that applies to a stock,
 * so it performs a specific functionality using the data of an IStock.
 */
public interface IStockStrategies {
  /**
   * stratGo method for an IStockStrategies takes in a scanner which holds
   * user input and an IStock object, and performs a specific functionality
   * or operation on the stock data.
   *
   * @param scanner represents a scanner that holds the user input.
   * @param stock   represents an IStock object
   */
  void stratGo(Scanner scanner, IStock stock);
}
