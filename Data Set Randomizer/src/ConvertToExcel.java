/** ConvertToExcel Class
*    writes an excel file with the given variables
*    Last Modified: 6/18/2016
*   @author Michael C.
*/
import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ConvertToExcel extends Object
{
  //Variable Declarations
  private DSRModel model;                                   //The Main Model
  private HSSFWorkbook workbook = new HSSFWorkbook();       //WorkBook being written To
  private double [] xValues;                                   //x values to be outprinted
  private double [] yValues;                                   //y values to be outprinted
  private String fileName;                                  //fileName on the excel file       
  private String heading1;
  private String heading2;
        
  
  /** Creates a default ConvertToExcel where there is 1 variable
    * @param newModel - is the model provide from the DSR Model Class
    * @param newFile - is a file name the user wants 
    * @param int []x - x values to be exported
    */
  public ConvertToExcel(DSRModel newModel,String newFile,double [] x)throws IOException
  {
    super();
    this.model = newModel;
    this.fileName =  newFile+ ".xls";
    this.heading1 = this.model.getHeading1();
    this.xValues = new double [x.length];
    //copys the x values     
    System.arraycopy(x,0,this.xValues,0,x.length);
  }
  
  /** Creates a default ConvertToExcel where there is 2 variable
    * @param newModel - is the model provide from the DSR Model Class
    * @param newFile - is a file name the user wants 
    * @param int []x - x values to be exported
    * @param int []y - y values to be exported
    */
  public ConvertToExcel(DSRModel newModel,String newFile,double [] x,double [] y)throws IOException
  {
    super();
    this.model = newModel;
    this.fileName = newFile+ ".xls";
    this.heading1 = this.model.getHeading1();
    this.heading2 = this.model.getHeading2();
    this.xValues = new double [x.length];
    this.yValues = new double [y.length];
    //copys the x values and y values  
    System.arraycopy(x,0,this.xValues,0,x.length);
    System.arraycopy(y,0,this.yValues,0,y.length);
  }
  
  /** exportOneVariable method for exporting only 1 variable
    */
  public void exportOneVariable()throws IOException
  {
    //makes a excel sheet
    HSSFSheet sheet = workbook.createSheet(this.fileName);
    
    //adds heading to the rows
    HSSFRow headingRow = sheet.createRow(0);
    HSSFCell heading1cell = headingRow.createCell(0);
    heading1cell.setCellValue(this.heading1);
    
    //creates a row and cell in the first column and inputs var 1
    for(int x = 1; x<xValues.length+1;x++)
    {
      HSSFRow row = sheet.createRow(x);
      HSSFCell cell = row.createCell(0);
      
      cell.setCellValue(this.xValues[x-1]);
    }
    this.writeFile();
  }
  
  /** exportTwoVariable method for exporting 2 variable
    */
  public void exportTwoVariable()throws IOException
  {
    //makes a excel sheet
    HSSFSheet sheet = workbook.createSheet("First");
    
    //setting headings
    HSSFRow headingRow = sheet.createRow(0);
    HSSFCell heading1Cell = headingRow.createCell(0);
    HSSFCell heading2Cell = headingRow.createCell(1);
    heading2Cell.setCellValue(this.heading2);
    heading1Cell.setCellValue(this.heading1);
    
    //creates 2 row and cell in the first column and second column and inputs var 1 & 2
    for(int x = 1; x<xValues.length+1;x++)
    {
      HSSFRow row = sheet.createRow(x);    
      HSSFCell cell = row.createCell(0);
      HSSFCell cell2 = row.createCell(1);

      cell.setCellValue(this.xValues[x-1]);
      cell2.setCellValue(this.yValues[x-1]);
      this.writeFile();
    }
  }
  
  /** writeFile method is a helper method to write the file
    */
  private void writeFile()throws IOException
  {
    FileOutputStream file = new FileOutputStream(fileName);
    workbook.write(file);
    file.close();
    workbook.close();   
  }
}