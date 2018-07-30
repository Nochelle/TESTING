package Testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SavaariTestNG {
	WebDriver driver;
	
	public XSSFRow excelWoorkBook() throws FileNotFoundException, IOException {
		
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("D:\\LeadSuite_1.xlsx"));
		XSSFSheet sheet = workbook.getSheetAt(0); 
		XSSFRow row = sheet.getRow(0);           	
		
		return row;
	}

	@BeforeTest
	@Parameters("browser")
public void webDriverConnection(String browser) throws Exception{
		
		 if(browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver","D:\\Selenium softwares\\Chrome Drivers\\chromedriver 2.35\\chromedriver.exe");
				//create chrome instance
				driver = new ChromeDriver();
			}
			else if(browser.equalsIgnoreCase("Internet Explorer")){
				System.setProperty("webdriver.ie.driver","D:\\Selenium\\Selenium softwares\\IE Driver\\IEDriverServer_x64_3.12.0\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
			else{
				//If no browser passed throw exception
				throw new Exception("Browser is not correct");
			}
		
		driver.manage().window().maximize();
		
		driver.get("http://www.savaari.com/");
	}
		
		@DataProvider(name = "travelDetails")
		public Object[][] dataDriven() throws FileNotFoundException, IOException{
			
			Object a[][] = new Object[1][2];
			XSSFRow row = excelWoorkBook();                                                                                              
			
			XSSFCell cell = row.getCell(0);
			String fromCity = cell.getStringCellValue();
			a[0][0] = fromCity;
			
			XSSFCell cell1 = row.getCell(1);
			String toCity = cell1.getStringCellValue();
			a[0][1] = toCity;
		
			return a;

		}
		
		@DataProvider(name = "CusDetails")
		public Object[][] dataProviders() throws FileNotFoundException, IOException{
			
			Object b[][] = new Object[1][6];
			
			XSSFRow row = excelWoorkBook();  
			
			XSSFCell cell2 = row.getCell(2);
			String cusName = cell2.getStringCellValue();
			b[0][0] = cusName;
			
			XSSFCell cell3 = row.getCell(3);
			String cusEmail = cell3.getStringCellValue();
			b[0][1] = cusEmail;
			
			XSSFCell cell4 = row.getCell(4);
			String cusPhone = String.valueOf( (long)  cell4.getNumericCellValue());
			b[0][2] = cusPhone;
			
			XSSFCell cell5 = row.getCell(5);
			String cusCity = cell5.getStringCellValue();
			b[0][3] = cusCity;
			
			XSSFCell cell6 = row.getCell(6);
			String cusLandMark = cell6.getStringCellValue();
			b[0][4] = cusLandMark;
			
			XSSFCell cell7 = row.getCell(7);
			String cusAddress = cell7.getStringCellValue();
			b[0][5] = cusAddress;
			
			return b;
		}
		
		
		
		@Test(dataProvider = "travelDetails")
	public void citiesData(String fromCity, String toCity) throws InterruptedException{
		
		driver.findElement(By.xpath("//*[@id=\"approot\"]")).click();
		
		driver.findElement(By.xpath("//*[@id=\"fromCityList\"]")).sendKeys(fromCity);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"fromCityList\"]")).sendKeys(Keys.ENTER);
		
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[2]/div[2]/input")).sendKeys(toCity);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[2]/div[2]/input")).sendKeys(Keys.ENTER);	
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[2]/div[2]/div/a/div")).click();
		Thread.sleep(1000);	
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[3]/div[2]/input")).sendKeys(toCity);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[3]/div[2]/input")).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);		
	}
		
		@Test(dependsOnMethods = { "citiesData" })
	public void datesPicker() throws InterruptedException{
		
		//Departure DAte
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[4]/div[1]/div/p-calendar[1]/span/input")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[4]/div[1]/div/p-calendar[1]/span/div/div/a[2]/span")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[4]/div[1]/div/p-calendar[1]/span/div/table/tbody/tr[2]/td[4]/a")).click();
		
		// Return Date
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[4]/div[3]/div/p-calendar[1]/span/input")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[4]/div[3]/div/p-calendar[1]/span/div/table/tbody/tr[3]/td[3]/a")).click();
		
		Select times = new Select(driver.findElement(By.xpath("//*[@id=\"pickUpTime\"]")));
		times.selectByIndex(3);
		screenShot();
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-home/div[1]/div[2]/div[2]/app-outstation/div/form/div[5]/div/div[1]/button")).click();
		
	}
		@Test(dependsOnMethods = { "datesPicker" })
	public void carSelection() throws InterruptedException{
		driver.navigate().forward();
		Thread.sleep(10);
		screenShot();
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-select-car/div[2]/div/div/div[3]/div[5]/div/button")).click();
									
		}
		
		@Test(dataProvider = "CusDetails",dependsOnMethods = { "carSelection" })
	public void customerDetails(String cusName,String cusEmail, String cusPhone, String cusAddress, String cusLandMark, String cusCity) throws InterruptedException{
		
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-booking/div[2]/app-booking-pickup-detail/div/div[2]/form/div[1]/div/input")).sendKeys(cusName);
		
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-booking/div[2]/app-booking-pickup-detail/div/div[2]/form/div[2]/input")).sendKeys(cusEmail);

		
		Select mobileNo = new Select(driver.findElement(By.xpath("//*[@id=\"idISD\"]")));
		mobileNo.selectByIndex(0);
		
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-booking/div[2]/app-booking-pickup-detail/div/div[2]/form/div[3]/div/input")).sendKeys(cusPhone);
		
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-booking/div[2]/app-booking-pickup-detail/div/div[2]/form/div[4]/div/input")).sendKeys(cusCity);
		
		Thread.sleep(10);
		
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-booking/div[2]/app-booking-pickup-detail/div/div[2]/form/div[4]/div/input")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		
		Thread.sleep(10);
		
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-booking/div[2]/app-booking-pickup-detail/div/div[2]/form/div[5]/div/textarea")).sendKeys(cusAddress);
		
		driver.findElement(By.xpath("//*[@id=\"approot\"]/mat-sidenav-container/mat-sidenav-content/app-booking/div[2]/app-booking-pickup-detail/div/div[2]/form/div[5]/div/input")).sendKeys(cusLandMark);
			
		
		screenShot();
		
		Reporter.log("Total Fare" + driver.findElement(By.xpath("//*[@id=\"static-1\"]/div/div[5]/div[2]/div")).getText());;
	}

	public void screenShot(){
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			screenshot.getAbsoluteFile();
			File dest = new File("C:\\Users\\694796\\Desktop\\"+ System.currentTimeMillis() +".png");
			FileUtils.copyFile(screenshot, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/*@AfterTest
	public void driverClose(){
		driver.close();
	}*/
}
