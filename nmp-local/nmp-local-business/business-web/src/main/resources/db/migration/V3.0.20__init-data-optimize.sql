alter table nmpl_system_resource add index nsr_systemId_uploadTime(system_id,upload_time);

ALTER TABLE `nmpl_device_info` MODIFY `device_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '11' COMMENT '设备类型 11:密钥中心 12:生成机 13:缓存机 20:采集设备';

ALTER TABLE `nmpl_link_relation` MODIFY `link_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '链路类型: 01:边界基站-边界基站,02:基站-分发机,03:基站-缓存机,04:分发机-生成机,05:采集系统-边界基站';
