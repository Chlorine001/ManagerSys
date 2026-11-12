package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    /** 密码：只允许反序列化写入，序列化输出时忽略，避免泄露 */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String nickName;

    private String avatar;

    private String phone;

    private String sex;

    private Integer age;

    private String sign;

    private String email;

    private String organization;

    private LocalDateTime createTime;

    private Integer state;

    private Integer trueState;

    @TableField("role_id")
    private Integer roleId;

    private String trueName;

    /** 身份证号：敏感字段，序列化输出时忽略 */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String idNumber;

    private String direction;

    private static final long serialVersionUID = 1L;
}