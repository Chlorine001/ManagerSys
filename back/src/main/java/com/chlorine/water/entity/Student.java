package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName student
 */
@TableName(value = "student")
@Data
public class Student implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer classId;

    private static final long serialVersionUID = 1L;
}