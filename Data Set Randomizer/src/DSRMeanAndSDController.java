/** DSRLabelController Class
*    This class retrieves the mean and standard deviation values from the view and gives it to the model
*    Last Modified: 6/19/2016
*   @author Michael C.
*/
import javax.swing.*;
import java.awt.event.*;

public class DSRMeanAndSDController implements ActionListener
{
  private DSRModel model; //Creates an instance of the DSRModel 
  private JTextField textField1; //Mean value one
  private JTextField textField2; //Mean value two
  private JTextField textField3; //Standard Deviation value one
  private JTextField textField4; //Standard Deviation value two
  
   /**
   * Constructor for the controller which retrives the mean and standard dev values
   * @param newModel is the DSRModel
   * @param aTextField1 is the textfield for the mean 1 value
   * @param aTextField2 is the textfield for the mean 2 value
   * @param aTextField3 is the textfield for the standard dev 1 value
   * @param aTextField3 is the textfield for the standard dev 2 value
   */
  public DSRMeanAndSDController(DSRModel newModel, JTextField  aTextField1,JTextField aTextField2,JTextField aTextField3,JTextField aTextField4)
  {
    this.model = newModel; 
    this.textField1 = aTextField1;
    this.textField2 = aTextField2;
    this.textField3 = aTextField3;
    this.textField4 = aTextField4;
  }
  
  /**
   * actionPerformed method which retrieves values from the view for the mean and standard deviation values
   * @param e is the event where a value is entered into the textbox(es)
   */
  public void actionPerformed(ActionEvent e)
  {
    try
    {
      double mean1 = Double.parseDouble(this.textField1.getText());
      double mean2 = Double.parseDouble(this.textField2.getText());
      double std1 = Double.parseDouble(this.textField2.getText());
      double std2 = Double.parseDouble(this.textField2.getText());
      this.model.setMeanStDev(mean1,std1,mean2,std2);
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