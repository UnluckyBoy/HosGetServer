package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class MedicineOrderBean
 * @Author Create By Matrix·张
 * @Date 2024/11/30 上午9:12
 * 销售订单&药剂属性基类
 */
@Data
public class MedicineOrderBean implements Serializable {
    private String order_uid;
    private String medicine_code;
    private String medicine_batch_number;
    private String order_time;
    private String order_status;
    private String order_amount;
    private String order_seller;
    private String order_quantity;
    private String order_outWareHouse;
    private String sell_type;
    private String medicine_name;
    private String medicine_price;
    private String medicine_retail;
    private String medicine_time;//字典创建时间
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
