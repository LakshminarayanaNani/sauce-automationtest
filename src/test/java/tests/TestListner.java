package tests;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;

import utils.ExtentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestListner implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListner.class);
    private ExtentReports extent;

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite started.", context.getName());
        extent = ExtentManager.getInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite finished. Flushing ExtentReports.", context.getName());
        extent.flush();
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestFailure(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}

    public static ExtentReports getExtent() {
        return ExtentManager.getInstance();
    }
}
