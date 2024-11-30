package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.MedicineAllBean;
import com.cloudestudio.hosgetserver.model.MedicineBaseBean;
import com.cloudestudio.hosgetserver.model.MedicineOrderBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Class MedicineMapper
 * @Author Create By Matrix·张
 * @Date 2024/11/25 下午2:02
 * 药剂查询Mapper
 */
@Service
@Mapper
@Repository
public interface MedicineMapper {
    MedicineBaseBean queryMedicineInfo(String medicine_code);//查询最新批次药品信息
    MedicineBaseBean queryNearMedicineCode();
    List<MedicineBaseBean> queryMedicineBaseInfo();
    MedicineAllBean queryInfoByCodeCreateTime(Map<String,Object> map);

    boolean addMedicineBaseInfo(Map<String,Object> map);
    boolean addMedicineToWareHouse(Map<String,Object> map);
}
