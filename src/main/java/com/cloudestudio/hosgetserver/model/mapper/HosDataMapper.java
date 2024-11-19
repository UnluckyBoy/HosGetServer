package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.HosDataBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class HosDataMapper
 * @Author Create By Matrix·张
 * @Date 2024/11/13 下午2:04
 * Mybaties接口
 */
@Service
@Mapper
@Repository
public interface HosDataMapper {
    @Select("SELECT" +
            "  INHOSD_HZSQ.zyh AS hos_number," +
            "  (" +
            "    SELECT" +
            "      INHOS_ZCXX.BRXM " +
            "    FROM" +
            "      INHOS_ZCXX " +
            "    WHERE" +
            "      INHOS_ZCXX.BRID IN ( SELECT INHOS_RYDJ.brid FROM INHOS_RYDJ WHERE INHOS_RYDJ.ZYH = INHOSD_HZSQ.zyh ) " +
            "  ) AS patient_name," +
            "  (" +
            "    SELECT" +
            "      INHOS_RYDJ.BRNL || (" +
            "        CASE" +
            "            " +
            "            WHEN INHOS_RYDJ.BRNLDW = '1' THEN" +
            "            '岁' " +
            "            WHEN INHOS_RYDJ.BRNLDW = '2' THEN" +
            "            '月' " +
            "            WHEN INHOS_RYDJ.BRNLDW = '3' THEN" +
            "            '天' " +
            "            WHEN INHOS_RYDJ.BRNLDW = '4' THEN" +
            "            '时' ELSE '分' " +
            "            END " +
            "          ) " +
            "        FROM" +
            "          INHOS_RYDJ " +
            "        WHERE" +
            "          INHOS_RYDJ.ZYH = INHOSD_HZSQ.zyh " +
            "      ) AS patient_age," +
            "      ( SELECT pu_ks.ksmc FROM pu_ks WHERE pu_ks.KSBM = INHOSD_HZSQ.KSBM ) AS create_office," +
            "      ( SELECT pu_czy.CZYXM FROM pu_czy WHERE pu_czy.CZYBM = INHOSD_HZSQ.SQYS ) AS create_doctor," +
            "      ( SELECT pu_ks.ksmc FROM pu_ks WHERE pu_ks.KSBM = INHOSD_HZSQ.Hzks ) AS inspect_office," +
            "      ( SELECT pu_czy.CZYXM FROM pu_czy WHERE pu_czy.CZYBM = INHOSD_HZSQ.HZYS ) AS inspect_doctor," +
            "      INHOSD_HZSQ.SQSJ AS create_time," +
            "      INHOSD_HZSQ.WCSJ AS finish_time," +
            "      (" +
            "        TO_CHAR( TRUNC( ( ( INHOSD_HZSQ.WCSJ - INHOSD_HZSQ.SQSJ ) * 24 ) / 24 ) ) || ' 天 ' || TO_CHAR( TRUNC( MOD( ( ( INHOSD_HZSQ.WCSJ - INHOSD_HZSQ.SQSJ ) * 24 ), 24 ) ) ) || ' 时 ' || TO_CHAR( TRUNC( MOD( ( ( INHOSD_HZSQ.WCSJ - INHOSD_HZSQ.SQSJ ) * 24 * 60 ), 60 ) ) ) || ' 分' || TO_CHAR( TRUNC( MOD( ( ( INHOSD_HZSQ.WCSJ - INHOSD_HZSQ.SQSJ ) * 24 * 60 * 60 ), 60 ) ) ) || ' 秒' " +
            "      ) AS use_time," +
            "      ( CASE WHEN INHOSD_HZSQ.WCBZ = '1' THEN '完成' ELSE '未完成' END ) AS finish_type " +
            "    FROM" +
            "      INHOSD_HZSQ " +
            "    WHERE" +
            "  ( INHOSD_HZSQ.WCSJ - INHOSD_HZSQ.SQSJ ) * 24 >= 24 " +
            "  AND ( INHOSD_HZSQ.SQSJ BETWEEN to_date( #{startTime}, 'yyyy-mm-dd hh24:mi:ss' ) AND to_date( #{endTime}, 'yyyy-mm-dd hh24:mi:ss' ) )")
    List<HosDataBean> queryInHospital_consultation(String startTime,String endTime);//院内会诊查询

}
