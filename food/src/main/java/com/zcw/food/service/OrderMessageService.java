package com.zcw.food.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface OrderMessageService {

    /**
     * 声明消息队列，交换机，绑定，消息的处理
     */
    void handleMessage() throws IOException, TimeoutException;
}
