package com.cloudestudio.hosgetserver.service.Report;

import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.webTools.WebResponse;

import java.util.Map;

/**
 * @Class OutSettlementReportService
 * @Author Create By Matrix·张
 * @Date 2025/10/12 下午3:27
 * 门诊结算分析报表
 */
public interface HosReportService {
    WebResponse queryOutSettlementReport(Map<String,Object> map);
    WebResponse queryDayOutPatient();
    WebResponse QueryWorksNum(BedDayBody queryMap);
}