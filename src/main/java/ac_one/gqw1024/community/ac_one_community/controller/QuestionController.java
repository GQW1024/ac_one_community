package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dto.QuestionDto;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ListIterator;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public ModelAndView question(
            @PathVariable("id") Integer id ,
            HttpServletRequest request,
            ModelAndView modelAndView){

        QuestionDto questionDto = questionService.getQuestionDtoById(id);//使用问题id来获取整合了用户与问题信息的QuestionDto对象
        if(questionDto != null){//如果信息不为空，则将信息添加至model,返回到正常页面
            modelAndView.addObject("questionDto",questionDto);
            modelAndView.setViewName("question");
        }else{//否则直接返回到首页
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
