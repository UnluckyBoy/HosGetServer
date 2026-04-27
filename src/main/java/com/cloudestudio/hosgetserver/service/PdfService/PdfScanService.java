package com.cloudestudio.hosgetserver.service.PdfService;

import com.cloudestudio.hosgetserver.model.Common.PdfConverterProperties;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Class PdfScanService
 * @Author Create By Matrix·张
 * @Date 2026/3/2 下午7:50
 * PDF扫描及处理服务
 */
@Service
@RequiredArgsConstructor
public class PdfScanService {
    private final PdfConverterProperties properties;
    private final PdfConversionService conversionService;
    private final FileTransferService transferService;

    /**
     * 扫描并处理所有PDF文件
     */
    public ProcessingSummary scanAndProcess() {
        System.out.println(TimeUtil.GetTime(true)+"\t "+"开始扫描文件夹:"+properties.getSourceFolder());
        ProcessingSummary summary = new ProcessingSummary();

        try {
            // 扫描PDF文件
            List<File> pdfFiles = scanPdfFiles();
            summary.setScannedCount(pdfFiles.size());
            System.out.println(TimeUtil.GetTime(true)+"\t "+"找到:"+pdfFiles.size()+"个PDF文件");

            if (pdfFiles.isEmpty()) {
                return summary;
            }

            // 分批处理，避免内存溢出
            List<List<File>> batches = partition(pdfFiles, 5);

            for (List<File> batch : batches) {
                processBatch(batch, summary);
            }

        } catch (Exception e) {
            System.out.println(TimeUtil.GetTime(true)+"\t "+"扫描处理过程出错:"+e);
            summary.setErrorMessage(e.getMessage());
        }

        System.out.println(TimeUtil.GetTime(true)+"\t "+"处理完成:成功"+summary.getSuccessCount()+"\t失败:"+summary.getFailureCount());
        return summary;
    }

    public ProcessingSummary processSinglePdf(String pdfFileName) {
        ProcessingSummary summary = new ProcessingSummary();
        try {
            Path sourcePath = Paths.get(properties.getSourceFolder());
            Path pdfPath = sourcePath.resolve(pdfFileName);
            File pdfFile = pdfPath.toFile();

            if (!pdfFile.exists() || !pdfFile.isFile()) {
                System.out.println(TimeUtil.GetTime(true) + "\t 文件不存在: " + pdfFileName);
                summary.setErrorMessage("文件不存在: " + pdfFileName);
                return summary;
            }

            System.out.println(TimeUtil.GetTime(true) + "\t 开始处理单个文件: " + pdfFileName);
            List<File> singleFileList = Collections.singletonList(pdfFile);
            processBatch(singleFileList, summary);

            System.out.println(TimeUtil.GetTime(true) + "\t 处理完成: " + pdfFileName);
        } catch (Exception e) {
            System.out.println(TimeUtil.GetTime(true) + "\t 处理单个文件出错: " + e);
            summary.setErrorMessage(e.getMessage());
        }
        return summary;
    }

    /**
     * 扫描源文件夹中的PDF文件
     */
    private List<File> scanPdfFiles() throws IOException {
        Path sourcePath = Paths.get(properties.getSourceFolder());

        if (!Files.exists(sourcePath)) {
            throw new IOException("源文件夹不存在: " + properties.getSourceFolder());
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath,
                path -> path.toString().toLowerCase().endsWith(".pdf"))) {

            List<File> files = new ArrayList<>();
            for (Path path : stream) {
                files.add(path.toFile());
            }
            return files;
        }
    }


    /**
     * 处理一批文件
     */
    private void processBatch(List<File> batch, ProcessingSummary summary) {
        List<File> allImageFiles = new ArrayList<>();

        for (File pdfFile : batch) {
            try {
                // 转换PDF
                List<File> imageFiles = conversionService.convertPdfToImages(pdfFile);
                allImageFiles.addAll(imageFiles);

                summary.addSuccess(pdfFile.getName(), imageFiles.size());
                System.out.println(TimeUtil.GetTime(true)+"\t "+"转换成功:成功"+pdfFile.getName()+"\t"+imageFiles.size()+"页");

            } catch (Exception e) {
                System.out.println(TimeUtil.GetTime(true)+"\t "+"转换失败:成功"+pdfFile.getName()+"\t"+e);
                summary.addFailure(pdfFile.getName(), e.getMessage());
            }
        }

        // 传输图片到远程服务器
        if (!allImageFiles.isEmpty()) {
            FileTransferService.TransferResult transferResult =
                    transferService.transferViaSftp(allImageFiles, "/path/to/folder/Y/");

            summary.setTransferResult(transferResult);

            // 清理临时文件
            conversionService.cleanupTempFiles(allImageFiles);
        }
    }

    /**
     * 将列表分批
     */
    private <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }

    @lombok.Data
    public static class ProcessingSummary {
        private int scannedCount;
        private int successCount;
        private int failureCount;
        private int totalPages;
        private List<FileDetail> successDetails = new ArrayList<>();
        private List<FileDetail> failureDetails = new ArrayList<>();
        private FileTransferService.TransferResult transferResult;
        private String errorMessage;

        public void addSuccess(String fileName, int pages) {
            successCount++;
            totalPages += pages;
            successDetails.add(new FileDetail(fileName, pages, null));
        }

        public void addFailure(String fileName, String error) {
            failureCount++;
            failureDetails.add(new FileDetail(fileName, 0, error));
        }
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    public static class FileDetail {
        private String fileName;
        private int pageCount;
        private String error;
    }
}
