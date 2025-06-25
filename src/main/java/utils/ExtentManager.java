package utils;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	
	 private static ExtentReports extent;

	    public static ExtentReports getInstance() {
	        if (extent == null) {
	            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	            String reportDir = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "reports";
	            new File(reportDir).mkdirs();

	            String reportPath = reportDir + File.separator + "ExtentReport_" + timestamp + ".html";

	            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
	            spark.config().setReportName("SauceDemo Test Execution");
	            spark.config().setDocumentTitle("Automation Report");

	            extent = new ExtentReports();
	            extent.attachReporter(spark);
	            extent.setSystemInfo("Project", "Sauce Demo");
	            extent.setSystemInfo("Senior QA Tester", "Lakshmi Narayana Sindiri");
	        }
	        return extent;
	    }
	}

