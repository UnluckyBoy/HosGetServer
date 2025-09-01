package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.greenChannel.CreateHisBean;
import com.cloudestudio.hosgetserver.model.greenChannel.GreenChannelCaseHis;
import com.cloudestudio.hosgetserver.model.greenChannel.PatientBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class GreenChannelMapper
 * @Author Create By Matrix·张
 * @Date 2025/7/4 下午1:26
 * 绿色通道数据集封装逻辑
 */
@Service
@Mapper
@Repository
public interface GreenChannelMapper {
    PatientBean queryPatient(String idCard);
    List<GreenChannelCaseHis> queryAllHis();//查询所有分诊病案
    List<GreenChannelCaseHis> queryHisParam(String startTime,String endTime);//条件查询分诊病案
    boolean cGCPatientInfo(PatientBean patientBean);//患者信息创建
    boolean cGCPatientInfoTN(PatientBean patientBean);//三无患者信息创建
    String queryHisNum();//查询分诊序号
    int queryRepeatHis(String patientId);//分诊创建重复查询
    boolean cGCCaseHis(CreateHisBean greenChannelCaseHis);//创建分诊信息
}
