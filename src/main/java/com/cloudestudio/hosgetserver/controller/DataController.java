package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.*;
import com.cloudestudio.hosgetserver.service.HosDataService;
import com.cloudestudio.hosgetserver.service.MedicineService;
import com.cloudestudio.hosgetserver.service.PrintStyleService;
import com.cloudestudio.hosgetserver.service.UserLoginService;
import com.cloudestudio.hosgetserver.webTools.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 数据控制类
 *
 * @author Matrix·张
 * @Date 2024/11/13 下午1:47
 */
@Controller
@RequestMapping("/api")
public class DataController {
    @Autowired
    private HosDataService hosDataService;

    @Autowired
    private UserLoginService userLoginService;

    private static final Gson gson=new Gson();//Json数据对象
    private static final Gson gsonConfig=new GsonBuilder().serializeNulls().create();//Json数据对象,强制将NULL返回
    private static final Map<String,Object> scheduledFutureMap=new HashMap<>();


    /***********************查询逻辑:Oracle库********************/
    @RequestMapping("/hosConsultation")
    public void getHosConsultation(HttpServletResponse response,
                                   @RequestParam("startTime") String startTime,
                                   @RequestParam("endTime") String endTime) throws IOException {
        System.out.println(TimeUtil.GetTime(true)+"---"+startTime+"---"+endTime);
        List<HosDataBean> resultList=hosDataService.queryInHospital_consultation(startTime,endTime);//TimeUtil.stingToTime("2024-01-01 00:00:00")
        response.setContentType("application/json;charset=UTF-8");
        if (resultList.isEmpty()) {
            response.getWriter().write(gson.toJson(WebServerResponse.failure("请求失败")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询成功:"+resultList);
            response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",resultList)));
        }
    }
    /**********************查询逻辑:Oracle库********************/

    /***********************查询逻辑:MySql库********************/
    /**
     * 登录查询
     * @param response
     * @param account
     * @param pass
     * @throws IOException
     */
    @RequestMapping("/loginQuery")
    public void Login(HttpServletResponse response,
                              @RequestParam("account") String account,
                              @RequestParam("pass") String pass) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> requestMap=new HashMap<>();
        String passTemp=MatrixEncodeUtil.decodeFromBase64(pass);//先解密
        int index = passTemp.indexOf('+');
        if (index != -1) {
            String originalPass = passTemp.substring(0, index);
            System.out.println(TimeUtil.GetTime(true)+"原密码:"+originalPass); // 输出原密码
            requestMap.put("account",account);
            requestMap.put("pass",MatrixEncodeUtil.encodeTwice(originalPass));
            UserInfoBean userInfoBean=userLoginService.loginQuery(requestMap);
            if (userInfoBean==null) {
                response.getWriter().write(gson.toJson(WebServerResponse.failure("请求失败")));
            }else{
                System.out.println(TimeUtil.GetTime(true)+" ---查询成功:"+userInfoBean);
                response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",userInfoBean)));
            }
        } else {
            System.out.println(passTemp); // 如果未找到 '+'，则输出原始字符串
            response.getWriter().write(gson.toJson(WebServerResponse.failure("后台异常：密码解码ERROR!"+passTemp)));
        }
    }

    @RequestMapping("/pushPatientInfo")
    public void pushPatientInfo(HttpServletResponse response,
                                @RequestParam("regisNumber") String regisNumber,
                                @RequestParam("apiUrl") String apiUrl) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PatientBaseInfoBean resultBean=hosDataService.queryPatientBaseInfo(regisNumber);
        if(resultBean!=null){
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息成功:"+resultBean);
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息成功--数据处理:"+gsonConfig.toJson(resultBean));
            PushResponse pushResult=HttpClientUtil.pushDataInfo(apiUrl,gsonConfig.toJson(resultBean));
            response.getWriter().write(gson.toJson(pushResult));
            System.out.println(TimeUtil.GetTime(true)+" ---成功--数据处理:"+gson.toJson(pushResult));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息失败:"+ null);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("查询患者基本信息失败")));
        }
    }
    @RequestMapping("/pushPatientActivity")
    public void pushPatientActivity(HttpServletResponse response,
                                @RequestParam("regisNumber") String regisNumber,
                                @RequestParam("apiUrl") String apiUrl) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        List<PatientActivityBean> resultBean=hosDataService.queryPatientActivityInfo(regisNumber);
        if(resultBean!=null&&!resultBean.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者门诊诊断信息成功:"+resultBean);
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者门诊诊断信息成功--数据处理:"+gsonConfig.toJson(resultBean));
            List<PushResponse> resultList=new ArrayList<PushResponse>();
            for (PatientActivityBean data : resultBean){
                PushResponse pushResult=HttpClientUtil.pushDataInfo(apiUrl,gsonConfig.toJson(data));
                resultList.add(pushResult);
            }
            response.getWriter().write(gson.toJson(resultList));
            System.out.println(TimeUtil.GetTime(true)+" ---成功--数据处理:"+gson.toJson(resultList));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者门诊诊断信息失败:"+ null);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("查询患者门诊诊断信息失败")));
        }
    }

    @RequestMapping("queryTaskStatus")
    public void queryTaskStatus(HttpServletResponse response,
                                @RequestParam("apiUrl") String apiUrl)throws IOException,URISyntaxException{
        response.setContentType("application/json;charset=UTF-8");
        //String m_key=new URI(apiUrl).getPath().split("/")[new URI(apiUrl).getPath().split("/").length - 1];
        String m_key=getUrlName(apiUrl);
        ScheduledFuture<?> task = (ScheduledFuture<?>) scheduledFutureMap.get(m_key);
        if(task!=null){
            /**
             * 取消为true
             * 未取消为false
             */
            System.out.println(TimeUtil.GetTime(true)+" 任务查询：" +m_key+" ---"+task.isCancelled());
            response.getWriter().write(gson.toJson(WebServerResponse.success("任务状态获取成功!",task.isCancelled())));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 任务查询：" +m_key+" 任务状态获取失败-任务未创建");
            response.getWriter().write(gson.toJson(WebServerResponse.success("任务状态获取失败-任务未创建",true)));
        }
    }

    @RequestMapping("pushCurrentPatientInfoOnTask")
    public void pushOnTask(HttpServletResponse response,
                           @RequestParam("apiUrl") String apiUrl,
                           @RequestParam("runInterval") String runInterval,
                           @RequestParam("runType") String runType) throws URISyntaxException,IOException {
        response.setContentType("application/json;charset=UTF-8");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //String m_key=new URI(apiUrl).getPath().split("/")[new URI(apiUrl).getPath().split("/").length - 1];
        String m_key=getUrlName(apiUrl);
        ScheduledFuture<?> futureToHandle = (ScheduledFuture<?>) scheduledFutureMap.get(m_key);
        switch (runType){
            case "start":
                if (futureToHandle != null) {
                    futureToHandle.cancel(false); // false表示如果任务正在执行，则不中断它
                    //scheduler.shutdownNow(); // 停止调度器
                    System.out.println(TimeUtil.GetTime(true)+" 任务重新调用：" +m_key+"成功");
                }
                ApiTask task = new ApiTask(apiUrl, hosDataService, gsonConfig, gson);
                int period = Integer.parseInt(runInterval.substring(0, runInterval.length() - 1));
                TimeUnit unit = switch (runInterval.substring(runInterval.length() - 1)) {
                    case "h" -> TimeUnit.HOURS;
                    case "m" -> TimeUnit.MINUTES;
                    case "s" -> TimeUnit.SECONDS;
                    default -> throw new IllegalArgumentException("Invalid interval unit: " + runInterval.substring(runInterval.length() - 1));
                };
                ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(task, 0, period, unit);
                scheduledFutureMap.put(m_key, scheduledFuture);
                response.getWriter().write(gson.toJson(WebServerResponse.success("启动任务"+m_key+"成功")));
                break;
            case "stop":
                 if (futureToHandle != null) {
                     futureToHandle.cancel(false); // false表示如果任务正在执行，则不中断它
                     //scheduler.shutdownNow(); // 停止调度器
                     System.out.println(TimeUtil.GetTime(true)+" API停止调用：" +apiUrl);
                     response.getWriter().write(gson.toJson(WebServerResponse.success("停止任务"+m_key+"成功")));
                 }else{
                     response.getWriter().write(gson.toJson(WebServerResponse.failure("停止任务"+m_key+"失败")));
                 }
                break;
        }
    }

//    @RequestMapping("/pushCurrentPatientInfo")
//    public void pushCurrentPatientInfo(HttpServletResponse response,
//                                       @RequestParam("apiUrl") String apiUrl) throws IOException, URISyntaxException {
//        response.setContentType("application/json;charset=UTF-8");
//        List<PushResponse> resultList=pushHandle(apiUrl);
//        if (resultList != null && !(resultList.isEmpty())) {
//            response.getWriter().write(gson.toJson(WebServerResponse.success("上传成功",resultList)));
//        }else{
//            response.getWriter().write(gson.toJson(WebServerResponse.failure("上传失败")));
//        }
//    }

//    /**
//     * 调用信息上传网络接口工具
//     * @param url
//     * @return
//     * @throws IOException
//     */
//    private List<PushResponse> pushHandle(String url) throws IOException, URISyntaxException {
//        String timeTemp="上传时间:"+TimeUtil.GetTime(true)+"\n";
//        switch (getUrlName(url)){
//            case "emrPatientInfo":
//                List<PatientBaseInfoBean> emrPatientInfoList=hosDataService.queryPatientBaseInfoList();
//                if(emrPatientInfoList!=null&& !emrPatientInfoList.isEmpty()){
//                    System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息成功:"+emrPatientInfoList);
//                    System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息成功--数据处理:"+gsonConfig.toJson(emrPatientInfoList));
//                    StringBuilder result= new StringBuilder();
//                    List<PushResponse> resultList=new ArrayList<PushResponse>();
//                    for (PatientBaseInfoBean data : emrPatientInfoList) {
//                        PushResponse pushResult=HttpClientUtil.pushDataInfo(url,gsonConfig.toJson(data));
//                        System.out.println(TimeUtil.GetTime(true)+" ---上报结果:"+gson.toJson(pushResult));
//                        result.append(gson.toJson(pushResult)).append("\n");
//                        resultList.add(pushResult);
//                    }
//                    boolean writeFile=FileUtil.writeLogFile(timeTemp+result,getUrlName(url)+"_"+TimeUtil.timeToString(TimeUtil.GetTime(false)+".log"));
//                    if(writeFile){
//                        return resultList;
//                    }else{
//                        return null;
//                    }
//                }else{
//                    return null;
//                }
//            case "emrActivityInfo":
//                List<PatientBaseInfoBean> emrActivityInfoList=hosDataService.queryPatientBaseInfoList();
//                if(emrActivityInfoList!=null&& !emrActivityInfoList.isEmpty()){
//                    System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息成功:"+emrActivityInfoList);
//                    System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息成功--数据处理:"+gsonConfig.toJson(emrActivityInfoList));
//                    StringBuilder result= new StringBuilder();
//                    List<PushResponse> resultList=new ArrayList<PushResponse>();
//                    for (PatientBaseInfoBean data : emrActivityInfoList) {
//                        PushResponse pushResult=HttpClientUtil.pushDataInfo(url,gsonConfig.toJson(data));
//                        System.out.println(TimeUtil.GetTime(true)+" ---上报结果:"+gson.toJson(pushResult));
//                        result.append(gson.toJson(pushResult)).append("\n");
//                        resultList.add(pushResult);
//                    }
//                    boolean writeFile=FileUtil.writeLogFile(timeTemp+result,getUrlName(url)+"_"+TimeUtil.timeToString(TimeUtil.GetTime(false)+".log"));
//                    if(writeFile){
//                        return resultList;
//                    }else{
//                        return null;
//                    }
//                }else{
//                    return null;
//                }
//            default:
//                return null;
//        }
//    }


    @RequestMapping("/test")
    public void Test(HttpServletResponse response,
                     @RequestParam("account") String account,
                     @RequestParam("pass") String pass) throws IOException {
        String encode= MatrixEncodeUtil.encodeTwice(pass);
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("encode",encode);
        requestMap.put("decode",MatrixEncodeUtil.decodeTwice(encode));
        response.getWriter().write(gson.toJson(WebServerResponse.success("请求成功",requestMap)));
    }
    /*********************查询逻辑:MySql库********************/


    /**********************公共逻辑********************/

    /**
     *
     * @param apiUrl
     * @return
     * @throws URISyntaxException
     */
    private String getUrlName(String apiUrl) throws URISyntaxException {
        return new URI(apiUrl).getPath().split("/")[new URI(apiUrl).getPath().split("/").length - 1];
    }
    /***********************公共逻辑********************/
}
