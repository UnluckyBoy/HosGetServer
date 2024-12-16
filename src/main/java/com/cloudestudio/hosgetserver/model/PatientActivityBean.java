package com.cloudestudio.hosgetserver.model;

import lombok.Data;

/**
 * @Class PatientActivityBean
 * @Author Create By Matrix·张
 * @Date 2024/12/16 下午4:30
 * 患者门诊信息基类
 */
@Data
public class PatientActivityBean {
    private String id;
    private String patientId;
    private String activityTypeCode;
    private String activityTypeName;
    private String serialNumber;
    private String activityTime;
    private String patientName;
    private String idCardTypeCode;
    private String idCardTypeName;
    private String idCard;
    private String chiefComplaint;
    private String presentIllnessHis;
    private String physicalExamination;
    private String studiesSummaryResult;
    private String diagnoseTime;
    private String diseaseCode;
    private String diseaseName;
    private String wmDiseaseCode;
    private String wmDiseaseName;
    private String tcmDiseaseCode;
    private String tcmDiseaseName;
    private String tcmSyndromeCode;
    private String tcmSyndromeName;
    private String fillDoctor;
    private String deptCode;
    private String deptName;
    private String orgCode;
    private String orgName;
    private String operatorId;
    private String operationTime;
}
