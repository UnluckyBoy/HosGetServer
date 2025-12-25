package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class WorkNums
 * @Author Create By Matrix·张
 * @Date 2025/12/23 上午9:50
 * 门诊医生工作量实体
 */
@Data
public class WorkNums implements Serializable {
    private String M_DOCTOR;
    private String M_DEP;
    private String M_TYPE;
    private int PATIENT_NUM;
}
