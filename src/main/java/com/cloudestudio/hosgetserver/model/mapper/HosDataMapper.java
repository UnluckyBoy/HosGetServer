package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.*;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT" +
            "  PATIENT_ZCXX.BRID AS id," +
            "  PATIENT_ZCXX.BRXM AS patientName," +
            "  '01' AS idCardTypeCode," +
            "  '居民身份证' AS idCardTypeName," +
            "  PATIENT_ZCXX.SFZH AS idCard," +
            "  PATIENT_ZCXX.BRXB AS genderCode," +
            "  ( CASE PATIENT_ZCXX.BRXB WHEN '1' THEN '男' WHEN '2' THEN '女' ELSE '未知' END ) AS genderName," +
            "  ( CASE WHEN ( PATIENT_ZCXX.BRSR IS NULL ) THEN '' ELSE to_char( PATIENT_ZCXX.BRSR, 'yyyy-MM-dd') END) AS birthDate," +
            "  '156' AS nationalityCode," +
            "  '中国' AS nationalityName," +
            "  PATIENT_ZCXX.MZBM AS nationCode," +
            "  (SELECT PU_SJZD.XMMC FROM PU_SJZD WHERE ((PU_SJZD.XMLB = '民族编码') AND (PU_SJZD.XMBM = PATIENT_ZCXX.MZBM))) AS nationName," +
            "  '' AS permanentAddrCode," +
            "  '' AS permanentAddrName," +
            "  '' AS permanentAddrDetail," +
            "  '' AS currentAddrCode," +
            "  '' AS currentAddrName," +
            "  PATIENT_ZCXX.JTZZ AS currentAddrDetail," +
            "  PATIENT_ZCXX.GZDW AS workUnit," +
            "  '' AS maritalStatusCode," +
            "  '' AS maritalStatusName," +
            "  '' AS educationCode," +
            "  '' AS educationName," +
            "  '' AS nultitudeTypeCode," +
            "  '' AS nultitudeTypeName," +
            "  '' AS nultitude_type_other," +
            "  PATIENT_ZCXX.SJ AS tel," +
            "  PATIENT_ZCXX.LXR AS contacts," +
            "  PATIENT_ZCXX.LXRDH AS contactsTel," +
            "  '522632003' AS orgCode," +
            "  '榕江县中医院' AS orgName," +
            "  '0001' AS operatorId," +
            "  SYSDATE AS operationTime " +
            "FROM" +
            "  PATIENT_ZCXX " +
            "  INNER JOIN GH_BRGH ON GH_BRGH.BRID = PATIENT_ZCXX.BRID " +
            "WHERE" +
            "  gh_brgh.ghxh=#{regisNumber}")
    PatientBaseInfoBean queryPatientBaseInfo(String regisNumber);//查询患者基表

    @Select("SELECT " +
            "  MZYS_BRZD.ZDID AS id, " +
            "  GH_BRGH.BRID AS patientId, " +
            "  ( CASE WHEN ( GH_BRGH.JZBZ = '1' ) THEN '2' ELSE '1' END ) AS activityTypeCode, " +
            "  ( CASE WHEN ( GH_BRGH.JZBZ = '1' ) THEN '急诊' ELSE '门诊' END ) AS activityTypeName, " +
            "  GH_BRGH.GHXH AS serialNumber, " +
            "  ( CASE WHEN ( GH_BRGH.JZRQ IS NULL ) THEN '' ELSE to_char( GH_BRGH.JZRQ, 'yyyy-MM-dd hh24:mi:ss' ) END ) AS activityTime, " +
            "  PATIENT_ZCXX.BRXM AS patientName, " +
            "  '01' AS idCardTypeCode, " +
            "  '居民身份证' AS idCardTypeName, " +
            "  PATIENT_ZCXX.SFZH AS idCard, " +
            "  MZYS_BRWZ.ZS AS chiefComplaint, " +
            "  MZYS_BRWZ.XBS AS presentIllnessHis, " +
            "  '体温：' || CAST( MZYS_BRWZ.TGJC_TW AS VARCHAR2 (4000) ) || ',心率：' || MZYS_BRWZ.TGJC_XT || ',血压：' || MZYS_BRWZ.TGJC_XY AS physicalExamination, " +
            "  MZYS_BRWZ.FZJC AS studiesSummaryResult, " +
            "  to_char( MZYS_BRZD.RQ, 'yyyy-MM-dd HH24:mi:ss' ) AS diagnoseTime, " +
            "  MZYS_BRZD.ZDBM AS diseaseCode, " +
            "  MZYS_BRZD.ZDMC AS diseaseName, " +
            "  MZYS_BRZD.ZDBM AS wmDiseaseCode, " +
            "  MZYS_BRZD.ZDMC AS wmDiseaseName, " +
            "  '' AS tcmDiseaseCode, " +
            "  '' AS tcmDiseaseName, " +
            "  '' AS tcmSyndromeCode, " +
            "  '' AS tcmSyndromeName, " +
            "  ((SELECT substr( PU_CZY.czyxm, instr( PU_CZY.czyxm, '/' ) + 1 ) FROM PU_CZY WHERE (PU_CZY.CZYBM = MZYS_BRZD.YSBM ) AND ROWNUM = 1)) AS fillDoctor, " +
            "  GH_BRGH.GHKS AS deptCode, " +
            "  ( SELECT KS.KSMC FROM PU_KS KS WHERE ( KS.KSBM = GH_BRGH.GHKS ) AND ROWNUM = 1 ) AS deptName, " +
            "  '522632003' AS orgCode, " +
            "  '榕江县中医院' AS orgName, " +
            "  '0001' AS operatorId, " +
            "  SYSDATE AS operationTime  " +
            "FROM " +
            "  MZYS_BRZD  " +
            "  INNER JOIN GH_BRGH ON ( MZYS_BRZD.GHXH = GH_BRGH.GHXH ) " +
            "  INNER JOIN PATIENT_ZCXX ON ( GH_BRGH.BRID = PATIENT_ZCXX.BRID ) " +
            "  INNER JOIN MZYS_BRWZ ON ( GH_BRGH.GHXH = MZYS_BRWZ.GHXH )  " +
            "WHERE " +
            "  ((GH_BRGH.JZRQ IS NOT NULL) AND (MZYS_BRZD.ZDLX ='1')) " +
            "  AND ( GH_BRGH.GHXH =#{regisNumber})")
    List<PatientActivityBean> queryPatientActivityInfo(String regisNumber);//查询患者门诊信息

    @Select("SELECT" +
            "  PATIENT_ZCXX.BRID AS id," +
            "  PATIENT_ZCXX.BRXM AS patientName," +
            "  '01' AS idCardTypeCode," +
            "  '居民身份证' AS idCardTypeName," +
            "  PATIENT_ZCXX.SFZH AS idCard," +
            "  PATIENT_ZCXX.BRXB AS genderCode," +
            "  ( CASE PATIENT_ZCXX.BRXB WHEN '1' THEN '男' WHEN '2' THEN '女' ELSE '未知' END ) AS genderName," +
            "  ( CASE WHEN ( PATIENT_ZCXX.BRSR IS NULL ) THEN '' ELSE to_char( PATIENT_ZCXX.BRSR, 'yyyy-MM-dd') END) AS birthDate," +
            "  '156' AS nationalityCode," +
            "  '中国' AS nationalityName," +
            "  PATIENT_ZCXX.MZBM AS nationCode," +
            "  (SELECT PU_SJZD.XMMC FROM PU_SJZD WHERE ((PU_SJZD.XMLB = '民族编码') AND (PU_SJZD.XMBM = PATIENT_ZCXX.MZBM))) AS nationName," +
            "  '' AS permanentAddrCode," +
            "  '' AS permanentAddrName," +
            "  '' AS permanentAddrDetail," +
            "  '' AS currentAddrCode," +
            "  '' AS currentAddrName," +
            "  PATIENT_ZCXX.JTZZ AS currentAddrDetail," +
            "  PATIENT_ZCXX.GZDW AS workUnit," +
            "  '' AS maritalStatusCode," +
            "  '' AS maritalStatusName," +
            "  '' AS educationCode," +
            "  '' AS educationName," +
            "  '' AS nultitudeTypeCode," +
            "  '' AS nultitudeTypeName," +
            "  '' AS nultitude_type_other," +
            "  PATIENT_ZCXX.SJ AS tel," +
            "  PATIENT_ZCXX.LXR AS contacts," +
            "  PATIENT_ZCXX.LXRDH AS contactsTel," +
            "  '522632003' AS orgCode," +
            "  '榕江县中医院' AS orgName," +
            "  '0001' AS operatorId," +
            "  SYSDATE AS operationTime " +
            "FROM" +
            "  PATIENT_ZCXX " +
            "  INNER JOIN GH_BRGH ON GH_BRGH.BRID = PATIENT_ZCXX.BRID " +
            "WHERE" +
            "  TRUNC(GH_BRGH.GHRQ) = TRUNC(SYSDATE) AND GH_BRGH.SFJZ='1' AND GH_BRGH.THBZ='0'")
    List<PatientBaseInfoBean> queryPatientBaseInfoList();//当日患者信息查询

    @Select("SELECT " +
            "  MZYS_BRZD.ZDID AS id, " +
            "  GH_BRGH.BRID AS patientId, " +
            "  ( CASE WHEN ( GH_BRGH.JZBZ = '1' ) THEN '2' ELSE '1' END ) AS activityTypeCode, " +
            "  ( CASE WHEN ( GH_BRGH.JZBZ = '1' ) THEN '急诊' ELSE '门诊' END ) AS activityTypeName, " +
            "  GH_BRGH.GHXH AS serialNumber, " +
            "  ( CASE WHEN ( GH_BRGH.JZRQ IS NULL ) THEN '' ELSE to_char( GH_BRGH.JZRQ, 'yyyy-MM-dd hh24:mi:ss' ) END ) AS activityTime, " +
            "  PATIENT_ZCXX.BRXM AS patientName, " +
            "  '01' AS idCardTypeCode, " +
            "  '居民身份证' AS idCardTypeName, " +
            "  PATIENT_ZCXX.SFZH AS idCard, " +
            "  MZYS_BRWZ.ZS AS chiefComplaint, " +
            "  MZYS_BRWZ.XBS AS presentIllnessHis, " +
            "  '体温：' || CAST( MZYS_BRWZ.TGJC_TW AS VARCHAR2 (4000) ) || ',心率：' || MZYS_BRWZ.TGJC_XT || ',血压：' || MZYS_BRWZ.TGJC_XY AS physicalExamination, " +
            "  MZYS_BRWZ.FZJC AS studiesSummaryResult, " +
            "  to_char( MZYS_BRZD.RQ, 'yyyy-MM-dd HH24:mi:ss' ) AS diagnoseTime, " +
            "  MZYS_BRZD.ZDBM AS diseaseCode, " +
            "  MZYS_BRZD.ZDMC AS diseaseName, " +
            "  MZYS_BRZD.ZDBM AS wmDiseaseCode, " +
            "  MZYS_BRZD.ZDMC AS wmDiseaseName, " +
            "  '' AS tcmDiseaseCode, " +
            "  '' AS tcmDiseaseName, " +
            "  '' AS tcmSyndromeCode, " +
            "  '' AS tcmSyndromeName, " +
            "  ((SELECT substr( PU_CZY.czyxm, instr( PU_CZY.czyxm, '/' ) + 1 ) FROM PU_CZY WHERE (PU_CZY.CZYBM = MZYS_BRZD.YSBM ) AND ROWNUM = 1)) AS fillDoctor, " +
            "  GH_BRGH.GHKS AS deptCode, " +
            "  ( SELECT KS.KSMC FROM PU_KS KS WHERE ( KS.KSBM = GH_BRGH.GHKS ) AND ROWNUM = 1 ) AS deptName, " +
            "  '522632003' AS orgCode, " +
            "  '榕江县中医院' AS orgName, " +
            "  '0001' AS operatorId, " +
            "  SYSDATE AS operationTime  " +
            "FROM " +
            "  MZYS_BRZD  " +
            "  INNER JOIN GH_BRGH ON ( MZYS_BRZD.GHXH = GH_BRGH.GHXH ) " +
            "  INNER JOIN PATIENT_ZCXX ON ( GH_BRGH.BRID = PATIENT_ZCXX.BRID ) " +
            "  INNER JOIN MZYS_BRWZ ON ( GH_BRGH.GHXH = MZYS_BRWZ.GHXH )  " +
            "WHERE " +
            "  ((GH_BRGH.JZRQ IS NOT NULL) AND (MZYS_BRZD.ZDLX ='1')) " +
            "  AND (GH_BRGH.GHXH IN (SELECT gh_brgh.ghxh FROM gh_brgh WHERE (TRUNC(GH_BRGH.GHRQ) = TRUNC(SYSDATE) AND GH_BRGH.SFJZ='1' AND GH_BRGH.THBZ='0')))")
    List<PatientActivityBean> queryEmrActivityInfo();//当日患者门诊诊断信息查询

    @Select("SELECT" +
            "  HW_CRBBGK.SQID AS id," +
            "  GH_BRGH.BRID AS patient_id," +
            "  GH_BRGH.GHXH AS serial_number," +
            "  ( CASE WHEN ( GH_BRGH.JZBZ = '1' ) THEN '2' ELSE '1' END ) AS activity_type_code," +
            "  ( CASE WHEN ( GH_BRGH.JZBZ = '1' ) THEN '急诊' ELSE '门诊' END ) AS activity_type_name," +
            "  HW_CRBBGK.HZXM AS patient_name," +
            "  '01' AS id_card_type_code," +
            "  '居民身份证' AS id_card_type_name," +
            "  PATIENT_ZCXX.SFZH AS id_card," +
            "  PATIENT_ZCXX.BRXB AS gender_code," +
            "  ( CASE PATIENT_ZCXX.BRXB WHEN '1' THEN '男' WHEN '2' THEN '女' ELSE '未知' END ) AS gender_name," +
            "  ( CASE WHEN ( PATIENT_ZCXX.BRSR IS NULL ) THEN '' ELSE to_char( PATIENT_ZCXX.BRSR, 'yyyy-MM-dd') END) AS birth_date," +
            "  (CASE SUBSTR(PATIENT_ZCXX.SFZH, 1, 1) WHEN '9' THEN '004' ELSE '156' END) AS nationality_code," +
            "  (CASE SUBSTR(PATIENT_ZCXX.SFZH, 1, 1) WHEN '9' THEN '外国' ELSE '中国' END) AS nationality_name," +
            "  PATIENT_ZCXX.MZBM AS nation_code," +
            "  (SELECT PU_SJZD.XMMC FROM PU_SJZD WHERE ((PU_SJZD.XMLB = '民族编码') AND (PU_SJZD.XMBM = PATIENT_ZCXX.MZBM))) AS nation_name," +
            "  '' AS permanent_addr_code," +
            "  '' AS permanent_addr_name," +
            "  PATIENT_ZCXX.JTZZ AS permanent_addr_detail," +
            "  '' AS current_addr_code," +
            "  '' AS current_addr_name," +
            "  HW_CRBBGK.XZZ AS current_addr_detail," +
            "  PATIENT_ZCXX.GZDW AS workunit," +
            "  (CASE WHEN HW_CRBBGK.XBBGFK1='1' THEN '1' WHEN HW_CRBBGK.XBBGFK2='1' THEN '2' WHEN HW_CRBBGK.XBBGFK3='1' THEN '3' WHEN HW_CRBBGK.XBBGFK37='1' THEN '4' END) AS marital_status_code," +
            "  (CASE WHEN HW_CRBBGK.XBBGFK1='1' THEN '未婚' WHEN HW_CRBBGK.XBBGFK2='1' THEN '已婚有配偶' WHEN HW_CRBBGK.XBBGFK3='1' THEN '离异或丧偶' WHEN HW_CRBBGK.XBBGFK37='1' THEN '不详' END) AS marital_status_name," +
            "  (CASE WHEN HW_CRBBGK.XBBGFK5='1' THEN '1' WHEN HW_CRBBGK.XBBGFK6='1' THEN '2' WHEN HW_CRBBGK.XBBGFK7='1' THEN '3' WHEN HW_CRBBGK.XBBGFK8='1' THEN '4' WHEN HW_CRBBGK.XBBGFK38='1' THEN '5' END) AS education_code," +
            "  (CASE WHEN HW_CRBBGK.XBBGFK5='1' THEN '文盲' WHEN HW_CRBBGK.XBBGFK6='1' THEN '小学' WHEN HW_CRBBGK.XBBGFK7='1' THEN '初中' WHEN HW_CRBBGK.XBBGFK8='1' THEN '高中或中专' WHEN HW_CRBBGK.XBBGFK38='1' THEN '大专及以上' END) AS education_name," +
            " (CASE WHEN HW_CRBBGK.RQFL_01='1' THEN '1' WHEN HW_CRBBGK.RQFL_06='1' THEN '2' WHEN HW_CRBBGK.RQFL_11='1' THEN '3' WHEN HW_CRBBGK.RQFL_16='1' THEN '4' WHEN HW_CRBBGK.RQFL_02='1' THEN '5' WHEN HW_CRBBGK.RQFL_07='1' THEN '6' WHEN HW_CRBBGK.RQFL_12='1' THEN '7' WHEN HW_CRBBGK.RQFL_17='1' THEN '8' WHEN HW_CRBBGK.RQFL_03='1' THEN '9' WHEN HW_CRBBGK.RQFL_08='1' THEN '10' WHEN HW_CRBBGK.RQFL_13='1' THEN '11' WHEN HW_CRBBGK.RQFL_18='1' THEN '12' WHEN HW_CRBBGK.RQFL_04='1' THEN '13' WHEN HW_CRBBGK.RQFL_09='1' THEN '14' WHEN HW_CRBBGK.RQFL_14='1' THEN '15' WHEN HW_CRBBGK.RQFL_19='1' THEN '16' WHEN HW_CRBBGK.RQFL_05='1' THEN '17' WHEN HW_CRBBGK.RQFL_10='1' THEN '18' WHEN HW_CRBBGK.RQFL_15='1' THEN '20' WHEN HW_CRBBGK.RQFL_15='1' THEN '99' END) AS nultitude_type_code," +
            " (CASE WHEN HW_CRBBGK.RQFL_01='1' THEN '幼托儿童' WHEN HW_CRBBGK.RQFL_06='1' THEN '散居儿童' WHEN HW_CRBBGK.RQFL_11='1' THEN '学生' WHEN HW_CRBBGK.RQFL_16='1' THEN '教师' WHEN HW_CRBBGK.RQFL_02='1' THEN '保育员及保姆' WHEN HW_CRBBGK.RQFL_07='1' THEN '餐饮食品业' WHEN HW_CRBBGK.RQFL_12='1' THEN '公共场所服务员' WHEN HW_CRBBGK.RQFL_17='1' THEN '商业服务' WHEN HW_CRBBGK.RQFL_03='1' THEN '医务人员' WHEN HW_CRBBGK.RQFL_08='1' THEN '工人' WHEN HW_CRBBGK.RQFL_13='1' THEN '民工' WHEN HW_CRBBGK.RQFL_18='1' THEN '农民' WHEN HW_CRBBGK.RQFL_04='1' THEN '牧民' WHEN HW_CRBBGK.RQFL_09='1' THEN '渔(船)民' WHEN HW_CRBBGK.RQFL_14='1' THEN '海关及长途驾驶员' WHEN HW_CRBBGK.RQFL_19='1' THEN '干部职员' WHEN HW_CRBBGK.RQFL_05='1' THEN '离退人员' WHEN HW_CRBBGK.RQFL_10='1' THEN '家务及待业' WHEN HW_CRBBGK.RQFL_15='1' THEN '不详' WHEN HW_CRBBGK.RQFL_15='1' THEN '其他' END) AS nultitude_type_name," +
            " HW_CRBBGK.RQFL_QT AS nultitude_type_other," +
            " HW_CRBBGK.LXDH AS tel," +
            " PATIENT_ZCXX.LXR AS contacts," +
            " PATIENT_ZCXX.LXRDH AS contacts_tel," +
            " HW_CRBBGK.FBRQ AS onset_date," +
            " MZYS_BRZD.RQ AS diagnose_time," +
            " (SELECT HW_CRB_ICD10_MAP.ICD_10 FROM HW_CRB_ICD10_MAP WHERE HW_CRB_ICD10_MAP.BGK_COLUMN_NAME IN (CASE WHEN HW_CRBBGK.BLCRB_01 = '1' THEN 'BLCRB_01' WHEN HW_CRBBGK.BLCRB_02='1' THEN 'BLCRB_02' WHEN HW_CRBBGK.BLCRB_03='1' THEN 'BLCRB_03' WHEN HW_CRBBGK.BLCRB_04='1' THEN 'BLCRB_04' WHEN HW_CRBBGK.BLCRB_05='1' THEN 'BLCRB_05' WHEN HW_CRBBGK.BLCRB_06='1' THEN 'BLCRB_06' WHEN HW_CRBBGK.BLCRB_07='1' THEN 'BLCRB_07' WHEN HW_CRBBGK.BLCRB_08='1' THEN 'BLCRB_08' WHEN HW_CRBBGK.BLCRB_09='1' THEN 'BLCRB_09' WHEN HW_CRBBGK.BLCRB_10='1' THEN 'BLCRB_10' WHEN HW_CRBBGK.BLCRB_11='1' THEN 'BLCRB_11' WHEN HW_CRBBGK.BLCRB_10='1' THEN 'BLCRB_10' WHEN HW_CRBBGK.JLCRB_01='1' THEN 'JLCRB_01' WHEN HW_CRBBGK.JLCRB_02='1' THEN 'JLCRB_02' WHEN HW_CRBBGK.QTCRB1='1' THEN 'QTCRB1' WHEN HW_CRBBGK.QTCRB2='1' THEN 'QTCRB2' WHEN HW_CRBBGK.QTCRB3='1' THEN 'QTCRB3' WHEN HW_CRBBGK.QTCRB4='1' THEN 'QTCRB4' WHEN HW_CRBBGK.QTCRB5='1' THEN 'QTCRB5' WHEN HW_CRBBGK.QTCRB6='1' THEN 'QTCRB6' WHEN HW_CRBBGK.QTCRB7='1' THEN 'QTCRB7' WHEN HW_CRBBGK.QTCRB8='1' THEN 'QTCRB8' WHEN HW_CRBBGK.QTCRB9='1' THEN 'QTCRB9' WHEN HW_CRBBGK.QTCRB10='1' THEN 'QTCRB10' WHEN HW_CRBBGK.QTCRB11='1' THEN 'QTCRB11' WHEN HW_CRBBGK.QTCRB12='1' THEN 'QTCRB12' WHEN HW_CRBBGK.QTCRB13='1' THEN 'QTCRB13' WHEN HW_CRBBGK.QTCRB14='1' THEN 'QTCRB14' WHEN HW_CRBBGK.QTCRB15='1' THEN 'QTCRB15' WHEN HW_CRBBGK.QTCRB16='1' THEN 'QTCRB16' WHEN HW_CRBBGK.QTCRB17='1' THEN 'QTCRB17' WHEN HW_CRBBGK.QT_CRB_AFP='1' THEN 'QT_CRB_AFP' WHEN HW_CRBBGK.QT_CRB_BMYY='1' THEN 'QT_CRB_BMYY' WHEN HW_CRBBGK.QT_CRB_BMYYFY='1' THEN 'QT_CRB_BMYYFY' WHEN HW_CRBBGK.QT_CRB_EBLCXR='1' THEN 'QT_CRB_EBLCXR' WHEN HW_CRBBGK.QT_CRB_FRBXXBJCZHZ='1' THEN 'QT_CRB_FRBXXBJCZHZ' WHEN HW_CRBBGK.QT_CRB_GXCB='1' THEN 'QT_CRB_GXCB' WHEN HW_CRBBGK.QT_CRB_MERS='1' THEN 'QT_CRB_MERS' WHEN HW_CRBBGK.QT_CRB_QT='1' THEN 'QT_CRB_QT' WHEN HW_CRBBGK.QT_CRB_QTXB_FLJXNDY='1' THEN 'QT_CRB_QTXB_FLJXNDY' WHEN HW_CRBBGK.QT_CRB_QTXB_JRSY='1' THEN 'QT_CRB_QTXB_JRSY' WHEN HW_CRBBGK.QT_CRB_QTXB_SZDYYTGR='1' THEN 'QT_CRB_QTXB_SZDYYTGR' WHEN HW_CRBBGK.QT_CRB_QTXB_SZQBZ='1' THEN 'QT_CRB_QTXB_SZQBZ' WHEN HW_CRBBGK.QT_CRB_RGRZLQJ='1' THEN 'QT_CRB_RGRZLQJ' WHEN HW_CRBBGK.QT_CRB_RLXBWXTB='1' THEN 'QT_CRB_RLXBWXTB' WHEN HW_CRBBGK.QT_CRB_SD='1' THEN 'QT_CRB_SD' WHEN HW_CRBBGK.QT_CRB_SKBD='1' THEN 'QT_CRB_SKBD' WHEN HW_CRBBGK.QT_CRB_SLNY='1' THEN 'QT_CRB_SLNY' WHEN HW_CRBBGK.QT_CRB_YCB='1' THEN 'QT_CRB_YCB' WHEN HW_CRBBGK.XXGZBD='1' THEN 'XXGZBD' WHEN HW_CRBBGK.YLCRB_01='1' THEN 'YLCRB_01' WHEN HW_CRBBGK.YLCRB_02='1' THEN 'YLCRB_02' WHEN HW_CRBBGK.YLCRB_03='1' THEN 'YLCRB_03' WHEN HW_CRBBGK.YLCRB_04='1' THEN 'YLCRB_04' WHEN HW_CRBBGK.YLCRB_05='1' THEN 'YLCRB_05' WHEN HW_CRBBGK.YLCRB_06='1' THEN 'YLCRB_06' WHEN HW_CRBBGK.YLCRB_07='1' THEN 'YLCRB_07' WHEN HW_CRBBGK.YLCRB_08='1' THEN 'YLCRB_08' WHEN HW_CRBBGK.YLCRB_09='1' THEN 'YLCRB_09' WHEN HW_CRBBGK.YLCRB_10='1' THEN 'YLCRB_10' WHEN HW_CRBBGK.YLCRB_11='1' THEN 'YLCRB_11' WHEN HW_CRBBGK.YLCRB_12='1' THEN 'YLCRB_12' WHEN HW_CRBBGK.YLCRB_13='1' THEN 'YLCRB_13' WHEN HW_CRBBGK.YLCRB_14='1' THEN 'YLCRB_14' WHEN HW_CRBBGK.YLCRB_15='1' THEN 'YLCRB_15' WHEN HW_CRBBGK.YLCRB_16='1' THEN 'YLCRB_16' WHEN HW_CRBBGK.YLCRB_17='1' THEN 'YLCRB_17' WHEN HW_CRBBGK.YLCRB_18='1' THEN 'YLCRB_18' WHEN HW_CRBBGK.YLCRB_19='1' THEN 'YLCRB_19' WHEN HW_CRBBGK.YLCRB_20='1' THEN 'YLCRB_20' WHEN HW_CRBBGK.YLCRB_21='1' THEN 'YLCRB_21' WHEN HW_CRBBGK.YLCRB_22='1' THEN 'YLCRB_22' WHEN HW_CRBBGK.YLCRB_23='1' THEN 'YLCRB_23' WHEN HW_CRBBGK.YLCRB_24='1' THEN 'YLCRB_24' WHEN HW_CRBBGK.YLCRB_25='1' THEN 'YLCRB_25' WHEN HW_CRBBGK.YLCRB_26='1' THEN 'YLCRB_26' WHEN HW_CRBBGK.YLCRB_27='1' THEN 'YLCRB_27' WHEN HW_CRBBGK.YLCRB_28='1' THEN 'YLCRB_28' WHEN HW_CRBBGK.YLCRB_29='1' THEN 'YLCRB_29' WHEN HW_CRBBGK.YLCRB_30='1' THEN 'YLCRB_30' WHEN HW_CRBBGK.YLCRB_31='1' THEN 'YLCRB_31' WHEN HW_CRBBGK.YLCRB_32='1' THEN 'YLCRB_32' WHEN HW_CRBBGK.YLCRB_33='1' THEN 'YLCRB_33' WHEN HW_CRBBGK.YLCRB_34='1' THEN 'YLCRB_34' WHEN HW_CRBBGK.YLCRB_35='1' THEN 'YLCRB_35' WHEN HW_CRBBGK.YLCRB_36='1' THEN 'YLCRB_36' WHEN HW_CRBBGK.YLCRB_37='1' THEN 'YLCRB_37' WHEN HW_CRBBGK.YLCRB_38='1' THEN 'YLCRB_38' WHEN HW_CRBBGK.YLCRB_39='1' THEN 'YLCRB_39' WHEN HW_CRBBGK.YLCRB_40='1' THEN 'YLCRB_40' WHEN HW_CRBBGK.YLCRB_41='1' THEN 'YLCRB_41' WHEN HW_CRBBGK.YLCRB_42='1' THEN 'YLCRB_42' WHEN HW_CRBBGK.YLCRB_43='1' THEN 'YLCRB_43' WHEN HW_CRBBGK.YLCRB_44='1' THEN 'YLCRB_44' WHEN HW_CRBBGK.YLCRB_45='1' THEN 'YLCRB_44' END)) AS disease_code," +
            " (SELECT HW_CRB_ICD10_MAP.BAK_CRB FROM HW_CRB_ICD10_MAP WHERE HW_CRB_ICD10_MAP.BGK_COLUMN_NAME IN (CASE WHEN HW_CRBBGK.BLCRB_01 = '1' THEN 'BLCRB_01' WHEN HW_CRBBGK.BLCRB_02='1' THEN 'BLCRB_02' WHEN HW_CRBBGK.BLCRB_03='1' THEN 'BLCRB_03' WHEN HW_CRBBGK.BLCRB_04='1' THEN 'BLCRB_04' WHEN HW_CRBBGK.BLCRB_05='1' THEN 'BLCRB_05' WHEN HW_CRBBGK.BLCRB_06='1' THEN 'BLCRB_06' WHEN HW_CRBBGK.BLCRB_07='1' THEN 'BLCRB_07' WHEN HW_CRBBGK.BLCRB_08='1' THEN 'BLCRB_08' WHEN HW_CRBBGK.BLCRB_09='1' THEN 'BLCRB_09' WHEN HW_CRBBGK.BLCRB_10='1' THEN 'BLCRB_10' WHEN HW_CRBBGK.BLCRB_11='1' THEN 'BLCRB_11' WHEN HW_CRBBGK.BLCRB_10='1' THEN 'BLCRB_10' WHEN HW_CRBBGK.JLCRB_01='1' THEN 'JLCRB_01' WHEN HW_CRBBGK.JLCRB_02='1' THEN 'JLCRB_02' WHEN HW_CRBBGK.QTCRB1='1' THEN 'QTCRB1' WHEN HW_CRBBGK.QTCRB2='1' THEN 'QTCRB2' WHEN HW_CRBBGK.QTCRB3='1' THEN 'QTCRB3' WHEN HW_CRBBGK.QTCRB4='1' THEN 'QTCRB4' WHEN HW_CRBBGK.QTCRB5='1' THEN 'QTCRB5' WHEN HW_CRBBGK.QTCRB6='1' THEN 'QTCRB6' WHEN HW_CRBBGK.QTCRB7='1' THEN 'QTCRB7' WHEN HW_CRBBGK.QTCRB8='1' THEN 'QTCRB8' WHEN HW_CRBBGK.QTCRB9='1' THEN 'QTCRB9' WHEN HW_CRBBGK.QTCRB10='1' THEN 'QTCRB10' WHEN HW_CRBBGK.QTCRB11='1' THEN 'QTCRB11' WHEN HW_CRBBGK.QTCRB12='1' THEN 'QTCRB12' WHEN HW_CRBBGK.QTCRB13='1' THEN 'QTCRB13' WHEN HW_CRBBGK.QTCRB14='1' THEN 'QTCRB14' WHEN HW_CRBBGK.QTCRB15='1' THEN 'QTCRB15' WHEN HW_CRBBGK.QTCRB16='1' THEN 'QTCRB16' WHEN HW_CRBBGK.QTCRB17='1' THEN 'QTCRB17' WHEN HW_CRBBGK.QT_CRB_AFP='1' THEN 'QT_CRB_AFP' WHEN HW_CRBBGK.QT_CRB_BMYY='1' THEN 'QT_CRB_BMYY' WHEN HW_CRBBGK.QT_CRB_BMYYFY='1' THEN 'QT_CRB_BMYYFY' WHEN HW_CRBBGK.QT_CRB_EBLCXR='1' THEN 'QT_CRB_EBLCXR' WHEN HW_CRBBGK.QT_CRB_FRBXXBJCZHZ='1' THEN 'QT_CRB_FRBXXBJCZHZ' WHEN HW_CRBBGK.QT_CRB_GXCB='1' THEN 'QT_CRB_GXCB' WHEN HW_CRBBGK.QT_CRB_MERS='1' THEN 'QT_CRB_MERS' WHEN HW_CRBBGK.QT_CRB_QT='1' THEN 'QT_CRB_QT' WHEN HW_CRBBGK.QT_CRB_QTXB_FLJXNDY='1' THEN 'QT_CRB_QTXB_FLJXNDY' WHEN HW_CRBBGK.QT_CRB_QTXB_JRSY='1' THEN 'QT_CRB_QTXB_JRSY' WHEN HW_CRBBGK.QT_CRB_QTXB_SZDYYTGR='1' THEN 'QT_CRB_QTXB_SZDYYTGR' WHEN HW_CRBBGK.QT_CRB_QTXB_SZQBZ='1' THEN 'QT_CRB_QTXB_SZQBZ' WHEN HW_CRBBGK.QT_CRB_RGRZLQJ='1' THEN 'QT_CRB_RGRZLQJ' WHEN HW_CRBBGK.QT_CRB_RLXBWXTB='1' THEN 'QT_CRB_RLXBWXTB' WHEN HW_CRBBGK.QT_CRB_SD='1' THEN 'QT_CRB_SD' WHEN HW_CRBBGK.QT_CRB_SKBD='1' THEN 'QT_CRB_SKBD' WHEN HW_CRBBGK.QT_CRB_SLNY='1' THEN 'QT_CRB_SLNY' WHEN HW_CRBBGK.QT_CRB_YCB='1' THEN 'QT_CRB_YCB' WHEN HW_CRBBGK.XXGZBD='1' THEN 'XXGZBD' WHEN HW_CRBBGK.YLCRB_01='1' THEN 'YLCRB_01' WHEN HW_CRBBGK.YLCRB_02='1' THEN 'YLCRB_02' WHEN HW_CRBBGK.YLCRB_03='1' THEN 'YLCRB_03' WHEN HW_CRBBGK.YLCRB_04='1' THEN 'YLCRB_04' WHEN HW_CRBBGK.YLCRB_05='1' THEN 'YLCRB_05' WHEN HW_CRBBGK.YLCRB_06='1' THEN 'YLCRB_06' WHEN HW_CRBBGK.YLCRB_07='1' THEN 'YLCRB_07' WHEN HW_CRBBGK.YLCRB_08='1' THEN 'YLCRB_08' WHEN HW_CRBBGK.YLCRB_09='1' THEN 'YLCRB_09' WHEN HW_CRBBGK.YLCRB_10='1' THEN 'YLCRB_10' WHEN HW_CRBBGK.YLCRB_11='1' THEN 'YLCRB_11' WHEN HW_CRBBGK.YLCRB_12='1' THEN 'YLCRB_12' WHEN HW_CRBBGK.YLCRB_13='1' THEN 'YLCRB_13' WHEN HW_CRBBGK.YLCRB_14='1' THEN 'YLCRB_14' WHEN HW_CRBBGK.YLCRB_15='1' THEN 'YLCRB_15' WHEN HW_CRBBGK.YLCRB_16='1' THEN 'YLCRB_16' WHEN HW_CRBBGK.YLCRB_17='1' THEN 'YLCRB_17' WHEN HW_CRBBGK.YLCRB_18='1' THEN 'YLCRB_18' WHEN HW_CRBBGK.YLCRB_19='1' THEN 'YLCRB_19' WHEN HW_CRBBGK.YLCRB_20='1' THEN 'YLCRB_20' WHEN HW_CRBBGK.YLCRB_21='1' THEN 'YLCRB_21' WHEN HW_CRBBGK.YLCRB_22='1' THEN 'YLCRB_22' WHEN HW_CRBBGK.YLCRB_23='1' THEN 'YLCRB_23' WHEN HW_CRBBGK.YLCRB_24='1' THEN 'YLCRB_24' WHEN HW_CRBBGK.YLCRB_25='1' THEN 'YLCRB_25' WHEN HW_CRBBGK.YLCRB_26='1' THEN 'YLCRB_26' WHEN HW_CRBBGK.YLCRB_27='1' THEN 'YLCRB_27' WHEN HW_CRBBGK.YLCRB_28='1' THEN 'YLCRB_28' WHEN HW_CRBBGK.YLCRB_29='1' THEN 'YLCRB_29' WHEN HW_CRBBGK.YLCRB_30='1' THEN 'YLCRB_30' WHEN HW_CRBBGK.YLCRB_31='1' THEN 'YLCRB_31' WHEN HW_CRBBGK.YLCRB_32='1' THEN 'YLCRB_32' WHEN HW_CRBBGK.YLCRB_33='1' THEN 'YLCRB_33' WHEN HW_CRBBGK.YLCRB_34='1' THEN 'YLCRB_34' WHEN HW_CRBBGK.YLCRB_35='1' THEN 'YLCRB_35' WHEN HW_CRBBGK.YLCRB_36='1' THEN 'YLCRB_36' WHEN HW_CRBBGK.YLCRB_37='1' THEN 'YLCRB_37' WHEN HW_CRBBGK.YLCRB_38='1' THEN 'YLCRB_38' WHEN HW_CRBBGK.YLCRB_39='1' THEN 'YLCRB_39' WHEN HW_CRBBGK.YLCRB_40='1' THEN 'YLCRB_40' WHEN HW_CRBBGK.YLCRB_41='1' THEN 'YLCRB_41' WHEN HW_CRBBGK.YLCRB_42='1' THEN 'YLCRB_42' WHEN HW_CRBBGK.YLCRB_43='1' THEN 'YLCRB_43' WHEN HW_CRBBGK.YLCRB_44='1' THEN 'YLCRB_44' WHEN HW_CRBBGK.YLCRB_45='1' THEN 'YLCRB_44' END)) AS disease_name," +
            " '' AS disease_other," +
            " (case WHEN HW_CRBBGK.BLFL_01='1' THEN '3' WHEN HW_CRBBGK.BLFL_02='1' THEN '1' WHEN HW_CRBBGK.BLFL_03='1' THEN '6' WHEN HW_CRBBGK.BLFL_04='1' THEN '4' WHEN HW_CRBBGK.BLFL_05='1' THEN '5' END) AS diagnose_state_code," +
            " (case WHEN HW_CRBBGK.BLFL_01='1' THEN '疑是病例' WHEN HW_CRBBGK.BLFL_02='1' THEN '临床诊断病例' WHEN HW_CRBBGK.BLFL_03='1' THEN '实验室确诊' WHEN HW_CRBBGK.BLFL_04='1' THEN '病原携带者' WHEN HW_CRBBGK.BLFL_05='1' THEN '阳性检测' END) AS diagnose_state_name," +
            " (CASE WHEN HW_CRBBGK.BLFL_06='1' THEN '1' WHEN HW_CRBBGK.BLFL_07='1' THEN '2' WHEN HW_CRBBGK.BLFL_09='1' THEN '3' END) AS case_type_code," +
            " (CASE WHEN HW_CRBBGK.BLFL_06='1' THEN '急性' WHEN HW_CRBBGK.BLFL_07='1' THEN '慢性' WHEN HW_CRBBGK.BLFL_09='1' THEN '未分型' END) AS case_type_name," +
            " HW_CRBBGK.SWRQ AS dead_date," +
            " '' AS is_dead_by_this_code," +
            " '' AS is_dead_by_this_name," +
            " '' AS symptoms_code," +
            " '' AS symptoms_name," +
            " '' AS verdict_code," +
            " '' AS verdict_name," +
            " '' AS detection_positive_date," +
            " '' AS detection_org_code," +
            " '' AS dt_diagnose," +
            " '' AS afp_areatype1_code," +
            " '' AS afp_areatype1_name," +
            " '' AS afp_palsy_date," +
            " '' AS afp_doctor_date," +
            " '' AS afp_areatype2_code," +
            " '' AS afp_areatype2_name," +
            " '' AS afp_addrcode_code," +
            " '' AS afp_addrcode_name," +
            " '' AS afp_addr," +
            " '' AS afp_palsy_symptom," +
            " HW_CRBBGK.TKRQ AS report_date," +
            " '' AS discovery_mode_code," +
            " '' AS discovery_mode_name," +
            " '' AS discovery_mode_other," +
            " '' AS venereal_dis_code," +
            " '' AS venereal_dis_name," +
            " '' AS bs_transmission_code," +
            " '' AS bs_transmission_name," +
            " '' AS bs_transmission_other," +
            " '' AS contact_type_code," +
            " '' AS contact_type_name," +
            " '' AS inject_count," +
            " '' AS nonweb_count," +
            " '' AS sm_count," +
            " '' AS contact_other," +
            " '' AS sinfect_code," +
            " '' AS sinfect_name," +
            " '' AS serverity_code," +
            " '' AS serverity_name," +
            " '' AS lab_result_code," +
            " '' AS lab_result_name," +
            " '' AS hbsag_code," +
            " '' AS hbsag_name," +
            " '' AS hbsag_first," +
            " '' AS hbsag_buxiang," +
            " '' AS hbsag_alt," +
            " '' AS hbcig_result_code," +
            " '' AS hbcig_result_name," +
            " '' AS hbliver_puncture_code," +
            " '' AS hbliver_puncture_name," +
            " '' AS hbsag_changename," +
            " '' AS contactflag_code," +
            " '' AS contactflag_name," +
            " ((SELECT substr( PU_CZY.czyxm, instr( PU_CZY.czyxm, '/' ) + 1 ) FROM PU_CZY WHERE (PU_CZY.CZYBM = MZYS_BRZD.YSBM ) AND ROWNUM = 1)) AS fill_doctor," +
            " '' AS notes," +
            " '' AS ncv_severity_code," +
            " '' AS ncv_severity_name," +
            " '' AS foreign_type_code," +
            " '' AS foreign_type_name," +
            " '' AS place_code," +
            " '' AS place_name," +
            " '522632' AS report_zone_code," +
            " '贵州省榕江县' AS report_zone_name," +
            " '522632003' AS report_org_code," +
            " '榕江县中医院' AS report_org_name," +
            " GH_BRGH.GHKS AS dept_code," +
            " (SELECT PU_KS.KSMC FROM PU_KS WHERE PU_KS.KSBM=GH_BRGH.GHKS) AS dept_name," +
            " '0001' AS operator_id," +
            " SYSDATE AS operation_time " +
            "FROM" +
            "  GH_BRGH" +
            "  INNER JOIN PATIENT_ZCXX ON GH_BRGH.BRID = PATIENT_ZCXX.BRID" +
            "  INNER JOIN HW_CRBBGK ON HW_CRBBGK.HZXM = PATIENT_ZCXX.BRXM " +
            "  INNER JOIN MZYS_BRZD ON MZYS_BRZD.GHXH=GH_BRGH.GHXH " +
            "WHERE" +
            "  GH_BRGH.GHXH = #{serial_number}")
    List<PatientInfReport> queryEmrInfReportBySerialNumber(String serial_number);//查询传染病报告卡


    @Select("SELECT" +
            " jc_zd_yw.bm " +
            "FROM" +
            " jc_zd_yw " +
            "WHERE" +
            " jc_zd_yw.LXmc = '行政区划代码' " +
            " AND jc_zd_yw.mc LIKE CONCAT('%', #{addr})")
    String queryAddrCode(String addr);//查询住址区域编码


    @Select("SELECT" +
            "  GH_BRGH.GHXH AS serial_number," +
            "  PATIENT_ZCXX.BRXM AS patient_name," +
            "  PATIENT_ZCXX.SFZH AS patient_idcard," +
            "  PATIENT_ZCXX.BRXB AS gender_code," +
            "  (CASE PATIENT_ZCXX.BRXB WHEN '1' THEN '男' WHEN '2' THEN '女' ELSE '未知' END) AS gender_name," +
            "  (GH_BRGH.BRNL||(CASE GH_BRGH.BRNLDW WHEN '1' THEN '岁' WHEN '2' THEN '月' WHEN '3' THEN '天' WHEN '4' THEN '时' ELSE '分' END)) AS patient_age," +
            "  PATIENT_ZCXX.SJ AS contract_tel," +
            "  PATIENT_ZCXX.LXR AS contracts," +
            "  PATIENT_ZCXX.GZDW AS workunit " +
            "FROM " +
            "  GH_BRGH" +
            "  INNER JOIN PATIENT_ZCXX ON PATIENT_ZCXX.BRID = GH_BRGH.BRID " +
            "WHERE" +
            "  GH_BRGH.GHXH = #{serial_number}")
    ReportQueryPatientBaseInfo createReportQueryBaseInfo(String serial_number);

    @Select("SELECT COUNT(1) FROM INFECTIOUS_DISEASE")
    int queryInfectiousDiseaseCount();//查询当前报告卡数量
    
    @Select("SELECT" +
            "  addr_info.addr_code," +
            "  addr_info.addr_name " +
            "FROM " +
            "  addr_info " +
            "WHERE addr_info.addr_name LIKE #{addr} " +
            "ORDER BY " +
            "  addr_info.addr_code ASC")
    List<AddrInfo> queryAddrInfo(String addr);//查询区域信息


    /**插入逻辑**/
    @Insert("INSERT INTO INFECTIOUS_DISEASE (INFECTIOUS_DISEASE.CARD_ID, INFECTIOUS_DISEASE.SERIAL_NUMBER, INFECTIOUS_DISEASE.REPORT_TYPE," +
            "INFECTIOUS_DISEASE.PATIENT_NAME,INFECTIOUS_DISEASE.PATIENT_CONTRACT,INFECTIOUS_DISEASE.PATIENT_IDCARD_TYPE,INFECTIOUS_DISEASE.PATIENT_IDCARD," +
            "INFECTIOUS_DISEASE.PATIENT_BIRTH,INFECTIOUS_DISEASE.PATIENT_GENDER,INFECTIOUS_DISEASE.PATIENT_AGE,INFECTIOUS_DISEASE.PATIENT_AGE_TYPE," +
            "INFECTIOUS_DISEASE.WORK_UNIT,INFECTIOUS_DISEASE.CONTRACT_TEL,INFECTIOUS_DISEASE.ADDR_TYPE,INFECTIOUS_DISEASE.ADDR,INFECTIOUS_DISEASE.PERSON_TYPE," +
            "INFECTIOUS_DISEASE.PERSON_TYPE_OTHER,INFECTIOUS_DISEASE.MARITAL_STATUS,INFECTIOUS_DISEASE.EDUCATION_LEVEL,INFECTIOUS_DISEASE.DISEASE_TYPE," +
            "INFECTIOUS_DISEASE.DIAGNOSE_TYPE,INFECTIOUS_DISEASE.MORBIDITY_TIME,INFECTIOUS_DISEASE.DIAGNOSE_TIME,INFECTIOUS_DISEASE.DEATH_TIME," +
            "INFECTIOUS_DISEASE.DISEASE_A,INFECTIOUS_DISEASE.DISEASE_B,INFECTIOUS_DISEASE.DISEASE_C,INFECTIOUS_DISEASE.OTHER_DISEASE," +
            "INFECTIOUS_DISEASE.COVID_19_LEVEL,INFECTIOUS_DISEASE.COVID_19_TYPE,INFECTIOUS_DISEASE.COVID_19_OUTHOS_TIME,INFECTIOUS_DISEASE.STD_EXPOSURE_PATTERN," +
            "INFECTIOUS_DISEASE.STD_EXPOSURE_SOURCE,INFECTIOUS_DISEASE.STD_EXPOSURE_TYPE,INFECTIOUS_DISEASE.STD_EXPOSURE_SAMPLE,INFECTIOUS_DISEASE.STD_EXPOSURE_TIME," +
            "INFECTIOUS_DISEASE.STD_EXPOSURE_ORG,INFECTIOUS_DISEASE.HEPATITISB_HBSAG_UPTIME,INFECTIOUS_DISEASE.HEPATITISB_IGM1,INFECTIOUS_DISEASE.HEPATITISB_PUNCTURE_RESULT," +
            "INFECTIOUS_DISEASE.HEPATITISB_HBSAG_UPRESULT,INFECTIOUS_DISEASE.HEPATITISB_SYMPTOM_TIME,INFECTIOUS_DISEASE.HEPATITISB_ALT,INFECTIOUS_DISEASE.HFM_DISEASE_RESULT," +
            "INFECTIOUS_DISEASE.HFM_DISEASE_LEVEL,INFECTIOUS_DISEASE.MONKEYPOX_RESULT,INFECTIOUS_DISEASE.PERTUSSIS_LEVEL,INFECTIOUS_DISEASE.INTIMATE_CONTACT_SYMPTOM," +
            "INFECTIOUS_DISEASE.UPDATE_DISEASE_NAME,INFECTIOUS_DISEASE.ROLLBACK_CARD_REASON,INFECTIOUS_DISEASE.REPORT_ORG,INFECTIOUS_DISEASE.REPORT_DOCTOR," +
            "INFECTIOUS_DISEASE.REPORT_DOCTOR_TEL,INFECTIOUS_DISEASE.REPORT_CREATE_TIME,INFECTIOUS_DISEASE.REMARK) " +
            "VALUES (#{reportCardId}, #{serialNumber}, #{personType},#{patientName},#{patientContract},#{idCardType},#{idCard},TO_DATE(#{patientBirthday},'YYYY-MM-DD Hh24:MI:SS')," +
            "#{patientGender},#{patientAge},#{patientAgeType},#{workUnit},#{patientTel},#{addrType},#{currentAddrDetailed},#{personType},#{personTypeOther}," +
            "#{maritalStatus},#{educationLevel},#{illnessType},#{diagnosisType},TO_DATE(#{illnessTime},'YYYY-MM-DD Hh24:MI:SS'),TO_DATE(#{diagnosisTime},'YYYY-MM-DD Hh24:MI:SS')," +
            "TO_DATE(#{deathTime},'YYYY-MM-DD Hh24:MI:SS'),#{diseaseA},#{diseaseB},#{diseaseC},#{diseaseD},#{covid19Level},#{covid19Type}," +
            "TO_DATE(#{covid19OutHosTime},'YYYY-MM-DD Hh24:MI:SS'),#{stdExposurePattern},#{stdExposureSource},#{stdExposureType},#{stdExposureSample}," +
            "TO_DATE(#{stdExposureTime},'YYYY-MM-DD Hh24:MI:SS'),#{stdExposureOrg},TO_DATE(#{hepatitisBHBsAgTime},'YYYY-MM-DD Hh24:MI:SS'),#{hepatitisBIgM1}," +
            "#{hepatitisBPunctureResult},#{hepatitisBHBsAgResult},TO_DATE(#{hepatitisBTime},'YYYY-MM-DD Hh24:MI:SS'),#{hepatitisBAlt},#{hfmDiseaseResult},#{hfmDiseaseLevel}," +
            "#{monkeyPoxResult},#{pertussisLevel},#{intimateContactSymptom},#{updateDiseaseName},#{rollbackCardReason},#{reportOrg},#{reportDoctor},#{reportDoctorTel}," +
            "TO_DATE(#{reportCreateTime},'YYYY-MM-DD Hh24:MI:SS'),#{remark})")
    boolean createCReportCard(Map<String,Object> map);//报告卡填写写入
    
    @Select("SELECT" +
            "  INFECTIOUS_DISEASE.CARD_ID AS reportCardId," +
            "  INFECTIOUS_DISEASE.REPORT_TYPE AS reportCardType," +
            "  INFECTIOUS_DISEASE.SERIAL_NUMBER AS serialNumber," +
            "  INFECTIOUS_DISEASE.Patient_Name AS patientName," +
            "  INFECTIOUS_DISEASE.Patient_Gender AS patientGender," +
            "  INFECTIOUS_DISEASE.Patient_Age AS patientAge," +
            "  INFECTIOUS_DISEASE.Contract_Tel AS patientTel," +
            "  INFECTIOUS_DISEASE.Patient_Contract AS patientContract," +
            "  INFECTIOUS_DISEASE.Patient_Birth AS patientBirthday," +
            "  INFECTIOUS_DISEASE.Patient_Idcard_Type AS idCardType," +
            "  INFECTIOUS_DISEASE.Patient_Idcard AS idCard," +
            "  INFECTIOUS_DISEASE.Work_Unit AS workUnit," +
            "  (SELECT ADDR_INFO.ADDR_NAME FROM ADDR_INFO WHERE ADDR_INFO.ADDR_CODE=INFECTIOUS_DISEASE.Addr) AS currentAddrDetailed," +
            "  INFECTIOUS_DISEASE.Morbidity_Time AS illnessTime," +
            "  INFECTIOUS_DISEASE.Diagnose_Time AS diagnosisTime," +
            "  INFECTIOUS_DISEASE.Death_Time AS deathTime," +
            "  INFECTIOUS_DISEASE.Addr_Type AS addrType," +
            "  INFECTIOUS_DISEASE.Person_Type AS personType," +
            "  INFECTIOUS_DISEASE.Person_Type_Other AS personTypeOther," +
            "  INFECTIOUS_DISEASE.Disease_Type," +
            "  INFECTIOUS_DISEASE.Diagnose_Type AS diagnosisType," +
            "  INFECTIOUS_DISEASE.Disease_a AS diseaseA," +
            "  INFECTIOUS_DISEASE.Disease_b AS diseaseB," +
            "  INFECTIOUS_DISEASE.Disease_c AS diseaseC," +
            "  INFECTIOUS_DISEASE.Other_Disease AS diseaseD," +
            "  INFECTIOUS_DISEASE.Marital_Status AS maritalStatus," +
            "  INFECTIOUS_DISEASE.Education_Level AS educationLevel," +
            "  INFECTIOUS_DISEASE.Covid_19_Level AS covid19Level," +
            "  INFECTIOUS_DISEASE.Covid_19_Type AS covid19Type," +
            "  INFECTIOUS_DISEASE.Covid_19_Outhos_Time AS covid19OutHosTime," +
            "  INFECTIOUS_DISEASE.Std_Exposure_Pattern AS stdExposurePattern," +
            "  INFECTIOUS_DISEASE.Std_Exposure_Source AS stdExposureSource," +
            "  INFECTIOUS_DISEASE.Std_Exposure_Type AS stdExposureType," +
            "  INFECTIOUS_DISEASE.Std_Exposure_Org AS stdExposureOrg," +
            "  INFECTIOUS_DISEASE.Std_Exposure_Time AS stdExposureTime," +
            "  INFECTIOUS_DISEASE.Std_Exposure_Sample AS stdExposureSample," +
            "  INFECTIOUS_DISEASE.Hepatitisb_Hbsag_Uptime AS hepatitisBHBsAgTime," +
            "  INFECTIOUS_DISEASE.Hepatitisb_Igm1 AS hepatitisBIgM1," +
            "  INFECTIOUS_DISEASE.Hepatitisb_Puncture_Result AS hepatitisBPunctureResult," +
            "  INFECTIOUS_DISEASE.Hepatitisb_Hbsag_Upresult AS hepatitisBHBsAgResult," +
            "  INFECTIOUS_DISEASE.Hepatitisb_Symptom_Time AS hepatitisBTime," +
            "  INFECTIOUS_DISEASE.Hepatitisb_Alt AS hepatitisBAlt," +
            "  INFECTIOUS_DISEASE.Hfm_Disease_Result AS hfmDiseaseResult," +
            "  INFECTIOUS_DISEASE.Hfm_Disease_Level AS hfmDiseaseLevel," +
            "  INFECTIOUS_DISEASE.Monkeypox_Result AS monkeyPoxResult," +
            "  INFECTIOUS_DISEASE.Pertussis_Level AS pertussisLevel," +
            "  INFECTIOUS_DISEASE.Intimate_Contact_Symptom AS intimateContactSymptom," +
            "  INFECTIOUS_DISEASE.Update_Disease_Name AS updateDiseaseName," +
            "  INFECTIOUS_DISEASE.Rollback_Card_Reason AS rollbackCardReason," +
            "  INFECTIOUS_DISEASE.Report_Org AS reportOrg," +
            "  INFECTIOUS_DISEASE.Report_Doctor AS reportDoctor," +
            "  INFECTIOUS_DISEASE.Report_Doctor_Tel AS reportDoctorTel," +
            "  INFECTIOUS_DISEASE.Report_Create_Time AS reportCreateTime," +
            "  INFECTIOUS_DISEASE.Remark AS remark " +
            "FROM" +
            "  INFECTIOUS_DISEASE " +
            "WHERE" +
            "  INFECTIOUS_DISEASE.SERIAL_NUMBER IN (#{serialNumber})")
    ReportCardBody queryReportCard(String serialNumber);//查询传染病报告卡

    @Select("SELECT " +
            "MZYS_JYSQ.Ryghxh AS serialNumber," +
            "(SELECT patient_zcxx.brxm FROM patient_zcxx WHERE patient_zcxx.brid=gh_brgh.brid) AS patientName," +
            "(SELECT DECODE(patient_zcxx.brxb,'1','男','2','女','3','未知') FROM patient_zcxx WHERE patient_zcxx.brid=gh_brgh.brid) AS patientGender," +
            "(gh_brgh.brnl||DECODE(gh_brgh.brnldw,'1','岁','2','月','3','天','4','时')) AS patientAge," +
            "'' AS patientBedNum," +
            "(MZYS_BRWZ.Cbzd||','||MZYS_BRWZ.Qtzd||','||MZYS_BRWZ.Qtzd2||','||MZYS_BRWZ.Qtzd3) AS diagnose,"+
            "(SELECT pu_czy.czyxm FROM pu_czy WHERE pu_czy.czybm=MZYS_JYSQ.Sqys) AS doctorName," +
            "(SELECT pu_ks.ksmc FROM pu_ks WHERE pu_ks.ksbm=MZYS_JYSQ.Mzks) AS doctorDepartment," +
            "(SELECT patient_zcxx.sj FROM patient_zcxx WHERE patient_zcxx.brid=gh_brgh.brid) AS patientTel " +
            "FROM MZYS_JYSQ " +
            "INNER JOIN MZYS_JYSQMX ON MZYS_JYSQMX.JYSQH=MZYS_JYSQ.JYSQH " +
            "INNER JOIN gh_brgh ON gh_brgh.ghxh=MZYS_JYSQ.Ryghxh " +
            "INNER JOIN MZYS_BRWZ ON MZYS_BRWZ.GHXH=MZYS_JYSQ.Ryghxh "+
            "WHERE (MZYS_JYSQ.JYSQH=#{queryKey} OR MZYS_JYSQ.Ryghxh=#{queryKey}) " +
            "AND ROWNUM=1" +
            "UNION ALL " +
            "SELECT " +
            "inhos_rydj.zyh AS serialNumber," +
            "(SELECT patient_zcxx.brxm FROM patient_zcxx WHERE patient_zcxx.brid=inhos_rydj.brid) AS patientName," +
            "(SELECT DECODE(patient_zcxx.brxb,'1','男','2','女','3','未知') FROM patient_zcxx WHERE patient_zcxx.brid=inhos_rydj.brid) AS patientGender," +
            "(inhos_rydj.brnl||DECODE(inhos_rydj.brnldw,'1','岁','2','月','3','天','4','时')) AS patientAge," +
            "(SELECT INHOS_CWH.CWBH FROM INHOS_CWH WHERE INHOS_CWH.CWID=inhos_rydj.rycwid) AS patientBedNum," +
            "inhos_rydj.mzxyzdmc AS diagnose,"+
            "(SELECT pu_czy.czyxm FROM pu_czy WHERE pu_czy.czybm=inhos_rydj.zyys) AS doctorName," +
            "(SELECT pu_ks.ksmc FROM pu_ks WHERE pu_ks.ksbm=inhos_rydj.ryks) AS doctorDepartment," +
            "(SELECT patient_zcxx.sj FROM patient_zcxx WHERE patient_zcxx.brid=inhos_rydj.brid) AS patientTel " +
            "FROM inhos_rydj WHERE inhos_rydj.zyh=#{queryKey}")
    PathologyPatientInfoBean queryPathology(String queryKey);//病理申请患者信息查询


    boolean releaseYfClock(String requestNum);//解除药房锁

    List<BedDayBean> QueryBedDay(BedDayBody queryMap);//床日数
}
