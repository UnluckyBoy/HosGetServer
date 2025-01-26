package com.cloudestudio.hosgetserver.webTools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @Class FileUtil
 * @Author Create By Matrix·张
 * @Date 2024/12/15 上午11:50
 * 文件工具类
 */
public class FileUtil {
    private static final String SYSTEM_PATH = System.getProperty("user.dir")+"/BackResource/";
    private static final String UPLOADED_FOLDER = "UpLoads/";

    /**
     * 将给定的字符串内容写入到指定的日志文件中。
     * 如果日志目录不存在，则会自动创建。
     * @param content 要写入文件的内容
     * @param logFileName 日志文件的名称
     */
    public static boolean writeLogFile(String content, String logFileName) {
        // 获取项目根目录或指定的日志目录
        String projectRootPath = Paths.get("").toAbsolutePath().toString();
        File logsDir = new File(projectRootPath,"logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs(); // 创建文件夹（包括必要的父文件夹）
        }
        // 构建日志文件的完整路径
        File logFile = new File(logsDir, logFileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) { // true表示追加模式
            writer.write(content);
            writer.newLine(); // 添加换行符，以便下次写入时内容在新的一行
            System.out.println(TimeUtil.GetTime(true)+"  日志已写入文件: " + logFile.getAbsolutePath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writePdfFile(String fileName,MultipartFile file){
        try {
            File dest = new File(SYSTEM_PATH+UPLOADED_FOLDER + fileName);// 创建文件保存路径
            dest.getParentFile().mkdirs();// 确保路径存在
            file.transferTo(dest);// 保存文件且覆盖
            System.out.println(TimeUtil.GetTime(true)+"  已写入文件: " + dest.getAbsolutePath());
            return true;
        } catch (IOException error) {
            error.printStackTrace();
            return false;
        }
    }
}
