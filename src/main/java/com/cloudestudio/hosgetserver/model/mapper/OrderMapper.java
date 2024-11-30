package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.MedicineOrderBean;
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
    MedicineOrderBean queryCurrentDaySell();//当日销售金额
    MedicineOrderBean queryYesterdaySell();//昨日销售金额
    MedicineOrderBean queryLastWeekSell();//上周销售金额
    MedicineOrderBean queryLastMonthSell();//上月售金额
    MedicineOrderBean queryCurrentYearSell();//全年售金额
    MedicineOrderBean queryAllSell();//总售金额

    boolean upOrderOutWareHouse(Map<String,Object> map);//销售出库
}
