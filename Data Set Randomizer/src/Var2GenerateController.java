/** Var2GenerateController Class
*    This class retrieves the values in the variable two tab from the view and gives it to the model 
*    Last Modified: 6/18/2016
*   @author Michael C
*/
import javax.swing.*;
import java.awt.event.*;

public class Var2GenerateController implements ActionListener
{
  private DSRModel model;  //Creates an instance of the model
  private JTextField title; //Title for the program
  private JTextField heading1; //First heading for the program
  private JTextField heading2; //Second heading for the program
  private JTextField aValue; // "a" value text box
  private JTextField bValue; // "b" value text box
  private JTextField cValue; // "c" value text box
  private JTextField dValue; // "d" value text box
  
   /**
   * Constructor for the controller which retrives the a,b,c, d values
   * @param newModel is the DSRModel
   * @param newTitle is the title textbox
   * @param newHeading1 is the heading1 textbox
   * @param newHeading2 is the heading2 textbox
   * @param newAValue is the textfield for the "a" value
   * @param newBValue is the textfield for the "b" value
   * @param newCValue is the textfield for the "c" value
   * @param newDValue is the textfield for the "d" value
   */
  public Var2GenerateController(DSRModel newModel,JTextField newTitle,JTextField newHeading1, JTextField newHeading2,JTextField newAValue,JTextField newBValue, JTextField newCValue,JTextField newDValue)
  {
    this.model = newModel;
    this.title = newTitle;
    this.heading1 = newHeading1;
    this.heading2 = newHeading2;
    this.aValue = newAValue;
    this.bValue = newBValue;
    this.cValue = newCValue;
    this.dValue = newDValue;
  }
  
   /**
   * actionPerformed method which retrieves all values from the variable two textfields
   * @param e is the event where a value is entered into the textfield(s)
   */
  public void actionPerformed(ActionEvent e)
  {
    try
    {
      this.model.setVarLabels(this.title.getText(),this.heading1.getText(),this.heading2.getText());
      this.model.setEquation(Double.parseDouble(this.aValue.getText()),Double.parseDouble(this.bValue.getText()),Double.parseDouble(this.cValue.getText()),Double.parseDouble(this.dValue.getText()));
      
    }
    catch(NumberFormatException ex)
    {
      this.aValue.setText("0");
      this.bValue.setText("0");
      this.cValue.setText("0");
      this.dValue.setText("0");
    }
    this.model.calculateVar2();
  }
}
