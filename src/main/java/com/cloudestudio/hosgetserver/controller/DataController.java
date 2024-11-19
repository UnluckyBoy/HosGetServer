package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.HosDataBean;
import com.cloudestudio.hosgetserver.model.UserInfoBean;
import com.cloudestudio.hosgetserver.service.HosDataService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebServerResponse;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

/**
 * 数据控制类
 *
 * @author Matrix·张
 * @Date 2024/11/13 下午1:47
 */
@Controller
@RequestMapping("/api")
public class DataController {
    @Autowired
    private HosDataService hosDataService;

    private static final Gson gson=new Gson();//Json数据对象

    /***********************查询逻辑:Oracle库********************/
    @RequestMapping("/hosConsultation")
    public void getHosConsultation(HttpServletResponse response,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime) throws IOException {
        System.out.println(TimeUtil.GetTime(true)+"---"+startTime+"---"+endTime);
        List<HosDataBean> resultList=hosDataService.queryInHospital_consultation(startTime,endTime);//TimeUtil.stingToTime("2024-01-01 00:00:00")
        response.setContentType("application/json;charset=UTF-8");
        if (resultList.isEmpty()) {
            response.getWriter().write(gson.toJson(WebServerResponse.failure("请求失败")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+"---查询成功:"+resultList);
            response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",resultList)));
        }
    }
    /***********************查询逻辑:Oracle库********************/

    /***********************查询逻辑:MySql库********************/
    @RequestMapping("/queryUserInfo")
    public void queryUserInfo(HttpServletResponse response,
                                   @Param("account") String account) throws IOException {
        UserInfoBean userInfoBean=hosDataService.queryUserInfo(account);//TimeUtil.stingToTime("2024-01-01 00:00:00")
        response.setContentType("application/json;charset=UTF-8");
        if (userInfoBean==null) {
            response.getWriter().write(gson.toJson(WebServerResponse.failure("请求失败")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+"---查询成功:"+userInfoBean);
            response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",userInfoBean)));
        }
    }
    /***********************查询逻辑:MySql库********************/


    /***********************公共逻辑********************/

    /***********************公共逻辑********************/
}
