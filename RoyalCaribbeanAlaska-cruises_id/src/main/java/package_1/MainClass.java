package package_1;
import java.time.Duration;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MainClass {
	public static WebDriver driver;
	static public String url = "https://www.royalcaribbean.com/alaska-cruises";
	public List<String> windowhandle; // store the multiple windows
    public static WebDriverWait wait;
    
	// Invoke the driver and navigate to the url
	public void InvokeDriver() {
		driver = DriverSetUp.getDriver();
		driver.manage().window().maximize();
		driver.get(url);
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	    driver.navigate().refresh();
		System.out.println("loaded page");
	}

	public void switchWindow() throws Exception {
		
		WebElement searchelement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='global-nav-search-button']/a/img")));
//		System.out.println("parent - " + driver.getTitle());
		searchelement.click();
		driver = DriverSetUp.getChildWindowLink(driver);
	}

	public void SearchCruise() throws InterruptedException {
		
		WebElement valueTextEle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class=\"siteSearchBox__input\"]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",valueTextEle);
		valueTextEle.sendKeys("Rhapsody of the Seas");
		//Thread.sleep(2000);
		WebElement searchsymbol = driver.findElement(By.xpath("//div[@class='siteSearchBox__glassIconContainer']/img"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchsymbol);
	}

	public void ClickFirstLink() throws InterruptedException {
		
		WebElement firstResult = wait.until(ExpectedConditions
  				.visibilityOfElementLocated(By.linkText("Rhapsody of the Seas | Cruise Ships | Royal Caribbean Cruises")));
		firstResult.click();
		driver=DriverSetUp.getChildWindowLink(driver);
	}
    
	public void clickBookNow() {
		
		WebElement booknow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("promoHeroCTAButton")));
		booknow.click();
		driver=DriverSetUp.getChildWindowLink(driver);
	}
	
	
	public void Filter_cruise_dates(String year) throws InterruptedException {
	    	
			WebElement cruiseDateele=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"filters-content\"]/button[1]")));
			cruiseDateele.click();
			WebElement months=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Months']")));
			if(!months.isDisplayed()) {
				months.click();
			}
			//Thread.sleep(2000);
			  List<WebElement> years_list=wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[starts-with(@class,'styles__YearWrapper')]")));
			  for(WebElement i:years_list) {
				  WebElement year_header=i.findElement(By.xpath(".//div[1]"));
				  //System.out.println(year_header.getText()); //years in list
				  if(year_header.getText().equals(year)) {
					  List<WebElement> months_list=i.findElements(By.xpath(".//div[2]/div[@class='styles__MonthTile-sc-e7674c24-4 bMGRrT']"));
					  WebElement button_month;
					  int num=0;
					  for(WebElement month:months_list) {
						  if(num>=4) break;
					    	 button_month=month.findElement(By.tagName("button"));
					         String className = button_month.getAttribute("class");
					         if (className.contains("disabled")) continue;
					         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button_month);
					         Thread.sleep(2000); // Optional delay
					         //System.out.println(button_month.getText()); //selected month names
					         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button_month);
					     	 num++; 
					  }
				  }
			  }
			  
	    }
	  
	public void SelectDeparturePort(String Nation) throws InterruptedException {
		DriverSetUp.backtotop();
		
		WebElement departurePort=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"filters-content\"]/button[3]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", departurePort);
		//Thread.sleep(2000);
		
		List<WebElement> port_Nations=wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class=\"components__Section-sc-6abd413c-3 AQzaS\"]")));
		 for(WebElement i:port_Nations) {
			  WebElement NationName=i.findElement(By.xpath(".//div[1]"));
			  //System.out.println(NationName.getText());  //nationality names stored in list
			  String Nationname=NationName.getText();
			  if(Nationname.equalsIgnoreCase(Nation)) {
				  List<WebElement> optionsList=i.findElements(By.xpath(".//div[2]/div[starts-with(@class,'components__ButtonWrap-sc-6abd413c-1 gBFaFs')]"));
				  int num=0;
				  for(WebElement option:optionsList) {
					     if(num>=1) break;
				         String className = option.getAttribute("class");
				         if (className.contains("disabled")) continue;
				         WebElement button_option=option.findElement(By.tagName("button"));
				         //System.out.println(button_option.getText()); option selected
				         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",button_option);
				         Thread.sleep(2000); // Optional delay
				         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button_option);
				         num++;
				  }
				  if(num==0) {
					  System.out.println("No available options in the "+Nationname);
				  }
			  }
		  }
	}
	
	
	public static void 	selectDestinaion() throws InterruptedException {
		DriverSetUp.backtotop();
        
		WebElement Destination=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"filters-content\"]/button[2]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", Destination);
		//Thread.sleep(2000);
		
		List<WebElement> destinations=wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class=\"components__DestinationGrid-sc-5b3fb64b-0 eHabzl\"]/div")));
		int num=0;
		        for(WebElement i:destinations) {
					     if(num>=1) break;
				         String className = i.getAttribute("class");
				         if (className.contains("disabled")) continue;
				         WebElement button_option=i.findElement(By.tagName("button"));
				         //System.out.println(button_option.getText()); //option selected
				         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",button_option);
				         Thread.sleep(2000); // Optional delay
				         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button_option);
				         num++;
				  }
				  if(num==0) {
					  System.out.println("No available options destinations");
				  }
           }

	
	public static void selectNumberOfNightsAndClickResults() throws InterruptedException {
		DriverSetUp.backtotop();
      
		WebElement NumerOfNights=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"filters-content\"]/button[4]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", NumerOfNights);
		Thread.sleep(2000);
		
		List<WebElement> LengthOfCruise=wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='NightsSelector__PillContainer-sc-c59cb1b1-1 fhXtlt']/div")));
		int num=0;
		for(WebElement i:LengthOfCruise) {
		     if(num>=1) break;
	         String className = i.getAttribute("class");
	         if (className.contains("disabled")) continue;
	         WebElement button_option=i.findElement(By.tagName("button"));
	         //System.out.println(button_option.getText()); //option selected
	         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",button_option);
	         Thread.sleep(2000); // Optional delay
	         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button_option);
	         num++;
	        }
		if(num==0) {
			  System.out.println("No available options for Number of nights");
		  }
		  Thread.sleep(2000);
		  
		  WebElement seeResults=driver.findElement(By.xpath("//button[text()=\"See results\"]"));
		  seeResults.click();
	}
	
	public void SortResults(String value) throws InterruptedException {
  	  WebElement sortElement=driver.findElement(By.xpath("//div[@data-testid=\"sort-by-results-dropdown\"]/button"));
  	  sortElement.click();
  	  Thread.sleep(2000);
//  	  WebElement dropdownList = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@class='styles__List-sc-5d55c2e3-5 kyzkgd']")));
//  	  List<WebElement> items = dropdownList.findElements(By.tagName("li"));
  	  List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@class='styles__List-sc-5d55c2e3-5 kyzkgd']/li")));
  	  for(WebElement item : items) {
  		  WebElement paragraph = item.findElement(By.tagName("p"));
  		  if(paragraph.getText().equalsIgnoreCase(value)){
  		  paragraph.click();
  		  }
  		  
  	  }
	  }
	
	public void CountDisplay() {
	    WebElement count=driver.findElement(By.xpath(" //div[@data-testid=\"number-results-label\"]/div/span/span"));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",count);
	    String result=count.getText();
	    System.out.println("Cruise Search Results : "+result);
	    
	    
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		MainClass object = new MainClass();
		object.InvokeDriver();                      // calling the method to create driver and navigate
		object.switchWindow();                      //calling the click the search icon and switch to window
		object.SearchCruise();                      //click and send value ,click on search icon
		object.ClickFirstLink();                    //getting firstlink id and click then switch to that window
		object.clickBookNow();                      // clicking booknow and switch to that window
		object.Filter_cruise_dates("2026");         //selecting any four months 
		selectDestinaion();                         //select the available destination 
		object.SelectDeparturePort("North America");//select the available departure port
		selectNumberOfNightsAndClickResults();      //select the available number of nights and click the seeResults button
		object.SortResults("Price lowest to highest"); //select the Price lowest to hoghest option in dropdown
		object.CountDisplay();                         //display the count of Cruise Search Results 
		Thread.sleep(3000);
		driver.quit();                                // close the window
	}

}
