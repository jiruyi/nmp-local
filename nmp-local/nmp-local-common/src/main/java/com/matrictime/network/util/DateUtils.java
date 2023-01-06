package com.matrictime.network.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateUtils {

    /**
     * 一天中的毫秒数
     */
    public static final long DAY_MS = 1000 * 3600 * 24L;

    /**
     * 一小时的毫秒数
     */
    public static final long HOUR_MS = 1000 * 60 * 60L;

    /**
     * yyyy-MM-dd格式化
     **/
    public static final String DATE_DF_INT = "yyyyMMdd";

    /**
     * yyyy-MM格式化
     **/
    public static final String DATE_YM_INT = "yyyyMM";

    /**
     * 默认日期字符串转换格式，SimpleDateFormat非线程安全的类，所以要ThreadLocal
     */
    private static final ThreadLocal<SimpleDateFormat> YYMMDDHHMMSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 默认日期字符串转换格式，SimpleDateFormat非线程安全的类，所以要ThreadLocal
     */
    public static final ThreadLocal<SimpleDateFormat> YYMMDDHHMM = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };

    /**
     * 默认日期字符串转换格式，SimpleDateFormat非线程安全的类，所以要ThreadLocal
     */
    public static final ThreadLocal<SimpleDateFormat> YYMMDD = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_DF_INT);
        }
    };


    public static final String HOUR_DATE_TIME_FORMAT = "yyyy-MM-dd HH:00";

    public static final String MINUTE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String MINUTE_TIME_FORMAT = "HH:mm";



    /**
     * 格式化时间yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String formatDateToInteger(Date date) {
        return formatDateToString2(date, DATE_DF_INT);
    }

    /**
     * 格式化20180211 为 2018-02-11
     *
     * @param date
     * @return
     */
    public static String formatStringDate(String date) {
        if (!StringUtils.isEmpty(date) && date.length() == 8) {
            String formatDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
            return formatDate;
        } else {
            return date;
        }
    }

    public static Date stampToDate(String s) {
        long lt = new Long(s);
        Date date = new Date(lt);
        return date;
    }

    /**
     * 根据时间戳获取月日信息
     */
    public static String dateInfo(Date date) {
        SimpleDateFormat fm = new SimpleDateFormat("MM月dd日");
        String result = fm.format(date);

        return result;

    }

    /**
     * date转换成yyyy-MM-dd格式字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * date转换成yyyy-MM格式字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    /**
     * yyyy-MM-dd HH:mm:ss 格式字符串转换成Date
     *
     * @param date
     * @return
     */
    public static Date formatStringToDate(String date) {

        return formatStringToDate(date, null);
    }

    /**
     * date转换成yyyy-MM-dd HH:mm:ss格式字符串
     *
     * @param date
     * @return
     */
    public static String formatDateToString(Date date) {

        return formatDateToString(date, null);
    }

    /**
     * date转换成yyyy-MM-dd HH:mm:ss格式字符串,null返回""
     *
     * @param date
     * @return
     */
    public static String dateToStringIfEmpty(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf = YYMMDDHHMMSS.get();
        return sdf.format(date);
    }

    public static Date formatStringToDate(String date, SimpleDateFormat sdf) {

        if (StringUtils.isBlank(date)) {
            return null;
        }

        if (sdf == null) {
            sdf = YYMMDDHHMMSS.get();
        }

        try {
            return sdf.parse(date);
        } catch (ParseException e) {

            return null;
        }
    }

    public static String formatDateToString(Date date, SimpleDateFormat sdf) {

        if (date == null) {
            return null;
        }

        if (sdf == null) {
            sdf = YYMMDDHHMMSS.get();
        }

        return sdf.format(date);
    }

    public static String formatDateToYYMMDDHHMM(Date date, SimpleDateFormat sdf) {

        if (date == null) {
            return null;
        }

        if (sdf == null) {
            sdf = YYMMDDHHMM.get();
        }

        return sdf.format(date);
    }

    /**
     * 抽象格式化日期
     *
     * @param date
     * @param dateDf
     * @return
     */
    public static String formatDateToString2(Date date, String dateDf) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateDf);
        return sdf.format(date);
    }


    /**
     * 当前日期 +/- N天
     *
     * @param n
     * @return
     */
    public static Date addDayForNow(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, n);
        return calendar.getTime();
    }

    /**
     * 指定日期 +/- N分钟
     *
     * @param n
     * @return
     */
    public static Date addMinuteForDate(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, n);
        return calendar.getTime();
    }

    /**
     * 指定日期 +/- N秒钟
     *
     * @param n
     * @return
     */
    public static Date addSecondsForDate(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, n);
        return calendar.getTime();
    }

    /**
     * 指定日期 +/- N天
     *
     * @param n
     * @return
     */
    public static Date addDayForDate(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        return calendar.getTime();
    }

    /**
     * 指定日期 +/- N年
     *
     * @param n
     * @return
     */
    public static Date addYearForDate(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, n);
        return calendar.getTime();
    }
    /**
     * 返回相差的天数
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static long daysDifference(Date beginTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginTime);
        long begin = cal.getTimeInMillis();
        cal.setTime(endTime);
        long end = cal.getTimeInMillis();
        long days = (end - begin) / DAY_MS;
        return days;
    }

    /**
     * 伪时间戳（毫秒）转天数
     *
     * @param t
     * @return
     */
    public static String timeUnitToDay(long t) {

        return String.valueOf(t / (24 * 60 * 60 * 1000));
    }

    public static Map<String, Object> getLastMonthBeginEndTimes(Date date) {

        // 计算前一个月的起始时间和结束时间
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime lastMonthDay = instant.atZone(zoneId).toLocalDateTime().minusMonths(1);
        LocalDateTime lastMonthBeginDay = lastMonthDay.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime lastMonthEndDay = lastMonthDay.with(TemporalAdjusters.lastDayOfMonth());
        Map<String, Object> dateMap = new HashMap<>(3);
        LocalDateTime beginTemp = lastMonthBeginDay.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endTemp = lastMonthEndDay.withHour(23).withMinute(59).withSecond(59);

        Date beginTime = Date.from(beginTemp.atZone(zoneId).toInstant());
        Date endTime = Date.from(endTemp.atZone(zoneId).toInstant());
        ;
        Date invoiceDate = Date.from(lastMonthDay.withDayOfMonth(10).atZone(zoneId).toInstant());
        dateMap.put("beginTime", beginTime);
        dateMap.put("endTime", endTime);
        dateMap.put("invoiceDate", invoiceDate);
        return dateMap;
    }

    /**
     * 查询几个月前或者后的时间
     * 传入负数表示查询几个月前的时间
     * 传入正数表示查询几个月后的时间
     *
     * @param beforeXMonth
     * @return
     */
    public static Date getBeforeOrAfterXMonthTime(int beforeXMonth) {
        Date date = new Date();
        //得到日历
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, beforeXMonth);
        date = calendar.getTime();
        return date;
    }

    public static Date getCurrentDayStartTime() {
        // 获取当天00点00分00秒Date
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        Date date = calendar.getTime();
        return date;
    }

    public static Date getCurrentDayLastTime() {
        // 获取当天23点59分59秒Date
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取指定日期的起始时间
     *
     * @param date 指定日期
     * @return
     */
    public static Date getStartForDay(Date date) {

        String dateStr = formatDateToString(date, new SimpleDateFormat("yyyy-MM-dd 00:00:00"));
        return formatStringToDate(dateStr);
    }

    /**
     * 获取指定日期的最后一分钟
     *
     * @param date 指定日期
     * @return
     */
    public static Date getEndForDay(Date date) {

        String dateStr = formatDateToString(date, new SimpleDateFormat("yyyy-MM-dd 23:59:59"));
        return formatStringToDate(dateStr);
    }

    /**
     * 设置前一天的最后时间
     *
     * @param date
     * @return
     */
    public static Date getBeforeDayLastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 设置后一天的开始时间
     *
     * @param date
     * @return
     */
    public static Date getAfterDayBeginTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 00);
        return calendar.getTime();
    }

    /**
     * 设置指定某一天的开始时间
     *
     * @param day
     * @return
     */
    public static Date getSomeDayBeginTimeByNow(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 00);
        return calendar.getTime();
    }

    /**
     * 将yyyy-MM转换为yyyyMM
     * eg：2019-05转换为201905
     *
     * @return eg:2019-03-31 23:59:59
     */
    public static String getDateToString(String strDate) {
        Date date = formatStringToDate(strDate, new SimpleDateFormat("yyyy-MM"));
        return formatDateToString(date, new SimpleDateFormat("yyyyMM"));
    }

    /**
     * 获取指定时间所在月份的月初时间
     *
     * @return eg:2019-03-01 00:00:00
     */
    public static Date getDateMonthFirst(String strDate, String format) {
        Date date = formatStringToDate(strDate, new SimpleDateFormat(format));
        return getDateMonthFirst(date);
    }

    /**
     * 获取指定时间所在月份的月初时间
     *
     * @return eg:2019-03-01 00:00:00
     */
    public static Date getDateMonthFirst(String strDate) {
        Date date = formatStringToDate(strDate, new SimpleDateFormat("yyyy-MM"));
        return getDateMonthFirst(date);
    }

    /**
     * 获取指定时间所在月份的月初时间
     *
     * @return eg:2019-03-01 00:00:00
     */
    public static Date getDateMonthFirst(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间所在月份的月末时间
     *
     * @return eg:2019-03-31 23:59:59
     */
    public static Date getDateMonthEnd(String strDate, String format) {
        Date date = formatStringToDate(strDate, new SimpleDateFormat(format));
        return getDateMonthEnd(date);
    }

    /**
     * 获取指定时间所在月份的月末时间
     *
     * @return eg:2019-03-31 23:59:59
     */
    public static Date getDateMonthEnd(String strDate) {
        Date date = formatStringToDate(strDate, new SimpleDateFormat("yyyy-MM"));
        return getDateMonthEnd(date);
    }

    /**
     * 获取指定时间所在月份的月末时间
     *
     * @return eg:2019-03-31 23:59:59
     */
    public static Date getDateMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * 获取时间月
     *
     * @param date 时间
     * @return
     */
    public static Integer getDateMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static Integer getCurrentDateMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前天(按月)
     */
    public static Integer getDateDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前天(按月)
     *
     * @return
     */
    public static Integer getCurrentDateDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算两个毫秒时间戳的天数，得到的天数默认向下取整
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getMillisecondFormatTimeDifference(long startTime, long endTime) {
        return (int) ((endTime - startTime) / DAY_MS);
    }


    /**
     * 漫游车业务，判断当前时间是否在出勤开始和结束时间范围内，如果是则是出勤中，如果否 则是休息中
     * 将入参时间  精确到小时位 后,进行小时级别的比较. <br>
     * 例:2019-12-11 11:12:13 与 2019-12-11 11:16:15比较时,其实是相等的小时时间.
     * date1 是否 早于 date2
     *
     * @param date1 非空
     * @param date2 非空
     * @return 0 相等,-1 date1 小于 date2, 1 date1 大于 date2
     */
    public static int compareHourTime(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException();
        }
        SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        try {
            Date hourDate1 = hourFormat.parse(hourFormat.format(date1));
            Date hourDate2 = hourFormat.parse(hourFormat.format(date2));
            return hourDate1.getTime() == hourDate2.getTime() ? 0 : (hourDate1.getTime() < hourDate2.getTime() ? -1 : 1);
        } catch (ParseException e) {
//            e.printStackTrace();
            //ignore will never occur
            return 0;
        }
    }



    /**
     * 获取时间Str
     *
     * @param dateTime 时间
     * @return
     */
    public static String getDateDayStr(Long dateTime) {
        if (dateTime == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(dateTime));
    }

    public static Date getAddDayLastTime(int num) {
        // 获取当天23点59分59秒Date
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        calendar.add(Calendar.DATE, num);
        //当前时间会带上毫秒，会产生四舍五入问题，会产生23:59:59变成第二天00:00:00
        long time = (calendar.getTime().getTime() / 1000) * 1000;
        return new Date(time);
    }

    /**
     * 将毫秒变成0
     * 时间会带上毫秒，会产生四舍五入问题，数据库里会产生23:59:59变成第二天00:00:00
     *
     * @return
     */
    public static Date formatMillisecondZero(Date date) {
        if (null == date) {
            return null;
        }
        //当前时间会带上毫秒，会产生四舍五入问题，会产生23:59:59变成第二天00:00:00
        long time = (date.getTime() / 1000) * 1000;
        return new Date(time);
    }

    /**
     * 计算两个时间之间相差的天数（只算天数，忽略后面的小时，分钟。。。）
     *
     * @return
     */
    public static int getDifferenceDay(Date before, Date after) {
        if (before == null || after == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(before);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long beforeTime = calendar.getTimeInMillis();

        calendar.setTime(after);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long afterTime = calendar.getTimeInMillis();

        return Long.valueOf((afterTime - beforeTime) / 24 / 3600 / 1000).intValue();
    }

    /**
     * 取时间归属的[年度,季度,月份]
     *
     * @param time
     * @return 如 [2020,3,7]
     */
    public static Integer[] getYearQuaterMonthNumber(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int quater = month / 3 + 1;
        return new Integer[]{year, quater, month + 1};
    }

    /**
     * 取截止入参时间的对应年的月份.
     *
     * @param time
     * @return [202007, 202006, ..202001]
     */
    public static List<String> getYearMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        List<String> yearMonth = new ArrayList<>();
        for (int i = month; i >= 0; i--) {
            yearMonth.add(String.format("%s%02d", year, i + 1));
        }
        return yearMonth;
    }

    /**
     * 取截止入参时间的对应季度的季度月份.
     *
     * @param time
     * @return [202006, 202005, 202004]
     */
    public static List<String> getQuaterMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int quater = month / 3 + 1;
        List<String> yearMonth = new ArrayList<>();
        for (int i = month; i >= 0; i--) {
            if (i / 3 + 1 != quater) {
                break;
            }
            yearMonth.add(String.format("%s%02d", year, i + 1));
        }
        return yearMonth;
    }

    /**
     * 获取起止时间之间的天
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<Integer> getBetweenDay(Long startTime, Long endTime) {
        if (Objects.isNull(startTime) || Objects.isNull(endTime) || startTime > endTime) {
            return Arrays.asList(Integer.valueOf(formatDateToInteger(new Date())));
        }
        if (startTime.equals(endTime)) {
            return Arrays.asList(Integer.valueOf(formatDateToInteger(new Date(startTime))));
        }

        List<Integer> result = new ArrayList<>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(new Date(startTime));
        max.setTime(new Date(endTime));
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(Integer.valueOf(formatDateToInteger(min.getTime())));
            curr.add(Calendar.DATE, 1);
        }
        return result;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            if (cal1 != null && cal2 != null) {
                return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
            }
        }
        return false;
    }
}
