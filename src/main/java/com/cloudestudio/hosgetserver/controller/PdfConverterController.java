package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.service.PdfService.PdfHandleService;
import com.cloudestudio.hosgetserver.service.PdfService.PdfScanService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class PdfConverterController
 * @Author Create By Matrix·张
 * @Date 2026/3/2 下午7:58
 * PDF转JPG控制类
 */
@Controller
@RequestMapping("/pdfConverterApi")
@RequiredArgsConstructor
public class PdfConverterController {
    @Autowired
    PdfHandleService pdfHandleService;

    private static final Gson gson=new Gson();

    /**
     * 手动触发扫描处理
     */
    @RequestMapping("/scan")
    public void scanNow(HttpServletResponse response, @RequestParam String pdfName) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gson.toJson(pdfHandleService.scanNow(pdfName)));
    }

    /**
     * 获取配置信息
     */
//    @GetMapping("/config")
//    public ResponseEntity<?> getConfig() {
//        // 返回脱敏后的配置信息
//        return ResponseEntity.ok(Map.of(
//                "sourceFolder", "***",
//                "targetFolder", "***",
//                "schedulingEnabled", true
//        ));
//    }

    /**
     * 健康检查
     */
//    @GetMapping("/health")
//    public ResponseEntity<?> health() {
//        return ResponseEntity.ok(Map.of(
//                "status", "UP",
//                "timestamp", System.currentTimeMillis()
//        ));
//    }
}
