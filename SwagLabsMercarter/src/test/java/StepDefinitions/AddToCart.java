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
import org.openqa.selenium.support.ui.Wait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AddToCart {
 WebDriver driver= null;
 @Before 
@Given("User is on the login page")
public void OpenBrower() {
	 WebDriverManager.chromedriver().setup();
	 driver = new ChromeDriver();
	 driver.navigate().to("https://www.saucedemo.com");
	 driver.manage().window().maximize();
}

@And("User logs on to the website")
public void LoginAsUser() {
	driver.findElement(By.id("user-name")).sendKeys("standard_user");
	driver.findElement(By.id("password")).sendKeys("secret_sauce");
	driver.findElement(By.id("login-button")).click();
	
}

@When("User added the highest price item to cart")
public void AddHighestPriceToCart() throws InterruptedException{
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
			Thread.sleep(200);
		}
	}
	}

@Then("User should see the highest price item in cart")
public void UserInCartPage()  {
	driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
	driver.findElement(By.className("shopping_cart_link")).click();
	String ItemNameActual = driver.findElement(By.xpath("//*[@id=\"item_5_title_link\"]/div")).getText();
	String ItemNameExpected = "Sauce Labs Fleece Jacket";
	Assert.assertEquals(ItemNameActual, ItemNameExpected);
	
	
    }
@After
public void CloseBrowser() {
	driver.quit();
}
}
