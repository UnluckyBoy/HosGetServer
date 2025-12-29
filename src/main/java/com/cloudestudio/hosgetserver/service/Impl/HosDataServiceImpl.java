package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.*;
import com.cloudestudio.hosgetserver.model.ReportBean.DayOutPatientBean;
import com.cloudestudio.hosgetserver.model.ReportBean.OutSettlementReport;
import com.cloudestudio.hosgetserver.model.ReportBean.SettlementBean;
import com.cloudestudio.hosgetserver.model.ReportBean.WorkNums;
import com.cloudestudio.hosgetserver.model.mapper.HosDataMapper;
import com.cloudestudio.hosgetserver.model.mapper.UserMapper;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.service.HosDataService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Matrix·张
 * @Date 2024/11/13 下午1:56
 */
@Service("HosDataService")
public class HosDataServiceImpl implements HosDataService {
    @Autowired
    HosDataMapper hosDataMapper;

    @DS("oracle")
    @Override
    public List<HosDataBean> queryInHospital_consultation(Map<String,String> map) {
        return hosDataMapper.queryInHospital_consultation(map);
    }

    @DS("oracle")
    @Override
    public List<PatientBaseInfoBean> queryPatientBaseInfoList() {
        return hosDataMapper.queryPatientBaseInfoList();
    }

    @DS("oracle")
    @Override
    public List<PatientActivityBean> queryEmrActivityInfo() {
        return hosDataMapper.queryEmrActivityInfo();
    }

    @DS("oracle")
    @Override
    public List<PatientInfReport> queryEmrInfReportBySerialNumber(String serial_number) {
        return hosDataMapper.queryEmrInfReportBySerialNumber(serial_number);
    }

    @DS("oracle")
    @Override
    public List<PatientInfReport> getReportBody(String serial_number) {
        return hosDataMapper.getReportBody(serial_number);
    }

    @DS("oracle")
    @Override
    public PatientBaseInfoBean queryPatientBaseInfo(String regisNumber) {
        return hosDataMapper.queryPatientBaseInfo(regisNumber);
    }

    @DS("oracle")
    @Override
    public PatientBaseInfoBean QueryBaseInfoByID(String PatientID) {
        return hosDataMapper.QueryBaseInfoByID(PatientID);
    }

    @DS("oracle")
    @Override
    public List<PatientActivityBean> queryPatientActivityInfo(String regisNumber) {
        return hosDataMapper.queryPatientActivityInfo(regisNumber);
    }

    @DS("oracle")
    @Override
    public ReportQueryPatientBaseInfo createReportQueryBaseInfo(String serial_number) {
        return hosDataMapper.createReportQueryBaseInfo(serial_number);
    }

    @DS("oracle")
    @Override
    public int queryInfectiousDiseaseCount() {
        return hosDataMapper.queryInfectiousDiseaseCount();
    }

    @DS("oracle")
    @Override
    public List<AddrInfo> queryAddrInfo(String addr) {
        return hosDataMapper.queryAddrInfo(addr);
    }

    @DS("oracle")
    @Override
    public boolean createCReportCard(Map<String, Object> map) {
        return hosDataMapper.createCReportCard(map);
    }

    @DS("oracle")
    @Override
    public WebResponse releaseYfClock() {
        boolean result=hosDataMapper.releaseYfClock();
        if(result){
            System.out.println(TimeUtil.GetTime(true)+" ---解锁药房锁-失败！");
            return WebResponse.failure();
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---解锁药房锁-成功!");
            return WebResponse.success();
        }
    }

    @DS("oracle")
    @Override
    public WebResponse freshCostEndTime(Map<String, Object> map) {
        boolean result=hosDataMapper.freshCostEndTime(map);
        if(result){
            System.out.println(TimeUtil.GetTime(true)+" ---更新时间-成功!--->>>参数:"+map.toString());
            return WebResponse.success();
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---更新时间-失败！--->>>参数:"+ map.toString());
            return WebResponse.paramError();
        }
    }

    @DS("oracle")
    @Override
    public ReportCardBody queryReportCard(String serialNumber) {
        return hosDataMapper.queryReportCard(serialNumber);
    }

    @DS("oracle")
    @Override
    public PathologyPatientInfoBean queryPathology(String queryKey) {
        return hosDataMapper.queryPathology(queryKey);
    }

    @DS("oracle")
    @Override
    public List<BedDayBean> QueryBedDay(BedDayBody queryMap) {
        return hosDataMapper.QueryBedDay(queryMap);
    }

    @DS("oracle")
    @Override
    public List<OutSettlementReport> queryOutSettlementReport(Map<String,Object> map) {
        return hosDataMapper.queryOutSettlementReport(map);
    }

    @DS("oracle")
    @Override
    public List<DayOutPatientBean> queryDayOutPatient() {
        return hosDataMapper.queryDayOutPatient();
    }

    @DS("oracle")
    @Override
    public List<WorkNums> QueryWorksNum(BedDayBody queryMap) {
        return hosDataMapper.QueryWorksNum(queryMap);
    }

    @DS("oracle")
    @Override
    public List<SettlementBean> QuerySettlement(BedDayBody queryMap) {
        return hosDataMapper.QuerySettlement(queryMap);
    }

    @DS("oracle2")
    @Override
    public String queryAddrCode(String addr) {
        return hosDataMapper.queryAddrCode(addr);
    }
}
