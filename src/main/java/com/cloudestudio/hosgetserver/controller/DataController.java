package com.cloudestudio.hosgetserver.controller;

import com.cloudestudio.hosgetserver.model.*;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.service.*;
import com.cloudestudio.hosgetserver.webTools.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Autowired
    private PathologyService pathologyService;

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

    @RequestMapping("pushReportBySerialNumber")
    public void pushReport(HttpServletResponse response,
                           @RequestParam("apiUrl") String apiUrl,
                           @RequestParam("serial_number") String serial_number) throws URISyntaxException,IOException{
        response.setContentType("application/json;charset=UTF-8");
        List<PatientInfReport> resultBean=hosDataService.queryEmrInfReportBySerialNumber(serial_number);
        if(resultBean!=null){
            System.out.println(TimeUtil.GetTime(true)+" ---查询报告卡成功:"+resultBean);
            System.out.println(TimeUtil.GetTime(true)+" ---查询报告卡成功--数据处理:"+gsonConfig.toJson(resultBean));
            List<PushResponse> resultList=new ArrayList<PushResponse>();
            for (PatientInfReport data : resultBean){
                Map<String,Object> tempMap=setReportParamMap(data);
                System.out.println(TimeUtil.GetTime(true)+" ---报告卡处理成功:"+gsonConfig.toJson(tempMap));
                PushResponse pushResult=HttpClientUtil.pushDataInfo(apiUrl,gsonConfig.toJson(tempMap));
                resultList.add(pushResult);
            }
            response.getWriter().write(gson.toJson(WebServerResponse.success("",resultList)));
            System.out.println(TimeUtil.GetTime(true)+" ---报告卡处理成功:"+gson.toJson(resultList));
        }else {
            System.out.println(TimeUtil.GetTime(true) + " ---查询报告卡失败:" + null);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("查询报考卡失败")));
        }
    }

    /**
     * 报告卡填写查询患者基本信息
     * @param response
     * @param serial_number
     * @throws IOException
     */
    @RequestMapping("createReportQueryBaseInfo")
    public void createReportQueryBaseInfo(HttpServletResponse response,
                                           @RequestParam("serial_number") String serial_number)throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(TimeUtil.GetTime(true) + " ---报告卡填写查询参数:" + serial_number);
        ReportQueryPatientBaseInfo requestBean=hosDataService.createReportQueryBaseInfo(serial_number);
        if(requestBean==null){
            System.out.println(TimeUtil.GetTime(true) + " ---查询失败:" + null);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("查询失败")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---查询成功:"+requestBean);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("查询成功",requestBean)));
        }
    }

    /**
     * 查询报告卡数量
     * 生成报告卡ID
     * @param response
     * @throws IOException
     */
    @RequestMapping("queryInfectiousDiseaseCount")
    public void queryInfectiousDiseaseCount(HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        int requestCount=hosDataService.queryInfectiousDiseaseCount();
        System.out.println(TimeUtil.GetTime(true)+" ---报告卡ID查询成功:"+requestCount);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("result",requestCount+1);
        response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("查询成功",resultMap)));
    }

    /**
     * 模糊查询地址
     * @param response
     * @param addr
     * @throws IOException
     */
    @RequestMapping("queryAddrInfo")
    public void queryAddrInfo(HttpServletResponse response,
                              @RequestParam("addr") String addr)throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        addr="%"+addr+"%";
        List<AddrInfo> resultList=hosDataService.queryAddrInfo(addr);
        if(resultList!=null&& !resultList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---区域信息查询成功:"+resultList);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("查询成功",resultList)));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---区域信息查询失败:");
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("查询失败")));
        }
    }

    /**
     * 传染病报告卡查询
     * @param response
     * @param serialNumber
     */
    @RequestMapping("queryReport")
    public void queryReport(HttpServletResponse response,String serialNumber) throws URISyntaxException,IOException{
        response.setContentType("application/json;charset=UTF-8");

        ReportCardBody body=hosDataService.queryReportCard(serialNumber);
        if(body==null){
            System.out.println(TimeUtil.GetTime(true)+" ---报告卡查询失败:"+serialNumber);
            response.getWriter().write(gson.toJson(WebServerResponse.failure("报告卡未存在")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---报告卡查询成功:"+body);
            response.getWriter().write(gson.toJson(WebServerResponse.success("报告卡查询成功",body)));
        }
    }

    /**
     * 创建报告卡
     * @param response
     * @param reportCardBody
     * @throws IOException
     */
    @RequestMapping("createReportCard")
    public void createReport(HttpServletResponse response, @RequestBody ReportCardBody reportCardBody)throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(TimeUtil.GetTime(true)+" ---报告卡创建请求参数:"+gsonConfig.toJson(reportCardBody));

        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("reportCardId",StringUtil.strNullToEmpty(reportCardBody.getReportCardId()));
        requestMap.put("reportCardType",StringUtil.strNullToEmpty(reportCardBody.getReportCardType()));
        requestMap.put("serialNumber",StringUtil.strNullToEmpty(reportCardBody.getSerialNumber()));
        requestMap.put("patientName",StringUtil.strNullToEmpty(reportCardBody.getPatientName()));
        switch(reportCardBody.getPatientGender()){
            case"男":
                requestMap.put("patientGender","1");
                break;
            case "女":
                requestMap.put("patientGender","2");
                break;
            default:
                requestMap.put("patientGender","0");
                break;
        }
        //requestMap.put("patientGender",StringUtil.strNullToEmpty(reportCardBody.getPatientGender()));
        String[] parts = reportCardBody.getPatientAge().matches("(\\d+)([\\u4E00-\\u9FFF]+)") ?
                reportCardBody.getPatientAge().replaceAll("(\\d+)([\\u4E00-\\u9FFF]+)", "$1 $2").split(" ") :
                new String[]{"", ""};
        String ageTemp=null,ageTypeTemp=null;
        if (parts.length == 2) {
            ageTemp=parts[0];
            ageTypeTemp=parts[1];
        } else {
            System.out.println("格式不匹配");
        }
        requestMap.put("patientAge",ageTemp);
        requestMap.put("patientAgeType",AgeUnitConverter.getValue(ageTypeTemp));
        requestMap.put("patientTel",StringUtil.strNullToEmpty(reportCardBody.getPatientTel()));
        requestMap.put("patientContract",StringUtil.strNullToEmpty(reportCardBody.getPatientContract()));
        requestMap.put("patientBirthday",StringUtil.strNullToEmpty(reportCardBody.getPatientBirthday()));
        requestMap.put("idCardType",StringUtil.strNullToEmpty(reportCardBody.getIdCardType()));
        requestMap.put("idCard",StringUtil.strNullToEmpty(reportCardBody.getIdCard()));
        requestMap.put("workUnit",StringUtil.strNullToEmpty(reportCardBody.getWorkUnit()));
        //requestMap.put("workUnit",(reportCardBody.getWorkUnit() != null) ? reportCardBody.getWorkUnit() : "");
        requestMap.put("currentAddrDetailed",StringUtil.strNullToEmpty(reportCardBody.getCurrentAddrDetailed()));
        requestMap.put("illnessTime",StringUtil.strNullToEmpty(reportCardBody.getIllnessTime()));
        requestMap.put("diagnosisTime",StringUtil.strNullToEmpty(reportCardBody.getDiagnosisTime()));
        requestMap.put("deathTime",StringUtil.strNullToEmpty(reportCardBody.getDeathTime()));
        requestMap.put("addrType",StringUtil.strNullToEmpty(reportCardBody.getAddrType()));
        requestMap.put("personType",StringUtil.strNullToEmpty(reportCardBody.getPersonType()));
        requestMap.put("personTypeOther",StringUtil.strNullToEmpty(reportCardBody.getPersonTypeOther()));
        requestMap.put("illnessType",StringUtil.strNullToEmpty(reportCardBody.getIllnessType()));
        requestMap.put("diagnosisType",StringUtil.strNullToEmpty(reportCardBody.getDiagnosisType()));
        requestMap.put("diseaseA",StringUtil.strNullToEmpty(reportCardBody.getDiseaseA()));
        requestMap.put("diseaseB",StringUtil.strNullToEmpty(reportCardBody.getDiseaseB()));
        requestMap.put("diseaseC",StringUtil.strNullToEmpty(reportCardBody.getDiseaseC()));
        requestMap.put("diseaseD",StringUtil.strNullToEmpty(reportCardBody.getDiseaseD()));
        requestMap.put("maritalStatus",StringUtil.strNullToEmpty(reportCardBody.getMaritalStatus()));
        requestMap.put("educationLevel",StringUtil.strNullToEmpty(reportCardBody.getEducationLevel()));
        requestMap.put("covid19Level",StringUtil.strNullToEmpty(reportCardBody.getCovid19Level()));
        requestMap.put("covid19Type",StringUtil.strNullToEmpty(reportCardBody.getCovid19Type()));
        requestMap.put("covid19OutHosTime",StringUtil.strNullToEmpty(reportCardBody.getCovid19OutHosTime()));
        requestMap.put("stdExposurePattern",StringUtil.strNullToEmpty(reportCardBody.getStdExposurePattern()));
        requestMap.put("stdExposureSource",StringUtil.strNullToEmpty(reportCardBody.getStdExposureSource()));
        requestMap.put("stdExposureType",StringUtil.strNullToEmpty(reportCardBody.getStdExposureType()));
        requestMap.put("stdExposureOrg",StringUtil.strNullToEmpty(reportCardBody.getStdExposureOrg()));
        requestMap.put("stdExposureTime",StringUtil.strNullToEmpty(reportCardBody.getStdExposureTime()));
        requestMap.put("stdExposureSample",StringUtil.strNullToEmpty(reportCardBody.getStdExposureSample()));
        requestMap.put("hepatitisBHBsAgTime",StringUtil.strNullToEmpty(reportCardBody.getHepatitisBHBsAgTime()));
        requestMap.put("hepatitisBIgM1",StringUtil.strNullToEmpty(reportCardBody.getHepatitisBIgM1()));
        requestMap.put("hepatitisBPunctureResult",StringUtil.strNullToEmpty(reportCardBody.getHepatitisBPunctureResult()));
        requestMap.put("hepatitisBHBsAgResult",StringUtil.strNullToEmpty(reportCardBody.getHepatitisBHBsAgResult()));
        requestMap.put("hepatitisBTime",StringUtil.strNullToEmpty(reportCardBody.getHepatitisBTime()));
        requestMap.put("hepatitisBAlt",StringUtil.strNullToEmpty(reportCardBody.getHepatitisBAlt()));
        requestMap.put("hfmDiseaseResult",StringUtil.strNullToEmpty(reportCardBody.getHfmDiseaseResult()));
        requestMap.put("hfmDiseaseLevel",StringUtil.strNullToEmpty(reportCardBody.getHfmDiseaseLevel()));
        requestMap.put("monkeyPoxResult",StringUtil.strNullToEmpty(reportCardBody.getMonkeyPoxResult()));
        requestMap.put("pertussisLevel",StringUtil.strNullToEmpty(reportCardBody.getPertussisLevel()));
        requestMap.put("intimateContactSymptom",StringUtil.strNullToEmpty(reportCardBody.getIntimateContactSymptom()));
        requestMap.put("updateDiseaseName",StringUtil.strNullToEmpty(reportCardBody.getUpdateDiseaseName()));
        requestMap.put("rollbackCardReason",StringUtil.strNullToEmpty(reportCardBody.getRollbackCardReason()));
        requestMap.put("reportOrg",StringUtil.strNullToEmpty(reportCardBody.getReportOrg()));
        requestMap.put("reportDoctor",StringUtil.strNullToEmpty(reportCardBody.getReportDoctor()));
        requestMap.put("reportDoctorTel",StringUtil.strNullToEmpty(reportCardBody.getReportDoctorTel()));
        requestMap.put("reportCreateTime",StringUtil.strNullToEmpty(reportCardBody.getReportCreateTime()));
        requestMap.put("remark",StringUtil.strNullToEmpty(reportCardBody.getRemark()));
        System.out.println(TimeUtil.GetTime(true)+" ---报告卡创建参数:"+requestMap);

        boolean createResult=hosDataService.createCReportCard(requestMap);
        if(createResult){
            System.out.println(TimeUtil.GetTime(true)+" ---报告卡创建成功:"+requestMap);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("报告卡创建成功")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---报告卡创建失败:"+requestMap);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("报告卡创建失败")));
        }
    }

    /**
     * 查询申请病理患者基本信息
     * @param response
     * @param serialNumber
     * @throws IOException
     */
    @RequestMapping("PathologyPatientInfo")
    public void queryPathology(HttpServletResponse response, @RequestParam("serialNumber") String serialNumber)throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(TimeUtil.GetTime(true)+" ---请求参数:"+serialNumber);
        PathologyPatientInfoBean responseBean=hosDataService.queryPathology(serialNumber);
        if(responseBean==null){
            System.out.println(TimeUtil.GetTime(true)+" ---获取失败_参数:"+serialNumber);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("查询失败")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---获取成功_参数:"+serialNumber+"--"+responseBean);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("查询成功",responseBean)));
        }
    }

    /*
    *
    * */
    @PostMapping("/upload/pdf")
    public void upLoadFile(HttpServletResponse response,
                                     @RequestParam("serialNumber") String serialNumber,
                                     @RequestParam("patientName") String patientName,
                                     @RequestParam("patientGender") String patientGender,
                                     @RequestParam("patientAge") String patientAge,
                                     @RequestParam("patientBedNum") String patientBedNum,
                                     @RequestParam("doctorName") String doctorName,
                                     @RequestParam("doctorDepartment") String doctorDepartment,
                                     @RequestParam("patientTel") String patientTel,
                                     @RequestParam("inspectionItem") String inspectionItem,
                                     @RequestParam("inspectionTime") String inspectionTime,
                                     @RequestParam("diagnose") String diagnose,
                                     @RequestParam("file") MultipartFile file)throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("serialNumber",serialNumber);
        requestMap.put("patientName",patientName);
        requestMap.put("patientGender",patientGender);
        requestMap.put("patientAge",patientAge);
        requestMap.put("patientBedNum",StringUtil.clearStr(patientBedNum));
        requestMap.put("doctorName",doctorName);
        requestMap.put("doctorDepartment",doctorDepartment);
        requestMap.put("patientTel",patientTel);
        requestMap.put("inspectionItem",inspectionItem);
        requestMap.put("inspectionTime",inspectionTime);
        requestMap.put("diagnose",diagnose);
        System.out.println(TimeUtil.GetTime(true)+" ---请求参数:"+requestMap);

        if(file.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---获取失败_参数:"+serialNumber);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("上传异常！文件缺失。。。")));
        }else{
            String originalFilename = file.getOriginalFilename();
            String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1).toLowerCase();//文件名后缀
            String fileName=serialNumber+"_"+patientName+"."+fileSuffix;
            System.out.println(TimeUtil.GetTime(true)+" ---文件名:"+fileName);
            boolean saveKey=FileUtil.writePdfFile(fileName,file);
            if(saveKey){
                requestMap.put("reportPath","/Uploads/"+fileName);

                boolean insertKey=pathologyService.createPathologyReport(requestMap);
                if(insertKey){
                    System.out.println(TimeUtil.GetTime(true)+" ---参数:"+requestMap);
                    response.getWriter().write(gsonConfig.toJson(WebServerResponse.success(patientName+"-病理报告上传成功")));
                }else{
                    System.out.println(TimeUtil.GetTime(true)+" ---参数:"+requestMap);
                    response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("上传异常-数据库写入异常!")));
                }
            }else{
                response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("上传异常-文件写入异常!")));
            }
        }
    }

    /**
     * 按科室查询病理报告
     * @param response
     * @param doctorDepartment
     * @throws IOException
     */
    @RequestMapping("PathologyReportByDepartment")
    public void queryPathologyByDepart(HttpServletResponse response,
                                       @RequestParam("doctorDepartment") String doctorDepartment)throws IOException{
        response.setContentType("application/json;charset=UTF-8");

        List<PathologyPatientInfoBean> reportBean=pathologyService.queryPathologyByDepart(doctorDepartment);
        if(reportBean==null){
            System.out.println(TimeUtil.GetTime(true)+" 病历报告查询异常---参数:"+doctorDepartment+null);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("病历报告查询异常!")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 病理报告查询成功---参数:"+doctorDepartment+reportBean);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("病理报告查询成功",reportBean)));
        }
    }

    /**
     * 管理员
     * @param response
     * @throws IOException
     */
    @RequestMapping("PathologyReport")
    public void queryPathologyReport(HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        List<PathologyPatientInfoBean> reportBean=pathologyService.queryPathologyReport();
        if(reportBean==null){
            System.out.println(TimeUtil.GetTime(true)+" 病历报告查询异常---参数:管理员"+null);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("病历报告查询异常!")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 病理报告查询成功---参数:管理员"+reportBean);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("病理报告查询成功",reportBean)));
        }
    }

    /**
     * 按月-科室统计
     * @param response
     * @throws IOException
     */
    @RequestMapping("statisticsByDepartMonth")
    public void statisticsByDepartMonth(HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=UTF-8");

        List<StatisticsBean> statisticsBeans=pathologyService.statisticsByDepartMonth();
        if(statisticsBeans==null|| statisticsBeans.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" 统计异常"+null);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("统计异常!")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 统计成功---参数:管理员"+statisticsBeans);

            // 使用Stream API进行分组和转换
            Map<String, List<Integer>> groupedData = statisticsBeans.stream()
                    .collect(Collectors.groupingBy(
                            StatisticsBean::getDoctorDepartment, // 按照科室分组
                            Collectors.mapping(
                                    StatisticsBean::getSNum, // 提取序列号计数
                                    Collectors.toList() // 收集到列表中
                            )
                    ));

            // 将分组后的数据转换为DepartmentStatistics对象列表
            List<DepartmentStatistics> departmentStatisticsList = new ArrayList<>();
            for (Map.Entry<String, List<Integer>> entry : groupedData.entrySet()) {
                departmentStatisticsList.add(new DepartmentStatistics(entry.getKey(), entry.getValue()));
            }
            departmentStatisticsList.forEach(System.out::println);

            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("统计成功",departmentStatisticsList)));
        }
    }


    @RequestMapping("RemoveReport")
    public void removeReport(HttpServletResponse response,
                             @RequestParam("serialNumber") String serialNumber) throws IOException{
        response.setContentType("application/json;charset=UTF-8");

        boolean removeKey=pathologyService.removeReport(serialNumber);
        if(removeKey){
            System.out.println(TimeUtil.GetTime(true)+" 删除成功_参数:"+serialNumber);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("报告删除成功:"+serialNumber)));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 删除异常_参数:"+serialNumber);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("报告删除异常!---"+serialNumber)));
        }
    }

    @RequestMapping("ReleaseYfClock")
    public void ReleaseYfClock(HttpServletResponse response,
                             @RequestParam("requestNum") String requestNum) throws IOException{
        response.setContentType("application/json;charset=UTF-8");

        boolean removeKey=hosDataService.releaseYfClock(requestNum);
        if(removeKey){
            System.out.println(TimeUtil.GetTime(true)+" 删除成功_参数:"+requestNum);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("报告删除成功:"+requestNum)));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 删除异常_参数:"+requestNum);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("删除异常!---"+requestNum)));
        }
    }
    @RequestMapping("QueryBedDay")
    public void queryBedDay(HttpServletResponse response,@RequestBody BedDayBody params) throws IOException{
        System.out.println(TimeUtil.GetTime(true)+" 参数:"+params);
        response.setContentType("application/json;charset=UTF-8");
        params.setStartTime(params.getStartTime()+"00:00:00");
        params.setEndTime(params.getEndTime()+"23:59:59");
        List<BedDayBean> queryList=hosDataService.QueryBedDay(params);
        if(queryList==null||queryList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" 查询失败_参数:"+params.toString());
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("查询失败",queryList)));
        }else{
            System.out.println(TimeUtil.GetTime(true)+" 查询成功_参数:"+params.toString());
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("查询成功",queryList)));
        }
    }



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
    @RequestMapping("/testAddr")
    public void testAddr(HttpServletResponse response,
                     @RequestParam("addr") String addr) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String result=MatchAddrUtil.matchAddr(addr);

//        String regex = "(?<province>[^省]+省)" +
//                "(?<city>[^市]+市)?" +
//                "(?<county>[^县]+县)?" +
//                "(?<township>([^镇]+镇|[^乡]+乡)|" + // 镇或乡优先
//                "([^区]+区|[^州]+州)(?![^市]+市))|" + // 如果没有镇或乡，则取区或州（但不包括后面的市）
//                "(?<cityWithoutLower>([^市]+市)(?![^县]+县|[^区]+区|[^镇]+镇|[^乡]+乡))";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(addr);
//
//        if (matcher.find()) {
//            String province = matcher.group("province");
//            String county = matcher.group("county");
//            String township = matcher.group("township");
//            String cityWithoutLower = matcher.group("cityWithoutLower");
//
//            if (township != null && !township.isEmpty()) {
//                // 如果匹配到了镇或乡，则直接返回
//                result=township;
//            } else if (cityWithoutLower != null && !cityWithoutLower.isEmpty() &&
//                    (matcher.group("county") == null || matcher.group("county").isEmpty())) {
//                /**
//                 * 如果没有匹配到镇或乡，但匹配到了市且后面没有县、区、镇、乡，则返回市
//                 * 注意：这里假设市后面直接跟的是我们想要的级别，而不是更低级别的行政区划
//                 * 这可能需要根据实际情况进行调整
//                 * return cityWithoutLower.replaceAll("市$", ""); // 去掉市字，只返回名称部分
//                 * */
//                result=cityWithoutLower;
//            } else {
//                /**
//                 * 如果上述都没有匹配到
//                 */
//                result="";
//            }
//        } else {
//            result="";
//        }
        if(StringUtil.isEmptyOrNull(result)){
            System.out.println(TimeUtil.GetTime(true)+"  住址截取失败:"+"匹配地址失败");
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.failure("匹配失败")));
        }else{
            System.out.println(TimeUtil.GetTime(true)+"  住址截取成功:"+result);
            response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("请求成功",result)));
        }
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

    /**
     * 参数处理
     * @param data
     * @return
     * @throws URISyntaxException
     */
    private Map<String,Object> setReportParamMap(PatientInfReport data) throws URISyntaxException {
        Map<String,Object> tempMap=new HashMap<>();
        tempMap.put("id",data.getId());
        tempMap.put("patient_id",data.getPatient_id());
        tempMap.put("serial_number",data.getSerial_number());
        tempMap.put("activity_type_code",data.getActivity_type_code());
        tempMap.put("activity_type_name",data.getActivity_type_name());
        tempMap.put("patient_name",data.getPatient_name());
        tempMap.put("id_card_type_code",data.getCase_type_code());
        tempMap.put("id_card_type_name",data.getId_card_type_name());
        tempMap.put("id_card",data.getId_card());
        tempMap.put("gender_code",data.getGender_code());
        tempMap.put("gender_name",data.getGender_name());
        tempMap.put("birth_date",data.getBirth_date());
        tempMap.put("nationality_code",data.getNationality_code());
        tempMap.put("nationality_name",data.getNationality_name());
        tempMap.put("nation_code",data.getNation_code());
        tempMap.put("nation_name",data.getNation_name());
        tempMap.put("permanent_addr_code",data.getPermanent_addr_code());
        tempMap.put("permanent_addr_name",data.getPermanent_addr_name());
        tempMap.put("permanent_addr_detail",data.getPermanent_addr_detail());
        System.out.println(TimeUtil.GetTime(true)+"  住址截取-前:"+data.getCurrent_addr_detail());
        String currentAddr=MatchAddrUtil.matchAddr(data.getCurrent_addr_detail());
        System.out.println(TimeUtil.GetTime(true)+"  住址截取:"+currentAddr);
        String currentAddrCode=hosDataService.queryAddrCode(MatchAddrUtil.matchAddr(data.getCurrent_addr_detail()));
        System.out.println(TimeUtil.GetTime(true)+"  住址截取编码:"+currentAddrCode);
        tempMap.put("current_addr_code",currentAddrCode);
        tempMap.put("current_addr_name",currentAddr);
        tempMap.put("current_addr_detail",data.getCurrent_addr_detail());
        tempMap.put("workunit",data.getWorkunit());
        tempMap.put("marital_status_code",data.getMarital_status_code());
        tempMap.put("education_code",data.getEducation_code());
        tempMap.put("education_name",data.getEducation_name());
        tempMap.put("nultitude_type_code",data.getNultitude_type_code());
        tempMap.put("nultitude_type_name",data.getNultitude_type_name());
        tempMap.put("nultitude_type_other",data.getNultitude_type_other());
        tempMap.put("tel",data.getTel());
        tempMap.put("contacts",data.getContacts());
        tempMap.put("contacts_tel",data.getContacts_tel());
        tempMap.put("onset_date",data.getOnset_date());
        tempMap.put("diagnose_time",data.getDiagnose_time());
        String disease_code=StringUtil.getSubstringBeforeFirstComma(data.getDisease_code());
        tempMap.put("disease_code",disease_code);
        tempMap.put("disease_name",data.getDisease_name());
        tempMap.put("disease_other",data.getDisease_other());
        tempMap.put("diagnose_state_code",data.getDiagnose_state_code());
        tempMap.put("diagnose_state_name",data.getDiagnose_state_name());
        tempMap.put("case_type_code",data.getCase_type_code());
        tempMap.put("case_type_name",data.getCase_type_name());
        tempMap.put("dead_date",data.getDead_date());
        tempMap.put("is_dead_by_this_code",data.getIs_dead_by_this_code());
        tempMap.put("is_dead_by_this_name",data.getIs_dead_by_this_name());
        tempMap.put("symptoms_code",data.getSymptoms_code());
        tempMap.put("symptoms_name",data.getSymptoms_name());
        tempMap.put("laboratory_detection_verdict_code",data.getVerdict_code());
        tempMap.put("laboratory_detection_verdict_name",data.getVerdict_name());
        tempMap.put("detection_positive_date",data.getDetection_positive_date());
        tempMap.put("detection_org_code",data.getDetection_org_code());
        tempMap.put("dt_diagnose",data.getDt_diagnose());
        tempMap.put("afp_areatype1_code",data.getAfp_areatype1_code());
        tempMap.put("afp_areatype1name",data.getAfp_areatype1name());
        tempMap.put("afp_palsy_date",data.getAfp_palsy_date());
        tempMap.put("afp_doctor_date",data.getAfp_doctor_date());
        tempMap.put("afp_areatype2_code",data.getAfp_areatype2_code());
        tempMap.put("afp_areatype2_name",data.getAfp_areatype2_name());
        tempMap.put("afp_addrcode_code",data.getAfp_addrcode_code());
        tempMap.put("afp_addrcode_name",data.getAfp_addrcode_name());
        tempMap.put("afp_addr",data.getAfp_addr());
        tempMap.put("afp_palsy_symptom",data.getAfp_palsy_symptom());
        tempMap.put("report_date",data.getReport_date());
        tempMap.put("discovery_mode_code",data.getDiscovery_mode_code());
        tempMap.put("discovery_mode_name",data.getDiscovery_mode_name());
        tempMap.put("discovery_mode_other",data.getDiscovery_mode_other());
        tempMap.put("venereal_dis_code",data.getVenereal_dis_code());
        tempMap.put("venereal_dis_name",data.getVenereal_dis_name());
        tempMap.put("bs_transmission_code",data.getBs_transmission_code());
        tempMap.put("bs_transmission_name",data.getBs_transmission_name());
        tempMap.put("bs_transmission_other",data.getBs_transmission_other());
        tempMap.put("contact_type_code",data.getContact_type_code());
        tempMap.put("contact_type_name",data.getContact_type_name());
        tempMap.put("inject_count",data.getInject_count());
        tempMap.put("nonweb_count",data.getNonweb_count());
        tempMap.put("sm_count",data.getSm_count());
        tempMap.put("contact_other",data.getContact_other());
        tempMap.put("sinfect_code",data.getSinfect_code());
        tempMap.put("sinfect_name",data.getSinfect_name());
        tempMap.put("serverity_code",data.getServerity_code());
        tempMap.put("serverity_name",data.getServerity_name());
        tempMap.put("lab_result_code",data.getLab_result_code());
        tempMap.put("lab_result_name",data.getLab_result_name());
        tempMap.put("hbsag_code",data.getHbsag_code());
        tempMap.put("hbsag_name",data.getHbsag_name());
        tempMap.put("hbsag_first",data.getHbsag_first());
        tempMap.put("hbsag_buxiang",data.getHbsag_buxiang());
        tempMap.put("hbsag_alt",data.getHbsag_alt());
        tempMap.put("hbcig_result_code",data.getHbcig_result_code());
        tempMap.put("hbcig_result_name",data.getHbcig_result_name());
        tempMap.put("hbliver_puncture_code",data.getHbliver_puncture_code());
        tempMap.put("hbliver_puncture_name",data.getHbliver_puncture_name());
        tempMap.put("hbsag_changename",data.getHbsag_changename());
        tempMap.put("contactflag_code",data.getContactflag_code());
        tempMap.put("contactflag_name",data.getContactflag_name());
        tempMap.put("fill_doctor",data.getFill_doctor());
        tempMap.put("notes",data.getNotes());
        tempMap.put("ncv_severity_code",data.getNcv_severity_code());
        tempMap.put("ncv_severity_name",data.getNcv_severity_name());
        tempMap.put("foreign_type_code",data.getForeign_type_code());
        tempMap.put("foreign_type_name",data.getForeign_type_name());
        tempMap.put("place_code",data.getPlace_code());
        tempMap.put("place_name",data.getPlace_name());
        tempMap.put("report_zone_code",data.getReport_zone_code());
        tempMap.put("report_zone_name",data.getReport_zone_name());
        tempMap.put("report_org_code",data.getReport_org_code());
        tempMap.put("report_org_name",data.getReport_org_name());
        tempMap.put("dept_code",data.getDept_code());
        tempMap.put("dept_name",data.getDept_name());
        tempMap.put("operator_id",data.getOperator_id());
        tempMap.put("operation_time",data.getOperation_time());
        return tempMap;
    }


    /***********************公共逻辑********************/
}
