/**DSRView Class
  * The Graphical User Interface for the Data Set Randomizer Program 
  * Last Modified: 6/19/2016
  * @author  Ammar Shaikh
  */
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.border.TitledBorder;
import java.lang.*;

public class DSRView extends JPanel
{
  private DSRModel dsrModel; //Creates an instance of the model
  private JTabbedPane contents = new JTabbedPane(); //Creates a new panel with tabs

  //Panels  for Variable 1
  private JPanel var1 = new JPanel(); //The main panel for variable one tab
  private JPanel var1Text = new JPanel(); //Panel for all textboxes and labels in the variable one tab
  private JPanel var1Table = new JPanel(); //Panel for the table containing generated values in the variable one tab
  private JPanel var1Buttons = new JPanel(); //Panel for all the buttons in the variable one tab
  private String[] var1ColumnNames; //An array used for the table column name in variable one to create the table
  private JPanel var1Graph = new JPanel(); //Panel for the graph in the variable one tab
  
  
  //Panels for Variable 2
  private JPanel var2 = new JPanel(); //The main panel for variable two tab
  private JPanel var2Table = new JPanel(); //Panel for the table containing generated values in the variable two tab
  private JPanel var2Text = new JPanel(); ///Panel for all textboxes and labels in the variable two tab
  private JPanel var2Buttons = new JPanel(); //Panel for all the buttons in the variable two tab
  private String [] var2ColumnNames; //An array used for the table column names in variable two to create the table
  private JPanel var2Graph = new JPanel(); //Panel for the graph in variable two
  
  
  //Variable 1 and 2 Common Components
  private JButton var1ExportButton = new JButton("Export 1st Variable"); //Button which exports data to excel when pressed
  private JButton var2ExportButton = new JButton("Export 1st and 2nd Variable"); //Button which exports data to excel when pressed
  private JTextField titleField = new JTextField(10);  //Title text box for the user to enter the title
  private JLabel titleLabel = new JLabel("Title"); //Label indicating which box is the title
  private JTextField heading1Field = new JTextField(10); //First heading text box for the user to enter the first heading
  private JLabel heading1Label = new JLabel("Heading 1"); //First heading label used to indicate the text box for the first heading
  private JTextField heading2Field = new JTextField(10); //Second heading text box for the user to enter the second heading
  private JLabel heading2Label = new JLabel("Heading 2");  //Second heading label used to indicate the text box for the first heading
  private int rows;
  
  //Variable 1 components
  private JLabel dataPointLabel = new JLabel("Number of points"); //Label used to indicate text box for the number of data points
  private JTextField dataPointField = new JTextField("0",8); //Text box used to input the number of data points
  private JTextField mean1Field = new JTextField("0",8); //Text box used to input the first mean value
  private JLabel mean1Label = new JLabel("Mean 1"); //Label used to indicate text box for the first mean value
  private JTextField mean2Field = new JTextField("0",8); //Text box used to input the second mean value
  private JLabel mean2Label = new JLabel("Mean 2"); //Label used to indicate text box for the first mean value
  private JTextField stDev1Field = new JTextField("0",8); //Text box used to input the first standard deviation value
  private JLabel stDev1Label = new JLabel("Standard Deviation 1"); //Label used to indicate text box for the first standard deviation value
  private JTextField stDev2Field = new JTextField("0",8); //Text box used to input the second standard deviation value
  private JLabel stDev2Label = new JLabel("Standard Deviation 2"); //Label used to indicate text box for the first standard deviation value
  private JLabel minLabel = new JLabel("Min"); //Label used to indicate text box for the minimum value
  private JTextField minField = new JTextField("0",8); //Text box used to input the minimum value
  private JLabel maxLabel = new JLabel("Max"); //Label used to indicate text box for the maximum value
  private JTextField maxField = new JTextField("0",8); //Text box used to input the maximum value
  private JLabel binWidthLabel = new JLabel("binWidth"); //Label used to indicate text box for the bin width value
  private JTextField binWidthField = new JTextField("0",8); //Text box used to input the bin width value
  private JButton uniformNormButton = new JButton ("Uniform Normal"); //Button used to produce a uniform normal graph
  private JButton randNormButton = new JButton("Random Normal"); //Button used to produce a random normal graph
  private JButton randBimodalButton = new JButton("Bimodal"); //Button used to produce a bimodal graph
  private JButton generateVar1  = new JButton ("Generate Variable 1 Values"); //Button used to randomly generate values between the min and max and produce one of the selected graphs in the variable one tab
  private JTable var1Chart; //Table for the variable one tab
  
  //Variable 2 Components
  private JLabel equationLabel = new JLabel ("Equation: ");
  private JLabel aValueLabel = new JLabel("\"a\" Value"); //Label indicating the text box for the "a" value
  private JTextField aValueField = new JTextField("0",8); //Text box for the user to input the "a" value in the linear, quadratic, and exponential equations
  private JLabel bValueLabel = new JLabel("\"b\" Value"); //Label indicating the text box for the "b" value
  private JTextField bValueField  = new JTextField("0",8); //Text box for the user to input the "b" value in the linear, quadratic, and exponential equations
  private JLabel cValueLabel = new JLabel("\"c\" Value"); //Label indicating the text box for the "c" value
  private JTextField cValueField = new JTextField("0",8); //Text box for the user to input the "c" value in the quadratic and exponential equations
  private JLabel dValueLabel = new JLabel("\"d\" Value"); //Label indicating the text box for the "d" value
  private JTextField dValueField = new JTextField("0",8); //Text box for the user to input the "d" value in the exponential equation
  private JButton linearEqButton = new JButton("Linear"); //Button used to produce a line of best fit based on the "a" and "b" values
  private JButton quadEqButton = new JButton("Quadratic"); //Button used to produce a curve of best fit based on the "a", "b", and "c" values
  private JButton exponentEqButton = new JButton ("Exponential");//Button used to produce a exponential curve of best fit  based on the "a", "b" and "c" values
  private JButton generateVar2 = new JButton ("Generate Variable 2 Values"); //Button used to randomly generate values between the min and max and produce one of the selected graphs in the variable two tab
  private JLabel noisePercentLabel = new JLabel("Noise Percent"); //Label indicating the slider for the noise percent value
  private JSlider noisePercentSlider = new JSlider(0,100,0); //Slider for selecting a value between 0 and 100
  private JLabel currentValueLabel = new JLabel("Current value: 0"); //Label for indicating the current noise percent value; changes when the slider is moved left or right
  private JTable var2Chart; //Table for the variable two tab
  
  /** Default constructor for the GUI.  Assigns Model and View, identifies controllers and draws the layout
    * @param calc The Model for the Data Set Randomizer
  */ 
  public DSRView(DSRModel model)
  {
    super();
    this.dsrModel = model;
    this.dsrModel.setGUI(this);
    this.registerControllers();
    this.layoutView();
  }
  
  /** Method for assigning controllers to their specified JComponents in the variable one tab
  */
  public void var1Controllers()
  {
    //Controller for  the variable one graphs/distribution buttons
    DistributionController distController = new DistributionController(dsrModel, uniformNormButton, randNormButton, randBimodalButton);
    this.uniformNormButton.addActionListener(distController);
    this.randNormButton.addActionListener(distController);
    this.randBimodalButton.addActionListener(distController);
    
    //Controller for the mean and standard deviation textboxes in the variable one tab
    DSRMeanAndSDController meanStDevController = new DSRMeanAndSDController(dsrModel, mean1Field, mean2Field, stDev1Field, stDev2Field);
    this.mean1Field.addActionListener(meanStDevController);
    this.mean2Field.addActionListener(meanStDevController);
    this.stDev1Field.addActionListener(meanStDevController);
    this.stDev2Field.addActionListener(meanStDevController);
    
    //Controller for the bin width textbox in the variable one tab
    DSRParameterController binWidth = new DSRParameterController(dsrModel, binWidthField);
    this.binWidthField.addActionListener(binWidth);
    
    //Controller for the maximum and minimum textboxes in the variable one tab
    DSRSetRangeController rangeController = new DSRSetRangeController(dsrModel, maxField, minField);
    this.maxField.addActionListener(rangeController);
    this.minField.addActionListener(rangeController);
    
    //Controller for variable one that generates random values based on the current text field values
    Var1GenerateController var1Result = new Var1GenerateController(dsrModel, titleField, heading1Field, heading2Field, mean1Field, mean2Field, stDev1Field, stDev2Field, minField, maxField, binWidthField, dataPointField );
    this.generateVar1.addActionListener(var1Result);
    
    //Controller for variable one that generates random values based on the export button pressed
    ExcelButtonController var1Export = new ExcelButtonController(dsrModel, var1ExportButton);
    this.var1ExportButton.addActionListener(var1Export);
  }
  
  /** Method for assigning controllers to their specified JComponents in the variable two tab
  */
  public void var2Controllers()
  {
    //Controller for the variable two equation text boxes ("a", "b", "c", "d" values)
    DSREquationValueController equationValueController = new DSREquationValueController(dsrModel, aValueField, bValueField, cValueField, dValueField);
    this.aValueField.addActionListener(equationValueController);
    this.bValueField.addActionListener(equationValueController);
    this.cValueField.addActionListener(equationValueController);
    this.dValueField.addActionListener(equationValueController);
    
    //Controller for the variable two buttons producing the linear, exponential or quadratic graphs
    EquationButtonController equationTypeController = new EquationButtonController(dsrModel, linearEqButton, quadEqButton, exponentEqButton);
    this.linearEqButton.addActionListener(equationTypeController);
    this.quadEqButton.addActionListener(equationTypeController);
    this.exponentEqButton.addActionListener(equationTypeController);
    
    //Controller for variable two that generates random values based on the current text field values
    Var2GenerateController var2Result = new Var2GenerateController(dsrModel, titleField, heading1Field, heading2Field, aValueField, bValueField, cValueField, dValueField);
    this.generateVar2.addActionListener(var2Result);
    
    //Controller for the noise percent slider which continuously displays the current value of the slider
    SliderValueChange changeValue = new SliderValueChange(dsrModel, noisePercentSlider, currentValueLabel);
    this.noisePercentSlider.addChangeListener(changeValue);
    
    //Controller for variable one that generates random values based on the export button pressed
    ExcelButtonController var2Export = new ExcelButtonController(dsrModel, var2ExportButton);
    this.var2ExportButton.addActionListener(var2Export);
    
  }
  /** Method for assigning controllers to their specified JComponents
   */
  public void registerControllers()
  {
    //Controller for the common textboxes between variable one and two
    DSRLabelController labelController = new DSRLabelController(dsrModel, titleField, heading1Field, heading2Field);
    this.titleField.addActionListener(labelController);
    this.heading1Field.addActionListener(labelController);
    this.heading2Field.addActionListener(labelController);
    
    this.var1Controllers();
    this.var2Controllers();
  }
  
   /** Draws the initial layout for the Data Set Randomizer
     */ 
  public void layoutView()
  {
    this.setPreferredSize(new Dimension(1350,650));
    this.common();
    this.variableOne();
    this.variableTwo();
  }
  
  /**Method used to add all common JComponents to the main panel
   */
  public void common()
  {
    this.contents.setPreferredSize(new Dimension(1350,650));
    this.add(titleLabel);
    this.add(titleField);
    this.add(heading1Label);
    this.add(heading1Field);
    this.add(heading2Label);
    this.add(heading2Field);
    this.add(var1ExportButton);
    this.add(var2ExportButton);
    this.add(contents); //Adds JTabbedPane to the main Panel
    
    //Tabs and panels assigned to variable one and two
    this.contents.addTab("Variable 1",var1); 
    this.contents.addTab("Variable 2",var2);
    
  }
  
  /**Method which creates and displays the table for variable one
   */
  public void drawVar1Table()
  {
    this.var1ColumnNames = new String[1];
    this.var1ColumnNames[0] = dsrModel.getHeading1();
    String [][] var1Data = new String [this.rows][1];
    
     for (int x = 0; x < this.rows; x++)
    {
      var1Data [x][0] = String.valueOf(dsrModel.getVar1Value(x));
    }
     
    this.var1Chart = new JTable(var1Data, var1ColumnNames);
    this.var1Chart.setPreferredScrollableViewportSize(new Dimension(200,500));
    this.var1Chart.setFillsViewportHeight(true);
    JScrollPane jps1 = new JScrollPane(var1Chart);
    
    String titleValue = dsrModel.getTitle();
    this.var1Table.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder(), titleValue, TitledBorder.CENTER,TitledBorder.TOP));
    this.var1Table.add(jps1);
    
    this.var1.add(var1Table, BorderLayout.EAST);
  }
  /**Method for adding all variable one components to their respective panels
   */
  public void variableOne()
  {
    this.var1Text.setLayout(new GridLayout(20,10)); //Sets the layout for the variable one text boxes and labels panel
    this.var1.setLayout(new BorderLayout()); //Sets the layout for the variable one main panel
    this.var1.setPreferredSize(new Dimension(1350,650)); //Sets size for the main panel
    
    //Components for text and label panel
    this.var1Text.add(dataPointLabel);
    this.var1Text.add(dataPointField);
    this.var1Text.add(mean1Label);
    this.var1Text.add(mean1Field);
    this.var1Text.add(mean2Label);
    this.var1Text.add(mean2Field);
    this.var1Text.add(stDev1Label);
    this.var1Text.add(stDev1Field);
    this.var1Text.add(stDev2Label);
    this.var1Text.add(stDev2Field);
    this.var1Text.add(minLabel);
    this.var1Text.add(minField);
    this.var1Text.add(maxLabel);
    this.var1Text.add(maxField);
    this.var1Text.add(binWidthLabel);
    this.var1Text.add(binWidthField);
    
    //Components for buttons panel
    this.var1Buttons.add(uniformNormButton);
    this.var1Buttons.add(randNormButton);
    this.var1Buttons.add(randBimodalButton);
    this.var1Buttons.add(generateVar1);

    //this.drawVar1Table();
    //Adding the sub-panels to the main panel
    this.var1.add(var1Text, BorderLayout.WEST);  
    this.var1.add(var1Buttons, BorderLayout.NORTH);
  }
  
  /**Creates and displays the graphs for variable on and two
   */ 
  public void drawGraph()
  {
    //Determines if the user on the first tab or second tab and creates a graph for the respective tab
    if (this.contents.getSelectedIndex()==0)
    {
      //Creates a graph for the variable one tab and adds it to the main panel
      this.var1Graph.removeAll();
      GraphComponent var1G = new GraphComponent(dsrModel, dsrModel.getVar1Array());
      this.var1Graph.add(var1G);
      this.var1.add(var1Graph, BorderLayout.CENTER);
    }
    else
    {
      //Creates a graph for the variable two tab and adds it to the main panel
      this.var2Graph.removeAll();
      GraphComponent var2G = new GraphComponent(dsrModel,dsrModel.getVar1Array(), dsrModel.getVar2Array());
      this.var2Graph.add(var2G);
      this.var2.add(var2Graph, BorderLayout.CENTER);
    }
    
  }
  /**Method for adding all variable one components to their respective panels
   */
  public void variableTwo()
  {
    this.var2Text.setLayout(new GridLayout(16,10)); 
    this.var2.setLayout(new BorderLayout());
    this.var2.setPreferredSize(new Dimension(1350,650));
  
    this.var2Text.add(equationLabel);
    this.var2Text.add(aValueLabel);
    this.var2Text.add(aValueField);
    this.var2Text.add(bValueLabel);
    this.var2Text.add(bValueField);
    this.var2Text.add(cValueLabel);
    this.var2Text.add(cValueField);
    this.var2Text.add(dValueLabel);
    this.var2Text.add(dValueField);
    this.var2Text.add(noisePercentLabel);
    this.var2Text.add(noisePercentSlider);
    this.var2Text.add(currentValueLabel);
    
    this.var2Buttons.add(linearEqButton);
    this.var2Buttons.add(quadEqButton);
    this.var2Buttons.add(exponentEqButton);
    this.var2Buttons.add(generateVar2);
    
    
    //Creates ticks for every value of 20; so 20, 40, 60, 80, 100
    this.noisePercentSlider.setMajorTickSpacing(20);
    this.noisePercentSlider.setPaintLabels(true);
    this.noisePercentSlider.setPaintTicks(true);
    
    //this.drawVar2Table();
    this.var2.add(var2Text, BorderLayout.WEST);
    this.var2.add(var2Buttons, BorderLayout.NORTH);
  }
  
  
  public void drawVar2Table()
  {
     //Table
    this.var2ColumnNames = new String [2];
    this.var2ColumnNames[0] = dsrModel.getHeading1();
    this.var2ColumnNames[1] = dsrModel.getHeading2();
    this.rows = dsrModel.getNumDataPoints();
    String [][] var2Data = new String [this.rows][2];
    
    //Column 1
    for (int x = 0; x < this.rows; x++)
    {
      var2Data [x][0] = "" + dsrModel.getVar1Value(x);
    }
    
    //Column 2
    for (int x = 0; x < this.rows; x++)
    {
      var2Data [x][1] = "" + dsrModel.getVar2Value(x);
    }
    
    this.var2Chart = new JTable(var2Data, var2ColumnNames);
    this.var2Chart.setPreferredScrollableViewportSize(new Dimension(200,500));
    this.var2Chart.setFillsViewportHeight(true);
    JScrollPane jps2 = new JScrollPane(var2Chart);
    String titleValue = dsrModel.getTitle();
    this.var2Table.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),titleValue,TitledBorder.CENTER,TitledBorder.TOP));
    this.var2Table.add(jps2);
    this.var2.add(var2Table, BorderLayout.EAST);
  }
  
  public void update()
  {
    this.rows = dsrModel.getNumDataPoints(); //Gets the amount of rows the table will have from the model
    //Determines which tab the user is currently on and creates the table based on that
    if (this.contents.getSelectedIndex()==0)
    {
      //Creates a table for variable one
      this.var1Table.removeAll();
      this.drawVar1Table();
    }
    else if(contents.getSelectedIndex()==1)
    {
      //Creates a table for variable one
      this.var2Table.removeAll();
      this.drawVar2Table();
      this.equationLabel.setText("Equation: " + dsrModel.getEquation());
    }
    this.drawGraph();
    this.repaint(); //Refreshes the entire main panel
  }
}