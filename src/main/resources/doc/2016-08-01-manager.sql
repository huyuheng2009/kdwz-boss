INSERT INTO `manager_lgc`.`boss_menu` (`id`, `menu_uri`, `menu_text`, `menu_img`, `permision_code`, `pid`, `level`, `order_no`, `status`, `position_text`) VALUES (151, '/freightRule/rule', '运费报价维护', NULL, 'LGC_FRULE', 2, 2, 15, 1, '公司＞运费报价维护');



ALTER TABLE `app_version`
	ADD COLUMN `ipa_address` VARCHAR(255) NULL DEFAULT NULL COMMENT 'ipa地址' AFTER `download_address`;



CREATE TABLE `wxxd_config` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`dskey` VARCHAR(20) NULL DEFAULT NULL,
`xd_url` VARCHAR(200) NULL DEFAULT NULL,
`appid` VARCHAR(200) NULL DEFAULT NULL,
`app_key` VARCHAR(200) NULL DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `dskey` (`dskey`)
)
COMMENT='微信下单信息'
ENGINE=InnoDB
AUTO_INCREMENT=1;




insert into boss_menu(menu_uri,menu_text,menu_img,permision_code,pid,`level`,order_no,`status`,position_text)
values('/lgc/weightConf','电子秤配置','','WEIGHT_CONFIG',2,2,8,1,'公司>电子秤配置');

insert into boss_menu(menu_uri,menu_text,menu_img,permision_code,pid,`level`,order_no,`status`,position_text)
values('/scan/revCenterScan','中心入仓扫描','','CENTER_WAREHOUSE_REVSCAN',5,2,7,1,'扫描>中心入仓扫描');

insert into sys_dict(dict_name,dict_key,dict_value) values('ORDER_SRC','BOSS_REV','后台收件');

insert into boss_menu(menu_text,permision_code,pid,level,order_no,`status`) VALUES('评价','JUDGE_MANAGE',0,1,10,1);
insert into boss_menu(menu_uri,menu_text,permision_code,pid,level,order_no,`status`,position_text) VALUES('/judge/all','快递员评价汇总','JUDGE_ALL',(select id from boss_menu where permision_code='JUDGE_MANAGE'),2,1,1,'评价>快递员评价汇总');
insert into boss_menu(menu_uri,menu_text,permision_code,pid,level,order_no,`status`,position_text) VALUES('/judge/label','标签管理','JUDGE_LABEL',(select id from boss_menu where permision_code='JUDGE_MANAGE'),2,2,1,'评价>标签管理');
insert into boss_menu(menu_uri,menu_text,permision_code,pid,level,order_no,`status`,position_text) VALUES('/judge/detailList','每单记录','JUDGE_DETAILLIST',(select id from boss_menu where permision_code='JUDGE_MANAGE'),2,3,1,'评价>每单记录');
INSERT INTO `boss_menu`(menu_uri,menu_text,permision_code,pid,level,order_no,`status`,position_text) VALUES ( '/notice/list', '公告通知', 'NOTIFY_LIST', (select id from boss_menu where permision_code='DEFAULT'), '2', '9', '1', '客服>公告通知');