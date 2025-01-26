package com.cloudestudio.hosgetserver.model;

import lombok.Data;

/**
 * @Class ReportCardBody
 * @Author Create By Matrix·张
 * @Date 2025/1/6 下午4:41
 * 报告卡请求实体类
 */
@Data
public class ReportCardBody {
    private String reportCardId;
    private String reportCardType;
    private String serialNumber;
    private String patientName;
    private String patientGender;
    private String patientAge;
    private String patientTel;
    private String patientContract;
    private String patientBirthday;
    private String idCardType;
    private String idCard;
    private String workUnit;
    private String currentAddrDetailed;
    private String illnessTime;
    private String diagnosisTime;
    private String deathTime;
    private String addrType;
    private String personType;
    private String personTypeOther;
    private String illnessType;
    private String diagnosisType;
    private String diseaseA;
    private String diseaseB;
    private String diseaseC;
    private String diseaseD;
    private String maritalStatus;
    private String educationLevel;
    private String covid19Level;
    private String covid19Type;
    private String covid19OutHosTime;
    private String stdExposurePattern;
    private String stdExposureSource;
    private String stdExposureType;
    private String stdExposureOrg;
    private String stdExposureTime;
    private String stdExposureSample;
    private String hepatitisBHBsAgTime;
    private String hepatitisBIgM1;
    private String hepatitisBPunctureResult;
    private String hepatitisBHBsAgResult;
    private String hepatitisBTime;
    private String hepatitisBAlt;
    private String hfmDiseaseResult;
    private String hfmDiseaseLevel;
    private String monkeyPoxResult;
    private String pertussisLevel;
    private String intimateContactSymptom;
    private String updateDiseaseName;
    private String rollbackCardReason;
    private String reportOrg;
    private String reportDoctor;
    private String reportDoctorTel;
    private String reportCreateTime;
    private String remark;
}
