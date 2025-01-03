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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("createReportCard")
    public void createReport(HttpServletResponse response,
                             @RequestParam("reportCardId") String reportCardId,@RequestParam("reportCardType") String reportCardType,
                             @RequestParam("serialNumber") String serialNumber,@RequestParam("patientName") String patientName,
                             @RequestParam("patientGender") String patientGender,@RequestParam("patientAge") String patientAge,
                             @RequestParam("patientTel") String patientTel,@RequestParam("patientContract") String patientContract,
                             @RequestParam("patientBirthday") String patientBirthday,@RequestParam("idCardType") String idCardType,
                             @RequestParam("idCard") String idCard,@RequestParam("workUnit") String workUnit,
                             @RequestParam("currentAddrDetailed") String currentAddrDetailed,@RequestParam("illnessTime") String illnessTime,
                             @RequestParam("diagnosisTime") String diagnosisTime,@RequestParam("deathTime") String deathTime,
                             @RequestParam("addrType") String addrType,@RequestParam("personType") String personType,
                             @RequestParam("illnessType") String illnessType,@RequestParam("diagnosisType") String diagnosisType,
                             @RequestParam("diseaseA") String diseaseA,@RequestParam("diseaseB") String diseaseB,@RequestParam("diseaseC") String diseaseC,
                             @RequestParam("diseaseD") String diseaseD,@RequestParam("maritalStatus") String maritalStatus,@RequestParam("educationLevel") String educationLevel,
                             @RequestParam("covid19Level") String covid19Level,@RequestParam("covid19Type") String covid19Type, @RequestParam("covid19OutHosTime") String covid19OutHosTime,
                             @RequestParam("stdExposurePattern") String stdExposurePattern,@RequestParam("stdExposureSource") String stdExposureSource,
                             @RequestParam("stdExposureType") String stdExposureType,@RequestParam("stdExposureOrg") String stdExposureOrg,
                             @RequestParam("stdExposureTime") String stdExposureTime,@RequestParam("stdExposureSample") String stdExposureSample,
                             @RequestParam("hepatitisBHBsAgTime") String hepatitisBHBsAgTime,@RequestParam("hepatitisBIgM1") String hepatitisBIgM1,
                             @RequestParam("hepatitisBPunctureResult") String hepatitisBPunctureResult,@RequestParam("hepatitisBHBsAgResult") String hepatitisBHBsAgResult,
                             @RequestParam("hepatitisBTime") String hepatitisBTime,@RequestParam("hepatitisBAlt") String hepatitisBAlt,
                             @RequestParam("hfmDiseaseResult") String hfmDiseaseResult,@RequestParam("hfmDiseaseLevel") String hfmDiseaseLevel,
                             @RequestParam("monkeyPoxResult") String monkeyPoxResult,@RequestParam("pertussisLevel") String pertussisLevel,
                             @RequestParam("intimateContactSymptom") String intimateContactSymptom,@RequestParam("updateDiseaseName") String updateDiseaseName,
                             @RequestParam("rollbackCardReason") String rollbackCardReason,@RequestParam("reportOrg") String reportOrg,
                             @RequestParam("reportDoctor") String reportDoctor,@RequestParam("reportDoctorTel") String reportDoctorTel,
                             @RequestParam("reportCreateTime") String reportCreateTime,@RequestParam("remark") String remark)throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("reportCardId",reportCardId);
        requestMap.put("reportCardType",reportCardType);
        requestMap.put("serialNumber",serialNumber);
        requestMap.put("patientName",patientName);
        requestMap.put("patientGender",patientGender);
        String[] parts = patientAge.matches("(\\d+)([\\u4E00-\\u9FFF]+)") ?
                patientAge.replaceAll("(\\d+)([\\u4E00-\\u9FFF]+)", "$1 $2").split(" ") :
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
        requestMap.put("patientTel",patientTel);
        requestMap.put("patientContract",patientContract);
        requestMap.put("patientBirthday",patientBirthday);
        requestMap.put("idCardType",idCardType);
        requestMap.put("idCard",idCard);
        requestMap.put("workUnit",workUnit);
        requestMap.put("currentAddrDetailed",currentAddrDetailed);
        requestMap.put("illnessTime",illnessTime);
        requestMap.put("diagnosisTime",diagnosisTime);
        requestMap.put("deathTime",deathTime);
        requestMap.put("addrType",addrType);
        requestMap.put("personType",personType);
        requestMap.put("personTypeOther","");
        requestMap.put("illnessType",illnessType);
        requestMap.put("diagnosisType",diagnosisType);
        requestMap.put("diseaseA",diseaseA);
        requestMap.put("diseaseB",diseaseB);
        requestMap.put("diseaseC",diseaseC);
        requestMap.put("diseaseD",diseaseD);
        requestMap.put("maritalStatus",maritalStatus);
        requestMap.put("educationLevel",educationLevel);
        requestMap.put("covid19Level",covid19Level);
        requestMap.put("covid19Type",covid19Type);
        requestMap.put("covid19OutHosTime",covid19OutHosTime);
        requestMap.put("stdExposurePattern",stdExposurePattern);
        requestMap.put("stdExposureSource",stdExposureSource);
        requestMap.put("stdExposureType",stdExposureType);
        requestMap.put("stdExposureOrg",stdExposureOrg);
        requestMap.put("stdExposureTime",stdExposureTime);
        requestMap.put("stdExposureSample",stdExposureSample);
        requestMap.put("hepatitisBHBsAgTime",hepatitisBHBsAgTime);
        requestMap.put("hepatitisBIgM1",hepatitisBIgM1);
        requestMap.put("hepatitisBPunctureResult",hepatitisBPunctureResult);
        requestMap.put("hepatitisBHBsAgResult",hepatitisBHBsAgResult);
        requestMap.put("hepatitisBTime",hepatitisBTime);
        requestMap.put("hepatitisBAlt",hepatitisBAlt);
        requestMap.put("hfmDiseaseResult",hfmDiseaseResult);
        requestMap.put("hfmDiseaseLevel",hfmDiseaseLevel);
        requestMap.put("monkeyPoxResult",monkeyPoxResult);
        requestMap.put("pertussisLevel",pertussisLevel);
        requestMap.put("intimateContactSymptom",intimateContactSymptom);
        requestMap.put("updateDiseaseName",updateDiseaseName);
        requestMap.put("rollbackCardReason",rollbackCardReason);
        requestMap.put("reportOrg",reportOrg);
        requestMap.put("reportDoctor",reportDoctor);
        requestMap.put("reportDoctorTel",reportDoctorTel);
        requestMap.put("reportCreateTime",reportCreateTime);
        requestMap.put("remark",remark);
        System.out.println(TimeUtil.GetTime(true)+" ---报告卡创建请求参数:"+requestMap);

        response.getWriter().write(gsonConfig.toJson(WebServerResponse.success("报告卡创建成功")));
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
