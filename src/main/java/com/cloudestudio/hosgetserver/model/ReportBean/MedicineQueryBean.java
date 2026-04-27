package com.cloudestudio.hosgetserver.model.ReportBean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class MedicineQueryBean
 * @Author Create By Matrix·张
 * @Date 2026/4/24 上午10:02
 * 追溯码查漏实体
 */
@Data
public class MedicineQueryBean implements Serializable {
    private String YBJSID;
    private String JSJLID;
    private String MSG;
    private String CFH;
    private String RYYPBM;
    private String MEDICINE;
    private String PHARMACY;
}
