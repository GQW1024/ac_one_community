## 问答社区 <br/>
## 资料 <br/>
[Spring文档](https://spring.io/guides) <br/>
[SpringWEB文档](https://spring.io/guides/gs.serving-wed-content/) <br/>
[对标社区网站](https://elasticsearch.cn/explore) <br/>
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[Bootstrap](https://v3.bootcss.com/getting-started/)
[Github OAuth文档](https://developer.github.com/apps/building-github-apps/creating-a-github-app/)
## 工具 <br/>
[Git](https://git-scm.com/download) <br/>
[Visual Paradigm](https://www.visual-paradigm.com)<br/>
IntelliJ IDEA <br/>
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)
[Lombox](https:www.projectlombox.org)
## 脚本
```sql
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
```
```bash
mvn flyway:migrate
```