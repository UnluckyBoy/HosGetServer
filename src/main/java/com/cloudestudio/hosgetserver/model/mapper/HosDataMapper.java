package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.*;
import com.cloudestudio.hosgetserver.model.ReportBean.OutSettlementReport;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class HosDataMapper
 * @Author Create By Matrix·张
 * @Date 2024/11/13 下午2:04
 * Mybaties接口
 */
@Service
@Mapper
@Repository
public interface HosDataMapper {
    List<HosDataBean> queryInHospital_consultation(Map<String,String> map);//院内会诊查询
    PatientBaseInfoBean queryPatientBaseInfo(String regisNumber);//查询患者基表
    PatientBaseInfoBean QueryBaseInfoByID(String PatientID);
    List<PatientActivityBean> queryPatientActivityInfo(String regisNumber);//查询患者门诊信息
    List<PatientBaseInfoBean> queryPatientBaseInfoList();//当日患者信息查询
    List<PatientActivityBean> queryEmrActivityInfo();//当日患者门诊诊断信息查询
    List<PatientInfReport> queryEmrInfReportBySerialNumber(String serial_number);//查询传染病报告卡
    List<PatientInfReport> getReportBody(String serial_number);
    String queryAddrCode(String addr);//查询住址区域编码
    ReportQueryPatientBaseInfo createReportQueryBaseInfo(String serial_number);
    int queryInfectiousDiseaseCount();//查询当前报告卡数量
    List<AddrInfo> queryAddrInfo(String addr);//查询区域信息
    ReportCardBody queryReportCard(String serialNumber);//查询传染病报告卡
    PathologyPatientInfoBean queryPathology(String queryKey);//病理申请患者信息查询
    List<BedDayBean> QueryBedDay(BedDayBody queryMap);//床日数
    List<OutSettlementReport> queryOutSettlementReport(Map<String,Object> map);//门诊结算分析报表

    boolean createCReportCard(Map<String,Object> map);//报告卡填写写入
    boolean releaseYfClock();//解除药房锁
    boolean freshCostEndTime(Map<String,Object> map);//刷新收费日期
}
