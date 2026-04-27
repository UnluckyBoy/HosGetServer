package com.cloudestudio.hosgetserver.model.HealthTool;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class Patientbasicinfo
 * @Author Create By Matrix·张
 * @Date 2026/4/9 下午12:19
 * 全民健康信息平台-患者基本信息
 */
@Data
public class PatientBasicInfo implements Serializable {
    // 患者ID
    private String PatientID;
    // 作者组织
    private String AuthorOrganization;
    // 作者组织名称
    private String AuthorOrganizationName;
    // 健康记录ID
    private String HealthRecordId;
    // 身份证
    private String IdCard;
    // 身份证代码
    private String IdCardCode;
    // 健康卡ID
    private String HealthCardId;
    // 医疗保险类别代码
    private String MedicalInsuranceCategoryCode;
    // 健康保险卡ID
    private String HealthInsuranceCardId;
    // 自费卡ID
    private String SelfPaidCardId;
    // 姓名
    private String Name;
    // 性别
    private String Sex;
    // 出生日期
    private String BirthDate;
    // 婚姻状态
    private String MaritalStatus;
    // 国籍
    private String Nationality;
    // 民族
    private String EthnicGroup;
    // 职业类别代码
    private String OccupationCategoryCode;
    // 患者电话
    private String PatientPhone;
    // 工作单位
    private String WorkUnit;
    // 工作地址电话
    private String WorkAddrPhone;
    // 系统时间
    private String SystemTime;
    // 作者
    private String Author;
    // 隐私标识
    private String PrivacySign;
    // 源患者ID
    private String SourcePatientId;
    // 源患者ID类型
    private String SourcePatientIdType;
    // 地址类型代码
    private String AddrTypeCode;
    // 地址类型名称
    private String AddrTypeName;
    // 地址
    private String Address;
    // 省份
    private String Province;
    // 城市
    private String City;
    // 县
    private String County;
    // 镇
    private String Town;
    // 村
    private String Village;
    // 门牌号
    private String HouseNumber;
    // 邮政编码
    private String PostalCode;
    // 与患者关系
    private String RelationShipWithPatient;
    // 联系人
    private String ContactPerson;
    // 联系人电话
    private String ContactPersonTel;
    // 联系人身份证
    private String ContactIdCard;
    // 联系人身份证代码
    private String ContactIdCardCode;
    // 时间戳
    private String TimeStamp;
    // 操作类型
    private int OperationType;
    // 卡类型
    private String CardType;
    // 发行地区
    private String IssuingRegion;
    // 修改标志
    private int ModifyFlag;
    // JRCSDM
    private String JRCSDM;
}
