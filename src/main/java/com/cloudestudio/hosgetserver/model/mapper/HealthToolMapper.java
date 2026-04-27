package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.HealthTool.PatientBasicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class HealthTool
 * @Author Create By Matrix·张
 * @Date 2026/4/9 下午12:14
 * 全民健康信息平台逻辑
 */
@Service
@Mapper
@Repository
public interface HealthToolMapper {
    List<PatientBasicInfo> exportBasePatientInfo();

    boolean upBasePatientInfo(PatientBasicInfo patientBasicInfo);
    boolean batchUpBasePatientInfo(@Param("list") List<PatientBasicInfo> list);
    boolean batchUpOrInsertInfo(@Param("list") List<PatientBasicInfo> list);
}
