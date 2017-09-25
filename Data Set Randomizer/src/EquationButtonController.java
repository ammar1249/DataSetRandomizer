/** EquationButtonController Class
*    This class retrieves the equation selected by the user from the view and gives it to the model
*    Last Modified: 6/18/2016
*   @author Michael C.
*/
import javax.swing.*;
import java.awt.event.*;

public class EquationButtonController implements ActionListener
{
  private DSRModel model; //Creates an instance of the DSRModel 
  private JButton equationButton1; //Button for linear
  private JButton equationButton2;//Button for quadratic
  private JButton equationButton3;//Button for exponential
  
   /**
   * Constructor for the controller which equation type the user selected
   * @param newModel is the DSRModel
   * @param newButton1 is the button for the linear graph
   * @param newButton2 is the button for the quadratic graph
   * @param newButton3 is the button for the exponential graph
   */
  public EquationButtonController(DSRModel newModel, JButton newButton1,JButton newButton2,JButton newButton3)
  {
    this.model = newModel;
    this.equationButton1 = newButton1;
    this.equationButton2 = newButton2;
    this.equationButton3 = newButton3;
  }
  
  /**
   * actionPerformed method which retrieves values from the view for the linear,quadratic or exponential selection
   * @param e is the event where a button is clicked from the linear,quadratic or exponential buttons
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getActionCommand().equals("Linear"))
    {
      this.model.setEquationChosen(this.model.LINEAR); 
      this.equationButton2.setEnabled(true);
      this.equationButton1.setEnabled(false);
      this.equationButton3.setEnabled(true);   
    }
    else if(e.getActionCommand().equals("Quadratic"))
    {
      this.model.setEquationChosen(this.model.QUADRATIC);  
      this.equationButton2.setEnabled(false);
      this.equationButton1.setEnabled(true);
      this.equationButton3.setEnabled(true); 
    }
    else
    {
      this.model.setEquationChosen(this.model.EXPONENTIAL);  
      this.equationButton2.setEnabled(true);
      this.equationButton1.setEnabled(true);
      this.equationButton3.setEnabled(false); 
    }   
  }
}