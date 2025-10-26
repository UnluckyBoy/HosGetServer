package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class DayOutPatient
 * @Author Create By Matrix·张
 * @Date 2025/10/26 下午4:31
 * 当日门诊量实体类
 */
@Data
public class DayOutPatientBean implements Serializable {
    private int dayNum;//门诊人次数
    private String inDepartment;//门诊科室
}
