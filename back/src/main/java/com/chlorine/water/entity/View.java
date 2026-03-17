package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName view
 */
@TableName(value = "view")
@Data
public class View implements Serializable {
    private Integer id;

    private Integer fileId;

    private Integer userId;

    private Date time;

    private static final long serialVersionUID = 1L;
}