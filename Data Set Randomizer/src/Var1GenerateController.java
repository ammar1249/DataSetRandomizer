/** Var1GenerateController Class
*    This class retrieves the values from the textfields in variable one tab from the view and gives it to the model 
*    Last Modified: 6/18/2016
*   @author Michael C
*/
import javax.swing.*;
import java.awt.event.*;

public class Var1GenerateController implements ActionListener
{
  private DSRModel model;  //Creates an instance of the model
  private JTextField title; //Title for the program
  private JTextField heading1; //First heading for the program
  private JTextField heading2; //Second heading for the program
  private JTextField mean1; //Mean value one text box
  private JTextField mean2; //Mean value two text box
  private JTextField std1; //Standard Deviation value one text box
  private JTextField std2; //Standard Deviation value two text box
  private JTextField min; //Minimum value text box
  private JTextField max; //Maximum value text box
  private JTextField binWidth; //binWidth value text box
  private JTextField numberOfPoints; //number of data points text box
  
  /**
   * Constructor for the controller which retrives the variable one textbox values
   * @param newModel is the DSRModel
   * @param newTitle is the title textbox
   * @param newHeading1 is the heading1 textbox
   * @param newHeading2 is the heading2 textbox
   * @param newMean1 is the mean1 textbox
   * @param newMean2 is the mean2 textbox
   * @param newSTD1 is the first standard deviation textbox
   * @param newSTD2 is the second standard deviation textbox
   * @param newMin is the minimum value textbox
   * @param newMax is the maximum value textbox
   * @param newBinWidth is the binWidth value textbox
   * @param newPointsNum is the number of points textbox
   */
  public Var1GenerateController(DSRModel newModel,JTextField newTitle,JTextField newHeading1, JTextField newHeading2,JTextField newMean1,JTextField newMean2,JTextField newSTD1,JTextField newSTD2,JTextField newMin,JTextField newMax,JTextField newBinWidth,JTextField newPointsNum)
  {
    this.model = newModel;
    this.title = newTitle;
    this.heading1 = newHeading1;
    this.heading2 = newHeading2;
    this.mean1 = newMean1;
    this.mean2 = newMean2;
    this.std1 = newSTD1;
    this.std2 = newSTD2;
    this.min = newMin;
    this.max = newMax;
    this.binWidth = newBinWidth;
    this.numberOfPoints = newPointsNum;
  }
  
  /**
   * actionPerformed method which retrieves all values from the variable one textfields
   * @param e is the event where a value is entered into the textfield(s)
   */
  public void actionPerformed(ActionEvent e)
  {
    try
    {
      this.model.setVarLabels(this.title.getText(),this.heading1.getText(),this.heading2.getText());
      this.model.setRange( Double.parseDouble(this.min.getText()), Double.parseDouble(this.max.getText()));
      this.model.setBinWidth(Double.parseDouble(this.binWidth.getText()));
      this.model.setNumDataPoints(Integer.parseInt(this.numberOfPoints.getText()));
      this.model.setMeanStDev(Double.parseDouble(this.mean1.getText()),Double.parseDouble(this.std1.getText()),Double.parseDouble(this.mean2.getText()),Double.parseDouble(this.std2.getText()));                        
      
    }
    catch(NumberFormatException ex)
    {
      this.title.setText("Error");
      this.heading1.setText("Error");
      this.heading2.setText("Error");
      this.std1.setText("0");
      this.std2.setText("0");
      this.mean1.setText("0");
      this.mean2.setText("0");
      this.max.setText("0");
      this.min.setText("0");
      this.binWidth.setText("0");
      this.numberOfPoints.setText("0");
      
    }
    this.model.generateVar1();
  }
}