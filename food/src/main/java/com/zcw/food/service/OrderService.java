package com.zcw.food.service;

import com.zcw.food.vo.OrderCreateVO;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface OrderService {
    void createOrder(OrderCreateVO orderCreateVO) throws IOException, TimeoutException;
}
