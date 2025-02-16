package com.cloudestudio.hosgetserver.webTools;

/**
 * @Class ResponseCode
 * @Author Create By Matrix·张
 * @Date 2025/2/16 下午10:10
 * 返回代码枚举
 */
public enum ResponseCode {

    FAILED(404,"请求失败!"),
    SUCCESS(200,"请求成功!"),
    NO_LOGIN(405,"未登录"),
    TOKEN_ERROR(501,"令牌错误"),
    TOKEN_EXPIRED(502,"令牌过期"),
    PARAM_ERROR(505,"请求参数异常!");


    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
