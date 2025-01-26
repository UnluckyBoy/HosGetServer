package com.cloudestudio.hosgetserver.model;

import lombok.Data;

/**
 * @Class PathologyPatientInfoBean
 * @Author Create By Matrix·张
 * @Date 2025/1/20 下午12:31
 * 病理申请检查信息实体类
 */
@Data
public class PathologyPatientInfoBean {
    private String serialNumber;
    private String patientName;
    private String patientGender;
    private String patientAge;
    private String patientBedNum;
    private String doctorName;
    private String doctorDepartment;
    private String patientTel;
    private String inspectionItem;
    private String inspectionTime;
    private String diagnose;
    private String reportPath;
}
