package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
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

    private Date createTime;

    private Boolean state;

    private Integer tureState;

    private Integer role;

    private String trueName;

    private String idNumber;

    private String direction;

    private static final long serialVersionUID = 1L;
}