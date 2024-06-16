package view;

public interface IViewGUI extends IView{
  String getData();
  void setData(String data);
  void addViewListener(IViewListener viewListener);
  void requestFocus();
  void setVisible(boolean value);
}
