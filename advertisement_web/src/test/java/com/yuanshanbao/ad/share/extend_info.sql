CREATE TABLE `tb_extend_info_%s` (
  `extend_info_id` varchar(30) NOT NULL,
  `user_id` varchar(30) DEFAULT NULL,
  `information_id` varchar(30) DEFAULT NULL,
  `key` varchar(1024) DEFAULT NULL,
  `value` varchar(1024) DEFAULT NULL,
  `status` int(5) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`extend_info_id`),
  UNIQUE KEY `index_extend_info_code` (`extend_info_id`) USING BTREE,
  KEY `index_user_information_create_time_code` (`user_id`,`information_id`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
