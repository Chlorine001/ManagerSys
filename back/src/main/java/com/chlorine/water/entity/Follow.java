package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName follow
 */
@TableName(value = "follow")
@Data
public class Follow implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer followerId;

    private Integer followingId;

    private Date time;

    private static final long serialVersionUID = 1L;
}