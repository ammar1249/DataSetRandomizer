/** DSRSetRangeController Class
*    This class retrieves the min and max values from the view and gives it to the model
*    Last Modified: 6/19/2016
*   @author Michael C.
*/
import javax.swing.*;
import java.awt.event.*;

public class DSRSetRangeController implements ActionListener
{
  private DSRModel model;  //Creates an instance of the DSRModel 
  private JTextField textField1; //Minimum value
  private JTextField textField2; //Maximum value
  
    /**
   * Constructor for the controller which retrives the maximum and minimum range
   * @param newModel is the DSRModel
   * @param aTextField1 is the textfield for the min value
   * @param aTextField2 is the textfield for the max value
   */
  public DSRSetRangeController(DSRModel newModel, JTextField  aTextField1,JTextField aTextField2)
  {
    this.model = newModel;
    this.textField1 = aTextField1;
    this.textField2 = aTextField2;
  }
  
  /**
   * actionPerformed method which retrieves values from the view for the min and max values
   * @param e is the event where a value is entered into the textbox(es)
   */
  public void actionPerformed(ActionEvent e)
  {
    try
    {
      double min = Double.parseDouble(this.textField1.getText());
      double max = Double.parseDouble(this.textField2.getText());
      this.model.setRange(min,max);
    } 
    catch (NumberFormatException ex)
    {
      this.textField1.setText("");
      this.textField2.setText("");
      this.textField1.selectAll();
    }
  } 
}