package com.cloudestudio.hosgetserver.service.HealthTool.Server;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.HealthTool.PatientBasicInfo;
import com.cloudestudio.hosgetserver.model.mapper.HealthToolMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class HealthToolServiceImpl
 * @Author Create By Matrix·张
 * @Date 2026/4/9 下午12:33
 * 全民信息健康平台实现
 */
@Service("HealthToolService")
public class HealthToolServiceImpl implements HealthToolService {
    @Autowired
    HealthToolMapper healthToolMapper;

    @DS("oracle")
    @Override
    public List<PatientBasicInfo> exportBasePatientInfo() {
        return healthToolMapper.exportBasePatientInfo();
    }

    @DS("oracle3")
    @Override
    public boolean upBasePatientInfo(PatientBasicInfo patientBasicInfo) {
        return healthToolMapper.upBasePatientInfo(patientBasicInfo);
    }
    @DS("oracle3")
    @Override
    public boolean batchUpBasePatientInfo(List<PatientBasicInfo> patientBasicInfoList) {
        if (patientBasicInfoList == null || patientBasicInfoList.isEmpty()) {
            return false;
        }
        //return healthToolMapper.batchUpBasePatientInfo(patientBasicInfoList);
        return healthToolMapper.batchUpOrInsertInfo(patientBasicInfoList);
    }
}
