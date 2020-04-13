package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.model.Question;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes({"user"})//将对应的用户信息存入session
public class PublishController {

    @Autowired
    QuestionService questionService;

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
            @RequestParam("creator")Long creator,
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String questionTag,
            @RequestParam(value = "questionID",required = false)Long questionID,
            ModelAndView modelAndView){
        if(creator == null || title.isEmpty() || description.isEmpty() || questionTag.isEmpty()){
            modelAndView.addObject("error","错误提交！可能存在空值");

            modelAndView.addObject("title",title);//保证前端就算出错，已填写的数据也不消失
            modelAndView.addObject("description",description);
            modelAndView.addObject("questionTag",questionTag);

            modelAndView.setViewName("publish");
            return modelAndView;
        }
        //设置问题的信息
        Question question = new Question();
        question.setCreator(creator);//问题的作者
        question.setTitle(title);//标题
        question.setDescription(description);//内容
        question.setQuestionTag(questionTag);//标签
        question.setGmtCreate(System.currentTimeMillis());//创建时间
        question.setGmtModified(question.getGmtCreate());//修改时间
        question.setId(questionID);//问题的id，以这个值的有无来判断是否执行修改操作
        System.out.println("creator="+creator+" title="+title+" description="+description+" tag"+questionTag);
        if (questionService.createOrUpdate(question)==1) {//如果操作执行成功
            modelAndView.addObject("success", true);//返回提示
        }
        modelAndView.setViewName("redirect:/publish");
        return modelAndView;
    }

    @RequestMapping("/publish/{id}")
    public ModelAndView editQuestion (@PathVariable("id")Long id,ModelAndView modelAndView){
        Question question = questionService.getById(id);
        modelAndView.addObject("title",question.getTitle());//保证前端就算出错，已填写的数据也不消失
        modelAndView.addObject("description",question.getDescription());
        modelAndView.addObject("questionTag",question.getQuestionTag());
        modelAndView.addObject("creatorID",question.getCreator());
        modelAndView.addObject("questionID",question.getId());//【编辑问题】与【发布问题】的主要区分点，就是是否存在questionID这个值
        modelAndView.setViewName("publish");
        return modelAndView;
    }

}
