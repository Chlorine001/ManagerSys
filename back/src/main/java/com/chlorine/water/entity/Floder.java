package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName floder
 */
@TableName(value = "floder")
@Data
public class Floder implements Serializable {
    private Integer id;

    private String name;

    private Integer pid;

    private Integer userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}