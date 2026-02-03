package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName back_notice
 */
@TableName(value ="back_notice")
@Data
public class BackNotice implements Serializable {
    private Integer id;

    private String report;

    private static final long serialVersionUID = 1L;

}