package com.chlorine.water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName file_comment
 */
@TableName(value ="file_comment")
@Data
public class FileComment implements Serializable {
    private Integer id;

    private Integer fileId;

    private Integer userId;

    private String content;

    private Integer pid;

    private Date time;

    private Integer likes;

    private static final long serialVersionUID = 1L;
}