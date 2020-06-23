alter table notification
    add NOTIFIER_NAME varchar(100) null comment '发出通知的对象的名字';

alter table notification
    add OUTER_TITLE varchar(256) null comment '回复对象的title';