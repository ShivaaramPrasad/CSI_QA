package database;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Event_Regtype_Rate {
    static  WebDriver driver;
	static String username="shivaaram"; static String password = "Shivaa.125";
	static String excelPath="AAST19PSM";
	static String sheetName="Reg Types";
	static int xlReg=0; static int rowNum=0;
	
	static String showName="aast19"; static int Tier=3; 
	static String earlyDate="2018-01-01 00:00:00.0"; static int xlOne =8;
	static String advanceDate="2019-08-20 00:00:00.0"; static int xlTwo =9;
	static String onsiteDate="2019-09-18 00:00:00.0"; static int xlThree=10;
	static String nondefaultDate="2001-01-01 00:00:00.0"; static int xlNon=11;

	public static void main(String[] args) throws ParseException, IOException {
	
		XSSFWorkbook wbook= new XSSFWorkbook("./PSM/"+excelPath+".xlsx");
		XSSFSheet sheet= wbook.getSheet(sheetName);
		DataFormatter formatter = new DataFormatter();
		int rowCount = sheet.getLastRowNum();
		System.out.println("Number of Rows "+rowCount);
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.compusystems.com/jsp/toolMain.jsp");
		driver.switchTo().frame("main");
		driver.findElement(By.xpath("(//input[@name='login'])[1]")).sendKeys(username);
		driver.findElement(By.name("Password")).sendKeys(password);
		driver.findElement(By.name("action")).click();
		driver.switchTo().defaultContent();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.switchTo().frame("main");
		driver.findElement(By.xpath("//a[contains(text(),'Database, WebSite, and Registration tools')]")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("main");
		driver.findElement(By.linkText("Select DB")).click();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//a[contains(text(),'"+showName+"')]")).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[contains(text(),'event_regtype_rate')]")).click();
		
		//x<rowCount of excel
		//x=0 determines starting of the row 

		for(int x =rowNum; x<rowCount; x++){


			XSSFCell  regType= sheet.getRow(x).getCell(xlReg);
			String regTypeValue = formatter.formatCellValue(regType);

			switch (Tier) {

			case 1:

				XSSFCell  excelFirstrate= sheet.getRow(x).getCell(xlOne);
				String FirstrateValue = excelFirstrate.toString();
				String trimFirstrateValue = FirstrateValue.replaceAll("$", "");
				Float trimedFirstfloat = Float.parseFloat(trimFirstrateValue);
				DecimalFormat dfone = new DecimalFormat("0.00");
				dfone.setMaximumFractionDigits(2);
				trimedFirstfloat = Float.parseFloat(dfone.format(trimedFirstfloat));
				System.out.println("Executing Firstrate tire for regtype "+regTypeValue);
				System.out.println("PSM rate "+trimedFirstfloat);
				driver.findElement(By.xpath("(//input[@name='clause'])[1]")).clear();			
				driver.findElement(By.xpath("(//input[@name='clause'])[1]")).sendKeys("regtype_evtcode='"+regTypeValue+"'and start_date='"+earlyDate+"'");	
				driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).clear();
				driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).sendKeys("0");
				driver.findElement(By.xpath("//input[@type='submit']")).click();
				WebElement tableOne = driver.findElement(By.xpath("//form[@name='col_form']//table/tbody[1]"));
				List<WebElement> rowone = tableOne.findElements(By.tagName("tr"));
				int rowOneSize = rowone.size();
				//System.out.println(rowOneSize);
				if(rowOneSize<2)
				{
					System.out.println("First rate is not programed for "+regTypeValue);

				}
				else {
					String rate = driver.findElement(By.xpath("//tr[2]//td[5]")).getText();
					Float rateFirstfloat = Float.parseFloat(rate);
					DecimalFormat dfw = new DecimalFormat("0.00");
					dfw.setMaximumFractionDigits(2);
					rateFirstfloat = Float.parseFloat(dfw.format(rateFirstfloat));
					System.out.println("Web rate"+rateFirstfloat);
					if (rateFirstfloat.equals(trimedFirstfloat) )
					{
						System.out.println("Excel rate is matching with web rate");
					}
					else
					{
						System.out.println("Excel rate is not matching with web rate");
					}}

				break;

			case 2:

				XSSFCell  excelSecondrate= sheet.getRow(x).getCell(xlTwo);
				String secondrateValue = excelSecondrate.toString();
				String trimSecondrateValue = secondrateValue.replaceAll("$", "");
				Float trimedSecondfloat = Float.parseFloat(trimSecondrateValue);
				DecimalFormat df = new DecimalFormat("0.00");
				df.setMaximumFractionDigits(2);
				trimedSecondfloat = Float.parseFloat(df.format(trimedSecondfloat));
				System.out.println("Executing secondrate tire for regtype "+regTypeValue);
				System.out.println("PSM rate "+trimedSecondfloat);
				driver.findElement(By.xpath("(//input[@name='clause'])[1]")).clear();			
				driver.findElement(By.xpath("(//input[@name='clause'])[1]")).sendKeys("regtype_evtcode='"+regTypeValue+"'and start_date='"+advanceDate+"'");	
				driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).clear();
				driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).sendKeys("0");
				driver.findElement(By.xpath("//input[@type='submit']")).click();
				WebElement tableTwo = driver.findElement(By.xpath("//form[@name='col_form']//table/tbody[1]"));
				List<WebElement> rowTwo = tableTwo.findElements(By.tagName("tr"));
				int rowTwoSize = rowTwo.size();
				//System.out.println(rowsize);
				if(rowTwoSize<2)
				{
					System.out.println("Second rate is not programed for "+regTypeValue);

				}
				else {
					String rate = driver.findElement(By.xpath("//tr[2]//td[5]")).getText();
					Float rateSecondfloat = Float.parseFloat(rate);
					DecimalFormat dfw = new DecimalFormat("0.00");
					dfw.setMaximumFractionDigits(2);
					rateSecondfloat = Float.parseFloat(dfw.format(rateSecondfloat));
					System.out.println("Web rate "+rateSecondfloat);
					if (rateSecondfloat.equals(trimedSecondfloat) )
					{
						System.out.println("Excel rate is matching with web rate");
					}
					else
					{
						System.out.println("Excel rate is not matching with web rate");
					}}

				break;


			case 3:

				XSSFCell  excelThirdrate= sheet.getRow(x).getCell(xlThree);
				String ThirdrateValue = excelThirdrate.toString();
				String trimThirdrateValue = ThirdrateValue.replaceAll("$", "");
				Float trimedThirdfloat = Float.parseFloat(trimThirdrateValue);
				DecimalFormat dfthree = new DecimalFormat("0.00");
				dfthree.setMaximumFractionDigits(2);
				trimedThirdfloat = Float.parseFloat(dfthree.format(trimedThirdfloat));
				System.out.println("Executing Thirdrate tire for regtype "+regTypeValue);
				System.out.println("PSM rate "+trimedThirdfloat);
				driver.findElement(By.xpath("(//input[@name='clause'])[1]")).clear();			
				driver.findElement(By.xpath("(//input[@name='clause'])[1]")).sendKeys("regtype_evtcode='"+regTypeValue+"'and start_date='"+onsiteDate+"'");	
				driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).clear();
				driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).sendKeys("0");
				driver.findElement(By.xpath("//input[@type='submit']")).click();
				WebElement tablethree = driver.findElement(By.xpath("//form[@name='col_form']//table/tbody[1]"));
				List<WebElement> rowthree = tablethree.findElements(By.tagName("tr"));
				int rowthreesize = rowthree.size();
				//System.out.println(rowthreesize);
				if(rowthreesize<2)
				{
					System.out.println("Third rate is not programed for "+regTypeValue);

				}
				else {
					String rate = driver.findElement(By.xpath("//tr[2]//td[5]")).getText();
					Float rateThirdfloat = Float.parseFloat(rate);
					DecimalFormat dfw = new DecimalFormat("0.00");
					dfw.setMaximumFractionDigits(2);
					rateThirdfloat = Float.parseFloat(dfw.format(rateThirdfloat));
					System.out.println("Web rate "+rateThirdfloat);
					if (rateThirdfloat.equals(trimedThirdfloat) )
					{
						System.out.println("Excel rate is matching with web rate");
					}
					else
					{
						System.out.println("Excel rate is not matching with web rate");
					}}
				break;

			case 4:

				XSSFCell excelNonrate= sheet.getRow(x).getCell(xlNon);
				String nonrateValue = excelNonrate.toString();
				String trimNonrateValue = nonrateValue.replaceAll("$", "");
				Float trimedNonfloat = Float.parseFloat(trimNonrateValue);
				DecimalFormat dfNon = new DecimalFormat("0.00");
				dfNon.setMaximumFractionDigits(2);
				trimedNonfloat = Float.parseFloat(dfNon.format(trimedNonfloat));
				System.out.println("Executing Nonrate tire for regtype "+regTypeValue);
				System.out.println("PSM rate "+trimedNonfloat);
				driver.findElement(By.xpath("(//input[@name='clause'])[1]")).clear();			
				driver.findElement(By.xpath("(//input[@name='clause'])[1]")).sendKeys("regtype_evtcode='"+regTypeValue+"'and start_date='"+nondefaultDate+"'");	
				driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).clear();
				driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).sendKeys("0");
				driver.findElement(By.xpath("//input[@type='submit']")).click();
				WebElement tableNon = driver.findElement(By.xpath("//form[@name='col_form']//table/tbody[1]"));
				List<WebElement> rowNon = tableNon.findElements(By.tagName("tr"));
				int rowNonSize = rowNon.size();
				//System.out.println(rowNonsize);
				if(rowNonSize<2)
				{
					System.out.println("Non Defalut rate is not programed for "+regTypeValue);

				}
				else {
					String rate = driver.findElement(By.xpath("//tr[2]//td[5]")).getText();
					Float rateNonfloat = Float.parseFloat(rate);
					DecimalFormat dfw = new DecimalFormat("0.00");
					dfw.setMaximumFractionDigits(2);
					rateNonfloat = Float.parseFloat(dfw.format(rateNonfloat));
					System.out.println("Web rate"+rateNonfloat);
					if (rateNonfloat.equals(trimedNonfloat) )
					{
						System.out.println("Excel rate is matching with web rate");
					}
					else
					{
						System.out.println("Excel rate is not matching with web rate");
					}}

				break;

			default:

				System.err.println("Please choose the correct rate tire");
				break;

			}


		}
		wbook.close();
		driver.close();
		driver.quit();
		System.exit(1);
	}
}

