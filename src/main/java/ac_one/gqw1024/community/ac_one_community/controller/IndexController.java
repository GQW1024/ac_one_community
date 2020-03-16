package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 首页Cntroller
 */
@Controller
@SessionAttributes({"user"})//将对应的用户信息存入session
public class IndexController {

    Cookie[] cookies;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public ModelAndView meeting(ModelAndView modelAndView, HttpServletRequest request){
        modelAndView.setViewName("index");
        if ((cookies = request.getCookies())!=null){
            for (Cookie c : cookies) {
                if(c.getName().equals("token")){
                    User user = userMapper.findByToken(c.getValue());
                    modelAndView.addObject("user", user);
                    break;
                }
            }
        }

        return modelAndView;
    }
    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
