package com.zhucq.mobile.testng;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

public class ReportReleaseListener extends TestListenerAdapter {

	@Override
	public void onFinish(ITestContext testContext) {
		// 上传报告到tomcat服务器
		File destReport = new File(System.getenv("CATALINA_HOME") + "\\webapps\\report");
		File srcReport = new File("test-output");
		try {
			FileUtils.copyDirectory(srcReport, destReport, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 上传appium log到服务器
		File srcLog = new File("logs/appium.log");
		File destLog = new File(System.getenv("CATALINA_HOME") + "\\webapps\\logs\\appium.log");
		try {
			FileUtils.copyFile(srcLog, destLog, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
