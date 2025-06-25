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

public class DriverManager {
	  private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	    public static WebDriver getDriver() {
	        if (driver.get() == null) {
	            String browserName = ConfigReader.get("browser").toUpperCase();
	            BrowserType browser = BrowserType.valueOf(browserName);
	            switch (browser) {
	                case CHROME -> {
	                    WebDriverManager.chromedriver().setup();
	                    ChromeOptions options = new ChromeOptions();

	                    boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("chrome.headless"));

	                    options.addArguments("--disable-notifications");
	                    options.addArguments("--disable-extensions");
	                    options.addArguments("--disable-gpu");
	                    options.addArguments("--no-sandbox");
	                   

	                    if (isHeadless) {
	                        options.addArguments("--headless=new");  
	                        options.addArguments("--window-size=1920,1080");
	                    } else {
	                    	driver.get().manage().window().maximize();
	                    }

	                    driver.set(new ChromeDriver(options));
	                }
	                case FIREFOX -> {
	                    WebDriverManager.firefoxdriver().setup();
	                    FirefoxOptions options = new FirefoxOptions();

	                    boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("firefox.headless"));

	                    if (isHeadless) {
	                        options.addArguments("-headless");
	                        options.addArguments("--width=1920");
	                        options.addArguments("--height=1080");
	                    }

	                    driver.set(new FirefoxDriver(options));

	                    if (!isHeadless) {
	                        driver.get().manage().window().maximize();
	                    }
	                }
	                case EDGE -> {
	                    WebDriverManager.edgedriver().setup();
	                    EdgeOptions options = new EdgeOptions();

	                    boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("edge.headless"));

	                    if (isHeadless) {
	                        options.addArguments("--headless=new");
	                        options.addArguments("--window-size=1920,1080");
	                    } else {
	                        options.addArguments("--start-maximized");
	                    }

	                    driver.set(new EdgeDriver(options));
	                }
	                default -> throw new RuntimeException("Unsupported browser: " + browserName);
	            }
	            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        }
	        return driver.get();
	    }

	    public static void quitDriver() {
	        if (driver.get() != null) {
	            driver.get().quit();
	            driver.remove();
	        }
	    }
	}