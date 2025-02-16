package com.cloudestudio.hosgetserver.model.physicalExamination;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class VisitRecordBean
 * @Author Create By Matrix·张
 * @Date 2025/2/16 下午9:09
 * 就诊记录Bean
 */
@Data
public class VisitRecordBean implements Serializable {
    private String type;
    private String clinicNo;
    private String patientId;
    private String patientName;
    private String sex;
    private String age;
    private String visitTime;
    private String deptName;
    private String doctorName;
    private String marriage;
    private String address;
}
