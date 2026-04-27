package com.cloudestudio.hosgetserver.model.Common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Class SftpProperties
 * @Author Create By Matrix·张
 * @Date 2026/3/2 下午7:24
 * Sftp配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "remote.transfer.sftp")
public class SftpProperties {
    private String host;
    private int port = 22;
    private String username;
    private String password;
    private String targetFolder;
}
