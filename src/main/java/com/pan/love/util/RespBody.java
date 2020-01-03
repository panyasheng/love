package com.pan.love.util;

import lombok.Data;
import net.minidev.json.JSONObject;

import java.util.Map;


/**
 * 返回格式
 *
 * @author pan
 * @date 2019/10/16
 */
@Data
public class RespBody {

    public String code;

    public Object msg;

    public Object data="";

    public int total;

    public RespBody(){
        this.code=RespBodyEnum.SUCCESS.getCode();
        this.msg=RespBodyEnum.SUCCESS.getMsg();
    }

    public RespBody(Object data){
        this();
        this.data=data;
    }

    public RespBody(String code,Object msg){
        this();
        this.msg=msg;
        this.code=code;
    }

    public RespBody(String code,Object msg,Object data){
        this();
        this.msg=msg;
        this.code=code;
        this.data=data;
    }

    /**
     * 成功
     *
     * @return
     */
    public static RespBody success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static RespBody success(Object data) {
        RespBody rb = new RespBody();
        rb.setCode(RespBodyEnum.SUCCESS.getCode());
        rb.setMsg(RespBodyEnum.SUCCESS.getMsg());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static RespBody error(String code, Object msg) {
        RespBody rb = new RespBody();
        rb.setCode(code);
        rb.setMsg(msg);
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static RespBody error( Object msg) {
        RespBody rb = new RespBody();
        rb.setCode("-1");
        rb.setMsg(msg);
        rb.setData(null);
        return rb;
    }

    public static RespBody error(RespBodyEnum internalServerError) {
        RespBody respBody=new RespBody();
        respBody.setCode(internalServerError.getCode());
        respBody.setMsg(internalServerError.getMsg());
        respBody.setData(null);
        return respBody;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString((Map<String, ? extends Object>) this);
    }
}
