package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName menu
 */
@TableName(value ="menu")
@Data
public class Menu implements Serializable {
    private Integer id;

    private String name;

    private String url;

    private String icon;

    private Integer pid;

    private static final long serialVersionUID = 1L;
}