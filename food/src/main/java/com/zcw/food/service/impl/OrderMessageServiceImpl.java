package com.zcw.food.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.zcw.food.dto.OrderMessageDTO;
import com.zcw.food.enummeration.OrderStatus;
import com.zcw.food.mapper.OrderDetailMapper;
import com.zcw.food.pojo.OrderDetail;
import com.zcw.food.service.OrderMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.zcw.food.enummeration.OrderStatus.*;

/**
 * @ClassName : OrderMessageServiceImpl
 * @Description : 消息处理相关业务逻辑
 * @Author : zhaocunwei
 * @Date: 2020-12-05 20:43
 */
@Service
@Slf4j
public class OrderMessageServiceImpl implements OrderMessageService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleMessage() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
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

    DeliverCallback deliverCallback = ((consumerTag, message) -> {
        String messageBody = new String(message.getBody());

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            ////////////////收到消息更新订单状态////////////////////
            //将消息体返序列化成DTO
            OrderMessageDTO orderMessageDTO = objectMapper.readValue(messageBody,
                    OrderMessageDTO.class);
            //数据库中读取订单pojo
            OrderDetail orderDetail =orderDetailMapper.selectOrder(orderMessageDTO.getOrderId());
            switch (orderDetail.getStatus()){
                case ORDER_CREATING:
                    if(orderMessageDTO.getConfirmed() && null !=orderMessageDTO.getPrice()){
                        orderDetail.setStatus(RESTAURANT_CONFIRMED.toString());
                        orderDetail.setPrice(orderMessageDTO.getPrice());
                        orderDetailMapper.update(orderDetail);

                        try(Connection connection = connectionFactory.newConnection();
                        Channel channel = connection.createChannel()){
                            String messageToSend = objectMapper.writeValueAsString(orderMessageDTO);
                            channel.basicPublish(
                                    "exchange.order.deliveryman",
                                    "key.deliveryman",
                                    null,
                                    messageToSend.getBytes());
                        }
                    }else{
                        orderDetail.setStatus(FAILED.toString());
                        orderDetailMapper.update(orderDetail);
                    }
                    break;
                case RESTAURANT_CONFIRMED:
                    break;
                case DELIVERTMAN_CONFIRMED:
                    break;
                case SETTLEMENT_CONFIRMED:
                    break;
                case ORDER_CREATED:
                    break;
                case FAILED:
                    break;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    });
}
