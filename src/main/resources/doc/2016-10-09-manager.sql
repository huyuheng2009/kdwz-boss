INSERT INTO `boss_menu` (`id`, `menu_uri`, `menu_text`, `menu_img`, `permision_code`, `pid`, `level`, `order_no`, `status`, `position_text`, `sub_limit`) VALUES (168, '/scant/center_out', '���ĳ���ɨ��', NULL, 'CENTER_WAREHOUSE_SENDSCAN', 5, 2, 8, 1, 'ɨ��>���ĳ���ɨ��', 0);


UPDATE `manager_lgc`.`boss_menu` SET `sub_limit`=1 WHERE  `id`=131;


UPDATE `manager_lgc`.`boss_menu` SET `sub_limit`=1 WHERE  `id`=132;



CREATE TABLE `common_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `k` varchar(64) DEFAULT NULL,
  `v` varchar(128) DEFAULT NULL,
  `title` varchar(64) DEFAULT NULL COMMENT '����',
  `group_name` varchar(64) DEFAULT NULL COMMENT '����',
  `input_type` varchar(32) DEFAULT NULL COMMENT '���������',
  `sort` int(11) DEFAULT NULL,
  `validate_attr` varchar(256) DEFAULT NULL,
  `validate_class` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


INSERT INTO `common_config` VALUES ('1', 'index_pic', '', '��ҳͼƬ����', 'backend_push', 'file', '1', null, null);
INSERT INTO `common_config` VALUES ('2', 'news_show_date', '5', '����Ϣ��ʾʱ������(������)', 'backend_push', 'number', '2', 'min=\"1\" max=\"10000\"', null);

