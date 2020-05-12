package ac_one.gqw1024.community.ac_one_community.dao;

import ac_one.gqw1024.community.ac_one_community.model.Comment;
import ac_one.gqw1024.community.ac_one_community.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}