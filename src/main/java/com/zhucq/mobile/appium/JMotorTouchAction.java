package com.zhucq.mobile.appium;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.zhucq.mobile.util.ImageUtils;
import com.zhucq.mobile.util.PropertiesUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class JMotorTouchAction extends TouchAction<JMotorTouchAction> {
	private int width;
	private int height;
	private RemoteWebDriver driver;
	
	@SuppressWarnings("unchecked")
	public JMotorTouchAction(RemoteWebDriver driver) {
		super((AppiumDriver<MobileElement>)driver);
		width = driver.manage().window().getSize().width;
		height = driver.manage().window().getSize().height;
		this.driver = driver;
	}

	/**
	 * 线程等待
	 * @param time
	 */
	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向左滑动
	 * @return
	 */
	public JMotorTouchAction leftSwipe() {
		press(PointOption.point(Math.round(width*3/4), Math.round(height/2)))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(250)))
		.moveTo(PointOption.point(Math.round(width/10), Math.round(height/2)))
		.release();
		return this;
	}

	/**
	 * 向右滑动
	 * @return
	 */
	public JMotorTouchAction rightSwipe() {
		press(PointOption.point(Math.round(width*1/4), Math.round(height/2)))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(250)))
		.moveTo(PointOption.point(Math.round(width*9/10), Math.round(height/2)))
		.release();
		return this;
	}

	/**
	 * 向上滑动
	 * @return
	 */
	public JMotorTouchAction upSwipe() {
		press(PointOption.point(Math.round(width/2), Math.round(height*3/4)))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(250)))
		.moveTo(PointOption.point(Math.round(width/2), Math.round(height/4)))
		.release();
		return this;
	}

	/**
	 * 向下滑动
	 * @return
	 */
	public JMotorTouchAction downSwipe() {
		press(PointOption.point(Math.round(width/2), Math.round(height/4)))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
		.moveTo(PointOption.point(Math.round(width/2), Math.round(height*3/4)))
		.release();
		return this;
	}
	
	/**
	 * 按照屏幕坐标比例press，如宽占屏幕80%，高占90%，则参数为0.8f,0.9f
	 * @param x
	 * @param y
	 * @return
	 */
	public JMotorTouchAction press(float x, float y) {
		press(PointOption.point(Math.round(width * x), Math.round(height * y)));
		return this;
	}
	
	/**
	 * 按照元素坐标press
	 * @param element
	 * @return
	 */
	public JMotorTouchAction press(WebElement element) {
		press(ElementOption.element(element));
		return this;
	}
	
	public JMotorTouchAction press(String img, int index) throws ImageNotFoundException{
		Point p = getCordinate(img, index);
		press(PointOption.point(p.getX(), p.getY()));
		return this;
	}
	
	/**
	 * 按照屏幕坐标比例moveTo，如宽占屏幕80%，高占90%，则参数为0.8f,0.9f
	 * @param x
	 * @param y
	 * @return
	 */
	public JMotorTouchAction moveTo(float x, float y) {
		moveTo(PointOption.point(Math.round(width * x), Math.round(height * y)));
		return this;
	}
	
	/**
	 * 按照元素坐标moveTo
	 * @param element
	 * @return
	 */
	public JMotorTouchAction moveTo(WebElement element) {
		moveTo(ElementOption.element(element));
		return this;
	}
	
	public JMotorTouchAction moveTo(String img, int index) throws ImageNotFoundException{
		Point p = getCordinate(img, index);
		moveTo(PointOption.point(p.getX(), p.getY()));
		return this;
	}
	
	/**
	 * 按照屏幕坐标比例longPress，如宽占屏幕80%，高占90%，则参数为0.8f,0.9f
	 * @param x
	 * @param y
	 * @return
	 */
	public JMotorTouchAction longPress(float x, float y) {
		longPress(PointOption.point(Math.round(width * x), Math.round(height * y)));
		return this;
	}
	
	/**
	 * 按照元素坐标longPress
	 * @param element
	 * @return
	 */
	public JMotorTouchAction longPress(WebElement element) {
		longPress(LongPressOptions.longPressOptions()
				.withPosition(ElementOption.element(element)));
		return this;
	}
	
	public JMotorTouchAction longPress(String img, int index) throws ImageNotFoundException{
		Point p = getCordinate(img, index);
		longPress(PointOption.point(p.getX(), p.getY()));
		return this;
	}
	
	/**
	 * 按照屏幕坐标比例longPress，如宽占屏幕80%，高占90%，则参数为0.8f,0.9f
	 * @param x
	 * @param y
	 * @param time 长按时间
	 * @return
	 */
	public JMotorTouchAction longPress(float x, float y, long time) {
		longPress(LongPressOptions.longPressOptions()
				.withPosition(PointOption.point(Math.round(width * x), Math.round(height * y)))
				.withDuration(Duration.ofMillis(time)));
		return this;
	}
	
	/**
	 * 按照元素坐标longPress
	 * @param element
	 * @param time 长按时间
	 * @return
	 */
	public JMotorTouchAction longPress(WebElement element, long time) {
		longPress(LongPressOptions.longPressOptions()
				.withPosition(ElementOption.element(element))
				.withDuration(Duration.ofMillis(time)));
		return this;
	}
	
	public JMotorTouchAction longPress(String img, int index, long time) throws ImageNotFoundException{
		Point p = getCordinate(img, index);
		longPress(LongPressOptions.longPressOptions()
				.withPosition(PointOption.point(p.getX(), p.getY()))
				.withDuration(Duration.ofMillis(time)));
		return this;
	}
	
	/**
	 * 按照屏幕坐标比例tap，如宽占屏幕80%，高占90%，则参数为0.8f,0.9f
	 * @param x
	 * @param y
	 * @return
	 */
	public JMotorTouchAction tap(float x, float y) {
		tap(PointOption.point(Math.round(width * x), Math.round(height * y)));
		return this;
	}
	
	/**
	 * 按照屏幕坐标比例tap，如宽占屏幕80%，高占90%，则参数为0.8f,0.9f
	 * @param x
	 * @param y
	 * @param tapsCount 点击次数
	 * @return
	 */
	public JMotorTouchAction tap(float x, float y, int tapsCount) {
		tap(TapOptions.tapOptions()
				.withPosition(PointOption.point(Math.round(width * x), Math.round(height * y)))
				.withTapsCount(tapsCount));
		return this;
	}
	
	/**
	 * 按照元素坐标tap
	 * @param element
	 * @return
	 */
	public JMotorTouchAction tap(WebElement element) {
		tap(ElementOption.element(element));
		return this;
	}
	
	/**
	 * 按照元素坐标tap
	 * @param element
	 * @param tapsCount 点击次数
	 * @return
	 */
	public JMotorTouchAction tap(WebElement element, int tapsCount) {
		tap(TapOptions.tapOptions()
				.withPosition(ElementOption.element(element))
				.withTapsCount(tapsCount));
		return this;
	}
	
	/**
	 * 按照元素截图tap
	 * @param img
	 * @param index 有多个相同样式的元素时，需要指定索引定位元素，如果只有一个，则填写0。
	 * @return
	 * @throws ImageNotFoundException 
	 */
	public JMotorTouchAction tap(String img, int index) throws ImageNotFoundException{
		Point p = getCordinate(img, index);
		tap(PointOption.point(p.getX(), p.getY()));
		return this;
	}
	
	/**
	 * 按照元素截图tap
	 * @param img
	 * @param index 有多个相同样式的元素时，需要指定索引定位元素，如果只有一个，则填写0。
	 * @param tapsCount 点击次数
	 * @return
	 * @throws ImageNotFoundException 
	 */
	public JMotorTouchAction tap(String img, int index, int tapsCount) throws ImageNotFoundException{
		Point p = getCordinate(img, index);
		tap(TapOptions.tapOptions()
				.withPosition(PointOption.point(p.getX(), p.getY()))
				.withTapsCount(tapsCount));
		return this;
	}
	
	/**
	 * 按照元素截图定位，返回坐标
	 * @param img
	 * @param index 有多个相同样式的元素时，需要指定索引定位元素，如果只有一个，则填写0。
	 * @return
	 * @throws ImageNotFoundException
	 */
	public Point getCordinate(String img, int index) throws ImageNotFoundException{
		
		long time = getTimeout();
		
		while(true){
			if(time<0)
				throw new ImageNotFoundException();
			try{
				File screen = driver.getScreenshotAs(OutputType.FILE);
				Point p = ImageUtils.findAllImage(img, screen.getAbsolutePath()).get(index);
				return p;
			} catch(IndexOutOfBoundsException ex){
				time-=1;
				sleep(500);
			}
		}
	}
	
	/**
	 * 等待某个画面图片出现,直到超时
	 * @param img
	 * @throws ImageNotFoundException
	 */
	public void waitImageVisable(String img) throws ImageNotFoundException{
		
		long time = getTimeout();
		
		while(true){
			if(time<0)
				throw new ImageNotFoundException();
			try{
				File screen = driver.getScreenshotAs(OutputType.FILE);
				ImageUtils.findAllImage(img, screen.getAbsolutePath()).get(0);
				return;
			} catch(IndexOutOfBoundsException ex){
				time-=1;
				sleep(1000);
			}
		}
	}

	/**
	 * 判断当前页面上是否存在截图
	 * @param img
	 * @return
	 */
	public boolean isImageExist(String img){
		boolean flag = false;
		File screen = driver.getScreenshotAs(OutputType.FILE);
		List<org.openqa.selenium.Point> pl = ImageUtils.findAllImage(img, screen.getAbsolutePath());
		if(!pl.isEmpty()){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 九宫格解锁
	 * @param element
	 * @param pwd
	 * @return
	 */
	public JMotorTouchAction wakeByGestures(AndroidElement element, String pwd) {
		int startx = element.getLocation().getX();
		int starty = element.getLocation().getY();
		int offsetx = element.getSize().getWidth();
		int offsety = element.getSize().getHeight();

		Map<Character, Point> matrix = new HashMap<Character, Point>();
		matrix.put('1', new Point(Math.round(startx + offsetx / 6), Math.round(starty + offsety / 6)));
		matrix.put('2', new Point(Math.round(startx + offsetx / 2), Math.round(starty + offsety / 6)));
		matrix.put('3', new Point(Math.round(startx + offsetx * 5 / 6), Math.round(starty + offsety / 6)));
		matrix.put('4', new Point(Math.round(startx + offsetx / 6), Math.round(starty + offsety / 2)));
		matrix.put('5', new Point(Math.round(startx + offsetx / 2), Math.round(starty + offsety / 2)));
		matrix.put('6', new Point(Math.round(startx + offsetx * 5 / 6), Math.round(starty + offsety / 2)));
		matrix.put('7', new Point(Math.round(startx + offsetx / 6), Math.round(starty + offsety * 5 / 6)));
		matrix.put('8', new Point(Math.round(startx + offsetx / 2), Math.round(starty + offsety * 5 / 6)));
		matrix.put('9', new Point(Math.round(startx + offsetx * 5 / 6), Math.round(starty + offsety * 5 / 6)));

		press(PointOption.point(matrix.get(pwd.charAt(0)).getX(), matrix.get(pwd.charAt(0)).getY()));
		for (int i = 1; i < pwd.length(); i++) {
			int x = matrix.get(pwd.charAt(i)).getX() - matrix.get(pwd.charAt(i - 1)).getX();
			int y = matrix.get(pwd.charAt(i)).getY() - matrix.get(pwd.charAt(i - 1)).getY();
			waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)));
			moveTo(PointOption.point(x, y));
		}
		release();
		return this;
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
