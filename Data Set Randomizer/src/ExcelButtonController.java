/** ExcelButtonController Class
*    This class retrieves which variable is exported to excel selected by the user from the view and gives it to the model 
*    Last Modified: 6/18/2016
*   @author Ammar Shaikh
*/
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class ExcelButtonController implements ActionListener
{
  private DSRModel model;  //Creates an instance of the DSRModel 
  private JButton excelButton; //Button used to export to excel
  
   /**
   * Constructor for the controller which retrives which export button the user has pressed
   * @param newModel is the DSRModel
   * @param newExcelButton is the button which exports data to excel
   */
  public ExcelButtonController (DSRModel newModel, JButton newExcelButton)
  {
    this.model = newModel;
    this.excelButton = newExcelButton;
  }
  
  /**
   * actionPerformed method which retrieves values from the view for the export to 1st and export to 1st and 2nd selection
   * @param e is the event where a button is clicked from the linear,quadratic or exponential buttons
   */
  public void actionPerformed(ActionEvent e)
  {
    if (this.excelButton.getText().equalsIgnoreCase("Export 1st Variable"))
    {
      try
      {
        this.model.exportToExcelVar1();
      }catch(IOException ex){
      }
    }
    else if (this.excelButton.getText().equalsIgnoreCase("Export 1st and 2nd Variable"))
    {
      try
      {
        this.model.exportToExcelVar2();
      }catch(IOException ex){
      }
    }
  }
}