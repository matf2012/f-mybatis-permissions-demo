package com.f.demo.utils;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class DateUtil {

    public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    //时间单位
    public final static String DAYUNIT = "d";
    public final static String YEARUNIT = "y";

    public static Date getCurrDateTime() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 取得当前系统时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrTime() {
        Date date_time = new Date();
        return formatDate(date_time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 取得当前系统日期
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrDate() {
        Date date_time = new Date();
        return formatDate(date_time, "yyyy-MM-dd");
    }

    /**
     * 取得日期的年份
     *
     * @param date 日期
     * @return yyyy 年份字符串
     */
    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }

    /**
     * 取得日期的月份
     *
     * @param date 日期
     * @return mm 月份字符串
     */
    public static String getMonth(Date date) {
        return formatDate(date, "MM");
    }

    /**
     * 取得日期的天份
     *
     * @param date 日期
     * @return dd 天字符串
     */
    public static String getDay(Date date) {
        return formatDate(date, "dd");
    }

    /**
     * 取得日期的小时
     *
     * @param date 日期
     * @return hh 小时字符串
     */
    public static String getHour(Date date) {
        return formatDate(date, "HH");
    }

    /**
     * 取得日期的分钟
     *
     * @param date 时间
     * @return mm 分钟字符串
     */
    public static String getMinute(Date date) {
        return formatDate(date, "mm");
    }

    /**
     * 取得时间的秒
     *
     * @param date 时间
     * @return ss 秒字符串
     */
    public static String getSecond(Date date) {
        return formatDate(date, "ss");
    }

    /**
     * 日期转化为字符串
     *
     * @param date 时间
     * @return yyyy-MM-dd HH:mm:ss 格式化的时间字符串
     */
    public static String dateToString(Date date) {
        if (date == null) return "";
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期转化为字符串
     *
     * @param date 时间
     * @return yyyy-MM-dd 格式化的时间字符串
     */
    public static String dateToStringShort(Date date) {
        if (date == null) return "";
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 字符串转换为日期
     *
     * @param dateString yyyy-MM-dd HH:mm:ss
     * @return 日期
     */
    public static Date stringToDate(String dateString) {
        String sf = "yyyy-MM-dd HH:mm:ss";
        Date dt = stringToDate(dateString, sf);
        return dt;
    }

    /**
     * 字符串转换为日期
     *
     * @param dateString yyyy-MM-dd
     * @return 日期
     */
    public static Date stringToDateShort(String dateString) {
        String sf = "yyyy-MM-dd";
        Date dt = stringToDate(dateString, sf);
        return dt;
    }

    /**
     * 对日期进行格式化
     *
     * @param date 日期
     * @param sf   日期格式
     * @return 字符串
     */
    public static String formatDate(Date date, String sf) {
        if (date == null) return "";
        SimpleDateFormat dateformat = new SimpleDateFormat(sf);
        return dateformat.format(date);
    }

    /**
     * 字符串转换为日期
     *
     * @param dateString 日期格式字符串
     * @param sf         日期格式化定义
     * @return 转换后的日期
     */
    public static Date stringToDate(String dateString, String sf) {
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat sdf = new SimpleDateFormat(sf);
        Date dt = sdf.parse(dateString, pos);
        return dt;
    }

//    /**
//     * 计算两个日期差（年），要求date1>date2
//     * @param date1 时间1
//     * @param date2 时间2
//     * @return 相差天数
//     */
//    public static int diffTwoDateYear(Date date1, Date date2) {
//    	int diff = Integer.parseInt(getYear(date1)) - Integer.parseInt(getYear(date2));
//    	int m1 = Integer.parseInt(getMonth(date1));
//    	int m2 = Integer.parseInt(getMonth(date2));
//    	if(m1 > m2) {
//    		return diff;
//    	}
//    	if(m1 < m2) {
//    		return diff-1;
//    	}
//		int d1 = Integer.parseInt(getDay(date1));
//    	int d2 = Integer.parseInt(getDay(date2));
//    	if(d1 >= d2) {
//    		return diff;
//    	} else {
//    		return diff-1;
//    	}
//    }
//

    /**
     * 计算两个日期差（天）
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 相差天数
     */
    public static int diffTwoDateDay(Date date1, Date date2) {
        long l1 = date1.getTime();
        long l2 = date2.getTime();
        int diff = Integer.parseInt("" + (l1 - l2) / 3600 / 24 / 1000);
        return diff;
    }

    /**
     * 计算两个日期差（毫秒）
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 相差毫秒数
     */
    public static long diffTwoDate(Date date1, Date date2) {
        long l1 = date1.getTime();
        long l2 = date2.getTime();
        return (l1 - l2);
    }

    public static Date getTime(String timeStr) {
        if (null == timeStr || timeStr.equals("")) {
            return null;
        }
        int length = timeStr.length();
        if (7 == length) {
            timeStr = timeStr + "-01 00:00:00";
        }
        if (10 == length) {
            timeStr = timeStr + " 00:00:00";
        }
        if (length > 7 && length < 10) {
            String str[] = timeStr.split("-");
            if (null == str || str.length != 3) {
                return null;
            }
            if (1 == str[1].length()) {
                str[1] = "0" + str[1];
            }
            if (1 == str[2].length()) {
                str[2] = "0" + str[2];
            }
            timeStr = str[0] + "-" + str[1] + "-" + str[2] + " 00:00:00";
        }

        String regex = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(timeStr).matches()) {
            return null;
        }

        DateFormat format = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        try {
            return format.parse(timeStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //比较两个时间谁先谁后
    public static int compareDate(Date d1, Date d2) {
        Calendar call = getCalendarAtDateBegin(d1);
        Calendar cal2 = getCalendarAtDateBegin(d2);
        if (call.before(cal2)) {
            return -1;
        }
        if (call.after(cal2)) {
            return 1;
        }
        return 0;
    }

    //设置Calendar的相关属性
    public static Calendar getCalendarAtDateBegin(Date date) {
        Calendar cal = getCalendar(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    //得到设置时间的Calendar
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * @param currentTime 计算的日期
     * @param type        偏移的类别
     * @param iQuantity   偏移数量
     * @return 偏移后的时间
     */
    public static Date getDateChangeTime(Date currentTime, String type,
                                         int iQuantity) {
        int year = Integer.parseInt(formatDate(currentTime, "yyyy"));
        int month = Integer.parseInt(formatDate(currentTime, "MM"));
        int day = Integer.parseInt(formatDate(currentTime, "dd"));
        int hour = Integer.parseInt(formatDate(currentTime, "HH"));
        int mi = Integer.parseInt(formatDate(currentTime, "mm"));
        int ss = Integer.parseInt(formatDate(currentTime, "ss"));
        GregorianCalendar gc = new GregorianCalendar(year, month - 1, day,
                hour, mi, ss);
        //月份修正
        //gc.add(GregorianCalendar.MONTH, -1);
        if (type.equalsIgnoreCase("y")) {
            gc.add(GregorianCalendar.YEAR, iQuantity);
            int i = gc.get(GregorianCalendar.YEAR);
            String dateString = DateUtil.getYearTime(String.valueOf(i));
            return DateUtil.stringToDateShort(dateString);
        } else if (type.equalsIgnoreCase("m")) {
            gc.add(GregorianCalendar.MONTH, iQuantity);
        } else if (type.equalsIgnoreCase("d")) {
            gc.add(GregorianCalendar.DATE, iQuantity);
        } else if (type.equalsIgnoreCase("h")) {
            gc.add(GregorianCalendar.HOUR, iQuantity);
        } else if (type.equalsIgnoreCase("mi")) {
            gc.add(GregorianCalendar.MINUTE, iQuantity);
        } else if (type.equalsIgnoreCase("s")) {
            gc.add(GregorianCalendar.SECOND, iQuantity);
        }

        return gc.getTime();
    }

    /**
     * 根据年取得年末的日期
     *
     * @param year 年
     * @return time  返回日期格式"yyyy-mm-dd"
     * @parm month 月
     */
    public static String getYearTime(String year) {
        String time = "";
        time = year + "-12-31";
        return time;
    }

    /**
     * 时间偏移
     *
     * @param currentTime 计算日期
     * @param type        偏移的类别
     * @param iQuantity   偏移数量
     * @return 偏移后的时间串
     */
    public String getDateChangeALL(String currentTime, String type,
                                   int iQuantity) {
        Date curr = null;
        String newtype = "";
        if (currentTime.length() == 10) {
            curr = this.stringToDateShort(currentTime);
        }
        if (currentTime.length() > 10) {
            curr = this.stringToDate(currentTime);
        }

        //    日
        if (type.equals("1")) {
            iQuantity = iQuantity;
            newtype = "d";
        }
        //    周，按照7天计算
        else if (type.equals("2")) {
            iQuantity = iQuantity * 7;
            newtype = "d";
        }
//  旬，按照10天计算
        else if (type.equals("3")) {
            iQuantity = iQuantity * 10;
            newtype = "d";
        }
        //月
        else if (type.equals("4")) {
            iQuantity = iQuantity;
            newtype = "m";
        }
//  旬，按照3个月计算
        else if (type.equals("5")) {
            iQuantity = iQuantity * 3;
            newtype = "m";
        }
        //半年，按照六个月计算
        else if (type.equals("6")) {
            iQuantity = iQuantity * 6;
            newtype = "m";
        }
        //年
        else if (type.equals("7")) {
            newtype = "y";
        } else {
            iQuantity = iQuantity;
            newtype = "d";
        }

        Date change = this.getDateChangeTime(curr, newtype, iQuantity);

//    if(!type.equals("d")){
//    change = this.getMonthEnd(change);
//    }

        return this.dateToStringShort(change);
    }

    /**
     * 取月末时间
     *
     * @param date 日期
     * @return date
     */
    public Date getMonthEnd(Date date) {
        int year = Integer.parseInt(this.formatDate(date, "yyyy"));
        int month = Integer.parseInt(this.formatDate(date, "MM"));
        int day = Integer.parseInt(this.formatDate(date, "dd"));

        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day, 0, 0, 0);
        int monthLength = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
        String newDateStr = this.formatDate(date, "yyyy") + "-" + this.formatDate(date, "MM") + "-";
        if (monthLength < 10)
            newDateStr += "0" + monthLength;
        else
            newDateStr += "" + monthLength;
        return this.stringToDateShort(newDateStr);
    }

    /**
     * 时间偏移
     *
     * @param currentTime
     * @param type
     * @param iQuantity
     * @return
     */
    public Date getDateChangeTimeNew(Date currentTime, String type, int iQuantity) {
        int year = Integer.parseInt(formatDate(currentTime, "yyyy"));
        int month = Integer.parseInt(formatDate(currentTime, "MM"));
        int day = Integer.parseInt(formatDate(currentTime, "dd"));
        int hour = Integer.parseInt(formatDate(currentTime, "HH"));
        int mi = Integer.parseInt(formatDate(currentTime, "mm"));
        int ss = Integer.parseInt(formatDate(currentTime, "ss"));
        GregorianCalendar gc = new GregorianCalendar(year, month - 1, 1, hour, mi, ss);
        //月份修正
        //gc.add(GregorianCalendar.MONTH, -1);
        if (type.equalsIgnoreCase("y")) {
            gc.add(GregorianCalendar.YEAR, iQuantity);
        } else if (type.equalsIgnoreCase("m")) {
            gc.add(GregorianCalendar.MONTH, iQuantity);
            return getMonthEnd(gc.getTime());
        } else if (type.equalsIgnoreCase("d")) {
            gc.add(GregorianCalendar.DATE, iQuantity);
        } else if (type.equalsIgnoreCase("h")) {
            gc.add(GregorianCalendar.HOUR, iQuantity);
        } else if (type.equalsIgnoreCase("mi")) {
            gc.add(GregorianCalendar.MINUTE, iQuantity);
        } else if (type.equalsIgnoreCase("s")) {
            gc.add(GregorianCalendar.SECOND, iQuantity);
        }

        return gc.getTime();
    }

    /**
     * 获取当前毫秒时间
     * @return
     */
    public static Timestamp getCurrentMillis() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    public static void main(String[] args) {
        DateUtil d = new DateUtil();
        Date dd = d.getDateChangeTime(d.getCurrDateTime(), "y", -9);
        String yearstr = d.getYear(d.getCurrDateTime());
        int paryear = Integer.parseInt(yearstr) - 9;
        String monthstr = d.getMonth(d.getCurrDateTime());
        String daystr = d.getDay(d.getCurrDateTime());
        //String  ss =  d.getDateChangeALL(currentDate,"y",years);
        String ss1 = DateUtil.dateToStringShort(dd);
        String ss = d.getDateChangeALL("2012-07-12", "y", -9);
    }
}
