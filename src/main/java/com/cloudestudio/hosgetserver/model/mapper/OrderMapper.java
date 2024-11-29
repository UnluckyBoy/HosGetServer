package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.OrderBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class OrderMapper
 * @Author Create By Matrix·张
 * @Date 2024/11/29 下午1:18
 * 订单Mapper接口
 */
@Service
@Mapper
@Repository
public interface OrderMapper {
    List<OrderBean> queryOrderOutWareHouse();//销售未出库

    boolean upOrderOutWareHouse(Map<String,Object> map);//销售出库
}
