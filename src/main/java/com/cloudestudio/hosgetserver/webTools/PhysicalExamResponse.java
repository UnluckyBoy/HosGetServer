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
        resultResponse.setMessage("请求成功!");
        resultResponse.setCode(200);
        resultResponse.setContent(data);
        return resultResponse;
    }
    public static PhysicalExamResponse failure(){
        PhysicalExamResponse resultResponse=new PhysicalExamResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage("请求失败!");
        resultResponse.setCode(404);
        resultResponse.setContent(null);
        return resultResponse;
    }
}
