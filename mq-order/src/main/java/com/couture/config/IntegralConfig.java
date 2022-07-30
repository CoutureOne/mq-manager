package com.couture.config;

import com.couture.contant.IntegralConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Couture
 * @Created by 2022/6/24 10:55
 */

//路由模式
@Configuration
public class IntegralConfig {

    //交换机
    @Bean
    public DirectExchange createIntegralExchange() {
        return new DirectExchange(IntegralConstant.INTEGRAL_EXCHANGE);
    }

    @Bean
    public Queue integralQueue() {
        return new Queue(IntegralConstant.INTEGRAL_QUEUE);
    }

    //交换机和队列绑定
    @Bean
    public Binding bindingIntegralQueue() {
        return BindingBuilder.bind(integralQueue()).to(createIntegralExchange()).with(IntegralConstant.INTEGRAL_ROUTING_KEY);
    }
}
