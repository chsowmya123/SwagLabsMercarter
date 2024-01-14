package StepDefinitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddToCart {
 WebDriver driver= null;
 @Before 
@Given("User opens Browser")
public void OpenBrower() {
	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
	//driver.manage().window().maximize();
}

@And("User navigate to login page and login to website")
public void LoginAsUser() throws InterruptedException  {
	driver.navigate().to("https://www.saucedemo.com");
	String Actualtitle = driver.getTitle();
	String Expectedtitle = "Swag Labs";
	Assert.assertEquals(Actualtitle, Expectedtitle);
	driver.findElement(By.id("user-name")).sendKeys("standard_user");
	Thread.sleep(200);
	driver.findElement(By.id("password")).sendKeys("secret_sauce");
	Thread.sleep(200);
	driver.findElement(By.id("login-button")).click();
	Thread.sleep(2000);
}

@When("User should add highest price item to cart")
public void AddHighestPriceToCart() throws InterruptedException {
	List<WebElement> Eprice = driver.findElements(By.className("inventory_item_price"));
	List<Double> Dprice =new ArrayList<>();
	for(WebElement a : Eprice) {
		Dprice.add(Double.valueOf(a.getText().replace("$","")));
	}
	Double[] sorted = new Double[Dprice.size()];
	for(int i=0;i<Dprice.size();i++) {
		 sorted[i]=Dprice.get(i);
		
	}
	Arrays.sort(sorted, Collections.reverseOrder());
	
	
	
	for(int j=0;j<Dprice.size();j++) 
	{
		
		if(Dprice.get(j).equals(sorted[0])) {
			
			List<WebElement> cart = driver.findElements(By.className("btn_inventory"));
			cart.get(j).click();
		}
	}
	Thread.sleep(500);
	}

@Then("user shoud be able to see highest price item in cart")
public void UserInCartPage() throws InterruptedException {
	driver.findElement(By.className("shopping_cart_link")).click();
	Thread.sleep(500);
    }
    
@After
public void CloseBrowser() {
	driver.quit();
}
}
