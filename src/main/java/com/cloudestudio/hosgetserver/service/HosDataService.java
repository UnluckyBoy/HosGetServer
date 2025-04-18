package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.*;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;

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
    List<PatientInfReport> queryEmrInfReportBySerialNumber(String serial_number);

    PatientBaseInfoBean queryPatientBaseInfo(String regisNumber);
    List<PatientActivityBean> queryPatientActivityInfo(String regisNumber);
    ReportQueryPatientBaseInfo createReportQueryBaseInfo(String serial_number);
    int queryInfectiousDiseaseCount();
    List<AddrInfo> queryAddrInfo(String addr);
    boolean createCReportCard(Map<String,Object> map);

    ReportCardBody queryReportCard(String serialNumber);
    PathologyPatientInfoBean queryPathology(String queryKey);

    boolean releaseYfClock(String requestNum);
    List<BedDayBean> QueryBedDay(BedDayBody queryMap);

    /** Oracle2库**/
    String queryAddrCode(String addr);
}
