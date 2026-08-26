package com.cloudestudio.hosgetserver.service.Report;

import com.cloudestudio.hosgetserver.model.ReportBean.*;
import com.cloudestudio.hosgetserver.model.department.BaseDepartMent;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.service.HosDataService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 结算分析报表
     * @param map
     * @return
     */
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

    /***
     * 门诊量
     * @return
     */
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

    /***
     * 门诊工作量
     * @param queryMap
     * @return
     */
    @Override
    public WebResponse QueryWorksNum(BedDayBody queryMap) {
        System.out.println(TimeUtil.GetTime(true)+" ---门诊工作量--->>>参数:"+ queryMap);
        List<WorkNums> resultList=hosDataService.QueryWorksNum(queryMap);
        if(resultList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---门诊工作量---查询为0");
            return WebResponse.queryZeroResult(null);
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---门诊工作量---查询成功--->>>参数:"+ queryMap+"--->结果:"+resultList);
            return WebResponse.success(resultList);
        }
    }

    /**
     * 门诊结算分析(新)
     * @param queryMap
     * @return
     */
    @Override
    public WebResponse QuerySettlement(BedDayBody queryMap) {
        System.out.println(TimeUtil.GetTime(true)+" ---门诊结算分析--->>>参数:"+ queryMap);
        List<SettlementBean> resultList=hosDataService.QuerySettlement(queryMap);
        if(resultList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---门诊工作量---查询为0");
            return WebResponse.queryZeroResult(null);
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---门诊工作量---查询成功--->>>参数:"+ queryMap+"--->结果:"+resultList);
            return WebResponse.success(resultList);
        }
    }

    /***
     * 小程序退费更新JRZY_INFO
     * @param orderId
     * @return
     */
    @Override
    public WebResponse freshJrzyInfo(String orderId) {
        System.out.println(TimeUtil.GetTime(true)+" ---退费id--->>>参数:"+ orderId);
        boolean result=hosDataService.freshJrzyInfo(orderId);
        if(!result){
            System.out.println(TimeUtil.GetTime(true)+" ---退费id---异常");
            return WebResponse.queryZeroResult(null);
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---退费id---成功--->>>参数:"+ orderId);
            return WebResponse.success();
        }
    }

    /***
     * 查询科室
     * @return
     */
    @Override
    public WebResponse queryBaseDepart() {
        List<BaseDepartMent> result=hosDataService.queryBaseDepart();
        if(result.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---科室查询异常");
            return WebResponse.queryZeroResult(null);
        }
        System.out.println(TimeUtil.GetTime(true)+" ---科室查询成功");
        return WebResponse.success(result);
    }

    @Override
    public WebResponse queryInfoByCf(String cfNumber) {
        List<PrescriptionReviewBean> resultList=hosDataService.queryInfoByCf(cfNumber);
        if(resultList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---查询异常");
            return WebResponse.failure();
        }
        System.out.println(TimeUtil.GetTime(true)+" ---查询成功->>>"+resultList);
        return WebResponse.success(resultList);
    }

    /**
     * 追溯码查漏
     * @return
     */
    @Override
    public WebResponse medicineCodeQuery() {
        List<MedicineQueryBean> resultList=hosDataService.medicineCodeQuery();
        System.out.println(TimeUtil.GetTime(true)+" ---查询返回:"+ resultList);
        return WebResponse.success(resultList);
    }

    /**
     * 财务运营分析-月报
     * @param body
     * @return
     */
    @Override
    public WebResponse queryMonOperation(BedDayBody body) {
        if(body==null){
            System.out.println(TimeUtil.GetTime(true)+" ---queryMonOperation查询参数异常");
            return WebResponse.paramError();
        }
        List<OperationEntity> queryResultList=hosDataService.queryMonOperation(body);
        if(queryResultList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---queryMonOperation查询异常");
            return WebResponse.failure();
        }
//        MonOperationResult monResult=new MonOperationResult();
//        monResult.setDepartName("All");
//        monResult.setOperationEntity(queryResultList);
//        List<MonOperationResult> resultList = new ArrayList<>();
//        resultList.add(monResult);
        System.out.println(TimeUtil.GetTime(true)+" ---queryMonOperation查询成功");
        return WebResponse.success(queryResultList);
    }


}
