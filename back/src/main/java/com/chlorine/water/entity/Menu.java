package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName menu
 */
@TableName(value = "menu")
@Data
public class Menu implements Serializable {
    private Integer id;

    private String name;

    private String url;

    private String icon;

    private Integer pid;

    private Boolean ishide;

    private static final long serialVersionUID = 1L;
}