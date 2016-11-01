CREATE TABLE `asign_order` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
	`order_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
	`asign_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '分配时间',
	`asing_date` DATE NOT NULL,
	`asign_no` VARCHAR(100) NULL DEFAULT NULL,
	`asign_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '分配人',
	`asign_duration` DECIMAL(10,0) NULL DEFAULT NULL COMMENT '分配时长',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `order_no_` (`order_no`),
	INDEX `order_no` (`order_no`),
	INDEX `asing_date` (`asing_date`),
	INDEX `asign_time` (`asign_time`),
	INDEX `order_time` (`order_time`),
	INDEX `asign_no` (`asign_no`)
)
COMMENT='分配表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;


ALTER TABLE `asign_order`
	ADD COLUMN `order_date` DATE NOT NULL AFTER `asing_date`,
	ADD INDEX `order_date` (`order_date`);


CREATE TABLE `lgc_config` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`config_type` VARCHAR(50) NULL DEFAULT NULL,
	`config_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '配置项',
	`config_value` VARCHAR(200) NULL DEFAULT NULL COMMENT '配置值',
	`note` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='公司配置项'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=7;


INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (1, 'LGC_ORDER_NO', 'MIN_LENGTH', '8', '运单号最小长度');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (2, 'LGC_ORDER_NO', 'MAX_LENGTH', '20', '运单号最大长度');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (3, 'LGC_ORDER_NO', 'CONSTATUTE', 'NO_OR_EN', '运单号构成');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (4, 'MONTH_NO', 'NO_FIX', '', '月结号前缀');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (5, 'MONTH_NO', 'NO_LENGTH', '5', '月结号长度');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (6, 'MONTH_NO', 'LX_LENGTH', '10', 'app联想位数');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (7, 'WAGES_BASE', 'FREIGHT', 'NONE', '金额');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (8, 'WAGES_BASE', 'WEIGHT', 'NONE', '重量');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (9, 'WAGES_BASE', 'COUNT', 'NONE', '件数');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (10, 'WAGES_BASE', 'COURIER_STATUS', '1', '计入快递员,1表示不计入已经停用的快递员，0');




INSERT INTO `require_type` (`id`, `code`, `describe`) VALUES (2, 'ORDER_INPUT', '寄件单录入必填项');
INSERT INTO `require_type` (`id`, `code`, `describe`) VALUES (3, 'MONTH_NO', '月结号配置');
INSERT INTO `require_type` (`id`, `code`, `describe`) VALUES (4, 'LGC_ORDER_NO', '运单号配置');
INSERT INTO `require_type` (`id`, `code`, `describe`) VALUES (5, 'LGC_MODEL', '分配模式');

INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (18, 2, 'message', '是否短信', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (19, 2, 'sendPhone', '寄件电话', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (20, 2, 'itemStatus', '物品类型', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (21, 2, 'takeCourier', '取件员', 'Y', 'N', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (22, 2, 'sendName', '寄件人', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (23, 2, 'timeType', '时效类型', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (24, 2, 'itemWeight', '重量', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (25, 2, 'sendKehu', '寄件公司', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (26, 2, 'itemName', '物品名称', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (27, 2, 'itemCount', '件数', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (28, 2, 'sendAddr', '寄件地址', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (29, 2, 'freight', '快递运费', 'Y', 'N', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (30, 2, 'revPhone', '收件电话', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (31, 2, 'freightType', '付款人', 'Y', 'N', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (32, 2, 'revAddr', '收件地址', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (33, 2, 'lgcOrderNo', '运单号', 'Y', 'N', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (34, 2, 'revName', '收件人', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (35, 1, 'itemCount', '件数', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (36, 1, 'timeType', '时效类型', 'N', 'Y', 'CHECKBOX');




ALTER TABLE `month_settle_user`
	ADD COLUMN `no_pre` VARCHAR(50) NULL DEFAULT NULL COMMENT '前缀' AFTER `status`,
	ADD COLUMN `no_fix` VARCHAR(50) NULL DEFAULT NULL AFTER `no_pre`;


update month_settle_user set no_fix=month_settle_no;


UPDATE  `table_field_sort` SET `col`='send_courier_name', `col_name`='派件员' WHERE  `id`=5;


INSERT INTO `sequence` (`name`, `current_value`) VALUES ('customer_no', 100001);


CREATE TABLE `lgc_customer` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`customer_no` INT(11) NULL DEFAULT NULL,
	`cpn_sname` VARCHAR(255) NULL DEFAULT NULL,
	`cpn_name` VARCHAR(255) NULL DEFAULT NULL,
	`concat_name` VARCHAR(255) NULL DEFAULT NULL,
	`concat_phone` VARCHAR(255) NULL DEFAULT NULL,
	`cell_phone` VARCHAR(255) NULL DEFAULT NULL,
	`concat_addr` VARCHAR(255) NULL DEFAULT NULL,
	`substation_no` VARCHAR(255) NULL DEFAULT NULL,
	`courier_no` VARCHAR(255) NULL DEFAULT NULL,
	`kefu_name` VARCHAR(255) NULL DEFAULT NULL,
	`month_no` VARCHAR(255) NULL DEFAULT NULL,
	`note` VARCHAR(255) NULL DEFAULT NULL,
	`source` VARCHAR(255) NULL DEFAULT NULL,
	`create_time` TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `concat_phone` (`concat_phone`),
	UNIQUE INDEX `customer_no` (`customer_no`),
	INDEX `concat_phone_` (`concat_phone`)
)
COMMENT='公司客户'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;


CREATE TABLE `customer_huifang` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`cid` INT(11) NULL DEFAULT NULL,
	`huifang_text` VARCHAR(255) NULL DEFAULT NULL,
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	INDEX `cid` (`cid`)
)
COMMENT='客户回访记录'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;


ALTER TABLE `discount_type`
	ADD COLUMN `min_val` DECIMAL(10,0) NULL DEFAULT NULL AFTER `discount`,
	ADD COLUMN `max_val` DECIMAL(10,0) NULL DEFAULT NULL AFTER `min_val`;

update discount_type set min_val=0,max_val=999;

ALTER TABLE `recharge_history`
	ADD COLUMN `pay_money` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '实付金额' AFTER `rmoney`;


CREATE TABLE `dis_user_note` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`uid` INT(11) NULL DEFAULT NULL,
	`note` VARCHAR(255) NULL DEFAULT NULL,
	`create_time` TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COMMENT='会员备注表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;



CREATE TABLE `weixin_vip_session` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '会员号',
	`oper_id` VARCHAR(100) NULL DEFAULT NULL COMMENT '微信open_id(session_no)',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
	`last_update_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改时间',
	`expiry_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '失效时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `oper_id` (`oper_id`)
)
COMMENT='微信会员登录状态信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;



CREATE TABLE `system_punish` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`punish_text` VARCHAR(255) NULL DEFAULT NULL COMMENT '违规事项',
	`rule_text` VARCHAR(255) NULL DEFAULT NULL COMMENT '处罚标准',
	PRIMARY KEY (`id`)
)
COMMENT='处罚条例表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;


ALTER TABLE `lgc_area`
	CHANGE COLUMN `baddr` `baddr` TEXT NULL DEFAULT NULL COMMENT '收派范围，多个以逗号分隔' AFTER `addr_area`,
	CHANGE COLUMN `naddr` `naddr` TEXT NULL DEFAULT NULL COMMENT '不收派范围，多个以逗号分隔' AFTER `baddr`;


DROP TABLE IF EXISTS `warehouse_staff`;
CREATE TABLE `warehouse_staff` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `substation_no` varchar(255) DEFAULT NULL,
  `id_card` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `create_operator` varchar(64) DEFAULT NULL,
  `warehouse_no` varchar(255) DEFAULT NULL,
  `queue_name` varchar(255) DEFAULT NULL,
  `head_image` varchar(255) DEFAULT NULL,
  `regist_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `inner_no` varchar(50) DEFAULT NULL,
  `inner_phone` varchar(50) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓管员表';


insert into warehouse_staff(user_name,pass_word,real_name,`status`,substation_no) 
select a.user_name,a.`password`,a.real_name,a.`status`,s.substation_no from boss_user a
left join  (select * from substation_user group by user_id ) s on a.id=s.user_id where s.substation_no is not null and a.id in(
select DISTINCT user_id from  boss_user_group where group_id in( select id from boss_group where clogin='Y'));




delete from boss_auth_group ;
delete from boss_group ;
delete from boss_user_group ;


DROP TABLE IF EXISTS `boss_auth_group`;
DROP TABLE IF EXISTS `boss_group`;


CREATE TABLE `boss_group` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`group_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '群组名称',
	`group_desc` VARCHAR(255) NULL DEFAULT NULL COMMENT '群组说明',
	`create_time` TIMESTAMP NULL DEFAULT NULL,
	`clogin` VARCHAR(50) NULL DEFAULT NULL COMMENT '能否登陆仓管员app，N/Y',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=MyISAM
AUTO_INCREMENT=14;


CREATE TABLE `boss_auth_group` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`auth_id` INT(11) NULL DEFAULT NULL COMMENT '权限ID',
	`group_id` INT(11) NULL DEFAULT NULL COMMENT '群组ID',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;



DROP TABLE IF EXISTS `boss_auth`;
CREATE TABLE IF NOT EXISTS `boss_auth` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `auth_code` varchar(255) DEFAULT NULL COMMENT '权限代码',
  `auth_name` varchar(25) DEFAULT NULL COMMENT '权限名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `parent_id` int(11) DEFAULT NULL COMMENT '父节点',
  `category` varchar(255) DEFAULT NULL COMMENT '权限类别LINK BUTTON',
  `status` int(11) DEFAULT '0' COMMENT '启用状态',
  `sub_permit` varchar(255) DEFAULT NULL COMMENT '权限是否具有网点权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;


INSERT INTO `boss_auth` (`id`, `auth_code`, `auth_name`, `remark`, `create_time`, `parent_id`, `category`, `status`, `sub_permit`) VALUES
	(1, 'SUDO', '特殊权限', NULL, '2016-08-16 09:54:21', 0, 'LINK', 0, NULL),
	(2, 'SYSTEM_MANAGE', '系统', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(3, 'LGC_MANAGE', '公司', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(4, 'MUSER_MANAGE', '客户', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(5, 'DISUSER_MANAGE', '会员', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(6, 'SEND_ORDER_MANAGE', '寄件运单', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(7, 'SIGN_ORDER_MANAGE', '签收运单', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(8, 'KEFU', '客服', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(9, 'SCAN_MANAGE', '扫描', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(10, 'GATHER_MANAGE', '收款', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(11, 'SUBSTATIC_MANAGE', '报表', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(12, 'MONITOR', '监控', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(13, 'WAGES_MANAGER', '工资', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(14, 'JIAMENG', '加盟', NULL, '2016-08-16 09:54:22', 0, 'LINK', 1, NULL),
	(15, 'JUDGE_MANAGE', '评价', NULL, '2016-08-16 09:54:22', 0, 'LINK', 1, NULL),
	(16, 'ZL_MAP', '战略地图', NULL, '2016-08-16 09:54:22', 0, 'LINK', 1, NULL),
	(17, 'HELP', '帮助', NULL, '2016-08-16 09:54:22', 0, 'LINK', 1, NULL),
	(18, 'SYSTEM_USER', '系统用户', NULL, '2016-08-16 09:58:39', 2, 'LINK', 1, NULL),
	(19, 'OPERATE_LOG', '操作日志', NULL, '2016-08-16 09:58:39', 2, 'LINK', 1, '124'),
	(20, 'LGC_SUBSTATION', '快递分站', NULL, '2016-08-16 10:02:19', 3, 'LINK', 1, '124'),
	(21, 'LGC_COURIER', '快递员', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, NULL),
	(22, 'WAREHOUSE_STAFF', '仓管员', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(23, 'LGC_CRULE', '代收款规则', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(24, 'LGC_VRULE', '保价规则', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(25, 'ITEM_TYPE', '物品类型配置', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(26, 'TIME_TYPE', '时效类型配置', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(27, 'FOR_CPN', '转件公司配置', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(28, 'LGC_RTYPE', '规则配置', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(29, 'WEIGHT_CONFIG', '电子秤配置', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(30, 'MESSAGE_SEND', '短信发送查询', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(31, 'MESSAGE_CZ', '短信充值查询', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(32, 'LGC_FRULE', '运费报价维护', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(33, 'MOBILE_USER', '手机用户', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(34, 'MUSER_LIST', '月结用户', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, NULL),
	(35, 'CUSER_LIST', '代收货款用户', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, NULL),
	(36, 'MOBILE_MSTYPE', '月结客户优惠管理', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(37, 'MOBILE_CSTYPE', '代收客户优惠管理', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(38, 'DISUSER_ADD', '会员新增', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(39, 'DISUSER_URECHARGE', '会员充值', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(40, 'DISUSER_LIST', '会员列表', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(41, 'DISUSER_TLIST', '优惠管理', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, '124'),
	(42, 'DISUSER_RLIST', '充值流水', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(43, 'DISUSER_CLIST', '扣款流水', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(44, 'ORDER_INPUT', '寄件单录入', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(45, 'SEND_BANTCH_ORDER', '寄件单批量导入', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(46, 'SEND_ORDER_LIST', '寄件单查询', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, NULL),
	(47, 'SEND_ORDER_EXAM', '寄件单审核', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(48, 'ORDER_EDIT', '运单修改', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, NULL),
	(49, 'ORDER_EXAM_HLIST', '运单修改流水', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, NULL),
	(50, 'BATCH_XD', '批量下单', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(51, 'UPLOAD_ORDER_PLANE', '面单上传', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(52, 'ORDER_SINPUT', '签收单录入', NULL, '2016-08-16 10:29:09', 7, 'LINK', 1, '124'),
	(53, 'REV_BANTCH_ORDER', '签收单批量导入', NULL, '2016-08-16 10:29:09', 7, 'LINK', 1, '124'),
	(54, 'REV_ORDER_LIST', '签收单查询', NULL, '2016-08-16 10:29:09', 7, 'LINK', 1, NULL),
	(55, 'REV_ORDER_EXAM', '签收单审核', NULL, '2016-08-16 10:29:09', 7, 'LINK', 1, '124'),
	(56, 'ORDER_ADD', '在线下单', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(57, 'ORDER_ASIGN', '运单分配', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124,125'),
	(58, 'ORDER_LIST', '运单列表', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, NULL),
	(59, 'ORDER_TRACKS_LIST', '运单查询', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, NULL),
	(60, 'LGC_COURIER_AREA', '快递员收派范围', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(61, 'LGC_AREAS', '公司收派范围', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(62, 'PRO_LIST', '问题件查询', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, NULL),
	(63, 'PRO_RESON', '问题件原因配置', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(64, 'NOTIFY_LIST', '公告通知', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(65, 'TAKE_SCAN', '收件扫描', NULL, '2016-08-16 10:55:01', 9, 'LINK', 1, '124'),
	(66, 'SEND_SCAN', '派件扫描', NULL, '2016-08-16 10:55:01', 9, 'LINK', 1, '124'),
	(67, 'SIGN_SCAN', '签收扫描', NULL, '2016-08-16 10:55:01', 9, 'LINK', 1, '124'),
	(68, 'SCAN_SREV', '网点入仓扫描', NULL, '2016-08-16 10:55:01', 9, 'LINK', 1, '124'),
	(69, 'SCAN_SSEND', '网点出仓扫描', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, '124'),
	(70, 'SCAN_FROSCAN', '转件扫描', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, '124'),
	(71, 'CENTER_WAREHOUSE_REVSCAN', '中心入仓扫描', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, '124'),
	(72, 'SCAN_EXCHANGE', '转件清单', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, NULL),
	(73, 'SCAN_PROSCAN', '问题件扫描', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, '124'),
	(74, 'SCAN_REVPRINT', '收件明细打印', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, NULL),
	(75, 'SCAN_SNEXTPRINT', '出站交接单打印', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, NULL),
	(76, 'COURIER_SETTLE_CREATE', '快递员账单生成', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(77, 'COURIER_SETTLE_LIST', '快递员收款登记', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(78, 'COURIER_SETTLE_EXAM', '快递员收款审核', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(79, 'MONTH_SETTLE_CREATE', '月结客户账单生成', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(80, 'MONTH_SETTLE_LIST', '月结收款登记', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(81, 'MONTH_SETTLE_EXAM', '月结收款审核', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(82, 'SUBSTATIC_DAY_LGC', '公司每日报表', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, '124'),
	(83, 'SUBSTATIC_DAY_SUB', '网点每日报表', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(84, 'SUBSTATIC_DAY_COU', '快递员每日报表', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(85, 'SUBSTATIC_DAY_COUNT', '快递员收派统计表', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(86, 'SUBSTATIC_MONTHCOUNT', '月结用户收派件明细', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(87, 'SUBSTATIC_MONTHUSERCOUNT', '月结用户月报', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(88, 'SUBSTATIC_MUSERRC', '月结客户每月发货统计', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(89, 'SUBSTATIC_CODLIST', '代收货款明细', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(90, 'SUBSTATIC_CODMONTH', '代收货款月报', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(91, 'SUB_SPHB', '网点收派环比表', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(92, 'COURIER_SPHB', '快递员收派环比表', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(93, 'YPJWQS', '有派件未签收对比表', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, '124'),
	(94, 'SCAN_CREV', '快递员收件', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(95, 'SCAN_CSEND', '快递员派件', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(96, 'MONITOR_ASIGN', '分配时效监控', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(97, 'MONITOR_TAKE', '收件时效监控', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(98, 'MONITOR_REV', '入仓监控', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(99, 'MONITOR_SEND', '出仓监控', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(100, 'SUBSTATIC_SUBSCAN', '网点扫描数据汇总', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(101, 'SUBSTATIC_SCAN', '运单扫描记录', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(102, 'WAGES_LIST', '工资表', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(103, 'WAGES_INPUT', '费用录入', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(104, 'WAGES_INPUT_LIST', '费用录入流水', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(105, 'WAGES_COST_MANAGER', '快递员费用管理', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(106, 'WAGE_COST_EDIT', '费用编辑流水', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(107, 'WAGES_COST_NAME', '费用名称维护', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, '124'),
	(108, 'WAGES_TC_EDIT', '快递员提成维护', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(109, 'WAGES_TC_LIST', '提成维护记录', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(110, 'JIAMENG_RULE', '报价维护', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(111, 'JIAMENG_ZLIST', '中心加盟对账', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(112, 'JIAMENG_SLIST', '中心派件对账', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(113, 'JIAMENG_KAIZHANG', '预付款开帐', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(114, 'JIAMENG_CHONGZHI', '预付款充值冲账', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(115, 'JIAMENG_SUBZLIST', '网点中转费查询', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, NULL),
	(116, 'JIAMENG_SUBSLIST', '网点派件费查询', NULL, '2016-08-16 11:13:05', 14, 'LINK', 1, NULL),
	(117, 'JIAMENG_YUE', '预付款余额查询', NULL, '2016-08-16 11:13:05', 14, 'LINK', 1, NULL),
	(118, 'JIAMENG_DETAIL', '收支明细查询', NULL, '2016-08-16 11:13:05', 14, 'LINK', 1, NULL),
	(119, 'JIAMENG_ACOUNT', '网点对账查询', NULL, '2016-08-16 11:13:05', 14, 'LINK', 1, NULL),
	(120, 'JUDGE_ALL', '快递员评价汇总', NULL, '2016-08-16 11:14:02', 15, 'LINK', 1, NULL),
	(121, 'JUDGE_LABEL', '标签管理', NULL, '2016-08-16 11:14:03', 15, 'LINK', 1, '124'),
	(122, 'JUDGE_DETAILLIST', '每单记录', NULL, '2016-08-16 11:14:03', 15, 'LINK', 1, NULL),
	(123, 'ZL_MAP_S', '地图', NULL, '2016-08-16 11:14:34', 16, 'LINK', 1, '124'),
	(124, 'LISTSUBSTATION', '具有所有网点权限', NULL, '2016-08-16 11:18:45', 1, 'LINK', 0, NULL),
	(125, 'ORDER_ASSIGN_', '查看未分配订单', NULL, '2016-08-16 11:20:04', 1, 'LINK', 1, NULL),
	(126, 'CUSTOMER_LIST', '客户列表', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(127, 'CUSTOMER_HUIFANG', '客户回访', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(128, 'CUSTOMER_REPORT', '客户报表', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(129, 'SYSTEM_PUNISH', '处罚条例', NULL, '2016-08-16 10:05:00', 17, 'LINK', 1, NULL);


INSERT INTO `boss_auth_group` (`id`, `auth_id`, `group_id`) VALUES
	(2699, 2, 18),
	(2700, 18, 18),
	(2701, 19, 18),
	(2702, 3, 18),
	(2703, 20, 18),
	(2704, 21, 18),
	(2705, 22, 18),
	(2706, 23, 18),
	(2707, 24, 18),
	(2708, 25, 18),
	(2709, 26, 18),
	(2710, 27, 18),
	(2711, 28, 18),
	(2712, 29, 18),
	(2713, 30, 18),
	(2714, 31, 18),
	(2715, 32, 18),
	(2716, 4, 18),
	(2717, 33, 18),
	(2718, 34, 18),
	(2719, 35, 18),
	(2720, 36, 18),
	(2721, 37, 18),
	(2722, 126, 18),
	(2723, 127, 18),
	(2724, 128, 18),
	(2725, 5, 18),
	(2726, 38, 18),
	(2727, 39, 18),
	(2728, 40, 18),
	(2729, 41, 18),
	(2730, 42, 18),
	(2731, 43, 18),
	(2732, 16, 18),
	(2733, 123, 18),
	(2734, 17, 18),
	(2735, 129, 18),
	(2736, 3, 19),
	(2737, 21, 19),
	(2738, 4, 19),
	(2739, 34, 19),
	(2740, 35, 19),
	(2741, 5, 19),
	(2742, 40, 19),
	(2743, 42, 19),
	(2744, 43, 19),
	(2745, 8, 19),
	(2746, 56, 19),
	(2747, 57, 19),
	(2748, 58, 19),
	(2749, 59, 19),
	(2750, 60, 19),
	(2751, 61, 19),
	(2752, 62, 19),
	(2753, 64, 19),
	(2754, 14, 19),
	(2755, 115, 19),
	(2756, 116, 19),
	(2757, 117, 19),
	(2758, 118, 19),
	(2759, 119, 19),
	(2760, 15, 19),
	(2761, 120, 19),
	(2762, 122, 19),
	(2763, 16, 19),
	(2764, 123, 19),
	(2765, 17, 19),
	(2766, 129, 19),
	(2767, 8, 20),
	(2768, 56, 20),
	(2769, 57, 20),
	(2770, 58, 20),
	(2771, 59, 20),
	(2772, 60, 20),
	(2773, 61, 20),
	(2774, 62, 20),
	(2775, 64, 20),
	(2776, 9, 20),
	(2777, 65, 20),
	(2778, 66, 20),
	(2779, 67, 20),
	(2780, 68, 20),
	(2781, 69, 20),
	(2782, 70, 20),
	(2783, 71, 20),
	(2784, 72, 20),
	(2785, 73, 20),
	(2786, 74, 20),
	(2787, 75, 20),
	(2788, 11, 20),
	(2789, 12, 20),
	(2790, 100, 20),
	(2791, 101, 20),
	(2792, 16, 20),
	(2793, 123, 20),
	(2794, 17, 20),
	(2795, 129, 20),
	(2796, 8, 21),
	(2797, 56, 21),
	(2798, 57, 21),
	(2799, 58, 21),
	(2800, 59, 21),
	(2801, 60, 21),
	(2802, 61, 21),
	(2803, 62, 21),
	(2804, 64, 21),
	(2805, 9, 21),
	(2806, 65, 21),
	(2807, 66, 21),
	(2808, 67, 21),
	(2809, 68, 21),
	(2810, 69, 21),
	(2811, 70, 21),
	(2812, 71, 21),
	(2813, 72, 21),
	(2814, 73, 21),
	(2815, 74, 21),
	(2816, 75, 21),
	(2817, 11, 21),
	(2818, 83, 21),
	(2819, 84, 21),
	(2820, 85, 21),
	(2821, 86, 21),
	(2822, 87, 21),
	(2823, 88, 21),
	(2824, 89, 21),
	(2825, 90, 21),
	(2826, 91, 21),
	(2827, 92, 21),
	(2828, 93, 21),
	(2829, 12, 21),
	(2830, 94, 21),
	(2831, 95, 21),
	(2832, 96, 21),
	(2833, 97, 21),
	(2834, 98, 21),
	(2835, 99, 21),
	(2836, 100, 21),
	(2837, 101, 21),
	(2838, 16, 21),
	(2839, 123, 21),
	(2840, 17, 21),
	(2841, 129, 21),
	(2842, 3, 22),
	(2843, 20, 22),
	(2844, 21, 22),
	(2845, 4, 22),
	(2846, 33, 22),
	(2847, 34, 22),
	(2848, 35, 22),
	(2849, 5, 22),
	(2850, 40, 22),
	(2851, 42, 22),
	(2852, 43, 22),
	(2853, 6, 22),
	(2854, 44, 22),
	(2855, 45, 22),
	(2856, 46, 22),
	(2857, 47, 22),
	(2858, 48, 22),
	(2859, 49, 22),
	(2860, 50, 22),
	(2861, 51, 22),
	(2862, 7, 22),
	(2863, 52, 22),
	(2864, 53, 22),
	(2865, 54, 22),
	(2866, 55, 22),
	(2867, 16, 22),
	(2868, 123, 22),
	(2869, 17, 22),
	(2870, 129, 22),
	(2871, 3, 23),
	(2872, 20, 23),
	(2873, 21, 23),
	(2874, 4, 23),
	(2875, 33, 23),
	(2876, 34, 23),
	(2877, 35, 23),
	(2878, 126, 23),
	(2879, 127, 23),
	(2880, 128, 23),
	(2881, 5, 23),
	(2882, 38, 23),
	(2883, 39, 23),
	(2884, 40, 23),
	(2885, 42, 23),
	(2886, 43, 23),
	(2887, 8, 23),
	(2888, 56, 23),
	(2889, 57, 23),
	(2890, 58, 23),
	(2891, 59, 23),
	(2892, 60, 23),
	(2893, 61, 23),
	(2894, 62, 23),
	(2895, 63, 23),
	(2896, 64, 23),
	(2897, 15, 23),
	(2898, 120, 23),
	(2899, 121, 23),
	(2900, 122, 23),
	(2901, 16, 23),
	(2902, 123, 23),
	(2903, 17, 23),
	(2904, 129, 23),
	(2905, 10, 24),
	(2906, 76, 24),
	(2907, 77, 24),
	(2908, 78, 24),
	(2909, 79, 24),
	(2910, 80, 24),
	(2911, 81, 24),
	(2912, 11, 24),
	(2913, 82, 24),
	(2914, 83, 24),
	(2915, 84, 24),
	(2916, 85, 24),
	(2917, 86, 24),
	(2918, 87, 24),
	(2919, 88, 24),
	(2920, 89, 24),
	(2921, 90, 24),
	(2922, 91, 24),
	(2923, 92, 24),
	(2924, 93, 24),
	(2925, 13, 24),
	(2926, 102, 24),
	(2927, 103, 24),
	(2928, 104, 24),
	(2929, 105, 24),
	(2930, 106, 24),
	(2931, 107, 24),
	(2932, 108, 24),
	(2933, 109, 24),
	(2934, 14, 24),
	(2935, 110, 24),
	(2936, 111, 24),
	(2937, 112, 24),
	(2938, 113, 24),
	(2939, 114, 24),
	(2940, 115, 24),
	(2941, 117, 24),
	(2942, 118, 24),
	(2943, 119, 24),
	(2944, 16, 24),
	(2945, 123, 24),
	(2946, 17, 24),
	(2947, 129, 24),
	(2948, 2, 25),
	(2949, 18, 25),
	(2950, 19, 25),
	(2951, 3, 25),
	(2952, 20, 25),
	(2953, 21, 25),
	(2954, 22, 25),
	(2955, 23, 25),
	(2956, 24, 25),
	(2957, 25, 25),
	(2958, 26, 25),
	(2959, 27, 25),
	(2960, 28, 25),
	(2961, 29, 25),
	(2962, 30, 25),
	(2963, 31, 25),
	(2964, 32, 25),
	(2965, 4, 25),
	(2966, 33, 25),
	(2967, 34, 25),
	(2968, 35, 25),
	(2969, 36, 25),
	(2970, 37, 25),
	(2971, 126, 25),
	(2972, 127, 25),
	(2973, 128, 25),
	(2974, 5, 25),
	(2975, 38, 25),
	(2976, 39, 25),
	(2977, 40, 25),
	(2978, 41, 25),
	(2979, 42, 25),
	(2980, 43, 25),
	(2981, 6, 25),
	(2982, 44, 25),
	(2983, 45, 25),
	(2984, 46, 25),
	(2985, 47, 25),
	(2986, 48, 25),
	(2987, 49, 25),
	(2988, 50, 25),
	(2989, 51, 25),
	(2990, 7, 25),
	(2991, 52, 25),
	(2992, 53, 25),
	(2993, 54, 25),
	(2994, 55, 25),
	(2995, 8, 25),
	(2996, 56, 25),
	(2997, 57, 25),
	(2998, 58, 25),
	(2999, 59, 25),
	(3000, 60, 25),
	(3001, 61, 25),
	(3002, 62, 25),
	(3003, 63, 25),
	(3004, 64, 25),
	(3005, 9, 25),
	(3006, 65, 25),
	(3007, 66, 25),
	(3008, 67, 25),
	(3009, 68, 25),
	(3010, 69, 25),
	(3011, 70, 25),
	(3012, 71, 25),
	(3013, 72, 25),
	(3014, 73, 25),
	(3015, 74, 25),
	(3016, 10, 25),
	(3017, 76, 25),
	(3018, 77, 25),
	(3019, 78, 25),
	(3020, 79, 25),
	(3021, 80, 25),
	(3022, 81, 25),
	(3023, 11, 25),
	(3024, 82, 25),
	(3025, 83, 25),
	(3026, 84, 25),
	(3027, 85, 25),
	(3028, 86, 25),
	(3029, 87, 25),
	(3030, 88, 25),
	(3031, 89, 25),
	(3032, 90, 25),
	(3033, 91, 25),
	(3034, 92, 25),
	(3035, 93, 25),
	(3036, 12, 25),
	(3037, 94, 25),
	(3038, 95, 25),
	(3039, 96, 25),
	(3040, 97, 25),
	(3041, 98, 25),
	(3042, 99, 25),
	(3043, 100, 25),
	(3044, 101, 25),
	(3045, 13, 25),
	(3046, 102, 25),
	(3047, 103, 25),
	(3048, 104, 25),
	(3049, 105, 25),
	(3050, 106, 25),
	(3051, 107, 25),
	(3052, 108, 25),
	(3053, 109, 25),
	(3054, 14, 25),
	(3055, 110, 25),
	(3056, 111, 25),
	(3057, 112, 25),
	(3058, 113, 25),
	(3059, 114, 25),
	(3060, 115, 25),
	(3061, 116, 25),
	(3062, 117, 25),
	(3063, 118, 25),
	(3064, 119, 25),
	(3065, 15, 25),
	(3066, 120, 25),
	(3067, 121, 25),
	(3068, 122, 25),
	(3069, 16, 25),
	(3070, 123, 25),
	(3071, 17, 25),
	(3072, 129, 25);


INSERT INTO `boss_group` (`id`, `group_name`, `group_desc`, `create_time`, `clogin`) VALUES
	(18, '系统和资料管理员', '管理客户、会员、公司、规则和后台登录用户资料', '2016-09-02 15:49:14', NULL),
	(19, '加盟网点', '加盟、客服、查询、评价', '2016-09-02 15:52:28', NULL),
	(20, '仓管员', '扫描、客服、监控', '2016-09-02 16:00:09', NULL),
	(21, '网点负责人', '扫描、客服、报表、监控', '2016-09-02 16:02:39', NULL),
	(22, '录单和审单人员', '录单、审单、查询', '2016-09-02 16:06:27', NULL),
	(23, '客服人员', '客服、公司、客户、会员、评价', '2016-09-02 16:08:33', NULL),
	(24, '财务人员', '财务', '2016-09-02 16:13:51', NULL),
	(25, '管理员', '超级权限', '2016-09-02 16:16:19', NULL);



alter table push_notice add type int(1) DEFAULT '0' COMMENT '0本地，1大后台推送';

alter table lgc add month_setting_url varchar(255);
alter table lgc add weixin_no varchar(255);
alter table lgc add process varchar(32);
alter table lgc add concat_person varchar(32);


ALTER TABLE `order_info` DROP INDEX `deled`;


