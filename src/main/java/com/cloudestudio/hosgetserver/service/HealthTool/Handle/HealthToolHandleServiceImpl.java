package com.cloudestudio.hosgetserver.service.HealthTool.Handle;

import com.cloudestudio.hosgetserver.model.HealthTool.PatientBasicInfo;
import com.cloudestudio.hosgetserver.service.HealthTool.Server.HealthToolService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import com.cloudestudio.hosgetserver.webTools.WebServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class HealthToolHandleServiceImpl
 * @Author Create By Matrix·张
 * @Date 2026/4/9 下午12:45
 * 实现类
 */
@Service("HealthToolHandleService")
public class HealthToolHandleServiceImpl implements HealthToolHandleService {
    @Autowired
    HealthToolService healthToolService;

    /**
     * 抓取、生成患者基本信息
     * @return
     */
    @Override
    public WebResponse exportBasePatientInfo() {
        List<PatientBasicInfo> patientBasicInfoList=healthToolService.exportBasePatientInfo();
        if (patientBasicInfoList.isEmpty()) {
            return WebResponse.failure();
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询成功:"+patientBasicInfoList);
            return WebResponse.success(patientBasicInfoList);
        }
    }

    @Override
    public WebResponse upBasePatientInfo() {
//        List<PatientBasicInfo> patientBasicInfoList=healthToolService.exportBasePatientInfo();
//        if (patientBasicInfoList.isEmpty()) {
//            System.out.println(TimeUtil.GetTime(true)+" ---生成数据异常:");
//            return WebResponse.serverError("生成数据异常");
//        }else{
//            System.out.println(TimeUtil.GetTime(true)+" ---查询成功:"+patientBasicInfoList);
//            boolean result = healthToolService.upBasePatientInfo(patientBasicInfoList);
//            if (result) {
//                System.out.println(TimeUtil.GetTime(true)+" ---数据推送成功:"+patientBasicInfoList.size()+"条");
//                return WebResponse.success("推送成功：共"+patientBasicInfoList.size()+"条");
//            }
//            System.out.println(TimeUtil.GetTime(true)+" ---数据推送失败:"+patientBasicInfoList.size()+"条");
//            return WebResponse.failure();
//        }
        return WebResponse.failure();
    }

    @Override
    public WebResponse batchUpBasePatientInfo() {
        List<PatientBasicInfo> patientBasicInfoList=healthToolService.exportBasePatientInfo();
        if (patientBasicInfoList.isEmpty()) {
            System.out.println(TimeUtil.GetTime(true)+" ---生成数据异常:");
            return WebResponse.serverError("生成数据异常");
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询成功:共"+patientBasicInfoList.size()+"条数据");
            boolean result = healthToolService.batchUpBasePatientInfo(patientBasicInfoList);
            if (result) {
                System.out.println(TimeUtil.GetTime(true)+" ---数据推送成功:"+patientBasicInfoList.size()+"条数据");
                return WebResponse.success("推送成功：共"+patientBasicInfoList.size()+"条数据");
            }
            System.out.println(TimeUtil.GetTime(true)+" ---数据推送失败:"+patientBasicInfoList.size()+"条数据");
            return WebResponse.failure();
        }
    }
}
