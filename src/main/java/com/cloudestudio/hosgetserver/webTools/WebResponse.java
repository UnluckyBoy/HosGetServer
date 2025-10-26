package com.cloudestudio.hosgetserver.webTools;

import lombok.Data;

/**
 * @Class PhysicalExamResponse
 * @Author Create By Matrix·张
 * @Date 2025/2/15 下午5:22
 * 体检返回信息类
 */
@Data
public class WebResponse {
    private boolean success;
    private String message;
    private int code;
    private Object content;

    private WebResponse(){};

    public static WebResponse success(){
        WebResponse resultResponse=new WebResponse();
        resultResponse.setSuccess(true);
        resultResponse.setMessage(ResponseCode.SUCCESS.getMsg());
        resultResponse.setCode(ResponseCode.SUCCESS.getCode());
        resultResponse.setContent(null);
        return resultResponse;
    }

    public static WebResponse success(Object data){
        WebResponse resultResponse=new WebResponse();
        resultResponse.setSuccess(true);
        resultResponse.setMessage(ResponseCode.SUCCESS.getMsg());
        resultResponse.setCode(ResponseCode.SUCCESS.getCode());
        resultResponse.setContent(data);
        return resultResponse;
    }
    public static WebResponse failure(){
        WebResponse resultResponse=new WebResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage(ResponseCode.FAILED.getMsg());
        resultResponse.setCode(ResponseCode.FAILED.getCode());
        resultResponse.setContent(null);
        return resultResponse;
    }
    public static WebResponse serverError(String msg){
        WebResponse resultResponse=new WebResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage(ResponseCode.SERVER_ERROR.getMsg()+"-->>>"+msg);
        resultResponse.setCode(ResponseCode.SERVER_ERROR.getCode());
        resultResponse.setContent(null);
        return resultResponse;
    }
    public static WebResponse paramError(){
        WebResponse resultResponse=new WebResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage(ResponseCode.PARAM_ERROR.getMsg());
        resultResponse.setCode(ResponseCode.PARAM_ERROR.getCode());
        resultResponse.setContent(null);
        return resultResponse;
    }
    public static WebResponse queryError(String msg){
        WebResponse resultResponse=new WebResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage(ResponseCode.QUERY_ERROR.getMsg()+"-->>>"+msg);
        resultResponse.setCode(ResponseCode.QUERY_ERROR.getCode());
        resultResponse.setContent(null);
        return resultResponse;
    }
    public static WebResponse repeatError(String msg){
        WebResponse resultResponse=new WebResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage(ResponseCode.REPEAT_ERROR.getMsg()+"-->>>"+msg);
        resultResponse.setCode(ResponseCode.REPEAT_ERROR.getCode());
        resultResponse.setContent(null);
        return resultResponse;
    }

    /**
     * 查询数据为0
     * @return
     */
    public static WebResponse queryZeroResult(Object object){
        WebResponse resultResponse=new WebResponse();
        resultResponse.setSuccess(false);
        resultResponse.setMessage(ResponseCode.ZERO_RESULT.getMsg());
        resultResponse.setCode(ResponseCode.QUERY_ERROR.getCode());
        resultResponse.setContent(object);
        return resultResponse;
    }
}
