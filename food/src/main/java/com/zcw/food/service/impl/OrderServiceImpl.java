package com.zcw.food.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.zcw.food.dto.OrderMessageDTO;
import com.zcw.food.enummeration.OrderStatus;
import com.zcw.food.mapper.OrderDetailMapper;
import com.zcw.food.pojo.OrderDetail;
import com.zcw.food.service.OrderService;
import com.zcw.food.vo.OrderCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName : OrderServiceImpl
 * @Description : 处理用户关于订单的业务请求
 * @Author : zhaocunwei
 * @Date: 2020-12-05 21:14
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void createOrder(OrderCreateVO orderCreateVO) throws IOException, TimeoutException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setAddress(orderCreateVO.getAddress());
        orderDetail.setAccountId(orderCreateVO.getAccountId());
        orderDetail.setProductId(orderCreateVO.getProductId());
        OrderStatus orderCreating = OrderStatus.ORDER_CREATING;
        orderDetail.setStatus(orderCreating.toString());
        orderDetail.setDate(new Date());
        orderDetailMapper.insert(orderDetail);

        OrderMessageDTO orderMessageDTO = new OrderMessageDTO();
        orderMessageDTO.setOrderId(orderDetail.getId());
        orderMessageDTO.setProductId(orderDetail.getProductId());
        orderMessageDTO.setAccountId(orderDetail.getAccountId());


        ConnectionFactory connectionFactory  = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");


        try(Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel()){
            String messageToSend = objectMapper.writeValueAsString(orderMessageDTO);
            channel.basicPublish(
                    "exchande.order.restaurant",
                    "key.restaurant",
                    null,
                    messageToSend.getBytes());
        }
    }
}
