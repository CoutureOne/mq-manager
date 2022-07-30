package com.couture.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.couture.contant.IntegralConstant;
import com.couture.entity.Msg;
import com.couture.mapper.MsgMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Couture
 * @Created by 2022/6/25 12:42
 */
@Configuration
public class MessageCompensateTask {

    @Resource
    private MsgMapper msgMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "10 * * * * * ?")
    public void compensateTask() {
        //设置条件
        QueryWrapper<Msg> queryWrapper = new QueryWrapper<Msg>();
        queryWrapper.eq("status", -2);
        queryWrapper.lt("try_count", 3);

        //获取符合条件的所有消息
        List<Msg> msgList = msgMapper.selectList(queryWrapper);

        //判断
        if (msgList != null && msgList.size() > 0) {
            for (Msg msg : msgList) {
                //发送消息
                rabbitTemplate.convertAndSend(
                        IntegralConstant.INTEGRAL_EXCHANGE,
                        IntegralConstant.INTEGRAL_ROUTING_KEY,
                        buildMessage(msg.getContent(), msg.getId()),
                        new CorrelationData(msg.getId())
                );

                //更新消息
                msg.setTryCount(msg.getTryCount() + 1);
                msgMapper.updateById(msg);
            }
        }
    }
    //构建消息
    private Message buildMessage(String body, String messageId) {
        MessageProperties messageProperties = MessagePropertiesBuilder
                .newInstance()
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setMessageId(messageId)
                .build();

        //返回message对象
        Message message = new Message(body.getBytes(), messageProperties);
        return message;
    }

}
