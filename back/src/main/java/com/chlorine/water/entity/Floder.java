package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName floder
 */
@TableName(value ="floder")
@Data
public class Floder implements Serializable {
    private Integer id;

    private String name;

    private Integer pid;

    private Integer userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}