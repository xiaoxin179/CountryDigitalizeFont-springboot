package com.xiaoxin.Country.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import cn.hutool.core.annotation.Alias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 
* </p>
*
* @author xiaoxin
* @since 2023-09-08
*/
@Getter
@Setter
@ApiModel(value = "Admin对象", description = "")
public class Admin implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 管理员账号
    @ApiModelProperty("管理员账号")
    @Alias("管理员账号")
    private String username;

    // 管理员密码
    @ApiModelProperty("管理员密码")
    @Alias("管理员密码")
    private String password;

    // 管理员头像
    @ApiModelProperty("管理员头像")
    @Alias("管理员头像")
    private String avatar;

    // 管理员身份证号
    @ApiModelProperty("管理员身份证号")
    @Alias("管理员身份证号")
    private String idcard;
}