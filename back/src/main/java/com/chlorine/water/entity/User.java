package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@Getter
@Setter
public class User implements Serializable {
    private Integer id;

    private String name;

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

    private Integer role;

    private String trueName;

    private String idNumber;

    private String direction;

    private static final long serialVersionUID = 1L;
}