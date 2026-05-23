package com.cloudestudio.hosgetserver.service.GreenChannel;

import com.cloudestudio.hosgetserver.model.greenChannel.PatientBean;

/**
 * @Class greenChannelHandleService
 * @Author Create By Matrix·张
 * @Date 2026/5/23 下午2:32
 */
public interface greenChannelHandleService {
    PatientBean queryPatient(String idCard);
    PatientBean queryPatientByHis(String idCard);
}
