package com.pan.love.system.service.impl;

import com.pan.love.framework.config.redis.JedisClient;
import com.pan.love.framework.config.redis.RedisCache;
import com.pan.love.framework.exception.LoveException;
import com.pan.love.system.bean.vo.*;
import com.pan.love.system.mapper.UserMapper;
import com.pan.love.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.pan.love.framework.config.sms.SmsUtil.SendSMS;
import static com.pan.love.util.GenerateToken.getToken;

/**
 * 用户
 *
 * @author pan
 * @date 2019/10/22
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public int addUser(AddUserVo user) {
        QueryUserInVo queryUserInVo=new QueryUserInVo();
        queryUserInVo.setLoginName(user.getLoginName());
        List<QueryUserOutVo> listUser=userMapper.queryUser(queryUserInVo);
        if(listUser.size() !=0){

        }
        int addCount=userMapper.addUser(user);
        return addCount;
    }

    @Override
    public int deleteUser(List<String> listId) {
        int deleteCount=userMapper.deleteUser(listId);
        return deleteCount;
    }

    @Override
    public int updateUser(UpdateUserVO updateUserVO) {
        int updateCount=userMapper.updateUser(updateUserVO);
        return updateCount;
    }

    @RedisCache(keyName = "user",cacheTime = 60)
    @Override
    public List<QueryUserOutVo> queryUser(QueryUserInVo queryUserInVo) {

        List<QueryUserOutVo> listUser=userMapper.queryUser(queryUserInVo);
        return listUser;
    }

    @Override
    public boolean getCode(String phone) {
        boolean result=false;
        QueryUserInVo queryUserInVo=new QueryUserInVo();
        queryUserInVo.setPhone(phone);
        List<QueryUserOutVo> listUser=userMapper.queryUser(queryUserInVo);
        //如果注册过
        if(listUser.size()!=0){

            //生成验证码
            String code=getSixCode();
            //5分钟
            String time="5";

            boolean b=SendSMS(phone,"1",new String[]{code,time});
            //发送短信验证码成功
            if(b){
                //保存的redis中，key:phone(电话号码);value:code(验证码)
                jedisClient.set(phone,code,Integer.parseInt(time)*60);
                result=true;
            }
        }
        return result;
    }

    @Override
    public String login(LoginUserInVo loginUserInVo) {
        //登陆时首先校验token是否存在，存在更新token或者重新生成一个token;或者token有效免登陆
        //区分用户名和密码登陆还是手机验证码登陆
        //手机验证码登陆要保存有效时间
        QueryUserInVo queryUserInVo=new QueryUserInVo();
        queryUserInVo.setPhone(loginUserInVo.getPhone());

        String token="";

        //5小时
        long time=5*60*60*1000;
        List<QueryUserOutVo> listUser=new ArrayList<>();
        //电话验证码登陆
        if("phone".equals(loginUserInVo.getType())) {

            listUser = userMapper.queryUser(queryUserInVo);
            //如果注册过
            if (listUser.size() != 0) {

                //获取保存redis中的验证码
                String valueCode = jedisClient.get(queryUserInVo.getPhone());
                if (StringUtils.isEmpty(valueCode)) {
                    throw new LoveException("验证码过期或者不存在，请检查！");
                }
                if(valueCode.equals(loginUserInVo.getCode())){
                    throw new LoveException("验证码错误，请检查！");
                }
                token = getToken(listUser.get(0).getUserId() + "", time);

            } else {
                listUser = userMapper.queryUser(queryUserInVo);
                //如果注册过
                if (listUser.size() != 0) {
                    if(!loginUserInVo.getLoginName().equals(listUser.get(0).getLoginName())){
                        throw new LoveException("用户名错误！");
                    }
                    if(!loginUserInVo.getPassword().equals(listUser.get(0).getPassword())){
                        throw new LoveException("密码错误！");
                    }
                    token = getToken(listUser.get(0).getUserId() + "", time);
                }else{
                    throw new LoveException("用户不存在！");
                }
            }
        }
        return token;
    }

    public String getSixCode(){
        int newNum = (int)((Math.random()*9+1)*100000);
        return String.valueOf(newNum);
    }

}
