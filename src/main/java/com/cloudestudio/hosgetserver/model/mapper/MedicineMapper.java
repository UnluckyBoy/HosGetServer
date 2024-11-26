package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.MedicineBaseBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
}
