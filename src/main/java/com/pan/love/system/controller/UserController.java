package com.pan.love.system.controller;

import com.pan.love.framework.config.redis.JedisClient;
import com.pan.love.system.bean.vo.*;
import com.pan.love.system.service.UserService;
import com.pan.love.util.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pan.love.framework.config.sms.SmsUtil.SendSMS;
import static com.pan.love.util.GenerateToken.getToken;


/**
 * 用户
 *
 * @author pan
 * @date 2019/10/16
 */
@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JedisClient jedisClient;

    @PostMapping("/add")
    @ApiOperation(value = "新增用户",notes = "新增时，登陆名要校验唯一性")
    public RespBody addUser(@RequestBody AddUserVo user){
        RespBody respBody=new RespBody();
        int addCount=userService.addUser(user);
        respBody.setData(addCount);
        return respBody;
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除用户")
    public RespBody deleteUser(@RequestBody List<String> listId){
        RespBody respBody=new RespBody();
        int deleteCount=userService.deleteUser(listId);
        respBody.setData(deleteCount);
        return respBody;
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户")
    public RespBody updateUser(@RequestBody UpdateUserVO updateUserVO){
        RespBody respBody=new RespBody();
        int updateCount=userService.updateUser(updateUserVO);
        respBody.setData(updateCount);
        return respBody;
    }


    @PostMapping("/query")
    @ApiOperation(value = "查找用户")
    public RespBody queryUser(@RequestBody QueryUserInVo queryUserInVo){
        RespBody respBody=new RespBody();
//        if(StringUtils.isEmpty(queryUserInVo.getLoginName())){
//            throw new LoveException("用户名不能为空");
//        }
        List<QueryUserOutVo> listUser=userService.queryUser(queryUserInVo);
        respBody.setData(listUser);
        respBody.setTotal(listUser.size());
        return respBody;
    }

    @PostMapping("/login")
    @ApiOperation(value = "登陆")
    public RespBody login(@RequestBody LoginUserInVo loginUserInVo){
        RespBody respBody=new RespBody();
        String token=userService.login(loginUserInVo);
        respBody.setData(token);
        return respBody;
    }

    @GetMapping("/getCode")
    @ApiOperation(value = "获取验证码")
    public RespBody getCode(String phone){

        RespBody respBody=new RespBody();

        //校验输入的电话号码是否是注册过的
        boolean b=userService.getCode(phone);
        if(!b){
            respBody.setMsg("短信发送失败！");
            respBody.setCode("-1");
        }

        return respBody;
    }

}
