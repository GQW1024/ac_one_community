package ac_one.gqw1024.community.ac_one_community.dto;

import lombok.Data;

/**
 * 创建回复是页面传递的dto
 */
@Data
public class CommentCreateDto {
    Long questionId;//问题的id,不管是问题的回复数还是，回复的回复数都要给问题的回复数加1
    Long parentId;//父类的id
    String content;//评论的内容
    Integer type;//评论的类型，可能是评论问题的评论，也可能是评论评论的评论
}
