package com.xiaoxin.Country.controller.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author:XIAOXIN
 * @date:2023/09/16
 **/
@Data
@Builder
public class ImMessageDTO {
    private String userid;
    private String username;
    private String sign;
    private String avatar;
    private String text;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;




}
