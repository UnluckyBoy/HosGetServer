package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.mapper.PhysicalExamMapper;
import com.cloudestudio.hosgetserver.model.physicalExamination.PatientInfoBean;
import com.cloudestudio.hosgetserver.model.physicalExamination.VisitRecordBean;
import com.cloudestudio.hosgetserver.service.PhysicalExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class PhysicalExamImpl
 * @Author Create By Matrix·张
 * @Date 2025/2/15 下午5:12
 * 体检服务接口实现
 */
@Service("PhysicalExamService")
public class PhysicalExamImpl implements PhysicalExamService {
    @Autowired
    PhysicalExamMapper physicalExamMapper;

    @DS("oracle")
    @Override
    public List<PatientInfoBean> queryPatientInfoByDate(String startDate, String endDate) {
        return physicalExamMapper.queryPatientInfoByDate(startDate,endDate);
    }

    @DS("oracle")
    @Override
    public List<PatientInfoBean> queryPatientInfoByMedCard(String medCardNo) {
        return physicalExamMapper.queryPatientInfoByMedCard(medCardNo);
    }

    @DS("oracle")
    @Override
    public List<PatientInfoBean> queryPatientInfoByIdCard(String idCard) {
        return physicalExamMapper.queryPatientInfoByIdCard(idCard);
    }

    @DS("oracle")
    @Override
    public List<VisitRecordBean> queryPatientVisitRecord(String patientId, String startDate, String endDate) {
        return physicalExamMapper.queryPatientVisitRecord(patientId,startDate,endDate);
    }
}
