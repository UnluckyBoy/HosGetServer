package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.PathologyPatientInfoBean;
import com.cloudestudio.hosgetserver.model.ReportQueryPatientBaseInfo;
import com.cloudestudio.hosgetserver.model.StatisticsBean;
import com.cloudestudio.hosgetserver.model.mapper.PathologyMapper;
import com.cloudestudio.hosgetserver.service.PathologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class PathologyServiceImpl
 * @Author Create By Matrix·张
 * @Date 2025/1/20 下午5:22
 * 病理接口实现类
 */
@Service("PathologyService")
public class PathologyServiceImpl implements PathologyService {
    @Autowired
    PathologyMapper pathologyMapper;

    @DS("mysql")
    @Override
    public boolean createPathologyReport(Map<String, Object> map) {
        return pathologyMapper.createPathologyReport(map);
    }

    @DS("mysql")
    @Override
    public boolean removeReport(String serialNumber) {
        return pathologyMapper.removeReport(serialNumber);
    }

    @DS("mysql")
    @Override
    public List<PathologyPatientInfoBean> queryPathologyByDepart(String department) {
        return pathologyMapper.queryPathologyByDepart(department);
    }

    @DS("mysql")
    @Override
    public List<PathologyPatientInfoBean> queryPathologyReport() {
        return pathologyMapper.queryPathologyReport();
    }

    @DS("mysql")
    @Override
    public List<StatisticsBean> statisticsByDepartMonth() {
        return pathologyMapper.statisticsByDepartMonth();
    }
}
