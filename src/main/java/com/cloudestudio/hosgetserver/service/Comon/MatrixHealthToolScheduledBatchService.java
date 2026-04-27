package com.cloudestudio.hosgetserver.service.Comon;

import com.cloudestudio.hosgetserver.model.Common.MatrixTaskStatus;
import com.cloudestudio.hosgetserver.service.HealthTool.Handle.HealthToolHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Class ScheduledBatchService
 * @Author Create By Matrix·张
 * @Date 2026/4/15 下午3:05
 * 任务服务
 */
@Service
public class MatrixHealthToolScheduledBatchService {
    @Autowired
    private HealthToolHandleService healthToolHandleService;

    private final Object lock = new Object();
    private final MatrixTaskStatus currentStatus = new MatrixTaskStatus(false,"IDLE", null, null, "等待首次执行", null);

    /**
     * 每30分钟执行一次（cron：分钟每隔30分钟）
     * 注意：若任务执行耗时超过30分钟，下一次调度会等待当前执行完成（默认单线程池）
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void executeBatchPush() {
        synchronized (lock) {
            // 更新状态为运行中
            currentStatus.setMSuccess(true);
            currentStatus.setStatus("RUNNING");
            currentStatus.setLastRunStartTime(LocalDateTime.now().toString());
            currentStatus.setMessage("正在执行 batchUpBasePatientInfo...");

            try {
                // 调用目标方法
                Object result = healthToolHandleService.batchUpBasePatientInfo();

                // 执行成功
                currentStatus.setMSuccess(true);
                currentStatus.setStatus("SUCCESS");
                currentStatus.setLastRunEndTime(LocalDateTime.now().toString());
                currentStatus.setMessage("执行成功");
                // 截取结果摘要（若返回结果太长，只取前200字符）
                String resultStr = (result == null) ? "null" : result.toString();
                if (resultStr.length() > 200) {
                    resultStr = resultStr.substring(0, 200) + "...";
                }
                currentStatus.setResultSummary(resultStr);

            } catch (Exception e) {
                // 执行失败
                currentStatus.setMSuccess(false);
                currentStatus.setStatus("FAILED");
                currentStatus.setLastRunEndTime(LocalDateTime.now().toString());
                currentStatus.setMessage("执行失败：" + e.getMessage());
                currentStatus.setResultSummary(null);
                // 可在此记录错误日志
                e.printStackTrace();
            }
        }
    }

    /**
     * 供外部获取当前任务状态(返回副本，避免直接修改)
     */
    public MatrixTaskStatus getCurrentStatus() {
        synchronized (lock) {
            // 深拷贝简单对象
            return new MatrixTaskStatus(
                    currentStatus.isMSuccess(),
                    currentStatus.getStatus(),
                    currentStatus.getLastRunStartTime(),
                    currentStatus.getLastRunEndTime(),
                    currentStatus.getMessage(),
                    currentStatus.getResultSummary()
            );
        }
    }
}
