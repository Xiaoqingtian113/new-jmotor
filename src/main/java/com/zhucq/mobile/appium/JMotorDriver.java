package com.zhucq.mobile.appium;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zhucq.mobile.PageElement;
import com.zhucq.mobile.TempProperties;
import com.zhucq.mobile.util.PropertiesUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.functions.ExpectedCondition;


@SuppressWarnings("unchecked")
public class JMotorDriver<T extends RemoteWebElement>{
	RemoteWebDriver driver;
	
	public JMotorDriver(RemoteWebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 线程等待
	 * 
	 * @param time
	 *            毫秒数
	 */
	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 按下返回键，只适用于android手机
	 */
	public void pressBack() {
		((AndroidDriver<T>) driver).pressKeyCode(AndroidKeyCode.BACK);
	}

	/**
	 * 按下Home键，只适用于android手机
	 */
	public void pressHome() {
		((AndroidDriver<T>) driver).pressKeyCode(AndroidKeyCode.HOME);
	}

	/**
	 * 按下菜单键，只适用于android手机
	 */
	public void pressMenu() {
		((AndroidDriver<T>) driver).pressKeyCode(AndroidKeyCode.MENU);
	}

	/**
	 * 按下电源键，只适用于android手机
	 */
	public void pressPower() {
		((AndroidDriver<T>) driver).pressKeyCode(AndroidKeyCode.KEYCODE_POWER);
	}

	/**
	 * 按下音量+键，只适用于android手机
	 */
	public void pressVolumeUp() {
		((AndroidDriver<T>) driver).pressKeyCode(AndroidKeyCode.KEYCODE_VOLUME_UP);
	}

	/**
	 * 按下音量-键，只适用于android手机
	 */
	public void pressVolumeDown() {
		((AndroidDriver<T>) driver).pressKeyCode(AndroidKeyCode.KEYCODE_VOLUME_DOWN);
	}
	
	/**
	 * 根据页面和元素名称去定位元素。
	 * 
	 * @param page
	 * @param element
	 * @return
	 */
	public T findElement(String page, String element) {
		return (T) driver.findElement(getBy(page, element));
	}
	
	public T findElement(By by) {
		return (T) driver.findElement(by);
	}
	
	/**
	 * 判断元素是否存在
	 * @param page
	 * @param element
	 * @return
	 */
	public boolean isElementExist(String page, String element){
		try{
			driver.findElement(getBy(page, element));
		}catch(NoSuchElementException ex) {
			return false;
		}
		return true;
	}
	
	/**
	 * 等待元素存在后，根据页面和元素名称去定位元素。
	 * @param page
	 * @param element
	 * @return
	 */
	public T findElementWhenPresence(String page, String element) {
		return (T) new WebDriverWait(driver, getTimeout()).until(ExpectedConditions.presenceOfElementLocated(getBy(page, element)));
	}
	
	public T findElementWhenPresence(By by) {
		return (T) new WebDriverWait(driver, getTimeout()).until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	/**
	 * 等待元素可以点击后，根据页面和元素名称去定位元素。
	 * @param page
	 * @param element
	 * @return
	 */
	public T findElementWhenClickable(String page, String element) {
		return (T) new WebDriverWait(driver, getTimeout()).until(ExpectedConditions.elementToBeClickable(getBy(page, element)));
	}
	
	public T findElementWhenClickable(By by) {
		return (T) new WebDriverWait(driver, getTimeout()).until(ExpectedConditions.elementToBeClickable(by));
	}
	
	/**
	 * 等待元素显示后，根据页面和元素名称去定位元素。
	 * @param page
	 * @param element
	 * @return
	 */
	public T findElementWhenVisibility(String page, String element) {
		return (T) new WebDriverWait(driver, getTimeout()).until(ExpectedConditions.visibilityOfElementLocated(getBy(page, element)));
	}
	
	public T findElementWhenVisibility(By by) {
		return (T) new WebDriverWait(driver, getTimeout()).until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	/**
	 * 等待元素不显示。
	 * @param page
	 * @param element
	 * @return
	 */
	public boolean WaitElementUntilInvisibility(String page, String element) {
		return new WebDriverWait(driver, getTimeout()).until(ExpectedConditions.invisibilityOfElementLocated(getBy(page, element)));
	}
	
	public boolean WaitElementUntilInvisibility(By by) {
		return new WebDriverWait(driver, getTimeout()).until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	/**
	 * 等待页面完全加载后，根据页面和元素名称去定位元素。
	 * @param page
	 * @param element
	 * @return
	 */
	public T findElementWhenPageLoaded(String page, String element) {
		return (T) new WebDriverWait(driver, getTimeout()).until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver input) {
				boolean pageLoaded = ((JavascriptExecutor) input).executeScript("return document.readyState")
						.equals("complete");
				boolean queryActive = ((JavascriptExecutor) input).executeScript("return jQuery.active").equals("0");
				return pageLoaded && queryActive ? input.findElement(getBy(page, element)) : null;
			}
		});
	}
	
	public T findElementWhenPageLoaded(By by) {
		return (T) new WebDriverWait(driver, getTimeout()).until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver input) {
				boolean pageLoaded = ((JavascriptExecutor) input).executeScript("return document.readyState")
						.equals("complete");
				boolean queryActive = ((JavascriptExecutor) input).executeScript("return jQuery.active").equals("0");
				return pageLoaded && queryActive ? input.findElement(by) : null;
			}
		});
	}
	
	/**
	 * 截屏，并保存到指定的路径
	 * @param savePath
	 */
	public void screenShot(String savePath) {
		File screen = driver.getScreenshotAs(OutputType.FILE);
		File screenFile = new File(savePath);
		try {
			FileUtils.copyFile(screen, screenFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对元素截图
	 * @param element
	 * @param file
	 * @throws Exception
	 */
	public void screenShot(WebElement element, String savePath) {
		Point location = element.getLocation();
		Dimension size = element.getSize();
		screenShot(location, size, savePath);
	}
	
	/**
	 * 根据区域截图
	 * @param location 左上角的坐标
	 * @param size 矩形区域
	 * @param savePath 保存路径
	 */
	public void screenShot(Point location, Dimension size, String savePath) {
		byte[] imageByte = driver.getScreenshotAs(OutputType.BYTES);
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(new ByteArrayInputStream(imageByte));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedImage subImage = originalImage.getSubimage(location.getX(), location.getY(), size.getWidth(),
				size.getHeight());
		File screenFile = new File(savePath);
		try {
			ImageIO.write(subImage, "png", screenFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据页面和元素的名称，获取定位的By对象，内部使用。
	 */
	public By getBy(String page, String element) {
		String pageElementConfigFile = TempProperties.pageElementConfigFile;
		PageElement pe = new PageElement(new File(pageElementConfigFile));
		return pe.getBy(page, element);
	}

	/**
	 * 获取配置的显式等待超时，内部使用。
	 * @return
	 */
	private long getTimeout() {
		Properties prop = PropertiesUtils.read("config/global.properties");
		return Long.parseLong(prop.getProperty("webDriverWaitTimeout"));
	}
}
