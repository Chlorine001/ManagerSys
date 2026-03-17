package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName flie
 */
@TableName(value = "flie")
@Data
public class Flie implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer floderId;

    private String name;

    private String type;

    private String tag;

    private String privacyLevel;

    private Double star;

    private String version;

    private Date uploadTime;

    private Boolean state;

    private static final long serialVersionUID = 1L;
}