package ac_one.gqw1024.community.ac_one_community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;//问题的id
    private Integer creator;//问题创建者，提出该问题的用户的account_id
    private String title;//问题的标题
    private String question_type = "default_type";//问题的类型
    private String description;//问题的内容
    private String question_tag;//问题的标签
    private int comment_count;//问题的评论数,ps:为什么使用int? 答：因为要展示的数据的默认值必须为数字，不可以为null
    private int view_count;//问题的阅读数
    private int like_count;//问题的点赞数
    private Long gmt_create;//问题的创建时间
    private Long gmt_modified;//问题的修改时间
}
