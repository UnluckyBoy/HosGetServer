package com.cloudestudio.hosgetserver.service.HosCommon;

import com.cloudestudio.hosgetserver.model.ReportBean.WorkInfoBean;
import com.cloudestudio.hosgetserver.model.ReportBean.WorkNearBean;
import com.cloudestudio.hosgetserver.model.ReportBean.WorkNums;
import com.cloudestudio.hosgetserver.service.HosDataService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class HosComServiceImpl
 * @Author Create By Matrix·张
 * @Date 2026/1/28 上午11:03
 * 公共服务实现
 */
@Service("HosComService")
public class HosComServiceImpl implements HosComService{
    @Autowired
    HosDataService hosDataService;

    /**
     * 创建工单
     * @param map
     * @return
     */
    @Override
    public WebResponse createWorkInfo(Map<String, Object> map) {
        if(map.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---创建工单--->>>参数为空");
            return WebResponse.queryZeroResult(null);
        }
        System.out.println(TimeUtil.GetTime(true)+" ---创建工单--->>>参数:"+ map);
        boolean result=hosDataService.createWorkInfo(map);
        if(result){
            System.out.println(TimeUtil.GetTime(true)+" ---创建工单---成功--->>>参数:"+ map+"--->结果:"+map);
            return WebResponse.success();
        }else{
            System.out.println(TimeUtil.GetTime(true)+" ---创建工单---异常");
            return WebResponse.failure();
        }
    }

    /**
     * 查询全部工单
     * @return
     */
    @Override
    public WebResponse queryWorkInfos() {
        List<WorkInfoBean> result=hosDataService.queryWorkInfos();
        if(!result.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---查询工单---成功");
            return WebResponse.success(result);
        }
        System.out.println(TimeUtil.GetTime(true)+" ---查询工单---异常");
        return WebResponse.failure();
    }

    /***
     * 查询近七日
     * @return
     */
    @Override
    public WebResponse queryNearWorks() {
        List<WorkNearBean> result=hosDataService.queryNearWorks();
        if(!result.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---查询工单---成功"+result);
            return WebResponse.success(result);
        }
        System.out.println(TimeUtil.GetTime(true)+" ---查询工单---异常");
        return WebResponse.failure();
    }

    @Override
    public WebResponse freshWorkStatus(Map<String,String> map) {
        if(map.isEmpty()){
            System.out.println(TimeUtil.GetTime(true)+" ---参数异常："+map);
            return WebResponse.paramError();
        }
        boolean result=hosDataService.freshWorkStatus(map);
        if(result){
            System.out.println(TimeUtil.GetTime(true)+" ---更新工单---成功--->参数："+map);
            return WebResponse.success();
        }
        System.out.println(TimeUtil.GetTime(true)+" ---更新工单---失败--->参数："+map);
        return WebResponse.failure();
    }

    @Override
    public WebResponse queryWorkContent(int workId) {
        if (workId<=0){
            return WebResponse.paramError();
        }
        WorkInfoBean result=hosDataService.queryWorkContent(workId);
        if(result!=null){
            System.out.println(TimeUtil.GetTime(true)+" ---查询工单内容-成功--->参数："+workId);
            return WebResponse.success(result);
        }
        System.out.println(TimeUtil.GetTime(true)+" ---查询工单内容-失败--->参数："+workId);
        return WebResponse.failure();
    }
}
