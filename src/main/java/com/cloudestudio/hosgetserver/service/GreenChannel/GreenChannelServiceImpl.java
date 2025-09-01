package com.cloudestudio.hosgetserver.service.GreenChannel;

import com.cloudestudio.hosgetserver.model.greenChannel.CreateHisBean;
import com.cloudestudio.hosgetserver.model.greenChannel.GreenChannelCaseHis;
import com.cloudestudio.hosgetserver.model.greenChannel.PatientBean;
import com.cloudestudio.hosgetserver.model.mapper.GreenChannelMapper;
import com.cloudestudio.hosgetserver.model.paramBody.BedDayBody;
import com.cloudestudio.hosgetserver.model.paramBody.GreenChannelCreateParamBody;
import com.cloudestudio.hosgetserver.webTools.UUIDNumberUtil;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Class CreateCaseHisServiceImpl
 * @Author Create By Matrix·张
 * @Date 2025/6/15 下午10:13
 * 分诊上传服务实现
 */
@Service("GreenChannelService")
public class GreenChannelServiceImpl implements GreenChannelService {
    @Autowired
    GreenChannelMapper greenChannelMapper;

    /**
     * 查询患者信息
     * @param idCard
     * @return
     */
    @Override
    public WebResponse queryPatientInfo(String idCard) {
        if(idCard == null){
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息失败!--->>>参数:"+ null);
            return WebResponse.paramError();
        }else{
            PatientBean queryBean=greenChannelMapper.queryPatient(idCard);
            if(queryBean==null){
                System.out.println(TimeUtil.GetTime(true)+" ---查询患者-失败!--->>>参数:"+ null);
                return WebResponse.serverError("患者不存在");
            }else{
                System.out.println(TimeUtil.GetTime(true)+" ---查询患者-成功!--->>>参数:"+ queryBean);
                return WebResponse.success(queryBean);
            }
        }
    }

    /**
     * 绿色通道注册患者逻辑
     * @param requestBody
     * @return
     */
    @Override
    public WebResponse createPatientInfo(GreenChannelCreateParamBody requestBody) {
        if(requestBody == null){
            System.out.println(TimeUtil.GetTime(true)+" ---查询患者基本信息失败!--->>>参数:"+ null);
            return WebResponse.paramError();
        }else{
            if(Objects.equals(requestBody.getPersonType(), "2")){
                System.out.println(TimeUtil.GetTime(true)+" ---三无人员requestBody--->>>参数:"+ requestBody);
                //return WebResponse.paramError();
                PatientBean createBean=classHandle(requestBody);
                boolean cKey=greenChannelMapper.cGCPatientInfoTN(createBean);
                if(cKey){
                    System.out.println(TimeUtil.GetTime(true)+" ---创建患者基本信息-成功!--->>>参数:"+ createBean);
                    CreateHisBean createHisBean=classHandle_GC(requestBody,createBean.getPatientId());
                    int mRepeat=greenChannelMapper.queryRepeatHis(createBean.getPatientId());//判断患者id是否在当日分诊
                    if(mRepeat==0){
                        System.out.println(TimeUtil.GetTime(true)+" ---创建分诊档案--->>>参数:"+ createHisBean);
                        boolean mKey=greenChannelMapper.cGCCaseHis(createHisBean);
                        if(mKey) {
                            System.out.println(TimeUtil.GetTime(true) + " ---创建分诊档案-->>成功!");
                            return WebResponse.success();
                        }else {
                            System.out.println(TimeUtil.GetTime(true) + " ---创建分诊档案-->>失败!");
                            return WebResponse.serverError("参数:"+createBean.getPatientId());
                        }
                    }else {
                        System.out.println(TimeUtil.GetTime(true) + " ---创建分诊档案-->>失败!-->>>重复分诊!");
                        return WebResponse.repeatError("参数:" + createBean.getPatientId() + "-->>>重复分诊!");
                    }
                }else{
                    System.out.println(TimeUtil.GetTime(true)+" ---创建患者基本信息-失败!--->>>参数:"+ createBean);
                    return WebResponse.serverError("参数:"+createBean.getPatientId()+"-->>>创建异常!");
                }
            }else{
                System.out.println(TimeUtil.GetTime(true)+" ---requestBody--->>>参数:"+ requestBody);
                String param=requestBody.getIdCard();
                PatientBean temp=greenChannelMapper.queryPatient(param);
                if(temp==null){
                    PatientBean createBean=classHandle(requestBody);
                    boolean cKey=greenChannelMapper.cGCPatientInfo(createBean);
                    if(cKey){
                        System.out.println(TimeUtil.GetTime(true)+" ---创建患者基本信息-成功!--->>>参数:"+ createBean);
                        CreateHisBean createHisBean=classHandle_GC(requestBody,createBean.getPatientId());
                        int mRepeat=greenChannelMapper.queryRepeatHis(createBean.getPatientId());//判断患者id是否在当日分诊
                        if(mRepeat==0){
                            System.out.println(TimeUtil.GetTime(true)+" ---创建分诊档案--->>>参数:"+ createHisBean);
                            boolean mKey=greenChannelMapper.cGCCaseHis(createHisBean);
                            if(mKey) {
                                System.out.println(TimeUtil.GetTime(true) + " ---创建分诊档案-->>成功!");
                                return WebResponse.success();
                            }else {
                                System.out.println(TimeUtil.GetTime(true) + " ---创建分诊档案-->>失败!");
                                return WebResponse.serverError("参数:"+param);
                            }
                        }else {
                            System.out.println(TimeUtil.GetTime(true) + " ---创建分诊档案-->>失败!-->>>重复分诊!");
                            return WebResponse.repeatError("参数:" + param + "-->>>重复分诊!");
                        }
                    }else{
                        System.out.println(TimeUtil.GetTime(true)+" ---创建患者基本信息-失败!--->>>参数:"+ createBean);
                        return WebResponse.serverError("参数:"+param+"-->>>创建异常!");
                    }
                }else{
                    System.out.println(TimeUtil.GetTime(true)+" ---患者基本信息已存在!--->>>参数:"+ temp);
                    CreateHisBean createHisBean=classHandle_GC(requestBody,temp.getPatientId());
                    int mRepeat=greenChannelMapper.queryRepeatHis(createHisBean.getPatientId());//判断患者id是否在当日分诊
                    if(mRepeat==0){
                        System.out.println(TimeUtil.GetTime(true)+" ---创建分诊档案--->>>参数:"+ createHisBean);
                        boolean mKey=greenChannelMapper.cGCCaseHis(createHisBean);
                        if(mKey) {
                            System.out.println(TimeUtil.GetTime(true) + " ---创建分诊档案-->>成功!");
                            return WebResponse.success();
                        }else {
                            System.out.println(TimeUtil.GetTime(true) + " ---创建分诊档案-->>失败!");
                            return WebResponse.serverError("参数:"+param);
                        }
                    }else {
                        System.out.println(TimeUtil.GetTime(true) + " ---创建分诊档案-->>失败!-->>>重复分诊!");
                        return WebResponse.repeatError("参数:" + param + "-->>>重复分诊!");
                    }
                }
            }
        }
    }

    /**
     * 分诊档案查询-全部
     * @return
     */
    @Override
    public WebResponse queryAllHis() {
        List<GreenChannelCaseHis> greenChannelCaseHisList=greenChannelMapper.queryAllHis();
        if(greenChannelCaseHisList==null||greenChannelCaseHisList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true) + " ---查询分诊档案-->>失败!");
            return WebResponse.serverError("");
        }else{
            System.out.println(TimeUtil.GetTime(true) + " ---查询分诊档案-->>成功!");
            return WebResponse.success(greenChannelCaseHisList);
        }
    }

    /**
     * 分诊档案查询-条件
     * @param requestBody
     * @return
     */
    @Override
    public WebResponse queryHisParam(BedDayBody requestBody) {
        List<GreenChannelCaseHis> greenChannelCaseHisList=greenChannelMapper.queryHisParam(requestBody.getStartTime(),requestBody.getEndTime());
        if(greenChannelCaseHisList==null||greenChannelCaseHisList.isEmpty()){
            System.out.println(TimeUtil.GetTime(true) + " ---查询分诊档案-->>失败!");
            return WebResponse.serverError("");
        }else{
            System.out.println(TimeUtil.GetTime(true) + " ---查询分诊档案-->>成功!");
            return WebResponse.success(greenChannelCaseHisList);
        }
    }

    /**
     * 实体类转化
     * GreenChannelCreateParamBody->PatientBean
     * @param requestBody
     * @return
     */
    private PatientBean classHandle(GreenChannelCreateParamBody requestBody){
        PatientBean createBean=new PatientBean();
        createBean.setInfoKey(requestBody.getPersonType());
        createBean.setPatientId(UUIDNumberUtil.randUUIDMediumNumber());
        createBean.setIdCardTypeName(requestBody.getIdCardTypeName());
        createBean.setPatientName(requestBody.getPatientName());
        createBean.setIdCard(requestBody.getIdCard());
        createBean.setBirthDate(requestBody.getBirthDate());
        createBean.setTel(requestBody.getTel());
        createBean.setPatientAge(requestBody.getPatientAge());
        createBean.setContacts(requestBody.getContacts());
        createBean.setContactsRelation(requestBody.getContactsRelation());
        createBean.setNationalityName(requestBody.getNationalityName());
        createBean.setNationName(requestBody.getNationName());
        createBean.setGenderName(requestBody.getGenderName());
        createBean.setPatientAddress(requestBody.getPatientAddress());
        return createBean;
    }

    /**
     * 实体类转化
     * GreenChannelCreateParamBody->CreateHisBean
     * @param requestBody
     * @param patientId
     * @return
     */
    private CreateHisBean classHandle_GC(GreenChannelCreateParamBody requestBody,String patientId){
        CreateHisBean tempBean=new CreateHisBean();
        //tempBean.setRecordNumber(TimeUtil.timeToString(requestBody.getRegisterTime()));
        tempBean.setRecordNumber(TimeUtil.timeToString(requestBody.getRegisterTime())+greenChannelMapper.queryHisNum());
        tempBean.setPatientId(patientId);
        tempBean.setMorbidity(requestBody.getMorbidity());
        tempBean.setInHosType(requestBody.getInHosType());
        tempBean.setToHosTime(requestBody.getToHosTime());
        tempBean.setVitalSigns(requestBody.getVitalSigns());
        tempBean.setSystolicPressure(requestBody.getSystolicPressure());
        tempBean.setDiastolicPressure(requestBody.getDiastolicPressure());
        tempBean.setBreathing(requestBody.getBreathing());
        tempBean.setBloodSugar(requestBody.getBloodSugar());
        tempBean.setTemperature(requestBody.getTemperature());
        tempBean.setHeartRate(requestBody.getHeartRate());
        tempBean.setPulse(requestBody.getPulse());
        tempBean.setBOxygenSaturation(requestBody.getBloodOxygen());
        tempBean.setLevelConsciousness(requestBody.getLevelConsciousness());
        tempBean.setTriageType(requestBody.getTriageType());
        tempBean.setTriageArea(requestBody.getTriageArea());
        tempBean.setTriageDepartment(requestBody.getTriageDepartment());
        tempBean.setIsGreenChannel(requestBody.getIsGreenChannel());
        tempBean.setDiseaseType(requestBody.getDiseaseType());
        tempBean.setDescriptionType(requestBody.getDescriptionType());
        tempBean.setDescription(requestBody.getDescription());
        tempBean.setCreateOperator(requestBody.getCreateOperator());
        tempBean.setCreateTime(requestBody.getCreateTime());
        tempBean.setCaseTime(String.valueOf(TimeUtil.calculateTimeSeconds(requestBody.getRegisterTime(),requestBody.getCreateTime(),"second")));
        return tempBean;
    }

}
