package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName file_star
 */
@TableName(value ="file_star")
@Data
public class FileStar implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer fileId;

    private Double star;

    private static final long serialVersionUID = 1L;
}