/**LogoText Class
  * The text labeling the name of the corporation 
  * Last Modified: 06/18/2016
  * @author  Ammar Shaikh
  */
import java.awt.*;
import javax.swing.*;
public class LogoText extends JComponent
{
  /**
   * Constructor in order to create an instance of the LogoText class
   */
  public LogoText()
  {
    super();
    this.setPreferredSize(new Dimension(400,100));
  }
  
  /**
   * Method which sets the font and creates a line of text based on the font size
   */
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setFont(new Font("Rockwell", Font.PLAIN, 36)); 
    g.drawString("DSC CORPORATION",30,50);
   }
}