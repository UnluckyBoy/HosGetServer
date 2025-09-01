package com.cloudestudio.hosgetserver.model.greenChannel;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class CreateHisBaen
 * @Author Create By Matrix·张
 * @Date 2025/7/5 上午9:15
 * 创建病案实体类
 */
@Data
public class CreateHisBean implements Serializable {
    private String recordNumber;
    private String patientId;
    private String morbidity;
    private String inHosType;
    private String toHosTime;
    private String vitalSigns;
    private String systolicPressure;
    private String diastolicPressure;
    private String breathing;
    private String bloodSugar;
    private String temperature;
    private String heartRate;
    private String pulse;
    private String bOxygenSaturation;
    private String levelConsciousness;
    private String triageType;
    private String triageArea;
    private String triageDepartment;
    private String isGreenChannel;
    private String diseaseType;
    private String descriptionType;
    private String description;
    private String createOperator;
    private String createTime;
    private String caseTime;//分诊耗时
}
