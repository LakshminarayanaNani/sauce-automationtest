package utils;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentManager {
	
	 private static ExtentReports extent;

	    public static ExtentReports getInstance() {
	        if (extent == null) {
	            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	            String reportDir = System.getProperty("user.dir")+File.separator+"target"+File.separator+"reports";
	            new File(reportDir).mkdirs();

	            String reportPath = reportDir+File.separator+"ExtentReport_"+timestamp+".html";

	            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
	            spark.config().setReportName("SauceDemo Test Execution");
	            spark.config().setDocumentTitle("Automation Report");
	            spark.config().setTheme(Theme.STANDARD);
	          
	            spark.viewConfigurer().viewOrder().as(new ViewName[]{
	            	    ViewName.DASHBOARD, 
	            	    ViewName.TEST, 
	            	    ViewName.AUTHOR, 
	            	    ViewName.CATEGORY, 
	            	    ViewName.DEVICE
	            	});
	            
	            extent = new ExtentReports();
	            extent.attachReporter(spark);
	            extent.setSystemInfo("Project", "Sauce Demo");
	            extent.setSystemInfo("Senior QA Automation Engineer", "Lakshmi Narayana Sindiri");
	        }
	        return extent;
	    }
	}

