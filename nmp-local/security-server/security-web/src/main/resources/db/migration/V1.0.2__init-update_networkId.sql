ALTER TABLE nmp_secret_area_conf ADD `prefix_network_id` bigint DEFAULT NULL COMMENT '入网码前缀';
ALTER TABLE nmp_secret_area_conf ADD `suffix_network_id` bigint DEFAULT NULL COMMENT '入网码后缀';
ALTER TABLE nmp_ca_conf ADD `prefix_network_id` bigint DEFAULT NULL COMMENT '入网码前缀';
ALTER TABLE nmp_ca_conf ADD `suffix_network_id` bigint DEFAULT NULL COMMENT '入网码后缀';
ALTER TABLE nmp_dns_conf ADD `prefix_network_id` bigint DEFAULT NULL COMMENT '入网码前缀';
ALTER TABLE nmp_dns_conf ADD `suffix_network_id` bigint DEFAULT NULL COMMENT '入网码后缀';