package com.cloudestudio.hosgetserver.webTools;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class QRCodeUtil
 * @Author Create By Matrix·张
 * @Date 2024/11/26 下午2:54
 * 条形码、二维码工具
 */
public class QRCodeUtil {
    private static final String backResourceDir="BackResource/";

    private static final int QR_CODE_SIZE = 300;
    private static final String IMAGE_TYPE = "PNG";
    private static final String IMAGE_PARENT_DIR = "BarcodeImage/";

    private static final int BARCODE_WIDTH = 37;
    private static final int BARCODE_HEIGHT = 26;

    /**
     * 二维码
     * @param text
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static String generateQRCodeImage(String text) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE, hints);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, IMAGE_TYPE, baos);

        //return baos.toByteArray();//返回字节数组
        return Base64.getEncoder().encodeToString(baos.toByteArray());//返回Base64字符串
    }

    /**
     * 条形码生成
     * @param text
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static String generateBarcodeImage(String text) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 使用Code128Writer生成条形码
        BitMatrix bitMatrix = new Code128Writer().encode(text, BarcodeFormat.CODE_128, BARCODE_WIDTH, BARCODE_HEIGHT, hints);
        // 将BitMatrix转换为BufferedImage
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        // 使用ByteArrayOutputStream将BufferedImage转换为字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, IMAGE_TYPE, baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * 将条形码写入本地磁盘,并返回文件名
     * @param text
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static String generateBarcodeImage2(String text) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 使用Code128Writer生成条形码
        BitMatrix bitMatrix = new Code128Writer().encode(text, BarcodeFormat.CODE_128, BARCODE_WIDTH, BARCODE_HEIGHT, hints);
        // 将BitMatrix转换为BufferedImage
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        // 构造图片文件名（这里简单使用计数器，实际应用中可以根据需求调整）
        String fileName = "barcode_" + (UUIDNumberUtil.randUUIDNumberAndTime_Param(TimeUtil.GetTime(true))) + "." + IMAGE_TYPE;
        // 定义图片保存路径（这里保存到当前工作目录，实际应用中可以根据需求调整）
        File outputFile = new File(backResourceDir+IMAGE_PARENT_DIR+fileName);
        // 使用FileOutputStream将BufferedImage写入文件
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            ImageIO.write(bufferedImage, IMAGE_TYPE, fos);
        }
        return IMAGE_PARENT_DIR+fileName;
    }

    /**
     * 解码条形码
     * @param base64Image
     * @return
     * @throws WriterException
     * @throws IOException
     * @throws NotFoundException
     */
    public static String BarcodeDecoderFromBase64(String base64Image) throws WriterException, IOException,NotFoundException{
        // 将Base64字符串解码为字节数组
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // 将字节数组转换为BufferedImage对象
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(bis);
        bis.close();

        // 如果图片读取成功，则进行条形码解码
        if (bufferedImage != null) {
            // 将BufferedImage转换为LuminanceSource
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            // 创建条码读取器
            MultiFormatReader reader = new MultiFormatReader();
            // 尝试解码
            Result result = reader.decode(binaryBitmap);
            // 输出解码结果
            //System.out.println("Barcode text is: " + result.getText());
            return result.getText();
        } else {
            //System.out.println("Failed to read image from Base64 string.");
            return null;
        }
    }
}
