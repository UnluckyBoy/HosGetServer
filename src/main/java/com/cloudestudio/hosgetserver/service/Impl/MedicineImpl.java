package com.cloudestudio.hosgetserver.service.Impl;

import com.cloudestudio.hosgetserver.model.MedicineBaseBean;
import com.cloudestudio.hosgetserver.model.mapper.MedicineMapper;
import com.cloudestudio.hosgetserver.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Class MedicineImpl
 * @Author Create By Matrix·张
 * @Date 2024/11/25 下午2:01
 * 药剂服务接口实现类
 */
@Service("MedicineService")
public class MedicineImpl implements MedicineService {
    @Autowired
    MedicineMapper medicineMapper;

    @Override
    public MedicineBaseBean queryMedicineInfo(String medicine_code) {
        return medicineMapper.queryMedicineInfo(medicine_code);
    }
}
