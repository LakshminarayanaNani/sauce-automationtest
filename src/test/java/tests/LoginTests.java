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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTests.class);

    @Test(dataProvider = "loginData", dataProviderClass = DataProviderUtils.class)
    public void loginTest(String username, String password, boolean shouldLogin) {

        logger.info("Starting loginTest for user: {} | Expected Success: {}", username, shouldLogin);
        driver.get(ConfigReader.get("base.url"));
        logger.debug("Navigated to URL: {}", ConfigReader.get("base.url"));

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

            logger.info("Attempting login with username: {}", username);
            loginSection.log(Status.INFO, "Trying with username: " + username + ", password: ******");

            if (shouldLogin) {
                logger.debug("Valid login scenario. Verifying redirection and session...");

                Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
                logger.info("Login successful. URL contains 'inventory'");
                loginSection.log(Status.PASS, "Login successful. Redirected to inventory page.");

                // --- Session Cookie Check (Positive) ---
                ExtentTest sessionSection = userNode.createNode("Session Cookie Check");
                Cookie sessionCookie = driver.manage().getCookieNamed("session-username");
                Assert.assertNotNull(sessionCookie, "Session cookie not found");
                Assert.assertEquals(sessionCookie.getValue(), username);
                logger.info("Session cookie validated: {}", sessionCookie.getValue());
                sessionSection.log(Status.PASS, "Session cookie validated: " + sessionCookie.getValue());

                // --- Local Storage Check (Positive) ---
                ExtentTest storageSection = userNode.createNode("Local Storage Check");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String localStorageSnapshot = (String) js.executeScript("return JSON.stringify(Object.entries(window.localStorage));");
                logger.debug("Local storage content: {}", localStorageSnapshot);
                storageSection.log(Status.INFO, "Local Storage: " + localStorageSnapshot);

                userNode.log(Status.PASS, "Test passed");
                logger.info("Positive login test passed for user: {}", username);

            } else {
                logger.debug("Negative login scenario. Verifying error message and cookie/storage absence...");

                Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected error message to be displayed for invalid login");
                String errorMsg = loginPage.getErrorMessage();
                logger.warn("Login failed as expected. Error: {}", errorMsg);
                loginSection.log(Status.PASS, "Error displayed: " + errorMsg);

                // --- Session Cookie Check (Negative) ---
                ExtentTest sessionSection = userNode.createNode("Session Cookie Check");
                Cookie sessionCookie = driver.manage().getCookieNamed("session-username");
                if (sessionCookie != null) {
                    logger.warn("Session cookie exists despite failed login: {}", sessionCookie.getValue());
                    sessionSection.log(Status.INFO, "Session cookie exists even though login failed. Username in cookie: " + sessionCookie.getValue());
                } else {
                    logger.info("No session cookie found after failed login.");
                    sessionSection.log(Status.INFO, "Session cookie not found after failed login as expected.");
                }

                // --- Local Storage Check (Negative) ---
                ExtentTest storageSection = userNode.createNode("Local Storage Check");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String localSession = (String) js.executeScript("return window.localStorage.getItem('session-username');");
                if (localSession != null) {
                    logger.warn("Local storage session-username found despite failed login: {}", localSession);
                    storageSection.log(Status.INFO, "Local storage session-username exists even though login failed: " + localSession);
                } else {
                    logger.info("No local storage session-username found after failed login.");
                    storageSection.log(Status.INFO, "Local storage session-username not found after failed login as expected.");
                }

                userNode.log(Status.PASS, "Test passed with expected login failure");
                logger.info("Negative login test passed for user: {}", username);
            }

        } catch (Exception e) {
            logger.error("Exception occurred during login test for user: {}", username, e);
            userNode.log(Status.FAIL, e);
            throw e;
        }
    }
}
