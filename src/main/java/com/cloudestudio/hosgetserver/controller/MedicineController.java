package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.MedicineAllBean;
import com.cloudestudio.hosgetserver.model.MedicineBaseBean;
import com.cloudestudio.hosgetserver.model.PrintStyleBean;
import com.cloudestudio.hosgetserver.service.MedicineService;
import com.cloudestudio.hosgetserver.service.PrintStyleService;
import com.cloudestudio.hosgetserver.webTools.QRCodeUtil;
import com.cloudestudio.hosgetserver.webTools.StringUtil;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebServerResponse;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class MedicineController
 * @Author Create By Matrix·张
 * @Date 2024/11/26 下午8:42
 * 药剂控制类
 */
@Controller
@RequestMapping("/MedicineApi")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private PrintStyleService printStyleService;

    private static final Gson gson=new Gson();

    /***********************查询逻辑:MySql库********************/
    /**
     * 查询药剂基表信息
     * @param medicine_code
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryBaseMedicineInfo")
    public void queryBaseMedicineInfo(@RequestParam("medicine_code") String medicine_code,
                                      HttpServletResponse response) throws IOException {
        MedicineBaseBean request=medicineService.queryMedicineInfo(medicine_code);
        response.setContentType("application/json;charset=UTF-8");
        if (request==null) {
            response.getWriter().write(gson.toJson(WebServerResponse.failure("请求异常：药剂编码未存在！")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 查询药剂信息成功："+request.toString());
            response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",request)));
        }
    }

    /***
     * 通过打印名称获取条码打印格式
     * @param printName
     * @param response
     * @throws IOException
     * @throws WriterException
     */
    @RequestMapping("/getPrintStyle")
    public void getPrintStyle(@RequestParam("printName") String printName,HttpServletResponse response) throws IOException, WriterException {
        PrintStyleBean printStyleBean = printStyleService.query_print_style(printName);
        response.setContentType("application/json;charset=UTF-8");
        if (printStyleBean != null) {
            System.out.println("打印格式:"+printStyleBean.toString());
            response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",printStyleBean)));
        }else{
            response.getWriter().write(gson.toJson(WebServerResponse.failure("打印操作失异常!")));
        }
    }

    /**
     * 创建药剂条码
     * @param medicine_code
     * @param response
     * @throws IOException
     * @throws WriterException
     */
    @RequestMapping("/generateBarcodeImage")
    public void createBarcodeImage(@RequestParam("medicine_code") String medicine_code,
                                   HttpServletResponse response) throws IOException, WriterException {
        response.setContentType("application/json;charset=UTF-8");
        String responseUrl= QRCodeUtil.generateBarcodeImage2(medicine_code);
        if(StringUtil.isEmptyOrNull(responseUrl)){
            response.getWriter().write(gson.toJson(WebServerResponse.failure("请求异常：生成条码错误！")));
        }else {
            System.out.println(TimeUtil.GetTime(true)+" 生成条码成功");
            Map<String,String> responseMap=new HashMap<>();
            responseMap.put("medicineBarcode",responseUrl);
            response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",responseMap)));
        }
    }

    /**
     * 获取最新药剂编码
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryNearMedicineCode")
    public void queryNearMedicineCode(HttpServletResponse response) throws IOException {
        MedicineBaseBean request=medicineService.queryNearMedicineCode();
        System.out.println(TimeUtil.GetTime(true)+" 最新药品Code:"+request.getMedicine_code());
        response.setContentType("application/json;charset=UTF-8");
        if (request==null) {
            response.getWriter().write(gson.toJson(WebServerResponse.failure("获取新药品编码异常:查询为空！")));
        }else{
            int index = -1;
            for (int i = 0; i < request.getMedicine_code().length(); i++) {
                if (Character.isDigit(request.getMedicine_code().charAt(i))) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                String prefix = request.getMedicine_code().substring(0, index); // "YP"
                String numberStr = request.getMedicine_code().substring(index);  // "1050"
                // 将数字字符串转换为整数
                int number = Integer.parseInt(numberStr);
                // 将数字加1
                number += 1;
                // 将整数转换回字符串
                String newNumberStr = Integer.toString(number);
                // 合并回新的字符串
                String newString = prefix + newNumberStr;
                System.out.println(TimeUtil.GetTime(true) + " 新编码:" + newString);
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("medicine_code", newString);
                response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功", responseMap)));
            }
        }
    }

    /**
     * 创建药剂基本字典
     * @param response
     * @throws IOException
     */
    @RequestMapping("/addMedicineBaseInfo")
    public void addMedicineBaseInfo(@RequestParam("medicine_code") String medicine_code,
                                    @RequestParam("medicine_name") String medicine_name,
                                    @RequestParam("medicine_price") String medicine_price,
                                    @RequestParam("medicine_retail") String medicine_retail,
                                    HttpServletResponse response) throws IOException {
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("medicine_code",medicine_code);
        requestMap.put("medicine_name",medicine_name);
        requestMap.put("medicine_price",medicine_price);
        requestMap.put("medicine_retail",medicine_retail);
        boolean insertKey=medicineService.addMedicineBaseInfo(requestMap);
        response.setContentType("application/json;charset=UTF-8");
        if (insertKey) {
            System.out.println(TimeUtil.GetTime(true) +" 药剂字典创建:"+requestMap.toString());
            response.getWriter().write(gson.toJson(WebServerResponse.success("药剂字典创建成功!")));
        }else{
            System.out.println(TimeUtil.GetTime(true) +" 药剂字典创建异常:"+requestMap.toString());
            response.getWriter().write(gson.toJson(WebServerResponse.failure("药剂字典创建异常!")));
        }
    }

    /***
     * 查询药品基表信息
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryMedicineBaseInfo")
    public void queryMedicineBaseInfo(HttpServletResponse response) throws IOException {
        List<MedicineBaseBean> medicineBaseInfoList=medicineService.queryMedicineBaseInfo();
        response.setContentType("application/json;charset=UTF-8");
        if (!medicineBaseInfoList.isEmpty()) {
            Map<String,Object> responseMap=new HashMap<>();
            responseMap.put("medicineBaseInfoList", medicineBaseInfoList);
            System.out.println(TimeUtil.GetTime(true) +" 查询药品:"+responseMap.toString());
            response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",responseMap)));
        }else{
            response.getWriter().write(gson.toJson(WebServerResponse.failure("请求失败")));
        }
    }

    /**
     * 入库逻辑
     * @param medicineCode
     * @param inHouseCount
     * @param operator
     * @param createTime
     * @param wareHouseAddr
     * @param response
     * @throws IOException
     */
    @RequestMapping("/medicineToWareHouse")
    public void medicineToWareHouse(@RequestParam("medicineCode") String medicineCode,
                                    @RequestParam("inHouseCount") int inHouseCount,
                                    @RequestParam("operator") String operator,
                                    @RequestParam("createTime") String createTime,
                                    @RequestParam("wareHouseAddr") String wareHouseAddr,
                                    HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("medicine_code",medicineCode);
        requestMap.put("inwarehouse_count",inHouseCount);
        requestMap.put("towarehouse_operator",operator);
        requestMap.put("create_time",createTime);
        requestMap.put("ware_addr",wareHouseAddr);
        System.out.println(TimeUtil.GetTime(true)+" 入库请求参数:"+requestMap.toString());
        MedicineAllBean requstueryBean=new MedicineAllBean();
        requstueryBean=medicineService.queryInfoByCodeCreateTime(requestMap);
        if(requstueryBean==null){
            //直接插入
            requestMap.put("medicine_batch_number",TimeUtil.timeToString(createTime)+"01");
            requestMap.put("warehouse_count",inHouseCount);
            requestMap.put("canuse_count",inHouseCount);
            System.out.println(TimeUtil.GetTime(true)+" 未存在-入库插值参数:"+requestMap.toString());
        }else{
            requestMap.put("medicine_batch_number",requstueryBean.getMedicine_batch_number()+1);
            requestMap.put("warehouse_count",inHouseCount);
            requestMap.put("canuse_count",inHouseCount);
            System.out.println(TimeUtil.GetTime(true)+" 已存在-入库插值参数:"+requestMap.toString());
        }
        boolean inwarehouse=medicineService.addMedicineToWareHouse(requestMap);
        if(inwarehouse){
            System.out.println(TimeUtil.GetTime(true)+" 入库成功！参数:"+requestMap.toString());
            response.getWriter().write(gson.toJson(WebServerResponse.success("入库成功！",requestMap)));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 入库失败！参数:"+requestMap.toString());
            response.getWriter().write(gson.toJson(WebServerResponse.failure("入库异常！")));
        }
    }
    /***********************查询逻辑:MySql库********************/
}
