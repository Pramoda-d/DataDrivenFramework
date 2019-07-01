package testcases;


import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.annotations.Test;

import base.BaseTest;
import utilities.TestUtil;


public class AddCustomerTest extends BaseTest {
	

	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp1")
	public  void addCustomerTest(Hashtable<String,String> data) throws IOException {
	
		checkRunMode(data);
		verifyEquals("abc","xyz");
		click("addcusttab_CSS");
		type("fname_CSS",data.get("firstname"));
		type("lname_CSS",data.get("lastname"));
		type("pcode_CSS",data.get("postcode"));
		click("acccustbtn_CSS");
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		System.out.println(alert.getText());
	
		alert.accept();

			}
	
	
		
		
	
}
