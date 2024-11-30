package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.MedicineOrderBean;
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
    List<MedicineOrderBean> queryOrderOutWareHouse();
    MedicineOrderBean queryCurrentDaySell();
    MedicineOrderBean queryYesterdaySell();//昨日销售金额
    MedicineOrderBean queryLastWeekSell();//上周销售金额
    MedicineOrderBean queryLastMonthSell();//上月售金额
    MedicineOrderBean queryCurrentYearSell();//全年售金额
    MedicineOrderBean queryAllSell();//总售金额

    boolean upOrderOutWareHouse(Map<String,Object> map);
}
