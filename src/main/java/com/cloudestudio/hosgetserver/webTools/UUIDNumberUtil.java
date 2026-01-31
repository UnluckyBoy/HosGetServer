package com.cloudestudio.hosgetserver.webTools;

import java.util.UUID;

/**
 * @Class UUIDNumberUtil
 * @Author Create By Matrix·张
 * @Date 2024/11/26 下午2:56
 * 生成UUID工具类
 */
public class UUIDNumberUtil {
    /**
     * 生成8位UUID
     * @return
     */
    public static String randShortUUIDNumber(){
        UUID uuid = UUID.randomUUID();
        return TimeUtil.timeToString(uuid.toString().substring(0, 8));
    }
    public static String randMiddleUUIDNumber(){
        UUID uuid = UUID.randomUUID();
        return TimeUtil.timeToString(uuid.toString().substring(0, 13));
    }
    public static String randLongUUIDNumber(){
        UUID uuid = UUID.randomUUID();
        return TimeUtil.timeToString(uuid.toString());
    }

    /**
     * 生成8位UUID+时间
     * @return
     */
    public static String randUUIDNumberAndTime(){
        UUID uuid = UUID.randomUUID();
        return TimeUtil.timeToString(uuid.toString().substring(0, 8)+TimeUtil.GetTime(true));
    }

    /**
     * 时间+生成8位UUID
     * @return
     */
    public static String randTimeAndUUIDNumber(){
        UUID uuid = UUID.randomUUID();
        return TimeUtil.timeToString(TimeUtil.GetTime(true)+uuid.toString().substring(0, 8));
    }

    /**
     * 生成8位UUID+时间参数
     * @param time
     * @return
     */
    public static String randUUIDNumberAndTime_Param(String time){
        UUID uuid = UUID.randomUUID();
        return TimeUtil.timeToString(uuid.toString().substring(0, 8)+time);
    }
}
