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
Due to not needing to make any changes to implement our new features we did not
to add a new class and extend this class we continued to use our original version.

2. StockShare Class:
The StockShare class facilitates the management of individual stock holdings within a portfolio, allowing for
easy retrieval. This class offers to retrieve data such as the stock and the amount of shares associated with that stock.
Due to not needing to make any changes to implement our new features we did not
to add a new class and extend this class we continued to use our original version.

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

6. SmartPortfolio Class:
The SmartPortfolio class introduces advanced functionalities for managing stock portfolios, enhancing the
basic capabilities provided by its superclass, Portfolio. It extends its features by allowing users to add and
sell shares , tracking the number of shares bought and sold through bought and sold maps respectively.
Additionally, it maintains an up-to-date record of the current state of shares (current map) and the date of
portfolio creation (dateCreated). Key methods such as addStockShare() and removeStockShare() facilitate
the addition and removal of stock shares with validation checks ensuring transaction integrity and
portfolio consistency. The portfolioStateAtDate() method retrieves the current state of the portfolio on a
specific date, providing insights into the holdings and their respective quantities.

7. SmartStockShares:
The SmartStockShares class enhances the functionality of tracking stock shares within a portfolio by incorporating
features related to purchase date and purchase status. It extends the basic properties of StockShares by adding
methods that allow for setting and retrieving the date when shares were purchased
as well as determining whether shares have been bought. This class supports management of
stock transactions within a portfolio, ensuring accurate record-keeping of purchase dates and transaction statuses.


9. ModelUtils
The ModelUtil class serves as a  utility for initializing and managing data within the
stock market system. It controls the  integration of stock and portfolio data by reading
files from designated directories (stocks and portfolios). Through its initializeData method,
this utility  populates the stock market instance  with stocks and
portfolios, using FileStockDataStreamImpl for reading stock data and StockBuilderImpl for
constructing stock objects. Additionally, it handles portfolio specifics such as current stock shares,
bought shares, sold shares, and creation dates through file parsing and mapping methods.


10.DateUtils
The DateUtil class serves as a utility for handling dates within a system, offering essential
methods for date manipulation and validation. It includes getNearestAvailableDate,
which adjusts a given date to the nearest weekday (Friday),
ensuring operational efficiency by providing a suitable business day for scheduling. Additionally,
isWeekend determines whether a given date falls on a weekend (Saturday or Sunday), aiding in
conditional logic and scheduling processes that require distinguishing between weekdays and weekends.
Together, these methods enhance the functionality of date management, facilitating streamlined
operations and ensuring accurate date-based within the system rather than giving the user no information.

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

11. BuyStockCommand Class:
The BuyStockCommand class facilitates the process of purchasing stocks shares within a portfolio management
system. When executed, it prompts the user through a series of interactive prompts, beginning with the
input of the desired number of shares to be bought and the transaction date. Utilizing input validation,
it ensures that only valid, positive integers are accepted for shares, and valid date formats are
provided.  Finally, it ensures data integrity by saving portfolio updates
using the SavePortfolioOperation, thereby ensuring that all transactions are persisted for
future reference and analysis.


12. PortfolioCompositionCommand Class:
The PortfolioCompositionCommand class allows users to view the composition of a portfolio at a
specific date by prompting them for input and retrieving the relevant data.
The class prompts the user to enter a date in the format YYYY-MM-DD. It validates the input
date, ensuring it is correctly formatted and not earlier than the portfolio's creation date.
Once a valid date is provided, the class retrieves the composition of the portfolio as of that date.
This includes details such as the stock tickers and the number of shares held for each stock.
The retrieved information is then displayed to the user through the view, offering a detailed
snapshot of the portfolio's holdings on the specified date.


13. PortfolioDistributionCommand Class:
The PortfolioDistributionCommand class is designed to help users understand the
distribution of their investment portfolio on a specific date. When executed,
it prompts the user to enter a date in the format YYYY-MM-DD and validates this input.
If the provided date is earlier than the portfolio's earliest purchase date, the user is
informed that distribution data is unavailable for such dates. For valid dates,
the class retrieves the portfolio's composition at that date, displaying the value
of each stock owned. This includes calculating the total value of the shares for each
stock based on their value on the specified date.


14. SellStockCommand Class:
The SellStockCommand class is designed to handle the user operation of selling stocks shares from a
specified portfolio. It begins by prompting the user to input the ticker symbol of the stock
they wish to sell, ensuring that the provided symbol is valid. Next, it asks for the number
of shares to be sold, validating that the input is a positive whole number to avoid any invalid
transactions. Following this, the class requests the date on which the sale should occur,
performing validation to ensure the date is correctly formatted and valid. Upon gathering
this information, the SellStockCommand executes the sell operation on the portfolio,
adjusting the portfolio to reflect the sale of the specified stock, number of shares,
and date. After the sell operation, the class saves the updated portfolio state to
maintain data consistency.


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

5.GetPortfolioPerformance Class:
The GetPortfolioPerformance class features methods to calculate and display the financial performance of
a portfolio over a specified time range, validating dates, adjusting for weekends, and using
market data to compute and present the portfolio's value at regular intervals.


6.GetStockPerformance Class:
The GetStockPerformance class features methods to calculate and display the financial performance of
a stock over a specified time range, validating dates, adjusting for weekends, and using
market data to compute and present the portfolio's value at regular intervals. However, for stocks we do
not have the date for like holidays we will inform the user that we do not have access to that stock when
inputting it has a start date or end date.

7.RebalancePortfolio Class:
The RebalancePortfolio class allows users to rebalance their investment portfolio by redistributing
their investments according to specified target percentages. The class provides a method
for gathering user input regarding the target distribution and the date for rebalancing.
It then calculates the necessary buy or sell transactions to adjust the portfolio to the desired state.
The class ensures that the total target distribution does not exceed 100% and that the number of
stocks in the portfolio matches the number of stocks specified in the target distribution.
After rebalancing, it provides detailed information about the rebalanced portfolio,
displaying the value and state of the portfolio on the specified date.




*** PLEASE BE CAREFUL RUNNING THE CREATE PORTFOLIO AND INPUT PORTFOLIO BECAUSE WE WILL ACTUALLY
CREATE NEW FILES ON YOUR COMPUTER IN THE PROGRAM THEN CAUSE ALL OUR OTHER TEST TO FAIL.