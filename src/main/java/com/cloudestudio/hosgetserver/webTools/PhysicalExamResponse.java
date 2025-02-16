package com.cloudestudio.hosgetserver.webTools;

import lombok.Data;

/**
 * @Class PhysicalExamResponse
 * @Author Create By Matrix·张
 * @Date 2025/2/15 下午5:22
 * 体检返回信息类
 */
@Data
public class PhysicalExamResponse {
    private boolean success;
    private String message;
    private int code;
    private Object content;

    private PhysicalExamResponse(){};

    public static PhysicalExamResponse success(Object data){
        PhysicalExamResponse resultResponse=new PhysicalExamResponse();
        resultResponse.setSuccess(true);
        resultResponse.setMessage(ResponseCode.SUCCESS.getMsg());
        resultResponse.setCode(ResponseCode.SUCCESS.getCode());
        resultResponse.setContent(data);
        return resultResponse;
    }
    public static PhysicalExamResponse failure(){
        PhysicalExamResponse resultResponse=new PhysicalExamResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage(ResponseCode.FAILED.getMsg());
        resultResponse.setCode(ResponseCode.FAILED.getCode());
        resultResponse.setContent(null);
        return resultResponse;
    }
    public static PhysicalExamResponse paramError(){
        PhysicalExamResponse resultResponse=new PhysicalExamResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage(ResponseCode.PARAM_ERROR.getMsg());
        resultResponse.setCode(ResponseCode.PARAM_ERROR.getCode());
        resultResponse.setContent(null);
        return resultResponse;
    }
}
