/**StartUpDSR Class
  * The main class which is run in order to use the Data Set Randomizer Program 
  * Last Modified: 06/19/2016
  * @author  Ammar Shaikh
  */
import javax.swing.*;
import java.awt.*;
public class StartUpDSR
{
  /**
   * Method which instantiates the title page, view and model in order to run and compile the program
   */
  public static void main (String [] args) throws InterruptedException
  {
    DSRModel model = new DSRModel();          
    DSRView mainScreen = new DSRView(model);  
    TitlePage titlePage = new TitlePage();
    
    //Creates the frame of the program, and sets a size for the title page
    JFrame frame = new JFrame("Data Set Randomizer");
    frame.setVisible(true);
    frame.setSize(800,600);
    frame.setContentPane(titlePage);
    
    Thread.sleep(2500);
    
    frame.setContentPane(mainScreen);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}