package com.cloudestudio.hosgetserver.model.paramBody;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class BedDayBody
 * @Author Create By Matrix·张
 * @Date 2025/4/6 下午3:49
 * 床日数参数类
 */
@Data
public class BedDayBody implements Serializable {
    private String startTime;
    private String endTime;
}
