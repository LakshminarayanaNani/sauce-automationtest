package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.ElementUtils;
import locators.LoginPageLocators;

public class LoginPage {

    private final WebDriver driver;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String pass) {
        logger.info("Starting login with username: {}", user);

        WebElement usernameField = driver.findElement(LoginPageLocators.USERNAME);
        WebElement passwordField = driver.findElement(LoginPageLocators.PASSWORD);
        WebElement loginButton   = driver.findElement(LoginPageLocators.LOGIN_BUTTON);

        usernameField.clear();
        logger.debug("Cleared username field");
        usernameField.sendKeys(user);
        logger.debug("Entered username");

        passwordField.clear();
        logger.debug("Cleared password field");
        passwordField.sendKeys(pass);
        logger.debug("Entered password");

        loginButton.click();
        logger.info("Clicked login button");
    }

    public boolean isErrorDisplayed() {
        boolean isDisplayed = !ElementUtils.getText(getErrorElement()).isEmpty();
        logger.info("Is error message displayed? {}", isDisplayed);
        return isDisplayed;
    }

    public String getErrorMessage() {
        String errorMessage = ElementUtils.getText(getErrorElement());
        logger.info("Error message retrieved: {}", errorMessage);
        return errorMessage;
    }

    private WebElement getErrorElement() {
        return driver.findElement(LoginPageLocators.ERROR_MESSAGE);
    }
}
