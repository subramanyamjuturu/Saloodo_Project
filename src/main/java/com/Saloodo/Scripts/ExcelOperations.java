package com.Saloodo.Scripts;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;

import com.Saloodo.Scripts.*;
import com.Saloodo.Results.ResultsClass;

public class ExcelOperations 
{
	ResultsClass htmlInt = new ResultsClass();
	public static String TempResultFile;
	public static String TestDescription;
	public static String CurrentFileName;
	public static String MethodName;
	public static int currentExecutionRowNumber;
	public String DataFile;
	//Workbook mastconfigwrkbook =null;


	List <String> listExecutionClasses = new ArrayList<String>(0);
	List <String> listExecutionPackages = new ArrayList<String>(0);
	List <String> listExecutionStatus = new ArrayList<String>(0);
	List <String> listExecutionMethods = new ArrayList<String>(0);
	List <String> listExecutionMethodsDescriptions = new ArrayList<String>(0);
	List <String> listExecutionMethodsNumber = new ArrayList<String>(0);



	String filepath ="com.subbuchinni."; 
	String SrcDataFolder=null;

	String className =null;
	POIFSFileSystem file;

		
	public Map<String,String> readData(String DataSheetName,String MethodName,int SheetRowNumber) throws  IOException
	{
		
		Map<String,String> data= new HashMap();

		try
		{
			//File file= new File(AbsolutePath+"\\"+"masterconfig.xlsx");

			FileInputStream inputStream = new FileInputStream(DataSheetName);

			HSSFWorkbook wrkbook= new HSSFWorkbook(inputStream);
			
			HSSFSheet DataExcelSheet = wrkbook.getSheet(MethodName);
			
			int rows =DataExcelSheet.getLastRowNum();
			HSSFRow RowValue;
			HSSFRow row =DataExcelSheet.getRow(0);
			
			RowValue= DataExcelSheet.getRow(SheetRowNumber);
			
			for(int i=0;i<row.getLastCellNum();i++)
			{
				RowValue.getCell(i).setCellType(RowValue.getCell(i).CELL_TYPE_STRING);
				
				String Value=RowValue.getCell(i).getStringCellValue();
				
				if(!Value.equalsIgnoreCase(""))
				{
					String KeyValue=DataExcelSheet.getRow(0).getCell(i).getStringCellValue();
					
					data.put(KeyValue, Value);
				}
				
			}
			
			
			//mastconfigwrkbook = new HSSFWorkbook(inputStream);
		}catch(Exception e)
		{
			
			System.out.println(e.getMessage());
		}

		return data;

	}

}
