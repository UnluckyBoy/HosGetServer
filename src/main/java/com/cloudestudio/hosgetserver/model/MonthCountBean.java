package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class MonthCountBean
 * @Author Create By Matrix·张
 * @Date 2024/12/2 下午4:54
 * 月订单量Bean
 */
@Data
public class MonthCountBean implements Serializable {
    private String month_number;
    private String order_count;
    private String valid_count;

    public String getMonthName(String monthNumber) {
        return switch (monthNumber) {
            case "1" -> "1月";
            case "2" -> "2月";
            case "3" -> "3月";
            case "4" -> "4月";
            case "5" -> "5月";
            case "6" -> "6月";
            case "7" -> "7月";
            case "8" -> "8月";
            case "9" -> "9月";
            case "10" -> "10月";
            case "11" -> "11月";
            case "12" -> "12月";
            default -> null;
        };
    }
}
