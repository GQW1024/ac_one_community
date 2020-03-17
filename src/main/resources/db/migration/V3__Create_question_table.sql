create table question
(
    id int auto_increment,
    creator int null comment '问题作者',
    title varchar(50) null comment '问题标题',
    question_type varchar(50) null comment '问题类型',
    description TEXT null comment '问题的内容',
    question_tag varchar(256) null comment '问题标签',
    comment_count int default 0 null comment '评论数',
    view_count int default 0 null comment '阅读数',
    like_count int default 0 null comment '点赞数',
    gmt_create BIGINT null comment '问题创建时间',
    gmt_modified BIGINT null comment '问题修改时间',
    constraint question_pk
        primary key (id)
)
    comment '问题表';