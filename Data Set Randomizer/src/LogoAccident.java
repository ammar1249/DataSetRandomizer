/**LogoAccident Class
  * The logo representing the DSC Corporation
  * Last Modified: 06/18/2016
  * @author  Alan Dong
  */
import java.awt.*;
import javax.swing.*;

public class LogoAccident extends JComponent
{
  /**
   * Constructor in order to create an instance of the LogoAccident class
   */
  public LogoAccident()
  {
    super();
    this.setPreferredSize(new Dimension(400,400));
  }
  
  /**
   * Method which creates the logo itself
   */
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.scale(this.getWidth()/30, this.getHeight()/30);
    g2.setStroke(new BasicStroke(1.0F/this.getWidth()));
    for (int counter = 0; counter<=30; counter++)
    {
      if (counter%2 ==0)
      {
        g2.setColor(Color.BLACK);
      }
      else if (counter%3 ==0)
      {
        g2.setColor(Color.GREEN);
      }
      else if (counter%5 == 0)
      {
        g2.setColor(Color.CYAN);
      }
      else
      {
        g2.setColor(Color.RED);
      }
      g2.fillOval((1+counter)/2, (1+counter)/2, 30-counter, 30-counter);
    }
  }
 
}