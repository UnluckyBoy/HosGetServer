package com.cloudestudio.hosgetserver.webTools;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Class MatchAddrUtil
 * @Author Create By Matrix·张
 * @Date 2024/12/27 上午9:48
 * 住址匹配
 */
public class MatchAddrUtil {
    private static final String REGEX =
            "(?<province>(?:[^省]+省|[^自治区]+自治区|[^特别行政区]+特别行政区|[^直辖市]+直辖市|上海|北京|天津|重庆))" +
            "(?<city>(?:[^市]+自治州|[^县]+县(?:市)?|[^区]+区|上海城区|北京城区|天津城区|重庆城区|重庆郊县|.*?市(?:辖区)?))" +
            "(?<county>(?:[^乡]+乡|[^镇]+镇|[^县]+县|[^区]+区|[^旗]+旗|[^连]+连|[^市]+市(?:辖区)?|.*?路氹城))?"+
            "(?<street>[^街道]+街道)?";

    public static String matchAddr(String addr){
        String result=null;
        String searchStr_ZY="镇远县";
        String searchStr_XJ="建设兵团";//西藏
        if (addr.contains(searchStr_ZY)) {
            // 获取包含子字符串之后的部分
            int startIndex = addr.indexOf(searchStr_ZY) + searchStr_ZY.length();
            result = addr.substring(startIndex);
            System.out.println(TimeUtil.GetTime(true)+" 包含"+searchStr_ZY+"的截取："+result);
        } else if(addr.contains(searchStr_XJ)){
            int startIndex = addr.indexOf(searchStr_XJ) + searchStr_XJ.length();
            result = addr.substring(startIndex);
            System.out.println(TimeUtil.GetTime(true)+" 包含"+searchStr_XJ+"的截取："+result);
        }else {
            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(addr);
            if (matcher.find()) {
                String province = matcher.group("province");
                String city = matcher.group("city");
                String county = matcher.group("county");
                String street = matcher.group("street");

                //System.out.println("截取:"+province + ", " + city + (county != null && !county.isEmpty() ? ", " + county : "")+(street!=null && !street.isEmpty() ? ", " + street : ""));
                if(!StringUtil.isEmptyOrNull(street)){
                    System.out.println(TimeUtil.GetTime(true)+"  住址截取-street:"+street);
                    result= street;
                }else if(StringUtil.isEmptyOrNull(street) && !StringUtil.isEmptyOrNull(county)){
                    System.out.println(TimeUtil.GetTime(true)+"  住址截取-county:"+county);
                    result= county;
                }else if(StringUtil.isEmptyOrNull(street) && StringUtil.isEmptyOrNull(county) && !StringUtil.isEmptyOrNull(city)){
                    System.out.println(TimeUtil.GetTime(true)+"  住址截取-city:"+city);
                    result= city;
                }
            }
        }
        return result;
    }
}
