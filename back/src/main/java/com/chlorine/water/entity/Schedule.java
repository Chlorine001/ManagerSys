package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName schedule
 */
@TableName(value = "schedule")
@Data
public class Schedule implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer classId;

    private Integer userId;

    private Integer courseId;

    private static final long serialVersionUID = 1L;
}