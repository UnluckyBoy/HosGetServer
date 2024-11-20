package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.HosDataBean;
import com.cloudestudio.hosgetserver.model.UserInfoBean;
import com.cloudestudio.hosgetserver.model.mapper.HosDataMapper;
import com.cloudestudio.hosgetserver.model.mapper.UserMapper;
import com.cloudestudio.hosgetserver.service.HosDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Matrix·张
 * @Date 2024/11/13 下午1:56
 */
@Service("HosDataService")
public class HosDataServiceImpl implements HosDataService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    HosDataMapper hosDataMapper;

    @DS("oracle")
    @Override
    public List<HosDataBean> queryInHospital_consultation(String startTime,String endTime) {
        return hosDataMapper.queryInHospital_consultation(startTime,endTime);
    }

    @DS("mysql")
    @Override
    public UserInfoBean loginQuery(Map<String, Object> map) {
        return userMapper.loginQuery(map);
    }
}
