package com.cloudestudio.hosgetserver.model.greenChannel;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class GreenChannelCaseHis
 * @Author Create By Matrix·张
 * @Date 2025/6/15 下午9:30
 * 绿色通道病历基类
 */
@Data
public class GreenChannelCaseHis implements Serializable {
    private String recordNumber;//病案号
    private String infoKey;
    private String patientId;
    private String idCardTypeName;//证件号类型
    private String patientName;//
    private String idCard;
    private String birthDate;
    private String patientAge;
    private String ageUnit;
    private String tel;
    private String contacts;
    private String contactsRelation;
    private String nationalityName;
    private String nationName;
    private String genderName;
    private String patientAddress;
    private String morbidity;
    private String inHosType;
    private String toHosTime;
    private String vitalSigns;
    private String systolicPressure;
    private String diastolicPressure;
    private String bloodPressure;
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
