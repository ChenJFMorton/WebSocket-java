package com.chenjf.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author William Chen(chenkewei@maijia.com)
 * @Description 日期工具类
 * @date 2016/8/27 13:38
 */
public class CalendarUtils {

    private static final String dateFormatPatternYyyyMM                  = "yyyy-MM";
    private static final String dateFormatPatternYyyyMMdd                = "yyyy-MM-dd";
    private static final String dateFormatPatternYyyyMMddHHmmss          = "yyyy-MM-dd HH:mm:ss";
    private static final String dateFormatPatternEEEMMMddHHmmsszzzyyyy   = "EEE MMM dd HH:mm:ss zzz yyyy";
    private static final double milliSecondOfOneDay = 24 * 60 * 60 * 1000.0;

    //月份名称简写与数字的对应Map
    private static final Map<String, String> monthShortStringAndNumberMap = new HashMap<String, String>();
    //时区与UTC(协调世界时)偏差时长的map
    private static final Map<String, Integer> timeZoneMap = new HashMap<String, Integer>();

    static{
        monthShortStringAndNumberMap.put("Jan",  "01");
        monthShortStringAndNumberMap.put("Feb",  "02");
        monthShortStringAndNumberMap.put("Mar",  "03");
        monthShortStringAndNumberMap.put("Apr",  "04");
        monthShortStringAndNumberMap.put("May",  "05");
        monthShortStringAndNumberMap.put("Jun",  "06");
        monthShortStringAndNumberMap.put("Jul",  "07");
        monthShortStringAndNumberMap.put("Agu",  "08");
        monthShortStringAndNumberMap.put("Sep",  "09");
        monthShortStringAndNumberMap.put("Oct",  "10");
        monthShortStringAndNumberMap.put("Nov",  "11");
        monthShortStringAndNumberMap.put("Dec",  "12");

        timeZoneMap.put("PDT", -7);//太平洋夏时制
        timeZoneMap.put("PST", -8);//太平洋标准时间
        timeZoneMap.put("EDT", -4);//美国东部夏时制
        timeZoneMap.put("CDT", -5);//美国中部夏时制
        timeZoneMap.put("EST", -5);//美国东部标准时间
        timeZoneMap.put("CST", -6);//美国中部标准时间
        timeZoneMap.put("MDT", -6);//美国山地夏时制
        timeZoneMap.put("MST", -7);//美国山地标准时间
        timeZoneMap.put("AKDT", -8);//阿拉斯加白昼时间
        timeZoneMap.put("AKST", -9);//阿拉斯加标准时间
        timeZoneMap.put("HDT", -9);//夏威夷/阿拉斯加夏时制
        timeZoneMap.put("AHST", -10);//夏威夷-阿拉斯加标准时间
        timeZoneMap.put("HST", -10);//夏威夷标准时间
        timeZoneMap.put("CAT", -10);//中阿拉斯加时间
        timeZoneMap.put("NT", -11);//阿拉斯加诺姆时间（Nome Time）
    }
    /**
     * 将 "yyyy-MM-dd"形式的字符串转化为Date，异常情况下默认返回系统当前时间
     * @param YyyyMMString
     * @return
     */
    public static Date transferYyyyMMStringToDate(String YyyyMMString){
        DateFormat sdf = new SimpleDateFormat(dateFormatPatternYyyyMM);
        try {
            return sdf.parse(YyyyMMString);
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * 将Long转化为 "yyyy-MM"形式字符串
     * @param Time
     * @return
     */
    public static String transferLongToYyyyMMString(Long Time){
        if(Time == null){
            return "";
        }
        Date date  = new Date();
        date.setTime(Time);
        DateFormat sdf = new SimpleDateFormat(dateFormatPatternYyyyMM);
        return sdf.format(date);
    }

    /**
     * 返回两个Long 时间之间的所有月 List<String> "yyyy-MM"
     * @param
     * @param
     * @return
     */
    public static List<String> returnMonthsBetweenTwoLong(Long startDate,Long endDate){
        if(startDate==null||endDate==null){
            return new ArrayList<String>();
        }
        //算两个Long 之间相差的月份数量
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTimeInMillis(startDate);
        endTime.setTimeInMillis(endDate);

        Integer a  = endTime.get(Calendar.MONTH)-startTime.get(Calendar.MONTH);
        Integer b = (endTime.get(Calendar.YEAR) - startTime.get(Calendar.YEAR))*12;
        Integer diffMonth = a+b;
        //初始化结果集合
        List<String> list = new ArrayList<String>();
        list.add(transferLongToYyyyMMString(startDate));
        // 循环遍历在List 中add String
        for(int i = 0;i<diffMonth;i++){
            startTime.add(Calendar.MONTH,1);
            Long date = startTime.getTime().getTime();
            String month = transferLongToYyyyMMString(date);
            list.add(month);

        }
        return list;
    }

    /**
     * 将Date转化为 "yyyy-MM-dd"形式的字符串
     * @param date
     * @return
     */
    public static String transferDateToYyyyMMddString(Date date) {
        DateFormat sdf = new SimpleDateFormat(dateFormatPatternYyyyMMdd);
        return sdf.format(date);
    }

    /**
     * 将Date转化为 "yyyy-MM-dd HH:mm:ss"形式的字符串
     * @param date
     * @return
     */
    public static String transferDateToYyyyMMddHHmmssString(Date date) {
        DateFormat sdf = new SimpleDateFormat(dateFormatPatternYyyyMMddHHmmss);
        return sdf.format(date);
    }

    /**
     * 将"yyyy-MM-dd HH:mm:ss"形式的字符串转化为Date
     * @param YyyyMMddHHmmssString
     * @return
     */
    public static Date transferYyyyMMddHHmmssStringToDate(String YyyyMMddHHmmssString) {
        DateFormat sdf = new SimpleDateFormat(dateFormatPatternYyyyMMddHHmmss);
        try {
            return sdf.parse(YyyyMMddHHmmssString);
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * 将"EEE MMM dd HH:mm:ss zzz yyyy"形式的字符串转化为Date
     * @param EEEMMMddHHmmsszzzyyyyString
     * @return
     */
    public static Date transferEEEMMMddHHmmsszzzyyyyStringToDate(String EEEMMMddHHmmsszzzyyyyString) {
        DateFormat sdf = new SimpleDateFormat(dateFormatPatternEEEMMMddHHmmsszzzyyyy, Locale.US);
        try {
            return sdf.parse(EEEMMMddHHmmsszzzyyyyString);
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * 将 "yyyy-MM-dd"形式的字符串转化为Date，异常情况下默认返回系统当前时间
     * @param YyyyMMddString
     * @return
     */
    public static Date transferYyyyMMddStringToDate(String YyyyMMddString){
        DateFormat sdf = new SimpleDateFormat(dateFormatPatternYyyyMMdd);
        try {
            return sdf.parse(YyyyMMddString);
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * 将 "yyyy-MM-dd"形式的字符串转化为Date，异常情况下默认返回空
     * @param YyyyMMddString
     * @return
     */
    public static Date transferYyyyMMddStringToDateWithoutDefault(String YyyyMMddString){
        DateFormat sdf = new SimpleDateFormat(dateFormatPatternYyyyMMdd);
        try {
            return sdf.parse(YyyyMMddString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将查询库中存储的ebay产品刊登时间，如"Mar-03-08 11:18:43 PST"形式的字符串转化为Date，且为北京时间
     * @param ebayGentimeString
     * @return
     */
    public static Date transferEbayGentimeStringToBJDate(String ebayGentimeString){
        //声明返回的结果
        Date result = null;
        //用空格分隔字符串
        String[] stringS = ebayGentimeString.split(" ");
        if(stringS.length == 3) {
            //声明一个对象，用于存储"yyyy-MM-dd HH:mm:ss"型结果
            StringBuffer sb = new StringBuffer();
            //1.获取日期
            String[] dateS = stringS[0].split("-");
            sb.append(Integer.parseInt(dateS[2]) > 95 ? "19" + dateS[2] : "20" + dateS[2])//ebay是1995年成立的
                    .append("-").append(monthShortStringAndNumberMap.get(dateS[0]))
                    .append("-").append(dateS[1])
                    .append(" ")
                    .append(stringS[1]);//2.获取时分秒
            //把"yyyy-MM-dd HH:mm:ss"型结果转换成Date型
            result = transferYyyyMMddHHmmssStringToDate(sb.toString());
            //3.获取时区，把所有时间转换成北京时间
            Integer deviation = timeZoneMap.get(stringS[2]);
            Calendar cal = Calendar.getInstance();
            if(result != null) {
                cal.setTime(result);
            }
            cal.add(Calendar.HOUR_OF_DAY, deviation - 8);//北京时间是东八区，比标准时间早8小时

            result = cal.getTime();
        }
        return result;
    }

    /**
     * 获取某日期前一天的日期的字符串
     *
     * @param date 日期。若不传，则默认返回前一天的日期字符串！
     * @return
     */
    public static String getYesterdayYyyyMMddDateString(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DATE, -1);
        return transferDateToYyyyMMddString(cal.getTime());
    }

    /**
     * 获取"yyyy-MM-dd"形式的字符串前一天的日期，并以"yyyy-MM-dd"形式返回
     * @param YyyyMMddString
     * @return
     */
    public static String getYesterdayYyyyMMddDateStringFromYyyyMMddDateString(String YyyyMMddString) {
        Date date = transferYyyyMMddStringToDate(YyyyMMddString);
        return getYesterdayYyyyMMddDateString(date);
    }

    /**
     * 获取某日期后一天的日期的字符串
     *
     * @param date 日期。若不传，则默认返回明天的日期字符串！
     * @return
     */
    public static String getNextdayYyyyMMddDateString(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DATE, 1);
        return transferDateToYyyyMMddString(cal.getTime());
    }

    /**
     * 判断字符串是否为日期格式
     *
     * @param dateStr 待校验字符串
     * @return
     */
    public static boolean isDateStr(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormatPatternYyyyMMdd);
        try {
            format.parse(dateStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 比较两个日期字符串,比较到天
     *
     * @param dateStr1 待比较日期字符串1
     * @param dateStr2 待比较日期字符串2
     * @return 若前者小于后者则返回-1；
     *           前者等于或者则返回0；
     *           前者大于后者则返回1；
     */
    public static int compareDateStr(String dateStr1, String dateStr2) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormatPatternYyyyMMdd);
        try {
            Date date1 = format.parse(dateStr1);
            Date date2 = format.parse(dateStr2);

            if (date1.before(date2)) {
                return -1;
            } else if (date1.equals(date2)) {
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("字符串为非日期格式！");
        }
    }


    /**
     * 获取某日期前30天的日期的字符串
     * @param date 日期。若不传，则默认返回前30天的日期字符串！
     * @return
     */
    public static String get30DaysAgoYyyyMMddDateString(Date date){
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DATE, -30);
        return transferDateToYyyyMMddString(cal.getTime());
    }

    /**
     * 获取某日期前7天的日期的字符串
     * @param date 日期。若不传，则默认返回前7天的日期字符串！
     * @return
     */
    public static String get7DaysAgoYyyyMMddDateString(Date date){
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DATE, -7);
        return transferDateToYyyyMMddString(cal.getTime());
    }

    /**
     * 获取某日期前60天的日期的字符串
     * @param date 日期。若不传，则默认返回前7天的日期字符串！
     * @return
     */
    public static String get60DaysAgoYyyyMMddDateString(Date date){
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DATE, -60);
        return transferDateToYyyyMMddString(cal.getTime());
    }

    /**
     * 获取某日期一个月后日期的字符串
     * @param date 日期。若不传，则默认返回当前日期一个月后的日期字符串！
     * @return
     */
    public static String get1MonthLaterYyyyMMddDateString(Date date){
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, 1);
        return transferDateToYyyyMMddString(cal.getTime());
    }

    /**
     * 获取某日期x小时前的日期的字符串
     * @param date 日期。若不传，则默认返回前7天的日期字符串！
     * @param num 小时数
     * @return
     */
    public static String getXHoursAgoYyyyMMddHHmmssDateString(Date date, Integer num){
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, -num);
        return transferDateToYyyyMMddHHmmssString(cal.getTime());
    }

    /**
     * 获取某日期x小时前的日期的字符串
     * @param date 日期。若不传，则默认返回前7天的日期字符串！
     * @param num 小时数
     * @return
     */
    public static Date getXHoursAgoYyyyMMddHHmmssDate(Date date, Integer num){
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, -num);
        return cal.getTime();
    }

    /**
     * 返回某日期所属月份的最后一天的日期，以yyyy-MM-dd格式返回
     * @param date 日期。若不传，则默认返回当前日期所属月份的最后一天！
     * @return
     */
    public static String getLastDateYyyyMMddDateStringOfOneDateMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return transferDateToYyyyMMddString(cal.getTime());
    }

    /**
     * 返回某日期所属月份的第一天的日期
     * @param date 日期。若不传，则默认返回当前日期所属月份的第一天！
     * @return
     */
    public static Date getFirstDateYyyyMMddDateOfOneDateMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * 分析剩余天数
     * 先计算过去的某个时间date距离当前时间的天数，再和interval比较获取剩余天数
     * @param date
     * @param interval
     * @return 剩余天数；如果比较结果小于0，则返回0
     */
    public static int getLeftDay(Date date, int interval){
        Date now = new Date();
        long gap = now.getTime() - date.getTime();
        int leftDays = (interval - (int)Math.floor(gap / milliSecondOfOneDay));
        if(leftDays < 0 ) {
            return 0;
        }
        else {
            return leftDays;
        }
    }

    /**
     * 比较2个Date型时间相差的天数，忽略时分秒
     * @param date1 时间1
     * @param date2 时间2
     * @return
     */
    public static int getIntervalDaysIgnoreHHMMSS(Date date1, Date date2) {
        //先把2个时间转换为yyyyMMdd格式的日志所对应的Date
        Date date1IgnoreHHMMSS = transferYyyyMMddStringToDate(transferDateToYyyyMMddString(date1));
        Date date2IgnoreHHMMSS = transferYyyyMMddStringToDate(transferDateToYyyyMMddString(date2));
        //计算相差的天数
        int interval = (int) ((date2IgnoreHHMMSS.getTime() - date1IgnoreHHMMSS.getTime()) / milliSecondOfOneDay);
        return Math.abs(interval);
    }

    /**
     * 比较2个Date型时间相差的天数，忽略时分秒
     * @param date1 时间1
     * @param date2 时间2
     * @return
     */
    public static int getIntervalDaysIgnoreHHMMSS(String date1, String date2) {
        //先把2个时间转换为yyyyMMdd格式的日志所对应的Date
        Date date1IgnoreHHMMSS = transferYyyyMMddStringToDate(date1);
        Date date2IgnoreHHMMSS = transferYyyyMMddStringToDate(date2);
        //计算相差的天数
        int interval = (int) ((date2IgnoreHHMMSS.getTime() - date1IgnoreHHMMSS.getTime()) / milliSecondOfOneDay);
        return Math.abs(interval);
    }

    /**
     * 获取某日期加减天数后的日期
     * @param date 日期。
     * @param days 天数
     * @return
     */
    public static Date addYyyyMMddHHmmssDateString(Date date, Integer days){
        Calendar cal = Calendar.getInstance();
        if(date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    public static void main(String[] args) {
        Date date = getFirstDateYyyyMMddDateOfOneDateMonth(new Date());
        System.out.println(date);
    }

}
