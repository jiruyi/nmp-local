/*
V4.0.1增量sql包
*/
-- 给nmpl_alarm_info表添加索引 jry
create index area_code_key on nmpl_alarm_info(alarm_area_code);

delete from nmpl_menu where menu_id = '14';

-- wq
truncate table nmpl_company_heartbeat;

truncate table nmpl_station_summary;