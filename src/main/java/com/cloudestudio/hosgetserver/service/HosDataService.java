package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.*;
import com.cloudestudio.hosgetserver.model.ReportBean.OutSettlementReport;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.webTools.WebResponse;

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
    List<HosDataBean> queryInHospital_consultation(Map<String,String> map);
    List<PatientBaseInfoBean> queryPatientBaseInfoList();
    List<PatientActivityBean> queryEmrActivityInfo();
    List<PatientInfReport> queryEmrInfReportBySerialNumber(String serial_number);
    List<PatientInfReport> getReportBody(String serial_number);
    PatientBaseInfoBean queryPatientBaseInfo(String regisNumber);
    PatientBaseInfoBean QueryBaseInfoByID(String PatientID);
    List<PatientActivityBean> queryPatientActivityInfo(String regisNumber);
    ReportQueryPatientBaseInfo createReportQueryBaseInfo(String serial_number);
    int queryInfectiousDiseaseCount();
    List<AddrInfo> queryAddrInfo(String addr);
    ReportCardBody queryReportCard(String serialNumber);
    PathologyPatientInfoBean queryPathology(String queryKey);
    List<BedDayBean> QueryBedDay(BedDayBody queryMap);
    List<OutSettlementReport> queryOutSettlementReport(Map<String,Object> map);

    /** Oracle2库**/
    String queryAddrCode(String addr);

    //boolean releaseYfClock(String requestNum);
    boolean createCReportCard(Map<String,Object> map);
    //boolean freshCostEndTime(Map<String,Object> map);
    WebResponse releaseYfClock();
    WebResponse freshCostEndTime(Map<String,Object> map);
}
