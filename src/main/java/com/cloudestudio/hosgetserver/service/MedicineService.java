package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.MedicineBaseBean;

import java.util.Map;

/**
 * @Class MedicineService
 * @Author Create By Matrix·张
 * @Date 2024/11/25 下午2:00
 * 药剂信息服务接口类
 */
public interface MedicineService {
    MedicineBaseBean queryMedicineInfo(String medicine_code);
    MedicineBaseBean queryNearMedicineCode();
    boolean addMedicineBaseInfo(Map<String,Object> map);
}
