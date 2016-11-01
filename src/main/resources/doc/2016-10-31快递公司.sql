ALTER TABLE `month_settle_user`
	ADD COLUMN `ht_date_begin` DATE NULL DEFAULT NULL AFTER `no_fix`,
	ADD COLUMN `ht_date_end` DATE NULL DEFAULT NULL AFTER `ht_date_begin`,
	ADD COLUMN `file1` VARCHAR(255) NULL DEFAULT NULL AFTER `ht_date_end`,
	ADD COLUMN `file2` VARCHAR(255) NULL DEFAULT NULL AFTER `file1`;



INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_sendlist', '�ļ�����ѯ�б�', 'examiner', '�ļ��������', 42, 0, '0');
INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_sendlist', '�ļ�����ѯ�б�', 'examine_time', '�ļ������ʱ��', 43, 0, '0');
INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_sendlist', '�ļ�����ѯ�б�', 'order_plane', '�˵�ͼƬ', 44, 0, '0');

INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_selist', '�ļ�����˱�', 'examiner', '�ļ��������', 42, 0, '0');
INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_selist', '�ļ�����˱�', 'examine_time', '�ļ������ʱ��', 43, 0, '0');
INSERT INTO `table_field_sort` (`tab`, `tab_name`, `col`, `col_name`, `sort`, `is_show`, `user_no`) VALUES ('order_selist', '�ļ�����˱�', 'order_plane', '�˵�ͼƬ', 44, 0, '0');



insert into lgc_config(config_type,config_name,config_value,note) values('MOBILE_CONFIG','TAKE_SEND_MSG',0,'����֪ͨ�ļ���');
insert into lgc_config(config_type,config_name,config_value,note) values('MOBILE_CONFIG','SEND_SEND_MSG',0,'����֪ͨ�ռ���');


update pro_deal_status set content='������' where id=3
update pro_deal_status set content='�Ѵ���' where id=6

insert into require_type(`code`,`describe`) values ('CODE_RULE','���ջ������');
insert into require_type(`code`,`describe`) values ('VALUATION_RULE','���۹���');
insert into require_type(`code`,`describe`) values ('WEIGHTCONF','���ӳ�����');
insert into require_type(`code`,`describe`) values ('MOBILEMSGCONFIG','��������');


update boss_auth set status=0 where auth_code = 'LGC_CRULE';
update boss_auth set status=0 where auth_code = 'LGC_VRULE';
update boss_auth set status=0 where auth_code = 'WEIGHT_CONFIG';


