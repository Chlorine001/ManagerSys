package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName role_menu
 */
@TableName(value = "role_menu")
@Data
public class RoleMenu implements Serializable {
    private Integer id;

    private Integer roleId;

    private Integer menuId;

    private static final long serialVersionUID = 1L;
}