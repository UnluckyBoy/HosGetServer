package com.cloudestudio.hosgetserver.service.GreenChannel;

import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.model.paramBody.GreenChannelCreateParamBody;
import com.cloudestudio.hosgetserver.webTools.WebResponse;

/**
 * @Class CreateCaseHisService
 * @Author Create By Matrix·张
 * @Date 2025/6/15 下午10:13
 * 分诊上传服务
 */
public interface GreenChannelService {
    WebResponse queryPatientInfo(String idCard);
    WebResponse createPatientInfo(GreenChannelCreateParamBody requestBody);
    WebResponse queryAllHis();
    WebResponse queryHisParam(BedDayBody requestBody);
}
