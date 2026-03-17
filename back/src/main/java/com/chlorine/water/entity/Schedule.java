package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName schedule
 */
@TableName(value = "schedule")
@Data
public class Schedule implements Serializable {
    private Integer id;

    private Integer classId;

    private Integer userId;

    private Integer courseId;

    private static final long serialVersionUID = 1L;
}