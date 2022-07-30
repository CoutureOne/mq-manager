package com.couture.config;

import com.couture.entity.Msg;
import com.couture.mapper.MsgMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Couture
 * @Created by 2022/6/24 10:59
 */
@Slf4j
@Configuration
public class PublisherConfigAndReturnConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MsgMapper msgMapper;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        //判断是否到达了交换机
        if (ack) {
            System.out.println("已经到达了broker");
            //我们把消费成功的消息从表中删除
            log.info("messageId is {}", correlationData.getId());
            //我们通过id 删除
            //msgMapper.deleteById(correlationData.getId);
            //也可以多个条件删除
            HashMap<String, Object> map = new HashMap<>();
            //设置条件
            map.put("id", correlationData.getId());
            map.put("status", -1);

            //删除
            msgMapper.deleteByMap(map);

        } else {
            System.out.println("没有到broker");
        }
    }

    //在confirm之前
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        System.out.println("未到达队列");

        String exchange = returnedMessage.getExchange();
        String routingKey = returnedMessage.getRoutingKey();
        Message message = returnedMessage.getMessage();

        //消息没有成功消费
        Msg msg = new Msg();

        msg.setId(UUID.randomUUID().toString());
        msg.setExchange(exchange);
        msg.setRoutingKey(routingKey);
        msg.setContent(new String(message.getBody()));//积分对象
        msg.setStatus(-2);
        msg.setTryCount(0);
        msg.setCreateTime(new Date());

        msgMapper.insert(msg);
    }
}
