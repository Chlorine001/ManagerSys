package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName course
 */
@TableName(value = "course")
@Data
public class Course implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String info;

    private static final long serialVersionUID = 1L;
}