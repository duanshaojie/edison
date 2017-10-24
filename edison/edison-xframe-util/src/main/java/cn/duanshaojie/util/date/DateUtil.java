package cn.duanshaojie.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

public class DateUtil {

	public static final String DATE_JFP_STR = "yyyyMM";
	public static final String DATE_DAY_STR = "yyyyMMdd";
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	public static final String DATE_KEY_STR = "yyMMddHHmmss";
	public static final String DATE_KEY_LONG_STR = "yyyyMMddHHmmss";
	public static final String DATE_WHOLE_FULL_STR = "yyyyMMddHHmmssS";

	public static Date parse(String strDate) {
		return parse(strDate, DATE_FULL_STR);
	}

	public static Date parse(String strDate, String pattern) {
		if (StringUtils.isEmpty(strDate)) {
			return null;
		}

		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formatFullPattern(Date date) {
		return format(date, DATE_FULL_STR);
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static int compareDateWithNow(Date date1) {
		Date date2 = new Date();
		int rnum = date1.compareTo(date2);
		return rnum;
	}

	public static int compareDateWithDate2(String minDate, String maxDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_SMALL_STR);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(minDate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(maxDate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	public static int compareDateWithDate2(String minDate, String maxDate,String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FULL_STR);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(minDate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(maxDate));
		long time2 = cal.getTimeInMillis()+1000;
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static int compareDateWithNow(long date1) {
		long date2 = dateToUnixTimestamp();
		if (date1 > date2) {
			return 1;
		} else if (date1 < date2) {
			return -1;
		} else {
			return 0;
		}
	}

	public static String getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
		return df.format(new Date());
	}

	public static String getNowTime(String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(new Date());
	}

	public static String getJFPTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
		return df.format(new Date());
	}

	public static long dateToUnixTimestamp(String date) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static long dateToUnixTimestamp(String date, String dateFormat) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static long dateToUnixTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp;
	}

	public static String unixTimestampToDate(long timestamp) {
		SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sd.format(new Date(timestamp));
	}

	public static Date calculateDate(Date date, int field, int value) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, value);
		return c.getTime();
	}

	public static int getMonthsBetweenDates(Date early, Date late) {

		Date sEarly = parse(format(early, DATE_SMALL_STR), DATE_SMALL_STR);
		Date sLate = parse(format(late, DATE_SMALL_STR), DATE_SMALL_STR);

		Calendar earlyCalendar = Calendar.getInstance();
		earlyCalendar.setTime(sEarly);

		Calendar lateCalendar = Calendar.getInstance();
		lateCalendar.setTime(sLate);

		int result = 12 * (lateCalendar.get(Calendar.YEAR) - earlyCalendar.get(Calendar.YEAR))
				+ (lateCalendar.get(Calendar.MONTH) - earlyCalendar.get(Calendar.MONTH));

		return result;
	}

	public static int getDaysBetweenDates(Date early, Date late) {

		Date sEarly = parse(format(early, DATE_SMALL_STR), DATE_SMALL_STR);
		Date sLate = parse(format(late, DATE_SMALL_STR), DATE_SMALL_STR);

		Calendar earlyCalendar = Calendar.getInstance();
		earlyCalendar.setTime(sEarly);

		Calendar lateCalendar = Calendar.getInstance();
		lateCalendar.setTime(sLate);

		long between_days = (late.getTime() - early.getTime()) / (1000 * 3600 * 24);

		return (int) between_days;
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 *
	 * @return String
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	public static Date beforeDate(Date date,int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - days);// 让日期减
		return calendar.getTime();
	}
	
	public static Date afterDate(Date date,int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);// 让日期减
		return calendar.getTime();
	}

}
