package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class OutSettlementReport
 * @Author Create By Matrix·张
 * @Date 2025/10/12 下午2:47
 * 门诊结算分析报表
 */
@Data
public class OutSettlementReport implements Serializable {
    private String settlementId;//结算ID
    private String serialNumber;
    private String patientName;
    private String insuranceType;//保险类别
    private String expenseType;//费别
    private String expenseTotal;//费用合计
    private String cashAmount;//现金支付
    private String medicalAmount;//医疗卡支付
    private String otherTotal;//其他支付合计
    private String insuranceAmount;//保险支付
    private String hospAmount;//医院承担
    private String otherAmount;//其他支付
    private String remark;//备注
    private String settlementTime;//结算日期
    private String operator;//操作员
    private String paymentVoucher;//交款凭据号
    private String patientId;//患者ID
}