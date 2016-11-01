INSERT INTO `manager_lgc`.`boss_menu` (`id`, `menu_uri`, `menu_text`, `menu_img`, `permision_code`, `pid`, `level`, `order_no`, `status`, `position_text`) VALUES (151, '/freightRule/rule', '�˷ѱ���ά��', NULL, 'LGC_FRULE', 2, 2, 15, 1, '��˾���˷ѱ���ά��');



ALTER TABLE `app_version`
	ADD COLUMN `ipa_address` VARCHAR(255) NULL DEFAULT NULL COMMENT 'ipa��ַ' AFTER `download_address`;



CREATE TABLE `wxxd_config` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`dskey` VARCHAR(20) NULL DEFAULT NULL,
`xd_url` VARCHAR(200) NULL DEFAULT NULL,
`appid` VARCHAR(200) NULL DEFAULT NULL,
`app_key` VARCHAR(200) NULL DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `dskey` (`dskey`)
)
COMMENT='΢���µ���Ϣ'
ENGINE=InnoDB
AUTO_INCREMENT=1;




insert into boss_menu(menu_uri,menu_text,menu_img,permision_code,pid,`level`,order_no,`status`,position_text)
values('/lgc/weightConf','���ӳ�����','','WEIGHT_CONFIG',2,2,8,1,'��˾>���ӳ�����');

insert into boss_menu(menu_uri,menu_text,menu_img,permision_code,pid,`level`,order_no,`status`,position_text)
values('/scan/revCenterScan','�������ɨ��','','CENTER_WAREHOUSE_REVSCAN',5,2,7,1,'ɨ��>�������ɨ��');

insert into sys_dict(dict_name,dict_key,dict_value) values('ORDER_SRC','BOSS_REV','��̨�ռ�');

insert into boss_menu(menu_text,permision_code,pid,level,order_no,`status`) VALUES('����','JUDGE_MANAGE',0,1,10,1);
insert into boss_menu(menu_uri,menu_text,permision_code,pid,level,order_no,`status`,position_text) VALUES('/judge/all','���Ա���ۻ���','JUDGE_ALL',(select id from boss_menu where permision_code='JUDGE_MANAGE'),2,1,1,'����>���Ա���ۻ���');
insert into boss_menu(menu_uri,menu_text,permision_code,pid,level,order_no,`status`,position_text) VALUES('/judge/label','��ǩ����','JUDGE_LABEL',(select id from boss_menu where permision_code='JUDGE_MANAGE'),2,2,1,'����>��ǩ����');
insert into boss_menu(menu_uri,menu_text,permision_code,pid,level,order_no,`status`,position_text) VALUES('/judge/detailList','ÿ����¼','JUDGE_DETAILLIST',(select id from boss_menu where permision_code='JUDGE_MANAGE'),2,3,1,'����>ÿ����¼');
INSERT INTO `boss_menu`(menu_uri,menu_text,permision_code,pid,level,order_no,`status`,position_text) VALUES ( '/notice/list', '����֪ͨ', 'NOTIFY_LIST', (select id from boss_menu where permision_code='DEFAULT'), '2', '9', '1', '�ͷ�>����֪ͨ');