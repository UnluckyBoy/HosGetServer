package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class OrderBean
 * @Author Create By Matrix·张
 * @Date 2024/11/29 下午1:19
 * 订单属性类
 */
@Data
public class OrderBean implements Serializable {
    private String order_uid;
    private String medicine_code;
    private String medicine_batch_number;
    private String order_time;
    private String order_status;
    private String order_amount;
    private String order_quantity;
    private String order_outWareHouse;
}
