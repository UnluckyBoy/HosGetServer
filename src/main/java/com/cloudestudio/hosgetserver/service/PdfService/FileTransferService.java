package com.cloudestudio.hosgetserver.service.PdfService;

import com.cloudestudio.hosgetserver.model.Common.SftpProperties;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @Class FileTransferService
 * @Author Create By Matrix·张
 * @Date 2026/3/2 下午7:36
 * 文件传输服务
 */
@Service
@RequiredArgsConstructor
public class FileTransferService {

    private final SftpProperties sftpProperties;
    private Session session;
    private ChannelSftp channel;

    /**
     * 通过SFTP传输文件
     */
    public TransferResult transferViaSftp(List<File> files, String targetPath) {
        TransferResult result = new TransferResult();

        try {
            connect();
            ensureRemoteDirectory(targetPath);

            for (File file : files) {
                try {
                    String remoteFile = targetPath + file.getName();
                    try (FileInputStream fis = new FileInputStream(file)) {
                        channel.put(fis, remoteFile);
                        result.addSuccess(file.getName());
                        System.out.println(TimeUtil.GetTime(true)+"\t "+"传输成功:"+file.getName());
                    }
                } catch (Exception e) {
                    System.out.println(TimeUtil.GetTime(true)+"\t "+"传输失败:"+file.getName()+"\t"+e);
                    result.addFailure(file.getName(), e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println(TimeUtil.GetTime(true)+"\t "+"SFTP传输过程出错:"+e);
            result.setGlobalError(e.getMessage());
        } finally {
            disconnect();
        }

        return result;
    }

    /**
     * 连接到SFTP服务器
     */
    private void connect() throws JSchException {
        JSch jsch = new JSch();

        // 创建会话
        session = jsch.getSession(
                sftpProperties.getUsername(),
                sftpProperties.getHost(),
                sftpProperties.getPort()
        );
        session.setPassword(sftpProperties.getPassword());

        // 配置
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        // 连接
        session.connect(30000);
        System.out.println(TimeUtil.GetTime(true)+"\t "+"SFTP会话已建立:");

        // 打开通道
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect(10000);
        System.out.println(TimeUtil.GetTime(true)+"\t "+"SFTP通道已打开:");
    }

    /**
     * 确保远程目录存在
     */
    private void ensureRemoteDirectory(String path) throws SftpException {
        String[] folders = path.split("/");
        String currentPath = "";

        for (String folder : folders) {
            if (folder.isEmpty()) continue;
            currentPath += "/" + folder;
            try {
                channel.stat(currentPath);
            } catch (SftpException e) {
                channel.mkdir(currentPath);
                System.out.println(TimeUtil.GetTime(true)+"\t "+"创建远程目录:"+currentPath);
            }
        }
    }

    /**
     * 断开连接
     */
    private void disconnect() {
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
        System.out.println(TimeUtil.GetTime(true)+"\t "+"SFTP连接已断开:");
    }

    @lombok.Data
    public static class TransferResult {
        private List<String> successFiles = new java.util.ArrayList<>();
        private List<FailureDetail> failureFiles = new java.util.ArrayList<>();
        private String globalError;

        public void addSuccess(String fileName) {
            successFiles.add(fileName);
        }

        public void addFailure(String fileName, String error) {
            failureFiles.add(new FailureDetail(fileName, error));
        }

        public boolean hasFailures() {
            return !failureFiles.isEmpty() || globalError != null;
        }
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    public static class FailureDetail {
        private String fileName;
        private String error;
    }
}
