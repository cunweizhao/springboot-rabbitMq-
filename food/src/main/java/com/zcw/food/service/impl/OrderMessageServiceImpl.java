package com.zcw.food.service.impl;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.zcw.food.service.OrderMessageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName : OrderMessageServiceImpl
 * @Description : 消息处理相关业务逻辑
 * @Author : zhaocunwei
 * @Date: 2020-12-05 20:43
 */
@Service
public class OrderMessageServiceImpl implements OrderMessageService {
    @Override
    public void handleMessage() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        try ( Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()){
            /*-----restaurant-----*/
            channel.exchangeDeclare(
                    "exchande.order.restaurant",
                    BuiltinExchangeType.DIRECT,
                    true,
                    false,
                    null

            );
            channel.queueDeclare(
                    "queue.order",
                    true,
                    false,
                    false,
                    null
            );
            channel.queueBind("queue.order",
                                "exchande.order.restaurant",
                                "key.order"
                    );

            /*-------deliveryman--------*/
            channel.exchangeDeclare(
                    "exchange.order.deliveryman",
                    BuiltinExchangeType.DIRECT,
                    true,
                    false,
                    null
            );
            channel.queueBind(
                    "queue.order",
                    "exchange.order.deliveryman",
                    "key.order"
            );
        }
    }
}
