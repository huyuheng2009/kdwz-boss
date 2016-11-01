ALTER TABLE `require_list`
	ADD COLUMN `view_type` VARCHAR(50) NULL DEFAULT NULL COMMENT 'CHECKBOX��REDIO��' AFTER `editable`;


UPDATE `require_list` SET `view_type`='CHECKBOX' ;


INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (16, 1, 'freight_type', '�˷�ģʽ', 'LAST', 'Y', 'RADIO');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (17, 1, 'weight_type', '����ģʽ', 'LAST', 'Y', 'RADIO');



CREATE TABLE `freight_rule` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`vpay` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '���ӷ�',
	`create_time` TIMESTAMP NULL DEFAULT NULL COMMENT '����ʱ��',
	`create_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '������',
	`fmoney` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '�𲽷���',
	`fdistance` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '�׾���',
	`step_distance` DECIMAL(10,2) NULL DEFAULT NULL COMMENT 'ÿ���Ӿ���',
	`step_distance_money` DECIMAL(10,2) NULL DEFAULT NULL COMMENT 'ÿ���Ӿ������',
	`fweight` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '����',
	`step_weight` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '��������',
	`step_weight_money` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '������������',
	`freight_text` TEXT NULL COMMENT '��ʾtext',
	PRIMARY KEY (`id`)
)
COMMENT='�ʷѹ����'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=1;


CREATE TABLE `freight_rule_itype` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`rid` INT(11) NOT NULL COMMENT '����id',
	`itype` VARCHAR(50) NOT NULL COMMENT '������Ʒ����',
	PRIMARY KEY (`id`),
	INDEX `rid` (`rid`)
)
COMMENT='�ʷѹ�����Ʒ����'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=1;


CREATE TABLE `freight_rule_ttype` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`rid` INT(11) NOT NULL COMMENT '����id',
	`time_type` VARCHAR(50) NOT NULL COMMENT '����ʱЧ����',
	PRIMARY KEY (`id`),
	INDEX `rid` (`rid`)
)
COMMENT='�ʷѹ���'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=1;


ALTER TABLE `order_track`
	ADD COLUMN `opname` VARCHAR(50) NULL DEFAULT NULL COMMENT '������' AFTER `scan_oname`;

update order_track set order_status='TAKEING' where order_status='TAKE_SCAN' ;

ALTER TABLE `order_track`
	ADD INDEX `order_time` (`order_time`);





CREATE TABLE `weight_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rev_minv` double(10,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `warehouse_minv` double(10,2) DEFAULT NULL,
  `select_v` int(11) DEFAULT '0' COMMENT '0���ռ�Ϊ׼,1�����������Ϊ׼,2�ռ�������ص�Ϊ׼',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='���ӳ�����';

CREATE TABLE `courier_salary_pay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '���ʷ���ʱ��',
  `courier_no` varchar(64) DEFAULT NULL,
  `cost_month` varchar(8) DEFAULT NULL COMMENT '�����·�',
  `cost_amount` decimal(10,2) DEFAULT NULL,
  `courier_tc_way` int(1) DEFAULT NULL COMMENT '��ɼ��㷽����1�����һ���޸�2��ÿ��ͳ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='���Ź��ʼ�¼��';

CREATE TABLE `judge_courier_label` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `courier_no` varchar(32) DEFAULT NULL,
  `label_id` bigint(20) DEFAULT NULL,
  `times` int(11) DEFAULT '0' COMMENT '���۴���',
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
  `take_or_send` int(11) DEFAULT '0' COMMENT '0ȡ��1�ɼ�',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='��������';

CREATE TABLE `judge_label` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `label_name` varchar(128) DEFAULT NULL COMMENT '��ǩ����',
  `status` int(1) DEFAULT '1' COMMENT '0ͣ��1����',
  `create_time` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='���۱�ǩ��';

CREATE TABLE `push_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_update_time` datetime NOT NULL,
  `user_no` varchar(32) DEFAULT NULL COMMENT '�������˺�',
  `is_send` int(1) NOT NULL DEFAULT '0' COMMENT '�Ƿ��Ѿ����͹���0û1���͹�',
  `content` text NOT NULL COMMENT '��Ϣ����',
  `title` varchar(128) NOT NULL COMMENT '��Ϣ����',
  `push_name` varchar(32) DEFAULT NULL COMMENT '����������',
  `is_red_title` int(1) DEFAULT '0' COMMENT '�����Ƿ�Ӻ�',
  `edit_name` varchar(32) DEFAULT NULL COMMENT '�޸���',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='������Ϣ�����Ա��';

CREATE TABLE `table_field_sort` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tab` varchar(32) DEFAULT NULL COMMENT 'table����',
  `tab_name` varchar(64) DEFAULT NULL COMMENT 'tab����',
  `col` varchar(32) DEFAULT NULL,
  `col_name` varchar(64) DEFAULT NULL,
  `sort` int(11) DEFAULT '0' COMMENT '�ֶ�����',
  `is_show` int(1) DEFAULT '1' COMMENT '�ֶ��Ƿ���ʾ0����ʾ1��ʾ',
  `user_no` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8;

insert into boss_auth(auth_code,auth_name,create_time,parent_id,category,`status`) 
values('WEIGHT_CONFIG','���ӳ�����',now(),(select id from boss_auth where auth_code='LGC_MANAGE'),'LINK',1);

insert into boss_auth(auth_code,auth_name,create_time,parent_id,category,`status`) 
values('CENTER_WAREHOUSE_REVSCAN','�������ɨ��',now(),(select id from boss_auth where auth_code='SCAN_MANAGE'),'LINK',1);

alter table order_info add center_warehouse_weight double;

alter table substation add sub_type int(1) DEFAULT 0 ;


insert into boss_auth (auth_code,auth_name,create_time,parent_id,category,`status`)
values('JUDGE_MANAGE','����',NOW(),0,'LINK',1);

insert into boss_auth (auth_code,auth_name,create_time,parent_id,category,`status`)
values('JUDGE_ALL','���Ա���ۻ���',NOW(),(select id from boss_auth where auth_code='JUDGE_MANAGE'),'LINK',1);

insert into boss_auth (auth_code,auth_name,create_time,parent_id,category,`status`)
values('JUDGE_LABEL','��ǩ����',NOW(),(select id from boss_auth where auth_code='JUDGE_MANAGE'),'LINK',1);

insert into boss_auth (auth_code,auth_name,create_time,parent_id,category,`status`)
values('JUDGE_DETAILLIST','ÿ����¼',NOW(),(select id from boss_auth where auth_code='JUDGE_MANAGE'),'LINK',1);

INSERT INTO `boss_auth` (auth_code,auth_name,create_time,parent_id,category,`status`) VALUES ( 'NOTIFY_LIST', '����֪ͨ', now(), (select id from boss_auth where auth_code='KEFU_MG'), 'LINK', '1');

insert into msg_type(msg_code,`describe`,jump_type,`level`,parent_code) values(499,'֪ͨ����','text',2,501);

INSERT INTO `table_field_sort` VALUES ('1', 'order_olist', '�˵�����', 'id', '���', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('2', 'order_olist', '�˵�����', 'lgc_order_no', '�˵���', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('3', 'order_olist', '�˵�����', 'item_Status', '��Ʒ����', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('4', 'order_olist', '�˵�����', 'take_courier_name', 'ȡ��Ա', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('5', 'order_olist', '�˵�����', 'take_courier_name', '�ļ�Ա', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('6', 'order_olist', '�˵�����', 'send_phone', '�ļ��绰', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('7', 'order_olist', '�˵�����', 'sendaddr', '�ļ���ַ', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('8', 'order_olist', '�˵�����', 'rev_name', '�ռ���', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('9', 'order_olist', '�˵�����', 'rev_phone', '�ռ��绰', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('10', 'order_olist', '�˵�����', 'revaddr', '�ռ���ַ', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('11', 'order_olist', '�˵�����', 'source', '��Դ', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('12', 'order_olist', '�˵�����', 'status', '�˵�״̬', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('13', 'order_olist', '�˵�����', 'asign_status', '����״̬', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('14', 'order_olist', '�˵�����', 'asign_name', '������', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('15', 'order_olist', '�˵�����', 'create_time', '�µ�ʱ��', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('16', 'order_olist', '�˵�����', 'edit', '����', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('17', 'order_olist', '�˵�����', 'order_no', '�������', '16', '0', '0');
INSERT INTO `table_field_sort` VALUES ('18', 'order_olist', '�˵�����', 'cm_order_no', '�ⲿ����', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('19', 'order_olist', '�˵�����', 'item_name', '��Ʒ����', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('20', 'order_olist', '�˵�����', 'item_count', '����', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('21', 'order_olist', '�˵�����', 'asign_status', '����״̬', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('22', 'order_olist', '�˵�����', 'item_Status', '��Ʒ����', '21', '0', '0');
INSERT INTO `table_field_sort` VALUES ('23', 'order_olist', '�˵�����', 'time_type', '��ƷʱЧ', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('24', 'order_olist', '�˵�����', 'freight_type', '������', '23', '0', '0');
INSERT INTO `table_field_sort` VALUES ('25', 'order_olist', '�˵�����', 'pay_type', '���ʽ', '24', '0', '0');
INSERT INTO `table_field_sort` VALUES ('26', 'order_olist', '�˵�����', 'good_valuation', '���۷�', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('27', 'order_olist', '�˵�����', 'dis_user_no', '��Ա��', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('28', 'order_olist', '�˵�����', 'cod_name', '�����', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('29', 'order_olist', '�˵�����', 'cod_sname', '��˾���', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('30', 'order_olist', '�˵�����', 'send_name', '�ļ���', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('31', 'order_olist', '�˵�����', 'send_phone', '�ļ��˵绰', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('32', 'order_olist', '�˵�����', 'create_time', '�ļ�ʱ��', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('33', 'order_olist', '�˵�����', 'send_addr', '�ļ���ַ', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('34', 'order_olist', '�˵�����', 'inputer', '�ļ�¼����', '34', '1', '0');
INSERT INTO `table_field_sort` VALUES ('35', 'order_olist', '�˵�����', 'send_order_time', 'ǩ��ʱ��', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('36', 'order_olist', '�˵�����', 'sign_inputer', 'ǩ��¼����', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('37', 'order_olist', '�˵�����', 'remark', '��ע', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('38', 'order_relist', 'ǩ�յ����', 'id', '���', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('39', 'order_relist', 'ǩ�յ����', 'lgc_order_no', '�˵���', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('40', 'order_relist', 'ǩ�յ����', 'monthSname', '��˾���', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('41', 'order_relist', 'ǩ�յ����', 'month_settle_no', '�½��˺�', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('42', 'order_relist', 'ǩ�յ����', 'item_weight', '����', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('43', 'order_relist', 'ǩ�յ����', 'freight', '�˷�', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('44', 'order_relist', 'ǩ�յ����', 'vpay', '���ӷ�', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('45', 'order_relist', 'ǩ�յ����', 'good_price', '���տ�', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('46', 'order_relist', 'ǩ�յ����', 'freight_type', '������', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('47', 'order_relist', 'ǩ�յ����', 'pay_type', '���ʽ', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('48', 'order_relist', 'ǩ�յ����', 'sendSubstationName', '�ɼ�����', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('49', 'order_relist', 'ǩ�յ����', 'send_courier_name', '�ɼ�Ա', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('50', 'order_relist', 'ǩ�յ����', 'send_order_time', 'ǩ��ʱ��', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('51', 'order_relist', 'ǩ�յ����', 'rece_no', '�ص���', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('52', 'order_relist', 'ǩ�յ����', 'for_no', 'ת����', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('53', 'order_relist', 'ǩ�յ����', 'edit', '����', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('54', 'order_relist', 'ǩ�յ����', 'order_no', '�������', '16', '0', '0');
INSERT INTO `table_field_sort` VALUES ('55', 'order_relist', 'ǩ�յ����', 'cm_order_no', '�ⲿ����', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('56', 'order_relist', 'ǩ�յ����', 'item_name', '��Ʒ����', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('57', 'order_relist', 'ǩ�յ����', 'item_count', '����', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('58', 'order_relist', 'ǩ�յ����', 'asign_status', '����״̬', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('59', 'order_relist', 'ǩ�յ����', 'item_Status', '��Ʒ����', '21', '0', '0');
INSERT INTO `table_field_sort` VALUES ('60', 'order_relist', 'ǩ�յ����', 'time_type', '��ƷʱЧ', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('61', 'order_relist', 'ǩ�յ����', 'good_valuation', '���۷�', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('62', 'order_relist', 'ǩ�յ����', 'dis_user_no', '��Ա��', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('63', 'order_relist', 'ǩ�յ����', 'cod_name', '�����', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('64', 'order_relist', 'ǩ�յ����', 'cod_sname', '��˾���', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('65', 'order_relist', 'ǩ�յ����', 'send_name', '�ļ���', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('66', 'order_relist', 'ǩ�յ����', 'send_phone', '�ļ��˵绰', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('67', 'order_relist', 'ǩ�յ����', 'create_time', '�ļ�ʱ��', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('68', 'order_relist', 'ǩ�յ����', 'send_addr', '�ļ���ַ', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('69', 'order_relist', 'ǩ�յ����', 'rev_name', '�ռ���', '33', '0', '0');
INSERT INTO `table_field_sort` VALUES ('70', 'order_relist', 'ǩ�յ����', 'rev_phone', '�ռ��˵绰', '34', '0', '0');
INSERT INTO `table_field_sort` VALUES ('71', 'order_relist', 'ǩ�յ����', 'inputer', '�ļ�¼����', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('72', 'order_relist', 'ǩ�յ����', 'send_order_time', 'ǩ��ʱ��', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('73', 'order_relist', 'ǩ�յ����', 'sign_inputer', 'ǩ��¼����', '37', '0', '0');
INSERT INTO `table_field_sort` VALUES ('74', 'order_relist', 'ǩ�յ����', 'rev_addr', '�ռ���ַ', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('75', 'order_relist', 'ǩ�յ����', 'remark', '��ע', '39', '0', '0');
INSERT INTO `table_field_sort` VALUES ('76', 'order_revlist', 'ǩ�յ���ѯ', 'id', '���', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('77', 'order_revlist', 'ǩ�յ���ѯ', 'lgc_order_no', '�˵���', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('78', 'order_revlist', 'ǩ�յ���ѯ', 'monthSname', '��˾���', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('79', 'order_revlist', 'ǩ�յ���ѯ', 'month_settle_no', '�½��˺�', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('80', 'order_revlist', 'ǩ�յ���ѯ', 'item_weight', '����', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('81', 'order_revlist', 'ǩ�յ���ѯ', 'freight', '�˷�', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('82', 'order_revlist', 'ǩ�յ���ѯ', 'vpay', '���ӷ�', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('83', 'order_revlist', 'ǩ�յ���ѯ', 'good_price', '���տ�', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('84', 'order_revlist', 'ǩ�յ���ѯ', 'freight_type', '������', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('85', 'order_revlist', 'ǩ�յ���ѯ', 'pay_type', '���ʽ', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('86', 'order_revlist', 'ǩ�յ���ѯ', 'send_substation_no', '�ɼ�����', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('87', 'order_revlist', 'ǩ�յ���ѯ', 'send_courier_name', '�ɼ�Ա', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('88', 'order_revlist', 'ǩ�յ���ѯ', 'send_order_time', 'ǩ��ʱ��', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('89', 'order_revlist', 'ǩ�յ���ѯ', 'rece_no', '�ص���', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('90', 'order_revlist', 'ǩ�յ���ѯ', 'for_no', 'ת����', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('91', 'order_revlist', 'ǩ�յ���ѯ', 'sign_examine_status', '���', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('92', 'order_revlist', 'ǩ�յ���ѯ', 'edit', '����', '16', '1', '0');
INSERT INTO `table_field_sort` VALUES ('93', 'order_revlist', 'ǩ�յ���ѯ', 'order_no', '�������', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('94', 'order_revlist', 'ǩ�յ���ѯ', 'cm_order_no', '�ⲿ����', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('95', 'order_revlist', 'ǩ�յ���ѯ', 'item_name', '��Ʒ����', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('96', 'order_revlist', 'ǩ�յ���ѯ', 'item_count', '����', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('97', 'order_revlist', 'ǩ�յ���ѯ', 'asign_status', '����״̬', '21', '0', '0');
INSERT INTO `table_field_sort` VALUES ('98', 'order_revlist', 'ǩ�յ���ѯ', 'item_Status', '��Ʒ����', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('99', 'order_revlist', 'ǩ�յ���ѯ', 'time_type', '��ƷʱЧ', '23', '0', '0');
INSERT INTO `table_field_sort` VALUES ('100', 'order_revlist', 'ǩ�յ���ѯ', 'freight_type', '������', '24', '0', '0');
INSERT INTO `table_field_sort` VALUES ('101', 'order_revlist', 'ǩ�յ���ѯ', 'pay_type', '���ʽ', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('102', 'order_revlist', 'ǩ�յ���ѯ', 'good_valuation', '���۷�', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('103', 'order_revlist', 'ǩ�յ���ѯ', 'dis_user_no', '��Ա��', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('104', 'order_revlist', 'ǩ�յ���ѯ', 'cod_name', '�����', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('105', 'order_revlist', 'ǩ�յ���ѯ', 'cod_sname', '��˾���', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('106', 'order_revlist', 'ǩ�յ���ѯ', 'send_name', '�ļ���', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('107', 'order_revlist', 'ǩ�յ���ѯ', 'send_phone', '�ļ��˵绰', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('108', 'order_revlist', 'ǩ�յ���ѯ', 'create_time', '�ļ�ʱ��', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('109', 'order_revlist', 'ǩ�յ���ѯ', 'send_addr', '�ļ���ַ', '33', '0', '0');
INSERT INTO `table_field_sort` VALUES ('110', 'order_revlist', 'ǩ�յ���ѯ', 'rev_name', '�ռ���', '34', '0', '0');
INSERT INTO `table_field_sort` VALUES ('111', 'order_revlist', 'ǩ�յ���ѯ', 'rev_phone', '�ռ��˵绰', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('112', 'order_revlist', 'ǩ�յ���ѯ', 'inputer', '�ļ�¼����', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('113', 'order_revlist', 'ǩ�յ���ѯ', 'send_order_time', 'ǩ��ʱ��', '37', '0', '0');
INSERT INTO `table_field_sort` VALUES ('114', 'order_revlist', 'ǩ�յ���ѯ', 'sign_inputer', 'ǩ��¼����', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('115', 'order_revlist', 'ǩ�յ���ѯ', 'rev_addr', '�ռ���ַ', '39', '0', '0');
INSERT INTO `table_field_sort` VALUES ('116', 'order_revlist', 'ǩ�յ���ѯ', 'remark', '��ע', '40', '0', '0');
INSERT INTO `table_field_sort` VALUES ('117', 'order_selist', '�ļ�����˱�', 'id', '���', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('118', 'order_selist', '�ļ�����˱�', 'lgc_order_no', '�˵���', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('119', 'order_selist', '�ļ�����˱�', 'monthSname', '��˾���	', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('120', 'order_selist', '�ļ�����˱�', 'month_settle_no', '�½��˺�', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('121', 'order_selist', '�ļ�����˱�', 'item_weight', '����', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('122', 'order_selist', '�ļ�����˱�', 'freight', '�˷�', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('123', 'order_selist', '�ļ�����˱�', 'vpay', '���ӷ�', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('124', 'order_selist', '�ļ�����˱�', 'good_price', '���տ�', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('125', 'order_selist', '�ļ�����˱�', 'freight_type', '������', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('126', 'order_selist', '�ļ�����˱�', 'pay_type', '���ʽ', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('127', 'order_selist', '�ļ�����˱�', 'takeSubstationName', '�ļ�����', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('128', 'order_selist', '�ļ�����˱�', 'sendSubstationName', '�ɼ�����', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('129', 'order_selist', '�ļ�����˱�', 'take_courier_name', 'ȡ��Ա', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('130', 'order_selist', '�ļ�����˱�', 'rece_no', '�ص���', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('131', 'order_selist', '�ļ�����˱�', 'for_no', 'ת����', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('132', 'order_selist', '�ļ�����˱�', 'status', '�˵�״̬', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('133', 'order_selist', '�ļ�����˱�', 'edit', '����', '16', '1', '0');
INSERT INTO `table_field_sort` VALUES ('134', 'order_selist', '�ļ�����˱�', 'order_no', '�������', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('135', 'order_selist', '�ļ�����˱�', 'cm_order_no', '�ⲿ����', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('136', 'order_selist', '�ļ�����˱�', 'item_name', '��Ʒ����', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('137', 'order_selist', '�ļ�����˱�', 'item_count', '����', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('138', 'order_selist', '�ļ�����˱�', 'asign_status', '����״̬', '21', '0', '0');
INSERT INTO `table_field_sort` VALUES ('139', 'order_selist', '�ļ�����˱�', 'item_Status', '��Ʒ����', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('140', 'order_selist', '�ļ�����˱�', 'time_type', '��ƷʱЧ', '23', '0', '0');
INSERT INTO `table_field_sort` VALUES ('141', 'order_selist', '�ļ�����˱�', 'freight_type', '������', '24', '0', '0');
INSERT INTO `table_field_sort` VALUES ('142', 'order_selist', '�ļ�����˱�', 'pay_type', '���ʽ', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('143', 'order_selist', '�ļ�����˱�', 'good_valuation', '���۷�', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('144', 'order_selist', '�ļ�����˱�', 'dis_user_no', '��Ա��', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('145', 'order_selist', '�ļ�����˱�', 'cod_name', '�����', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('146', 'order_selist', '�ļ�����˱�', 'cod_sname', '��˾���', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('147', 'order_selist', '�ļ�����˱�', 'send_name', '�ļ���', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('148', 'order_selist', '�ļ�����˱�', 'send_phone', '�ļ��˵绰', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('149', 'order_selist', '�ļ�����˱�', 'create_time', '�ļ�ʱ��', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('150', 'order_selist', '�ļ�����˱�', 'send_addr', '�ļ���ַ', '33', '0', '0');
INSERT INTO `table_field_sort` VALUES ('151', 'order_selist', '�ļ�����˱�', 'rev_name', '�ռ���', '34', '0', '0');
INSERT INTO `table_field_sort` VALUES ('152', 'order_selist', '�ļ�����˱�', 'rev_phone', '�ռ��˵绰', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('153', 'order_selist', '�ļ�����˱�', 'inputer', '�ļ�¼����', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('154', 'order_selist', '�ļ�����˱�', 'send_order_time', 'ǩ��ʱ��', '37', '0', '0');
INSERT INTO `table_field_sort` VALUES ('155', 'order_selist', '�ļ�����˱�', 'sign_inputer', 'ǩ��¼����', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('156', 'order_selist', '�ļ�����˱�', 'rev_addr', '�ռ���ַ', '39', '0', '0');
INSERT INTO `table_field_sort` VALUES ('157', 'order_selist', '�ļ�����˱�', 'remark', '��ע', '40', '0', '0');
INSERT INTO `table_field_sort` VALUES ('158', 'order_selist', '�ļ�����˱�', 'examine_status', '���״̬', '41', '0', '0');
INSERT INTO `table_field_sort` VALUES ('159', 'order_sendlist', '�ļ�����ѯ�б�', 'id', '���', '0', '1', '0');
INSERT INTO `table_field_sort` VALUES ('160', 'order_sendlist', '�ļ�����ѯ�б�', 'lgc_order_no', '�˵���', '1', '1', '0');
INSERT INTO `table_field_sort` VALUES ('161', 'order_sendlist', '�ļ�����ѯ�б�', 'monthSname', '��˾���', '2', '1', '0');
INSERT INTO `table_field_sort` VALUES ('162', 'order_sendlist', '�ļ�����ѯ�б�', 'month_settle_no', '�½��˺�', '3', '1', '0');
INSERT INTO `table_field_sort` VALUES ('163', 'order_sendlist', '�ļ�����ѯ�б�', 'item_weight', '����', '4', '1', '0');
INSERT INTO `table_field_sort` VALUES ('164', 'order_sendlist', '�ļ�����ѯ�б�', 'freight', '�˷�', '5', '1', '0');
INSERT INTO `table_field_sort` VALUES ('165', 'order_sendlist', '�ļ�����ѯ�б�', 'vpay', '���ӷ�', '6', '1', '0');
INSERT INTO `table_field_sort` VALUES ('166', 'order_sendlist', '�ļ�����ѯ�б�', 'good_price', '���տ�', '7', '1', '0');
INSERT INTO `table_field_sort` VALUES ('167', 'order_sendlist', '�ļ�����ѯ�б�', 'sub_station_no', '�ļ�����', '8', '1', '0');
INSERT INTO `table_field_sort` VALUES ('168', 'order_sendlist', '�ļ�����ѯ�б�', 'send_substation_no', '�ɼ�����', '9', '1', '0');
INSERT INTO `table_field_sort` VALUES ('169', 'order_sendlist', '�ļ�����ѯ�б�', 'take_courier_name', 'ȡ��Ա', '10', '1', '0');
INSERT INTO `table_field_sort` VALUES ('170', 'order_sendlist', '�ļ�����ѯ�б�', 'send_courier_no', '�ɼ�Ա', '11', '1', '0');
INSERT INTO `table_field_sort` VALUES ('171', 'order_sendlist', '�ļ�����ѯ�б�', 'rece_no', '�ص���', '12', '1', '0');
INSERT INTO `table_field_sort` VALUES ('172', 'order_sendlist', '�ļ�����ѯ�б�', 'for_no', 'ת����', '13', '1', '0');
INSERT INTO `table_field_sort` VALUES ('173', 'order_sendlist', '�ļ�����ѯ�б�', 'source', '��Դ', '14', '1', '0');
INSERT INTO `table_field_sort` VALUES ('174', 'order_sendlist', '�ļ�����ѯ�б�', 'status', '�˵�״̬', '15', '1', '0');
INSERT INTO `table_field_sort` VALUES ('175', 'order_sendlist', '�ļ�����ѯ�б�', 'edit', '����', '16', '1', '0');
INSERT INTO `table_field_sort` VALUES ('176', 'order_sendlist', '�ļ�����ѯ�б�', 'order_no', '�������', '17', '0', '0');
INSERT INTO `table_field_sort` VALUES ('177', 'order_sendlist', '�ļ�����ѯ�б�', 'cm_order_no', '�ⲿ����', '18', '0', '0');
INSERT INTO `table_field_sort` VALUES ('178', 'order_sendlist', '�ļ�����ѯ�б�', 'item_name', '��Ʒ����', '19', '0', '0');
INSERT INTO `table_field_sort` VALUES ('179', 'order_sendlist', '�ļ�����ѯ�б�', 'item_count', '����', '20', '0', '0');
INSERT INTO `table_field_sort` VALUES ('180', 'order_sendlist', '�ļ�����ѯ�б�', 'asign_status', '����״̬', '22', '0', '0');
INSERT INTO `table_field_sort` VALUES ('181', 'order_sendlist', '�ļ�����ѯ�б�', 'item_Status', '��Ʒ����', '23', '0', '0');
INSERT INTO `table_field_sort` VALUES ('182', 'order_sendlist', '�ļ�����ѯ�б�', 'time_type', '��ƷʱЧ', '24', '0', '0');
INSERT INTO `table_field_sort` VALUES ('183', 'order_sendlist', '�ļ�����ѯ�б�', 'freight_type', '������', '25', '0', '0');
INSERT INTO `table_field_sort` VALUES ('184', 'order_sendlist', '�ļ�����ѯ�б�', 'pay_type', '���ʽ', '26', '0', '0');
INSERT INTO `table_field_sort` VALUES ('185', 'order_sendlist', '�ļ�����ѯ�б�', 'good_valuation', '���۷�', '27', '0', '0');
INSERT INTO `table_field_sort` VALUES ('186', 'order_sendlist', '�ļ�����ѯ�б�', 'dis_user_no', '��Ա��', '28', '0', '0');
INSERT INTO `table_field_sort` VALUES ('187', 'order_sendlist', '�ļ�����ѯ�б�', 'cod_name', '�����', '29', '0', '0');
INSERT INTO `table_field_sort` VALUES ('188', 'order_sendlist', '�ļ�����ѯ�б�', 'cod_sname', '��˾���', '30', '0', '0');
INSERT INTO `table_field_sort` VALUES ('189', 'order_sendlist', '�ļ�����ѯ�б�', 'send_name', '�ļ���', '31', '0', '0');
INSERT INTO `table_field_sort` VALUES ('190', 'order_sendlist', '�ļ�����ѯ�б�', 'send_phone', '�ļ��˵绰', '32', '0', '0');
INSERT INTO `table_field_sort` VALUES ('191', 'order_sendlist', '�ļ�����ѯ�б�', 'create_time', '�ļ�ʱ��', '33', '0', '0');
INSERT INTO `table_field_sort` VALUES ('192', 'order_sendlist', '�ļ�����ѯ�б�', 'send_addr', '�ļ���ַ', '34', '0', '0');
INSERT INTO `table_field_sort` VALUES ('193', 'order_sendlist', '�ļ�����ѯ�б�', 'rev_name', '�ռ���', '35', '0', '0');
INSERT INTO `table_field_sort` VALUES ('194', 'order_sendlist', '�ļ�����ѯ�б�', 'rev_phone', '�ռ��˵绰', '36', '0', '0');
INSERT INTO `table_field_sort` VALUES ('195', 'order_sendlist', '�ļ�����ѯ�б�', 'inputer', '�ļ�¼����', '37', '0', '0');
INSERT INTO `table_field_sort` VALUES ('196', 'order_sendlist', '�ļ�����ѯ�б�', 'send_order_time', 'ǩ��ʱ��', '38', '0', '0');
INSERT INTO `table_field_sort` VALUES ('197', 'order_sendlist', '�ļ�����ѯ�б�', 'sign_inputer', 'ǩ��¼����', '39', '0', '0');
INSERT INTO `table_field_sort` VALUES ('198', 'order_sendlist', '�ļ�����ѯ�б�', 'rev_addr', '�ռ���ַ', '40', '0', '0');
INSERT INTO `table_field_sort` VALUES ('199', 'order_sendlist', '�ļ�����ѯ�б�', 'remark', '��ע', '41', '0', '0');