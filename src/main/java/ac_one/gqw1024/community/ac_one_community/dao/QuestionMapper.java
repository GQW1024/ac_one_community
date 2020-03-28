package ac_one.gqw1024.community.ac_one_community.dao;

import ac_one.gqw1024.community.ac_one_community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    @Insert("insert into question(creator,title,question_type,description,question_tag,gmt_create,gmt_modified) values(#{creator},#{title},#{questionType},#{description},#{questionTag},#{gmtCreate},#{gmtModified})")
    int create(Question question);
    /**
     * 通过问题的id查询指定的问题
     * @param id
     * @return
     */
    @Select("select * from question where id = #{id}")
    Question findQuestion(@Param("id")int id);

    /**
     * 先按创建日期进行排序，如果相等，则继续按阅读数排序，如果还想等，则再按评论数排序，都排好序后，再按指定【页码】与【页内条数】进行分页
     * 从第pageOffect 条记录开始开始，查询 pageSize条记录
     * @param pageOffect
     * @param pageSize
     * @return
     */
    @Select("select * from question order by gmt_create ,view_count , comment_count ASC limit #{pageOffect},#{pageSize}")
    List<Question> pageList(@Param("pageOffect") int pageOffect,@Param("pageSize") int pageSize);
    /**
     * 查询用户本人发布的问题
     * 先按创建日期进行排序，如果相等，则继续按阅读数排序，如果还想等，则再按评论数排序，都排好序后，再按指定【页码】与【页内条数】进行分页
     * 从第pageOffect 条记录开始开始，查询 pageSize条记录
     * @param userid
     * @param pageOffect
     * @param pageSize
     * @return
     */
    @Select("select * from question where creator=#{userid} order by gmt_create ,view_count , comment_count ASC limit #{pageOffect},#{pageSize}")
    List<Question> userpageList(@Param("userid")int userid,@Param("pageOffect") int pageOffect,@Param("pageSize") int pageSize);

    /**
     * 查询记录数
     */
    @Select("select count(*) from question")
    int listCount();
    /**
     * 查询指定用户的记录数
     */
    @Select("select count(*) from question where creator = #{userid}")
    int listCountByUserID(@Param("userid") int userid);
}
