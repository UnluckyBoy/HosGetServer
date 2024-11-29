package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.OrderBean;
import com.cloudestudio.hosgetserver.model.mapper.OrderMapper;
import com.cloudestudio.hosgetserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class OrderServiceImpl
 * @Author Create By Matrix·张
 * @Date 2024/11/29 下午1:22
 * 订单服务实现类
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @DS("mysql")
    @Override
    public List<OrderBean> queryOrderOutWareHouse() {
        return orderMapper.queryOrderOutWareHouse();
    }

    @Override
    public boolean upOrderOutWareHouse(Map<String, Object> map) {
        return orderMapper.upOrderOutWareHouse(map);
    }
}
