CREATE TABLE `order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `account_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `product_id` int(11) DEFAULT NULL COMMENT '产品ID',
  `deliveryman_id` int(11) DEFAULT NULL COMMENT '骑手ID',
  `settlement_id` int(11) DEFAULT NULL COMMENT '结算ID',
  `reward_id` int(11) DEFAULT NULL COMMENT '积分奖励ID',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `date` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;