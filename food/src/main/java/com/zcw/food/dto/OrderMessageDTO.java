package com.zcw.food.dto;

import com.zcw.food.enummeration.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName : OrderMessageDTO
 * @Description :
 * @Author : zhaocunwei
 * @Date: 2020-12-05 13:35
 */
@Data
public class OrderMessageDTO {
    /**
     * 订单ID
     */
    private Integer OrderId;
    /**
     * 订单状态
     */
    private OrderStatus orderStatus;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 骑手ID
     */
    private Integer deliverymanId;
    /**
     * 产品ID
     */
    private Integer productId;
    /**
     * 用户ID
     */
    private Integer accountId;
    /**
     * 结算ID
     */
    private Integer settLementId;
    /**
     * 积分结算ID
     */
    private Integer rewardId;
    /**
     * 结算积分奖励数量
     */
    private Integer rewardAmount;
    /**
     * 确认
     */
    private Boolean confirmed;
}
