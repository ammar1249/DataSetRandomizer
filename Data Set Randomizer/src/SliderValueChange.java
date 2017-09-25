/** SliderValueChange Class
*    This class retrieves the noise percent the user selects through the slider from the view and gives it to the model 
*    Last Modified: 6/18/2016
*   @author Ammar Shaikh
*/
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class SliderValueChange implements ChangeListener
{
  private DSRModel dsrModel; //Creates an instance of the model
  private JSlider noisePercent; //The noise percent slider
  private JLabel currentValueLabel;//The label which continously displays the value of the noise percent
  
  /**
   * Constructor for the controller which retrives noise percent value
   * @param newModel is the DSRModel
   * @param slider is the slider which determines the noise percent
   * @param label is the label which displays the noise percent
   */
  public SliderValueChange (DSRModel model, JSlider slider, JLabel label)
  {
    this.dsrModel = model;
    this.noisePercent = slider;
    this.currentValueLabel = label;
  }
 
  /**
   * stateChanged method which continously retrives the value as the slider is changed
   * @param e is the event where the user changes the value on the JSlider
   */
  public void stateChanged(ChangeEvent e)
  {
    int currentValue = noisePercent.getValue();
    this.dsrModel.setNoisePercent(currentValue);
    this.currentValueLabel.setText("Current Value: " + currentValue);
  }
}
  