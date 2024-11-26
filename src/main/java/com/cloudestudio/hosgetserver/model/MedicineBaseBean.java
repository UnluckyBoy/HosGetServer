package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Class MedicineBaseBean
 * @Author Create By Matrix·张
 * @Date 2024/11/25 下午1:57
 * 药剂基类
 */
@Data
public class MedicineBaseBean implements Serializable {
    private String medicine_code;
    private String medicine_name;
    private BigDecimal medicine_price;
    private BigDecimal medicine_retail;
    private String medicine_time;
}
