package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.MedicineOrderBean;
import com.cloudestudio.hosgetserver.model.MonthCountBean;
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
    List<MedicineOrderBean> queryCurrentDaySell();
    List<MedicineOrderBean> queryYesterdaySell();//昨日销售金额
    List<MedicineOrderBean> queryLastWeekSell();//上周销售金额
    List<MedicineOrderBean> queryLastMonthSell();//上月售金额
    List<MedicineOrderBean> queryCurrentYearSell();//全年售金额
    List<MedicineOrderBean> queryAllSell();//总售金额
    List<MedicineOrderBean> querySellOrderInfo();
    List<MedicineOrderBean> queryUnSellOrderInfo();

    OrderBean queryCurrentDayAmount();//当日销售总金额查询
    OrderBean queryYesterdayAmount();//当日销售总金额查询
    OrderBean queryLastWeekAmount();//上周销售总金额查询
    OrderBean queryLastMonthAmount();//上月销售总金额查询
    OrderBean queryCurrentYearAmount();//当日销售总金额查询
    OrderBean queryAllAmount();//销售总金额查询
    String queryOrderTime(String order_uid);
    MedicineOrderBean queryOrderBaseAndMedicineName(Map<String, Object> map);

    List<MonthCountBean> queryMonthAllOrder();

    boolean upOrderOutWareHouse(Map<String,Object> map);
    boolean addOrderItem(Map<String,Object> map);//创建订单条目
    boolean updateOrderStatus(String order_uid);//交易时更新订单状态
    boolean updateOrderQuantity(Map<String,Object> map);
}
