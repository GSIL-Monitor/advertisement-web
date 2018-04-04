package com.yuanshanbao.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StrAndNumUtil
{
	private static final DecimalFormat AMOUNT_FORMAT = new DecimalFormat("0.00");

	/**
	 * 将金额格式化为 0.00的格式
	 * 
	 * @param big
	 * @return
	 */
	public static String formatAmount(BigDecimal amount)
	{
		if (amount == null)
		{
			return "";
		}
		return AMOUNT_FORMAT.format(amount);
	}

	public static String shortStrings(String source, int num)
	{
		if (source == null)
		{
			source = "";
		}

		if (source.length() > num)
		{
			source = source.substring(0, num - 1);
			source = source + "...";
		}

		return source;
	}

	public static String getSubStrBeforeBlank(String source)
	{
		if (source == null)
		{
			source = "";
		}

		if (source.indexOf(" ") <= -1)
		{
			return source;
		}

		return source.substring(0, source.indexOf(" "));
	}

	/**
	 * 将所有的复式投注转换为单式票
	 * @param bidBalls
	 * @return
	 */
	public static List<String> splitToSimple(String[] bidBalls)
	{
		List<String> stakeNumbers = new ArrayList<String>();
		if (bidBalls == null || bidBalls.length < 1)
		{
			return stakeNumbers;
		}
		//		for (int i = 0; i < bidBalls[0].length(); i++)
		//		{
		//			stakeNumbers.add("" + bidBalls[0].charAt(i));
		//		}
		for (int i = 0; i < bidBalls.length; i++)
		{
			if (stakeNumbers.isEmpty())
			{
				for (int index = 0; index < bidBalls[i].length(); index++)
				{
					stakeNumbers.add("" + bidBalls[i].charAt(index));
				}
			}
			else
			{
				List<String> newStakeNumbers = new ArrayList<String>();
				for (int index = 0; index < bidBalls[i].length(); index++)
				{
					for (String sn : stakeNumbers)
					{
						newStakeNumbers.add(sn + " " + bidBalls[i].charAt(index));
					}
				}
				stakeNumbers.clear();
				stakeNumbers = newStakeNumbers;
			}
		}

		return stakeNumbers;
	}

	/**
	 * 过滤字符串首尾斜杠
	 * @param path
	 * @return
	 */
	public static String trimSlash(String path){
		if(StringUtils.isEmpty(path)){
			return "";
		}
		while(path.startsWith("/")){
			path = path.substring(1, path.length());
		}
		while(path.endsWith("/")){
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}
}
