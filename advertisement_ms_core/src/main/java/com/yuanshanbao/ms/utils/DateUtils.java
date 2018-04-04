package com.yuanshanbao.ms.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	public static Date parseStr2Date(String format, String dateStr) {
		DateFormat df = new SimpleDateFormat(format);
		
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String parseDate2Str(String format, Date date) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
}
