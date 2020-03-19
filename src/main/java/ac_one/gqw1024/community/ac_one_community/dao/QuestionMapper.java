package ac_one.gqw1024.community.ac_one_community.dao;

import ac_one.gqw1024.community.ac_one_community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionMapper {
    @Insert("insert into question(creator,title,question_type,description,question_tag,gmt_create,gmt_modified) values(#{creator},#{title},#{question_type},#{description},#{question_tag},#{gmt_create},#{gmt_modified})")
    int create(Question question);
    /**
     * 通过问题的id查询指定的问题
     * @param id
     * @return
     */
    @Select("select * from question where id = #{id}")
    Question findQuestion(@Param("id")int id);
}
