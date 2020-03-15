package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.AccessTockenDTO;
import ac_one.gqw1024.community.ac_one_community.dto.GithubUser;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.provider.GithubProvider;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sun.net.www.http.HttpClient;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Github用户信息处理Conntroller
 */
@Controller
@SessionAttributes({"user"})//将对应的用户信息存入session
public class AuthorizeConntroller {

    @Autowired  //自动装配
    private GithubProvider githubProvider;//github用户信息提供者

    @Value("${github.client.id}")   //中间填写的是配置文件中对应的key
    String clientID; //Github上创建的 OAuth app 所持有的标识\

    @Value("${github.client.secret}")
    String clientSecret; //Github上创建的 OAuth app 所持有的标识\

    @Value("${github.redirect.uri}")
    String redirect_uri; //返回access_token的接口，/callback

    private User user;

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
    public ModelAndView collback(@RequestParam(name = "code",required = false) String code,
                                 @RequestParam(name = "state",required = false) String state,
                                 HttpServletResponse response,
                                 ModelAndView modelAndView) throws IOException {
        AccessTockenDTO tockenDTO = new AccessTockenDTO();
        tockenDTO.setClient_id(clientID); //Github上创建的 OAuth app 所持有的标识
        tockenDTO.setClient_secret(clientSecret);  //Github上创建的 OAuth app 所持有的标识
        tockenDTO.setCode(code);
        tockenDTO.setRedirect_uri(redirect_uri);//返回access_token的接口，本方法
        tockenDTO.setState(state);
        String access_token =githubProvider.getAccessToken(tockenDTO);//获得返回的用户认证：access_token，并将其转为JSON字符串，方便使用        modelAndView.setViewName("index");
        //System.out.println(access_token);
        GithubUser githubUser = githubProvider.getUser(access_token);//通过access_token向github获取用户信息
        if(githubUser != null) {  //用户信息不为空
            if (userMapper.selectUserByName(githubUser.getId().toString()) == null) {//且数据库中没有用户信息
                user = new User();
                user.setToken(UUID.randomUUID().toString());//设置唯一的 tocken ,UUID的随机ID本身就有唯一属性
                user.setName(githubUser.getName());
                user.setAccount_id(String.valueOf(githubUser.getId()));
                user.setGmt_create(System.currentTimeMillis());
                user.setGmt_modified(user.getGmt_create());
                userMapper.insertUser(user); //插入当前用户的数据
                modelAndView.addObject("user", user);//将 生成的 用户信息返回到前端，随后存入session
            }else if (( user = userMapper.selectUserByName(githubUser.getId().toString()) ) != null){//否则，数据库中也迅有该用户的信息，则直接取出并赋值给user
                modelAndView.addObject("user", user);//将用户信息返回到前端，随后存入session
            }
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
        //System.out.println(user.toString());
    }

    @RequestMapping("/doLoginOut")//登出
    public String doLogin(HttpSession session, SessionStatus sessionStatus){
        //userService.updateisRegister(0,uid);//将用户设为下线
        session.removeAttribute("user");
        session.invalidate();//无效化session删除这次的用户会话信息
        sessionStatus.setComplete();//提交当前session状态
        return "index";
    }
}


