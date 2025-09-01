package com.cloudestudio.hosgetserver.model.physicalExamination;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class PatientInfoBean
 * @Author Create By Matrix·张
 * @Date 2025/2/15 下午2:56
 * 体检接口患者信息实体
 */
@Data
public class PatientInfoBean implements Serializable {
    private String patientId;//就诊人 ID
    private String patientName;//就诊人姓名
    private String sex;//就诊人性别(1:男,0:女)
    private String birthday;//出生日期(yyyy-MM-dd)
    private String idCardType;//证件类型
    private String idCard;//证件号码
    private String nation;//民族
    private String citizenship;//国籍
    private String phone;//手机号
    private String medCardNo;//就诊卡号
    private String allergicHistory;//过敏史
    private String professional;//职业
    private String address;//省市区详细地址
}
