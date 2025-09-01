package com.cloudestudio.hosgetserver.service.PhysicalExam;

import com.cloudestudio.hosgetserver.model.paramBody.PatientVisitBody;
import com.cloudestudio.hosgetserver.model.paramBody.QueryBodyPatientInfo;
import com.cloudestudio.hosgetserver.webTools.WebResponse;

/**
 * @Class PatientInfoService
 * @Author Create By Matrix·张
 * @Date 2025/2/16 下午2:11
 * 获取体检用户信息接口
 */
public interface PatientInfoService {
    WebResponse getPatientInfo(QueryBodyPatientInfo requestBody);
    WebResponse getPatientVisitRecord(PatientVisitBody requestBody);
}
