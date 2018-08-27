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

public class SaloodoGUIE2ETask {

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


		Map<String,String> data =fileoperations.readData(DataPath+"SaloodoTask_Data.xls", "SaloodoGUIE2ETask", 1);
		Map<String,String> ResourceDetails =fileoperations.readData(DataPath+"SaloodoTask_Data.xls", "DriverDetails", 1);
		// setting Path for the Driver so that Chrome driver server communicates with our code and executes in browser
		System.out.println(ResourceDetails.get("ChromePath"));
		
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

		String Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

		System.out.println("Language--"+Language);
		
		//This step is store all languages into array
		String LangArry [] = data.get("LanguageDropDown").split(";");

		for(int j=0;j<LangArry.length;j++)
		{
			// to get the default pre selected language fro UI
			Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

			if(!Language.equalsIgnoreCase("DEUTSCH") && j==0)
			{
				//selecting Language
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Laguage DropDown");
				//This two lines are to get the object properties from Data sheet
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
				actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

				System.out.println(LangArry[j]);

				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

				System.out.println("Language## "+Language);
			}
			else if(j>0)
			{
				//selecting Language
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Language Drop Down");
				//This two lines are to get the object properties from Data sheet
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
				actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

				System.out.println(LangArry[j]);

				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

				System.out.println("Language## "+Language);
			}

			System.out.println("Language## "+Language);
			
			// Getting all error messages from Data sheet and storing into collections
			List <String> listLangErrorMsg = new ArrayList<String>();

			String RegistratationArryContent [] = data.get(LangArry[j]+"_ErrorMsgs").split("###");
			for(int k=0;k<RegistratationArryContent.length;k++)
			{
				listLangErrorMsg.add(RegistratationArryContent[k]);
			}
			int var = 0;
			
			// clicking on login button without entering login details
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			// verifying Email error message
			actions.VerifyText("EmailErrorMsg", "EmailErrorMsg", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Email Error Msg");
			// verifying Password error message
			actions.VerifyText("PasswordErrorMsg", "PasswordErrorMsg", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Password Error Msg");
			
			//Entering email id
			actions.enterText("EmailId", "EmailId", data.get("EmailId"), driver, TempResultFile, "Entering Email Id");
			// clicking on login button with email id
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			// verifying Password error message
			actions.VerifyText("PasswordErrorMsg", "PasswordErrorMsg", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Password Error Msg");

			// clearing entered email id
			driver.findElement(By.name("_email")).clear();
			// entering password
			actions.enterText("Password", "Password", data.get("Password"), driver, TempResultFile, "Entering Password");
			// clicking on login button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			// verifying Email error message
			actions.VerifyText("EmailErrorMsg", "EmailErrorMsg", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Email Error Msg");
			
			// clearing entered Password
			driver.findElement(By.name("_password")).clear();
			// entering email id
			actions.enterText("EmailId", "EmailId", data.get("EmailId"), driver, TempResultFile, "Entering Email Id");
			// entering incorrect password
			actions.enterText("Password", "Password", data.get("IncorrectPassword"), driver, TempResultFile, "Entering Password");
			// cllicking on login button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			// Verifying error message
			actions.VerifyText("IncorrectDetailsError", "IncorrectDetailsError", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Error Msg for Incorrect Details");
			
			// clearing entered emai and password
			driver.findElement(By.name("_email")).clear();
			driver.findElement(By.name("_password")).clear();
			actions.enterText("EmailId", "EmailId", data.get("IncorrecrEmailId"), driver, TempResultFile, "Entering Email Id");
			// entering password
			actions.enterText("Password", "Password", data.get("Password"), driver, TempResultFile, "Entering Password");
			// clicking on login password
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			// verifying error message
			actions.VerifyText("IncorrectDetailsError", "IncorrectDetailsError", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Error Msg for Incorrect Details");
			
			// clearing entered emai and password
			driver.findElement(By.name("_email")).clear();
			driver.findElement(By.name("_password")).clear();
			// entering in correct email id
			actions.enterText("EmailId", "EmailId", data.get("IncorrecrEmailId"), driver, TempResultFile, "Entering Email Id");
			// entering password
			actions.enterText("Password", "Password", data.get("IncorrectPassword"), driver, TempResultFile, "Password");
			// clicking on login button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			// verifying error message
			actions.VerifyText("IncorrectDetailsError", "IncorrectDetailsError", listLangErrorMsg.get(var++), driver, TempResultFile, "Verifying Error Msg for Incorrect Details");

		}


		for(int j=0;j<LangArry.length;j++)
		{
			// geting laguage from UI 
			Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

			if(!Language.equalsIgnoreCase("DEUTSCH") && j==0)
			{
				//clicking on laguage drop down
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Language DropDown");
				// getting object properties from Data sheet
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
				// selecting language
				actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

				System.out.println(LangArry[j]);
				
				// geting laguage from UI 
				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

				System.out.println("Language## "+Language);
			}
			else if(j>0)
			{
				// Clicking on language drop down
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Language Drop Down");
				// geting object properties from data sheet
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
				// Selecting laguage in UI 
				actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

				System.out.println(LangArry[j]);
				// geting laguage from UI 
				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

				System.out.println("Language## "+Language);
			}
			
			// entering emai id
			actions.enterText("EmailId", "EmailId", data.get("EmailId"), driver, TempResultFile, "Entering Email Id");
			// entering password
			actions.enterText("Password", "Password", data.get("Password"), driver, TempResultFile, "Entering Email Id");
			// clicking on login button
			actions.Click("LoginButton", "LoginButton", "Login Button", driver, TempResultFile, "clicking on Login Button");
			// verfying user logged to UI
			actions.VerifyText("LoggedUserProfile", "LoggedUserProfile", data.get("LoggedUserProfile"), driver, TempResultFile, "Verifying User is logged to application");
			// clicking on log out
			actions.Click("Logout", "Logout", "Logout", driver, TempResultFile, "clicking on Logout");


		}

		for(int i=0;i<LangArry.length;i++)
		{
			// geting laguage from UI 
			Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();
			
			if(!Language.equalsIgnoreCase("DEUTSCH") && i==0)
			{
				// Clicking on language dropdown
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on login button");
				// getting object properties from data sheet
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", "DEUTSCH");
				// selecting Deutsch language
				driver.findElement(By.xpath(ObjectProprtyValue)).click();
				// geting laguage from UI 
				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

			}
			else if(i>0)
			{
				// clikcing on language drop down
				actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Language Drop Down");
				
				// getting object properties from data sheet
				ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[i]);
				// selecting langauge
				actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[i], driver, TempResultFile, "Selecting Language--"+LangArry[i]);
				// geting laguage from UI 
				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();
			}

			System.out.println("Language--"+LangArry[i]);
			//Verifying Text Chat Info Label
			actions.VerifyText("ChatInfoLabel", "ChatInfoLabel", data.get(Language+"_ChatInfoLabel"), driver, TempResultFile, "Verifying Text Chat Info Label");
			//Verifying Text Registratation DropDown Label
			actions.VerifyText("RegistratationDropDown", "RegistratationDropDown", data.get(Language+"_RegistratationDropDown"), driver, TempResultFile, "Verifying Text Registratation DropDown Label");
			//Verifying Text LoginButtonMainOption Label
			actions.VerifyText("LoginButtonMainOption", "LoginButtonMainOption", data.get(Language+"_LoginButtonMainOption"), driver, TempResultFile, "Verifying Text LoginButtonMainOption Label");
			//Verifying Text LanguageDisplay Label
			actions.VerifyText("LanguageDisplayLabel", "LanguageDisplayLabel", LangArry[i].toUpperCase(), driver, TempResultFile, "Verifying Text LanguageDisplay Label");
			//Verifying Text LoginHeadder Label
			actions.VerifyText("LoginHeadderLabel", "LoginHeadderLabel", data.get(Language+"_LoginHeadderLabel"), driver, TempResultFile, "Verifying Text LoginHeadder Label");
			//Verifying Text Rememberme Label
			actions.VerifyText("RemembermeLabel", "RemembermeLabel", data.get(Language+"_RemembermeLabel"), driver, TempResultFile, "Verifying Text Rememberme Label");
			//Verifying Text ForgotPassword Label
			actions.VerifyText("ForgotPassword", "ForgotPassword", data.get(Language+"_ForgotPassword"), driver, TempResultFile, "Verifying Text ForgotPassword Label");
			//Verifying Text CookiesLabel Label
			actions.VerifyText("CookiesLabel", "CookiesLabel", data.get(Language+"_CookiesLabel"), driver, TempResultFile, "Verifying Text CookiesLabel Label");

			System.out.println();

		}

		String RegistratationArry [] = data.get("RegistratationDropDown").split(";");
		int x;

		for(int i=0;i<RegistratationArry.length;i++)
		{
			//clicking on registration dropdown
			actions.Click("RegistratationDropDown", "RegistratationDropDown", "Registratation DropDown", driver, TempResultFile, "clicking on Registratation DropDown");

			x=i;
			//getting object properties from Data sheet
			ObjectProprty=ScriptExecuter.Objpropertey.get("RegistratationDropDownList");
			ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("RegistratationDropDownList").replace("DUMMY", Integer.toString(++x));
			// selecing registratation
			actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting RegistratationDropDownList--"+RegistratationArry[i], driver, TempResultFile, "Selecting RegistratationDropDownList--"+RegistratationArry[i]);

			System.out.println("Registratation--"+RegistratationArry[i]);

			for(int j=0;j<LangArry.length;j++)
			{
				//getting language from UI
				Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

				if(!Language.equalsIgnoreCase("DEUTSCH") && j==0)
				{	
					// clicking on language dropdown
					actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on login button");
					
					// getting object properties from UI
					ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
					ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
					//selectinglanguage
					actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

					System.out.println(LangArry[j]);
					
					// getting language from UI
					Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

					System.out.println("Language## "+Language);
				}
				else if(j>0)
				{
					// clicking on language drop down
					actions.Click("LanguageDisplayLabel", "LanguageDisplayLabel", "LanguageDisplayLabel", driver, TempResultFile, "clicking on Language Drop Down");
					// getting object properties from data sheet
					ObjectProprty=ScriptExecuter.Objpropertey.get("LanguageDropDownSelection");
					ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("LanguageDropDownSelection").replace("DUMMY", LangArry[j]);
					// selecting language
					actions.Select(ObjectProprty, ObjectProprtyValue, "Selecting Language--"+LangArry[j], driver, TempResultFile, "Selecting Language--"+LangArry[j]);

					System.out.println(LangArry[j]);
					// getting language from UI
					Language = driver.findElement(By.xpath("//div[contains(@class,'header__lang-switcher')]//a[contains(@class,'dropdown')]")).getText();

					System.out.println("Language## "+Language);
				}
				//getting registratation options from dsta sheet and storing in collections
				List <String> listRegistratationContent = new ArrayList<String>();

				if(RegistratationArry [i].equalsIgnoreCase("Register Your Company"))
				{
					String RegistratationArryContent [] = data.get(LangArry[j]+"_RegisterYourCopmany").split("###");
					for(int k=0;k<RegistratationArryContent.length;k++)
					{
						listRegistratationContent.add(RegistratationArryContent[k]);
					}
				}
				else if(RegistratationArry [i].equalsIgnoreCase("Register as a Carrier"))
				{
					String RegistratationArryContent [] = data.get(LangArry[j]+"_RegisterasaCarrer").split("###");
					for(int k=0;k<RegistratationArryContent.length;k++)
					{
						listRegistratationContent.add(RegistratationArryContent[k]);
					}
				}

				System.out.println(LangArry[j]);
				int var = 0;
				//Verifying Text of Registratation Headder
				actions.VerifyText("RegistratationHeadder", "RegistratationHeadder", listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of Registratation Headder");
				//Verifying Text of VideoContentText
				actions.VerifyText("VideoContentText", "VideoContentText", listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of VideoContentText");
				//Verifying Text of CountOfTheContent
				actions.VerifyText("CountOfTheContent", "CountOfTheContent", listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of CountOfTheContent");
				//Verifying Text of NewsLetterContent
				actions.VerifyText("NewsLetterContent", "NewsLetterContent", listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of NewsLetterContent");
				//Verifying Text of TermsContent
				actions.VerifyText("TermsContent", "TermsContent", listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of TermsContent");
				//Verifying Text of Middle Section Content
				actions.VerifyText("MiddleSectionContent", "MiddleSectionContent", listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of Middle Section Content");
				//getting object properties from data sheet
				ObjectProprty=ScriptExecuter.Objpropertey.get("Steps");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("Steps").replace("DUMMY", "1");
				//Verifying Text of Step 1
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of Step 1");

				ObjectProprty=ScriptExecuter.Objpropertey.get("Steps");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("Steps").replace("DUMMY", "2");
				//Verifying Text of Step 2
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of Step 2");

				ObjectProprty=ScriptExecuter.Objpropertey.get("Steps");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("Steps").replace("DUMMY", "3");
				//Verifying Text of Step 3
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of Step 3");

				ObjectProprty=ScriptExecuter.Objpropertey.get("FeatureContent");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("FeatureContent").replace("DUMMY", "1");
				//Verifying Text of FeatureContent 1
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of FeatureContent 1");

				ObjectProprty=ScriptExecuter.Objpropertey.get("FeatureContent");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("FeatureContent").replace("DUMMY", "2");
				//Verifying Text of FeatureContent 2
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of FeatureContent 2");

				ObjectProprty=ScriptExecuter.Objpropertey.get("FeatureContent");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("FeatureContent").replace("DUMMY", "3");
				//Verifying Text of FeatureContent 3
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of FeatureContent 3");

				ObjectProprty=ScriptExecuter.Objpropertey.get("BottomContent");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("BottomContent").replace("DUMMY", "1");
				//Verifying Text of BottomContent 1
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of BottomContent 1");

				ObjectProprty=ScriptExecuter.Objpropertey.get("BottomContent");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("BottomContent").replace("DUMMY", "2");
				//Verifying Text of BottomContent 2
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of BottomContent 2");

				ObjectProprty=ScriptExecuter.Objpropertey.get("BottomContent");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("BottomContent").replace("DUMMY", "3");
				//Verifying Text of BottomContent 3
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of BottomContent 3");
				//Verifying Text of FooterContent
				actions.VerifyText("FooterContent", "FooterContent", listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of FooterContent");
				//Verifying Text of BottomRegistrationButton
				actions.VerifyText("BottomRegistrationButton", "BottomRegistrationButton", listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of BottomRegistrationButton");

				ObjectProprty=ScriptExecuter.Objpropertey.get("ContactUsInfo");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("ContactUsInfo").replace("DUMMY", "1");
				//Verifying Text of ContactUsInfo 1
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of ContactUsInfo 1");

				ObjectProprty=ScriptExecuter.Objpropertey.get("ContactUsInfo");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("ContactUsInfo").replace("DUMMY", "2");
				//Verifying Text of ContactUsInfo 2
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of ContactUsInfo 2");

				ObjectProprty=ScriptExecuter.Objpropertey.get("ContactUsInfo");
				ObjectProprtyValue=ScriptExecuter.ObjproperteyValue.get("ContactUsInfo").replace("DUMMY", "3");
				//Verifying Text of ContactUsInfo 3
				actions.VerifyTextDirectObjProperty(ObjectProprty, ObjectProprtyValue, listRegistratationContent.get(var++), driver, TempResultFile, "Verifying Text of ContactUsInfo 3");

				System.out.println();

				System.out.println("##############################");

			}

			System.out.println("##############################");
		}


		
		Report.FinalResultWrite(TempResultFile,"SaloodoGUIE2ETask",DataFile);
		Report.InsertOverallStatus(TempResultFile,"SaloodoGUIE2ETask",DataFile);


	}

}
