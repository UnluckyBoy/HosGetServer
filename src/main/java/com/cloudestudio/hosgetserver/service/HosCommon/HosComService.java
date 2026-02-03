package com.cloudestudio.hosgetserver.service.HosCommon;

import com.cloudestudio.hosgetserver.model.ReportBean.WorkInfoBean;
import com.cloudestudio.hosgetserver.model.ReportBean.WorkTotalBean;
import com.cloudestudio.hosgetserver.webTools.WebResponse;

import java.util.List;
import java.util.Map;

/**
 * @Class HosComService
 * @Author Create By Matrix·张
 * @Date 2026/1/28 上午11:02
 * 服务公共类
 */
public interface HosComService {
    WebResponse createWorkInfo(WorkInfoBean workInfoBean);
    WebResponse queryWorkInfos();
    WebResponse queryNearWorks();
    WebResponse freshWorkStatus(Map<String,String> map);
    WebResponse queryWorkContent(int workId);
    WebResponse queryAllWorks();
    WebResponse queryWorkStatusTotal();
    WebResponse queryWorkTypeTotal();
    WebResponse queryWorkComplete();
    WebResponse queryWorkCompleteInterval();
}
