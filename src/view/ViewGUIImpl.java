package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

public class ViewGUIImpl extends JFrame implements IViewGUI, ActionListener, KeyListener, MouseListener {
  private final JButton getData;
  private final JButton sendData;
  private final JLabel  enterTextLabel;
  private final JTextArea enterTextArea;
  private final List<IViewListener> myListeners;
  
  public ViewGUIImpl(){
    super();
    setSize(new Dimension(800, 400));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

   this.myListeners = new ArrayList<>();

    setLayout(new BorderLayout() );
    this.enterTextArea = new JTextArea("Enter your text here.");
    this.enterTextLabel = new JLabel("Enter text: ");
    this.sendData = new JButton("Send Data");
    this.getData = new JButton("Get Data");


    this.getData.setActionCommand("getData");
    this.sendData.setActionCommand("sendData");

    this.getData.addActionListener( this );//instead of this use MyActionListner or you could use a lambda.
    this.sendData.addActionListener(this );
    this.addKeyListener(this);
    this.addKeyListener(this);
    this.enterTextLabel.addMouseListener(this);

    add(this.enterTextLabel, BorderLayout.NORTH);
    add(this.getData, BorderLayout.WEST);
    add(this.sendData, BorderLayout.EAST);
    add(this.enterTextArea, BorderLayout.CENTER);

    setFocusable(true);
    requestFocus();
    pack();

  }
  @Override
  public void writeMessage(String message) {

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch ( e.getActionCommand() ){
      case "getData":
        fireGetDataEvent();
        break;
      case "sendData":
        fireSetDataEvent();
        break;
      default:
        throw new IllegalStateException("Unknown action command");
    }
    //Which button got cliked!
  }


  private void fireGetDataEvent(){
    for ( IViewListener myListener : myListeners ){
      myListener.handleGetData();
    }
  }

  private void fireSetDataEvent(){
    for ( IViewListener myListener : myListeners ){
      myListener.handleSetData();
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if ( e.getKeyChar() == 's'){
      fireSetDataEvent();
    }
    else if ( e.getKeyChar() == 'g'){
      fireGetDataEvent();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public String getData(){
    return this.enterTextArea.getText();
  }


  @Override
  public void setData(String data){
    this.enterTextLabel.setText( Objects.requireNonNull(data));
  }


  @Override
  public void addViewListener(IViewListener listener){
    this.myListeners.add(Objects.requireNonNull(listener));
  }

}
