package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dto.AccessTockenDTO;
import ac_one.gqw1024.community.ac_one_community.dto.GithubUser;
import ac_one.gqw1024.community.ac_one_community.provider.GithubProvider;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.http.HttpClient;

import java.io.IOException;
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
            String clientID; //Github上创建的 OAuth app 所持有的标识
    @Value("${github.client.secret}")
    String clientSecret; //Github上创建的 OAuth app 所持有的标识
    @Value("${github.redirect.uri}")
    String redirect_uri; //返回access_token的接口，/callback
    /**
     *
     * @param code  //authorize授权请求成功后，重定向回来时携带的数据，获取用户信息的步骤中所必要的数据
     * @param state //重定向回来时的状态
     * @param modelAndView
     * @return
     */
    @RequestMapping("/callback")
    public ModelAndView collback(@RequestParam(name = "code",required = false) String code,
                                 @RequestParam(name = "state",required = false) String state,
                                 ModelAndView modelAndView) throws IOException {
        AccessTockenDTO tockenDTO = new AccessTockenDTO();
        tockenDTO.setClient_id(clientID); //Github上创建的 OAuth app 所持有的标识
        tockenDTO.setClient_secret(clientSecret);  //Github上创建的 OAuth app 所持有的标识
        tockenDTO.setCode(code);
        tockenDTO.setRedirect_uri(redirect_uri);//返回access_token的接口，本方法
        tockenDTO.setState(state);
        String access_token =githubProvider.getAccessToken(tockenDTO);//获得返回的用户认证：access_token，并将其转为JSON字符串，方便使用        modelAndView.setViewName("index");
        //System.out.println(access_token);
        GithubUser user = githubProvider.getUser(access_token);//通过access_token向github获取用户信息
        modelAndView.addObject("user",user);//将用户信息返回到前端，随后存入session
        modelAndView.setViewName("index");
        System.out.println(user.toString());
        return modelAndView;
    }
}


