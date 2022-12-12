DROP TABLE `sys_sequence`;
CREATE TABLE IF NOT EXISTS `sys_sequence` (
    `seq_name` varchar(50) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
    `min_value` int NOT NULL,
    `max_value` bigint NOT NULL,
    `current_value` bigint NOT NULL,
    `increment_value` int NOT NULL,
    PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `sys_sequence` (`seq_name`,`min_value`,`max_value`,`current_value`,`increment_value`)
SELECT 'seq_name1', 1, 4294967295, 1, 1 WHERE NOT EXISTS (SELECT * FROM `sys_sequence` WHERE `seq_name`= 'seq_name1' );

