package com.congxing.core.utils;

import java.util.Calendar;
import java.util.Date;


public class MonthUtils {
	
	/**
	 * 取当前月份
	 * @return
	 */
	public static String getCurrentMonth(){
		return DateFormatFactory.getShortMonthFormat().format(new Date());
	}
	
	/**
	 * 取月份month的上一个月份
	 * @param month
	 * @return
	 */
	public static String getLastMonth(String month){
		return getOffsetMonth(month, -1);
	}
	
	/**
	 * 取当前月份的上一个月份
	 * @return
	 */
	public static String getLastMonth(){
		return getOffsetMonth(getCurrentMonth(), -1);
	}
	
	/**
	 * 取指定月份第一天
	 * @param month
	 * @return
	 */
	public static String getBeginDayOfMonth(String month){
		return month + "01";
	}
	
	/**
	 * 取指定月份的最后一天
	 * @param month
	 * @return
	 */
	public static String getEndDayOfMonth(String month){
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(new Date(DateUtils.getEndTimeOfMonth(month).getTime()));

		int c_year = calendar.get(Calendar.YEAR);
		int c_month = calendar.get(Calendar.MONTH) + 1;
		int c_day = calendar.get(Calendar.DATE);
		return String.valueOf(c_year) + (c_month < 10 ?("0" + c_month):(String.valueOf(c_month))) + (c_day < 10 ?("0" + c_day):(String.valueOf(c_day)));
	}
	
	/**
	 * 由指定月份计算相对该月份的偏移月份
	 * @param @param calmonth
	 * @param @param increments
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getOffsetMonth(String calmonth, int offset){
		if (null == calmonth || calmonth.length() < 6)
			return calmonth;

		int yearCt = Integer.parseInt(calmonth.substring(0, 4));
		int monCt = Integer.parseInt(calmonth.substring(4, 6));
		String sb = calmonth.substring(6);
		int monTotal = yearCt * 12 + monCt + offset;
		
		int curYear = monTotal / 12;
		int curMon = monTotal % 12;
		if(curMon == 0){
			return String.valueOf(curYear - 1) + "12" + sb;
		}else{
			return String.valueOf(curYear) + (curMon < 10 ? ("0" + curMon):String.valueOf(curMon)) + sb;
		}
	}
	
	/**
	 * 计算month1相对month2的偏移量
	 * @param month1
	 * @param month2
	 * @return
	 */
	public static int getMonthOffset(String month1, String month2){
		int beginYearCt = Integer.parseInt(month1.substring(0, 4));
		int beginMonCt = Integer.parseInt(month1.substring(4, 6), 10);
		int beginTotal = beginYearCt * 12 + beginMonCt;
		
		int endYearCt = Integer.parseInt(month2.substring(0, 4));
		int endMonCt = Integer.parseInt(month2.substring(4, 6), 10);
		int endTotal = endYearCt * 12 + endMonCt;
		return endTotal - beginTotal;
	}

}
