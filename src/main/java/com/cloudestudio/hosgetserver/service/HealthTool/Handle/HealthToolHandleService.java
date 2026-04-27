package com.cloudestudio.hosgetserver.service.HealthTool.Handle;

import com.cloudestudio.hosgetserver.webTools.WebResponse;
import com.cloudestudio.hosgetserver.webTools.WebServerResponse;

/**
 * @Class HealthToolHandleService
 * @Author Create By Matrix·张
 * @Date 2026/4/9 下午12:39
 * 全民信息健康平台逻辑服务
 */
public interface HealthToolHandleService {
    WebResponse exportBasePatientInfo();

    WebResponse upBasePatientInfo();
    WebResponse batchUpBasePatientInfo();
}
