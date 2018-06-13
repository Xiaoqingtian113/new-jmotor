package com.zhucq.mobile.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static long dayDifference(String pattern, String from, String to){
		SimpleDateFormat simpleFormat = new SimpleDateFormat(pattern);
		try {
			Date fromDate = simpleFormat.parse(from);
			Date toDate = simpleFormat.parse(to);
			long diff = fromDate.getTime() > toDate.getTime() ? fromDate.getTime()-toDate.getTime() : toDate.getTime()-fromDate.getTime();
			return diff/(24*3600*1000);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static String daysAdd(String pattern, String str, int num, int field) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(pattern);
		try {
			Date date = simpleFormat.parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(field, num);
			return simpleFormat.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String dateToStr(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str = format.format(date);
		return str;
	}
	
	public static Date strToDate(String pattern, String str) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
