package utils;

import org.openqa.selenium.WebElement;

public class ElementUtils {

	public static String getText(WebElement element) {
	    try {
	        String tag = element.getTagName().toLowerCase();
	        if (tag.equals("input") || tag.equals("textarea") || tag.equals("button")) {
	            String value = element.getAttribute("value");
	            return value != null ? value.trim() : "";
	        }

	        return element.getText().trim();
	    } catch (Exception e) {
	        System.err.println("Error getting text from element: " + e.getMessage());
	        return "";
	    }
	}


	public static void click(WebElement element) {
		try {
			element.click();
		}
		catch (Exception e) {
			System.err.println("Error clicking element: " + e.getMessage());
		}
	}

	public static void sendKeys(WebElement element, String value) {
		try {
			element.clear();
			element.sendKeys(value);
		} 
		catch (Exception e) {
			System.err.println("Error sending keys: " + e.getMessage());
		}
	}
}
