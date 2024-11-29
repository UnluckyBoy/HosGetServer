package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.OrderBean;

import java.util.List;
import java.util.Map;

/**
 * @Class OrderService
 * @Author Create By Matrix·张
 * @Date 2024/11/29 下午1:21
 * 订单服务接口类
 */
public interface OrderService {
    List<OrderBean> queryOrderOutWareHouse();
    boolean upOrderOutWareHouse(Map<String,Object> map);
}
