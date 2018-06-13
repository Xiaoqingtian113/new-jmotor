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

	protected RemoteWebDriver driver;
	protected String platformName;
	protected String platformVersion;
	protected String deviceName;
	protected String browserName;
	protected String browserVersion;

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
		Properties prop = PropertiesUtils.read("config/global.properties");
		DesiredCapabilities caps = new DesiredCapabilities();

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
			if (appPath.equals("noAppPath")) {
				caps.setCapability(IOSMobileCapabilityType.BUNDLE_ID, bundleId);
			} else
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
	public void saveFailedScreen(ITestResult result) {
		System.out.println("======AfterMethod saveFailedScreen -- thread:" + Thread.currentThread().getId());
		if (!result.isSuccess()) {
			String imageDir = PropertiesUtils.read("config/global.properties").getProperty("CataLinaHome") + "/webapps/images/";
			String imageName = (String) result.getAttribute("imageName");
			String imagePath = imageDir + imageName;
			String localPath = System.getProperty("java.io.tmpdir") + imageName;
			if (driver == null) {
				System.out.println("Driver已经退出，截图失败");
				return;
			}
			new JMotorDriver<>(driver).screenShot(localPath);
			if(driver instanceof AppiumDriver)
				ImageUtils.resize(localPath, localPath, 0.4);
			else
				ImageUtils.resize(localPath, localPath, 0.4);
			Properties prop = PropertiesUtils.read("config/global.properties");
			String ip = prop.getProperty("ip");
			int port = Integer.parseInt(prop.getProperty("sshPort","22"));
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			SshUtils.sftpUpload(localPath, imagePath, ip, port, user, password);
		}
	}

	@AfterClass
	public void quitServer() {
		System.out.println("======AfterClass quitServer -- thread:" + Thread.currentThread().getId());
		if(driver != null) driver.quit();
	}
}
