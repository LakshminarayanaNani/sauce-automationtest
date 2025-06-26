package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ElementUtils;
import locators.LoginPageLocators;

public class LoginPage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String pass) {
        driver.findElement(LoginPageLocators.USERNAME).clear();
        driver.findElement(LoginPageLocators.USERNAME).sendKeys(user);
        driver.findElement(LoginPageLocators.PASSWORD).clear();
        driver.findElement(LoginPageLocators.PASSWORD).sendKeys(pass);
        driver.findElement(LoginPageLocators.LOGIN_BUTTON).click();
    }

    public boolean isErrorDisplayed() {
        return !ElementUtils.getText(getErrorElement()).isEmpty();
    }

    public String getErrorMessage() {
        return ElementUtils.getText(getErrorElement());
    }

    private WebElement getErrorElement() {
        return driver.findElement(LoginPageLocators.ERROR_MESSAGE);
    }
}
