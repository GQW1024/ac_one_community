package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dto.AccessTockenDTO;
import ac_one.gqw1024.community.ac_one_community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.net.www.http.HttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Github用户信息处理Conntroller
 */
@Controller
public class AuthorizeConntroller {

    @Autowired  //自动装配
    private GithubProvider githubProvider;//github用户信息提供者
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
                                 ModelAndView modelAndView){

        modelAndView.setViewName("index");
        //modelAndView.addObject("name",name);
        return modelAndView;
    }
}
