package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.PathologyPatientInfoBean;
import com.cloudestudio.hosgetserver.model.ReportQueryPatientBaseInfo;
import com.cloudestudio.hosgetserver.model.StatisticsBean;

import java.util.List;
import java.util.Map;

/**
 * @Class PathologyService
 * @Author Create By Matrix·张
 * @Date 2025/1/20 下午5:22
 * 病理服务接口
 */
public interface PathologyService {
    boolean createPathologyReport(Map<String,Object> map);
    boolean removeReport(String serialNumber);

    List<PathologyPatientInfoBean> queryPathologyByDepart(String department);
    List<PathologyPatientInfoBean> queryPathologyReport();
    List<StatisticsBean> statisticsByDepartMonth();
}
