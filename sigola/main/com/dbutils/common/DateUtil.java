package com.dbutils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间操作类
 * 
 * @author 军
 * 
 */
public class DateUtil {

	public static final int HOURS_PER_DAY = 24;

	public static final long MILLIS_PER_SECOND = 1000;

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat sf = new SimpleDateFormat(
			DEFAULT_FORMAT);

	public static final String DEFAULT_MINIFORMAT = "yyyy-MM-dd HH:mm";
	public static final SimpleDateFormat minisf = new SimpleDateFormat(
			DEFAULT_MINIFORMAT);

	public static final String DEFAULT_DateFORMAT = "yyyy-MM-dd";
	public static final SimpleDateFormat datesf = new SimpleDateFormat(
			DEFAULT_DateFORMAT);

	public static Date tranDate(long time) {
		if (time == 0)
			return null;
		return new Date(time);
	}

	public static String tranDate(Date time) {
		if (time == null)
			return "";
		return sf.format(time);
	}

	public static String tranMiniDate(long time) {
		if (time == 0)
			return "";
		return minisf.format(new Date(time * 1000));
	}

	public static String converToDate(Long time) {
		if (time == null || time == 0)
			return "";
		return datesf.format(new Date(time * 1000));
	}

	public static String converToDate(long time) {
		if (time == 0)
			return "";
		return datesf.format(new Date(time * 1000));
	}

	public static String tranSfDate(Long time) {
		if (time == null || time == 0)
			return "...";
		return minisf.format(new Date(time * 1000));
	}

	public static long parseDate(Date time) {
		return time == null ? 0 : time.getTime();
	}

	public static String getCurrentDate() {
		return sf.format(new Date());
	}

	public static long getCurrentDateLong() {
		return parseDate(new Date()) / 1000;
	}

	public static String getDateByNow(int day) {
		String DEFAULT_dateORMAT = "yyyy年MM月dd日";
		SimpleDateFormat datesf = new SimpleDateFormat(DEFAULT_dateORMAT);
		if (day == 0) {
			return datesf.format(new Date());
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, day);
			return datesf.format(cal.getTime());
		}
	}

	public static long getDateLong(String dateStr) {
		Date date = null;
		try {
			if (dateStr.length() < 12)
				dateStr += " 0:00:00";
			date = sf.parse(dateStr.replace("年", "-").replace("月", "-")
					.replace("日", ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parseDate(date) / 1000;
	}

	public static int getCurMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	public static int getCurHour() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	static Pattern days = Pattern.compile("^([0-9]+)d$");
	static Pattern hours = Pattern.compile("^([0-9]+)h$");
	static Pattern minutes = Pattern.compile("^([0-9]+)mi?n$");
	static Pattern seconds = Pattern.compile("^([0-9]+)s$");

	public static int parseDuration(String duration) {
		if (duration == null) {
			return 60 * 60 * 24 * 30;
		}
		int toAdd = -1;
		if (days.matcher(duration).matches()) {
			Matcher matcher = days.matcher(duration);
			matcher.matches();
			toAdd = Integer.parseInt(matcher.group(1)) * (60 * 60) * 24;
		} else if (hours.matcher(duration).matches()) {
			Matcher matcher = hours.matcher(duration);
			matcher.matches();
			toAdd = Integer.parseInt(matcher.group(1)) * (60 * 60);
		} else if (minutes.matcher(duration).matches()) {
			Matcher matcher = minutes.matcher(duration);
			matcher.matches();
			toAdd = Integer.parseInt(matcher.group(1)) * (60);
		} else if (seconds.matcher(duration).matches()) {
			Matcher matcher = seconds.matcher(duration);
			matcher.matches();
			toAdd = Integer.parseInt(matcher.group(1));
		}
		if (toAdd == -1) {
			throw new IllegalArgumentException("Invalid duration pattern : "
					+ duration);
		}
		return toAdd;
	}

	public static String getCalculateDay(long startDate, long afterDate) {
		long l = afterDate - startDate;
		long day = l / (24 * 60 * 60);
		if (day < 0)
			return "已过期";
		long hour = (l / (60 * 60) - day * 24);
		return "还剩下：" + day + "天" + hour + "小时";
	}

	@SuppressWarnings("deprecation")
	public static String getBairenDate(Long time) {
		if (time == null || time == 0)
			return "";
		Date date = new Date(time * 1000);
		Date nowDate = new Date();
		// return datesf.format();

		if (date.getYear() == nowDate.getYear())
			if (date.getMonth() == nowDate.getMonth()) {
				if (date.getDay() == nowDate.getDay()) {
					return "今天";
				}
				else {
					
					return intAddZore(date.getMonth()) + "-"
							+ intAddZore(date.getDay());
				}

			} else {
				return intAddZore(date.getMonth()) + "-"
						+ intAddZore(date.getDay());
			}
		else {
			return date.getYear() + "-" + intAddZore(date.getMonth()) + "-"
					+ intAddZore(date.getDay());
		}

	}

	public static String intAddZore(int intdate) {
		if (intdate < 10)
			return "0" + (intdate+1);
		return (intdate+1) + "";
	}
}
