ALTER TABLE `require_list`
	ADD COLUMN `view_type` VARCHAR(50) NULL DEFAULT NULL COMMENT 'CHECKBOX、REDIO等' AFTER `editable`;


UPDATE `require_list` SET `view_type`='CHECKBOX' ;


INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (16, 1, 'freight_type', '运费模式', 'LAST', 'Y', 'RADIO');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (17, 1, 'weight_type', '重量模式', 'LAST', 'Y', 'RADIO');



CREATE TABLE `freight_rule` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`vpay` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '附加费',
	`create_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
	`create_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '创建人',
	`fmoney` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '起步费用',
	`fdistance` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '首距离',
	`step_distance` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '每增加距离',
	`step_distance_money` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '每增加距离费用',
	`fweight` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '首重',
	`step_weight` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '增加重量',
	`step_weight_money` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '增加重量费用',
	`freight_text` TEXT NULL COMMENT '显示text',
	PRIMARY KEY (`id`)
)
COMMENT='邮费规则表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=1;


CREATE TABLE `freight_rule_itype` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`rid` INT(11) NOT NULL COMMENT '规则id',
	`itype` VARCHAR(50) NOT NULL COMMENT '规则物品类型',
	PRIMARY KEY (`id`),
	INDEX `rid` (`rid`)
)
COMMENT='邮费规则物品类型'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=1;


CREATE TABLE `freight_rule_ttype` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`rid` INT(11) NOT NULL COMMENT '规则id',
	`time_type` VARCHAR(50) NOT NULL COMMENT '规则时效类型',
	PRIMARY KEY (`id`),
	INDEX `rid` (`rid`)
)
COMMENT='邮费规则'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=1;


ALTER TABLE `order_track`
	ADD COLUMN `opname` VARCHAR(50) NULL DEFAULT NULL COMMENT '操作人' AFTER `scan_oname`;

update order_track set order_status='TAKEING' where order_status='TAKE_SCAN' ;

ALTER TABLE `order_track`
	ADD INDEX `order_time` (`order_time`);





CREATE TABLE `weight_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rev_minv` double(10,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `warehouse_minv` double(10,2) DEFAULT NULL,
  `select_v` int(11) DEFAULT '0' COMMENT '0以收件为准,1中心入仓重量为准,2收件入仓以重的为准',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='电子秤配置';

CREATE TABLE `courier_salary_pay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '工资发放时间',
  `courier_no` varchar(64) DEFAULT NULL,
  `cost_month` varchar(8) DEFAULT NULL COMMENT '发放月份',
  `cost_amount` decimal(10,2) DEFAULT NULL,
  `courier_tc_way` int(1) DEFAULT NULL COMMENT '提成计算方法：1按最后一次修改2按每天统计',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='发放工资记录表';

CREATE TABLE `judge_courier_label` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `courier_no` varchar(32) DEFAULT NULL,
  `label_id` bigint(20) DEFAULT NULL,
  `times` int(11) DEFAULT '0' COMMENT '评价次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `judge_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `courier_no` varchar(64) DEFAULT NULL,
  `star` int(11) DEFAULT '5',
  `order_no` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `user_no` varchar(64) DEFAULT NULL,
  `label_txt` varchar(256) DEFAULT NULL,
  `comments` varchar(512) DEFAULT NULL,
  `lgc_order_no` varchar(32) DEFAULT NULL,
  `take_or_send` int(11) DEFAULT '0' COMMENT '0取件1派件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='订单评价';

CREATE TABLE `judge_label` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `label_name` varchar(128) DEFAULT NULL COMMENT '标签名称',
  `status` int(1) DEFAULT '1' COMMENT '0停用1启用',
  `create_time` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='评价标签表';

CREATE TABLE `push_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_update_time` datetime NOT NULL,
  `user_no` varchar(32) DEFAULT NULL COMMENT '创建人账号',
  `is_send` int(1) NOT NULL DEFAULT '0' COMMENT '是否已经推送过：0没1推送过',
  `content` text NOT NULL COMMENT '消息内容',
  `title` varchar(128) NOT NULL COMMENT '消息标题',
  `push_name` varchar(32) DEFAULT NULL COMMENT '推送人名字',
  `is_red_title` int(1) DEFAULT '0' COMMENT '标题是否加红',
  `edit_name` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='推送消息给快递员表';

CREATE TABLE `table_field_sort` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tab` varchar(32) DEFAULT NULL COMMENT 'table别名',
  `tab_name` varchar(64) DEFAULT NULL COMMENT 'tab名称',
  `col` varchar(32) DEFAULT NULL,
  `col_name` varchar(64) DEFAULT NULL,
  `sort` int(11) DEFAULT '0' COMMENT '字段排序',
  `is_show` int(1) DEFAULT '1' COMMENT '字段是否显示0不显示1显示',
  `user_no` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8;

insert into boss_auth(auth_code,auth_name,create_time,parent_id,category,`status`) 
values('WEIGHT_CONFIG','电子秤配置',now(),(select id from boss_auth where auth_code='LGC_MANAGE'),'LINK',1);

insert into boss_auth(auth_code,auth_name,create_time,parent_id,category,`status`) 
values('CENTER_WAREHOUSE_REVSCAN','中心入仓扫描',now(),(select id from boss_auth where auth_code='SCAN_MANAGE'),'LINK',1);

alter table order_info add center_warehouse_weight double;

alter table substation add sub_type int(1) DEFAULT 0 ;


insert into boss_auth (auth_code,auth_name,create_time,parent_id,category,`status`)
values('JUDGE_MANAGE','评价',NOW(),0,'LINK',1);

insert into boss_auth (auth_code,auth_name,create_time,parent_id,category,`status`)
values('JUDGE_ALL','快递员评价汇总',NOW(),(select id from boss_auth where auth_code='JUDGE_MANAGE'),'LINK',1);

insert into boss_auth (auth_code,auth_name,create_time,parent_id,category,`status`)
values('JUDGE_LABEL','标签管理',NOW(),(select id from boss_auth where auth_code='JUDGE_MANAGE'),'LINK',1);

insert into boss_auth (auth_code,auth_name,create_time,parent_id,category,`status`)
values('JUDGE_DETAILLIST','每单记录',NOW(),(select id from boss_auth where auth_code='JUDGE_MANAGE'),'LINK',1);

INSERT INTO `boss_auth` (auth_code,auth_name,create_time,parent_id,category,`status`) VALUES ( 'NOTIFY_LIST', '公告通知', now(), (select id from boss_auth where auth_code='KEFU_MG'), 'LINK', '1');

insert into msg_type(msg_code,`describe`,jump_type,`level`,parent_code) values(499,'通知公告','text',2,501);

INSERT INTO `table_field_sort` VALUES ('1', 'order_olist', '运单分配', 'id', '序号', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('2', 'order_olist', '运单分配', 'lgc_order_no', '运单号', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('3', 'order_olist', '运单分配', 'item_Status', '物品类型', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('4', 'order_olist', '运单分配', 'take_courier_name', '取件员', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('5', 'order_olist', '运单分配', 'take_courier_name', '寄件员', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('6', 'order_olist', '运单分配', 'send_phone', '寄件电话', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('7', 'order_olist', '运单分配', 'sendaddr', '寄件地址', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('8', 'order_olist', '运单分配', 'rev_name', '收件人', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('9', 'order_olist', '运单分配', 'rev_phone', '收件电话', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('10', 'order_olist', '运单分配', 'revaddr', '收件地址', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('11', 'order_olist', '运单分配', 'source', '来源', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('12', 'order_olist', '运单分配', 'status', '运单状态', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('13', 'order_olist', '运单分配', 'asign_status', '分配状态', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('14', 'order_olist', '运单分配', 'asign_name', '分配人', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('15', 'order_olist', '运单分配', 'create_time', '下单时间', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('16', 'order_olist', '运单分配', 'edit', '操作', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('17', 'order_olist', '运单分配', 'order_no', '订单编号', '16', '0', '0');
INSERT INTO `table_field_sort` VALUES ('18', 'order_olist', '运单分配', 'cm_order_no', '外部单号', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('19', 'order_olist', '运单分配', 'item_name', '物品名称', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('20', 'order_olist', '运单分配', 'item_count', '件数', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('21', 'order_olist', '运单分配', 'asign_status', '分配状态', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('22', 'order_olist', '运单分配', 'item_Status', '物品类型', '21', '0', '0');
INSERT INTO `table_field_sort` VALUES ('23', 'order_olist', '运单分配', 'time_type', '物品时效', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('24', 'order_olist', '运单分配', 'freight_type', '付款人', '23', '0', '0');
INSERT INTO `table_field_sort` VALUES ('25', 'order_olist', '运单分配', 'pay_type', '付款方式', '24', '0', '0');
INSERT INTO `table_field_sort` VALUES ('26', 'order_olist', '运单分配', 'good_valuation', '保价费', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('27', 'order_olist', '运单分配', 'dis_user_no', '会员号', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('28', 'order_olist', '运单分配', 'cod_name', '贷款号', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('29', 'order_olist', '运单分配', 'cod_sname', '公司简称', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('30', 'order_olist', '运单分配', 'send_name', '寄件人', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('31', 'order_olist', '运单分配', 'send_phone', '寄件人电话', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('32', 'order_olist', '运单分配', 'create_time', '寄件时间', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('33', 'order_olist', '运单分配', 'send_addr', '寄件地址', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('34', 'order_olist', '运单分配', 'inputer', '寄件录入人', '34', '1', '0');
INSERT INTO `table_field_sort` VALUES ('35', 'order_olist', '运单分配', 'send_order_time', '签收时间', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('36', 'order_olist', '运单分配', 'sign_inputer', '签收录入人', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('37', 'order_olist', '运单分配', 'remark', '备注', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('38', 'order_relist', '签收单审核', 'id', '序号', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('39', 'order_relist', '签收单审核', 'lgc_order_no', '运单号', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('40', 'order_relist', '签收单审核', 'monthSname', '公司简称', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('41', 'order_relist', '签收单审核', 'month_settle_no', '月结账号', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('42', 'order_relist', '签收单审核', 'item_weight', '重量', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('43', 'order_relist', '签收单审核', 'freight', '运费', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('44', 'order_relist', '签收单审核', 'vpay', '附加费', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('45', 'order_relist', '签收单审核', 'good_price', '代收款', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('46', 'order_relist', '签收单审核', 'freight_type', '付款人', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('47', 'order_relist', '签收单审核', 'pay_type', '付款方式', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('48', 'order_relist', '签收单审核', 'sendSubstationName', '派件网点', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('49', 'order_relist', '签收单审核', 'send_courier_name', '派件员', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('50', 'order_relist', '签收单审核', 'send_order_time', '签收时间', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('51', 'order_relist', '签收单审核', 'rece_no', '回单号', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('52', 'order_relist', '签收单审核', 'for_no', '转单号', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('53', 'order_relist', '签收单审核', 'edit', '操作', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('54', 'order_relist', '签收单审核', 'order_no', '订单编号', '16', '0', '0');
INSERT INTO `table_field_sort` VALUES ('55', 'order_relist', '签收单审核', 'cm_order_no', '外部单号', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('56', 'order_relist', '签收单审核', 'item_name', '物品名称', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('57', 'order_relist', '签收单审核', 'item_count', '件数', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('58', 'order_relist', '签收单审核', 'asign_status', '分配状态', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('59', 'order_relist', '签收单审核', 'item_Status', '物品类型', '21', '0', '0');
INSERT INTO `table_field_sort` VALUES ('60', 'order_relist', '签收单审核', 'time_type', '物品时效', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('61', 'order_relist', '签收单审核', 'good_valuation', '保价费', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('62', 'order_relist', '签收单审核', 'dis_user_no', '会员号', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('63', 'order_relist', '签收单审核', 'cod_name', '贷款号', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('64', 'order_relist', '签收单审核', 'cod_sname', '公司简称', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('65', 'order_relist', '签收单审核', 'send_name', '寄件人', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('66', 'order_relist', '签收单审核', 'send_phone', '寄件人电话', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('67', 'order_relist', '签收单审核', 'create_time', '寄件时间', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('68', 'order_relist', '签收单审核', 'send_addr', '寄件地址', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('69', 'order_relist', '签收单审核', 'rev_name', '收件人', '33', '0', '0');
INSERT INTO `table_field_sort` VALUES ('70', 'order_relist', '签收单审核', 'rev_phone', '收件人电话', '34', '0', '0');
INSERT INTO `table_field_sort` VALUES ('71', 'order_relist', '签收单审核', 'inputer', '寄件录入人', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('72', 'order_relist', '签收单审核', 'send_order_time', '签收时间', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('73', 'order_relist', '签收单审核', 'sign_inputer', '签收录入人', '37', '0', '0');
INSERT INTO `table_field_sort` VALUES ('74', 'order_relist', '签收单审核', 'rev_addr', '收件地址', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('75', 'order_relist', '签收单审核', 'remark', '备注', '39', '0', '0');
INSERT INTO `table_field_sort` VALUES ('76', 'order_revlist', '签收单查询', 'id', '序号', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('77', 'order_revlist', '签收单查询', 'lgc_order_no', '运单号', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('78', 'order_revlist', '签收单查询', 'monthSname', '公司简称', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('79', 'order_revlist', '签收单查询', 'month_settle_no', '月结账号', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('80', 'order_revlist', '签收单查询', 'item_weight', '重量', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('81', 'order_revlist', '签收单查询', 'freight', '运费', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('82', 'order_revlist', '签收单查询', 'vpay', '附加费', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('83', 'order_revlist', '签收单查询', 'good_price', '代收款', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('84', 'order_revlist', '签收单查询', 'freight_type', '付款人', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('85', 'order_revlist', '签收单查询', 'pay_type', '付款方式', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('86', 'order_revlist', '签收单查询', 'send_substation_no', '派件网点', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('87', 'order_revlist', '签收单查询', 'send_courier_name', '派件员', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('88', 'order_revlist', '签收单查询', 'send_order_time', '签收时间', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('89', 'order_revlist', '签收单查询', 'rece_no', '回单号', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('90', 'order_revlist', '签收单查询', 'for_no', '转单号', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('91', 'order_revlist', '签收单查询', 'sign_examine_status', '审核', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('92', 'order_revlist', '签收单查询', 'edit', '操作', '16', '1', '0');
INSERT INTO `table_field_sort` VALUES ('93', 'order_revlist', '签收单查询', 'order_no', '订单编号', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('94', 'order_revlist', '签收单查询', 'cm_order_no', '外部单号', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('95', 'order_revlist', '签收单查询', 'item_name', '物品名称', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('96', 'order_revlist', '签收单查询', 'item_count', '件数', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('97', 'order_revlist', '签收单查询', 'asign_status', '分配状态', '21', '0', '0');
INSERT INTO `table_field_sort` VALUES ('98', 'order_revlist', '签收单查询', 'item_Status', '物品类型', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('99', 'order_revlist', '签收单查询', 'time_type', '物品时效', '23', '0', '0');
INSERT INTO `table_field_sort` VALUES ('100', 'order_revlist', '签收单查询', 'freight_type', '付款人', '24', '0', '0');
INSERT INTO `table_field_sort` VALUES ('101', 'order_revlist', '签收单查询', 'pay_type', '付款方式', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('102', 'order_revlist', '签收单查询', 'good_valuation', '保价费', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('103', 'order_revlist', '签收单查询', 'dis_user_no', '会员号', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('104', 'order_revlist', '签收单查询', 'cod_name', '贷款号', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('105', 'order_revlist', '签收单查询', 'cod_sname', '公司简称', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('106', 'order_revlist', '签收单查询', 'send_name', '寄件人', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('107', 'order_revlist', '签收单查询', 'send_phone', '寄件人电话', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('108', 'order_revlist', '签收单查询', 'create_time', '寄件时间', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('109', 'order_revlist', '签收单查询', 'send_addr', '寄件地址', '33', '0', '0');
INSERT INTO `table_field_sort` VALUES ('110', 'order_revlist', '签收单查询', 'rev_name', '收件人', '34', '0', '0');
INSERT INTO `table_field_sort` VALUES ('111', 'order_revlist', '签收单查询', 'rev_phone', '收件人电话', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('112', 'order_revlist', '签收单查询', 'inputer', '寄件录入人', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('113', 'order_revlist', '签收单查询', 'send_order_time', '签收时间', '37', '0', '0');
INSERT INTO `table_field_sort` VALUES ('114', 'order_revlist', '签收单查询', 'sign_inputer', '签收录入人', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('115', 'order_revlist', '签收单查询', 'rev_addr', '收件地址', '39', '0', '0');
INSERT INTO `table_field_sort` VALUES ('116', 'order_revlist', '签收单查询', 'remark', '备注', '40', '0', '0');
INSERT INTO `table_field_sort` VALUES ('117', 'order_selist', '寄件单审核表', 'id', '序号', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('118', 'order_selist', '寄件单审核表', 'lgc_order_no', '运单号', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('119', 'order_selist', '寄件单审核表', 'monthSname', '公司简称	', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('120', 'order_selist', '寄件单审核表', 'month_settle_no', '月结账号', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('121', 'order_selist', '寄件单审核表', 'item_weight', '重量', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('122', 'order_selist', '寄件单审核表', 'freight', '运费', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('123', 'order_selist', '寄件单审核表', 'vpay', '附加费', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('124', 'order_selist', '寄件单审核表', 'good_price', '代收款', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('125', 'order_selist', '寄件单审核表', 'freight_type', '付款人', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('126', 'order_selist', '寄件单审核表', 'pay_type', '付款方式', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('127', 'order_selist', '寄件单审核表', 'takeSubstationName', '寄件网点', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('128', 'order_selist', '寄件单审核表', 'sendSubstationName', '派件网点', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('129', 'order_selist', '寄件单审核表', 'take_courier_name', '取件员', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('130', 'order_selist', '寄件单审核表', 'rece_no', '回单号', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('131', 'order_selist', '寄件单审核表', 'for_no', '转单号', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('132', 'order_selist', '寄件单审核表', 'status', '运单状态', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('133', 'order_selist', '寄件单审核表', 'edit', '操作', '16', '1', '0');
INSERT INTO `table_field_sort` VALUES ('134', 'order_selist', '寄件单审核表', 'order_no', '订单编号', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('135', 'order_selist', '寄件单审核表', 'cm_order_no', '外部单号', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('136', 'order_selist', '寄件单审核表', 'item_name', '物品名称', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('137', 'order_selist', '寄件单审核表', 'item_count', '件数', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('138', 'order_selist', '寄件单审核表', 'asign_status', '分配状态', '21', '0', '0');
INSERT INTO `table_field_sort` VALUES ('139', 'order_selist', '寄件单审核表', 'item_Status', '物品类型', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('140', 'order_selist', '寄件单审核表', 'time_type', '物品时效', '23', '0', '0');
INSERT INTO `table_field_sort` VALUES ('141', 'order_selist', '寄件单审核表', 'freight_type', '付款人', '24', '0', '0');
INSERT INTO `table_field_sort` VALUES ('142', 'order_selist', '寄件单审核表', 'pay_type', '付款方式', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('143', 'order_selist', '寄件单审核表', 'good_valuation', '保价费', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('144', 'order_selist', '寄件单审核表', 'dis_user_no', '会员号', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('145', 'order_selist', '寄件单审核表', 'cod_name', '贷款号', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('146', 'order_selist', '寄件单审核表', 'cod_sname', '公司简称', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('147', 'order_selist', '寄件单审核表', 'send_name', '寄件人', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('148', 'order_selist', '寄件单审核表', 'send_phone', '寄件人电话', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('149', 'order_selist', '寄件单审核表', 'create_time', '寄件时间', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('150', 'order_selist', '寄件单审核表', 'send_addr', '寄件地址', '33', '0', '0');
INSERT INTO `table_field_sort` VALUES ('151', 'order_selist', '寄件单审核表', 'rev_name', '收件人', '34', '0', '0');
INSERT INTO `table_field_sort` VALUES ('152', 'order_selist', '寄件单审核表', 'rev_phone', '收件人电话', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('153', 'order_selist', '寄件单审核表', 'inputer', '寄件录入人', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('154', 'order_selist', '寄件单审核表', 'send_order_time', '签收时间', '37', '0', '0');
INSERT INTO `table_field_sort` VALUES ('155', 'order_selist', '寄件单审核表', 'sign_inputer', '签收录入人', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('156', 'order_selist', '寄件单审核表', 'rev_addr', '收件地址', '39', '0', '0');
INSERT INTO `table_field_sort` VALUES ('157', 'order_selist', '寄件单审核表', 'remark', '备注', '40', '0', '0');
INSERT INTO `table_field_sort` VALUES ('158', 'order_selist', '寄件单审核表', 'examine_status', '审核状态', '41', '0', '0');
INSERT INTO `table_field_sort` VALUES ('159', 'order_sendlist', '寄件单查询列表', 'id', '编号', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('160', 'order_sendlist', '寄件单查询列表', 'lgc_order_no', '运单号', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('161', 'order_sendlist', '寄件单查询列表', 'monthSname', '公司简介', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('162', 'order_sendlist', '寄件单查询列表', 'month_settle_no', '月结账号', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('163', 'order_sendlist', '寄件单查询列表', 'item_weight', '重量', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('164', 'order_sendlist', '寄件单查询列表', 'freight', '运费', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('165', 'order_sendlist', '寄件单查询列表', 'vpay', '附加费', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('166', 'order_sendlist', '寄件单查询列表', 'good_price', '代收款', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('167', 'order_sendlist', '寄件单查询列表', 'sub_station_no', '寄件网点', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('168', 'order_sendlist', '寄件单查询列表', 'send_substation_no', '派件网点', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('169', 'order_sendlist', '寄件单查询列表', 'take_courier_name', '取件员', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('170', 'order_sendlist', '寄件单查询列表', 'send_courier_no', '派件员', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('171', 'order_sendlist', '寄件单查询列表', 'rece_no', '回单号', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('172', 'order_sendlist', '寄件单查询列表', 'for_no', '转单号', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('173', 'order_sendlist', '寄件单查询列表', 'source', '来源', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('174', 'order_sendlist', '寄件单查询列表', 'status', '运单状态', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('175', 'order_sendlist', '寄件单查询列表', 'edit', '操作', '16', '1', '0');
INSERT INTO `table_field_sort` VALUES ('176', 'order_sendlist', '寄件单查询列表', 'order_no', '订单编号', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('177', 'order_sendlist', '寄件单查询列表', 'cm_order_no', '外部单号', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('178', 'order_sendlist', '寄件单查询列表', 'item_name', '物品名称', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('179', 'order_sendlist', '寄件单查询列表', 'item_count', '件数', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('180', 'order_sendlist', '寄件单查询列表', 'asign_status', '分配状态', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('181', 'order_sendlist', '寄件单查询列表', 'item_Status', '物品类型', '23', '0', '0');
INSERT INTO `table_field_sort` VALUES ('182', 'order_sendlist', '寄件单查询列表', 'time_type', '物品时效', '24', '0', '0');
INSERT INTO `table_field_sort` VALUES ('183', 'order_sendlist', '寄件单查询列表', 'freight_type', '付款人', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('184', 'order_sendlist', '寄件单查询列表', 'pay_type', '付款方式', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('185', 'order_sendlist', '寄件单查询列表', 'good_valuation', '保价费', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('186', 'order_sendlist', '寄件单查询列表', 'dis_user_no', '会员号', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('187', 'order_sendlist', '寄件单查询列表', 'cod_name', '贷款号', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('188', 'order_sendlist', '寄件单查询列表', 'cod_sname', '公司简称', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('189', 'order_sendlist', '寄件单查询列表', 'send_name', '寄件人', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('190', 'order_sendlist', '寄件单查询列表', 'send_phone', '寄件人电话', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('191', 'order_sendlist', '寄件单查询列表', 'create_time', '寄件时间', '33', '0', '0');
INSERT INTO `table_field_sort` VALUES ('192', 'order_sendlist', '寄件单查询列表', 'send_addr', '寄件地址', '34', '0', '0');
INSERT INTO `table_field_sort` VALUES ('193', 'order_sendlist', '寄件单查询列表', 'rev_name', '收件人', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('194', 'order_sendlist', '寄件单查询列表', 'rev_phone', '收件人电话', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('195', 'order_sendlist', '寄件单查询列表', 'inputer', '寄件录入人', '37', '0', '0');
INSERT INTO `table_field_sort` VALUES ('196', 'order_sendlist', '寄件单查询列表', 'send_order_time', '签收时间', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('197', 'order_sendlist', '寄件单查询列表', 'sign_inputer', '签收录入人', '39', '0', '0');
INSERT INTO `table_field_sort` VALUES ('198', 'order_sendlist', '寄件单查询列表', 'rev_addr', '收件地址', '40', '0', '0');
INSERT INTO `table_field_sort` VALUES ('199', 'order_sendlist', '寄件单查询列表', 'remark', '备注', '41', '0', '0');