package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.matrixBean.MatrixRequestBody;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.webTools.NumberParser;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebServerResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class MatrixController
 * @Author Create By Matrix·张
 * @Date 2026/1/21 下午1:29
 * 自定义接口
 */
@Controller
@RequestMapping("/matrixApi")
public class MatrixController {
    private static final Gson gson=new Gson();//Json数据对象
    private static final Gson gsonConfig=new GsonBuilder().serializeNulls().create();

    @RequestMapping("/matrix")
    public void getOutSettlementReport(HttpServletResponse response, @RequestBody MatrixRequestBody body) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        List<String> tempList=NumberParser.convertToLines(body.getContent());
        System.out.println(TimeUtil.GetTime(true)+" ---转换字符："+tempList);
//        temp.add();
        Map<Integer, Double> resultMap=NumberParser.processStrings(tempList);
        response.getWriter().write(gsonConfig.toJson(WebServerResponse.success(resultMap)));
    }
}
