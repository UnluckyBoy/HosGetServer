package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.physicalExamination.PatientInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class PhysicalExamMapper
 * @Author Create By Matrix·张
 * @Date 2025/2/15 下午3:02
 * 体检mapper接口
 */
@Service
@Mapper
@Repository
public interface PhysicalExamMapper {
    List<PatientInfoBean> queryPatientInfoByDate(String startDate, String endDate);//按注册日期查询
    List<PatientInfoBean> queryPatientInfoByMedCard(String medCardNo);//按就诊卡号查询
    List<PatientInfoBean> queryPatientInfoByIdCard(String idCard);//按身份证查询
}
