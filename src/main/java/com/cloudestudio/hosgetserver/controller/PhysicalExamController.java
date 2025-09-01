package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.paramBody.PatientVisitBody;
import com.cloudestudio.hosgetserver.model.paramBody.QueryBodyPatientInfo;
import com.cloudestudio.hosgetserver.service.PhysicalExam.PatientInfoService;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

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
    PatientInfoService patientInfoService;

    private static final Gson gson=new Gson();//Json数据对象
    private static final Gson gsonConfig=new GsonBuilder().serializeNulls().create();//Json数据对象,强制将NULL返回


    @RequestMapping("/getPatientInfo")
    public void getPatientInfo(HttpServletResponse response, @RequestBody QueryBodyPatientInfo requestBody) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(TimeUtil.GetTime(true)+"---"+requestBody.toString());
        response.getWriter().write(gsonConfig.toJson(WebResponse.success(patientInfoService.getPatientInfo(requestBody))));
    }

    @RequestMapping("/getPatientVisitRecord")
    public void getPatientVisitRecord(HttpServletResponse response, @RequestBody PatientVisitBody requestBody) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(TimeUtil.GetTime(true)+"---"+requestBody.toString());
        response.getWriter().write(gsonConfig.toJson(WebResponse.success(patientInfoService.getPatientVisitRecord(requestBody))));
    }
}
