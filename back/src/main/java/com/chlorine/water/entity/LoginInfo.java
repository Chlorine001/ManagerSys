package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName login_info
 */
@TableName(value = "login_info")
@Data
public class LoginInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Date loginTime;

    private String ipAddress;

    private String deviceInfo;

    private static final long serialVersionUID = 1L;
}