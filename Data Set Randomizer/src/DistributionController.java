/** DistributionController Class
*    to find out what type of distribution needs to graphed
*    Last Modified: 6/19/2016
*   @author Michael C.
*/
import javax.swing.*;
import java.awt.event.*;

public class DistributionController implements ActionListener
{
  private DSRModel model;  //Instantiates an instance of the Data set Randomizer model
  private JButton generateButton1; //Linear button
  private JButton generateButton2; // Quadratic Button
  private JButton generateButton3; // Exponential button
  
  /**
   * Constructor for creating distribution controller
   * @param newModel is the instance of the model class
   * @param newButton1 is the uniform normal button
   * @param newButton2 is the random normal button
   * @param newButton3 is the bimodal button
   */
  public DistributionController(DSRModel newModel, JButton newButton1,JButton newButton2,JButton newButton3)
  {
    this.model = newModel;
    this.generateButton1 = newButton1;
    this.generateButton2 = newButton2;
    this.generateButton3 = newButton3;
  }
  
    /**
   * actionPerform method that reacts to the type of distribution selected
   * @param e is the event of the button(s) being clicked
   */
  public void actionPerformed(ActionEvent e)
  {
    //If a certain button is clicked, it is disabled and all other buttons remain enable.
    if(e.getActionCommand().equals("Uniform Normal"))
    {
      this.model.setDistributionChosen(this.model.RANDOM_UNIFORM); 
      this.generateButton2.setEnabled(true);
      this.generateButton1.setEnabled(false);
      this.generateButton3.setEnabled(true);   
    }
    else if(e.getActionCommand().equals("Random Normal"))
    {
      this.model.setDistributionChosen(this.model.RANDOM_NORMAL);  
      this.generateButton2.setEnabled(false);
      this.generateButton1.setEnabled(true);
      this.generateButton3.setEnabled(true); 
    }
    else  if(e.getActionCommand().equals("Bimodal"))
    {
      this.model.setDistributionChosen(this.model.RANDOM_BIMODAL);  
      this.generateButton2.setEnabled(true);
      this.generateButton1.setEnabled(true);
      this.generateButton3.setEnabled(false); 
    }   
  }
}