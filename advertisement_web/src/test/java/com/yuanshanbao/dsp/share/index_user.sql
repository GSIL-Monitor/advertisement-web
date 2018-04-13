CREATE TABLE `tb_index_user_%s` (
  `open_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `type` int(4) DEFAULT NULL,
  PRIMARY KEY (`open_id`),
  UNIQUE KEY `index_open_code` (`open_id`) USING BTREE,
  KEY `index_open_user_code` (`open_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;