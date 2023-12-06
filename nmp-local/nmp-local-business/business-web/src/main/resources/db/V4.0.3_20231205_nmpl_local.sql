/*
V4.0.2增量sql包
*/

USE `nmp_local`;

-- wq
truncate table nmpl_company_heartbeat;

truncate table nmpl_system_heartbeat;

update nmpl_link set active_auth = null,link_type = null where main_device_type <> '02';