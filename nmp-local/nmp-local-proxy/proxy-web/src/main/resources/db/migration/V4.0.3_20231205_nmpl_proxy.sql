/*
V4.0.3增量sql包
*/

USE `nmp_proxy`;

update nmpl_link set active_auth = null,link_type = null where main_device_type <> '02';

-- wq
truncate table nmpl_company_heartbeat;

truncate table nmpl_system_heartbeat;