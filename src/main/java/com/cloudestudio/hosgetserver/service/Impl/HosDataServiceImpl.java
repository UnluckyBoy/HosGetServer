package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.*;
import com.cloudestudio.hosgetserver.model.mapper.HosDataMapper;
import com.cloudestudio.hosgetserver.model.mapper.UserMapper;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.service.HosDataService;
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
    public List<HosDataBean> queryInHospital_consultation(String startTime,String endTime) {
        return hosDataMapper.queryInHospital_consultation(startTime,endTime);
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
    public PatientBaseInfoBean queryPatientBaseInfo(String regisNumber) {
        return hosDataMapper.queryPatientBaseInfo(regisNumber);
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
    public boolean releaseYfClock(String requestNum) {
        return hosDataMapper.releaseYfClock(requestNum);
    }

    @DS("oracle")
    @Override
    public List<BedDayBean> QueryBedDay(BedDayBody queryMap) {
        return hosDataMapper.QueryBedDay(queryMap);
    }

    @DS("oracle2")
    @Override
    public String queryAddrCode(String addr) {
        return hosDataMapper.queryAddrCode(addr);
    }
}
