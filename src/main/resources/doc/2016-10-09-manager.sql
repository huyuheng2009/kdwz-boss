INSERT INTO `boss_menu` (`id`, `menu_uri`, `menu_text`, `menu_img`, `permision_code`, `pid`, `level`, `order_no`, `status`, `position_text`, `sub_limit`) VALUES (168, '/scant/center_out', '中心出仓扫描', NULL, 'CENTER_WAREHOUSE_SENDSCAN', 5, 2, 8, 1, '扫描>中心出仓扫描', 0);


UPDATE `manager_lgc`.`boss_menu` SET `sub_limit`=1 WHERE  `id`=131;


UPDATE `manager_lgc`.`boss_menu` SET `sub_limit`=1 WHERE  `id`=132;



CREATE TABLE `common_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `k` varchar(64) DEFAULT NULL,
  `v` varchar(128) DEFAULT NULL,
  `title` varchar(64) DEFAULT NULL COMMENT '名称',
  `group_name` varchar(64) DEFAULT NULL COMMENT '分组',
  `input_type` varchar(32) DEFAULT NULL COMMENT '输入框类型',
  `sort` int(11) DEFAULT NULL,
  `validate_attr` varchar(256) DEFAULT NULL,
  `validate_class` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


INSERT INTO `common_config` VALUES ('1', 'index_pic', '', '首页图片配置', 'backend_push', 'file', '1', null, null);
INSERT INTO `common_config` VALUES ('2', 'news_show_date', '5', '新消息显示时间配置(整数天)', 'backend_push', 'number', '2', 'min=\"1\" max=\"10000\"', null);

