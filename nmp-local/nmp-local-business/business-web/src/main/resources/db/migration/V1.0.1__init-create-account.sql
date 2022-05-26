CREATE TABLE `account` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `userid` int(11) DEFAULT NULL,
    `money` double DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
