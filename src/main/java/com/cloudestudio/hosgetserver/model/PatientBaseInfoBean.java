package com.cloudestudio.hosgetserver.model;

import lombok.Data;

/**
 * @Class PatientBaseInfoBean
 * @Author Create By Matrix·张
 * @Date 2024/12/14 下午2:46
 * 患者信息基类
 */
@Data
public class PatientBaseInfoBean {
    private String id;
    private String patientName;
    private String idCardTypeCode;
    private String idCardTypeName;
    private String idCard;
    private String genderCode;
    private String genderName;
    private String birthDate;
    private String nationalityCode;
    private String nationalityName;
    private String nationCode;
    private String nationName;
    private String permanentAddrCode;
    private String permanentAddrName;
    private String permanentAddrDetail;
    private String currentAddrCode;
    private String currentAddrName;
    private String currentAddrDetail;
    private String workUnit;
    private String maritalStatusCode;
    private String maritalStatusName;
    private String educationCode;
    private String educationName;
    private String nultitudeTypeCode;
    private String nultitudeTypeName;
    private String tel;
    private String contacts;
    private String contactsTel;
    private String orgCode;
    private String orgName;
    private String operatorId;
    private String operationTime;
}
