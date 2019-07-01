package utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import base.BaseTest;

public class TestUtil extends BaseTest {
	
	public static String filepath;
	public static String  screenshotName;
	public static void captureScreenshot() throws IOException {
		
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = "errorscreen_"+d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		filepath = System.getProperty("user.dir")+ "\\target\\surefire-reports\\html\\"+screenshotName;
		FileUtils.copyFile(file, new File(filepath));
	
	}
	
	@DataProvider(name="dp1")
	public static Object[][] getData(Method m) {
		
		String sheetname = m.getName();
		int rows = excel.getRowCount(sheetname);
		int cols = excel.getColumnCount(sheetname);
		Object[][] data = new Object[rows-1][1];
		Hashtable<String,String> table = null;
		for (int rownum=2;rownum<=rows;rownum++) {
			table = new Hashtable<String,String>();
			for(int colnum=0;colnum<cols;colnum++) {
				table.put(excel.getCellData(sheetname, colnum, 1), excel.getCellData(sheetname, colnum, rownum));
				data[rownum-2][0]=table;
			}
		}
		return data;
	}
}
