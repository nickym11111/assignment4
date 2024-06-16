package command;

import java.util.Scanner;

import model.ISmartPortfolio;

/**
 * An IPortfolioStrategies is a specific type of strategy that applies to a portfolio,
 * so it performs a specific functionality using the data of an IPortfolio.
 */
public interface IPortfolioStrategies {
  /**
   * stratGo method for an IPortfolioStrategies takes in a scanner which holds
   * user input and an IPortfolio object, and performs a specific functionality
   * or operation on the portfolio data.
   *
   * @param s         represents a scanner that holds the user input.
   * @param portfolio represents an portfolio object
   */
  void stratGo(Scanner s, ISmartPortfolio portfolio);
}
