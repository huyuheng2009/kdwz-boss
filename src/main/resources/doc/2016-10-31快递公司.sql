ALTER TABLE `month_settle_user`
	ADD COLUMN `ht_date_begin` DATE NULL DEFAULT NULL AFTER `no_fix`,
	ADD COLUMN `ht_date_end` DATE NULL DEFAULT NULL AFTER `ht_date_begin`,
	ADD COLUMN `file1` VARCHAR(255) NULL DEFAULT NULL AFTER `ht_date_end`,
	ADD COLUMN `file2` VARCHAR(255) NULL DEFAULT NULL AFTER `file1`;



INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_sendlist', '寄件单查询列表', 'examiner', '寄件单审核人', 42, 0, '0');
INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_sendlist', '寄件单查询列表', 'examine_time', '寄件单审核时间', 43, 0, '0');
INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_sendlist', '寄件单查询列表', 'order_plane', '运单图片', 44, 0, '0');

INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_selist', '寄件单审核表', 'examiner', '寄件单审核人', 42, 0, '0');
INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_selist', '寄件单审核表', 'examine_time', '寄件单审核时间', 43, 0, '0');
INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_selist', '寄件单审核表', 'order_plane', '运单图片', 44, 0, '0');



insert into lgc_config(config_type,config_name,config_value,note) values('MOBILE_CONFIG','TAKE_SEND_MSG',0,'短信通知寄件人');
insert into lgc_config(config_type,config_name,config_value,note) values('MOBILE_CONFIG','SEND_SEND_MSG',0,'短信通知收件人');


update pro_deal_status set content='处理中' where id=3
update pro_deal_status set content='已处理' where id=6

insert into require_type(`code`,`describe`) values ('CODE_RULE','代收货款规则');
insert into require_type(`code`,`describe`) values ('VALUATION_RULE','保价规则');
insert into require_type(`code`,`describe`) values ('WEIGHTCONF','电子秤配置');
insert into require_type(`code`,`describe`) values ('MOBILEMSGCONFIG','短信配置');


update boss_auth set status=0 where auth_code = 'LGC_CRULE';
update boss_auth set status=0 where auth_code = 'LGC_VRULE';
update boss_auth set status=0 where auth_code = 'WEIGHT_CONFIG';


