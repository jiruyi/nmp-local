CREATE TABLE IF NOT EXISTS `sys_sequence` (
    `seq_name` varchar(50) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
    `min_value` int(11) NOT NULL,
    `max_value` int(11) NOT NULL,
    `current_value` int(11) NOT NULL,
    `increment_value` int(11) NOT NULL DEFAULT '1000',
    PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB;

DROP FUNCTION IF EXISTS `_nextval`;

DELIMITER $$

CREATE DEFINER=`root`@`%` FUNCTION `_nextval`(name varchar(50)) RETURNS int(11)
begin
declare _cur int;
declare _maxvalue int;  -- 接收最大值
declare _increment int; -- 接收增长步数
set _increment = (select increment_value from sys_sequence where seq_name = name);
set _maxvalue = (select max_value from sys_sequence where seq_name = name);
set _cur = (select current_value from sys_sequence where seq_name = name);
update sys_sequence                      -- 更新当前值
set current_value = _cur + increment_value
where seq_name = name ;
if(_cur + _increment >= _maxvalue) then  -- 判断是都达到最大值
update sys_sequence
set current_value = min_value
where seq_name = name ;
end if;
return _cur;

end$$

INSERT INTO `sys_sequence` (`seq_name`,`min_value`,`max_value`,`current_value`,`increment_value`)VALUES('seq_name1', 1, 99999999, 1000, 1);

