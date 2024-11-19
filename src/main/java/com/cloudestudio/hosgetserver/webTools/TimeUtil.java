package com.cloudestudio.hosgetserver.webTools;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * @Class TimeUtil
 * @Author Create By Matrix·张
 * @Date 2024/11/13 下午4:17
 * 时间类函数
 */
public class TimeUtil {
    /**
     *获取当前时间
     * @param add_hour 是否添加小时
     * @return
     */
    public static String GetTime(boolean add_hour){
        //获取当前系统时间
        //long time=System.currentTimeMillis();
        //new日期对象
        Date date =new Date(System.currentTimeMillis());
        //转换提日期输出格式
        if(add_hour){
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String st = dateFormat.format(date);
            return st;
        }else{
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
            String st = dateFormat.format(date);
            return st;
        }
    }

    /**
     * 去掉时间格式
     * yyyy-MM-dd变成YYYYMMDD
     * @param time
     * @return
     */
    public static String timeToString(String time) throws DateTimeParseException {
        return time.replaceAll("[- :]","");//通过replaceAll将"-"、" "、":"替换为""
    }

    /**
     * 将字符串为时间
     * @param startTimeStr
     * @return
     */
    public static Timestamp stringToTime(String startTimeStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(startTimeStr, formatter);
        // 如果你需要 Timestamp 对象，可以这样做：
        // 现在你可以将 Timestamp 对象传递给 MyBatis
        // 注意：Java 8 引入了新的日期时间 API，但 MyBatis 仍然使用 java.sql.Date, java.sql.Time, 和 java.sql.Timestamp
        //System.out.println("Timestamp: " + timestamp);
        return Timestamp.valueOf(startDate);
    }

    /**
     * 时间格式设置
     * @param time
     * @return
     */
    private static LocalDate setTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(time, formatter);
        return date;
    }

    /**
     * 平，润年计算
     * @param time
     * @return
     */
    public static boolean LeapYearChecker(String time){
        boolean isLeapYear = LocalDate.of(setTime(time).getYear(), 1, 1).isLeapYear(); // 判断是否为闰年
        return isLeapYear;
    }

    /**
     * 大小月计算
     * @param time
     * @return
     */
    public static boolean MonthSizeChecker(String time){
        Month monthEnum = Month.of(setTime(time).getMonthValue()); // 将月份转换为枚举类型
        if (monthEnum.length(false) == 31) {
            //System.out.println(" 大月");
            return true;
        } else {
            //System.out.println(" 小月");
            return false;
        }
    }
}
