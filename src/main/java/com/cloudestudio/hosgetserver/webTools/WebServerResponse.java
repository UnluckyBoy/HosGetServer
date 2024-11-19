package com.cloudestudio.hosgetserver.webTools;

import lombok.Data;

/**
 * @Class WebServerResponse
 * @Author Create By Matrix·张
 * @Date 2024/11/13 下午3:39
 * 后台返回类
 */
@Data
public class WebServerResponse {
    private boolean handleType;//处理状态
    private int handleCode;//处理代码
    private String handleMessage;//处理描述
    private Object handleData;//处理数据

    private WebServerResponse(){};

    public static WebServerResponse error(){
        WebServerResponse resultResponse=new WebServerResponse();
        resultResponse.setHandleType(false);
        resultResponse.setHandleCode(404);
        resultResponse.setHandleMessage("请求错误");
        resultResponse.setHandleData(null);
        return resultResponse;
    }
    public static WebServerResponse success(){
        WebServerResponse resultResponse=new WebServerResponse();
        resultResponse.setHandleType(true);
        resultResponse.setHandleCode(200);
        resultResponse.setHandleMessage("请求成功");
        resultResponse.setHandleData(null);
        return resultResponse;
    }
    public static WebServerResponse success(String message){
        WebServerResponse resultResponse=new WebServerResponse();
        resultResponse.setHandleType(true);
        resultResponse.setHandleCode(200);
        resultResponse.setHandleMessage(message);
        resultResponse.setHandleData(null);
        return resultResponse;
    }
    public static WebServerResponse success(String message,Object object){
        WebServerResponse resultResponse=new WebServerResponse();
        resultResponse.setHandleType(true);
        resultResponse.setHandleCode(200);
        resultResponse.setHandleMessage(message);
        resultResponse.setHandleData(object);
        return resultResponse;
    }
    public static WebServerResponse failure(){
        WebServerResponse resultResponse=new WebServerResponse();
        resultResponse.setHandleType(false);
        resultResponse.setHandleCode(404);
        resultResponse.setHandleMessage("内部错误");
        resultResponse.setHandleData(null);
        return resultResponse;
    }
//    public static WebServerResponse failure(AuthenticationException exception){
//        WebServerResponse resultResponse=new WebServerResponse();
//        resultResponse.setHandleType(false);
//        resultResponse.setHandleCode(404);
//        resultResponse.setHandleMessage(MessageUtil.Message(exception));
//        resultResponse.setHandleData(null);
//        return resultResponse;
//    }
    public static WebServerResponse failure(String message){
        WebServerResponse resultResponse=new WebServerResponse();
        resultResponse.setHandleType(false);
        resultResponse.setHandleCode(404);
        resultResponse.setHandleMessage(message);
        resultResponse.setHandleData(null);
        return resultResponse;
    }
}
