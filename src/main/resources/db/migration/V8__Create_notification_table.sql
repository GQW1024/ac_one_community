create table notification
(
    id bigint auto_increment comment '通知的id',
    notifier bigint not null comment '发出通知的人',
    receiver bigint not null comment '接收通知的人',
    outerId bigint not null comment '被回复对象的id',
    type int not null comment '被回复对象的类型，可以是问题或者是另一个回复',
    gmt_create bigint not null comment '通知创建时间',
    status int default 0 not null comment '判断该通知是已读还是未读，默认0为未读，1为已读',
    constraint notification_pk
        primary key (id)
)
    comment '通知表';
