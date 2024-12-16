package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.HosDataBean;
import com.cloudestudio.hosgetserver.model.PatientActivityBean;
import com.cloudestudio.hosgetserver.model.PatientBaseInfoBean;
import com.cloudestudio.hosgetserver.model.PatientInfReport;
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

    List<PatientInfReport> queryEmrInfReport();//查询传染病报告卡
}
