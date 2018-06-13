package com.zhucq.mobile;

public class RunningConfig {
	private String description;//用例描述，为了方便了解此用例
	private String className;//用例的类名
	private String platformName;//操作系统名称
	private String platformVersion;//操作系统版本
	private String deviceName;//设备名称
	private String browserName;//浏览器名称
	private String browserVersion;//浏览器版本
	private String appPath;//待测试的app的路径
	private String appPackage;//待测app的包
	private String appActivity;//待测app的main activity
	private String bundleId;//iPhone手机待测app的bundleId
	private String udid;//设备id
	private String appWaitActivity;//启动时当前Activity
	private String toRun;//是否执行这条用例
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getPlatformVersion() {
		return platformVersion;
	}
	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	public String getBrowserVersion() {
		return browserVersion;
	}
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	public String getAppPath() {
		return appPath;
	}
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
	public String getAppPackage() {
		return appPackage;
	}
	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}
	public String getAppActivity() {
		return appActivity;
	}
	public void setAppActivity(String appActivity) {
		this.appActivity = appActivity;
	}
	public String getBundleId() {
		return bundleId;
	}
	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getAppWaitActivity() {
		return appWaitActivity;
	}
	public void setAppWaitActivity(String appWaitActivity) {
		this.appWaitActivity = appWaitActivity;
	}
	public String getToRun() {
		return toRun;
	}
	public void setToRun(String toRun) {
		this.toRun = toRun;
	}

}
