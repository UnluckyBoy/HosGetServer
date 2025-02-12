package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.MedicineOrderBean;
import com.cloudestudio.hosgetserver.model.MonthCountBean;
import com.cloudestudio.hosgetserver.model.OrderBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class OrderMaapper
 * @Author Create By Matrix·张
 * @Date 2024/11/30 上午11:19
 * 订单Mapper接口类
 */
@Service
@Mapper
@Repository
public interface OrderMapper {
    List<MedicineOrderBean> queryOrderOutWareHouse();//销售未出库
    List<MedicineOrderBean> queryCurrentDaySell();//药剂分类当日销售金额
    List<MedicineOrderBean> queryYesterdaySell();//药剂分类昨日销售金额
    List<MedicineOrderBean> queryLastWeekSell();//药剂分类上周销售金额
    List<MedicineOrderBean> queryLastMonthSell();//药剂分类上月售金额
    List<MedicineOrderBean> queryCurrentYearSell();//药剂分类全年售金额
    List<MedicineOrderBean> queryAllSell();//药剂分类总售金额
    List<MedicineOrderBean> querySellOrderInfo();
    List<MedicineOrderBean> queryUnSellOrderInfo();
    List<MedicineOrderBean> queryOrderAllItem(String order_uid);//查询订单条目-销售UI使用

    OrderBean queryCurrentDayAmount();//当日销售总金额查询
    OrderBean queryYesterdayAmount();//当日销售总金额查询
    OrderBean queryLastWeekAmount();//上周销售总金额查询
    OrderBean queryLastMonthAmount();//上月销售总金额查询
    OrderBean queryCurrentYearAmount();//当日销售总金额查询
    OrderBean queryAllAmount();//销售总金额查询
    OrderBean queryOrderItem(Map<String,Object> map);//销售总金额查询
    String queryOrderTime(String order_uid);//查询订单时间
    MedicineOrderBean queryOrderBaseAndMedicineName(Map<String, Object> map);

    List<MonthCountBean> queryMonthAllOrder();

    boolean upOrderOutWareHouse(Map<String,Object> map);//销售出库
    boolean addOrderItem(Map<String,Object> map);//创建订单条目
    boolean updateOrderStatus(Map<String,Object> map);//交易时更新订单状态
    boolean updateOrderQuantity(Map<String,Object> map);
    boolean removeOrderItem(Map<String,Object> map);//移除订单条目
}
