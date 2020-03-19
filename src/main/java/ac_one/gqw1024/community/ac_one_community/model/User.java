package ac_one.gqw1024.community.ac_one_community.model;

import lombok.Data;

@Data//Lombox工具包中的注解，该注解会自动生成 【被注解类的】 ToString,equalsAndHashCode方法,以及 【被注解类当中的所有成员变量的】 Getter和Setter方法
public class User {
    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private Long gmt_create;//数据创建时间
    private long gmt_modified;//数据修改时间
    private String avatarUrl = "default_url";//用户头像
}
