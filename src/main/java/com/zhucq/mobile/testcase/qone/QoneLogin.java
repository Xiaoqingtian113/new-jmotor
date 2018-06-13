package com.zhucq.mobile.testcase.qone;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.zhucq.mobile.TestCaseBase;
import com.zhucq.mobile.appium.ImageNotFoundException;
import com.zhucq.mobile.appium.JMotorDriver;
import com.zhucq.mobile.appium.JMotorTouchAction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class QoneLogin extends TestCaseBase {
	public static Logger logger = Logger.getLogger("system");
	
	@SuppressWarnings("unchecked")
	@Test(dataProvider="dp")
	public void login(HashMap<String,String> dmp) throws ImageNotFoundException{
		JMotorDriver<MobileElement> motor = new JMotorDriver<MobileElement>(driver);
		JMotorTouchAction action = new JMotorTouchAction(driver);
		List<String> engins = driver.manage().ime().getAvailableEngines();
		for(String s : engins){
			System.out.println("===================================engins:"+s);
		}
		System.out.println("========active engin:"+driver.manage().ime().getActiveEngine());
//		action.waitImageVisable("img/welcome1.png");
		action.sleep(2000);
		action.leftSwipe().perform();
//		action.waitImageVisable("img/welcome2.png");
		action.sleep(1000);
		action.leftSwipe().perform();
		action.tap("img/lijijinru.png", 0).perform();
		
//		action.press(0.5f, 0.76f).perform();
		motor.findElementWhenVisibility("Q-One登录页面", "登录手机号").sendKeys(dmp.get("userName"));
		motor.findElement("Q-One登录页面", "密码").sendKeys(dmp.get("passWord"));
		motor.findElement("Q-One登录页面", "登录按钮").click();
		action.tap("img/allow.png", 0).perform();
		motor.findElement("Q-One登录页面", "登录按钮").click();
		motor.findElementWhenClickable("首页", "签到");
		motor.sleep(1000);
		motor.pressBack();
		motor.sleep(1000);
		motor.pressBack();
		motor.sleep(1000);
		if(platformName.equals("Android")){
			((AndroidDriver<AndroidElement>)driver).startActivity(new Activity("com.quarkfinance.qone","com.quarkfinance.qone.ui.StartActivity"));
			AndroidElement ele = (AndroidElement) motor.findElement("手势解锁页面","解锁图案");
			action.wakeByGestures(ele, "1245689").perform();
			motor.sleep(1000);
		}
		
		Set<String> hds = ((AppiumDriver<MobileElement>)driver).getContextHandles();
		Iterator<String> it = hds.iterator();
		while(it.hasNext()){
			System.out.println("**************************Contexts:"+it.next());
		}
		motor.findElementWhenClickable("首页", "签到").click();
		motor.sleep(3000);
	}
}
