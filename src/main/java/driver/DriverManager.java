package driver;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import config.BrowserType;
import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browserName = ConfigReader.get("browser").toUpperCase();
            logger.info("Initializing browser: {}", browserName);
            BrowserType browser = BrowserType.valueOf(browserName);

            switch (browser) {
                case CHROME -> {
                    logger.debug("Setting up ChromeDriver...");
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();

                    boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("chrome.headless"));
                    logger.debug("Chrome headless mode: {}", isHeadless);

                    options.addArguments("--disable-notifications");
                    options.addArguments("--disable-extensions");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");

                    if (isHeadless) {
                        options.addArguments("--headless=new");
                        options.addArguments("--window-size=1920,1080");
                    }

                    WebDriver chromeDriver = new ChromeDriver(options);
                    driver.set(chromeDriver);
                    if (!isHeadless) {
                        chromeDriver.manage().window().maximize();
                        chromeDriver.manage().deleteAllCookies();
                    }
                }

                case FIREFOX -> {
                    logger.debug("Setting up FirefoxDriver...");
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions options = new FirefoxOptions();

                    boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("firefox.headless"));
                    logger.debug("Firefox headless mode: {}", isHeadless);

                    if (isHeadless) {
                        options.addArguments("-headless");
                        options.addArguments("--width=1920");
                        options.addArguments("--height=1080");
                    }

                    WebDriver firefoxDriver = new FirefoxDriver(options);
                    driver.set(firefoxDriver);
                    if (!isHeadless) {
                        firefoxDriver.manage().window().maximize();
                    }
                }

                case EDGE -> {
                    logger.debug("Setting up EdgeDriver...");
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions options = new EdgeOptions();

                    boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("edge.headless"));
                    logger.debug("Edge headless mode: {}", isHeadless);

                    if (isHeadless) {
                        options.addArguments("--headless=new");
                        options.addArguments("--window-size=1920,1080");
                    } else {
                        options.addArguments("--start-maximized");
                    }

                    WebDriver edgeDriver = new EdgeDriver(options);
                    driver.set(edgeDriver);
                }

                default -> {
                    logger.error("Unsupported browser: {}", browserName);
                    throw new RuntimeException("Unsupported browser: " + browserName);
                }
            }

            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            logger.info("{} browser initialized successfully", browserName);
        }

        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting browser session...");
            driver.get().quit();
            driver.remove();
            logger.info("Browser session terminated.");
        }
    }
}
