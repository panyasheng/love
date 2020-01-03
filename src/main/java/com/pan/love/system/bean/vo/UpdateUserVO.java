package com.pan.love.system.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 修改用户参数
 *
 * @author pan
 * @date 2019/10/22
 */
@Data
@ApiModel(value = "修改用户参数")
public class UpdateUserVO {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像路径")
    private String headUrl;

    /**修改时间*/
    @JsonIgnore
    private Date updateTime;
}
