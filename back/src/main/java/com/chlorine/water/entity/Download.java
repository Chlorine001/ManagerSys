package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName download
 */
@TableName(value = "download")
@Data
public class Download implements Serializable {
    private Integer id;

    private Integer fileId;

    private Integer userId;

    private Date time;

    private static final long serialVersionUID = 1L;
}