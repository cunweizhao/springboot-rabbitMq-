package com.zcw.food.controller;

import com.zcw.food.service.OrderService;
import com.zcw.food.vo.OrderCreateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName : OrderController
 * @Description :
 * @Author : zhaocunwei
 * @Date: 2020-12-05 22:54
 */
@Controller
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    public void createOrder(OrderCreateVO orderCreateVO) throws IOException, TimeoutException {
        orderService.createOrder(orderCreateVO);
    }
}
