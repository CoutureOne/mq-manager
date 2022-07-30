package com.couture.service.impl;

import com.alibaba.fastjson.JSON;
import com.couture.contant.IntegralConstant;
import com.couture.entity.Integral;
import com.couture.entity.Msg;
import com.couture.entity.Order;
import com.couture.mapper.MsgMapper;
import com.couture.mapper.OrderMapper;
import com.couture.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author Couture
 * @Created by 2022/6/23 17:47
 */
@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MsgMapper msgMapper;

    //插入订单
    @Override
    public void insertOrder(Order order) {
        //插入订单
        orderMapper.insert(order);

        //插入积分信息
        Integral integral = new Integral();
        //谁下的订单，给谁积分
        integral.setUserId(order.getUserId());
        integral.setScore(10L);
        integral.setMsg("购物积分");
        integral.setCreateTime(new Date());


        //转换json类型
        String jsonIntegral= JSON.toJSONString(integral);
        log.info("integral is {}", jsonIntegral);

        //发送消息有可能消息丢失，我们可以在发送消息之前，把消息内容插入到数据库
        //如果发送成功，，再去数据库删除消息
        Msg msg = new Msg();

        //消息的id不能重复，分布式架构下，生成唯一的id：1.百度UidGenerator 2.美团leaf (雪花算法 ->
        String uuid = UUID.randomUUID().toString();
        msg.setId(uuid);
        msg.setExchange(IntegralConstant.INTEGRAL_EXCHANGE);
        msg.setRoutingKey(IntegralConstant.INTEGRAL_ROUTING_KEY);
        msg.setContent(jsonIntegral);
        msg.setStatus(-1);
        msg.setTryCount(10);
        msg.setCreateTime(new Date());

        //插入消息
        msgMapper.insert(msg);

        //发送消息时，我们可以把消息id单独提取处理，方便处理
        CorrelationData correlationData = new CorrelationData(uuid);
        System.out.println(uuid == correlationData.getId());

        //发送消息
        rabbitTemplate.convertAndSend(
                IntegralConstant.INTEGRAL_EXCHANGE,
                IntegralConstant.INTEGRAL_ROUTING_KEY,
                buildMessage(jsonIntegral,uuid),
                correlationData

        );
    }

    //构建消息
    private Message buildMessage(String body, String messageId) {
        MessageProperties messageProperties = MessagePropertiesBuilder
                .newInstance()
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)//持久化消息
                .setMessageId(messageId)
                .build();

        //返回 Message 对象
        Message message = new Message(body.getBytes(), messageProperties);
        return message;
    }
}
