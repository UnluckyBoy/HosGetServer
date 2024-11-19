package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据体类-院内会诊
 *
 * @author Matrix·张
 * @Date 2024/11/13 下午1:57
 */
@Data
public class HosDataBean implements Serializable {
    private String hos_number;//住院号
    private String patient_name;//姓名
    private String patient_age;//年龄
    private String create_office;//发起科室
    private String create_doctor;//发起医生
    private String inspect_office;//会诊科室
    private String inspect_doctor;//会诊医生
    private String create_time;//发起日期
    private String finish_time;//会诊日期
    private String use_time;//会诊用时
    private String finish_type;//完成标志
}
