package com.cloudestudio.hosgetserver.model.greenChannel;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class PateintBean
 * @Author Create By Matrix·张
 * @Date 2025/7/4 下午1:14
 * 绿色通道患者实体
 */
@Data
public class PatientBean implements Serializable {
    private String infoKey;//三无人员标识
    private String patientId;
    private String idCardTypeName;//证件号类型
    private String patientName;//
    private String idCard;
    private String birthDate;
    private String patientAge;
    private String tel;
    private String contacts;
    private String contactsRelation;
    private String nationalityName;
    private String nationName;
    private String genderName;
    private String patientAddress;
}
