ALTER TABLE `item_type`
	ADD COLUMN `default_item` INT(11) NULL DEFAULT '0' AFTER `note`;

INSERT INTO `boss_auth` (`id`, `auth_code`, `auth_name`, `remark`, `create_time`, `parent_id`, `category`, `status`, `sub_permit`) VALUES (130, 'CENTER_WAREHOUSE_SENDSCAN', '中心出仓扫描', NULL, '2016-08-16 10:55:02', 9, 'LINK', 1, '124');


ALTER TABLE `boss_group`
	ADD COLUMN `creator` VARCHAR(255) NULL DEFAULT NULL AFTER `clogin`;


alter table push_notice add expire_time datetime;

alter table lgc_addr add incoming_phone varchar(32);

update table_field_sort set col_name='派件时间' where id=35;

update table_field_sort set col_name='派件时间' where id=50;

update table_field_sort set col='complete_time' where id=72;

update table_field_sort set col_name='派件时间' where id=88;

update table_field_sort set col='complete_time' where id=113;

update table_field_sort set col_name='派件时间' where id=154;

update table_field_sort set col_name='派件时间' where id=196;

