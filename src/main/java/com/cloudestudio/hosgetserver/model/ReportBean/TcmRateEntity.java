package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Class TcmRateEntity
 * @Author Create By Matrix·张
 * @Date 2026/9/17 上午11:06
 * 中治率实体
 */
@Data
public class TcmRateEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 科室 */
    private String department;
    /** ICD编码 */
    private String icdCode;
    /** ICD名称 */
    private String icdName;
    /** 中医治疗费用 */
    private BigDecimal tcmCost;
    /** 总费用 */
    private BigDecimal totalCost;
    /** 中医治疗率 */
    private String tcmRate;
    /** 人数*/
    private int pNum;
}
