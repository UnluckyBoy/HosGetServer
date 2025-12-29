package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Class SettlementBean
 * @Author Create By Matrix·张
 * @Date 2025/12/29 下午10:34
 * 门诊结算实体类
 */
@Data
public class SettlementBean implements Serializable {
    private String PATIENT_NAME;
    private String IDCARD_NUMBER;
    private String VISIT_NUMBER;
    private String SETTLEMENT_NUMBER;
    private String SETTLEMENT_TYPE;
    private String OPERATION_TIME;
    private String OPERATOR_NAME;
    private BigDecimal TOTAL_EXPENSES;
    private BigDecimal CASH_PAYMENT;
    private BigDecimal MEDICAL_TOTAL_AMOUNT;
    private BigDecimal BASIC_MEDICAL_AMOUNT;
    private BigDecimal PERSONAL_AMOUNT;
    private BigDecimal HOS_AMOUNT;
    private BigDecimal ASSISTANCE_AMOUNT;
    private BigDecimal CIVIL_SERVANT_AMOUNT;
    private BigDecimal SERIOUS_ILLNESS_AMOUNT;
    private BigDecimal BALANCE_PERSONAL_ACCOUNT;
}
