/** DSRParameterController Class
*    This class retrieves the binWidth and number of data points from the view and gives it to the model
*    Last Modified: 6/19/2016
*   @author Michael C.
*/
import javax.swing.*;
import java.awt.event.*;

public class DSRParameterController implements ActionListener
{
  private DSRModel model; //Creates an instance of the DSRModel 
  private JTextField textField; //Textfield for the binWidth or the number of data points field

  /**
   * Constructor for the controller which retrives the binWidth and number of data points
   * @param newModel is the DSRModel
   * @param aTextField1 is the textfield for the binWidth or Data point values
   */
  public DSRParameterController(DSRModel newModel, JTextField  aTextField)
  {
    this.model = newModel;
    this.textField = aTextField;
  }
  
  /**
   * actionPerformed method which retrieves values from the view for the binWidth or Data point values
   * @param e is the event where a value is entered into the textbox(es)
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getActionCommand().equals("binWidthField"))
    {
      try
      {
        double binWidth = Double.parseDouble(this.textField.getText());
        this.model.setBinWidth(binWidth);
      } 
      catch (NumberFormatException ex)
      {
        this.textField.setText("");
      }
    }
    else if(e.getActionCommand().equals("dataPointField"))
    {
      try
      {
        int dataPoint = Integer.parseInt(this.textField.getText());
        this.model.setNumDataPoints(dataPoint);
      } 
      catch (NumberFormatException ex)
      {
        this.textField.setText("");
      }   
    }
  } 
}