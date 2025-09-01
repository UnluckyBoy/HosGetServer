package com.cloudestudio.hosgetserver.model.paramBody;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class GreenChannelParamBody
 * @Author Create By Matrix·张
 * @Date 2025/6/15 下午8:21
 * 绿色通道请求参数
 */
@Data
public class GreenChannelCreateParamBody implements Serializable {
    private String personType;
    private String patientName;
    private String idCardTypeName;
    private String idCard;
    private String birthDate;
    private String patientAge;
    private String tel;
    private String contacts;
    private String contactsRelation;
    private String nationalityName;
    private String nationName;
    private String genderName;
    private String morbidity;
    private String inHosType;
    private String patientAddress;
    private String toHosTime;
    private String vitalSigns;
    private String systolicPressure;
    private String diastolicPressure;
    private String breathing;
    private String bloodSugar;
    private String temperature;
    private String heartRate;
    private String pulse;
    private String bloodOxygen;
    private String levelConsciousness;
    private String triageType;
    private String triageArea;
    private String triageDepartment;
    private String isGreenChannel;
    private String diseaseType;
    private String descriptionType;
    private String description;
    private String createOperator;
    private String registerTime;
    private String createTime;
}
