alter table user modify id BIGINT auto_increment;

alter table question modify id BIGINT auto_increment;

alter table question modify creator BIGINT null comment '问题作者';

alter table comment modify id BIGINT auto_increment comment '评论的id';

alter table comment modify parent_id BIGINT not null comment '父类的id';

alter table comment modify commentator BIGINT not null comment '评论人的id';

