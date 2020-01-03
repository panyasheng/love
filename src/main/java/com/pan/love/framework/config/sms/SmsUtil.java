package com.pan.love.framework.config.sms;

import com.pan.love.framework.config.redis.JedisClient;
import com.pan.love.framework.config.redis.JedisClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 短信验证码工具(容联云通讯)
 *
 * @author pan
 * @date 2019/11/6
 */
public class SmsUtil {
    private static final String ACCOUNT_SID="8aaf070860e4708e0160e4c414c0006f";
    private static final String AUTH_TOKEN="a4a7a439d339428bb59d730bc4608c9a";
    private static final String REST_URL="https://app.cloopen.com:8883";
    private static final String APP_ID="8aaf070860e4708e0160e4c415260076";

    static final Logger logger= LoggerFactory.getLogger(SmsUtil.class);


    /**
     * 发送短信
     * @param phone 发送的电话号码
     * @param templateId 模板的id
     * @param templateContent 模板占位符的内容
     * @return 发送短信是否成功，true成功；false失败
     */
    public static boolean SendSMS(String phone,String templateId,String[] templateContent){
        boolean b=false;
        Map<String,Object> result=new HashMap<>(16);
        CCPRestSDK restAPI = new CCPRestSDK();
        // 初始化服务器地址和端口，格式如下，服务器地址不需要写https
        restAPI.init("app.cloopen.com", "8883");
        // 初始化主帐号和主帐号TOKEN
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);
        // 初始化应用ID
        restAPI.setAppId(APP_ID);
        result = restAPI.sendTemplateSMS(phone,templateId ,templateContent);
        if("000000".equals(result.get("statusCode"))){
            b=true;
            logger.info(phone+"短信发送成功");
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
               logger.info(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            logger.info("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }

        return b;
    }

    public static void main(String[] args) {

    }
}
