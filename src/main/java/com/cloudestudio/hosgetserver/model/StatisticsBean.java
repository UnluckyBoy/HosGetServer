package com.cloudestudio.hosgetserver.model;

import lombok.Data;

/**
 * @Class StatisticsBean
 * @Author Create By Matrix·张
 * @Date 2025/1/24 上午8:04
 * 统计结果实体类
 */
@Data
public class StatisticsBean {
    private String doctorDepartment;
    private String sMonth;
    private int sNum;
}
