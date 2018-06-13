/**
 * 页面元素对象库类，提供测试页面和元素的定位
 * @author chunqingzhu
 * @version 1.0
 * @time 2017-8-17
 */

package com.zhucq.mobile;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;

import com.zhucq.mobile.appium.JMotorBy;

public class PageElement extends ExcelModel {
	List<HashMap<String,String>> mpList;

	public PageElement(File f) {
		super(f);
		mpList = getSheetAsMapList(0);
	}

	public Set<String> getPages() {
		Set<String> pages = new HashSet<String>();
		Iterator<HashMap<String,String>> it = mpList.iterator();
		while (it.hasNext()) {
			HashMap<String,String> mp = it.next();
			pages.add(mp.get("页面名称"));
		}
		return pages;
	}

	public Set<String> getElements(String page) {
		Set<String> elements = new HashSet<String>();
		Iterator<HashMap<String,String>> it = mpList.iterator();
		while (it.hasNext()) {
			HashMap<String,String> mp = it.next();
			if (mp.get("页面名称") == page)
				elements.add(mp.get("页面元素名称"));
		}
		return elements;
	}

	public String getlocateType(String page, String element) {
		String locateType = null;
		Iterator<HashMap<String,String>> it = mpList.iterator();
		while (it.hasNext()) {
			HashMap<String,String> mp = it.next();
			if (mp.get("页面名称").equals(page) && mp.get("页面元素名称").equals(element))
				locateType = mp.get("页面元素定位方式");
		}
		return locateType;
	}

	public String getLocateExp(String page, String element) {
		String locateExp = null;
		Iterator<HashMap<String,String>> it = mpList.iterator();
		while (it.hasNext()) {
			HashMap<String,String> mp = it.next();
			if (mp.get("页面名称").equals(page) && mp.get("页面元素名称").equals(element))
				locateExp = mp.get("定位表达式");
		}
		return locateExp;
	}

	public By getBy(String page, String element) {
		return JMotorBy.getBy(getlocateType(page, element), getLocateExp(page, element));
	}
}
