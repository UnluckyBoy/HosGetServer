package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.paramBody.SavePermissionBean;
import com.cloudestudio.hosgetserver.service.HosCommon.ManagerService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @Class ManagerController
 * @Author Create By Matrix·张
 * @Date 2026/6/3 下午4:54
 * 管理员操作控制类
 */
@Controller
@RequestMapping("/managerApi")
public class ManagerController {
    @Autowired
    ManagerService managerService;

    private static final Gson gson=new Gson();//Json数据对象
    private static final Gson gsonConfig=new GsonBuilder().serializeNulls().create();//Json数据对象,强制将NULL返回

    @RequestMapping("/getPermissions")
    public void queryPatientInfo(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gsonConfig.toJson(managerService.queryUPermission()));
    }

    @RequestMapping("/getAllPermission")
    public void queryAllPermission(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gsonConfig.toJson(managerService.queryAllPermission()));
    }

    @RequestMapping("/savePermission")
    public void savePermission(HttpServletResponse response, @RequestBody SavePermissionBean requestBody) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(TimeUtil.GetTime(true)+"保存权限->入参: " + requestBody);

        response.getWriter().write(gsonConfig.toJson(managerService.batchUpPerMission(requestBody.getAccount(),requestBody.getPermission())));
    }
}
