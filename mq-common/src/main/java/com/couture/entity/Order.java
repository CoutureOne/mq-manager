package com.couture.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
/*
  {"bizNo": "20200803173145877",
   "status": 1,
   "price": 34.12,
   "goodId": 1002,
   "userId": 100
  }

*/
/**
 * @author Couture
 * @Created by 2022/6/23 17:30
 */
//订单
@TableName("orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "biz_no")
    private String bizNo; //业务编号

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "price")
    private BigDecimal price;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "pay_time")
    private Date payTime;

    @TableField(value = "good_id")
    private Integer goodId;

    @TableField(value = "user_id")
    private Integer userId;

    //exist = false：该属性不使用
    @TableField(value = "num", exist = false)
    private Integer num;
}