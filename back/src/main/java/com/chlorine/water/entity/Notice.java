package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName notice
 */
@TableName(value = "notice")
@Data
public class Notice implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String tittle;

    private String content;

    private Date publishTime;

    private Date expirationTime;

    private static final long serialVersionUID = 1L;
}