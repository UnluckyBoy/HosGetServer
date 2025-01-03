package com.cloudestudio.hosgetserver.model;

import lombok.Data;

/**
 * @Class ReportQueryPaitentBaseInfo
 * @Author Create By Matrix·张
 * @Date 2025/1/1 上午9:21
 * 报告卡填写查询患者基本信息类
 */
@Data
public class ReportQueryPatientBaseInfo {
    private String serial_number;
    private String patient_name;
    private String patient_idcard;
    private String gender_code;
    private String gender_name;
    private String patient_age;
    private String contract_tel;
    private String contracts;
    private String workunit;
}
