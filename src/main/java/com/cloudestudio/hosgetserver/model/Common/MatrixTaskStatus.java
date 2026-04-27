package com.cloudestudio.hosgetserver.model.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Class MatrixTaskStatus
 * @Author Create By Matrix·张
 * @Date 2026/4/15 下午3:04
 * 任务实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrixTaskStatus implements Serializable {
    private boolean mSuccess;
    private String status;          // RUNNING, SUCCESS, FAILED, IDLE
    private String lastRunStartTime;
    private String lastRunEndTime;
    private String message;
    private String resultSummary;   // 执行结果摘要（可选）
}
