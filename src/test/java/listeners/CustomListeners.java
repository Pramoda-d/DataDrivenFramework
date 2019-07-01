package listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import utilities.TestUtil;

public class CustomListeners extends BaseTest implements ITestListener {


public void onTestStart(ITestResult result) {
	
	test=extent.createTest(result.getName().toUpperCase());
  }

  
  public void onTestSuccess(ITestResult result) {
	test.log(Status.PASS, result.getName().toUpperCase()+" passed");
	
  }

  
  public void onTestFailure(ITestResult result) {
System.setProperty("org.uncommons.reportng.escape-output", "false");
	  try {
		TestUtil.captureScreenshot();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    
	  Reporter.log("click the link to view screenshot"+ "<a target=\"_blank\" href="+TestUtil.screenshotName+">screenshot</a>");
	  Reporter.log("<br>");
	  Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
	  
	  test.log(Status.FAIL, result.getName().toUpperCase()+" failed with:" + result.getThrowable());
	  try {
		test.fail("failed screen:"+ test.addScreenCaptureFromPath(TestUtil.screenshotName));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  
  public void onTestSkipped(ITestResult result) {
	  BaseTest.test.log(Status.SKIP, result.getName().toUpperCase()+" skipped");
	  
  }

 
  public void onTestFailedWithTimeout(ITestResult result) {
    onTestFailure(result);
  }

  
  public void onStart(ITestContext context) {
    // not implemented
  }

  
  public void onFinish(ITestContext context) {
    // not implemented
  }


public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	// TODO Auto-generated method stub
	
}


}
