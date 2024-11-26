package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class PrintStyleBean
 * @Author Create By Matrix·张
 * @Date 2024/11/26 下午3:28
 * 打印格式实体
 */
@Data
public class PrintStyleBean implements Serializable {
    private int print_code;
    private String print_name;
    private String print_style;
}
