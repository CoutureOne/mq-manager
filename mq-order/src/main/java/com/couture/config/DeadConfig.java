package com.couture.config;

import com.couture.contant.DeadConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Couture
 * @Created by 2022/6/24 16:23
 */
@Configuration
public class DeadConfig {

    //创建队列
    @Bean
    public Queue createQueue() {
        return new Queue(DeadConstant.DEAD_LETTER_QUEUE);
    }

    //创建交换机

}
