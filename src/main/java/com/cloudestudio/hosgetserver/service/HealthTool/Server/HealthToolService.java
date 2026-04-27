package com.cloudestudio.hosgetserver.service.HealthTool.Server;

import com.cloudestudio.hosgetserver.model.HealthTool.PatientBasicInfo;

import java.util.List;

/**
 * @Class HealthToolService
 * @Author Create By Matrix·张
 * @Date 2026/4/9 下午12:32
 * 全民健康信息平台接口
 */
public interface HealthToolService {
    List<PatientBasicInfo> exportBasePatientInfo();//生成、抓取数据

    boolean upBasePatientInfo(PatientBasicInfo patientBasicInfo);
    boolean batchUpBasePatientInfo(List<PatientBasicInfo> patientBasicInfoList);
}
