package selenium_test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAcceptGuest {
	
	private WebDriver driver;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
		driver = new ChromeDriver();
	}
	
	@After
	public void cleanUp() {
		driver.close();
	}
	
	@Test
	public void testAcceptGuest() {
		
		driver.get("http://localhost:8080/hosteat_webapp/index.jsp");
	    driver.manage().window().setSize(new Dimension(945, 1015));
	    driver.findElement(By.cssSelector("p > #btn")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("testHost");
	    driver.findElement(By.id("password")).sendKeys("test");
	    driver.findElement(By.name("login")).click();
	    driver.findElement(By.name("open0")).click();
	    driver.findElement(By.name("view")).click();
	    driver.findElement(By.name("deny0")).click();
	    driver.findElement(By.xpath("(//input[@id=\'btn\'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id=\'btn\'])[5]")).click();
	    driver.findElement(By.cssSelector("p > #btn")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("testGuest");
	    driver.findElement(By.id("password")).sendKeys("test");
	    driver.findElement(By.name("login")).click();
	    driver.findElement(By.xpath("(//input[@id=\'btn\'])[4]")).click();
	    driver.findElement(By.name("events0")).click();
	    driver.findElement(By.name("open0")).click();
	    driver.findElement(By.name("join")).click();
	    driver.findElement(By.xpath("(//input[@id=\'btn\'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@id=\'btn\'])[6]")).click();
	    driver.findElement(By.cssSelector("p > #btn")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("testHost");
	    driver.findElement(By.id("password")).sendKeys("test");
	    driver.findElement(By.name("login")).click();
	    driver.findElement(By.name("open0")).click();
	    driver.findElement(By.name("view")).click();
	    driver.findElement(By.name("accept0")).click();		
		
	    String result = driver.findElement(By.xpath("/html/body/div[2]/p")).getText();
	    String expectedString = "ACCEPTED";
	    
	    assertEquals(expectedString, result);
	}
}
