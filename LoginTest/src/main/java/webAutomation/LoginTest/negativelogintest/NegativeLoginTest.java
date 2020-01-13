package webAutomation.LoginTest.negativelogintest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import webAutomation.LoginTest.base.TestUtilities;

public class NegativeLoginTest extends TestUtilities
{
	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 1)
	public void negativeTest(String username, String password, String expectedErrorMessage)
	{
		log.info("Starting negative login Test");
		validateUser(username, password);
		takeScreenshot("User validated");
		
		// Verification
		String actualErrorMessage = driver.findElement(By.id("flash")).getText();
		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"actualErrorMessage does not contain expectedErrorMessage\nexpectedErrorMessage: "
				+ expectedErrorMessage + "\nactualErrorMessage: " + actualErrorMessage);
	}
}