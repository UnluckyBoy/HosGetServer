package com.cloudestudio.hosgetserver.webTools;

/**
 * @Class StringUtil
 * @Author Create By Matrix·张
 * @Date 2024/11/26 下午2:59
 * 字符串处理工具
 */
public class StringUtil {
    /**
     * 判断字符串是否为空或者null
     * @param str
     * @return
     */
    public static boolean isEmptyOrNull(String str){
        return str==null||str.isEmpty();
    }

    /**
     * 截取、之前的字符串
     * @param input
     * @return
     */
    public static String getSubstringBeforeFirstComma(String input) {
        // 使用 indexOf 方法查找第一个中文逗号的位置
        int commaIndex = input.indexOf("、");
        if (commaIndex != -1) {
            // 使用 substring 方法获取逗号之前的字符串
            return input.substring(0, commaIndex);
        } else {
            // 如果没有找到中文逗号，返回原始字符串
            return input;
        }
    }
}
