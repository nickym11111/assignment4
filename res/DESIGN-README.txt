
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