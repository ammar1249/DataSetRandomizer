/** GraphComponent Class
*    Creates a graph using the jfreechart and graphs the data
*    and is add to the jpanel for use
*    Last Modified: 6/8/2016
*   @author Michael C.
*/
import javax.swing.*;
import java.awt.*;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
public class GraphComponent extends JPanel
{
  private DSRModel model;                     //the model given by the user
  private double [] variableOne;              //also x value for equation
  private double [] variableTwo;              //the y values
  private double []yEquationPoint;            //y values for the equation
  private double lowest;                      
  private double highest;
  
  
  private String title;                      // titles for the graph
  private String xHeading;
  private String yHeading;
  
  /** Creates a default GraphComponent where there is 1 variable
    * @param newModel - is the model provide from the DSR Model Class
    * @param tempVar1 - x values to be to be graph
    */
  public GraphComponent(DSRModel newModel,double[] tempVar1)
  {
    //setting values for the graph
    super();    
    this.model = newModel;
    this.title =model.getTitle();
    this.xHeading =model.getHeading1();
    this.yHeading =model.getHeading2();     
    this.variableOne = tempVar1;
    this.lowest = model.getRangeMin();
    this.highest = model.getRangeMax();
    
    this.generateOneVarGraph();
  }
  
  /** Creates a default GraphComponent where there is 2 variable
    * @param newModel - is the model provide from the DSR Model Class
    * @param tempVar1 - x values to be to be graph
    * @param tempVar2 - y values to be to be graph
    */
  public GraphComponent(DSRModel newModel,double[] tempVar1,double[] tempVar2)
  {
    //setting values for the graph
    super();
    this.model = newModel;
    this.title =model.getTitle();
    this.xHeading =model.getHeading1();
    this.yHeading =model.getHeading2();     
    this.variableOne = tempVar1;
    this.variableTwo = tempVar2;
    this.yEquationPoint =model.getVar2NoNoise();
    
    this.generateTwoVarGraph();
  }
  
  /** generateOneVarGraph method
    * makes a histogram when there is 1 variable given
    */
   private void generateOneVarGraph()
   {  
      //makes a HistogramDataset and adds data to it
      HistogramDataset dataset = new HistogramDataset();   
      dataset.addSeries("", variableOne, this.model.getNumBins(), this.lowest, this.highest); 
      
      //makes chart where you can then add a plot to
      JFreeChart chart = ChartFactory.createHistogram(this.title,this.xHeading,this.yHeading,dataset,PlotOrientation.VERTICAL,false,false,false);
      XYPlot catPlot = chart.getXYPlot();
      
      //changes colour of the points
      catPlot.getRenderer().setSeriesPaint(0, Color.BLUE);
      catPlot.setRangeGridlinePaint(Color.BLACK);
      
      //since chart is not a jcomponent you can't add it the jpanels so you need to it add it to chartpanel    
      ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
      this.add(chartPanel);  
   } 
   
   /** generateTwoVarGraph method
    * makes a xy scatter plot and makes a line of best fit
    */
   private void generateTwoVarGraph()
   {
     
     XYSeries points = new XYSeries("points");                        //a collection of points for variable 1 and variable 2
     XYSeries equationPoints = new XYSeries("Equation of Best Fit");  //a collection of points for the line of best fit    
     XYSeriesCollection dataset = new XYSeriesCollection();           //a group of series of the variable 1 and variable 2
     XYSeriesCollection equationDataset = new XYSeriesCollection();   //a group of series of the line of best fit
     XYItemRenderer equationRender = new XYSplineRenderer();          //a render item used to make the curve of the line of best fit smooth
     JFreeChart chart = ChartFactory.createScatterPlot(title,this.xHeading,this.yHeading,dataset,PlotOrientation.VERTICAL,false,false,false); 
     XYPlot plot = chart.getXYPlot();   //makes a xy plot which is the actual points, above is a scatterplot chart the plot is going on
     
     //adding values to series
     for(int x =0;x<variableOne.length;x++)
     {
       points.add(variableOne[x],variableTwo[x]); 
       equationPoints.add(variableOne[x],yEquationPoint[x]); 
     }
     
     //add series to dataSet
     dataset.addSeries(points); 
     equationDataset.addSeries(equationPoints);
     
     //setting render because on the regular chart there can only be one plot to make extra plots you a render object
     plot.setDataset(1, equationDataset);
     plot.setRenderer(1, equationRender);
     
     //setting colors of graph and adding to component
     plot.getRenderer().setSeriesPaint(0, Color.BLUE);
     plot.setRangeGridlinePaint(Color.BLACK);
     ChartPanel chartPanel = new ChartPanel(chart);
     chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
     this.add(chartPanel);  
   }
}