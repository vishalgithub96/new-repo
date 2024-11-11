package Demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.Assert;
import org.testng.annotations.*;

public class OrangeHRM {

	public String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver ;

	@BeforeTest
	public void setup() 
	{   

		System.out.println("Before test executed");
		driver = new ChromeDriver();
		//maximise windows

		driver.manage().window().maximize();
		//open url 	
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));


	}

	@Test(priority = 1)
	public void loginwithInvalidData() throws InterruptedException 
	{

		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");

		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123");//wrong password

		driver.findElement(By.xpath("//button[@type='submit']")).click();

		String message_expected = "Invalid credentials";

		String message_actual = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
		Assert.assertEquals(message_expected, message_actual );
		Thread.sleep(1500);



	}
	@Test(priority = 2)
	public void loginTestwithValidDtat() 
	{   //find username
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
		//find password
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("admin123");
		//login button click
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		String pagetitle = driver.getTitle();
		if(pagetitle.equals("OrangeHRM")) {
			System.out.println("Login Successful");
		}  else{
			System.out.println("login failed");
		}	

		Assert.assertEquals(pagetitle, "OrangeHRM");
	}

	public void logOut() throws InterruptedException 
	{
		driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
		//driver.findElement(By.xpath("//a[text()='Logout']")).click();
		List<WebElement> listOflogOut  = driver.findElements(By.xpath("//a[@role='menuitem']"));
		for (int i = 0; i < listOflogOut.size(); i++) 
		{
			Thread.sleep(1000);
			System.out.println(i+":"+ listOflogOut.get(i).getText());

		}	
		listOflogOut.get(3).click();

	}
	@AfterTest
	public void tearDown() throws InterruptedException 
	{   
		logOut();
		Thread.sleep(5000);  //wait for 5 secs before quit  
		driver.close();
		driver.quit();
	}	

}
