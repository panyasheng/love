package com.pan.love.system.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 登录入参对象
 *
 * @author pan
 * @date 2019/10/16
 */
@Data
@ApiModel("登录入参对象")
public class LoginUserInVo implements Serializable {

    @ApiModelProperty(value = "登陆名")
    private String loginName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "登录类别")
    private String type;

    @ApiModelProperty(value = "验证码")
    private String code;
}
