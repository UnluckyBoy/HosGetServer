package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Class WorkStatusToTal
 * @Author Create By Matrix·张
 * @Date 2026/2/2 下午1:07
 * 工单处理统计实体
 */
@Data
public class WorkTotalBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String totalNum;
    private String completedNum;
    private String notCompletedNum;
    private String workStatus;
    private String workType;
    private String createDate;
    private String timeInterval;
}
