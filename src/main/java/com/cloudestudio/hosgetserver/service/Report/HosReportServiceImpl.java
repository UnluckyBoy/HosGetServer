package com.cloudestudio.hosgetserver.service.Report;

import com.cloudestudio.hosgetserver.model.ReportBean.DayOutPatientBean;
import com.cloudestudio.hosgetserver.model.ReportBean.OutSettlementReport;
import com.cloudestudio.hosgetserver.service.HosDataService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class OutSettlementReportServiceImpl
 * @Author Create By Matrix·张
 * @Date 2025/10/12 下午3:30
 */
@Service("OutSettlementReportService")
public class HosReportServiceImpl implements HosReportService {
    @Autowired
    HosDataService hosDataService;

    @Override
    public WebResponse queryOutSettlementReport(Map<String, Object> map) {
        if(map.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---门诊结算分析报表--->>>参数:"+ null);
            return WebResponse.paramError();
        }
        System.out.println(TimeUtil.GetTime(true)+" ---门诊结算分析报表--->>>参数:"+ map);
        List<OutSettlementReport> resultList = hosDataService.queryOutSettlementReport(map);
        if(resultList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---门诊结算分析报表---查询异常--->>>参数:"+ map);
            return WebResponse.failure();
        }
        System.out.println(TimeUtil.GetTime(true)+" ---门诊结算分析报表---查询成功--->>>参数:"+ map);
        return WebResponse.success(resultList);
    }

    @Override
    public WebResponse queryDayOutPatient() {
        List<DayOutPatientBean> resultList=hosDataService.queryDayOutPatient();
        if(resultList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---日门诊量---查询为0");
            return WebResponse.queryZeroResult(null);
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---日门诊量---查询成功--->>>参数:"+ resultList);
            return WebResponse.success(resultList);
        }
    }
}
