package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Class DayDynamicEntity
 * @Author Create By Matrix·张
 * @Date 2026/8/27 上午9:21
 * 日均动态数据
 */
@Data
public class DayDynamicEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 科室名称（通过fn_get_ksmc函数转换得到）
     */
    private String department;

    /**
     * 门诊人次 (MZRC)
     */
    private Long mzrc;

    /**
     * 开具住院人次 (KJZYRC)
     */
    private Long kjzyrc;

    /**
     * 开具中医CT人次 (ZYCTRC)
     */
    private Long zyctrc;

    /**
     * 门诊次均费用 (MZCJ)，保留2位小数
     */
    private BigDecimal mzcj;

    /**
     * 总费用 (TOTAL_FY)
     */
    private BigDecimal totalFy;

    /**
     * 检验费用 (JY_FY)
     */
    private BigDecimal jyFy;

    /**
     * 检验收入占比 (JYSRRATE)，格式：百分比字符串，如 "25.50%"
     */
    private String jysrRate;

    /**
     * 检查费用 (JC_FY)
     */
    private BigDecimal jcFy;

    /**
     * 检查收入占比 (JCSRRATE)，格式：百分比字符串，如 "30.00%"
     */
    private String jcsrRate;

    /**
     * 药品费用 (DRUG_FY)
     */
    private BigDecimal drugFy;

    /**
     * 药品收入占比 (DRUGRATE)，格式：百分比字符串，如 "45.50%"
     */
    private String drugRate;
    private Long cfs; // 处方数
    private String zyllzb;// 中医理疗占比
}
