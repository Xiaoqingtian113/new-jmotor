package com.zhucq.mobile.testcase.ufo;

import java.util.HashMap;
import org.testng.annotations.Test;

import com.zhucq.mobile.TestCaseBase;
import com.zhucq.mobile.appium.ImageNotFoundException;
import com.zhucq.mobile.appium.JMotorDriver;
import com.zhucq.mobile.appium.JMotorTouchAction;

import io.appium.java_client.android.AndroidElement;

public class LoginUFO extends TestCaseBase{
	
	@Test(dataProvider="dp")
	public void login(HashMap<String,String> dmp) throws ImageNotFoundException{
		JMotorTouchAction jmotorTouch = new JMotorTouchAction(driver);
		JMotorDriver<AndroidElement> jmotorDriver = new JMotorDriver<AndroidElement>(driver);
		jmotorTouch.sleep(3000);
		
		jmotorTouch.leftSwipe().perform();
		jmotorTouch.sleep(1000);
		jmotorTouch.leftSwipe().perform();
		jmotorTouch.sleep(1000);
		
//		jmotorTouch.tap(0.5f,0.9f).perform();
		jmotorTouch.tap("img/tiyan.png",0).perform();
		jmotorTouch.sleep(1000);
		
		jmotorTouch.upSwipe().perform();
		jmotorTouch.sleep(1000);
		jmotorTouch.tap(jmotorDriver.findElementWhenPresence("主页面","注册登录")).perform();
		jmotorDriver.findElementWhenPresence("登录页面","手机号").sendKeys(dmp.get("userName"));
		jmotorDriver.findElementWhenPresence("登录页面","密码").sendKeys(dmp.get("passWord"));
		jmotorTouch.tap(jmotorDriver.findElementWhenPresence("登录页面","登录按钮")).perform();
		jmotorTouch.tap(jmotorDriver.findElementWhenPresence("提示","提示1")).perform();
//		jmotorTouch.tap(jmotorDriver.findElementWhenPresence("主页","我的")).perform();
		jmotorTouch.tap("img/wode.png",0).perform();
		jmotorTouch.tap(jmotorDriver.findElementWhenPresence("提示","提示2")).perform();
//		jmotorTouch.tap(jmotorDriver.findElementWhenPresence("主页","产品列表")).perform();
		jmotorTouch.tap("img/chanpin.png",0).perform();
		
		
		jmotorDriver.sleep(5000);
	}
}
