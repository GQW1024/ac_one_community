alter table user modify account_id varchar(100) null comment 'github中用户的account_id';

alter table user modify name varchar(50) null comment 'github中用户的名字';

alter table user
    add bio varchar(256) null comment 'github中用户的bio';