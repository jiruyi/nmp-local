/*
V4.0.3增量sql包
*/

USE `nmp_proxy`;

update nmpl_link set active_auth = null,link_type = null where main_device_type <> '02';