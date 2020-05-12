package ac_one.gqw1024.community.ac_one_community.dao;

import ac_one.gqw1024.community.ac_one_community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Question的一些工具事务
 */
@Repository
public interface QuestionExtMapper {
    /**
     * 更新问题的阅读数
     * @param question
     * @return
     */
    int incView(Question question);

    /**
     * 更新问题的评论数
     * @param question
     * @return
     */
    int incCommentCount(Question question);

    /**
     * 使用正则表达式查找所有匹配tag的问题
     * @param question
     * @return
     */
    List<Question> queryQuestionByTag(Question question);
}
