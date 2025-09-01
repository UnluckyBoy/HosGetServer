package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.model.paramBody.GreenChannelCreateParamBody;
import com.cloudestudio.hosgetserver.service.GreenChannel.GreenChannelService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * @Class GreenChannelController
 * @Author Create By Matrix·张
 * @Date 2025/6/15 下午9:34
 * 绿色通道控制类
 */
@Controller
@RequestMapping("/GreenChannelApi")
public class GreenChannelController {
    private static final Gson gson=new Gson();//Json数据对象
    private static final Gson gsonConfig=new GsonBuilder().serializeNulls().create();

    @Autowired
    GreenChannelService greenChannelService;

    @RequestMapping("/getPatientInfo")
    public void queryPatientInfo(HttpServletResponse response, @RequestParam String idCard) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gsonConfig.toJson(greenChannelService.queryPatientInfo(idCard)));
    }

    @RequestMapping("/createCaseHis")
    public void getPatientInfo(HttpServletResponse response, @RequestBody GreenChannelCreateParamBody requestBody) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gsonConfig.toJson(greenChannelService.createPatientInfo(requestBody)));
    }

    @RequestMapping("/queryAllCaseHis")
    public void getAllHis(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gsonConfig.toJson(greenChannelService.queryAllHis()));
    }

    @RequestMapping("/queryCaseHisParam")
    public void queryCaseHisParam(HttpServletResponse response, @RequestBody BedDayBody requestBody) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gsonConfig.toJson(greenChannelService.queryHisParam(requestBody)));
    }
}
