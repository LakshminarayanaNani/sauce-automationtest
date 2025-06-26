package tests;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import config.ConfigReader;
import pages.LoginPage;
import utils.DataProviderUtils;

public class LoginTests extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = DataProviderUtils.class)
    public void loginTest(String username, String password, boolean shouldLogin) {
       
    	driver.get(ConfigReader.get("base.url"));

    	String scenarioType = shouldLogin ? "Positive" : "Negative";
        String testName = String.format("loginTest - [%s] - %s", scenarioType, username);
        ExtentTest parent = TestListner.getExtent().createTest(testName);

        
        String userInfo = String.format("User: %s | Valid Login: %s | Session + Storage Validation", username, shouldLogin);
        ExtentTest userNode = parent.createNode(userInfo);

        try {
            // --- Login Section ---
            ExtentTest loginSection = userNode.createNode("Login Validation");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(username, password);

            // Masking password in log
            loginSection.log(Status.INFO, "Trying with username: " + username + ", password: ******");

            if (shouldLogin) {
                Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
                loginSection.log(Status.PASS, "Login successful. Redirected to inventory page.");

                // --- Session Cookie Check (Positive)---
                ExtentTest sessionSection = userNode.createNode("Session Cookie Check");
                Cookie sessionCookie = driver.manage().getCookieNamed("session-username");
                Assert.assertNotNull(sessionCookie, "Session cookie not found");
                Assert.assertEquals(sessionCookie.getValue(), username);
                sessionSection.log(Status.PASS, "Session cookie validated: " + sessionCookie.getValue());

                // --- Local Storage Check (Positive)---
                ExtentTest storageSection = userNode.createNode("Local Storage Check");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String localStorageSnapshot = (String) js.executeScript("return JSON.stringify(Object.entries(window.localStorage));");
                storageSection.log(Status.INFO, "Local Storage: " + localStorageSnapshot);

                userNode.log(Status.PASS, "Test passed");

            } else {
                Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected error message to be displayed for invalid login");
                loginSection.log(Status.PASS, "Error displayed: " + loginPage.getErrorMessage());

                // --- Session Cookie Check (Negative) ---
                ExtentTest sessionSection = userNode.createNode("Session Cookie Check");
                Cookie sessionCookie = driver.manage().getCookieNamed("session-username");
                if (sessionCookie != null) {
                    sessionSection.log(Status.INFO, "Session cookie exists even though login failed. Username in cookie: " + sessionCookie.getValue());
                } else {
                    sessionSection.log(Status.INFO, "Session cookie not found after failed login as expected.");
                }

                // --- Local Storage Check (Negative) ---
                ExtentTest storageSection = userNode.createNode("Local Storage Check");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String localSession = (String) js.executeScript("return window.localStorage.getItem('session-username');");
                if (localSession != null) {
                    storageSection.log(Status.INFO, "Local storage session-username exists even though login failed: " + localSession);
                } else {
                    storageSection.log(Status.INFO, "Local storage session-username not found after failed login as expected.");
                }

                userNode.log(Status.PASS, "Test passed with expected login failure");
            }

        } catch (Exception e) {
            userNode.log(Status.FAIL, e);
            throw e;
        }
    }
}
