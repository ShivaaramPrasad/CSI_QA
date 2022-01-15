package database;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Regtype_X_Option_Detail {
    static  WebDriver driver;
	static String userName="shivaaram"; static String password="Shivaa.125";
	static String excelPath="AAST19PSM";
	static String sheetName="Reg Types";
	static int xlOpt=15; static int xlReg=0; static int rowNum=0;
	static String showName="aast19";
	
	
	public static void main(String[] args) throws IOException {

		Map< String,Integer> excelMap = new LinkedHashMap< String,Integer>(); 
		XSSFWorkbook wbook= new XSSFWorkbook("./PSM/"+excelPath+".xlsx");
		XSSFSheet sheet= wbook.getSheet(sheetName);
		DataFormatter formatter = new DataFormatter();
		int rowCount = sheet.getLastRowNum();
		System.out.println("Number of Rows "+rowCount);
		System.setProperty("webdriver.chrome.silentOutput", "true");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.compusystems.com/jsp/toolMain.jsp");
		driver.switchTo().frame("main");
		driver.findElement(By.xpath("(//input[@name='login'])[1]")).sendKeys(userName);
		driver.findElement(By.name("Password")).sendKeys(password);
		driver.findElement(By.name("action")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("main");
		driver.findElement(By.xpath("//a[contains(text(),'Database, WebSite, and Registration tools')]")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("main");
		driver.findElement(By.linkText("Select DB")).click();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//a[contains(text(),'"+showName+"')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'regtype_x_option_detail')]")).click();
		driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).clear();
		
		//x<rowCount of excel	//x=0 determines starting of the row 
		for(int x =rowNum; x<rowCount; x++){
			Map< String,Integer> webMap = new LinkedHashMap< String,Integer>();
			XSSFCell  regType= sheet.getRow(x).getCell(xlReg);
			String regTypeValue = formatter.formatCellValue(regType);
			System.out.println("Executing "+regTypeValue);
			XSSFCell  option= sheet.getRow(x).getCell(xlOpt);
			wbook.close();
			excelMap.clear();
			String optionValue = option.toString();
			String trimOptionValue = optionValue.replaceAll("\\s+", "").replaceAll(",", " ");
			String[] splitoption = trimOptionValue.split(" ");
			
			if(splitoption.length>1) {
				for (int j = 0; j < splitoption.length; j++) {
					excelMap.put(splitoption[j], j+1); 
					splitoption[j] = regTypeValue + " " + splitoption[j];
				}
			}else {
				excelMap.put("0", 0);
			}

			driver.findElement(By.xpath("(//input[@name='clause'])[1]")).clear();			
			driver.findElement(By.xpath("(//input[@name='clause'])[1]")).sendKeys("regtype_evtcode='"+regTypeValue+"'");
			driver.findElement(By.xpath("(//table[@align='CENTER']//input[1])[3]")).sendKeys("0");
			driver.findElement(By.xpath("//input[@type='submit']")).click();
			List<WebElement> webOption = driver.findElements(By.xpath("//input[contains(@name,'opd_id')]/parent::td[1]"));
			int key_value=1;
			if(webOption.size()>0) {
		       for (WebElement optionValues : webOption) {
		    	   webMap.put(optionValues.getText(), key_value);
		    	   key_value = key_value+1;
			}
			}else {
				webMap.put("0", 0);
			}
			System.out.println("Web"+webMap.keySet());
			System.out.println("Excel"+excelMap.keySet());
			if( webMap.keySet().equals( excelMap.keySet() ))
			{
				System.out.println("For regtype "+regTypeValue+" all options are programed correct");
			}
			else
			{
				// First map: Excel //Second Map: Web //Extra in web
				HashSet<String> unionKeys = new HashSet<String>(excelMap.keySet());
				unionKeys.addAll(webMap.keySet());
				unionKeys.removeAll(excelMap.keySet());
				System.out.println("For regtype "+regTypeValue+" this options "+unionKeys+" is extra on web not available on Excel."); 

				// Missisng in web
				HashSet<String> unionKeys1 = new HashSet<String>(webMap.keySet());
				unionKeys1.addAll(excelMap.keySet());
				unionKeys1.removeAll(webMap.keySet());
				System.out.println("For regtype "+regTypeValue+" this options " +unionKeys1 +" is missing on web."); 
			}
		}
		wbook.close();		
		driver.close();
		driver.quit();
		System.exit(1);
	}
}