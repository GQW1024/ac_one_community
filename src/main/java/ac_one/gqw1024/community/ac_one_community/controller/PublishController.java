package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.QuestionMapper;
import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.model.Question;
import ac_one.gqw1024.community.ac_one_community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"user"})//将对应的用户信息存入session
public class PublishController {

    Cookie[] cookies;//前端的cookie集合

    @Autowired
    private UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @GetMapping("/publish")
    public ModelAndView publish(ModelAndView modelAndView, HttpServletRequest request){
        modelAndView.setViewName("publish");
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

    @PostMapping("/dopublish")
    public ModelAndView dopublish(
            @RequestParam("creator")Integer creator,
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String question_tag,
            ModelAndView modelAndView){
        if(creator == null || title.isEmpty() || description.isEmpty() || question_tag.isEmpty()){
            modelAndView.addObject("error","错误提交！可能存在空值");

            modelAndView.addObject("title",title);//保证前端就算出错，已填写的数据也不消失
            modelAndView.addObject("description",description);
            modelAndView.addObject("question_tag",question_tag);

            modelAndView.setViewName("publish");
            return modelAndView;
        }
        Question question = new Question();
        question.setCreator(creator);
        question.setTitle(title);
        question.setDescription(description);
        question.setQuestion_tag(question_tag);
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        questionMapper.create(question);
        System.out.println("creator="+creator+" title="+title+" description="+description+" tag"+question_tag);
        modelAndView.addObject("success","您已发布成功!");
        modelAndView.setViewName("publish");
        return modelAndView;
    }
}
