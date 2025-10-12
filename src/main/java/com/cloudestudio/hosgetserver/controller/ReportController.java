package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.service.Report.OutSettlementReportService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class ReportCntroller
 * @Author Create By Matrix·张
 * @Date 2025/10/12 下午3:41
 * 报表控制类
 */
@Controller
@RequestMapping("/reportApi")
public class ReportController {
    @Autowired
    OutSettlementReportService outSettlementReportService;

    private static final Gson gson=new Gson();//Json数据对象
    private static final Gson gsonConfig=new GsonBuilder().serializeNulls().create();//Json数据对象,强制将NULL返回

    @RequestMapping("/outSettlementReport")
    public void getOutSettlementReport(HttpServletResponse response, @RequestBody BedDayBody body) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        Map<String,Object> map=new HashMap<>();
        map.put("startTime",body.getStartTime()+" 00:00:00");
        map.put("endTime",body.getEndTime()+" 23:59:59");
        response.getWriter().write(gsonConfig.toJson(outSettlementReportService.queryOutSettlementReport(map)));
    }
}
