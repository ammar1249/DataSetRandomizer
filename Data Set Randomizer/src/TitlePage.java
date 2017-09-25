/**TitlePage Class
  * The first 'View' Class that displays the title page. 
  * Last Modified: 06/19/2016
  * @author  Ammar Shaikh
  */
import java.awt.*;
import javax.swing.*;
public class TitlePage extends JPanel
{
  private LogoAccident logo; //Creates an instance of the LogoAccident class
  private LogoText text; //Creates an instance of the LogoText class
  
  /**
   * Method which displays the title page itself
   */
  public TitlePage()
  {
    super();
    logo = new LogoAccident();
    this.add(logo);
    text = new LogoText();
    this.add(text);
  }
  
}