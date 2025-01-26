package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.util.List;

/**
 * @Class DepartmentStatistics
 * @Author Create By Matrix·张
 * @Date 2025/1/25 上午10:14
 * 统计返回数据实体
 */
@Data
public class DepartmentStatistics {
    private String doctorDepartment;
    private List<Integer> sNumList;

    public DepartmentStatistics(String doctorDepartment, List<Integer> sNumList) {
        this.doctorDepartment = doctorDepartment;
        this.sNumList = sNumList;
    }
}
