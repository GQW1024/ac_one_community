package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.QuestionMapper;
import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.dto.QuestionDto;
import ac_one.gqw1024.community.ac_one_community.model.Question;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.service.QuestionService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 首页Cntroller
 */
@Controller
@SessionAttributes({"user"})//将对应的用户信息存入session
public class IndexController {

    Cookie[] cookies;//前端的cookie集合

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /**
     * 首页Mapping
     * @param modelAndView
     * @param request
     * @return
     */
    @GetMapping("/")
    public ModelAndView meeting(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "pageSize",defaultValue = "2")Integer pageSize,
            HttpServletRequest request,ModelAndView modelAndView){
        modelAndView.setViewName("index");
        if ((cookies = request.getCookies())!=null){//如果前端cookie不为空【防止空指针异常】
            for (Cookie c : cookies) {//则遍历cookie
                if(c.getName().equals("token")){//当找到name为token的cookie时，证明以前有用户在该浏览器中登陆过
                    User user = userMapper.findByToken(c.getValue());//通过token查找用户数据
                    request.getSession().setAttribute("user",user);
                    //modelAndView.addObject("user", user);//返回到前端
                    break;//退出循环
                }
            }
        }
        PaginationDto paginationDto = questionService.LimitQuestionDtolist(page,pageSize);//获取当前页的问题列表，并存入【页面信息传输类】，然后返回
        modelAndView.addObject("Page",paginationDto);
        return modelAndView;
    }
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
