package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class BedDayBean
 * @Author Create By Matrix·张
 * @Date 2025/4/6 下午3:39
 * 床日数类
 */
@Data
public class BedDayBean implements Serializable {
    private String Doctor;
    private String InhosNumber;
    private String InsuranceType;
    private String OnhosTime;
    private String DepartMent;
    private String InhosTime;
}
