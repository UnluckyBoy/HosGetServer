package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.HosDataBean;
import com.cloudestudio.hosgetserver.model.paramBody.QueryBodyPatientInfo;
import com.cloudestudio.hosgetserver.model.physicalExamination.PatientInfoBean;
import com.cloudestudio.hosgetserver.service.PhysicalExamService;
import com.cloudestudio.hosgetserver.webTools.PhysicalExamResponse;
import com.cloudestudio.hosgetserver.webTools.StringUtil;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebServerResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * @Class PhysicalExamController
 * @Author Create By Matrix·张
 * @Date 2025/2/15 下午5:15
 * 体检控制类
 */
@Controller
@RequestMapping("/PhysicalExamApi")
public class PhysicalExamController {
    @Autowired
    PhysicalExamService physicalExamService;

    private static final Gson gson=new Gson();//Json数据对象
    private static final Gson gsonConfig=new GsonBuilder().serializeNulls().create();//Json数据对象,强制将NULL返回

    @RequestMapping("/getPatientInfo")
    public void getPatientInfo(HttpServletResponse response, @RequestBody QueryBodyPatientInfo requestBody) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(TimeUtil.GetTime(true)+"---"+requestBody.toString());
        List<PatientInfoBean> queryBean=null;
        if(StringUtil.isNotEmptyOrNotNull(requestBody.getStartDate())&&StringUtil.isNotEmptyOrNotNull(requestBody.getEndDate())
                &&StringUtil.isEmptyOrNull(requestBody.getMedCardNo())&&StringUtil.isEmptyOrNull(requestBody.getIdCard())){
            queryBean=physicalExamService.queryPatientInfoByDate(requestBody.getStartDate(),requestBody.getEndDate());
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
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("请求失败")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询成功:"+queryBean);
            response.getWriter().write(gsonConfig.toJson(PhysicalExamResponse.success(queryBean)));
        }
    }
}
