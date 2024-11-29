package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.MedicineAllBean;
import com.cloudestudio.hosgetserver.model.MedicineBaseBean;
import com.cloudestudio.hosgetserver.model.mapper.MedicineMapper;
import com.cloudestudio.hosgetserver.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class MedicineImpl
 * @Author Create By Matrix·张
 * @Date 2024/11/25 下午2:01
 * 药剂服务接口实现类
 */
@Service("MedicineService")
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    MedicineMapper medicineMapper;

    @DS("mysql")
    @Override
    public MedicineBaseBean queryMedicineInfo(String medicine_code) {
        return medicineMapper.queryMedicineInfo(medicine_code);
    }

    @DS("mysql")
    @Override
    public MedicineBaseBean queryNearMedicineCode() {
        return medicineMapper.queryNearMedicineCode();
    }

    @DS("mysql")
    @Override
    public boolean addMedicineBaseInfo(Map<String, Object> map) {
        return medicineMapper.addMedicineBaseInfo(map);
    }

    @DS("mysql")
    @Override
    public boolean addMedicineToWareHouse(Map<String, Object> map) {
        return medicineMapper.addMedicineToWareHouse(map);
    }

    @DS("mysql")
    @Override
    public List<MedicineBaseBean> queryMedicineBaseInfo() {
        return medicineMapper.queryMedicineBaseInfo();
    }

    @DS("mysql")
    @Override
    public MedicineAllBean queryInfoByCodeCreateTime(Map<String,Object> map) {
        return medicineMapper.queryInfoByCodeCreateTime(map);
    }
}
