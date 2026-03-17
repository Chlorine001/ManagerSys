package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName role
 */
@TableName(value = "role")
@Data
public class Role implements Serializable {
    private Integer id;

    private String name;

    private String state;

    private String info;

    private Integer numb;

    private static final long serialVersionUID = 1L;
}