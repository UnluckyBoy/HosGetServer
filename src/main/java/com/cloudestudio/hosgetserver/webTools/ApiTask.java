package com.cloudestudio.hosgetserver.webTools;

import com.cloudestudio.hosgetserver.model.PatientActivityBean;
import com.cloudestudio.hosgetserver.model.PatientBaseInfoBean;
import com.cloudestudio.hosgetserver.service.HosDataService;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Class ApiTask
 * @Author Create By Matrix·张
 * @Date 2024/12/15 下午3:47
 * 任务管理工具
 */
public class ApiTask implements Runnable {
    private final String apiUrl;
    private final HosDataService hosDataService;
    private final Gson gsonConfig;
    private final Gson gson;

    public ApiTask(String apiUrl, HosDataService hosDataService, Gson gsonConfig, Gson gson) {
        this.apiUrl = apiUrl;
        this.hosDataService = hosDataService;
        this.gsonConfig = gsonConfig;
        this.gson = gson;
    }

    @Override
    public void run() {
        try {
            List<PushResponse> responses = pushHandle(apiUrl);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private List<PushResponse> pushHandle(String url) throws IOException, URISyntaxException {
        String timeTemp="上传时间:"+TimeUtil.GetTime(true)+"\n";
        switch (getUrlName(url)){
            case "emrPatientInfo":
                List<PatientBaseInfoBean> emrPatientInfoList=hosDataService.queryPatientBaseInfoList();
                if(emrPatientInfoList!=null&& !emrPatientInfoList.isEmpty()){
                    System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息成功:"+emrPatientInfoList);
                    System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息成功--数据处理:"+gsonConfig.toJson(emrPatientInfoList));
                    StringBuilder result= new StringBuilder();
                    List<PushResponse> resultList=new ArrayList<PushResponse>();
                    for (PatientBaseInfoBean data : emrPatientInfoList) {
                        PushResponse pushResult=HttpClientUtil.pushDataInfo(url,gsonConfig.toJson(data));
                        System.out.println(TimeUtil.GetTime(true)+" ---上报结果:"+gson.toJson(pushResult));
                        result.append(gson.toJson(pushResult)).append("\n");
                        resultList.add(pushResult);
                    }
                    boolean writeFile=FileUtil.writeLogFile(timeTemp+result,getUrlName(url)+"_"+TimeUtil.timeToString(TimeUtil.GetTime(false)+".log"));
                    if(writeFile){
                        return resultList;
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            case "emrActivityInfo":
                List<PatientActivityBean> emrActivityInfoList=hosDataService.queryEmrActivityInfo();
                if(emrActivityInfoList!=null&& !emrActivityInfoList.isEmpty()){
                    System.out.println(TimeUtil.GetTime(true)+" ---查询患者门诊信息成功:"+emrActivityInfoList);
                    System.out.println(TimeUtil.GetTime(true)+" ---查询患者门诊信息成功--数据处理:"+gsonConfig.toJson(emrActivityInfoList));
                    StringBuilder result= new StringBuilder();
                    List<PushResponse> resultList=new ArrayList<PushResponse>();
                    for (PatientActivityBean data : emrActivityInfoList) {
                        PushResponse pushResult=HttpClientUtil.pushDataInfo(url,gsonConfig.toJson(data));
                        System.out.println(TimeUtil.GetTime(true)+" ---上报门诊信息结果:"+gson.toJson(pushResult));
                        result.append(gson.toJson(pushResult)).append("\n");
                        resultList.add(pushResult);
                    }
                    boolean writeFile=FileUtil.writeLogFile(timeTemp+result,getUrlName(url)+"_"+TimeUtil.timeToString(TimeUtil.GetTime(false)+".log"));
                    if(writeFile){
                        return resultList;
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            default:
                return null;
        }
    }

    private String getUrlName(String apiUrl) throws URISyntaxException {
        return new URI(apiUrl).getPath().split("/")[new URI(apiUrl).getPath().split("/").length - 1];
    }
}
