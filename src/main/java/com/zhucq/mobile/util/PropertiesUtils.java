package com.zhucq.mobile.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	
	public static Properties read(String path) {
		Properties prop = new Properties();
		FileInputStream fins = null;
		try {
			fins = new FileInputStream(path);
			prop.load(fins);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				fins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

	public static void save(Properties prop, String path) {
		FileOutputStream fous = null;
		try {
			fous = new FileOutputStream(path);
			prop.store(fous, "runtime properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fous.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
