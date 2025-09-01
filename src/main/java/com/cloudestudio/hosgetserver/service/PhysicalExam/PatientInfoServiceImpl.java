package com.cloudestudio.hosgetserver.service.PhysicalExam;

import com.cloudestudio.hosgetserver.model.paramBody.PatientVisitBody;
import com.cloudestudio.hosgetserver.model.paramBody.QueryBodyPatientInfo;
import com.cloudestudio.hosgetserver.model.physicalExamination.PatientInfoBean;
import com.cloudestudio.hosgetserver.model.physicalExamination.VisitRecordBean;
import com.cloudestudio.hosgetserver.service.PhysicalExamService;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import com.cloudestudio.hosgetserver.webTools.StringUtil;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class PatientInfoServiceImpl
 * @Author Create By Matrix·张
 * @Date 2025/2/16 下午2:13
 * 获取体检用户信息实现
 */
@Service("PatientInfoService")
public class PatientInfoServiceImpl implements PatientInfoService{
    @Autowired
    PhysicalExamService physicalExamService;

    /**
     * 查询患者基本信息
     * @param requestBody
     * @return
     */
    @Override
    public WebResponse getPatientInfo(QueryBodyPatientInfo requestBody) {
        List<PatientInfoBean> queryBean;
        if(StringUtil.isNotEmptyOrNotNull(requestBody.getStartDate())&&StringUtil.isNotEmptyOrNotNull(requestBody.getEndDate())
                &&StringUtil.isEmptyOrNull(requestBody.getMedCardNo())&&StringUtil.isEmptyOrNull(requestBody.getIdCard())){
            queryBean=physicalExamService.queryPatientInfoByDate(requestBody.getStartDate()+"00:00:00",requestBody.getEndDate()+"23:59:59");
        }else if(StringUtil.isEmptyOrNull(requestBody.getStartDate())&&StringUtil.isEmptyOrNull(requestBody.getEndDate())
                &&StringUtil.isNotEmptyOrNotNull(requestBody.getMedCardNo())&&StringUtil.isEmptyOrNull(requestBody.getIdCard())){
            queryBean=physicalExamService.queryPatientInfoByMedCard(requestBody.getMedCardNo());
        }else if(StringUtil.isEmptyOrNull(requestBody.getStartDate())&&StringUtil.isEmptyOrNull(requestBody.getEndDate())
                &&StringUtil.isEmptyOrNull(requestBody.getMedCardNo())&&StringUtil.isNotEmptyOrNotNull(requestBody.getIdCard())){
            queryBean=physicalExamService.queryPatientInfoByIdCard(requestBody.getIdCard());
        }else{
            queryBean=physicalExamService.queryPatientInfoByDate(TimeUtil.GetTime(false)+" 00:00:00",TimeUtil.GetTime(true));
        }
        if (queryBean!=null && queryBean.isEmpty()) {
            System.out.println(TimeUtil.GetTime(true)+" ---查询体检患者基本信息失败!---参数:"+ requestBody);
            return WebResponse.failure();
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询体检患者基本信息成功!---参数:"+ requestBody+"  ---返回值："+queryBean);
            return WebResponse.success(queryBean);
        }
    }

    /**
     * 查询患者就诊记录
     * @param requestBody
     * @return
     */
    @Override
    public WebResponse getPatientVisitRecord(PatientVisitBody requestBody) {
        if(requestBody==null){
            return WebResponse.paramError();
        }
        List<VisitRecordBean> queryBean=physicalExamService.queryPatientVisitRecord(requestBody.getPatientId(),
                requestBody.getStartDate()+"00:00:00", requestBody.getEndDate()+"23:59:59");
        if(queryBean!=null && queryBean.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者就诊记录失败!---参数:"+ requestBody);
            return WebResponse.failure();
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者就诊记录失败!---参数:"+ requestBody+"  ---返回值："+queryBean);
            return WebResponse.success(queryBean);
        }
    }
}
