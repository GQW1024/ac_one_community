package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.AccessTokenDto;
import ac_one.gqw1024.community.ac_one_community.dto.GithubUser;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * Github用户信息处理Conntroller
 */
@Controller
//@SessionAttributes({"user"})//将对应的用户信息存入session
public class AuthorizeController {

    @Autowired  //自动装配
    private GithubProvider githubProvider;//github用户信息提供者

    @Value("${github.client.id}")   //中间填写的是配置文件中对应的key
    String clientID; //Github上创建的 OAuth app 所持有的标识\

    @Value("${github.client.secret}")
    String clientSecret; //Github上创建的 OAuth app 所持有的标识\

    @Value("${github.redirect.uri}")
    String redirect_uri; //返回access_token的接口，/callback

    private User user;//社区用户

    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param code  //authorize授权请求成功后，重定向回来时携带的数据，获取用户信息的步骤中所必要的数据
     * @param state //重定向回来时的状态
     * @param modelAndView
     * @param response
     * @return
     */
    @RequestMapping("/callback")
    public ModelAndView collback(
            @RequestParam(name = "code",required = false) String code,
            @RequestParam(name = "state",required = false) String state,
            HttpServletResponse response,
            ModelAndView modelAndView) throws IOException {

        modelAndView.setViewName("redirect:/");//设置重定向目标为首页

        AccessTokenDto tockenDTO = new AccessTokenDto();
        tockenDTO.setClient_id(clientID); //Github上创建的 OAuth app 所持有的标识
        tockenDTO.setClient_secret(clientSecret);  //Github上创建的 OAuth app 所持有的标识
        tockenDTO.setCode(code);
        tockenDTO.setRedirect_uri(redirect_uri);//返回access_token的接口，本方法
        tockenDTO.setState(state);
        String access_token =githubProvider.getAccessToken(tockenDTO);//获得返回的用户认证：access_token，并将其转为JSON字符串，方便使用        modelAndView.setViewName("index");
        //System.out.println(access_token);
        GithubUser githubUser = githubProvider.getUser(access_token);//通过access_token向github获取用户信息
        if(githubUser != null && githubUser.getId() != null) {  //双重保险，用户信息不为空

            if (( user = userMapper.findByAccountID(githubUser.getId().toString()) ) != null) {//如果，数据库中存有该用户的信息,将其取出后
                Cookie ctoken = new Cookie("token", user.getToken());
                ctoken.setMaxAge(7 * 24 * 60 * 60);//设置为7天过期
                response.addCookie(ctoken);
                return modelAndView;
            }
            else if (userMapper.findByAccountID(githubUser.getId().toString()) == null) {//否则，数据库中没有用户信息
                user = new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);//设置唯一的 tocken 标识该用户,UUID的随机ID本身就有唯一属性
                user.setName(githubUser.getName());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatarUrl());//用户的头像
                userMapper.insertUser(user); //插入当前用户的数据

                Cookie ctoken = new Cookie("token", token);//将用户专属的token存入cookie,使得首页每次可以通过token来查找数据库，确定当前用户信息
                ctoken.setMaxAge(7 * 24 * 60 * 60);//设置cookie为7天过期
                response.addCookie(ctoken);//向前端存入带有token的cookie,当重定向后，首页加载时会自动根据该token从数据库中查找信息
                return modelAndView;
            }
        }
        return modelAndView;
        //System.out.println(user.toString());
    }

    @RequestMapping("/doLoginOut")//登出
    public String doLoginout(HttpServletResponse response,HttpSession session,SessionStatus sessionStatus){
        Cookie ctoken = new Cookie("token",null);
        ctoken.setMaxAge(0);
        response.addCookie(ctoken);
        session.removeAttribute("user");
        session.invalidate();//无效化session删除这次的用户会话信息
        sessionStatus.setComplete();
        return "redirect:/";
    }
}


