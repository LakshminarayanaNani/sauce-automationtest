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
			{"problem_user", "Secret", false},
			{"secret_sauce", "problem_user", false},
			{"visual_user", "secret_sauce", true}
				};
	}
	
}

