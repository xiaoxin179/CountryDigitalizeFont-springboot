package com.xiaoxin.datinghubback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import cn.hutool.core.annotation.Alias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.partner.boot.common.LDTConfig;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 
* </p>
*
* @author xiaoxin
* @since 2023-08-27
*/
@Getter
@Setter
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 用户名
    @ApiModelProperty("用户名")
    @Alias("用户名")
    private String username;

    // 密码
    @ApiModelProperty("密码")
    @Alias("密码")
    private String password;

    // 姓名
    @ApiModelProperty("姓名")
    @Alias("姓名")
    private String name;

    // 头像
    @ApiModelProperty("头像")
    @Alias("头像")
    private String img;

    // 关注
    @ApiModelProperty("关注")
    @Alias("关注")
    private String focus;

    // 用户唯一id标识
    @ApiModelProperty("用户唯一id标识")
    @Alias("用户唯一id标识")
    private String userid;

    // 邮箱
    @ApiModelProperty("邮箱")
    @Alias("邮箱")
    private String email;

    // 电话
    @ApiModelProperty("电话")
    @Alias("电话")
    private String phone;

    // 地址
    @ApiModelProperty("地址")
    @Alias("地址")
    private String address;

    // 用户是否被删除，0为删除，1为存在
    @ApiModelProperty("用户是否被删除，0为删除，1为存在")
    @Alias("用户是否被删除，0为删除，1为存在")
    private String isDelete;
}