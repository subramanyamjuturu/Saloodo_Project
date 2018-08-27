package com.Saloodo.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Saloodo.Actions.Action;
import com.Saloodo.Results.ResultsClass;
import com.Saloodo.Scripts.*;

public class LoginFormE2E {

	public static String strAbsolutePath =new File("").getAbsolutePath();
	public static String ResultPath = strAbsolutePath+"\\Results";
	public static String DataPath = strAbsolutePath+"\\data\\";
	public static String DataFile = DataPath+"SaloodoTask_Data.xls";
	public static String TempResultFile;
	public static String TempResult=ResultPath+"\\TemproryResultFile";
	static String ObjectProprty;
	static String ObjectProprtyValue;

	static ResultsClass Report = new ResultsClass();
	static Action actions = new Action();
	static ExcelOperations fileoperations = new ExcelOperations();

	public static void main(String[] args) throws Exception 
	{
		ScriptExecuter.storeObjectProperteyValues();

		ScriptExecuter.storeObjectProperties();

		// getting object properties and driver details from Data sheet and storing in Collections
		Map<String,String> data =fileoperations.readData(DataPath+"SaloodoTask_Data.xls", "LoginFormE2E", 1);
		Map<String,String> ResourceDetails =fileoperations.readData(DataPath+"SaloodoTask_Data.xls", "DriverDetails", 1);
		
		System.out.println(ResourceDetails.get("ChromePath"));
		// setting Path for the Driver so that Chrome driver server communicates with our code and executes in browser
		System.setProperty("webdriver.chrome.driver",ResourceDetails.get("ChromePath"));

		//Launching chrome Browser
		WebDriver driver = new ChromeDriver();

		//Mathematical Random  number function just for external Report purpose
		Random rand = new Random();
		double  n = rand.nextInt(50)*1.13;

		//This line is for saving result in Result folder In our project location
		TempResultFile =TempResult+n+".htm";

		//This is for external Report for the Automation execution and it contains detailed step by step execution line which we performed in automation including with screenshots
		Report.HTMLInitialisation(TempResultFile, ResultPath);

		//This is for Launching Saloodo site and inside this method Detailed Report code is available.
		actions.browserLaunch(data.get("URL_Data"), driver, TempResultFile, "Launching Browser");
		// getting language from UI
		String Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

		System.out.println("Language--"+Language);

		String LangArry [] = data.get("LanguageDropDown").split(";");

		for(int j=0;j<LangArry.length;j++)
		{
			// getting language from UI
			Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

			if(!Language.equalsIgnoreCase("DEUTSCH") && j==0)
			{
				// clicking on language drop down
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Laguage DropDown");
				//getting object properties from Collectiuons
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
				// selecting language
				actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

				System.out.println(LangArry[j]);
				// getting language from UI
				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

				System.out.println("Language## "+Language);
			}
			else if(j>0)
			{
				// clicking on language dropdown
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Language Drop Down");
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
				// selecting language
				actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

				System.out.println(LangArry[j]);
				// getting language from UI
				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

				System.out.println("Language## "+Language);
			}

			System.out.println("Language## "+Language);

			List <String> listLangErrorMsg = new ArrayList<String>();

			String RegistratationArryContent [] = data.get(LangArry[j]+"_ErrorMsgs").split("###");
			for(int k=0;k<RegistratationArryContent.length;k++)
			{
				listLangErrorMsg.add(RegistratationArryContent[k]);
			}
			int var = 0;
			//clicking on Login Button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			//Verifying Email Error Msg
			actions.VerifyText("EmailErrorMsg", "EmailErrorMsg", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Email Error Msg");
			//Verifying Password Error Msg
			actions.VerifyText("PasswordErrorMsg", "PasswordErrorMsg", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Password Error Msg");
			
			//Entering Email Id
			actions.enterText("EmailId", "EmailId", data.get("EmailId"), driver, TempResultFile, "Entering Email Id");
			//clicking on Login Button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			//Verifying Password Error Msg
			actions.VerifyText("PasswordErrorMsg", "PasswordErrorMsg", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Password Error Msg");
			
			//clearing Entered Email Id
			driver.findElement(By.name("_email")).clear();
			//Entering Email Id
			actions.enterText("Password", "Password", data.get("Password"), driver, TempResultFile, "Entering password");
			//clicking on Login Button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			//Verifying Email Error Msg
			actions.VerifyText("EmailErrorMsg", "EmailErrorMsg", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Email Error Msg");
			
			// clearing entered password
			driver.findElement(By.name("_password")).clear();
			//Entering Email Id
			actions.enterText("EmailId", "EmailId", data.get("EmailId"), driver, TempResultFile, "Entering Email Id");
			//Entering incorrect Password
			actions.enterText("Password", "Password", data.get("IncorrectPassword"), driver, TempResultFile, "Entering Password");
			//clicking on Login Button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			//Verifying Error Msg for Incorrect Details
			actions.VerifyText("IncorrectDetailsError", "IncorrectDetailsError", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Error Msg for Incorrect Details");
			
			// clearing entered email id and password
			driver.findElement(By.name("_email")).clear();
			driver.findElement(By.name("_password")).clear();
			//Entering incorrect Email Id
			actions.enterText("EmailId", "EmailId", data.get("IncorrecrEmailId"), driver, TempResultFile, "Entering Email Id");
			//Entering password
			actions.enterText("Password", "Password", data.get("Password"), driver, TempResultFile, "Entering password");
			//clicking on Login Button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			//Verifying Error Msg for Incorrect Details
			actions.VerifyText("IncorrectDetailsError", "IncorrectDetailsError", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Error Msg for Incorrect Details");
			
			// clearing entered email id and password
			driver.findElement(By.name("_email")).clear();
			driver.findElement(By.name("_password")).clear();
			//Entering incorrect Email Id
			actions.enterText("EmailId", "EmailId", data.get("IncorrecrEmailId"), driver, TempResultFile, "Entering Email Id");
			//Entering IncorrectPassword
			actions.enterText("Password", "Password", data.get("IncorrectPassword"), driver, TempResultFile, "Entering password");
			//clicking on Login Button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			//Verifying Error Msg for Incorrect Details
			actions.VerifyText("IncorrectDetailsError", "IncorrectDetailsError", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Error Msg for Incorrect Details");

			System.out.println();
		}


		for(int j=0;j<LangArry.length;j++)
		{
			// getting language from UI
			Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

			if(!Language.equalsIgnoreCase("DEUTSCH") && j==0)
			{
				//clicking on Language DropDown
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Language DropDown");
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
				// selecting language
				actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

				System.out.println(LangArry[j]);
				
				// getting language from UI
				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

				System.out.println("Language## "+Language);
			}
			else if(j>0)
			{
				//clicking on Language Drop Down
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Language Drop Down");
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
				// selecting language
				actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

				System.out.println(LangArry[j]);
				
				// getting language from UI
				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

				System.out.println("Language## "+Language);
			}
			
			//Entering Email Id
			actions.enterText("EmailId", "EmailId", data.get("EmailId"), driver, TempResultFile, "Entering Email Id");
			//Entering password
			actions.enterText("Password", "Password", data.get("Password"), driver, TempResultFile, "Entering password");
			//clicking on Login Button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			//Verifying User is logged to application
			actions.VerifyText("LoggedUserProfile", "LoggedUserProfile", data.get("LoggedUserProfile"), driver, TempResultFile, "Verifying User is logged to application");
			//clicking on Logout
			actions.Click("Logout", "Logout", "Logout", driver, TempResultFile, "clicking on Logout");


		}

		Report.FinalResultWrite(TempResultFile,"LoginFormE2E",DataFile);
		Report.InsertOverallStatus(TempResultFile,"LoginFormE2E",DataFile);


	}

}
