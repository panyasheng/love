package com.pan.love.util;
/**
 * 响应码枚举类
 *
 * @author pan
 * @date 2019/10/22
 */
public enum RespBodyEnum {

    /**成功*/
    SUCCESS("0","success"),
    /**失败*/
    FAIL("-1","fail"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503","服务器正忙，请稍后再试!");

    private String code;
    private String msg;

    RespBodyEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
