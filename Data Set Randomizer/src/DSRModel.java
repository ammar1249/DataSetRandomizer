/** DSRModel.java
  * Contains all the calculations, inner code, and attributes for computation. Based on given information from the user, this model can:
  * 1. Create a 1 variable data set that models a uniform, normal, or bimodal distribution.
  * 2. Based on the given 1 variable data, and given equation parameters, compute a 2nd variable data set. Then it can apply randomness to the 2nd variable data set
  * 3. Export the data sets into an Excel file.
  * @since June 19th, 2016
  * @author Alan Dong
  */

import java.io.*;
import org.apache.commons.math3.distribution.*;
public class DSRModel
{
  
  /*Terminology in these comments: "X" is variable 1, "Y" is variable 2.
   * Bins: Intervals that group X values together.
   */
  //CONSTANTS
  //These 3 constants are used to determine which distribution is chosen.
  public final int RANDOM_UNIFORM =1;  
  public final int RANDOM_NORMAL = 2;
  public final int RANDOM_BIMODAL = 3;
  
  //These 3 constants are used to determine which equation is chosen. 
  public final int LINEAR = 1;                       //Form: y = ax +b
  public final int QUADRATIC = 2;             //Form: y = ax^2 + bx + c
  public final int EXPONENTIAL = 3;         //Form: y = ab^(cx) +d
  
  //Common Attributes to be placed outside of the tabbed panel.
  private String graphName; //The graph's name, and the Excel file's title.
  private String heading1; //The X variable title in the graph
  private String heading2; //The Y variable title in the graph
  private DSRView dataSetView;  //The Data Set Randomizer's View.
  
  //Attributes used for 1 var stats.
  private double [] var1Values;  //The array for the X Values.
  private double minRange;  //The range to generate values within.
  private double maxRange;
  private double mean1; //Properties required for the normal or bimodal distribution.
  private double mean2;
  private double stDev1;
  private double stDev2;
  private double binWidth;  //The width of each bin. (interval of values)
  private int numDataPoints; //Number of X Values to generate.
  private int distributionChosen; //The distribution type chosen by the user.
  private int numBins;  //The number of bins to be given to the histogram.
  private int arrayIndex; //Since the generation of the X Values are not reliant on the length of the var1Values array, this arrayIndex is used to fill in values for that array. It will not exceed the array's length and result in an error.
  
  //Attributes used for 2 var stats
  private double [] var2Values;  //The "actual, data gathered" Y values when X Values are put into the equation and computed. Then these values have noise (randomness)
                                                     // applied to them, since in a real life case, gathered data is never perfectly represented by an equation.
  private double [] var2Equation; //These are the theoretical values for Y when the X values are put into the equation and computed. This is needed to draw the line of best fit.
  private double noisePercent; //The percentage of noise or randomness to apply to the Y Values.
  private double aValue;  //The four values that represent the coefficients in the equation. Their placement changes based on the equation chosen. 
  private double bValue;  //Ex: The b Value is multiplied with X in quadratic form, but in linear form, it acts as the y-intercept.
  private double cValue;
  private double dValue;
  private String equation;  //The equation as a string.
  private int equationChosen; //A number to represent the equation chosen.
  
  /**DSRModel Constructor
   * Initializes all the properties and options to zero. 
   */
  public DSRModel()
  {
    super();
    this.distributionChosen = 0;
    this.equationChosen = 0;
    this.numBins = 0;
    this.arrayIndex = 0;
    this.aValue = 0;
    this.bValue = 0;
    this.cValue = 0;
    this.dValue  = 0;
    this.mean1 = 0;
    this.stDev1 = 0;
    this.mean2 = 0;
    this.stDev2 = 0;
    this.numDataPoints = 0;
    this.minRange = 0;
    this.maxRange = 0;
    this.var1Values  = new double [0];
    this.var2Values = new double [0];
    this.var2Equation = new double [0];
    this.binWidth = 0;
    this.graphName = " ";
    this.heading1 = " ";
    this.heading2 = " ";
  }
  
  /**setGUI method
   * Sets the indicated view as the view to communicate with.
   * @param DSRView inputView   The new view to link the model with.
   */
  public void setGUI (DSRView inputView)
  {
    this.dataSetView = inputView;
  }
  
  /**generateVar1 method
   * Based on the given properties of the distribution and the distribution chosen, initializes the var1Values array, generating random values that fit the user's inputted properties.
   */
  public void generateVar1()
  {
    double [] tempVar1 = new double [this.numDataPoints];
    
    this.arrayIndex = 0;
    double minOfBin;  //temporary storage variables for minimum and maximum values of the current bin.
    double maxOfBin;
    double zScoreMin;  //The z-scores of the min and maxOfBin. These can be converted to percentiles down below.
    double zScoreMax;
    NormalDistribution dist = new NormalDistribution(); //Used to access the cumulative probability method. (since it is not static)
    double percentileMin;  //The percentile of a value is the area under the curve that precedes the value.
    double percentileMax;  //Therefore, the percentile of the max minus the percentile of the min gives the percent of values that should be in the bin.
    double percentileBin;
    this.calculateNumBins();
    if (this.distributionChosen == this.RANDOM_UNIFORM)
    {
      //For every bin
      for (int x = 1; x <= this.numBins; x++)
      {
        //Generates random values within the bin.
        for (int y = 0; y < (Math.round(this.numDataPoints / this.numBins)); y++)
        {
          if (arrayIndex < this.numDataPoints)
          {
            tempVar1[this.arrayIndex] = Math.random()*(this.binWidth)+(this.minRange+this.binWidth*(x-1));
            tempVar1[this.arrayIndex] = Math.round(tempVar1[this.arrayIndex]*100.0) /100.0;
            this.arrayIndex++;
          }
        }
      }
    }
    
    else if (this.distributionChosen == this.RANDOM_NORMAL)
    {
      //for every bin.
      for (int x = 1; x<= this.numBins; x++)
      {
        minOfBin = this.minRange + this.binWidth*(x-1);
        maxOfBin = this.minRange + this.binWidth*x;
        zScoreMin = (minOfBin - this.mean1)/this.stDev1;
        zScoreMax = (maxOfBin - this.mean1)/this.stDev1;
        percentileMin = dist.cumulativeProbability(zScoreMin);
        percentileMax = dist.cumulativeProbability(zScoreMax);
        percentileBin = percentileMax - percentileMin;
        //produce values accordingly to the percentile of the bin. Ex: If the percentileBin is 0.3, then 30% of the total number of values should be within that bin.
        for (int y = 0; y < (Math.round(percentileBin*this.numDataPoints)); y++)
        {
          if (arrayIndex < this.numDataPoints)
          {
            tempVar1[this.arrayIndex] = Math.random()*(this.binWidth)+(this.minRange+this.binWidth*(x-1));
            tempVar1[this.arrayIndex] = Math.round(tempVar1[this.arrayIndex]*100.0) /100.0;
            this.arrayIndex++;
          }
        }
      }
    }
    
    else if (this.distributionChosen == this.RANDOM_BIMODAL)
    {
      //Calculate how many frequencies in each bin.
      //based on how close each bin is to each mean, this will determine its percentileBin
      //for every bin.
      for (int x = 1; x<= this.numBins; x++)
      {
        minOfBin = this.minRange + this.binWidth*(x-1);
        maxOfBin = this.minRange + this.binWidth*x;
        if (Math.abs(minOfBin-this.mean1) < Math.abs(minOfBin-this.mean2))
        {
          zScoreMin = (minOfBin - this.mean1)/this.stDev1;
          zScoreMax = (maxOfBin - this.mean1)/this.stDev1;
        }
        else
        {
          zScoreMin = (minOfBin - this.mean2)/this.stDev2;
          zScoreMax = (maxOfBin - this.mean2)/this.stDev2;
        }
        percentileMin = dist.cumulativeProbability(zScoreMin);
        percentileMax = dist.cumulativeProbability(zScoreMax);
        percentileBin = percentileMax - percentileMin;
        //produce values accordingly to the percentile of the bin. Ex: If the percentileBin is 0.3, then 30% of the total number of values should be within that bin.
        //Also, 50% of the data should be in the first bell curve (with its own mean and stdev) and vice versa. Therefore, the /2 component is necessary or else the program will crash.
        for (int y = 0; y < (Math.round(percentileBin*this.numDataPoints/2)); y++)
        {
          if (arrayIndex < this.numDataPoints)
          {
            tempVar1[this.arrayIndex] = Math.random()*(this.binWidth)+(this.minRange+this.binWidth*(x-1));
            tempVar1[this.arrayIndex] = Math.round(tempVar1[this.arrayIndex]*100.0) /100.0;  //Round to 3 decimal places
            this.arrayIndex++;
          }
        }
        
      }
    }
    this.var1Values = new double [this.arrayIndex];
    for (int x = 0; x < this.arrayIndex; x++)
    {
      this.var1Values[x] = tempVar1[x];
    }
    this.numDataPoints = this.var1Values.length;
    this.jumbleArray();
    this.updateView();
  }
  
  /**jumbleArray method
    * After the entire variable 1 array is initialized, it is already sorted from lowest to highest. Using a swap algorithm and a loop, the variable 1 array is scrambled.
    */
  private void jumbleArray ()
  {
    double tempNum;
    for (int x = 0; x < this.numDataPoints*2; x++)
    {
      int swapNum1 =(int)(Math.random()*this.numDataPoints); 
      int swapNum2 = (int)(Math.random()*this.numDataPoints);
      tempNum = this.var1Values[swapNum2];
      this.var1Values[swapNum2] = this.var1Values[swapNum1];
      this.var1Values[swapNum1] = tempNum;
    }
  }
  
  /** calculateVar2 method
    * Based on the already initialized variable 1 array, and the chosen equation and its coefficients, this method initializes the 2nd variable array. (Like X and Y coordinates)
    * Afterward, the variable 2 array has randomness applied to it.
    */
  public void calculateVar2()
  {
    this.var2Values = new double [this.var1Values.length];
    this.var2Equation = new double [this.var1Values.length];
    if (this.equationChosen == this.LINEAR)
    {
      for (int x = 0; x < this.var1Values.length; x++)
      {
        this.var2Values[x] = this.aValue*this.var1Values[x] + this.bValue;
      }
    }
    else if (this.equationChosen == this.QUADRATIC)
    {
      for (int x = 0; x < this.var1Values.length; x++)
      {
        this.var2Values[x] = this.aValue*(this.var1Values[x]*this.var1Values[x]) + this.bValue*this.var1Values[x] + this.cValue;
      }
    }
    else if (this.equationChosen == this.EXPONENTIAL)
    {
      for (int x = 0; x < this.var1Values.length; x++)
      {
        this.var2Values[x] = this.aValue*(Math.pow(this.bValue, (this.cValue*this.var1Values[x]))) + this.dValue;
      }
    }
    //rounding
    for (int x = 0; x < this.var2Values.length; x++)
    {
      this.var2Values[x] = Math.round(this.var2Values[x]*100.0) /100.0;
      this.var2Equation[x] = this.var2Values[x];  //Saves the theoretical values for variable 2 before applying noise.
    }
    this.applyNoise();
    this.updateView();
  }
  
  /**applyNoise method
    * Helper method. Applies randomness to the 2nd variable array values, to simulate a real life case. (randomness or noise percent of 0 will generate a perfect correlation.)
    */
  private void applyNoise()
  {
    //Compute the range of values for the 2nd variable, by applying the equation to the min and max values of the 1st variable.
    double maxRangeVar2 = 0; //The maximum value possible for variable 2.
    double minRangeVar2 = 0;  //The minimum value possible for variable 2.
    if (this.equationChosen == this.LINEAR)
    {
      maxRangeVar2 = this.aValue*(this.maxRange) + this.bValue;
      minRangeVar2 = this.aValue*(this.minRange) + this.bValue;
    }
    else if (this.equationChosen == this.QUADRATIC)
    {
      maxRangeVar2 = this.aValue*(this.maxRange*this.maxRange) + this.bValue*this.maxRange + this.cValue;
      minRangeVar2 = this.aValue*(this.minRange*this.minRange) + this.bValue*this.minRange + this.cValue;
    }
    else if (this.equationChosen == this.EXPONENTIAL)
    {
      maxRangeVar2 = this.aValue*(Math.pow(this.bValue,(this.cValue*this.maxRange)))+ this.dValue;
      minRangeVar2 = this.aValue*(Math.pow(this.bValue,(this.cValue*this.minRange)))+ this.dValue;
    }
    double randomNum = 0;
    for (int x =0; x < this.var2Values.length; x++)  //
    {
      randomNum = this.noisePercent*(Math.random()*2 - 1);
      if (this.equationChosen == this.LINEAR)  
      {
        this.var2Values[x] = this.var2Values[x] + (randomNum*(this.noisePercent*(maxRangeVar2-minRangeVar2))); 
      }
      else
      {
        this.var2Values[x] = this.var2Values[x] + (randomNum*(this.noisePercent*(maxRangeVar2+minRangeVar2))); 
      }
      this.var2Values[x] = Math.round(this.var2Values[x]*100.0) /100.0;
    }
  }
  
  /** exportToExcelVar1 method
    * Creates a ConvertToExcel object and exports the1 variable data set to an excel sheet
    */
  public void exportToExcelVar1() throws IOException
  {
    ConvertToExcel exporter = new ConvertToExcel (this, this.graphName, this.var1Values);
    exporter.exportOneVariable();
  }
  
  /** exportToExcelVar2 method
    * Creates a ConvertToExcel object and exports the 2 variable data sets to an excel sheet
    */
  public void exportToExcelVar2() throws IOException
  {
    ConvertToExcel exporter = new ConvertToExcel (this, this.graphName, this.var1Values, this.var2Values);
    exporter.exportTwoVariable();
  }
    
  /**calculateNumBins method
    * Helper method that calculates the number of bins to be used when calculating how many number of points fit into each bin for the generateVar1 method.
    */
  private void calculateNumBins()
  {
    this.numBins = 0;
    for (double x = this.minRange; x < this.maxRange; x+= this.binWidth)
    {
      this.numBins++;
    }
  }
  
  /** getNumBins method
   * @returns numBins   The number of the bins or grouped intervals.
   */
  public int getNumBins()
  {
    return this.numBins;
  }
  
  /** getVar1Value method
    * @param int arrayIndex    the index of the array. Required information to return the array's value at arrayIndex.
    * @returns double var1Values[arrayIndex]   The X value to be returned.
    */
  public double getVar1Value (int arrayIndex)
  {
    return this.var1Values[arrayIndex];
  }
  
  /** getVar1Array method
    * @returns double [] var1Values    The entire variable 1 array.
    */
  public double [] getVar1Array()
  {
    return this.var1Values;
  }
  
  /** getVar2Value method
    * @param int arrayIndex    the index of the array. Required information to return the array's value at arrayIndex.
    * @returns double var2Values[arrayIndex]   The Y value to be returned.
    */
  public double getVar2Value (int arrayIndex)
  {
    return this.var2Values[arrayIndex];
  }
  
  /** getVar2Array method
    * @returns double [] var2Values    The entire variable 2 array with applied noise.
    */
  public double [] getVar2Array()
  {
    return this.var2Values;
  }
  
  /** getVar2NoNoise method
    * @returns double [] var2Equation    The entire variable 2 array without applied noise. Required to draw the line of best fit.
    */
  public double [] getVar2NoNoise()
  {
    return this.var2Equation;
  }
  
  /** getNumDataPoints method
    * @returns int numDataPoints    The number of data points is returned.
    */
  public int getNumDataPoints()
  {
    return this.numDataPoints;
  }
  
  /** getEquation method
    * @returns String equation    The equation is returned as a string.
    */
  public String getEquation()
  {
    return this.equation;
  }
  
  /** getTitle method
    * @returns String graphName   The title of the graph is returned as a string.
    */
  public String getTitle()
  {
    return this.graphName;
  }
  
  /** getHeading1 method
    * @returns String heading1   The heading for the X axis of the graph.
    */
  public String getHeading1()
  {
    return this.heading1;
  }
  
  /** getHeading2 method
    * @returns String heading2   The heading for the Y axis of the graph.
    */
  public String getHeading2()
  {
    return this.heading2;
  }
  
  /** getBinWidth method
    * @returns double binWidth    The width of each interval/bin is returned.
    */
  public double getBinWidth()
  {
    return this.binWidth;
  }
  
  /** getRangeMax method
    * @returns double maxRange    The maximum value that the values within the X array can reach.
    */
  public double getRangeMax()
  {
    return this.maxRange;
  }
  
  /** getRangeMin method
    * @returns double minRange    The minimum value that the values within the X array can reach.
    */
  public double getRangeMin()
  {
    return this.minRange;
  }
  
  /** updateView method
    * Calls the current view's update method.
    */
  public void updateView()
  {
    this.dataSetView.update();
  }
  
  /** setNumDataPoints method
    * Sets the number of data points needed by the user.
    * @param int inputDataPointCount     The user's inputted number of data points.
    */
  public void setNumDataPoints( int inputDataPointCount)
  {
    this.numDataPoints = inputDataPointCount;
  }
  
  /** setBinWidth method
    * Sets the width of each bin or interval.
    * @param double inputBinWidth   The user's inputted bin width.
    */
  public void setBinWidth (double inputBinWidth)
  {
    this.binWidth = inputBinWidth;
  }
  
  /** setVarLabels method
    * Sets the variable labels and title of the graph.
    * @param String inputTitle   The user's designated title.
    * @param String inputHeadingX     The user's designated X axis label.
    * @param String inputHeadingY     The user's designated Y axis label.
    */
  public void setVarLabels (String inputTitle, String inputHeadingX, String inputHeadingY)
  {
    this.graphName = inputTitle;
    this.heading1 = inputHeadingX;
    this.heading2 = inputHeadingY;
  }
  
  /**setMeanStDev method
    * Sets the standard deviations and means of the X values. There are two sets of mean and standard deviation since the bimodal distributions requires two sets.
    * @param double inputMean1   The first mean of the X values. Required for both bimodal and random normal distributions.
    * @param double inputStDev1   The first standard deviation value for the X values. Required for both bimodal and random normal distributions.
    * @param double inputMean2   The second mean of the X values. Required for bimodal distributions.
    * @param double inputStDev2   The second standard deviation value for the X values. Required for bimodal distributions.
    */
  public void setMeanStDev (double inputMean1, double inputStDev1, double inputMean2, double inputStDev2)
  {
    this.mean1 = inputMean1;
    this.stDev1 = inputStDev1;
    this.mean2 = inputMean2;
    this.stDev2 = inputStDev2;
  }
  
  /** setEquationChosen method
    * Sets the equationChosen attribute to specified constants.
    * @param int whichEquation   The user's selected equation type.
    */
  public void setEquationChosen (int whichEquation)
  {
    this.equationChosen = whichEquation;
  }
  
  /** setDistributionChosen method
    * Sets the distributionChosen attribute to specified constants.
    * @param int whichDistribution   The user's selected distribution type.
    */
  public void setDistributionChosen (int whichDistribution)
  {
    this.distributionChosen = whichDistribution;
  }
  
  /** setNoisePercent method
    * Sets the percentage of randomness or noise percent.
    * @param double inputNoise     The user's desired noise percentage. Ranges from 0 to 100, and is converted into decimal form.
    */
  public void setNoisePercent (double inputNoise)
  {
    this.noisePercent = inputNoise/100.0;
  }
  
  /** setRange method
    * Sets the range for the X values to generate within.
    * @param double inputMin   The user's desired minimum value.
    * @param double inputMax   The user's desired maximum value.
    */
  public void setRange(double inputMin, double inputMax)
  {
    this.minRange = inputMin;
    this.maxRange = inputMax;
  }
  
  /** setEquation method
   * Formats the equation string, and sets the coefficient values.
   * @param double inputA    The user's desired 'A' coefficient.
   * @param double inputB     The user's desired 'B' coefficient.
   * @param double inputC     The user's desired 'C' coefficient.
   * @param double inputD     The user's desired 'D' coefficient.
   */
  public void setEquation(double inputA, double inputB, double inputC, double inputD)
  {
    this.aValue = inputA;
    this.bValue = inputB;
    this.cValue = inputC;
    this.dValue = inputD;
    if (this.equationChosen == this.LINEAR)
    {
      if (this.bValue < 0)
        this.equation = Double.toString(aValue) + "x "+ Double.toString (bValue);
      else
        this.equation = Double.toString(aValue) + "x + "+ Double.toString (bValue);
    }
    else if (this.equationChosen == this.QUADRATIC)
    {
      if (this.bValue < 0)
      {
        this.equation = Double.toString(aValue) + "x^2 "+ Double.toString (bValue) + "x ";
      }
      else if (this.bValue ==0)
      {
        this.equation = Double.toString(aValue) + "x^2 ";
      }
      else
      {
        this.equation = Double.toString(aValue) + "x^2 + "+ Double.toString (bValue) + "x ";
        
      }
      if (this.cValue > 0)
      {
        this.equation = this.equation + "+ "+Double.toString(cValue);
      }
      else if (this.cValue < 0)
      {
        this.equation = this.equation + Double.toString (cValue);
      }
    }
    else if (this.equationChosen == this.EXPONENTIAL)
    {
      if (this.aValue == 0 || this.bValue == 0)
      {
        this.equation = Double.toString(dValue);
      }
      else
      {
        this.equation = Double.toString(aValue) + "(" + Double.toString(bValue) + ")^(" + Double.toString(cValue) + "x)";
      }
      if (this.dValue > 0)
      {
        this.equation = this.equation + "+ " + Double.toString(dValue);
      }
      else if (this.dValue < 0)
      {
        this.equation = this.equation + Double.toString (dValue);
      }
    }
  }
}