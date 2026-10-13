package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName file_star
 */
@TableName(value = "file_star")
@Data
public class FileStar implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer fileId;

    private Double star;

    private static final long serialVersionUID = 1L;
}