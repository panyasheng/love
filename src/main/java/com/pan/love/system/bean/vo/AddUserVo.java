package com.pan.love.system.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户对象
 *
 * @author pan
 * @date 2019/10/16
 */
@Data
@ApiModel("新增用户参数")
public class AddUserVo {

    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "登陆名")
    private String loginName;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "头像路径")
    private String headUrl;

    /**创建时间*/
    @JsonIgnore
    private Date createTime;

    /**修改时间*/
    @JsonIgnore
    private Date updateTime;
}
