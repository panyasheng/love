package com.pan.love.framework.exception;
/**
 * 自定义统一异常
 *
 * @author pan
 * @date 2019/10/22
 */
public class LoveException extends RuntimeException {
    /**错误码*/
    protected String code;
    /**错误信息*/
    protected Object msg;

    public LoveException(){
        super();
    }

    public LoveException(Object msg) {
        this.code = "-1";
        this.msg=msg;
    }

    public LoveException(String code,Object msg) {
        super(msg+"");
        this.code = code;
        this.msg=msg;

    }

    public LoveException(Object msg, Throwable cause, String code) {
        super(msg+"", cause);
        this.code = code;
    }

    public LoveException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public LoveException(Object msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(msg+"", cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
