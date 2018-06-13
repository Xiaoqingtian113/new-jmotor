package com.zhucq.mobile.testcase.zhucq;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.Test;

import com.zhucq.mobile.TestCaseBase;
import com.zhucq.mobile.appium.JMotorDriver;

public class OpenBaidu extends TestCaseBase {
	@Test
	public void baiduSearch(){
		JMotorDriver<RemoteWebElement> motor = new JMotorDriver<RemoteWebElement>(driver);
		driver.get("https://m.baidu.com");
		motor.findElementWhenPresence(By.id("index-kw")).sendKeys("hello baidu search");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
