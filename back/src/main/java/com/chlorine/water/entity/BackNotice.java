package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName back_notice
 */
@TableName(value = "back_notice")
@Data
public class BackNotice implements Serializable {
    private Integer id;

    private String report;

    private static final long serialVersionUID = 1L;

}