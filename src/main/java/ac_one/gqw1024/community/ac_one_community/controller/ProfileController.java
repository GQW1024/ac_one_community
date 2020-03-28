package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.provider.CookieUserProvider;
import ac_one.gqw1024.community.ac_one_community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 个人页面控制器
 */
@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    /**
     * 跳转至个人中心/个人中心中的任意板块（action）
     * @param action    //个人中心中的板块名称
     * @param modelAndView
     * @return
     */
    @RequestMapping("/profile/{action}")
    public ModelAndView profile(
            @PathVariable(value = "action",required = false)String action,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "pageSize",defaultValue = "3")Integer pageSize,
            HttpServletRequest request,
            ModelAndView modelAndView){

        User user =(User) request.getSession().getAttribute("user");
        if (user==null){
            modelAndView.setViewName("index");
            return modelAndView;
        }

        if(action.equals("questions")){
            PaginationDto paginationDto = questionService.UserQuestionlist(user.getId(), page, pageSize);
            modelAndView.addObject("pagination",paginationDto);
            modelAndView.addObject("section","questions");//设置选中我的问题板块
            modelAndView.addObject("sectionName","我的提问");//设置选中的版块的名称
        }else if(action.equals("replies")){
            modelAndView.addObject("section","replies");//设置选中我的问题板块
            modelAndView.addObject("sectionName","最新回复");//设置选中的版块的名称
        }

        modelAndView.setViewName("profile");
        return modelAndView;
    }
}
