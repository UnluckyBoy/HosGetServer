package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Class TcmRateEntityByNum
 * @Author Create By Matrix·张
 * @Date 2026/10/28 下午3:15
 */
@Data
public class TcmRateEntityByNum implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String inHosNum;
    private String patientName;
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
}
