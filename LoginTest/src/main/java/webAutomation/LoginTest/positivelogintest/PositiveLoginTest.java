package webAutomation.LoginTest.positivelogintest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import webAutomation.LoginTest.base.TestUtilities;

public class PositiveLoginTest extends TestUtilities
{
	@Parameters({ "username", "password", "expectedSuccessMessage"})
	@Test(priority = 1)
	public  void positive_Login(String username, String password, String expectedSuccessMessage) 
	{	
		log.info("Starting positive login test");
		validateUser(username, password);
		takeScreenshot("User validated");
		
		// verification
		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);

		// log out button is visible
		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='button secondary radius']")).isDisplayed(),
						"logOutButton is not visible.");

		// Successful log in message
		//String expectedSuccessMessage = "You logged into a secure area!";
		String actualSuccessMessage = driver.findElement(By.id("flash")).getText();
		Assert.assertTrue(actualSuccessMessage.contains(expectedSuccessMessage),
		"actualSuccessMessage does not contain expectedSuccessMessage\nexpectedSuccessMessage: "
		+ expectedSuccessMessage + "\nactualSuccessMessage: " + actualSuccessMessage);
		
		//assertEquals(actualSuccessMessage, expectedSuccessMessage);
		
	}
}