package com.couture.service.impl;

import com.alibaba.fastjson.JSON;
import com.couture.contant.IntegralConstant;
import com.couture.entity.Integral;
import com.couture.mapper.IntegralMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Couture
 * @Created by 2022/6/24 10:47
 */
@Slf4j
@Service
public class IntegralServiceImpl {

    @Resource
    private IntegralMapper integralMapper;

    @RabbitListener(queues = IntegralConstant.INTEGRAL_QUEUE)
    public void receive(Message message) {
        //获取消息
        String body = new String(message.getBody());

        //类型转换
        Integral integral = JSON.parseObject(body, Integral.class);
        log.info("receive integral is {}", integral);

        //插入积分表
        integralMapper.insert(integral);
    }
}
