package ac_one.gqw1024.community.ac_one_community.dao;

import ac_one.gqw1024.community.ac_one_community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper //为Mapper生成实现类
@Repository  //将Mapper注入到SpringIOC容器中
public interface UserMapper {

    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified) values(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified})")
    int insertUser(User user);//插入用户信息

    @Delete("delete from user where id = #{id}")
    int deltById(@Param("id") int id);//以用户id为key删除用户信息

    @Select("select * from user where account_id = #{account_id}")
    User findByName(@Param("account_id")String account_id);//以用户id为key查询用户信息

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token")String token);//以用户token为key查询用户信息

}
