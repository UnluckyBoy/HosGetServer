package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.physicalExamination.PatientInfoBean;

import java.util.List;

/**
 * @Class PhysicalExamService
 * @Author Create By Matrix·张
 * @Date 2025/2/15 下午5:11
 * 体检数据服务接口
 */
public interface PhysicalExamService {
    List<PatientInfoBean> queryPatientInfoByDate(String startDate, String endDate);
    List<PatientInfoBean> queryPatientInfoByMedCard(String medCardNo);
    List<PatientInfoBean> queryPatientInfoByIdCard(String idCard);
}
