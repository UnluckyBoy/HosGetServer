package com.cloudestudio.hosgetserver.model.Common;

import java.io.File;
import java.util.List;

/**
 * @Class PdfConversionResult
 * @Author Create By Matrix·张
 * @Date 2026/3/2 下午7:33
 */
@lombok.Data
@lombok.AllArgsConstructor
public class PdfConversionResult {
    private File sourceFile;
    private List<File> imageFiles;
    private boolean success;
    private String errorMessage;

    public static PdfConversionResult success(File source, List<File> images) {
        return new PdfConversionResult(source, images, true, null);
    }

    public static PdfConversionResult failure(File source, String error) {
        return new PdfConversionResult(source, null, false, error);
    }
}
