package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Class WorkInfoBean
 * @Author Create By Matrix·张
 * @Date 2026/1/28 下午12:39
 * 工单实体类
 */
@Data
public class WorkInfoBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String workID;
    private String workTitle;
    private String priority;
    private String workType;
    private String department;
    private String initiator;
    private String creator;
    private String finishOperator;
    private String workStatus;
    private String createDate;
    private String finishDate;
    private String workContent;
    private String workResult;
    private String workAssignee;
}
