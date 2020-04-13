package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页Cntroller
 */
@Controller
@SessionAttributes({"user"})//将对应的用户信息存入session
public class IndexController {

    @Autowired
    private UserMapper userMapper1;

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
            @RequestParam(value = "page",defaultValue = "1")int page,
            @RequestParam(value = "pageSize",defaultValue = "5")int pageSize,
            HttpServletRequest request,ModelAndView modelAndView){

        modelAndView.setViewName("index");

        PaginationDto paginationDto = questionService.LimitQuestionDtolist(page,pageSize);//获取当前页的问题列表，并存入【页面信息传输类】，然后返回
        modelAndView.addObject("pagination",paginationDto);
        return modelAndView;
    }
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
