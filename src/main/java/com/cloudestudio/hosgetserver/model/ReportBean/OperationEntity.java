package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Class OperationEntity
 * @Author Create By Matrix·张
 * @Date 2026/8/19 下午4:10
 * 月报分析实体
 */
@Data
public class OperationEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String department;

    // 总费用
    private BigDecimal currentAllAmount;
    private BigDecimal previousAllAmount;
    private BigDecimal lastYearAllAmount;
    private String allQuarterRate;
    private String allYoYRate;

    // 医务性收入占比
    private String currentYwxSrPersent;
    private String previousYwxSrPersent;
    private String lastYearYwxSrPersent;
    private String ywxSrQuarterRate;
    private String ywxSrYoYRate;

    // 中医理疗占比
    private String currentZyllPersent;
    private String previousZyllPersent;
    private String lastYearZyllPersent;
    private String zyllQuarterRate;
    private String zyllYoYRate;

    // 检查检验占比
    private String currentJcjyPersent;
    private String previousJcjyPersent;
    private String lastYearJcjyPersent;
    private String jcjyQuarterRate;
    private String jcjyYoYRate;

    // 门诊人次
    private BigDecimal currentMzRcs;
    private BigDecimal previousMzRcs;
    private BigDecimal lastYearMzRcs;
    private String mzRcsQuarterRate;
    private String mzRcsYoYRate;

    // 门诊收入
    private BigDecimal currentMzAmount;
    private BigDecimal previousMzAmount;
    private BigDecimal lastYearMzAmount;
    private String mzSrQuarterRate;
    private String mzSrYoYRate;

    // 门诊次均
    private BigDecimal currentMzCj;
    private BigDecimal previousMzCj;
    private BigDecimal lastYearMzCj;
    private String mzCjQuarterRate;
    private String mzCjYoYRate;

    // 出院人次
    private BigDecimal currentCyRcs;
    private BigDecimal previousCyRcs;
    private BigDecimal lastYearCyRcs;
    private String cyRcsQuarterRate;
    private String cyRcsYoYRate;

    // 住院收入
    private BigDecimal currentZyAmount;
    private BigDecimal previousZyAmount;
    private BigDecimal lastYearZyAmount;
    private String zySrQuarterRate;
    private String zySrYoYRate;

    // 住院次均
    private BigDecimal currentZyCj;
    private BigDecimal previousZyCj;
    private BigDecimal lastYearZyCj;
    private String zyCjQuarterRate;
    private String zyCjYoYRate;

    // 床位使用率
    private String currentBedRate;
    private String previousBedRate;
    private String lastYearBedRate;
    private String bedQuarterRate;
    private String bedYoYRate;

    // 手术申请
    private BigDecimal currentSs;
    private BigDecimal previousSs;
    private BigDecimal lastYearSs;
    private String ssQuarterRate;
    private String ssYoYRate;
}
