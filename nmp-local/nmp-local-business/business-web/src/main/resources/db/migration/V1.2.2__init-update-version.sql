ALTER TABLE nmpl_version MODIFY file_name varchar(64);

ALTER TABLE nmpl_version MODIFY file_path varchar(128);

ALTER TABLE nmpl_version MODIFY version_desc varchar(1024);

DELETE FROM nmpl_menu WHERE menu_id = '103';

DELETE FROM nmpl_menu WHERE menu_id = '104';

DELETE FROM nmpl_menu WHERE menu_id = '105';