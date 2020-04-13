package ac_one.gqw1024.community.ac_one_community.dto;

import ac_one.gqw1024.community.ac_one_community.model.User;
import lombok.Data;

/**
 * @author GQW1024
 * @version 1.0
 * @date 2020/4/10 22:30
 */
@Data
public class CommentDto {
    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentator;

    private String content;

    private Integer likeCount;

    private Integer commentCount;

    private Long gmtCreate;

    private Long gmtModified;

    private Long gmtNow;

    private User commentCreator;
}
