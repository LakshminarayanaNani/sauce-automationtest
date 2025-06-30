package tests;

import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("Test setup starting...");
        driver = DriverManager.getDriver();
        logger.info("Driver initialized.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Test teardown starting...");
        DriverManager.quitDriver();
        logger.info("Driver quit successfully.");
    }
}
