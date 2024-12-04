package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.MedicineOrderBean;
import com.cloudestudio.hosgetserver.model.MonthCountBean;
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
    public List<MedicineOrderBean> queryOrderOutWareHouse() {
        return orderMapper.queryOrderOutWareHouse();
    }

    @DS("mysql")
    @Override
    public List<MedicineOrderBean> queryCurrentDaySell() {
        return orderMapper.queryCurrentDaySell();
    }

    @DS("mysql")
    @Override
    public List<MedicineOrderBean> queryYesterdaySell() {
        return orderMapper.queryYesterdaySell();
    }

    @DS("mysql")
    @Override
    public List<MedicineOrderBean> queryLastWeekSell() {
        return orderMapper.queryLastWeekSell();
    }

    @DS("mysql")
    @Override
    public List<MedicineOrderBean> queryLastMonthSell() {
        return orderMapper.queryLastMonthSell();
    }

    @DS("mysql")
    @Override
    public List<MedicineOrderBean> queryCurrentYearSell() {
        return orderMapper.queryCurrentYearSell();
    }

    @DS("mysql")
    @Override
    public List<MedicineOrderBean> queryAllSell() {
        return orderMapper.queryAllSell();
    }

    @DS("mysql")
    @Override
    public List<MedicineOrderBean> querySellOrderInfo() {
        return orderMapper.querySellOrderInfo();
    }

    @Override
    public List<MedicineOrderBean> queryUnSellOrderInfo() {
        return orderMapper.queryUnSellOrderInfo();
    }

    @DS("mysql")
    @Override
    public OrderBean queryCurrentDayAmount() {
        return orderMapper.queryCurrentDayAmount();
    }

    @DS("mysql")
    @Override
    public OrderBean queryYesterdayAmount() {
        return orderMapper.queryYesterdayAmount();
    }

    @DS("mysql")
    @Override
    public OrderBean queryLastWeekAmount() {
        return orderMapper.queryLastWeekAmount();
    }

    @DS("mysql")
    @Override
    public OrderBean queryLastMonthAmount() {
        return orderMapper.queryLastMonthAmount();
    }

    @DS("mysql")
    @Override
    public OrderBean queryCurrentYearAmount() {
        return orderMapper.queryCurrentYearAmount();
    }

    @DS("mysql")
    @Override
    public OrderBean queryAllAmount() {
        return orderMapper.queryAllAmount();
    }

    @DS("mysql")
    @Override
    public List<MonthCountBean> queryMonthAllOrder() {
        return orderMapper.queryMonthAllOrder();
    }

    @DS("mysql")
    @Override
    public boolean upOrderOutWareHouse(Map<String, Object> map) {
        return orderMapper.upOrderOutWareHouse(map);
    }
}
