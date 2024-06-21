package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.swing.border.Border;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.UIManager;


/**
 * ViewGUIImpl is responsible for managing the graphical user interface
 * of the stock market program. It allows users to create portfolios, buy
 * and sell stocks, and view portfolio values and compositions.
 */
public class ViewGUIImpl extends JFrame implements IViewGUI, ActionListener {
  private final JButton createPortfolio = new JButton("Create Portfolio");
  private final List<IViewListener> myListeners;
  private final List<JButton> portfolioButtons;
  private JDialog portfolioOptions;
  private JDialog currentDialog;
  private JPanel newPortfolioPanel;
  private static final Font buttonFont =
          new Font("Monospaced", Font.BOLD, 14);
  private static final Dimension buttonSize = new Dimension(150, 50);
  private static final List<JButton> finalPortfolioButtons;
  private static final Color backgroundColor = Color.WHITE;
  private JComboBox<Integer> days;
  private JComboBox<Integer> months;
  private JComboBox<Integer> years;
  private final JPanel datePicker = new JPanel();
  private final JTextField tickerSymbolField;
  private final JTextField sharesField;
  private String currentPortfolio;
  private JPanel mostRecentMessageDisplayPanel = new JPanel();

  static {
    finalPortfolioButtons = new ArrayList<>();
    JButton compButton = new JButton("Composition");
    JButton valueButton = new JButton("Value Portfolio");
    JButton sellButton = new JButton("Sell Stock");
    JButton buyButton = new JButton("Buy Stock");
    JButton[] portOptions = {compButton, valueButton, sellButton, buyButton};
    for (JButton button : portOptions) {
      button.setFont(new Font("Monospaced", Font.PLAIN, 14));
    }
    buyButton.setActionCommand("Buy Stock");
    sellButton.setActionCommand("Sell Stock");
    valueButton.setActionCommand("Value");
    compButton.setActionCommand("Comp");
    finalPortfolioButtons.addAll(Arrays.asList(compButton, valueButton, sellButton, buyButton));
    for (JButton button : finalPortfolioButtons) {
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
  }

  /**
   * Constructs a ViewGUIImpl object will initialize the main fields.
   */
  public ViewGUIImpl() {
    initializeProgram();
    myListeners = new ArrayList<>();
    portfolioButtons = new ArrayList<>();

    portfolioOptions = new JDialog();
    currentDialog = new JDialog();

    createPortfolio.addActionListener(this);
    createPortfolio.setActionCommand("create Portfolio");
    tickerSymbolField = new JTextField();
    sharesField = new JTextField();
    initializeDatePicker();
  }

  //Initializes the main program window with specified dimensions and settings.
  private void initializeProgram() {
    setSize(new Dimension(900, 400));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    this.setBackground(Color.BLUE);
    newPortfolioPanel = new JPanel();
    newPortfolioPanel.setLayout(new BoxLayout(newPortfolioPanel, BoxLayout.Y_AXIS));
    add(newPortfolioPanel, BorderLayout.CENTER);
    setBackground(backgroundColor);
    setVisible(true);
  }

  // Initializes the date picker component with dropdowns for day, month, and year.
  private void initializeDatePicker() {
    Integer[] daysArray = new Integer[31];
    for (int i = 0; i < 31; i++) {
      daysArray[i] = i + 1;
    }
    days = new JComboBox<>(daysArray);

    Integer[] monthsArray = new Integer[12];
    for (int j = 0; j < 12; j++) {
      monthsArray[j] = j + 1;
    }
    months = new JComboBox<>(monthsArray);

    Integer[] yearsArray = new Integer[100];
    for (int p = 0 ; p < 100; p ++) {
      yearsArray[p] = 2024 - p;
    }
    years = new JComboBox<>(yearsArray);

    JPanel yearPanel = new JPanel(new BorderLayout());
    JLabel yearLabel = new JLabel("Year");
    yearLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
    yearPanel.add(yearLabel, BorderLayout.NORTH);
    yearPanel.add(years, BorderLayout.CENTER);

    JPanel monthPanel = new JPanel(new BorderLayout());
    JLabel monthLabel = new JLabel("Month");
    monthLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
    monthPanel.add(monthLabel, BorderLayout.NORTH);
    monthPanel.add(months, BorderLayout.CENTER);

    JPanel dayPanel = new JPanel(new BorderLayout());
    JLabel dayLabel = new JLabel("Day");
    dayLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
    dayPanel.add(dayLabel, BorderLayout.NORTH);
    dayPanel.add(days, BorderLayout.CENTER);

    datePicker.setLayout(new GridLayout(1, 3, 10, 10));
    datePicker.add(yearPanel);
    datePicker.add(monthPanel);
    datePicker.add(dayPanel);
    repaint();
    revalidate();
  }

  /**
   * Writes a message to the text-view.
   *
   * @param message the message to be written
   */
  @Override
  public void writeMessage(String message) {
    throw new UnsupportedOperationException("Not supported in GUI implementation.");
  }

  /**
   * Handles various action events triggered by the user. Such as when a portfolio is being
   * created it will make a button. Then if a user would like to buy or sell a shares of stock it
   * prompt them with the following text boxes to enter the information and similar for getting the
   * composition and valur of portfolio. Also,if a user clicks on their portfolio they have
   * options such as selling shares, buying shares, getting the
   * composition or value of their portfolio.
   * @param e The action event triggered by the user.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "portfolio":
        JButton button = (JButton) e.getSource();
        String name = button.getName();
        this.currentPortfolio = name;
        displayPortOptions();
        break;
      case "create Portfolio":
        displayCreatePortfolio();
        break;
      case "Buy Stock":
        displayBuyStocksOption();
        break;
      case "Sell Stock":
        displaySellStocksOption();
        break;
      case "Value":
        displayPortfolioValue();
        break;
      case "Comp":
        displayPortfolioComposition();
        break;
      case "enterInformationBuy" :

        try {
          if (getCurrentShares() < 0) {
            displayCurrentUserMessage("Not able to buy stock, " +
                    "share must be positive whole number");
          }
          fireBuyStock(getCurrentDate(), getCurrentTicker(), getCurrentShares(),
                  this.currentPortfolio);
          displayCurrentUserMessage("Successfully bought stock");
        } catch (Exception ex) {
          displayCurrentUserMessage(ex.getMessage());
        }
        break;

      case "enterInformationSell" :
        try {
          fireSellStock(getCurrentDate(), getCurrentTicker(), getCurrentShares(),
                  this.currentPortfolio);
          displayCurrentUserMessage("Successfully sold stock");

        } catch (Exception ex) {
          displayCurrentUserMessage(ex.getMessage());
        }
        break;
      case "enterValueDate":

        fireValue(this.getCurrentDate(), this.currentPortfolio);
        break;
      case "enterCompDate":

        try {
          fireComp(this.getCurrentDate(), this.currentPortfolio);
        } catch (FileNotFoundException ex) {
          // failed comp
        }
        break;
      default:
        throw new IllegalStateException("Unknown action command");
    }
    resetInputFields();
    updatePanels();
  }

  // Will continue revalidate and repaint the portfolio and main panels.
  private void updatePanels() {
    newPortfolioPanel.revalidate();
    newPortfolioPanel.repaint();
    revalidate();
    repaint();
  }

  // Will reset the all the input fields
  private void resetInputFields() {
    this.days.setSelectedIndex(0);
    this.months.setSelectedIndex(0);
    this.years.setSelectedIndex(0);
    this.tickerSymbolField.setText("");
    this.sharesField.setText("");
  }

  // A helper method that will display the current message on to the user.
  private void displayCurrentUserMessage(String message) {
    mostRecentMessageDisplayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    mostRecentMessageDisplayPanel.removeAll();
    mostRecentMessageDisplayPanel.setLayout(new BoxLayout(mostRecentMessageDisplayPanel,
            BoxLayout.Y_AXIS));

    JLabel messageLabel = new JLabel("Most recent transaction message: ");
    messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel resultedMessage = new JLabel(message);
    resultedMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
    mostRecentMessageDisplayPanel.add(messageLabel);
    mostRecentMessageDisplayPanel.add(resultedMessage);
    Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    mostRecentMessageDisplayPanel.setBorder(padding);
    portfolioOptions.add(mostRecentMessageDisplayPanel, BorderLayout.SOUTH);
    portfolioOptions.revalidate();
    portfolioOptions.repaint();
  }

  // A helper methods that will get the ticker symbol that represents a stock.
  private String getCurrentTicker() {
    return this.tickerSymbolField.getText();
  }

  // A helper that counts the current amount of shares.
  private int getCurrentShares() {
    int shares = -1;
    try {
      shares = Integer.parseInt(this.sharesField.getText());
    }
    catch (NumberFormatException e) {
      //Not an integer
    }
    return shares;
  }

  // a private method that normalizes the Date field and gets the current date.
  private Date getCurrentDate() {
    int day = this.days.getSelectedIndex();
    int year = 2024 ;
    year -= this.years.getSelectedIndex();
    year -= 1900;
    int month = this.months.getSelectedIndex();
    return new Date(year, month, day);

  }

  // A method that gets the information for buying a stock shares from the controller
  // a model to then be viewed in the GUI.
  void fireBuyStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException {
    for (IViewListener myListener : myListeners) {
      myListener.handleBuyStock(date, ticker, shares, portfolio);
    }
  }

  // A method that gets the information for selling a stock shares from the controller
  // and model to then be viewed in the GUI.
  void fireSellStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException {
    for (IViewListener myListener : myListeners) {
      myListener.handleSellStock(date, ticker, shares, portfolio);
    }
  }

  //  A method that gets the information for getting the value of portfolio
  //  from the controller and model to then be viewed in the GUI.
  void fireValue(Date date, String portfolio) {
    for (IViewListener myListener : myListeners) {
      myListener.handleGetValue(date, portfolio);
    }
  }

  //  A method that gets the information for getting the composition of portfolio
  //  from the controller and model to then be viewed in the GUI.
  void fireComp(Date date, String portfolio) throws FileNotFoundException {
    for (IViewListener myListener : myListeners) {
      myListener.handleGetComposition(date, portfolio);
    }
  }

  // A method that displays all the portfolio as buttons.
  void getPortfolioButtons() {
    for (IViewListener myListener : myListeners) {
      myListener.getPortfolioButtons();
    }
  }

  // A method that displays all the new portfolio as a button while
  // adding it to the list of portfolio and saving it on the computer.
  void getNewPortfolio(String name) {
    for (IViewListener myListener : myListeners) {
      myListener.handleCreatePortfolios(name);
    }
  }

  /**
   * Creates buttons for all the portfolio currently in the system.
   * @param names the names of the portfolios in the system.
   */
  public void setPortfolioButtons(ArrayList<String> names) {
    for (int i = 0; i < names.size(); i++) {
      if (names.contains(names.get(i))) {
        addOneButton(names.get(i));
      }
    }
  }

  /**
   * Displays the value of the portfolio on specific date onto the screen.
   * @param value the total value of the portfolio.
   * @param date the date chosen to find the value.
   */
  public void setValue(double value, Date date) {
    if (currentDialog != null && currentDialog.isVisible()) {
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout());
      panel.add(new JLabel("Value at " +
              new SimpleDateFormat("yyyy/MM/dd").format(date) + ": " + value));
      JButton cancelButton = new JButton("Back");
      cancelButton.addActionListener(this);
      cancelButton.addActionListener(e -> currentDialog.dispose());
      panel.add(cancelButton);
      currentDialog.getContentPane().removeAll();
      currentDialog.getContentPane().add(panel, BorderLayout.CENTER);

      currentDialog.revalidate();
      currentDialog.repaint();
    }
  }

  /**
   * Displays the composition of the portfolio on specific date onto the screen.
   * @param comp the line that contains the stock with the current number of shares.
   * @param date the date chosen to find the composition.
   */
  @Override
  public void setComposition(ArrayList<String> comp, Date date) {
    if (currentDialog != null && currentDialog.isVisible()) {
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout());
      panel.add(new JLabel("Composition at " +
              new SimpleDateFormat("yyyy/MM/dd").format(date) + ": " ));
      for (int i = 0; i < comp.size(); i++) {
        JLabel label = new JLabel(comp.get(i));
        panel.add(label);
      }
      JButton cancelButton = new JButton("Back");
      cancelButton.addActionListener(this);
      cancelButton.addActionListener(e -> currentDialog.dispose());
      panel.add(cancelButton);
      currentDialog.getContentPane().removeAll();
      currentDialog.getContentPane().add(panel, BorderLayout.CENTER);

      currentDialog.revalidate();
      currentDialog.repaint();
    }
  }

  /**
   * Displays the header of our program to welcome our user and instructions to
   * show how to use our program.
   */
  public void displayHeader() {
    JLabel nameofProgram = new JLabel("Welcome to Our Stock Market Program");
    JLabel instructions = new JLabel("Here are the current portfolios in our system");
    JLabel options = new JLabel("Click on a portfolio to see options");

    nameofProgram.setFont(new Font("Palatino", Font.BOLD, 40));
    nameofProgram.setForeground(new Color(102, 0 ,153));

    instructions.setFont(new Font("Palatino", Font.BOLD, 20));
    instructions.setForeground(new Color(0,0,153));

    options.setFont(new Font("Palatino", Font.BOLD, 20));
    options.setForeground(Color.DARK_GRAY);

    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
    headerPanel.add(nameofProgram);
    headerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    headerPanel.add(instructions);
    headerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    headerPanel.add(options);
    headerPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    nameofProgram.setAlignmentX(Component.CENTER_ALIGNMENT);
    instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
    options.setAlignmentX(Component.CENTER_ALIGNMENT);

    headerPanel.setBackground(backgroundColor);
    add(headerPanel, BorderLayout.NORTH);
  }

  // A helper method that adds a quit button for the user to exit out
  private JButton createQuitButton() {
    JButton mainQuit = new JButton("Quit");
    mainQuit.setPreferredSize(buttonSize);
    mainQuit.setFont(buttonFont);
    mainQuit.setForeground(new Color(255, 102, 102));
    return mainQuit;
  }

  /**
   * Displays the options of  for the portfolio such as selling, buying shares,
   * getting the composition and, value of the portfolio onto buttons.
   */
  public void displayButtonOptions() {
    this.createPortfolio.setFont(buttonFont);
    this.createPortfolio.setPreferredSize(buttonSize);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.add(this.createPortfolio);

    JButton quitButton = createQuitButton();
    quitButton.addActionListener(e -> System.exit(0));
    buttonPanel.add(quitButton);

    buttonPanel.setBackground(backgroundColor);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Displays the portfolios in our system onto the interface.
   */
  public void displayPort() {
    getPortfolioButtons();
    newPortfolioPanel.setBackground(backgroundColor);
    newPortfolioPanel.setPreferredSize(new Dimension(1000, 1000));
    add(newPortfolioPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  //Displays the newly create portfolio in our system onto the interface.
  private void displayCreatePortfolio() {
    JDialog dialog = new JDialog(this, "Create New Portfolio", true);
    dialog.setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    JLabel nameLabel = new JLabel("Portfolio Name:");
    JTextField nameField = new JTextField(20);
    nameField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          String name = nameField.getText();
          if (!name.isEmpty()) {
            getNewPortfolio(name);
            addOneButton(name);
          }
          dialog.dispose();
        }
      }
    });
    panel.add(nameLabel);
    panel.add(nameField);
    JButton quitButton = createQuitButton();
    quitButton.addActionListener(e -> dialog.dispose());
    panel.add(quitButton);
    dialog.add(panel);
    dialog.pack();
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  /**
   * Displays the options of  for the portfolio such as selling, buying shares,
   * getting the composition and, value of the portfolio.
   */
  public void displayPortOptions() {
    if (!portfolioOptions.isVisible()) {
      portfolioOptions = new JDialog(this, "Portfolio Options for " +
              this.currentPortfolio, true);
      portfolioOptions.setSize(400, 300);
      portfolioOptions.setLayout(new BorderLayout());
    }
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    for (int i = 0; i < finalPortfolioButtons.size(); i++) {
      buttonPanel.add(finalPortfolioButtons.get(i));
      finalPortfolioButtons.get(i).addActionListener(this);
    }
    JButton quitButton = createQuitButton();
    quitButton.addActionListener(e -> portfolioOptions.dispose());
    quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    buttonPanel.add(quitButton, BorderLayout.CENTER);
    this.portfolioOptions.add(buttonPanel, BorderLayout.CENTER);
    this.portfolioOptions.setLocationRelativeTo(this);
    this.portfolioOptions.setVisible(true);
  }

  // Creates and displays the input panels for selling and buying a stock. The input
  // panels consists of the ticker symbol of the stock and the number of shares they would
  // like to buy or sell.
  private JPanel stockInputPanel() {
    JPanel detailsPanel = new JPanel();
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
    JLabel portNameLabel = new JLabel("Portfolio: " + this.currentPortfolio);
    portNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    portNameLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
    detailsPanel.add(portNameLabel);

    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    JLabel nameLabel = new JLabel("Stock Name:");
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    nameLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
    detailsPanel.add(nameLabel);
    this.tickerSymbolField.setMaximumSize(new Dimension(160, 40));
    this.tickerSymbolField.setMinimumSize(new Dimension(160, 40));
    this.tickerSymbolField.setPreferredSize(new Dimension(160, 40));
    this.tickerSymbolField.setAlignmentX(Component.CENTER_ALIGNMENT);

    detailsPanel.add(this.tickerSymbolField);

    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    JLabel quantLabel = new JLabel("Stock quantity:");
    quantLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    quantLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
    detailsPanel.add(quantLabel);
    this.sharesField.setMaximumSize(new Dimension(160, 40));
    this.sharesField.setMinimumSize(new Dimension(160, 40));
    this.sharesField.setPreferredSize(new Dimension(160, 40));
    this.sharesField.setAlignmentX(Component.CENTER_ALIGNMENT);
    detailsPanel.add(this.sharesField);

    JLabel dateOfTransactionLabel = new JLabel("Date of transaction: ");
    dateOfTransactionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    dateOfTransactionLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    detailsPanel.add(dateOfTransactionLabel);
    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    detailsPanel.add(this.datePicker, BorderLayout.CENTER);

    detailsPanel.revalidate();
    detailsPanel.repaint();
    return detailsPanel;
  }

  // A helper methods that displays the panel for the date for the year, month, day.
  private JPanel dateInputPanel() {
    JPanel detailsPanel = new JPanel();
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    JLabel selectDateLabel = new JLabel("Select date: ");
    selectDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    selectDateLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
    detailsPanel.add( selectDateLabel);
    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));

    detailsPanel.add(this.datePicker, BorderLayout.CENTER);

    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    return detailsPanel;
  }

  // Displays the button for buying a stock and the dialog which will have the button
  // on it.
  private void displayBuyStocksOption() {
    JDialog dialog = new JDialog(this, "Buying A Stock", true);
    dialog.setSize(400, 350);
    dialog.setLayout(new BorderLayout());

    dialog.add(stockInputPanel(), BorderLayout.CENTER);
    JButton buyButton = new JButton("Buy");
    buyButton.setFont(buttonFont);
    buyButton.setActionCommand("enterInformationBuy");
    buyButton.addActionListener(this);
    buyButton.addActionListener(e -> {
      dialog.dispose();
    });
    JButton cancelButton = createQuitButton();
    cancelButton.addActionListener(e -> dialog.dispose());
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.add(buyButton);
    buttonPanel.add(cancelButton);
    dialog.add(buttonPanel, BorderLayout.SOUTH);
    this.currentDialog = dialog;
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  // Displays the button for selling a stock and the dialog which will have the button
  // on it.
  private void displaySellStocksOption() {
    JDialog dialog = new JDialog(this, "Selling A Stock", true);
    dialog.setSize(400, 350);
    dialog.setLayout(new BorderLayout());

    dialog.add(stockInputPanel(), BorderLayout.CENTER);
    JButton sellButton = new JButton("Sell");
    sellButton.setFont(buttonFont);
    sellButton.setActionCommand("enterInformationSell");
    sellButton.addActionListener(this);
    sellButton.addActionListener(e -> {
      dialog.dispose();
    });
    JButton cancelButton = createQuitButton();
    cancelButton.addActionListener(e -> dialog.dispose());
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.add(sellButton);
    buttonPanel.add(cancelButton);
    dialog.add(buttonPanel, BorderLayout.SOUTH);
    this.currentDialog = dialog;
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  // Displays the button for value of the portfolio and the dialog which will have the button
  // on it.
  private void displayPortfolioValue() {
    JDialog dialog = new JDialog(this, "Value of Portfolio: " + this.currentPortfolio,
            true);
    dialog.setName("value dialog");
    dialog.setSize(400, 200);
    dialog.setLayout(new BorderLayout());

    dialog.add(dateInputPanel(), BorderLayout.CENTER);
    JButton valueButton = new JButton("Get Value");
    valueButton.setFont(buttonFont);
    valueButton.setActionCommand("enterValueDate");
    valueButton.addActionListener(this);

    JButton cancelButton = createQuitButton();
    cancelButton.addActionListener(e -> dialog.dispose());
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.add(valueButton);
    buttonPanel.add(cancelButton);
    dialog.add(buttonPanel, BorderLayout.SOUTH);
    this.currentDialog = dialog;
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  // Displays the button for composition of the portfolio and the dialog which will have the button
  // on it.
  private void displayPortfolioComposition() {
    JDialog dialog = new JDialog(this, "Composition of Portfolio: " + this.currentPortfolio,
            true);
    dialog.setSize(400, 200);
    dialog.setLayout(new BorderLayout());

    dialog.add(dateInputPanel(), BorderLayout.CENTER);
    JButton compButton = new JButton("Get Composition");
    compButton.setFont(buttonFont);
    compButton.setActionCommand("enterCompDate");
    compButton.addActionListener(this);
    JButton cancelButton = createQuitButton();
    cancelButton.addActionListener(e -> dialog.dispose());
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.add(compButton);
    buttonPanel.add(cancelButton);
    dialog.add(buttonPanel, BorderLayout.SOUTH);
    this.currentDialog = dialog;
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  /**
   * Adds a view listener to our list of listener to handle the different action commands.
   * @param listener the current listener that will be added to the list.
   */
  @Override
  public void addViewListener(IViewListener listener) {
    this.myListeners.add(Objects.requireNonNull(listener));
  }

  // A private that will create a button will the given string.
  private void addOneButton(String name) {
    JButton newButton = new JButton(name);
    Dimension buttonSize = new Dimension(160, 100);
    newButton.setPreferredSize(buttonSize);
    newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    newButton.setFont(buttonFont);
    newButton.addActionListener(this);
    newButton.setActionCommand("portfolio");

    newButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        newButton.setOpaque(true);
        newButton.setBackground(new Color(102, 0 ,153));
        newButton.setForeground(new Color(102, 0 ,153));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        newButton.setBackground(UIManager.getColor("Button.background"));
        newButton.setForeground(UIManager.getColor("Button.foreground"));
      }
    });
    setFocusable(true);
    requestFocus();
    newButton.setName(name);
    newButton.setBackground(backgroundColor);
    portfolioButtons.add(newButton);
    newPortfolioPanel.add(newButton);
    newPortfolioPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    newPortfolioPanel.revalidate();
    newPortfolioPanel.repaint();
    repaint();
  }


}
