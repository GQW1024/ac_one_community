create table comment
(
    id int auto_increment comment '评论的id',
    parent_id int not null comment '父类的id',
    type int not null comment '父类类型，可能是问题，也可能是评论',
    commentator int not null comment '评论人的id',
    content varchar(200) not null comment '评论的内容',
    like_count int default 0 null comment '评论的点赞数',
    comment_count int default 0 null comment '评论的评论数',
    gmt_create BIGINT null comment '评论的创建日期',
    gmt_modified BIGINT null comment '评论的修改日期',
    constraint comment_pk
        primary key (id)
)
    comment '评论表';