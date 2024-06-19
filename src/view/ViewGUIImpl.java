package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * This class allows for
 */
public class ViewGUIImpl extends JFrame implements IViewGUI, ActionListener {
  private final JButton createPortfolio = new JButton("Create Portfolio");
  private final JPanel mainPanel = new JPanel();
  private final List<IViewListener> myListeners;
  private final List<JButton> portfolioButtons;
  private final List<JLabel> stockButtons;
  private JDialog portfolioOptions;
  private JDialog currentDialog;

  private JPanel newPortfolioPanel;
  private static final Font buttonFont =
          new Font("Time New Roman", Font.BOLD, 14);

  private static final Dimension buttonSize = new Dimension(150, 50);

  private static final List<JButton> finalPortfolioButtons;

  private final JXDatePicker datePicker;
  private final JTextField tickerSymbolField;
  private final JTextField sharesField;


  private String currentPortfolio;

  static {
    finalPortfolioButtons = new ArrayList<>();
    JButton compButton = new JButton("Composition");
    JButton valueButton = new JButton("Value Portfolio");
    JButton sellButton = new JButton("Sell Stock");
    JButton buyButton = new JButton("Buy Stock");

    buyButton.setActionCommand("Buy Stock");
    sellButton.setActionCommand("Sell Stock");
    valueButton.setActionCommand("Value");
    compButton.setActionCommand("Comp");

    finalPortfolioButtons.addAll(Arrays.asList(compButton, valueButton, sellButton, buyButton));

    for(JButton button : finalPortfolioButtons) {
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

  }

  public ViewGUIImpl() {
    setSize(new Dimension(800, 400));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    mainPanel.setLayout(new BorderLayout());
    mainPanel.setPreferredSize(new Dimension(800, 400));
    this.newPortfolioPanel = new JPanel();
    this.newPortfolioPanel.setLayout(new BoxLayout(this.newPortfolioPanel, BoxLayout.Y_AXIS));

    this.mainPanel.setLayout(new BorderLayout());
    this.mainPanel.add(newPortfolioPanel, BorderLayout.CENTER);

    this.myListeners = new ArrayList<>();
    this.portfolioButtons = new ArrayList<>();

    this.stockButtons = new ArrayList<>();

    this.portfolioOptions = new JDialog();

    this.createPortfolio.addActionListener(this);
    this.createPortfolio.setActionCommand("create Portfolio");

    this.datePicker = new JXDatePicker();
    this.tickerSymbolField = new JTextField();
    this.sharesField = new JTextField();
    this.tickerSymbolField.setPreferredSize(new Dimension(50, 10));
    this.sharesField.setPreferredSize(new Dimension(50, 10));
    this.currentDialog = new JDialog();

    add(mainPanel);
    pack();
    setVisible(true);
  }

  @Override
  public void writeMessage(String message) {
    throw new UnsupportedOperationException("Not supported in GUI implementation.");
  }

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
          if(getCurrentShares() < 0) {
            displayCurrentUserMessage("Not able to buy stock, share must be positive whole number");
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
        System.out.println("pressed value button");
        fireValue(this.getCurrentDate(), this.currentPortfolio);
        break;
      case "enterCompDate":
        System.out.println("pressed value button");
        try {
          fireComp(this.getCurrentDate(), this.currentPortfolio);
        } catch (FileNotFoundException ex) {
          // failed comp
        }
        break;

//        case "option "
//      default:
//        throw new IllegalStateException("Unknown action command");
//    }
//    //Which button got cliked!
    }
    newPortfolioPanel.revalidate();
    newPortfolioPanel.repaint();
    mainPanel.revalidate();
    mainPanel.repaint();
  }

  private void displayCurrentUserMessage(String message) {
    JPanel pan = new JPanel();
    pan.setLayout(new FlowLayout());
    pan.add(new JLabel("Most recent transaction message: "));
    pan.add(new JLabel(message));
    portfolioOptions.add(pan, BorderLayout.SOUTH);
    portfolioOptions.revalidate();
    portfolioOptions.repaint();
  }

  private String getCurrentTicker() {
    return this.tickerSymbolField.getText();
  }

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

  private Date getCurrentDate() {
    return this.datePicker.getDate();
  }

  void fireBuyStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException {
    for (IViewListener myListener : myListeners) {
      myListener.handleBuyStock(date, ticker, shares, portfolio);
    }
  }

  void fireSellStock(Date date, String ticker, int shares, String portfolio)
          throws FileNotFoundException {
    for (IViewListener myListener : myListeners) {
      myListener.handleSellStock(date, ticker, shares, portfolio);
    }
  }

  void fireValue(Date date, String portfolio) {
    for(IViewListener myListener : myListeners) {
      myListener.handleGetValue(date, portfolio);
    }
  }

  void fireComp(Date date, String portfolio) throws FileNotFoundException {
    for(IViewListener myListener : myListeners) {
      myListener.handleGetComposition(date, portfolio);
    }
  }

  void getPortfolioButtions() {
    for (IViewListener myListener : myListeners) {
      myListener.getPortfolioButtons();
    }
  }

  private void getStocks() {
    for (IViewListener myListener : myListeners) {
      myListener.getStocks();
    }
  }


  void getNewPortfolio(String name) {
    for (IViewListener myListener : myListeners) {
      myListener.handleCreatePortfolios(name);
    }
  }

  public void setPortfolioButtons(ArrayList<String> names) {
    for (int i = 0; i < names.size(); i++) {
      if (names.contains(names.get(i))) {
        addOneButton(names.get(i));
      }
    }
  }

  public void setValue(double value, Date date) {
//    System.out.println("got to setvalue with " + value + " " + date.toString());
//    System.out.println(currentDialog.getName());
//    JPanel panel = new JPanel();
//    panel.setLayout(new FlowLayout());
//    panel.add(new JLabel("Value at " + date.toString() +": " + value ));
//    currentDialog.add(panel, BorderLayout.CENTER);
//    currentDialog.revalidate();
//    currentDialog.repaint();
    System.out.println("got to setvalue with " + value + " " + date.toString());
    System.out.println(currentDialog.getName());

    // Ensure currentDialog is not null and is visible
    if (currentDialog != null && currentDialog.isVisible()) {
      // Create a new panel with the value
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout());
      panel.add(new JLabel("Value at " + new SimpleDateFormat("yyyy/MM/dd").format(date) + ": " + value));
      JButton cancelButton = new JButton("Back");
      cancelButton.addActionListener(this);
      cancelButton.addActionListener(e -> currentDialog.dispose());
      panel.add(cancelButton);
      // Clear the current dialog's content and add the new panel
      currentDialog.getContentPane().removeAll();
      currentDialog.getContentPane().add(panel, BorderLayout.CENTER);

      // Revalidate and repaint the dialog to ensure the new content is shown
      currentDialog.revalidate();
      currentDialog.repaint();
    }
  }

  @Override
  public void setComposition(ArrayList<String> comp, Date date) {
    if (currentDialog != null && currentDialog.isVisible()) {
      // Create a new panel with the value
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout());
      panel.add(new JLabel("Composition at " +
              new SimpleDateFormat("yyyy/MM/dd").format(date) + ": " ));
      for(int i = 0; i<comp.size(); i++) {
        JLabel label = new JLabel(comp.get(i));
        panel.add(label);
      }
      JButton cancelButton = new JButton("Back");
      cancelButton.addActionListener(this);
      cancelButton.addActionListener(e -> currentDialog.dispose());
      panel.add(cancelButton);
      // Clear the current dialog's content and add the new panel
      currentDialog.getContentPane().removeAll();
      currentDialog.getContentPane().add(panel, BorderLayout.CENTER);

      // Revalidate and repaint the dialog to ensure the new content is shown
      currentDialog.revalidate();
      currentDialog.repaint();
    }
  }

  public void setStocks(ArrayList<String> names) {
    for (int i = 0; i < names.size(); i++) {
      JLabel label = new JLabel(names.get(i));
      this.stockButtons.add(label);
      //this.stockButtons.get(i)    NOT SURE IF SHOULD BE AN ACTION LISTENER
    }
  }

  @Override
  public void displayPortfolios(String portfolioName) {
    for (IViewListener myListener : myListeners) {
      myListener.handleDisplayPortfolios();
    }
  }

  public void displayHeader() {
    JLabel nameofProgram = new JLabel("Welcome to Our Stock Market Program");
    JLabel instructions = new JLabel("Here are the current portfolios in our system");
    JLabel options = new JLabel("Click on a portfolio to see options");

    nameofProgram.setFont(new Font("Times New Roman", Font.BOLD, 40));
    nameofProgram.setForeground(Color.BLUE);
    instructions.setFont(new Font("Times New Roman", Font.BOLD, 20));
    instructions.setForeground(Color.BLUE);
    options.setFont(new Font("Times New Roman", Font.BOLD, 20));
    options.setForeground(Color.BLUE);
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
    headerPanel.add(nameofProgram);
    headerPanel.add(instructions);
    headerPanel.add(options);
    nameofProgram.setAlignmentX(Component.CENTER_ALIGNMENT);
    instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
    options.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainPanel.add(headerPanel, BorderLayout.NORTH);
  }

  private JButton createQuitButton() {
    JButton mainQuit = new JButton("Quit");
    mainQuit.setPreferredSize(buttonSize);
    return mainQuit;
  }
  public void displayButtonOptions() {
    this.createPortfolio.setFont(buttonFont);
    this.createPortfolio.setPreferredSize(buttonSize);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.add(this.createPortfolio);
    JButton quitButton = createQuitButton();
    quitButton.addActionListener(e -> System.exit(0));
    buttonPanel.add(quitButton);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
  }

  public void displayPort() {
//    JScrollPane scroller = new JScrollPane( newPortfolioPanel );
    newPortfolioPanel.setLayout(new BoxLayout(newPortfolioPanel, BoxLayout.Y_AXIS));
    getPortfolioButtions();
    mainPanel.revalidate();
    mainPanel.repaint();
    mainPanel.add(newPortfolioPanel, BorderLayout.CENTER);
  }

  public void displayStocks() {
    JPanel stockPanel = new JPanel();
    stockPanel.setLayout(new BoxLayout(stockPanel, BoxLayout.Y_AXIS));
    Dimension buttonSize = new Dimension(160, 100);

    getStocks();
    for (JLabel stock : stockButtons) {
      stock.setFont(buttonFont);
      stock.setSize(buttonSize);
      stock.setAlignmentX(Component.CENTER_ALIGNMENT);
      stockPanel.add(stock);
      stockPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    }
    mainPanel.revalidate();
    mainPanel.repaint();
    mainPanel.add(stockPanel, BorderLayout.WEST);
  }


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
          System.out.println("Portfolio name in keyPressed method: " + name);
          if (!name.isEmpty()) {
            addOneButton(name);
            getNewPortfolio(name);
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


  public void displayPortOptions() {
    if (!portfolioOptions.isVisible()) {
      portfolioOptions = new JDialog(this, "Portfolio Options for " +
              this.currentPortfolio, true);
      portfolioOptions.setSize(400, 300);
      portfolioOptions.setLayout(new BorderLayout());

      newPortfolioPanel = new JPanel();

      newPortfolioPanel.setLayout(new BoxLayout(newPortfolioPanel, BoxLayout.Y_AXIS));
      portfolioOptions.add(newPortfolioPanel, BorderLayout.CENTER);
      portfolioOptions.setLocationRelativeTo(this);
    }

    newPortfolioPanel.removeAll();
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
    //this.portfolioOptions.add(new JLabel("Most recent message"), BorderLayout.SOUTH);
    this.portfolioOptions.add(buttonPanel, BorderLayout.CENTER);
    this.portfolioOptions.setLocationRelativeTo(this);
    this.portfolioOptions.setVisible(true);
  }

  private JXDatePicker createDatePicker() {
    // Create a JXDatePicker instance
    JXDatePicker datePicker = new JXDatePicker();
    datePicker.setFormats(new SimpleDateFormat("yyyy/MM/dd"));

    // Customize the date picker (optional)
    datePicker.getEditor().setFont(new Font("Arial", Font.PLAIN, 16));
    datePicker.setToolTipText("Select a date");
    return datePicker;
  }


  private JPanel stockInputPanel() {
    JPanel detailsPanel = new JPanel();
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
    detailsPanel.add( new JLabel("Portfolio: " + this.currentPortfolio));

    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    detailsPanel.add(new JLabel("Stock Name:"));
    detailsPanel.add(this.tickerSymbolField);

    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    detailsPanel.add(new JLabel("Stock Quantity:"));
    detailsPanel.add(this.sharesField);

    detailsPanel.add(new JLabel("Date of transaction:"));
    detailsPanel.add(this.datePicker, BorderLayout.CENTER);

    return detailsPanel;
  }

  private JPanel dateInputPanel() {
    JPanel detailsPanel = new JPanel();
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
    detailsPanel.add( new JLabel("Select date: "));

    detailsPanel.add(this.datePicker, BorderLayout.CENTER);

    detailsPanel.add(Box.createRigidArea(new Dimension(20, 10)));
    return detailsPanel;
  }

  private void displayBuyStocksOption() {
    JDialog dialog = new JDialog(this, "Buying A Stock", true);
    dialog.setSize(400, 300);
    dialog.setLayout(new BorderLayout());

    dialog.add(stockInputPanel(), BorderLayout.CENTER);
    JButton buyButton = new JButton("Buy");
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


  private void displaySellStocksOption() {
    JDialog dialog = new JDialog(this, "Selling A Stock", true);
    dialog.setSize(400, 300);
    dialog.setLayout(new BorderLayout());

    dialog.add(stockInputPanel(), BorderLayout.CENTER);
    JButton sellButton = new JButton("Sell");
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

  private void displayPortfolioValue() {
    JDialog dialog = new JDialog(this, "Value of Portfolio: " + this.currentPortfolio,
            true);
    dialog.setName("value dialog");
    dialog.setSize(400, 200);
    dialog.setLayout(new BorderLayout());

    dialog.add(dateInputPanel(), BorderLayout.CENTER);
    JButton valueButton = new JButton("Get Value");
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

  private void displayPortfolioComposition() {
    JDialog dialog = new JDialog(this, "Composition of Portfolio: " + this.currentPortfolio,
            true);
    dialog.setSize(400, 200);
    dialog.setLayout(new BorderLayout());

    dialog.add(dateInputPanel(), BorderLayout.CENTER);
    JButton compButton = new JButton("Get Composition");
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


  @Override
  public void addViewListener(IViewListener listener) {
    this.myListeners.add(Objects.requireNonNull(listener));
  }

  private void addOneButton(String name) {
//    JButton newButton = new JButton(name);
//    newButton.setName(name);
//    portfolioButtons.add(newButton);
//    Dimension buttonSize = new Dimension(160, 100);
//    newButton.addActionListener(this);
//    newButton.setActionCommand("portfolio");
//    newPortfolioPanel.add(newButton);
//    newButton.setVisible(true);
//    newButton.setFont(buttonFont);
//    newButton.setSize(buttonSize);
//    newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//    newPortfolioPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//    newPortfolioPanel.repaint();
//    newPortfolioPanel.revalidate();
//    mainPanel.revalidate();
//    mainPanel.repaint();
//    mainPanel.add(newPortfolioPanel, BorderLayout.CENTER);
    JButton newButton = new JButton(name);
    newButton.setName(name);
    portfolioButtons.add(newButton);

    Dimension buttonSize = new Dimension(160, 100);
    newButton.setPreferredSize(buttonSize);
    newButton.setMaximumSize(buttonSize);
    newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    newButton.setFont(buttonFont);
    newButton.addActionListener(this);
    newButton.setActionCommand("portfolio");

    newPortfolioPanel.add(newButton);
    newPortfolioPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    newPortfolioPanel.revalidate();
    newPortfolioPanel.repaint();
    System.out.println(newPortfolioPanel.getActionMap().toString());
    //newPortfolioPanel.setVisible(true);
    mainPanel.revalidate();
    mainPanel.repaint();
  }



}
