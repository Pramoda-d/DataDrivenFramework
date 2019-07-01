package testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
 import org.testng.annotations.Test;

import base.BaseTest;
import utilities.TestUtil;

public class OpenAccountTest extends BaseTest {
	
	@Test (dataProviderClass=TestUtil.class,dataProvider="dp1")
	public void openAccountTest(Hashtable<String,String> data) {
		
	//	checkRunMode(data);
		click("openaccttab_CSS");
		
	select("custdrop_CSS", data.get("customer"));
	select("currencydrop_CSS",data.get("currency"));
	click("processbtn_CSS");
	Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	System.out.println(alert.getText());
	alert.accept();
		
	}

}
