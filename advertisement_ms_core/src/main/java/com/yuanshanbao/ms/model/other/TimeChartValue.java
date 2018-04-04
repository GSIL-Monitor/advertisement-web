package com.yuanshanbao.ms.model.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeChartValue {
	private String dateStr;
	
	private double value;
	
	private String format;

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	@SuppressWarnings("deprecation")
	public int getYear() {
		int year = -1;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(dateStr);
			year = date.getYear() + 1900;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return year;
	}
	
	@SuppressWarnings("deprecation")
	public int getMonth() {
		int month = -1;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(dateStr);
			month = date.getMonth() + 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return month;
	}
	
	@SuppressWarnings("deprecation")
	public int getDay() {
		int day = -1;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(dateStr);
			day = date.getDate();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
}
