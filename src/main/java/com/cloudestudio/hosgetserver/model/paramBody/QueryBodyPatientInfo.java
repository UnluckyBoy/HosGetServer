package com.cloudestudio.hosgetserver.model.paramBody;

import lombok.Data;

/**
 * @Class QueryBodyPatientInfo
 * @Author Create By Matrix·张
 * @Date 2025/2/15 下午5:19
 * 查询患者信息参数实体
 */
@Data
public class QueryBodyPatientInfo {
    private String startDate;
    private String endDate;
    private String idCard;
    private String medCardNo;
}
