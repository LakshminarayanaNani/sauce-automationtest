package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.Cookie;
import config.ConfigReader;
import pages.LoginPage;
import utils.DataProviderUtils;

public class LoginTests extends BaseTest{


	@Test(dataProvider = "loginData", dataProviderClass = DataProviderUtils.class)
	public void loginTest(String username, String password, boolean shouldLogin) {
		
		driver.get(ConfigReader.get("base.url"));

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		if (shouldLogin) {
			Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
					"User should be redirected to inventory page");

			
			Cookie sessionCookie = driver.manage().getCookieNamed("session-username");
			Assert.assertNotNull(sessionCookie, "Session cookie should be set");
			Assert.assertEquals(sessionCookie.getValue(), username,
					"Session cookie should match logged-in user");

		} else {
			
			Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be shown for invalid login");
			System.out.println("Error: " + loginPage.getErrorMessage());
		}
	}

	@Test(dataProvider = "localStorageUser", dataProviderClass = DataProviderUtils.class)
	public void testLocalStorageAfterLogin(String username, String password) {
		driver.get(ConfigReader.get("base.url"));

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
				"Expected to be on inventory page after login");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String sessionUser = (String) js.executeScript(
				"return window.localStorage.getItem('session-username');");

		Assert.assertEquals(sessionUser, username,
				"session-username in localStorage should match the logged-in user");
	}

}