package com.cloudestudio.hosgetserver.webTools;


import java.util.Base64;

/**
 * @Class BCrybtEncodeUtil
 * @Author Create By Matrix·张
 * @Date 2024/11/19 下午11:22
 * BCrybt加密解密工具类
 */

public class MatrixEncodeUtil {
    private static final String matrixKey="+CloudStudio";

    // 将字符串转换为Base64编码的乱码
    public static String encodeToBase64DoublePara(String input,String strKey) {
        byte[] byteArray = (input+matrixKey+strKey).getBytes();
        return Base64.getEncoder().encodeToString(byteArray);
    }
    public static String encode(String input) {
        byte[] byteArray = (input).getBytes();
        return Base64.getEncoder().encodeToString(byteArray);
    }
    /**
     * 循环加码两次
     * @param input
     * @return
     */
    public static String encodeTwice(String input) {
        String firstEncoded = Base64.getEncoder().encodeToString(input.getBytes());
        // 第二次Base64编码，这次是对第一次编码后的字符串进行编码
        return Base64.getEncoder().encodeToString(firstEncoded.getBytes());
    }

    // 将Base64编码的乱码还原为原始字符串
    public static String decodeFromBase64(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }
    //针对encodeTwice进行解码两次
    public static String decodeTwice(String doubleEncodedInput) {
        // 第一次Base64解码
        byte[] firstDecodedBytes = Base64.getDecoder().decode(doubleEncodedInput);
        String firstDecodedString = new String(firstDecodedBytes);
        // 第二次Base64解码
        return decodeFromBase64(firstDecodedString);
    }
}