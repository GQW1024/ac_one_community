package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.QuestionMapper;
import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.model.Question;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.provider.CookieUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"user"})//将对应的用户信息存入session
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @GetMapping("/publish")
    public ModelAndView publish(ModelAndView modelAndView, HttpServletRequest request){

        User user =(User) request.getSession().getAttribute("user");
        if (user==null){//如果用户没有登录，直接返回主页
            modelAndView.setViewName("index");
            return modelAndView;
        }
        modelAndView.setViewName("publish");
        return modelAndView;
    }

    /**
     * 上传用户提交的信息
     * @param creator
     * @param title
     * @param description
     * @param questionTag
     * @param modelAndView
     * @return
     */
    @PostMapping("/dopublish")
    public ModelAndView dopublish(
            @RequestParam("creator")Integer creator,
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String questionTag,
            ModelAndView modelAndView){
        if(creator == null || title.isEmpty() || description.isEmpty() || questionTag.isEmpty()){
            modelAndView.addObject("error","错误提交！可能存在空值");

            modelAndView.addObject("title",title);//保证前端就算出错，已填写的数据也不消失
            modelAndView.addObject("description",description);
            modelAndView.addObject("questionTag",questionTag);

            modelAndView.setViewName("publish");
            return modelAndView;
        }
        Question question = new Question();
        question.setCreator(creator);
        question.setTitle(title);
        question.setDescription(description);
        question.setQuestionTag(questionTag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        System.out.println("creator="+creator+" title="+title+" description="+description+" tag"+questionTag);
        modelAndView.addObject("success",true);
        modelAndView.setViewName("redirect:/publish");
        return modelAndView;
    }
}
