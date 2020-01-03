package com.pan.love.system.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 用户对象
 *
 * @author pan
 * @date 2019/10/16
 */
@Data
@ApiModel("查找用户参数")
public class QueryUserInVo implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "登陆名")
    private String loginName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

}
