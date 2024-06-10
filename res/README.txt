Stock Market Portfolio Management System
========================================


Overview
--------
This program is designed to manage stock market portfolios, including tracking daily stock data, managing multiple
portfolios, and calculating portfolio values. The system supports adding stocks, creating portfolios, and retrieving
stock and portfolio information.


Features That Properly Work In Model
--------

1. DailyStock Class:
Holds the daily stock data with information such as ticker symbol, date, open, high, low,
close, and volume. This class allows users to receive any specific information about a stock on a specific date.

2. StockShare Class:
The StockShare class facilitates the management of individual stock holdings within a portfolio, allowing for
easy retrieval. This class offers to retrieve data such as the stock and the amount of shares associated with that stock.

3. Portfolio Class:
The Portfolio class manages a collection of stock shares, allowing users to add and retrieve shares of various stocks
within the portfolio. It provides methods to add stock shares, retrieve the name of the portfolio, and access the map
of stock shares. Additionally, it ensures that each portfolio is uniquely identified by its name.

4. Stock Class:
The Stock class represents a stock in the stock market, storing daily stock data mapped by date. It provides methods
to retrieve daily stock data, check the availability of data for a specific date, and calculate the closing value of
the stock for a given date. Additionally, it offers functionality to manage the most recent and earliest dates for
which stock data is available.

5. StockMarket Class:
The StockMarket class serves as a center that manages stocks and portfolios in the application. It offers methods that
allow users to add, check, and retrieve stocks and portfolios. This class ensures data integrity by organizing
and storing the data in HashMaps to have easy access and properly create a manipulation of stock market data.


Features That Properly Work For Command
--------

1. CheckSpecificPortfolioCommand Class:
The CheckSpecificPortfolioCommand class interacts with the IStockMarket class to retrieve portfolio data and provides options
for users to evaluate portfolio values or return back to the previous stages. The command allows user to input a
specific portfolio name and offers instructions for portfolio evaluation. In the case that the user inputs a portfolio
that does not exist in our system it will prompt the users to either re-enter the portfolio name or return back to the
previous stages.

2. CheckSpecificStockCommand Class:
The CheckSpecificStockCommand class interacts with IStockMarket to examine specific stock statics within the stock market.
It retrieves stock data and provides options for users to view gain/loss, moving averages, and crossovers for particular
stock. The command prompts the user to input a stock ticker symbol and offers instructions for viewing different stock
statics. In the case that the user inputs a stock that does not exist in our system it will prompt the users to either
re-enter the ticker symbol or return back to the previous stages.

3. CreatePortfolioCommand Class:
The CreatePortfolioCommand class allows the users to create a new portfolio within the stock market system. It will prompt
the users to provide a name for portfolio, specify the number of stocks they want to include, and input the shares for each
stock. Once all data is entered, the portfolio is hard-saved for future access even after the program is closed.

4. InputAPIStockCommand Class:
The InputAPIStockCommand class is responsible for allowing users to input a new stock using the API. It prompts the user
to enter the ticker symbol of the desired stock company. Then it will build a stock object though the API source. If there
are no more API quires, it notifies the user and suggests viewing and inputting stocks from saved files as alternative.

5. InputPortfolioCommand Class:
The InputPortfolioCommand class prompts the user to decide whether they want to continue creating a new portfolio or
to go back to previous options.

6. InputStockCommand Class:
The InputStockCommand class enables the user to input stock data from various sources like CSV files or an API. It guides
users though the input process by offer presenting clear options.

7. InputUserFileStockCommand Class:
The InputUserFileStockCommand class handles the input of stock data from user provide CSV file. It prompts the user to
specify the CSV file contain stock information wil the corresponding ticker symbols. Then it will allow the user to
request more information bout the recent inputted stock.


9. ViewPortfolioCommand Class:
The ViewPortfolioCommand represents a command in the program where users can view all current portfolios and choose to
inspect specific portfolio statistics or go back to previous options. It retrieves portfolios stored in the stock market
and displays the names to user. The user can then select a portfolio to view its details or choose to go back. The class
handles invalid inputs by first informing the user that no portfolio with that name exist in the system then prompts the user
to re-enter the portfolio name or go back to pervious options.

10. ViewStockCommand Class:
The ViewStockCommand class is responsible for displaying all current stocks to the user and prompting them to view
statistics about a specific stock or go back to previous options. It retrieves the list of stocks from the stock market
and iterates through them to display their names. The user can then choose to view statistics for a specific stock by
entering 's' or go back by entering 'b'. If the user enters an unrecognized instruction, an appropriate message is
displayed. Additionally, a note is provided indicating that some stocks may not be up-to-date if they were previously
inputted.


Features That Properly Work For Calculations/Strategies:
--------
1. CrossOvers Class:
The CrossOver class is a stock strategy for when crossover days occur which happens when the closing price for a day is
greater than the x-day moving average for that day. It provides a method to calculate the number of crossover days within
a specified date range for the requested stock. It prompts the user to input start and end dates, along with the value of x
for the moving average, and then it will display the list of crossover days. When inputting invalid dates it will adjust
the date for the nearest available date from the date inputted.

2. GainOrLoss Class:
The GainOrLoss class is a stock strategy that calculates the gain or loss of a stock over given period of time, based on
the closing price only. It provides a method that prompts the user to input a start ad end date and then calculates and
displays the difference in closing prices between these dates. When inputting invalid dates it will adjust the date
for the nearest available date from the date inputted.

3. MovingAverage Class:
The MovingAverage class is a stock strategy to calculate the x-day moving average for a given stock starting from a specified
date. It prompts users to input the start date and the number of days for the moving average calculations. When inputting
invalid dates it will adjust the date for the nearest available date from the date inputted.


4.ValuePortfolio Class:
The ValuePortfolio class is a stock strategy within the program that calculates the value of a portfolio at a given date.
It prompts the user to input a date and then computes the total value of the portfolio by multiplying the closing previous for
each stock by the number of shares and adding them together. If the user incorrectly inputs the date, it notifies the user and
prompts them to try the process again.

