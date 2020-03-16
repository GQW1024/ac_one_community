create table user
(
    id           int auto_increment
        primary key,
    account_id   varchar(100) null,
    name         varchar(50)  null,
    token        char(36)     null,
    gmt_create   bigint       null comment '数据创建时间',
    gmt_modified bigint       null comment '数据修改时间'
)
    comment '用户信息表';