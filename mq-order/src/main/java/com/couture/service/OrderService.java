package com.couture.service;

import com.couture.entity.Order;

/**
 * @author Couture
 * @Created by 2022/6/23 17:42
 */
public interface OrderService {
    /**
     *
     * 订单插入
     * @param order 订单
     */
    public void insertOrder(Order order);

}
