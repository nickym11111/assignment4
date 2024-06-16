
DESIGN READ-ME FOR PART 2:

Our design for part 2 followed the same MVC structure as part 1 as well as the command design
and strategy pattern from part 1. To see the design documentation for part one, read below in the
part 1 section.

In part two we minimalized modifications to our old code by creating interfaces and classes
which extend old interfaces and classes and making new commands and strategies that were easily added
to our controller.

Here are the design changes we made in this assignment by package:
----------------------------------------------------------------------------------------------------
In the model:

We wanted a new way to store portfolio information so that everything was time stamped. We also
needed to add new portfolio features like buy and sell.

For this reason, we decided to make a new ISmartPortfolio interface. This interface extends our old
interface to ensure all of the old functionalities still work for a portfolio and has new methods to
implement our new features. Then we created a SmartPortfolio class that extends our old Portfolio
 class and implements the ISmartPortfolio interface. By creating a new class we don't have to change
 our old class code and we can use new classes and interfaces to extend our old code to keep our old
 functionality while still implementing new features.

We did a similar thing for our StockShares class, with the new need to have every buy and sell
(share of stock) time stamped and the addition of new methods, we decided to make a new class and
interface to represent a Smart StockShare. We made a new interface called ISmartStockShares that
extended our old StockShares interface; we made a new class called SmartStockShares that extended
our old stock share class and implemented our new ISmartStock share interface. The creation of a new
interface and a new class for stock shares allows us to have new fields and methods for dates, buys
and sells without changing any of our old code.

The new sets of interfaces and classes we made for portfolios and stock shares in our model demonstrate
the solid principles and show that our code is generally closed to modification and open to extension.

There were some small changes that we made to our old code in the model that were necessary and justified.
Those changes were:

1. Changing the stock shares ‘shares’ field to a double. We were given feedback on our part one and
had points taken off for not being able to share a fractional amount of stocks. The deduction of points
from part one in addition to the requirement of being able to hold fractional shares in part 2 was our
reasoning for changing this field to be a double so that we could support the necessary requirements
of this assignment.

2. Changing the portfolios field (Map<String, IPortfolio>) in StockMarket to be a
Map<String, ISmartPortfolio>. Here we just changed the type of portfolio to the smart portfolio so
that our user can have all of the new features while still keeping all of our old functionality.
Since we didn't add any new fields or methods to the IStockMarket interface or class we didn’t find
it necessary to create a whole new extended ISmartStockMarket, instead, we changed that portfolio
return in a few places throughout the class.

3. We also updated our ModelUtil class to read in a new portfolio format, since this is our utils
class we found it reasonable to add our new implementation directly to the class since it is a helper
class to initialize our data.

4. Other small changes based on feedback from part 1 include:
   1. Changing StockMarket methods to return Map type instead of hashmap.
   2. Changing those same StockMarket methods to return a copy of the map, not the map itself.

We did not make a new SmartStock interface or class since there were not any new methods or fields we
needed to add to our stock class. So we were able to entirely reuse our stock class and IStock
interface for this assignment.

Overall these were the changes we made to our model class incorporating new classes and interfaces
that use our old code to implement new features without changing old classes.
----------------------------------------------------------------------------------------------------

In the controller:

We did not have to change any code in the controller package. Using commands allowed us to use
our old controller class and interface in this assignment as well and easily add new command functionality
in the command package. The only minor change we made to the controller was changing our go method
name to be longer for java style.

----------------------------------------------------------------------------------------------------

In the command:

The design of our command package is the same as written in part 1 for background reference.

In the command package we made new classes for new features. New features were based in calculations
or data from a stock or portfolio were created as strategies. On the other hand new features that
were based on a user action or the user input were created as commands.

The new command classes we added were:
1. BuyStockCommand
2. SellStockCommand
3. PortfolioCompositionCommand
4. PortfolioDistributionCommand

The purpose of each of these commands is self-explanatory based on the name, they all represent
a specific action the user has taken to do something (sell, buy, view composition, distribution, etc).
This design is easy to read and each class has one action purpose.

The new Strategy classes we added were:
1. GetPortfolioPerformance
2. GetStockPerformance
3. RebalancePortfolio
4. APerformance (abstract class to hold helper methods for similar performance code)

Once again, the name explains the action of the strategy. Each of these represents a calculation we
make using the stock data that we can then show to the user. By using strategies
we easily added more operations as strategies without touching the classes of old ones
and can repeat this process in the future. Additionally, we didn't have to change
our controller or other commands to implement new commands or strategies.

The only minor but necessary changes we made in the command package were:
 1. Changing some return types from IPortfolio to ISmartPortfolio. Since our model (StockMarket)
 now holds ISmartPortfolio instead of IPortfolio, now the portfolios being passed into any
 portfolio strategy would be ISmartPortfolio. (EX: We changed the parameter type of ValuePortfolio
 to ISmartPortfolio)

 2. Adding view.writeMessage lines to commands as well as adding new cases to switch statements in
 commands. We did this to provide the user access to the new features. For example, all of our code from
 check specific portfolio/stock commands remained unchanged except we added the messages for entering
 new letters for new features as well as new cases in the switch statement for when the user entered that
 letter. For example, adding the message "Enter 'p' to see the performance of this stock/portfolio"
 and then having a case in the switch that calls the GetStock/PortfolioPerfomance strategy. These
 were minor and justified changes that allowed us to extend new functionality to the user without making
 an entirely new controller and set of commands.

Overall we added new commands and strategies to create new functionality without changing old code
to extend from our previous design without heavy modifications.

----------------------------------------------------------------------------------------------------

In readerbuilder (inside of command) :

This package was the same, with the same classes. All classes remained unchanged except for SavePortfolioOperation.
We changed this class to now save a portfolio as a directory with the portfolio name as the directory with 4 csv files
inside representing, all buying history, all selling history, the current state of the portfolio, as well as
the date created. We made this change in order to save important portfolio data with time stamps and be able to
load it in everytime the program restarts. We believe it was reasonable to add our new saving functionality
into our SavePortfolioOperation class since this is just a helper class to help us save our data into files.

----------------------------------------------------------------------------------------------------

In view:

Our view remains entirely unchanged from the last assignment except we moved our StockProgram class
that holds our main method to outside of the package per feedback from assignment 1. Due to our interface
and class implementation from part 1 we were able to reuse our view class/interface and view methods
without changing any of our old code demonstrating good design.


=======================================================================================================

DESIGN READ-ME FOR PART 1:

Our design as followed the model, view, and controller structure as well as using
command design and strategies to further organize our functionality.
So to describe our design I will go over each part of the MVC.

=======================================================================================================

The first package is the Model. In the model, we have all of our data-representative
object classes. These model classes represent objects that hold important data for
our program such as stocks and portfolios. The classes and interfaces in the model are:

IStock which has the subclass Stock where we represent our stock objects.
The stock objects hold the ticker symbol, oldest date, newest date, and a map of date -> daily stock
to represent the daily stock data for that stock.

DailyStock is a class where we hold the stock information for one day:
the ticker symbol, date, open, close, high low, and volume.

StockShares is a class where we hold an IStock and its respective number of shares, so
they can be easily held together and well organized in the portfolios.

IPortfolio which has the subclass Portfolio where we represent our portfolios.
We represent our portfolios using strings for the name and a map of the ticker symbol-> stock shares
to represent which companies are in the portfolio and how many shares of each.

Finally, we have the IStockMarket which has the subclass StockMarket. The StockMarket holds a hashmap
of all the stocks and all the portfolios with their respective names. This stock market is initialized
to use all saved data each time the program is run so that previously inputted stocks and portfolios
will be available to the user.

We decided to design our model like this to neatly store all the relevant data within classes that create
objects for that data. We broke it down from the smallest scale: a stock for one day (DailyStock), to a
stock history, (IStock/ Stock), to a stock and a share (StockShares), to a group of stocks'
(IPortfolio/Portfolio), to a group of stocks and portfolios (IStockMarket, StockMarket). This is an
organized design to hold our data and also create space for new data to be held in other classes and
interfaces for future features.

============================================================================================================

The next package is the view, the view package has an IView interface, an
ViewImpl class, and a StockProgram class with our main method.
The IView interface is an interface that represents a way of viewing,
and our ViewImpl is an implementation of that interface that implements methods
based on the current view format which is text-based. The view's purpose is to display
output to the user and for now it is text-based, in the future that may change which
is why we chose to implement the view this way so that in the future we could easily
change the view to output using GUI or other formats and not have to extensively
change our code.

============================================================================================================

The next package is the controller, which contains an IController and a StockController. These
are a standard Interface and class that handle user input and are called from StockProgram. The initial
options for input are to view stocks, input a stock, view portfolios, input a portfolio or quit.
Our control uses the StockMarket class as the model class that it accesses data from since
that class holds all the data of current stocks and portfolios.
From a design perspective, these options allow for an easy user experience and clear options for the user
to navigate.

============================================================================================================

The next package is the command package which has all the commands and strategies. We split each action
that the user can do into a command, so everywhere we had a switch statement that broke off
into multiple different actions, each other of those actions would be a command. So our commands consisted of:
InputAPIStockCommand, InputPortfolioCommand, InputStockCommand, InputUserFileStockCommand,
ViewPortfolioCommand, ViewStockCommand, CheckSpecificPortfolioCommand, CheckSpecificStockCommand,
and CreatePortfolioCommand. The action for each of these commands is self-explanatory based on their name.
We decided to use commands for each user action so that for future features new user actions can be easily
added without heavily changing old code. In addition, this design is easy to read and each class has one action
purpose. All commands extend the ACommand abstract class which implements the ICommand interface, this way
new commands can easily be added to the interface and have the same fields/constructor as the abstract class to
avoid duplicate code.

In the command package, we also have the interface IStrategies which represents a strategy,
or a specific functionality/operation applied to an object. Then we have IStockStrategies and
IPortfolioStrategies which extend this IStrategies and are more specified to take in an object
of that type (stock vs portfolio). IStockStrategies subclasses are: GainOrLoss, Crossovers, and
MovingAverage, and IPortfolioStrategies subclasses are ValuePortfolio. These 4 classes are
the specific operations that are being performed in this assignment. By organizing them as strategies
we can easily add more operations as strategies in the future without touching the classes of old ones
and our design is well organized.

In our command package, we also had a readerBuilder class which has an interface IStockDataStream.
This interface represents any type of data that can be used to create a stock object and can be
used to create a readable. The current subclasses for this interface are FileStockDataStreamImpl
and APIStockDataStreamImpl which are the current types of data used to make a stock: file and API.
In the future, we can easily add new data sources or different types of files using this design
and not change anything like the constructor of our stock. The stock is built using the data streams
from the builder class. In the package, we have the IStockBuilder interface which represents a builder
class or a stock. The implementation, StockBuilderImpl parses through the readable of the data stream
and adds the data to a stock object. This builder class allows for an organized construction of a stock
through different types of data.

