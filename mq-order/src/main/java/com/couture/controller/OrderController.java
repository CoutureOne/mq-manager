package com.couture.controller;

import com.couture.entity.Order;
import com.couture.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Couture
 * @Created by 2022/6/23 17:41
 */

@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;


    @PostMapping("insertOrder")
    public String insertOrder(@RequestBody Order order) {
        orderService.insertOrder(order);
        return "success";
    }

}
