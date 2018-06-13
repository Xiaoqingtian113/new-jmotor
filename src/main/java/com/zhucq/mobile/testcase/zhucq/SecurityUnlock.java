package com.zhucq.mobile.testcase.zhucq;

import org.testng.annotations.Test;

import com.zhucq.mobile.TestCaseBase;
import com.zhucq.mobile.appium.JMotorDriver;

import io.appium.java_client.android.AndroidElement;

public class SecurityUnlock extends TestCaseBase {
	@Test
	public void unlockD(){
		JMotorDriver<AndroidElement> motor = new JMotorDriver<AndroidElement>(driver);
		
		int i=4;
		while(i>0){
			motor.sleep(2000);
			motor.pressPower();
			i--;
		}
		
		motor.sleep(1000);
		motor.pressMenu();
		motor.sleep(1000);
		motor.pressVolumeUp();
		motor.sleep(1000);
		motor.pressBack();
		motor.pressBack();
	}
}
