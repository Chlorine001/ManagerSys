package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName download
 */
@TableName(value ="download")
@Data
public class Download implements Serializable {
    private Integer id;

    private Integer fileId;

    private Integer userId;

    private Date time;

    private static final long serialVersionUID = 1L;
}