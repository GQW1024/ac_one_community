package ac_one.gqw1024.community.ac_one_community.service;

import ac_one.gqw1024.community.ac_one_community.dto.CommentDto;
import ac_one.gqw1024.community.ac_one_community.enums.CommentTypeEnum;
import ac_one.gqw1024.community.ac_one_community.model.Comment;
import ac_one.gqw1024.community.ac_one_community.model.User;

import java.util.List;

/**
 * 【回复】的 业务接口
 * @author GQW1024
 * @version 1.0
 * @date 2020/4/4 22:19
 */
public interface CommentService {
    /**
     * 插入回复的业务方法
     * @param comment
     * @param questionId
     * @return
     */
    int insert(Comment comment, Long questionId, User commentator);

    List<CommentDto> listByQuestionIdAndType(Long id, int page, int pageSize, CommentTypeEnum type);
}
