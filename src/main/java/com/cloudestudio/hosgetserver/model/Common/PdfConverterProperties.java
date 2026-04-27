package com.cloudestudio.hosgetserver.model.Common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Class PdfConverterProperties
 * @Author Create By Matrix·张
 * @Date 2026/3/2 下午7:23
 * PDF配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "pdf.converter")
public class PdfConverterProperties {
    private String sourceFolder;
    private String tempFolder;
    private int imageDpi = 300;
    private boolean deleteSource = false;
    private long scanInterval = 5000;
}
