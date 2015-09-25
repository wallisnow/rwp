package com.congxing.core.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	
	/**
	 * <p>
	 * 把月份转换为日期格式(无异常转换)
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * <p>
	 * getDateByMonth("201302") = 2013-02-01 00:00:00
	 * </p>
	 * @param month 月份格式为yyyyMM
	 * @return
	 */
	public static Date getDateByMonth(String month){
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.set(Integer.parseInt(StringUtils.substring(month, 0, 4)), Integer.parseInt(StringUtils.substring(month, 4, 6)) - 1, 1, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime(); 
	}
	
	/**
	 * <p>
	 * 把天转换为日期格式(无异常转换)
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * <p>
	 * getDateByDay("20130201") = 2013-02-01 00:00:00
	 * </p>
	 * @param month 默认格式为yyyyMMdd
	 * @return
	 */
	public static Date getDateByDay(String day){
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.set(Integer.parseInt(StringUtils.substring(day, 0, 4)), Integer.parseInt(StringUtils.substring(day, 4, 6)) - 1, Integer.parseInt(StringUtils.substring(day, 6, 8)), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime(); 
	}
	
	/**
	 * <p>
	 * 计算指定日期date相对指定类型addType的增量日期
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * @param date 日期
	 * @param addType 增量类型
	 * @param increment	增量值
	 * @return
	 */
	public static Date getOffsetDate(Date date, int addType, int increment){
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(date);
		calendar.add(addType, increment); //设置增量
		return calendar.getTime();
	}
	
	/**
	 * <p>
	 * 计算指定日期date增加increment年后的日期
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * @param date
	 * @param increment
	 * @return
	 */
	public static Date addYears(Date date, int increment) {
        return DateUtils.getOffsetDate(date, Calendar.YEAR, increment);
    }
	
	/**
	 * <p>
	 * 计算指定日期date增加increment月份后的日期
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * @param date
	 * @param increment
	 * @return
	 */
	public static Date addMonths(Date date, int increment) {
        return DateUtils.getOffsetDate(date, Calendar.MONTH, increment);
    }
	
	/**
	 * <p>
	 * 计算指定日期date增加increment天后的日期
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * @param date
	 * @param increment
	 * @return
	 */
	public static Date addDays(Date date, int increment) {
        return DateUtils.getOffsetDate(date, Calendar.DAY_OF_MONTH, increment);
    }
	
	/**
	 * 由{@code java.sql.Timestamp }获取{@code java.util.Date }类型的时间格式
	 * @param date
	 * @return
	 */
	public static Date getDateByTimestamp(Timestamp timestamp){
		return new java.util.Date(timestamp.getTime());
	}
	
	/**
	 * 由{@code java.util.Date }获取{@code java.sql.Timestamp }类型的时间格式
	 * @param date
	 * @return
	 */
	public static Timestamp getTimestampByDate(Date date){
		return new java.sql.Timestamp(date.getTime());
	}
	
	/**
	 * 设置日期的时、分、秒和毫秒信息，返回{@code java.sql.Timestamp }类型时间
	 * @param date
	 * @param hour_of_day
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public static Timestamp getTimestampByDate(Date date, int hour_of_day, int minute, int second, int millisecond){
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour_of_day);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return new java.sql.Timestamp(calendar.getTime().getTime());
	}
	
	/**
	 * <p>
	 * 取指定月份的开始时间
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * <p>
	 * getBeginTimeOfMonth("201302") = 2013-02-01 00:00:00.0
	 * </p>
	 * @param month 格式为yyyyMM
	 * @return
	 */
	public static Timestamp getBeginTimeOfMonth(String month) {
		Date date = DateUtils.getDateByMonth(month);
		return DateUtils.getTimestampByDate(date, 0, 0, 0, 0);
	}
	
	/**
	 * <p>
	 * 取指定月份的结束时间
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * <p>
	 * getEndTimeOfMonth("201302") = 2013-02-28 23:59:59.999
	 * </p>
	 * @param month 格式为yyyyMM
	 * @return
	 */
	public static Timestamp getEndTimeOfMonth(String month) {
		Date curMonthFirstDate = DateUtils.getDateByMonth(month);
		Date nextMonthFirstDate = DateUtils.getOffsetDate(curMonthFirstDate, Calendar.MONTH, 1);
		Date endTime = DateUtils.getOffsetDate(nextMonthFirstDate, Calendar.DATE, -1);
		return DateUtils.getTimestampByDate(endTime, 23, 59, 59, 999);
	}
	
	
	/**
	 * <p>
	 * 取指定日期的开始时间
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * <p>
	 * getBeginTimeOfDay("20130203") = 2013-02-03 00:00:00.0
	 * </p>
	 * @param day 格式为yyyyMMdd
	 * @return
	 */
	public static Timestamp getBeginTimeOfDay(String day) {
		Date date = DateUtils.getDateByDay(day);
		return DateUtils.getTimestampByDate(date, 0, 0, 0, 0);
	}
	
	/**
	 * <p>
	 * 取指定日期的结束时间
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * <p>
	 * getEndTimeOfDay("20130203") = 2013-02-03 23:59:59.999
	 * </p>
	 * @param day 格式为yyyyMMdd
	 * @return
	 */
	public static Timestamp getEndTimeOfDay(String day) {
		Date date = DateUtils.getDateByDay(day);
		return DateUtils.getTimestampByDate(date, 23, 59, 59, 999);
	}
	
	
	/**
	 * <p>
	 * 取指定日期的开始时间
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * @param day 格式为yyyyMMdd
	 * @return
	 */
	public static Timestamp getBeginTimeOfDate(Date date) {
		return DateUtils.getTimestampByDate(date, 0, 0, 0, 0);
	}
	
	/**
	 * <p>
	 * 取指定日期的结束时间
	 * </p>
	 * <p>
	 * <strong>日期时、分、秒、毫秒均为0</strong>
	 * </p>
	 * @param day 格式为yyyyMMdd
	 * @return
	 */
	public static Timestamp getEndTimeOfDate(Date date) {
		return DateUtils.getTimestampByDate(date, 23, 59, 59, 999);
	}
	
	
	/**
	 * 取当前日期
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}
	
	/**
	 * 取相对date的前面days天的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getPreDaysOfDate(Date date, int days){
		return DateUtils.getOffsetDate(date, Calendar.DATE, -1);
	}
	
	/**
	 * 取相对于指定日期date的前一天
	 * @return
	 */
	public static Date getLastDate(Date date){
		return DateUtils.getPreDaysOfDate(date, -1);
	}
	
	/**
	 * 取当前日期的前一天
	 * @return
	 */
	public static Date getLastDate(){
		return DateUtils.getLastDate(DateUtils.getNow());
	}
	
	/**
	 * 取相对于指定日期date的后面days天的日期
	 * @return
	 */
	public static Date getNextDaysOfDate(Date date, int days){
		return DateUtils.getOffsetDate(date, Calendar.DATE, days);
	}
	
	/**
	 * 取相对于指定日期date的后一天
	 * @return
	 */
	public static Date getNextDate(Date date){
		return DateUtils.getNextDaysOfDate(date, 1);
	}
	
	/**
	 * 取当前日期的后一天
	 * @return
	 */
	public static Date getNextDate(){
		return DateUtils.getNextDate(DateUtils.getNow());
	}
	
	/**
	 * 由指定月份计算相对该月份的增量月份
	 * @param @param calmonth
	 * @param @param increments
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String calcMonth(String calmonth, int increment){
		if (null == calmonth || calmonth.length() != 6)
			return calmonth;

		int yearCt = Integer.parseInt(calmonth.substring(0, 4));
		int monCt = Integer.parseInt(calmonth.substring(4));
		int monTotal = yearCt * 12 + monCt + increment;
		
		int curYear = monTotal / 12;
		int curMon = monTotal % 12;
		if(curMon == 0){
			return String.valueOf(curYear - 1) + "12";
		}else{
			return String.valueOf(curYear) + (curMon < 10 ? ("0" + curMon):String.valueOf(curMon));
		}
	}
	
	/**
	 * 由指定天计算相关该天的增量日期
	 * @param curDate
	 * @param increment
	 * @return
	 */
	public static String calcDate(String curDate, int increment){
		Calendar calendar = Calendar.getInstance(); // 得到日历
		String year = StringUtils.substring(curDate, 0, 4);
		String month = StringUtils.substring(curDate, 4, 6);
		String date = StringUtils.substring(curDate, 6, 8);
		calendar.set(Integer.parseInt(year, 10), Integer.parseInt(month,10) - 1, Integer.parseInt(date, 10));
		calendar.add(Calendar.DATE, increment); //设置增量
		return DateFormatFactory.getInstance("yyyyMMdd").format(calendar.getTime());
	}
	
	/**
	 * 计算指定月份最后一天
	 * @param month
	 * @return
	 */
	public static String calcLastDateOfMonth(String month){
		String nextMonth = calcMonth(month, 1);
		return calcDate(nextMonth + "01", -1);
	}
}
