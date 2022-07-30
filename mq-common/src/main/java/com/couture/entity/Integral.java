package com.couture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * @author Couture
 * @Created by 2022/6/23 17:27
 */

//积分
@TableName("integral")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Integral{

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    private Long score;
    private String msg;

    @TableField("create_time")
    private Date createTime;
}