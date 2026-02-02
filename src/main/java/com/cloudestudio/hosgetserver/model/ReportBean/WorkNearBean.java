package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class WorkNearBean
 * @Author Create By Matrix·张
 * @Date 2026/1/29 下午2:48
 * 近期工单
 */
@Data
public class WorkNearBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String workID;
    private String workTitle;
    private String priority;
    private String workType;
    private String department;
    private String initiator;
    private String creator;
    private String creatorHead;
    private String finishOperator;
    private String finishOperatorHead;
    private String workStatus;
    private String createDate;
    private String finishDate;
    private String workContent;
    private String workResult;
}
