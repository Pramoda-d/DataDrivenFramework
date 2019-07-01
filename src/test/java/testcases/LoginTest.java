package testcases;


import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;


public class LoginTest extends BaseTest {

	@Test(groups="P1")
	public static void bankMngrLogin() {
		click("mgrlbtn_CSS");
		
		Assert.assertTrue(isElementPresent("addcusttab_CSS"),"Login not successful");
		log.info("Login successful!!!");
		Assert.fail();
			}
	
	
		
	}

