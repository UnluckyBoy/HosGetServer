package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class PrescriptionReviewBean
 * @Author Create By Matrix·张
 * @Date 2026/2/12 下午4:57
 * 处方查询信息实体
 */
@Data
public class PrescriptionReviewBean implements Serializable {
    private String patientNumber;
    private String patientName;
    private String cfNumber;
    private String departName;
    private String doctorName;
    private String inHosDate;
    private String cfDate;
    private String primaryDiagnosis;
    private String secondaryDiagnosis;
    private String medicine;
    private String medicineSpecification;
    private String medicineDosage;
    private String allDosage;
}
