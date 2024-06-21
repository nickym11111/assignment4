 :**Prerequisites:**

1.
   - Ensure you have Java installed on your system. You can verify this by running `java -version` in your command line.
   - If Java is not installed, download and install it from the official website: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
   -

   To run the Jar file, please follow these instructions:
   - copy the jar file
   - paste it into a new folder
   - copy the stocks directory
   - paste it into the new folder
   - copy the portfolios directory
   - paste it into the new folder
   - cd to that folder
   - run : java -jar NameOfJarFile.jar
   - to run text based interface: java -jar NameOfJarFile.jar -text
   (if your having problems check that you have the most recent java on your computer (22))
   (if it says its picking up a DS_STORE portfolio run: find . -name ".DS_Store" -delete)
   (if having issues be sure to call the delete line above in the portfolios folder of the new folder you made
   and possibly the portfolios in the assignment file)
   - to open it with the most recent java : ~/OpenJDK/jdk-22.0.1.jdk/Contents/Home/bin/java -jar NameOfJar.jar


** Creating and Managing portfolios ** FOR ASSIGNMENT 3:

All saved portfolios are displayed on the opening page of the GUI. To create a new portfolio press
the create portfolios button, enter the name, and press enter. To buy, sell, find value, or composition
of any portfolio, click on the button for that portfolio itself. To close the program press the quit
button on the front page.


** Creating and Managing Stocks **

- **Important Notes:**
  - Ensure that the dates entered are within the supported ranges for each stock.
  - The program will notify you if the entered date is not available or invalid. However,
    calculating the moving average, crossover days,

List of Supported Stocks:

  - AAPL (Apple Inc.)
  - MFST (Microsoft Corporation)
  - GOOG (Alphabet Inc.)
  - META (Facebook, Inc.)
  - AMZN (Amazon.com)
  - NKE (Nike)
  - CMCSA (Comcast)

Available Dates for Stock Date:

   - AAPL: 1999-11-01 to 2024-06-13
   - MFST: 1999-11-01 to 2022-04-29
   - GOOGL: 1999-11-01 to 2024-6-7
   - META: 1999-11-01 to 2024-6-7
   - AMZN: 1999-11-01 to 2024-06-10
   - NKE: 1999-11-01 to 2024-06-13
   - NFLX: 2003-09-19 to 2024-06-10
   - CMCSA: 1999-11-01 to 2024-06-10


 ** Creating and Managing Portfolios **

Create Portfolio1 with 3 stocks
Once the program is running, follow these steps:

     1. Select the option to create a new portfolio.
 Type: Enter 'portfolio' to create a new portfolio

     2. Select the option "p"

     3. Enter the name for your new portfolio when prompted.
 Type: name of choice

     4. Enter the amount of stock you will being adding.
 Type: 3

    5. Enter the date you would like to buy the stock (YYYY-MM-DD)
    Type date of choice (EX: 2019-03-19, 2020-08-08, 2021-09-02)


    6. How would you input the stock data?
 Type : ‘a’ for API
 Type “ ‘f’ for saved files

   6. If you entered: ‘f" Enter CSV file
 Type : STOCKTICKERSYMBOL.csv (stocks/STOCKTICKERSYMBOL.csv if you're getting it from our stocks folder)

   7. If you entered: ‘a", Enter CSV file
   Type : 4 captial letters for stock

   8. repeat these steps for all stocks in the portfolio (will be prompted to do so)

   9. How to know your profolio has been created:

   On Screen will prompt:
   Successfully entered stock data
   Successfully entered portfolio data

** Once you add all the stocks to portfolio **

    1. Go back to main menu
Type: 'b'

    2. Now view information about portfolio
Type: ‘vp”

    3. Will show you the portfolio that have been made if
your is not there then it has not been full create

    4.Type which  portlfio you want
Type: Name

    5.To see the value press ‘v’
Type: ‘v’

    6.Then type in date
Type : YYYY-MM-DD  (EX: 2021-12-13)

    7. It will then bring you back to the menu, to view the cost basis or distribution,
    Enter d
    Type: d

    8. Then enter a date (YYYY-MM-DD) (EX: 2024-05-01)


FROM ASSIGNMENT 1:

Create Portfolio1 with 2 stocks
Once the program is running, follow these steps:

     1. Select the option to create a new portfolio.
 Type: Enter 'portfolio' to create a new portfolio

     2. Select the option "p"

     3. Enter the name for your new portfolio when prompted.
 Type: name of choice

     4. Enter the amount of stock you will being adding.
 Type: 2

    5. How would you input the stock data?
 Type : ‘a’ for API
 Type “ ‘f’ for saved files

   6. If types ‘f" Enter CSV file
    Type : STOCKTICKERSYMBOL.csv

   7. If types ‘f" Enter CSV file
   Type : 4 captial letters for stock


** Once you add all the stocks to portfolio **

    1. Go back to main menu
Type: 'b'

    2. Now view information about portfolio
Type: ‘vp”

    3. Will show you the portfolio that have been made if
your is not there then it has not been full create

    4.Type which  portlfio you want
Type: Name

    5.To see the value press ‘v’
Type: ‘v’

    6.Then type in date
Type : YYYY-MM-DD






