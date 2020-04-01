package ac_one.gqw1024.community.ac_one_community.service.impl;

import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.GithubUser;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.model.UserExample;
import ac_one.gqw1024.community.ac_one_community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * github用户的登录注册业务方法
     * @param githubUser
     * @return
     */
    @Override
    public String githubUserLogin(GithubUser githubUser) {

        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(githubUser.getId().toString());
        List<User> users = userMapper.selectByExample(example);
        //如果，数据库中存有该用户的信息,为了防止用户在github上修改了自身的信息，需要更新该用户的一部分信息
        if (users.size() == 1 && users.get(0)!= null) {
            String token = UUID.randomUUID().toString();
            User updateUsermessage = new User();
            updateUsermessage.setId(users.get(0).getId());
            updateUsermessage.setToken(token);//更新用户的token
            updateUsermessage.setBio(githubUser.getBio());//更新用户的简介
            updateUsermessage.setName(githubUser.getName());//更新用户的名称
            updateUsermessage.setAvatarUrl(githubUser.getAvatarUrl());//更新用户䣌头像
            updateUsermessage.setGmtModified(System.currentTimeMillis());//更新用户信息的修改时间

            UserExample updateexample = new UserExample();//逆向生成的代码内部会自行判断修改不为空的值
            updateexample.createCriteria()
                    .andIdEqualTo(updateUsermessage.getId());
            if(userMapper.updateByExampleSelective(updateUsermessage,updateexample)==1) {//更新GitHub用户的一些信息，成功后返回token
                return token;
            }
        }
        else{//否则，数据库中没有用户信息
            User newuser = new User();
            String token = UUID.randomUUID().toString();
            newuser.setToken(token);//设置唯一的 tocken 来做为后续获取该用户信息的标识,UUID的随机ID本身就有唯一属性
            newuser.setName(githubUser.getName());
            newuser.setAccountId(String.valueOf(githubUser.getId()));
            newuser.setGmtCreate(System.currentTimeMillis());//设置用户账户创建时间
            newuser.setBio(githubUser.getBio());//设置用户的自我介绍？
            newuser.setGmtModified(newuser.getGmtCreate());//设置用户最后一次登录的时间，此处为一开始创建账户的时候吗，故设置为账户创建时间
            newuser.setAvatarUrl(githubUser.getAvatarUrl());//用户的头像

            int iSsuccess = userMapper.insertSelective(newuser); //插入当前用户的数据
            if(iSsuccess == 1){//如果注册成功
                return token;//返回token
            }
        }
        return null;
    }
}
