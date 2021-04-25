package Config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class KeyWordAction {
	WebDriver driver = null;
	public KeyWordAction(WebDriver driver) {
		this.driver = driver;
	}
	
	public void OpenBrowser(String BrowserName)
	{
		if(BrowserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "D:\\TOOL\\WEBDRIVER\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(BrowserName.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "D:\\TOOL\\WEBDRIVER\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else {
			System.out.println("Loi trinh duyet");
		}
	}
	
	public void Navigate(String URL) {
		driver.get(URL);
	}
	
	public void Click(String address)
	{
		if(address.contains("["))
		{
			driver.findElement(By.xpath(address)).click();
		}else 
		{
			driver.findElement(By.id(address)).click();
		}
	}
	
	public void SetText(String address, String Value)
	{
		if(address.contains("["))
		{
			driver.findElement(By.xpath(address)).sendKeys(Value);
		}else 
		{
			driver.findElement(By.id(address)).sendKeys(Value);
		}
	}
	
	public Boolean VerifyTitle(String Value)
	{
		return driver.getTitle().equals(Value);
	}
	public Boolean VerifyElementText(String address, String Value)
	{
		if(address.contains("["))
		{
			return driver.findElement(By.xpath(address)).getText().equals(Value);
		}else 
		{
			return driver.findElement(By.id(address)).getText().equals(Value);
		}
	}
	
	public void Close()
	{
		driver.close();
	}
}
