package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.HosDataBean;
import com.cloudestudio.hosgetserver.model.PatientActivityBean;
import com.cloudestudio.hosgetserver.model.PatientBaseInfoBean;
import com.cloudestudio.hosgetserver.model.UserInfoBean;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 获取数据服务类
 *
 * @author Matrix·张
 * @Date 2024/11/13 下午1:56
 */
public interface HosDataService {
    List<HosDataBean> queryInHospital_consultation(String startTime,String endTime);
    List<PatientBaseInfoBean> queryPatientBaseInfoList();
    List<PatientActivityBean> queryEmrActivityInfo();

    PatientBaseInfoBean queryPatientBaseInfo(String regisNumber);
    List<PatientActivityBean> queryPatientActivityInfo(String regisNumber);
}
