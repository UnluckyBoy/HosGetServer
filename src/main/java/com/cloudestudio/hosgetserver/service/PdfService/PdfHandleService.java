package com.cloudestudio.hosgetserver.service.PdfService;

import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class PdfHandleService
 * @Author Create By Matrix·张
 * @Date 2026/3/2 下午8:01
 * PDF逻辑处理
 */
@Service
@RequiredArgsConstructor
public class PdfHandleService {
    private final PdfScanService pdfScanService;

    public WebServerResponse scanNow(String pdfName) throws IOException {
        System.out.println(TimeUtil.GetTime(true)+"\t "+"手动触发扫描任务");

        PdfScanService.ProcessingSummary summary = pdfScanService.processSinglePdf(pdfName);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("summary", summary);
        response.put("message", String.format(
                "扫描完成: 共%d个文件, 成功%d个, 失败%d个",
                summary.getScannedCount(),
                summary.getSuccessCount(),
                summary.getFailureCount()
        ));

        return WebServerResponse.success(response);
    }
}
