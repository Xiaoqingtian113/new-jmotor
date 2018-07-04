package com.zhucq.mobile;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.zhucq.mobile.appium.JMotorDriver;
import com.zhucq.mobile.util.ImageUtils;
import com.zhucq.mobile.util.PropertiesUtils;
import com.zhucq.mobile.util.SshUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;

public class TestCaseBase {

	public RemoteWebDriver driver;
	public String platformName;
	public String platformVersion;
	public String deviceName;
	public String browserName;
	public String browserVersion;
	Properties prop = PropertiesUtils.read("config/global.properties");
	DesiredCapabilities caps = new DesiredCapabilities();
	
	@BeforeClass
	@Parameters({ "platformName", "platformVersion", "deviceName", "browserName", "browserVersion", "appPath",
			"appPackage", "appActivity", "bundleId", "udid", "appWaitActivity" })
	public void initDriver(@Optional("noPlatformName") String platformName,
			@Optional("noPlatformVersion") String platformVersion, @Optional("noDeviceName") String deviceName,
			@Optional("noBrowserName") String browserName, @Optional("noBrowserVersion") String browserVersion,
			@Optional("noAppPath") String appPath, @Optional("noAppPackage") String appPackage,
			@Optional("noAppActivity") String appActivity, @Optional("noBundleIdy") String bundleId,
			@Optional("noUdid") String udid, @Optional("noAppWaitActivity") String appWaitActivity) {
		System.out.println("======BeforeClass initDriver -- thread:" + Thread.currentThread().getId());
		
		if(driver != null) return;
		
		this.platformName = platformName;
		this.platformVersion = platformVersion;
		this.deviceName = deviceName;
		this.browserName = browserName;
		this.browserVersion = browserVersion;
		
		// Android Native App Test
		if (platformName.equals("Android") && browserName.equals("noBrowserName")) {
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			if (!platformVersion.equals("noPlatformVersion"))
				caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			if (!udid.equals("noUdid"))
				caps.setCapability(MobileCapabilityType.UDID, udid);
			caps.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
			caps.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Port.getUnusedPort().getPortNum());
			if (appPath.equals("noAppPath")) {
				caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
				caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
			} else
				caps.setCapability(MobileCapabilityType.APP, appPath);
			if (!appWaitActivity.equals("noAppWaitActivity"))
				caps.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, appWaitActivity);
			try {
				driver = new AndroidDriver<AndroidElement>(
						new URL(prop.getProperty("gridHubPath", "http://localhost:4723/wd/hub")), caps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return;
		}
		// Android H5 Test
		if (platformName.equals("Android") && !browserName.equals("noBrowserName")) {
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			if (!platformVersion.equals("noPlatformVersion"))
				caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			if (!udid.equals("noUdid"))
				caps.setCapability(MobileCapabilityType.UDID, udid);
			caps.setCapability(AndroidMobileCapabilityType.BROWSER_NAME, browserName);
			try {
				driver = new AndroidDriver<WebElement>(
						new URL(prop.getProperty("gridHubPath", "http://localhost:4723/wd/hub")), caps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return;
		}
		// IOS Native App Test
		if (platformName.equals("IOS") && browserName.equals("noBrowserName")) {
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			caps.setCapability(MobileCapabilityType.UDID, udid);
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCuiTest");
			caps.setCapability(IOSMobileCapabilityType.START_IWDP, "true");
			caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");
			caps.setCapability(IOSMobileCapabilityType.XCODE_ORG_ID, "B7JT4RKB36");
			caps.setCapability(IOSMobileCapabilityType.XCODE_SIGNING_ID, "124248270@qq.com");
			if (appPath.equals("noAppPath"))
				caps.setCapability(IOSMobileCapabilityType.BUNDLE_ID, bundleId);
			else
				caps.setCapability(MobileCapabilityType.APP, appPath);
			try {
				driver = new IOSDriver<IOSElement>(
						new URL(prop.getProperty("gridHubPath", "http://localhost:4723/wd/hub")), caps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return;
		}
		// IOS H5 Test
		if (platformName.equals("IOS") && !browserName.equals("noBrowserName")) {
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			caps.setCapability(MobileCapabilityType.UDID, udid);
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCuiTest");
			caps.setCapability(IOSMobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
			caps.setCapability(IOSMobileCapabilityType.START_IWDP, "true");
			caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");
			caps.setCapability(IOSMobileCapabilityType.XCODE_ORG_ID, "B7JT4RKB36");
			caps.setCapability(IOSMobileCapabilityType.XCODE_SIGNING_ID, "124248270@qq.com");
			try {
				driver = new IOSDriver<WebElement>(
						new URL(prop.getProperty("gridHubPath", "http://localhost:4723/wd/hub")), caps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return;
		}
		// Win Web Test
		if (platformName.matches("(Windows|Mac|Linux)") && !browserName.equals("noBrowserName")) {
			if (!deviceName.equals("noDeviceName"))
				caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(CapabilityType.BROWSER_NAME, browserName);
			if (!browserVersion.equals("noBrowserVersion"))
				caps.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);
			caps.setCapability(CapabilityType.PLATFORM_NAME, platformName);
			if (!platformVersion.equals("noPlatformVersion"))
				caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			try {
				driver = new RemoteWebDriver(
						new URL(prop.getProperty("gridHubPath", "http://localhost:4444/wd/hub")), caps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return;
		}
		// Windows/Mac App Test
		if (platformName.matches("(Windows|Mac)") && browserName.equals("noBrowserName")) {
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Windows");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			caps.setCapability(MobileCapabilityType.APP, appPath);
			System.out.println("Windows/Mac App Test暂时还不支持");
			return;
		}
	}

	@DataProvider(name = "dp")
	public Object[][] provideData(Method m) {
		System.out.println("======DataProvider provideData -- thread:" + Thread.currentThread().getId());
		String dataDriveConfigPath = TempProperties.dataDriveConfigDir + "/dataDrive_" + getClass().getSimpleName()
				+ ".xlsx";
		File file = new File(dataDriveConfigPath);
		if (!file.exists())
			return new Object[][] {};
		DataDriver dataDrive = new DataDriver(file, m.getName());
		return dataDrive.getData();
	}
	
	@AfterMethod
	public void quitAfterMethod(ITestResult result) {
		if(driver != null && prop.getProperty("quitDriver").equals("Method")) {
			System.out.println("======AfterMethod quitDriver -- thread:" + Thread.currentThread().getId());
			driver.quit();
			caps = null;
		}
	}
	@AfterClass
	public void quitAfterClass(ITestResult result) {
		if(driver != null && prop.getProperty("quitDriver").equals("Class")) {
			System.out.println("======AfterClass quitDriver -- thread:" + Thread.currentThread().getId());
			driver.quit();
			caps = null;
		}
	}
	@AfterTest
	public void quitAfterTest(ITestResult result) {
		if(driver != null && prop.getProperty("quitDriver").equals("Test")) {
			System.out.println("======AfterTest quitDriver -- thread:" + Thread.currentThread().getId());
			driver.quit();
			caps = null;
		}
	}
	@AfterSuite
	public void quitAfterSuite(ITestResult result) {
		if(driver != null && prop.getProperty("quitDriver").equals("Suite")) {
			System.out.println("======AfterSuite quitDriver -- thread:" + Thread.currentThread().getId());
			driver.quit();
			caps = null;
		}
	}
}
