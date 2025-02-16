package com.cloudestudio.hosgetserver.model.paramBody;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class PatientVisitBody
 * @Author Create By Matrix·张
 * @Date 2025/2/16 下午2:25
 * 查询患者就诊记录请求Body
 */
@Data
public class PatientVisitBody implements Serializable {
    private String patientId;
    private String startDate;
    private String endDate;
}
