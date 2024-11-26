package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.HosDataBean;
import com.cloudestudio.hosgetserver.model.MedicineBaseBean;
import com.cloudestudio.hosgetserver.model.PrintStyleBean;
import com.cloudestudio.hosgetserver.model.UserInfoBean;
import com.cloudestudio.hosgetserver.service.HosDataService;
import com.cloudestudio.hosgetserver.service.MedicineService;
import com.cloudestudio.hosgetserver.service.PrintStyleService;
import com.cloudestudio.hosgetserver.webTools.*;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                                   @RequestParam("startTime") String startTime,
                                   @RequestParam("endTime") String endTime) throws IOException {
        System.out.println(TimeUtil.GetTime(true)+"---"+startTime+"---"+endTime);
        List<HosDataBean> resultList=hosDataService.queryInHospital_consultation(startTime,endTime);//TimeUtil.stingToTime("2024-01-01 00:00:00")
        response.setContentType("application/json;charset=UTF-8");
        if (resultList.isEmpty()) {
            response.getWriter().write(gson.toJson(WebServerResponse.failure("请求失败")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询成功:"+resultList);
            response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",resultList)));
        }
    }
    /**********************查询逻辑:Oracle库********************/

    /***********************查询逻辑:MySql库********************/
    /**
     * 登录查询
     * @param response
     * @param account
     * @param pass
     * @throws IOException
     */
    @RequestMapping("/loginQuery")
    public void Login(HttpServletResponse response,
                              @RequestParam("account") String account,
                              @RequestParam("pass") String pass) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> requestMap=new HashMap<>();
        String passTemp=MatrixEncodeUtil.decodeFromBase64(pass);//先解密
        int index = passTemp.indexOf('+');
        if (index != -1) {
            String originalPass = passTemp.substring(0, index);
            System.out.println(TimeUtil.GetTime(true)+"原密码:"+originalPass); // 输出原密码
            requestMap.put("account",account);
            requestMap.put("pass",MatrixEncodeUtil.encodeTwice(originalPass));
            UserInfoBean userInfoBean=hosDataService.loginQuery(requestMap);
            if (userInfoBean==null) {
                response.getWriter().write(gson.toJson(WebServerResponse.failure("请求失败")));
            }else{
                System.out.println(TimeUtil.GetTime(true)+" ---查询成功:"+userInfoBean);
                response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",userInfoBean)));
            }
        } else {
            System.out.println(passTemp); // 如果未找到 '+'，则输出原始字符串
            response.getWriter().write(gson.toJson(WebServerResponse.failure("后台异常：密码解码ERROR!"+passTemp)));
        }
    }

    @RequestMapping("/test")
    public void Test(HttpServletResponse response,
                     @RequestParam("account") String account,
                     @RequestParam("pass") String pass) throws IOException {
        String encode= MatrixEncodeUtil.encodeTwice(pass);
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("encode",encode);
        requestMap.put("decode",MatrixEncodeUtil.decodeTwice(encode));
        response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",requestMap)));
    }
    /*********************查询逻辑:MySql库********************/


    /**********************公共逻辑********************/

    /***********************公共逻辑********************/
}
