package utils;

import org.testng.annotations.DataProvider;

public class DataProviderUtils 
{

	@DataProvider(name = "loginData")
	public static Object[][] loginData() 
	{
		return new Object[][]
				{
			{"standard_user", "secret_sauce", true},
			{"locked_out_user", "secret_sauce", false},
			{"standard_user", "Secret", false},
			{"", "abcdefgh_wrong", false},
			{"lakshminarayana", "secret_sauce", false}
				};
	}
	
	@DataProvider(name = "localStorageUser")
	public Object[][] localStorageUser() {
	    return new Object[][] {
	        {"standard_user", "secret_sauce"}
	    };
	}
}

