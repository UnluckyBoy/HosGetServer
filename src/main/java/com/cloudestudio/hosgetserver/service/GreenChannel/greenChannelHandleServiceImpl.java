package com.cloudestudio.hosgetserver.service.GreenChannel;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.greenChannel.PatientBean;
import com.cloudestudio.hosgetserver.model.mapper.GreenChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Class greenChannelHandleServiceImpl
 * @Author Create By Matrix·张
 * @Date 2026/5/23 下午2:32
 */
@Service("greenChannelHandleService")
public class greenChannelHandleServiceImpl implements greenChannelHandleService {
    @Autowired
    GreenChannelMapper greenChannelMapper;

    @DS("mysql")
    @Override
    public PatientBean queryPatient(String idCard) {
        return greenChannelMapper.queryPatient(idCard);
    }

    /**查询His端信息**/
    @DS("oracle")
    @Override
    public PatientBean queryPatientByHis(String idCard) {
        return greenChannelMapper.queryPatientByHis(idCard);
    }
}
