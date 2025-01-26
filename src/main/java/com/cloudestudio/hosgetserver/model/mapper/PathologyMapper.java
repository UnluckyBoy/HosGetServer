package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.PathologyPatientInfoBean;
import com.cloudestudio.hosgetserver.model.ReportQueryPatientBaseInfo;
import com.cloudestudio.hosgetserver.model.StatisticsBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class PathologyMapper
 * @Author Create By Matrix·张
 * @Date 2025/1/20 下午5:11
 * 病理报告数据接口
 */
@Service
@Mapper
@Repository
public interface PathologyMapper {
    boolean createPathologyReport(Map<String,Object> map);//创建病理报告
    boolean removeReport(String serialNumber);//删除报告

    List<PathologyPatientInfoBean> queryPathologyByDepart(String department);//按科室查询病历报告
    List<PathologyPatientInfoBean> queryPathologyReport();//查询病历报告
    List<StatisticsBean> statisticsByDepartMonth();//以月-科室为单位查询统计
}
