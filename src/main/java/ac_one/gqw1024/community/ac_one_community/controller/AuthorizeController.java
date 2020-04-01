package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dto.AccessTokenDto;
import ac_one.gqw1024.community.ac_one_community.dto.GithubUser;
import ac_one.gqw1024.community.ac_one_community.provider.GithubProvider;
import ac_one.gqw1024.community.ac_one_community.service.UserService;
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

/**
 * Github用户信息处理Conntroller
 */
@Controller
//@SessionAttributes({"user"})//将对应的用户信息存入session
public class AuthorizeController {

    @Value("${github.client.id}")   //中间填写的是配置文件中对应的key
    private String clientID; //Github上创建的 OAuth app 所持有的标识\

    @Value("${github.client.secret}")
    private String clientSecret; //Github上创建的 OAuth app 所持有的标识\

    @Value("${github.redirect.uri}")
    private String redirect_uri; //返回access_token的接口，/callback

    @Autowired  //自动装配
    private GithubProvider githubProvider;//github用户信息提供者

    @Autowired
    private UserService userService;//用户业务类


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

        if(githubUser != null && githubUser.getId() != null) {  //双重保险，github用户信息必定不能为空
            String usertoken = null;
            if ((usertoken = userService.githubUserLogin(githubUser)) != null) {//注册登录用户信息，如果，用户已存在或用户注册成功,将其token取出
                //将可以指定用户信息的token添加到cookie中，防止因服务器重启而导致的用户端重新登录的情况
                Cookie ctoken = new Cookie("token", usertoken);
                ctoken.setMaxAge(7 * 24 * 60 * 60);//设置为7天过期
                response.addCookie(ctoken);
            }
        }

        return modelAndView;
    }

    /**
     * 用户登出，将token与用户信息 从cookie与session中删除
     * @param response
     * @param session
     * @param sessionStatus
     * @return
     */
    @RequestMapping("/doLoginOut")
    public String doLoginout(HttpServletResponse response,HttpSession session,SessionStatus sessionStatus){
        //删除cookie中的token
        Cookie ctoken = new Cookie("token",null);//
        ctoken.setMaxAge(0);
        response.addCookie(ctoken);
        //删除session中的用户信息
        session.removeAttribute("user");
        session.invalidate();//无效化session删除这次的用户会话信息
        sessionStatus.setComplete();
        return "redirect:/";
    }
}


