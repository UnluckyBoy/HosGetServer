package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.MedicineAllBean;
import com.cloudestudio.hosgetserver.model.MedicineBaseBean;
import com.cloudestudio.hosgetserver.model.mapper.MedicineMapper;

import java.util.List;
import java.util.Map;

/**
 * @Class MedicineService
 * @Author Create By Matrix·张
 * @Date 2024/11/25 下午2:00
 * 药剂信息服务接口类
 */
public interface MedicineService {
    /**查询**/
    MedicineBaseBean queryMedicineInfo(String medicine_code);
    MedicineBaseBean queryNearMedicineCode();
    List<MedicineBaseBean> queryMedicineBaseInfo();
    MedicineAllBean queryInfoByCodeCreateTime(Map<String,Object> map);
    MedicineAllBean queryOldestMedicWareHouseInfo(String medicine_code);
    /**插入**/
    boolean addMedicineBaseInfo(Map<String,Object> map);
    boolean addMedicineToWareHouse(Map<String,Object> map);
}
