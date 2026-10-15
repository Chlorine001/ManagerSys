package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName role
 */
@TableName(value = "role")
@Data
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    /** 1=启用 0=禁用 */
    private String state;

    private String info;

    private static final long serialVersionUID = 1L;
}