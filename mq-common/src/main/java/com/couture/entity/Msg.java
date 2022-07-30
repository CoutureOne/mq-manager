package com.couture.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * @author Couture
 * @Created by 2022/6/23 17:29
 */
//消息
@TableName("message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg {

    @TableId(value = "id")
    private String id;

    @TableField(value = "exchange")
    private String exchange;

    @TableField(value = "routing_key")
    private String routingKey;

    @TableField(value = "content")
    private String content; // 消息的内容

    @TableField
    private Integer status; // 消息的状态

    @TableField(value = "try_count")
    private int tryCount; //尝试次数

    @TableField(value = "create_time")
    private Date createTime;
}