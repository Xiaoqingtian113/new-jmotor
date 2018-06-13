package com.zhucq.mobile.util;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QappUpload {
	
	public static void uploadAndClose(WebDriver driver, String framId, int num) throws InterruptedException {
		int uploadCount = 1;// 上传图片张数
		uploadCount = framId.equals("Id1") ? 2 : uploadCount;
		uploadCount = framId.equals("carFileTypeId") ? 2 : uploadCount;
		uploadCount = framId.equals("Pboc2") ? 4 : uploadCount;

		String legendB = ".//*[@id='" + framId + "_legend']/div/button";
		String selectB = ".//*[@id='" + framId + "_SelectButton']/lable";
		String frameStr = framId + "_iframe";
		String xpathStr = ".//*[@id='ui-id-" + num + "']/../button";
		
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(legendB))).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(selectB))).click();
		Thread.sleep(2000);
		
		WebElement newFrameElem = driver.findElement(By.id(frameStr));
		driver.switchTo().frame(newFrameElem);
		for (int i = 0; i < uploadCount; i++) {
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("pickfiles"))).click();
			String picPath = new File("res/third_script/test.jpg").getAbsolutePath();
			try {
				Runtime.getRuntime().exec("res/third_script/upload.exe " + picPath);
				Thread.sleep(3000);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		driver.switchTo().defaultContent();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathStr))).click();
	}
}
