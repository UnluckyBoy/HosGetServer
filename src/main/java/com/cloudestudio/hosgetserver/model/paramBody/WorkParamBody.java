package com.cloudestudio.hosgetserver.model.paramBody;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class WorkParamBody
 * @Author Create By Matrix·张
 * @Date 2026/1/28 上午11:06
 * 创建工单请求类
 */
@Data
public class WorkParamBody implements Serializable {
    private static final long serialVersionUID = 1L;

    private String workID;
    private String workTitle;
    private String workType;
    private String priority;
    private String workStatus;
    private String creator;
    private String initiator;
    private String finishOperator;
    private String department;
    private String description;
    private String createDate;
    private String finishDate;
    private String workResult;
    private String workAssignee;
}
