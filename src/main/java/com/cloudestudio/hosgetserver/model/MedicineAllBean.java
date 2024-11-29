package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class MedicineAllBean
 * @Author Create By Matrix·张
 * @Date 2024/11/29 上午8:45
 * 药剂所有属性Bean
 */
@Data
public class MedicineAllBean implements Serializable {
    private String medicine_code;
    private String medicine_name;
    private String medicine_price;
    private String medicine_retail;
    private String medicine_time;//字典创建时间
    private int medicine_batch_number;
    private int warehouse_count;
    private int canuse_count;
    private int inwarehouse_count;
    private String create_time;//生产日期
    private String towarehouse_time;
    private String towarehouse_operator;
    private String outwarehouse_time;
    private String outwarehouse_operator;
    private String ware_addr;
}
