package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName class
 */
@TableName(value = "class")
@Data
public class Class implements Serializable {
    private Integer id;

    private Integer grade;

    private Integer name;

    private String info;

    private static final long serialVersionUID = 1L;
}