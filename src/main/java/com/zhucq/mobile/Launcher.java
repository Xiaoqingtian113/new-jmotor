package com.zhucq.mobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.PropertyConfigurator;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;

import org.testng.xml.XmlTest;

public class Launcher {
	public static void main(String[] args) {

		// log4j配置
		PropertyConfigurator.configure("config/log4j.properties");

		// 根据参数设置各配置文件路径
		if (args.length == 0) {
			System.out.println("======没有参数，请设置参数重新运行======");
			return;
		}
		TempProperties.testCaseConfigFile = "config/" + args[0] + "/testCase.xlsx";
		TempProperties.pageElementConfigFile = "config/" + args[0] + "/pageElement.xlsx";
		TempProperties.dataDriveConfigDir = "config/" + args[0];
		
		//创建一个TestNG.xml的Model
		RunningConfigManager runConfigMgr = new RunningConfigManager(new File(TempProperties.testCaseConfigFile));
		List<RunningConfig> cfgList = runConfigMgr.getBeanList();
		int clsNum = cfgList.size();
		
		XmlSuite suite = new XmlSuite();
		suite.setName("Jmotor自动化测试");
		suite.setParallel(ParallelMode.TESTS);
		suite.setThreadCount(clsNum);
		ArrayList<String> listeners = new ArrayList<String>();
		listeners.add("org.uncommons.reportng.HTMLReporter");
		listeners.add("com.quark.mobile.testng.ScreenShotListener");
		listeners.add("com.quark.mobile.testng.RetryListener");
		suite.setListeners(listeners);
		
		for(int i=0; i<clsNum; i++){
			RunningConfig rc = cfgList.get(i);
			
			XmlTest test = new XmlTest(suite);
			String testName = "";
			try {
				testName = BeanUtils.getProperty(rc,"className") + "-"
						+ BeanUtils.getProperty(rc,"platformName")
						+ BeanUtils.getProperty(rc,"platformVersion") + "-"
						+ BeanUtils.getProperty(rc,"deviceName") + "-"
						+ BeanUtils.getProperty(rc,"browserName")
						+ BeanUtils.getProperty(rc, "browserVersion") + "-"
						+ BeanUtils.getProperty(rc,"udid");
			} catch (Exception e) {
				e.printStackTrace();
			}
			test.setName(testName.replaceAll("[-]+", "-").replaceAll("-$", ""));
			test.setPreserveOrder(true);
			Map<String, String> parameters = new HashMap<String, String>();
			try {
				if(!BeanUtils.getProperty(rc,"platformName").equals(""))
					parameters.put("platformName", BeanUtils.getProperty(rc,"platformName"));
				if(!BeanUtils.getProperty(rc,"platformVersion").equals(""))
					parameters.put("platformVersion", BeanUtils.getProperty(rc,"platformVersion"));
				if(!BeanUtils.getProperty(rc,"deviceName").equals(""))
					parameters.put("deviceName", BeanUtils.getProperty(rc,"deviceName"));
				if(!BeanUtils.getProperty(rc,"browserName").equals(""))
					parameters.put("browserName", BeanUtils.getProperty(rc,"browserName"));
				if(!BeanUtils.getProperty(rc, "browserVersion").equals(""))
					parameters.put("browserVersion", BeanUtils.getProperty(rc, "browserVersion"));
				if(!BeanUtils.getProperty(rc,"appPath").equals(""))
					parameters.put("appPath", BeanUtils.getProperty(rc,"appPath"));
				if(!BeanUtils.getProperty(rc,"appPackage").equals(""))
					parameters.put("appPackage", BeanUtils.getProperty(rc,"appPackage"));
				if(!BeanUtils.getProperty(rc,"appActivity").equals(""))
					parameters.put("appActivity", BeanUtils.getProperty(rc,"appActivity"));
				if(!BeanUtils.getProperty(rc,"bundleId").equals(""))
					parameters.put("appActivity", BeanUtils.getProperty(rc,"bundleId"));
				if(!BeanUtils.getProperty(rc,"appWaitActivity").equals(""))
					parameters.put("appWaitActivity", BeanUtils.getProperty(rc,"appWaitActivity"));
				if(!BeanUtils.getProperty(rc,"udid").equals(""))
					parameters.put("udid", BeanUtils.getProperty(rc,"udid"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			test.setParameters(parameters);
			
			XmlClass[] classes = new XmlClass[1];
			try {
				classes[0] = new XmlClass("com.quark.mobile.testcase." + args[0] + "." + BeanUtils.getProperty(rc,"className"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			test.setXmlClasses(Arrays.asList(classes));
		}
		//执行TestNG.xml并且保持文件
		TestNG tng = new TestNG();
		tng.setXmlSuites(Arrays.asList(new XmlSuite[] { suite }));
		PrintWriter tngxml = null;
		try {
			tngxml = new PrintWriter("testng.xml");
			tngxml.println(suite.toXml());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			tngxml.close();
		}
		tng.run();
	}
}
