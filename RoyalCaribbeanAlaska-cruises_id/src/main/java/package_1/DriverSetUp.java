package package_1;
import java.time.Duration;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class DriverSetUp {
	   static public WebDriver driver;
	   
       public static WebDriver getDriver() {
		   driver=new ChromeDriver();
 
    	   return driver;
       }
       public static WebDriver getChildWindowLink(WebDriver childdriver) {
    	   String parentHandle=childdriver.getWindowHandle();
    	   Set<String> windowHandles=childdriver.getWindowHandles();
    	   for(String i:windowHandles) {
    		   if(!i.equals(parentHandle)) {
    			   childdriver.switchTo().window(i);
    			   break;
    		   }
    	   }
    	   System.out.println("child - "+childdriver.getTitle());
    	   
    	   return childdriver;
       }
       
       public static void backtotop() {
       	WebDriverWait wait  = new WebDriverWait(driver, Duration.ofSeconds(50));
           WebElement filterBar = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("close-filters-button")));
           ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterBar);
       }
}
