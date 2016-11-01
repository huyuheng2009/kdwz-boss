CREATE TABLE `asign_order` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`order_no` VARCHAR(50) NOT NULL COMMENT '������',
	`order_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�µ�ʱ��',
	`asign_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
	`asing_date` DATE NOT NULL,
	`asign_no` VARCHAR(100) NULL DEFAULT NULL,
	`asign_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '������',
	`asign_duration` DECIMAL(10,0) NULL DEFAULT NULL COMMENT '����ʱ��',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `order_no_` (`order_no`),
	INDEX `order_no` (`order_no`),
	INDEX `asing_date` (`asing_date`),
	INDEX `asign_time` (`asign_time`),
	INDEX `order_time` (`order_time`),
	INDEX `asign_no` (`asign_no`)
)
COMMENT='�����'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;


ALTER TABLE `asign_order`
	ADD COLUMN `order_date` DATE NOT NULL AFTER `asing_date`,
	ADD INDEX `order_date` (`order_date`);


CREATE TABLE `lgc_config` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`config_type` VARCHAR(50) NULL DEFAULT NULL,
	`config_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '������',
	`config_value` VARCHAR(200) NULL DEFAULT NULL COMMENT '����ֵ',
	`note` VARCHAR(200) NULL DEFAULT NULL COMMENT '��ע',
	PRIMARY KEY (`id`)
)
COMMENT='��˾������'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=7;


INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (1, 'LGC_ORDER_NO', 'MIN_LENGTH', '8', '�˵�����С����');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (2, 'LGC_ORDER_NO', 'MAX_LENGTH', '20', '�˵�����󳤶�');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (3, 'LGC_ORDER_NO', 'CONSTATUTE', 'NO_OR_EN', '�˵��Ź���');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (4, 'MONTH_NO', 'NO_FIX', '', '�½��ǰ׺');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (5, 'MONTH_NO', 'NO_LENGTH', '5', '�½�ų���');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (6, 'MONTH_NO', 'LX_LENGTH', '10', 'app����λ��');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (7, 'WAGES_BASE', 'FREIGHT', 'NONE', '���');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (8, 'WAGES_BASE', 'WEIGHT', 'NONE', '����');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (9, 'WAGES_BASE', 'COUNT', 'NONE', '����');
INSERT INTO `lgc_config` (`id`, `config_type`, `config_name`, `config_value`, `note`) VALUES (10, 'WAGES_BASE', 'COURIER_STATUS', '1', '������Ա,1��ʾ�������Ѿ�ͣ�õĿ��Ա��0');




INSERT INTO `require_type` (`id`, `code`, `describe`) VALUES (2, 'ORDER_INPUT', '�ļ���¼�������');
INSERT INTO `require_type` (`id`, `code`, `describe`) VALUES (3, 'MONTH_NO', '�½������');
INSERT INTO `require_type` (`id`, `code`, `describe`) VALUES (4, 'LGC_ORDER_NO', '�˵�������');
INSERT INTO `require_type` (`id`, `code`, `describe`) VALUES (5, 'LGC_MODEL', '����ģʽ');

INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (18, 2, 'message', '�Ƿ����', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (19, 2, 'sendPhone', '�ļ��绰', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (20, 2, 'itemStatus', '��Ʒ����', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (21, 2, 'takeCourier', 'ȡ��Ա', 'Y', 'N', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (22, 2, 'sendName', '�ļ���', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (23, 2, 'timeType', 'ʱЧ����', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (24, 2, 'itemWeight', '����', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (25, 2, 'sendKehu', '�ļ���˾', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (26, 2, 'itemName', '��Ʒ����', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (27, 2, 'itemCount', '����', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (28, 2, 'sendAddr', '�ļ���ַ', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (29, 2, 'freight', '����˷�', 'Y', 'N', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (30, 2, 'revPhone', '�ռ��绰', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (31, 2, 'freightType', '������', 'Y', 'N', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (32, 2, 'revAddr', '�ռ���ַ', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (33, 2, 'lgcOrderNo', '�˵���', 'Y', 'N', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (34, 2, 'revName', '�ռ���', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (35, 1, 'itemCount', '����', 'N', 'Y', 'CHECKBOX');
INSERT INTO `require_list` (`id`, `tid`, `name`, `describe`, `required`, `editable`, `view_type`) VALUES (36, 1, 'timeType', 'ʱЧ����', 'N', 'Y', 'CHECKBOX');




ALTER TABLE `month_settle_user`
	ADD COLUMN `no_pre` VARCHAR(50) NULL DEFAULT NULL COMMENT 'ǰ׺' AFTER `status`,
	ADD COLUMN `no_fix` VARCHAR(50) NULL DEFAULT NULL AFTER `no_pre`;


update month_settle_user set no_fix=month_settle_no;


UPDATE  `table_field_sort` SET `col`='send_courier_name', `col_name`='�ɼ�Ա' WHERE  `id`=5;


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
COMMENT='��˾�ͻ�'
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
COMMENT='�ͻ��طü�¼'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;


ALTER TABLE `discount_type`
	ADD COLUMN `min_val` DECIMAL(10,0) NULL DEFAULT NULL AFTER `discount`,
	ADD COLUMN `max_val` DECIMAL(10,0) NULL DEFAULT NULL AFTER `min_val`;

update discount_type set min_val=0,max_val=999;

ALTER TABLE `recharge_history`
	ADD COLUMN `pay_money` DECIMAL(10,2) NULL DEFAULT NULL COMMENT 'ʵ�����' AFTER `rmoney`;


CREATE TABLE `dis_user_note` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`uid` INT(11) NULL DEFAULT NULL,
	`note` VARCHAR(255) NULL DEFAULT NULL,
	`create_time` TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COMMENT='��Ա��ע��'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;



CREATE TABLE `weixin_vip_session` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '��Ա��',
	`oper_id` VARCHAR(100) NULL DEFAULT NULL COMMENT '΢��open_id(session_no)',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
	`last_update_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����޸�ʱ��',
	`expiry_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'ʧЧʱ��',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `oper_id` (`oper_id`)
)
COMMENT='΢�Ż�Ա��¼״̬��Ϣ'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;



CREATE TABLE `system_punish` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`punish_text` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Υ������',
	`rule_text` VARCHAR(255) NULL DEFAULT NULL COMMENT '������׼',
	PRIMARY KEY (`id`)
)
COMMENT='����������'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;


ALTER TABLE `lgc_area`
	CHANGE COLUMN `baddr` `baddr` TEXT NULL DEFAULT NULL COMMENT '���ɷ�Χ������Զ��ŷָ�' AFTER `addr_area`,
	CHANGE COLUMN `naddr` `naddr` TEXT NULL DEFAULT NULL COMMENT '�����ɷ�Χ������Զ��ŷָ�' AFTER `baddr`;


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�ֹ�Ա��';


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
	`group_name` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Ⱥ������',
	`group_desc` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Ⱥ��˵��',
	`create_time` TIMESTAMP NULL DEFAULT NULL,
	`clogin` VARCHAR(50) NULL DEFAULT NULL COMMENT '�ܷ��½�ֹ�Աapp��N/Y',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=MyISAM
AUTO_INCREMENT=14;


CREATE TABLE `boss_auth_group` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`auth_id` INT(11) NULL DEFAULT NULL COMMENT 'Ȩ��ID',
	`group_id` INT(11) NULL DEFAULT NULL COMMENT 'Ⱥ��ID',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1;



DROP TABLE IF EXISTS `boss_auth`;
CREATE TABLE IF NOT EXISTS `boss_auth` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `auth_code` varchar(255) DEFAULT NULL COMMENT 'Ȩ�޴���',
  `auth_name` varchar(25) DEFAULT NULL COMMENT 'Ȩ������',
  `remark` varchar(255) DEFAULT NULL COMMENT '��ע',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '����ʱ��',
  `parent_id` int(11) DEFAULT NULL COMMENT '���ڵ�',
  `category` varchar(255) DEFAULT NULL COMMENT 'Ȩ�����LINK BUTTON',
  `status` int(11) DEFAULT '0' COMMENT '����״̬',
  `sub_permit` varchar(255) DEFAULT NULL COMMENT 'Ȩ���Ƿ��������Ȩ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;


INSERT INTO `boss_auth` (`id`, `auth_code`, `auth_name`, `remark`, `create_time`, `parent_id`, `category`, `status`, `sub_permit`) VALUES
	(1, 'SUDO', '����Ȩ��', NULL, '2016-08-16 09:54:21', 0, 'LINK', 0, NULL),
	(2, 'SYSTEM_MANAGE', 'ϵͳ', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(3, 'LGC_MANAGE', '��˾', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(4, 'MUSER_MANAGE', '�ͻ�', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(5, 'DISUSER_MANAGE', '��Ա', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(6, 'SEND_ORDER_MANAGE', '�ļ��˵�', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(7, 'SIGN_ORDER_MANAGE', 'ǩ���˵�', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(8, 'KEFU', '�ͷ�', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(9, 'SCAN_MANAGE', 'ɨ��', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(10, 'GATHER_MANAGE', '�տ�', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(11, 'SUBSTATIC_MANAGE', '����', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(12, 'MONITOR', '���', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(13, 'WAGES_MANAGER', '����', NULL, '2016-08-16 09:54:21', 0, 'LINK', 1, NULL),
	(14, 'JIAMENG', '����', NULL, '2016-08-16 09:54:22', 0, 'LINK', 1, NULL),
	(15, 'JUDGE_MANAGE', '����', NULL, '2016-08-16 09:54:22', 0, 'LINK', 1, NULL),
	(16, 'ZL_MAP', 'ս�Ե�ͼ', NULL, '2016-08-16 09:54:22', 0, 'LINK', 1, NULL),
	(17, 'HELP', '����', NULL, '2016-08-16 09:54:22', 0, 'LINK', 1, NULL),
	(18, 'SYSTEM_USER', 'ϵͳ�û�', NULL, '2016-08-16 09:58:39', 2, 'LINK', 1, NULL),
	(19, 'OPERATE_LOG', '������־', NULL, '2016-08-16 09:58:39', 2, 'LINK', 1, '124'),
	(20, 'LGC_SUBSTATION', '��ݷ�վ', NULL, '2016-08-16 10:02:19', 3, 'LINK', 1, '124'),
	(21, 'LGC_COURIER', '���Ա', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, NULL),
	(22, 'WAREHOUSE_STAFF', '�ֹ�Ա', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(23, 'LGC_CRULE', '���տ����', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(24, 'LGC_VRULE', '���۹���', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(25, 'ITEM_TYPE', '��Ʒ��������', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(26, 'TIME_TYPE', 'ʱЧ��������', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(27, 'FOR_CPN', 'ת����˾����', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(28, 'LGC_RTYPE', '��������', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(29, 'WEIGHT_CONFIG', '���ӳ�����', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(30, 'MESSAGE_SEND', '���ŷ��Ͳ�ѯ', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(31, 'MESSAGE_CZ', '���ų�ֵ��ѯ', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(32, 'LGC_FRULE', '�˷ѱ���ά��', NULL, '2016-08-16 10:02:20', 3, 'LINK', 1, '124'),
	(33, 'MOBILE_USER', '�ֻ��û�', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(34, 'MUSER_LIST', '�½��û�', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, NULL),
	(35, 'CUSER_LIST', '���ջ����û�', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, NULL),
	(36, 'MOBILE_MSTYPE', '�½�ͻ��Żݹ���', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(37, 'MOBILE_CSTYPE', '���տͻ��Żݹ���', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(38, 'DISUSER_ADD', '��Ա����', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(39, 'DISUSER_URECHARGE', '��Ա��ֵ', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(40, 'DISUSER_LIST', '��Ա�б�', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(41, 'DISUSER_TLIST', '�Żݹ���', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, '124'),
	(42, 'DISUSER_RLIST', '��ֵ��ˮ', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(43, 'DISUSER_CLIST', '�ۿ���ˮ', NULL, '2016-08-16 10:26:13', 5, 'LINK', 1, NULL),
	(44, 'ORDER_INPUT', '�ļ���¼��', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(45, 'SEND_BANTCH_ORDER', '�ļ�����������', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(46, 'SEND_ORDER_LIST', '�ļ�����ѯ', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, NULL),
	(47, 'SEND_ORDER_EXAM', '�ļ������', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(48, 'ORDER_EDIT', '�˵��޸�', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, NULL),
	(49, 'ORDER_EXAM_HLIST', '�˵��޸���ˮ', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, NULL),
	(50, 'BATCH_XD', '�����µ�', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(51, 'UPLOAD_ORDER_PLANE', '�浥�ϴ�', NULL, '2016-08-16 10:08:35', 6, 'LINK', 1, '124'),
	(52, 'ORDER_SINPUT', 'ǩ�յ�¼��', NULL, '2016-08-16 10:29:09', 7, 'LINK', 1, '124'),
	(53, 'REV_BANTCH_ORDER', 'ǩ�յ���������', NULL, '2016-08-16 10:29:09', 7, 'LINK', 1, '124'),
	(54, 'REV_ORDER_LIST', 'ǩ�յ���ѯ', NULL, '2016-08-16 10:29:09', 7, 'LINK', 1, NULL),
	(55, 'REV_ORDER_EXAM', 'ǩ�յ����', NULL, '2016-08-16 10:29:09', 7, 'LINK', 1, '124'),
	(56, 'ORDER_ADD', '�����µ�', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(57, 'ORDER_ASIGN', '�˵�����', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124,125'),
	(58, 'ORDER_LIST', '�˵��б�', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, NULL),
	(59, 'ORDER_TRACKS_LIST', '�˵���ѯ', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, NULL),
	(60, 'LGC_COURIER_AREA', '���Ա���ɷ�Χ', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(61, 'LGC_AREAS', '��˾���ɷ�Χ', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(62, 'PRO_LIST', '�������ѯ', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, NULL),
	(63, 'PRO_RESON', '�����ԭ������', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(64, 'NOTIFY_LIST', '����֪ͨ', NULL, '2016-08-16 10:51:28', 8, 'LINK', 1, '124'),
	(65, 'TAKE_SCAN', '�ռ�ɨ��', NULL, '2016-08-16 10:55:01', 9, 'LINK', 1, '124'),
	(66, 'SEND_SCAN', '�ɼ�ɨ��', NULL, '2016-08-16 10:55:01', 9, 'LINK', 1, '124'),
	(67, 'SIGN_SCAN', 'ǩ��ɨ��', NULL, '2016-08-16 10:55:01', 9, 'LINK', 1, '124'),
	(68, 'SCAN_SREV', '�������ɨ��', NULL, '2016-08-16 10:55:01', 9, 'LINK', 1, '124'),
	(69, 'SCAN_SSEND', '�������ɨ��', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, '124'),
	(70, 'SCAN_FROSCAN', 'ת��ɨ��', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, '124'),
	(71, 'CENTER_WAREHOUSE_REVSCAN', '�������ɨ��', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, '124'),
	(72, 'SCAN_EXCHANGE', 'ת���嵥', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, NULL),
	(73, 'SCAN_PROSCAN', '�����ɨ��', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, '124'),
	(74, 'SCAN_REVPRINT', '�ռ���ϸ��ӡ', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, NULL),
	(75, 'SCAN_SNEXTPRINT', '��վ���ӵ���ӡ', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, NULL),
	(76, 'COURIER_SETTLE_CREATE', '���Ա�˵�����', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(77, 'COURIER_SETTLE_LIST', '���Ա�տ�Ǽ�', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(78, 'COURIER_SETTLE_EXAM', '���Ա�տ����', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(79, 'MONTH_SETTLE_CREATE', '�½�ͻ��˵�����', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(80, 'MONTH_SETTLE_LIST', '�½��տ�Ǽ�', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(81, 'MONTH_SETTLE_EXAM', '�½��տ����', NULL, '2016-08-16 10:57:42', 10, 'LINK', 1, NULL),
	(82, 'SUBSTATIC_DAY_LGC', '��˾ÿ�ձ���', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, '124'),
	(83, 'SUBSTATIC_DAY_SUB', '����ÿ�ձ���', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(84, 'SUBSTATIC_DAY_COU', '���Աÿ�ձ���', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(85, 'SUBSTATIC_DAY_COUNT', '���Ա����ͳ�Ʊ�', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(86, 'SUBSTATIC_MONTHCOUNT', '�½��û����ɼ���ϸ', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(87, 'SUBSTATIC_MONTHUSERCOUNT', '�½��û��±�', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(88, 'SUBSTATIC_MUSERRC', '�½�ͻ�ÿ�·���ͳ��', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(89, 'SUBSTATIC_CODLIST', '���ջ�����ϸ', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(90, 'SUBSTATIC_CODMONTH', '���ջ����±�', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(91, 'SUB_SPHB', '�������ɻ��ȱ�', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(92, 'COURIER_SPHB', '���Ա���ɻ��ȱ�', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, NULL),
	(93, 'YPJWQS', '���ɼ�δǩ�նԱȱ�', NULL, '2016-08-16 11:00:26', 11, 'LINK', 1, '124'),
	(94, 'SCAN_CREV', '���Ա�ռ�', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(95, 'SCAN_CSEND', '���Ա�ɼ�', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(96, 'MONITOR_ASIGN', '����ʱЧ���', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(97, 'MONITOR_TAKE', '�ռ�ʱЧ���', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(98, 'MONITOR_REV', '��ּ��', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(99, 'MONITOR_SEND', '���ּ��', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(100, 'SUBSTATIC_SUBSCAN', '����ɨ�����ݻ���', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(101, 'SUBSTATIC_SCAN', '�˵�ɨ���¼', NULL, '2016-08-16 11:07:56', 12, 'LINK', 1, NULL),
	(102, 'WAGES_LIST', '���ʱ�', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(103, 'WAGES_INPUT', '����¼��', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(104, 'WAGES_INPUT_LIST', '����¼����ˮ', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(105, 'WAGES_COST_MANAGER', '���Ա���ù���', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(106, 'WAGE_COST_EDIT', '���ñ༭��ˮ', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(107, 'WAGES_COST_NAME', '��������ά��', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, '124'),
	(108, 'WAGES_TC_EDIT', '���Ա���ά��', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(109, 'WAGES_TC_LIST', '���ά����¼', NULL, '2016-08-16 11:10:08', 13, 'LINK', 1, NULL),
	(110, 'JIAMENG_RULE', '����ά��', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(111, 'JIAMENG_ZLIST', '���ļ��˶���', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(112, 'JIAMENG_SLIST', '�����ɼ�����', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(113, 'JIAMENG_KAIZHANG', 'Ԥ�����', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(114, 'JIAMENG_CHONGZHI', 'Ԥ�����ֵ����', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, '124'),
	(115, 'JIAMENG_SUBZLIST', '������ת�Ѳ�ѯ', NULL, '2016-08-16 11:13:04', 14, 'LINK', 1, NULL),
	(116, 'JIAMENG_SUBSLIST', '�����ɼ��Ѳ�ѯ', NULL, '2016-08-16 11:13:05', 14, 'LINK', 1, NULL),
	(117, 'JIAMENG_YUE', 'Ԥ��������ѯ', NULL, '2016-08-16 11:13:05', 14, 'LINK', 1, NULL),
	(118, 'JIAMENG_DETAIL', '��֧��ϸ��ѯ', NULL, '2016-08-16 11:13:05', 14, 'LINK', 1, NULL),
	(119, 'JIAMENG_ACOUNT', '������˲�ѯ', NULL, '2016-08-16 11:13:05', 14, 'LINK', 1, NULL),
	(120, 'JUDGE_ALL', '���Ա���ۻ���', NULL, '2016-08-16 11:14:02', 15, 'LINK', 1, NULL),
	(121, 'JUDGE_LABEL', '��ǩ����', NULL, '2016-08-16 11:14:03', 15, 'LINK', 1, '124'),
	(122, 'JUDGE_DETAILLIST', 'ÿ����¼', NULL, '2016-08-16 11:14:03', 15, 'LINK', 1, NULL),
	(123, 'ZL_MAP_S', '��ͼ', NULL, '2016-08-16 11:14:34', 16, 'LINK', 1, '124'),
	(124, 'LISTSUBSTATION', '������������Ȩ��', NULL, '2016-08-16 11:18:45', 1, 'LINK', 0, NULL),
	(125, 'ORDER_ASSIGN_', '�鿴δ���䶩��', NULL, '2016-08-16 11:20:04', 1, 'LINK', 1, NULL),
	(126, 'CUSTOMER_LIST', '�ͻ��б�', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(127, 'CUSTOMER_HUIFANG', '�ͻ��ط�', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(128, 'CUSTOMER_REPORT', '�ͻ�����', NULL, '2016-08-16 10:05:00', 4, 'LINK', 1, '124'),
	(129, 'SYSTEM_PUNISH', '��������', NULL, '2016-08-16 10:05:00', 17, 'LINK', 1, NULL);


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
	(18, 'ϵͳ�����Ϲ���Ա', '����ͻ�����Ա����˾������ͺ�̨��¼�û�����', '2016-09-02 15:49:14', NULL),
	(19, '��������', '���ˡ��ͷ�����ѯ������', '2016-09-02 15:52:28', NULL),
	(20, '�ֹ�Ա', 'ɨ�衢�ͷ������', '2016-09-02 16:00:09', NULL),
	(21, '���㸺����', 'ɨ�衢�ͷ����������', '2016-09-02 16:02:39', NULL),
	(22, '¼��������Ա', '¼�����󵥡���ѯ', '2016-09-02 16:06:27', NULL),
	(23, '�ͷ���Ա', '�ͷ�����˾���ͻ�����Ա������', '2016-09-02 16:08:33', NULL),
	(24, '������Ա', '����', '2016-09-02 16:13:51', NULL),
	(25, '����Ա', '����Ȩ��', '2016-09-02 16:16:19', NULL);



alter table push_notice add type int(1) DEFAULT '0' COMMENT '0���أ�1���̨����';

alter table lgc add month_setting_url varchar(255);
alter table lgc add weixin_no varchar(255);
alter table lgc add process varchar(32);
alter table lgc add concat_person varchar(32);


ALTER TABLE `order_info` DROP INDEX `deled`;


