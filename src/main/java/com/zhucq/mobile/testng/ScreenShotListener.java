package com.zhucq.mobile.testng;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.zhucq.mobile.TestCaseBase;
import com.zhucq.mobile.appium.JMotorDriver;
import com.zhucq.mobile.util.DateUtils;
import com.zhucq.mobile.util.ImageUtils;
import com.zhucq.mobile.util.PropertiesUtils;
import com.zhucq.mobile.util.SshUtils;

import io.appium.java_client.AppiumDriver;

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
		TestCaseBase tb = (TestCaseBase) result.getInstance();
		if (tb.driver == null) {
			System.out.println("Driver已经退出，截图失败");
			return;
		}
		
		Date date = new Date();
		String imageName = DateUtils.dateToStr("yyyyMMddHHmmssSSS", date) + ".png";
		Properties prop = PropertiesUtils.read("config/global.properties");
		String ip = prop.getProperty("ip");
		String httpPort = prop.getProperty("httpPort","8080");
		File bugIcon = new File("config/screenshot.png");
		System.setProperty("org.uncommons.reportng.escape-output", "false");// 解决html页面显示问题
		Reporter.log("<a href=" + "http://" + ip + ":" + httpPort + "/images/" + imageName + " target=_blank style=color:red>"
				+ "<img src=" + bugIcon.getAbsolutePath() + " style=width:16px;height:16px align=absmiddle><b>" + imageName
				+ "</b></a>", true);
		
		File screen = tb.driver.getScreenshotAs(OutputType.FILE);
		String imageDir = prop.getProperty("CataLinaHome") + "/webapps/images/";
		String imagePath = imageDir + imageName;
		int sshPort = Integer.parseInt(prop.getProperty("sshPort","22"));
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		SshUtils.sftpUpload(screen.getAbsolutePath(), imagePath, ip, sshPort, user, password);
	}
}
