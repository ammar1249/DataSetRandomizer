/** DSRLabelController Class
*    This class retrieves the headings and title from the view and gives it to the model
*    Last Modified: 6/19/2016
*   @author Michael C.
*/
import javax.swing.*;
import java.awt.event.*;

public class DSRLabelController implements ActionListener
{
  private DSRModel model;  //Creates an instance of the DSRModel
  private JTextField textField1; //Title for the program
  private JTextField textField2; //First heading for the program
  private JTextField textField3; //Second heading for the program
  
   /**
   * Constructor for the controller which retrives the title and heading values
   * @param newModel is the DSRModel
   * @param aTextField1 is the textfield for the title value
   * @param aTextField2 is the textfield for the heading1 value
   * @param aTextField3 is the textfield for the heading2 value
   */
  public DSRLabelController(DSRModel newModel, JTextField  aTextField1,JTextField aTextField2,JTextField aTextField3)
  {
    this.model = newModel;
    this.textField1 = aTextField1;
    this.textField2 = aTextField2;
    this.textField3 = aTextField3;
  }
  
  /**
   * actionPerformed method which retrieves values from the view for the title and heading
   * @param e is the event where a value is entered into the textbox(es)
   */
  public void actionPerformed(ActionEvent e)
  {
    try
    {
      this.model.setVarLabels(this.textField1.getText(),this.textField2.getText(),this.textField2.getText());
    } 
    catch (NumberFormatException ex)
    {
      this.textField1.setText("");
      this.textField2.setText("");
      this.textField3.setText("");
    }
  } 
}