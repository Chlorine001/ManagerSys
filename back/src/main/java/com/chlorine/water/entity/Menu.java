package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName menu
 */
@TableName(value = "menu")
@Data
public class Menu implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String url;

    private String icon;

    private Integer pid;

    @TableField("is_hide")
    @JsonProperty("isHide")
    private Boolean isHide;

    @TableField("is_leaf")
    @JsonProperty("isLeaf")
    private Boolean isLeaf;

    private static final long serialVersionUID = 1L;
}