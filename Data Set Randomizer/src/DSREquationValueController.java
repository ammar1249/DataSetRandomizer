/** DSREquationValueController Class
*    Retrieves the values from the a,b,c,d textboxes and sends it to the model to make the graph
*    Last Modified: 6/19/2016
*   @author Michael C.
*/
import javax.swing.*;
import java.awt.event.*;

public class DSREquationValueController implements ActionListener
{
  private DSRModel model;  //Instantiates an instance of the Data set Randomizer model
  private JTextField textField1; //Textfield for the a value
  private JTextField textField2; //Textfield for the b value
  private JTextField textField3; //Textfield for the c value
  private JTextField textField4; //Textfield for the d value
  
  /**
   * Constructor
   * @param newModel is the DSRModel
   * @param aTextField1 is the textfield for the "a" value
   * @param aTextField2 is the textfield for the "b" value
   * @param aTextField3 is the textfield for the "c" value
   * @param aTextField4 is the textfield for the "d" value
   */
  public DSREquationValueController(DSRModel newModel, JTextField  aTextField1,JTextField aTextField2,JTextField aTextField3,JTextField aTextField4)
  {
    this.model = newModel;
    this.textField1 = aTextField1;
    this.textField2 = aTextField2;
    this.textField3 = aTextField3;
    this.textField4 = aTextField4;
  }
  
  /**
   * actionPerformed method which retrieves values from the a, b, c, and d textboxes
   * @param e is the event where a value is entered into the textbox(es)
   */
  public void actionPerformed(ActionEvent e)
  {
    //If the value is invalid the textbox is set to a space
    try
    {
      double aValue = Integer.parseInt(this.textField1.getText());
      double bValue = Integer.parseInt(this.textField2.getText());
      double cValue = Integer.parseInt(this.textField3.getText());
      double dValue = Integer.parseInt(this.textField4.getText());
      this.model.setEquation(aValue,bValue,cValue,dValue);
    } 
    catch (NumberFormatException ex)
    {
      this.textField1.setText("");
      this.textField2.setText("");
      this.textField3.setText("");
      this.textField4.setText("");
    }
  } 
}