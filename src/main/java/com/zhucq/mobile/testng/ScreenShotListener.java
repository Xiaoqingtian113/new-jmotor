package com.zhucq.mobile.testng;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.zhucq.mobile.util.DateUtils;
import com.zhucq.mobile.util.PropertiesUtils;

public class ScreenShotListener extends TestListenerAdapter {

	@Override
	public void onTestFailure(ITestResult result) {
		super.onTestFailure(result);
		logScreenShot(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		super.onTestSkipped(result);
		logScreenShot(result);
	}

	private void logScreenShot(ITestResult result) {
		if (!result.isSuccess()) {
			Date date = new Date();
			String imageName = DateUtils.dateToStr("yyyyMMddHHmmssSSS", date) + ".png";
			Properties prop = PropertiesUtils.read("config/global.properties");
			String ip = prop.getProperty("ip");
			String port = prop.getProperty("httpPort","8080");
			File screeshot = new File("config/screenshot.png");
			System.setProperty("org.uncommons.reportng.escape-output", "false");// 解决html页面显示问题
			Reporter.log("<a href=" + "http://" + ip + ":" + port + "/images/" + imageName + " target=_blank style=color:red>"
					+ "<img src=" + screeshot.getAbsolutePath() + " style=width:16px;height:16px align=absmiddle><b>" + imageName
					+ "</b></a>", true);
			result.setAttribute("imageName", imageName);
		}
	}
}
