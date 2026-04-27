package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.Common.MatrixTaskStatus;
import com.cloudestudio.hosgetserver.service.Comon.MatrixHealthToolScheduledBatchService;
import com.cloudestudio.hosgetserver.service.HealthTool.Handle.HealthToolHandleService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @Class HealthToolController
 * @Author Create By Matrix·张
 * @Date 2026/4/9 下午12:48
 * 全民信息健康平台控制类
 */
@Controller
@RequestMapping("/HealthApi")
public class HealthToolController {
    private static final Gson gson=new Gson();//Json数据对象
    private static final Gson gsonConfig=new GsonBuilder().serializeNulls().create();//Json数据对象,强制将NULL返回

    @Autowired
    HealthToolHandleService healthToolHandleService;

    @Autowired
    private MatrixHealthToolScheduledBatchService matrixHealthToolScheduledBatchService;

    @RequestMapping("/exportBasePatientInfo")
    public void getExportBasePatientInfo(HttpServletResponse response) throws IOException {
        System.out.println(TimeUtil.GetTime(true)+"开始抓取全民健康信息平台数据-患者基本信息");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gson.toJson(healthToolHandleService.exportBasePatientInfo()));
    }

    @RequestMapping("/batchPushBasePatientInfo")
    public void getBatchPushBasePatientInfo(HttpServletResponse response) throws IOException {
        System.out.println(TimeUtil.GetTime(true)+"开始抓取全民健康信息平台数据-患者基本信息");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gson.toJson(healthToolHandleService.batchUpBasePatientInfo()));
    }

    // 新增接口：获取定时任务状态（Spring 自动转为 JSON）
    @RequestMapping("/healthToolTask")
    public void getTaskStatus(HttpServletResponse response) throws IOException {
        System.out.println(TimeUtil.GetTime(true)+"开始抓取全民健康信息平台数据-患者基本信息");
        response.setContentType("application/json;charset=UTF-8");
        MatrixTaskStatus temp=matrixHealthToolScheduledBatchService.getCurrentStatus();
        if(temp.isMSuccess()){
            response.getWriter().write(gson.toJson(WebResponse.success(matrixHealthToolScheduledBatchService.getCurrentStatus())));
        }else{
            response.getWriter().write(gson.toJson(WebResponse.failure()));
        }
    }
}
