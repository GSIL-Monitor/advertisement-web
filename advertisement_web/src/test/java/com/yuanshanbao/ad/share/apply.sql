CREATE TABLE `tb_apply_%s` (
  `apply_id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `information_id` varchar(50) DEFAULT NULL,
  `merchant_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `status` int(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`apply_id`),
  UNIQUE KEY `index_apply_code` (`apply_id`) USING BTREE,
  KEY `index_user_information_merchant_product_code` (`user_id`,`information_id`,`merchant_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;