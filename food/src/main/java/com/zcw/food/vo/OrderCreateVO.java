package com.zcw.food.vo;

import lombok.Data;

/**
 * @ClassName : OrderCreateVO
 * @Description :
 * @Author : zhaocunwei
 * @Date: 2020-12-05 13:21
 */

@Data
public class OrderCreateVO {
    /**
     * 用户ID
     */
    private Integer accountId;
    /**
     * 地址
     */
    private String address;
    /**
     * 产品ID
     */
    private Integer productId;


}
