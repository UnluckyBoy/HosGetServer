package com.cloudestudio.hosgetserver.service.PdfService;

import com.cloudestudio.hosgetserver.model.Common.PdfConversionResult;
import com.cloudestudio.hosgetserver.model.Common.PdfConverterProperties;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Class PdfConversionService
 * @Author Create By Matrix·张
 * @Date 2026/3/2 下午7:26
 * PDF转换服务
 */
@Service
@RequiredArgsConstructor
public class PdfConversionService {

    private final PdfConverterProperties properties;

    /**
     * 转换单个PDF文件
     */
    public List<File> convertPdfToImages(File pdfFile) throws IOException {
        List<File> imageFiles = new ArrayList<>();
        String fileName = pdfFile.getName().replace(".pdf", "");

        // 确保临时目录存在
        Path tempPath = Paths.get(properties.getTempFolder());
        if (!Files.exists(tempPath)) {
            Files.createDirectories(tempPath);
        }

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page,
                        properties.getImageDpi());

                File imageFile = new File(properties.getTempFolder(),
                        String.format("%s_page_%d.jpg", fileName, page + 1));

                ImageIO.write(bim, "jpg", imageFile);
                imageFiles.add(imageFile);

                System.out.println(TimeUtil.GetTime(true)+"\t "+"转换第"+page + 1+"页:"+imageFile.getName());
            }

            System.out.println(TimeUtil.GetTime(true)+"\t "+"PDF转换完成:"+pdfFile.getName()+"\t"+imageFiles.size()+"张图片");

        } catch (Exception e) {
            System.out.println(TimeUtil.GetTime(true)+"\t "+"转换PDF失败:"+pdfFile.getName()+e);
            throw new IOException("PDF转换失败", e);
        }

        return imageFiles;
    }

    /**
     * 批量转换PDF文件
     */
    public List<PdfConversionResult> convertBatch(List<File> pdfFiles) {
        List<PdfConversionResult> results = new ArrayList<>();

        for (File pdfFile : pdfFiles) {
            try {
                List<File> images = convertPdfToImages(pdfFile);
                results.add(PdfConversionResult.success(pdfFile, images));

                // 如果需要删除源文件
//                if (properties.isDeleteSource()) {
//                    Files.delete(pdfFile.toPath());
//                    System.out.println(TimeUtil.GetTime(true)+"\t "+"已删除源文件:"+pdfFile.getName());
//                }

            } catch (Exception e) {
                System.out.println(TimeUtil.GetTime(true)+"\t "+"处理文件失败:"+pdfFile.getName()+"\t"+e);
                results.add(PdfConversionResult.failure(pdfFile, e.getMessage()));
            }
        }

        return results;
    }

    /**
     * 清理临时文件
     */
    public void cleanupTempFiles(List<File> files) {
        for (File file : files) {
            try {
                Files.deleteIfExists(file.toPath());
                System.out.println(TimeUtil.GetTime(true)+"\t "+"已删除临时文件:"+file.getName());
            } catch (IOException e) {
                System.out.println(TimeUtil.GetTime(true)+"\t "+"删除临时文件失败:"+file.getName()+"\t"+e);
            }
        }
    }
}
